package com.mpaike.core.util.ip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Title: IP查询
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class IPSeeker {

	/**
	 * <pre>
	 *    用来封装ip相关信息，目前只有两个字段，ip所在的国家和地区
	 * </pre>
	 */
	private class IPLocation {
		public String country;

		public String area;

		public IPLocation() {
			country = area = "";
		}

		public IPLocation getCopy() {
			IPLocation ret = new IPLocation();
			ret.country = country;
			ret.area = area;
			return ret;
		}
	}

	// 一些固定常量，比如记录长度等等
	private static final int IP_RECORD_LENGTH = 7;

	private static final byte REDIRECT_MODE_1 = 0x01;

	private static final byte REDIRECT_MODE_2 = 0x02;

	// Log对象
	private static Log log = LogFactory.getLog(IPSeeker.class);

	// 用来做为cache，查询一个ip时首先查看cache，以减少不必要的重复查找
	private Map<String, IPSeeker.IPLocation> ipCache;

	// 随机文件访问类
	//private RandomAccessFile ipFile;

	// 内存映射文件
	private BlockingQueue<MappedByteBuffer> queue = new LinkedBlockingQueue<MappedByteBuffer>();

	// 单一模式实例
	private static IPSeeker instance = new IPSeeker();
	
	private static final int poolSize = 20;
	// 起始地区的开始和结束的绝对偏移
	private long ipBegin, ipEnd;

	private static String IPDATE_FILE_PATH = "";

	private static final String IPDATE_FILE = "nnabc_isp.dat";

	/**
	 * 私有构造函数
	 */
	public IPSeeker() {
		ipCache = new ConcurrentHashMap<String, IPSeeker.IPLocation>();
		RandomAccessFile ipFile = null;
		long startMem = (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
		try {
//			 ClassPathResource cpr = new ClassPathResource("/" + IPDATE_FILE);
			 System.out.println(this.getClass().getResource("").getPath());
			IPDATE_FILE_PATH = this.getClass().getResource("").getPath();
			ipFile = new RandomAccessFile(IPDATE_FILE_PATH + IPDATE_FILE, "r");
			// ipFile = new RandomAccessFile(cpr.getFile(), "r");
		} catch (FileNotFoundException e) {
			// 如果找不到这个文件，再尝试再当前目录下搜索，这次全部改用小写文件名
			// 因为有些系统可能区分大小写导致找不到ip地址信息文件
			String filename = new File(IPDATE_FILE_PATH + IPDATE_FILE).getName().toLowerCase();
			File[] files = new File(IPDATE_FILE_PATH).listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					if (files[i].getName().toLowerCase().equals(filename)) {
						try {
							ipFile = new RandomAccessFile(files[i], "r");
						} catch (FileNotFoundException e1) {
							log.error("IP地址信息文件没有找到，IP显示功能将无法使用");
							ipFile = null;
						}
						break;
					}
				}
			}
		}
		// 如果打开文件成功，读取文件头信息
		if (ipFile != null) {
			try {
				// 映射IP信息文件到内存中
				FileChannel fc = ipFile.getChannel();
				MappedByteBuffer mbb;
				for(int i=0;i<poolSize;i++){
					mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, ipFile.length());
					mbb.order(ByteOrder.LITTLE_ENDIAN);
					queue.offer(mbb);
				}

				ipBegin = readLong4(0);
				ipEnd = readLong4(4);
				if (ipBegin == -1 || ipEnd == -1) {
					ipFile.close();
					ipFile = null;
				}

			} catch (IOException e) {
				log.error("IP地址信息文件格式有错误，IP显示功能将无法使用");
				ipFile = null;
			} 
		}
		System.out.println("mem end:"+((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())-startMem)/(1024)+"k");
	}

	/**
	 * @return 单一实例
	 */
	public static IPSeeker getInstance() {
	 return instance;
	}
	/**
	 * 给定一个地点的不完全名字，得到一系列包含s子串的IP范围记录
	 *
	 * @param s
	 *            地点子串
	 * @return 包含IPEntry类型的List
	 */
	public List getIPEntriesDebug(String s) {
		List<IPEntry> ret = new ArrayList<IPEntry>();
		byte[] b4 = new byte[4];
		long endOffset = ipEnd + 4;
		for (long offset = ipBegin + 4; offset <= endOffset; offset += IP_RECORD_LENGTH) {
			// 读取结束IP偏移
			long temp = readLong3(offset);
			// 如果temp不等于-1，读取IP的地点信息
			if (temp != -1) {
				IPLocation loc = getIPLocation(temp);
				// 判断是否这个地点里面包含了s子串，如果包含了，添加这个记录到List中，如果没有，继续
				if (loc.country.indexOf(s) != -1 || loc.area.indexOf(s) != -1) {
					IPEntry entry = new IPEntry();
					entry.country = loc.country;
					entry.area = loc.area;
					// 得到起始IP
					readIP(offset - 4, b4);
					entry.beginIp = getIpStringFromBytes(b4);
					// 得到结束IP
					readIP(temp, b4);
					entry.endIp = getIpStringFromBytes(b4);
					// 添加该记录
					ret.add(entry);
				}
			}
		}
		return ret;
	}

	/**
	 * 给定一个地点的不完全名字，得到一系列包含s子串的IP范围记录
	 *
	 * @param s
	 *            地点子串
	 * @return 包含IPEntry类型的List
	 */
	public List getIPEntries(String s) {
		List<IPEntry> ret = new ArrayList<IPEntry>();
//		try {
			byte[] b4 = new byte[4];
			int endOffset = (int) ipEnd;
			for (int offset = (int) ipBegin + 4; offset <= endOffset; offset += IP_RECORD_LENGTH) {
				int temp = readInt3(offset);
				if (temp != -1) {
					IPLocation loc = getIPLocation(temp);
					// 判断是否这个地点里面包含了s子串，如果包含了，添加这个记录到List中，如果没有，继续
					if (loc.country.indexOf(s) != -1 || loc.area.indexOf(s) != -1) {
						IPEntry entry = new IPEntry();
						entry.country = loc.country;
						entry.area = loc.area;
						// 得到起始IP
						readIP(offset - 4, b4);
						entry.beginIp = getIpStringFromBytes(b4);
						// 得到结束IP
						readIP(temp, b4);
						entry.endIp = getIpStringFromBytes(b4);
						// 添加该记录
						ret.add(entry);
					}
				}
			}
//		} catch (IOException e) {
//			log.error(e.getMessage());
//		}
		return ret;
	}

	/**
	 * 从内存映射文件的offset位置开始的3个字节读取一个int
	 *
	 * @param offset
	 * @return
	 */
	private int readInt3(int offset) {
		MappedByteBuffer mbb = queue.peek();
		mbb.position(offset);
		int a = mbb.getInt() & 0x00FFFFFF;
		queue.offer(mbb);
		return a;
	}

	/**
	 * 从内存映射文件的当前位置开始的3个字节读取一个int
	 *
	 * @return
	 */
	private int readInt3() {
		MappedByteBuffer mbb = queue.peek();
		int a = mbb.getInt() & 0x00FFFFFF;
		queue.offer(mbb);
		return a;
	}

	/**
	 * 根据IP得到国家名
	 *
	 * @param ip
	 *            ip的字节数组形式
	 * @return 国家名字符串
	 */
	public String getCountry(byte[] ip) {
		// 检查ip地址文件是否正常
//		if (ipFile == null) {
//			// return LumaQQ.getString("bad.ip.file");
//			return "bad.ip.file";  
//		}
		// 保存ip，转换ip字节数组为字符串形式
		String ipStr = getIpStringFromBytes(ip);
		// 先检查cache中是否已经包含有这个ip的结果，没有再搜索文件
		if (ipCache.containsKey(ipStr)) {
			IPLocation loc = (IPLocation) ipCache.get(ipStr);
			return loc.country;
		} else {
			IPLocation loc = getIPLocation(ip);
			ipCache.put(ipStr, loc.getCopy());
			return loc.country;
		}
	}

	/**
	 * 根据IP得到国家名
	 *
	 * @param ip
	 *            IP的字符串形式
	 * @return 国家名字符串
	 */
	public String getCountry(String ip) {
		return getCountry(getIpByteArrayFromString(ip));
	}

	/**
	 * 根据IP得到地区名
	 *
	 * @param ip
	 *            ip的字节数组形式
	 * @return 地区名字符串
	 */
	public String getArea(byte[] ip) {
		// 检查ip地址文件是否正常
//		if (ipFile == null) {
//			// return LumaQQ.getString("bad.ip.file");
//			return "bad.ip.file";
//		}
		// 保存ip，转换ip字节数组为字符串形式
		String ipStr = getIpStringFromBytes(ip);
		// 先检查cache中是否已经包含有这个ip的结果，没有再搜索文件
		if (ipCache.containsKey(ipStr)) {
			IPLocation loc = (IPLocation) ipCache.get(ipStr);
			return loc.area;
		} else {
			IPLocation loc = getIPLocation(ip);
			ipCache.put(ipStr, loc.getCopy());
			return loc.area;
		}
	}

	/**
	 * 根据IP得到地区名
	 *
	 * @param ip
	 *            IP的字符串形式
	 * @return 地区名字符串
	 */
	public String getArea(String ip) {
		return getArea(getIpByteArrayFromString(ip));
	}

	/**
	 * 根据ip搜索ip信息文件，得到IPLocation结构，所搜索的ip参数从类成员ip中得到
	 *
	 * @param ip
	 *            要查询的IP
	 * @return IPLocation结构
	 */
	private IPLocation getIPLocation(byte[] ip) {
		IPLocation info = null;
		long offset = locateIP(ip);
		if (offset != -1) {
			info = getIPLocation(offset);
		}
		if (info == null) {
			info = new IPLocation();
			// info.country = LumaQQ.getString("unknown.country");
			// info.area = LumaQQ.getString("unknown.area");
			info.country = "";
			info.area = "";
		}
		return info;
	}

	/**
	 * 从offset位置读取4个字节为一个long，因为java为big-endian格式，所以没办法 用了这么一个函数来做转换
	 *
	 * @param offset
	 * @return 读取的long值，返回-1表示读取文件失败
	 */
	private long readLong4(long offset) {
		long ret = 0;
		MappedByteBuffer mbb = queue.peek();
		mbb.position((int)offset);
		ret |= (mbb.get() & 0xFF);
		ret |= ((mbb.get() << 8) & 0xFF00);
		ret |= ((mbb.get() << 16) & 0xFF0000);
		ret |= ((mbb.get() << 24) & 0xFF000000);
		queue.offer(mbb);
		return ret;
	}

	/**
	 * 从offset位置读取3个字节为一个long，因为java为big-endian格式，所以没办法 用了这么一个函数来做转换
	 *
	 * @param offset
	 *            整数的起始偏移
	 * @return 读取的long值，返回-1表示读取文件失败
	 */
	private long readLong3(long offset) {
		long ret = 0;
		byte[] b3 = new byte[3];
		MappedByteBuffer mbb = queue.peek();
			mbb.position((int)offset);
			mbb.get(b3);
			ret |= (b3[0] & 0xFF);
			ret |= ((b3[1] << 8) & 0xFF00);
			ret |= ((b3[2] << 16) & 0xFF0000);
			return ret;
	}

	/**
	 * 从当前位置读取3个字节转换成long
	 *
	 * @return 读取的long值，返回-1表示读取文件失败
	 */
	private long readLong3() {
		long ret = 0;
		byte[] b3 = new byte[3];
		MappedByteBuffer mbb = queue.peek();
		mbb.get(b3);
		queue.offer(mbb);
		ret |= (b3[0] & 0xFF);
		ret |= ((b3[1] << 8) & 0xFF00);
		ret |= ((b3[2] << 16) & 0xFF0000);
		return ret;
	}

	/**
	 * 从offset位置读取四个字节的ip地址放入ip数组中，读取后的ip为big-endian格式，但是
	 * 文件中是little-endian形式，将会进行转换
	 *
	 * @param offset
	 * @param ip
	 */
	private void readIP(long offset, byte[] ip) {
		readIP((int)offset,ip);
	}

	/**
	 * 从offset位置读取四个字节的ip地址放入ip数组中，读取后的ip为big-endian格式，但是
	 * 文件中是little-endian形式，将会进行转换
	 *
	 * @param offset
	 * @param ip
	 */
	private void readIP(int offset, byte[] ip) {
		MappedByteBuffer mbb = queue.peek();
		mbb.position(offset);
		mbb.get(ip);
		queue.offer(mbb);
		byte temp = ip[0];
		ip[0] = ip[3];
		ip[3] = temp;
		temp = ip[1];
		ip[1] = ip[2];
		ip[2] = temp;
		
	}

	/**
	 * 把类成员ip和beginIp比较，注意这个beginIp是big-endian的
	 *
	 * @param ip
	 *            要查询的IP
	 * @param beginIp
	 *            和被查询IP相比较的IP
	 * @return 相等返回0，ip大于beginIp则返回1，小于返回-1。
	 */
	private int compareIP(byte[] ip, byte[] beginIp) {
		for (int i = 0; i < 4; i++) {
			int r = compareByte(ip[i], beginIp[i]);
			if (r != 0) {
				return r;
			}
		}
		return 0;
	}

	/**
	 * 把两个byte当作无符号数进行比较
	 *
	 * @param b1
	 * @param b2
	 * @return 若b1大于b2则返回1，相等返回0，小于返回-1
	 */
	private int compareByte(byte b1, byte b2) {
		if ((b1 & 0xFF) > (b2 & 0xFF)) { // 比较是否大于
			return 1;
		} else if ((b1 ^ b2) == 0) { // 判断是否相等
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * 这个方法将根据ip的内容，定位到包含这个ip国家地区的记录处，返回一个绝对偏移 方法使用二分法查找。
	 *
	 * @param ip
	 *            要查询的IP
	 * @return 如果找到了，返回结束IP的偏移，如果没有找到，返回-1
	 */
	private long locateIP(byte[] ip) {
		long m = 0;
		byte[] b4 = new byte[4];
		int r;
		// 比较第一个ip项
		readIP(ipBegin, b4);
		r = compareIP(ip, b4);
		if (r == 0) {
			return ipBegin;
		} else if (r < 0) {
			return -1;
		}
		// 开始二分搜索
		for (long i = ipBegin, j = ipEnd; i < j;) {
			m = getMiddleOffset(i, j);
			readIP(m, b4);
			r = compareIP(ip, b4);
			// log.debug(Utils.getIpStringFromBytes(b));
			if (r > 0) {
				i = m;
			} else if (r < 0) {
				if (m == j) {
					j -= IP_RECORD_LENGTH;
					m = j;
				} else {
					j = m;
				}
			} else {
				return readLong3(m + 4);
			}
		}
		// 如果循环结束了，那么i和j必定是相等的，这个记录为最可能的记录，但是并非
		// 肯定就是，还要检查一下，如果是，就返回结束地址区的绝对偏移
		m = readLong3(m + 4);
		readIP(m, b4);
		r = compareIP(ip, b4);
		if (r <= 0) {
			return m;
		} else {
			return -1;
		}
	}

	/**
	 * 得到begin偏移和end偏移中间位置记录的偏移
	 *
	 * @param begin
	 * @param end
	 * @return
	 */
	private long getMiddleOffset(long begin, long end) {
		long records = (end - begin) / IP_RECORD_LENGTH;
		records >>= 1;
		if (records == 0) {
			records = 1;
		}
		return begin + records * IP_RECORD_LENGTH;
	}

	/**
	 * 给定一个ip国家地区记录的偏移，返回一个IPLocation结构
	 *
	 * @param offset
	 *            国家记录的起始偏移
	 * @return IPLocation对象
	 */
	private IPLocation getIPLocation(long offset) {
			return getIPLocation((int)offset);
	}
	

	/**
	 * 给定一个ip国家地区记录的偏移，返回一个IPLocation结构，此方法应用与内存映射文件方式
	 *
	 * @param offset
	 *            国家记录的起始偏移
	 * @return IPLocation对象
	 */
	private IPLocation getIPLocation(int offset) {
		IPLocation loc = new IPLocation();
		MappedByteBuffer mbb = queue.peek();
		// 跳过4字节ip
		mbb.position(offset + 4);
		// 读取第一个字节判断是否标志字节
		byte b = mbb.get();
		if (b == REDIRECT_MODE_1) {
			// 读取国家偏移
			int countryOffset = readInt3();
			// 跳转至偏移处
			mbb.position(countryOffset);
			// 再检查一次标志字节，因为这个时候这个地方仍然可能是个重定向
			b = mbb.get();
			if (b == REDIRECT_MODE_2) {
				loc.country = readString(readInt3());
				mbb.position(countryOffset + 4);
			} else {
				loc.country = readString(countryOffset);
			}
			// 读取地区标志
			loc.area = readArea(mbb.position());
		} else if (b == REDIRECT_MODE_2) {
			loc.country = readString(readInt3());
			loc.area = readArea(offset + 8);
		} else {
			loc.country = readString(mbb.position() - 1);
			loc.area = readArea(mbb.position());
		}
		queue.offer(mbb);
		return loc;
	}

	/**
	 * 从offset偏移开始解析后面的字节，读出一个地区名
	 *
	 * @param offset
	 *            地区记录的起始偏移
	 * @return 地区名字符串
	 * @throws IOException
	 */
	private String readArea(long offset) {
		return readArea((int)offset);
	}

	/**
	 * @param offset
	 *            地区记录的起始偏移
	 * @return 地区名字符串
	 */
	private String readArea(int offset) {
		MappedByteBuffer mbb = queue.peek();
		mbb.position(offset);
		byte b = mbb.get();
		queue.offer(mbb);
		if (b == REDIRECT_MODE_1 || b == REDIRECT_MODE_2) {
			int areaOffset = readInt3();
			if (areaOffset == 0) {
				// return LumaQQ.getString("unknown.area");
				return "unknown.area";
			} else {
				return readString(areaOffset);
			}
		} else {
			return readString(offset);
		}
	}

	/**
	 * 从offset偏移处读取一个以0结束的字符串
	 *
	 * @param offset
	 *            字符串起始偏移
	 * @return 读取的字符串，出错返回空字符串
	 */
	private String readString(long offset) {
		return readString((int)offset);
	}
	

	/**
	 * 从内存映射文件的offset位置得到一个0结尾字符串
	 *
	 * @param offset
	 *            字符串起始偏移
	 * @return 读取的字符串，出错返回空字符串
	 */
	private String readString(int offset) {
		try {
			byte[] buf = new byte[100];
			MappedByteBuffer mbb = queue.peek();
			mbb.position(offset);
			int i;
			for (i = 0, buf[i] = mbb.get(); buf[i] != 0; buf[++i] = mbb.get()) {
				;
			}
			queue.offer(mbb);
			if (i != 0) {
				return getString(buf, 0, i, "GBK");
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		}
		return "";
	}

	public static String getIpStringFromBytes(byte[] ip) {
		StringBuffer sb = new StringBuffer();
		sb.append(ip[0] & 0xFF);
		sb.append('.');
		sb.append(ip[1] & 0xFF);
		sb.append('.');
		sb.append(ip[2] & 0xFF);
		sb.append('.');
		sb.append(ip[3] & 0xFF);
		return sb.toString();
	}

	public static String getString(byte[] b, int offset, int len, String encoding) {
		try {
			return new String(b, offset, len, encoding);
		} catch (UnsupportedEncodingException ex) {
			return new String(b, offset, len);
		}
	}

	public static byte[] getIpByteArrayFromString(String ip) {
		byte[] ret = new byte[4];
		StringTokenizer st = new StringTokenizer(ip, ".");
		try {
			ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
			ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return ret;
	}

	public static void main(String[] args) {

		for(int i=0;i<10000;i++){
			 System.out.println(IPSeeker.getInstance().getCountry("123.123.34.33"));
			 System.out.println(IPSeeker.getInstance().getCountry("210.51.190.3"));
			 System.out.println(IPSeeker.getInstance().getArea("210.51.190.3"));
		}

	}
}
