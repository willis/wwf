package com.mpaike.core.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.hibernate.id.UUIDHexGenerator;

public class StrUtils {
	
	public static final String C_BODY_END_REGEX = "<\\s*/\\s*body[^>]*>";
	public static final String C_BODY_START_REGEX = "<\\s*body[^>]*>";
	public static final String C_LINE_SEPARATOR = System.getProperty("line.separator");
	public static final String C_TABULATOR = "  ";
	
	private static final Pattern C_BODY_END_PATTERN = Pattern.compile(C_BODY_END_REGEX, 2);
	private static final Pattern C_BODY_START_PATTERN = Pattern.compile(C_BODY_START_REGEX, 2);
	private static final long C_DAYS = 0x5265c00L;
	private static final long C_HOURS = 0x36ee80L;
	private static final long C_MINUTES = 60000L;
	private static final long C_SECONDS = 1000L;
	private static final Pattern C_XML_ENCODING_REGEX = Pattern.compile("encoding\\s*=\\s*[\"'].+[\"']", 2);
	private static final Pattern C_XML_HEAD_REGEX = Pattern.compile("<\\s*\\?.*\\?\\s*>", 2);

	

	/**
	 * 私有构造方法，防止类的实例化，因为工具类不需要实例化。
	 */
	private StrUtils() {
	}

	/**
	 * 是否有中文字符
	 * 
	 * @param s
	 * @return
	 */
	public static boolean hasCn(String s) {
		if (s == null) {
			return false;
		}
		return countCn(s) > s.length();
	}

	/**
	 * 获得字符。符合中文习惯。
	 * 
	 * @param s
	 * @param length
	 * @return
	 */
	public static String getCn(String s, int len) {
		if (s == null) {
			return s;
		}
		int sl = s.length();
		if (sl <= len) {
			return s;
		}
		// 留出一个位置用于…
		len -= 1;
		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		while (count < maxCount && i < sl) {
			if (s.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
			i++;
		}
		if (count > maxCount) {
			i--;
		}
		return s.substring(0, i) + "…";
	}

	/**
	 * 计算GBK编码的字符串的字节数
	 * 
	 * @param s
	 * @return
	 */
	public static int countCn(String s) {
		if (s == null) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
		}
		return count;
	}

	/**
	 * 文本转html
	 * 
	 * @param txt
	 * @return
	 */
	public static String txt2htm(String txt) {
		if (StringUtils.isBlank(txt)) {
			return txt;
		}
		StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
		char c;
		for (int i = 0; i < txt.length(); i++) {
			c = txt.charAt(i);
			switch (c) {
			case '&':
				sb.append("&amp;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case ' ':
				sb.append("&nbsp;");
				break;
			case '\n':
				sb.append("<br/>");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * html转文本
	 * 
	 * @param htm
	 * @return
	 */
	public static String htm2txt(String htm) {
		if (htm == null) {
			return htm;
		}
		htm = htm.replace("&amp;", "&");
		htm = htm.replace("&lt;", "<");
		htm = htm.replace("&gt;", ">");
		htm = htm.replace("&quot;", "\"");
		htm = htm.replace("&nbsp;", " ");
		htm = htm.replace("<br/>", "\n");
		return htm;
	}

	/**
	 * 替换字符串
	 * 
	 * @param sb
	 * @param what
	 * @param with
	 * @return
	 */
	public static StringBuilder replace(StringBuilder sb, String what,
			String with) {
		int pos = sb.indexOf(what);
		while (pos > -1) {
			sb.replace(pos, pos + what.length(), with);
			pos = sb.indexOf(what);
		}
		return sb;
	}

	/**
	 * 替换字符串
	 * 
	 * @param s
	 * @param what
	 * @param with
	 * @return
	 */
	public static String replace(String s, String what, String with) {
		return replace(new StringBuilder(s), what, with).toString();
	}

	/**
	 * 全角-->半角
	 * 
	 * @param qjStr
	 * @return
	 */
	public String Q2B(String qjStr) {
		String outStr = "";
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < qjStr.length(); i++) {
			try {
				Tstr = qjStr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (b[3] == -1) {
				b[2] = (byte) (b[2] + 32);
				b[3] = 0;
				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else
				outStr = outStr + Tstr;
		}
		return outStr;
	}

	public static final char[] N62_CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };
	public static final char[] N36_CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };

	private static StringBuilder longToNBuf(long l, char[] chars) {
		int upgrade = chars.length;
		StringBuilder result = new StringBuilder();
		int last;
		while (l > 0) {
			last = (int) (l % upgrade);
			result.append(chars[last]);
			l /= upgrade;
		}
		return result;
	}

	/**
	 * 长整数转换成N62
	 * 
	 * @param l
	 * @return
	 */
	public static String longToN62(long l) {
		return longToNBuf(l, N62_CHARS).reverse().toString();
	}

	public static String longToN36(long l) {
		return longToNBuf(l, N36_CHARS).reverse().toString();
	}

	/**
	 * 长整数转换成N62
	 * 
	 * @param l
	 * @param length
	 *            如N62不足length长度，则补足0。
	 * @return
	 */
	public static String longToN62(long l, int length) {
		StringBuilder sb = longToNBuf(l, N62_CHARS);
		for (int i = sb.length(); i < length; i++) {
			sb.append('0');
		}
		return sb.reverse().toString();
	}

	public static String longToN36(long l, int length) {
		StringBuilder sb = longToNBuf(l, N36_CHARS);
		for (int i = sb.length(); i < length; i++) {
			sb.append('0');
		}
		return sb.reverse().toString();
	}

	/**
	 * N62转换成整数
	 * 
	 * @param n62
	 * @return
	 */
	public static long n62ToLong(String n62) {
		return nToLong(n62, N62_CHARS);
	}

	public static long n36ToLong(String n36) {
		return nToLong(n36, N36_CHARS);
	}

	private static long nToLong(String s, char[] chars) {
		char[] nc = s.toCharArray();
		long result = 0;
		long pow = 1;
		for (int i = nc.length - 1; i >= 0; i--, pow *= chars.length) {
			int n = findNIndex(nc[i], chars);
			result += n * pow;
		}
		return result;
	}

	private static int findNIndex(char c, char[] chars) {
		for (int i = 0; i < chars.length; i++) {
			if (c == chars[i]) {
				return i;
			}
		}
		throw new RuntimeException("N62(N36)非法字符：" + c);
	}

	private static final BitSet allowed_query;

	private static MessageDigest digest = null;

	static {
		allowed_query = new BitSet(256);
		for (int i = 48; i <= 57; i++) {
			allowed_query.set(i);
		}

		for (int i = 97; i <= 122; i++) {
			allowed_query.set(i);
		}

		for (int i = 65; i <= 90; i++) {
			allowed_query.set(i);
		}

		allowed_query.set(45);
		allowed_query.set(95);
		allowed_query.set(46);
		allowed_query.set(33);
		allowed_query.set(126);
		allowed_query.set(42);
		allowed_query.set(39);
		allowed_query.set(40);
		allowed_query.set(41);
	}

	public static String toUpperFirst(String source) {
		return source.substring(0, 1).toUpperCase() + source.substring(1);

	}


	/**
	 * 循环打印字符串数组。 字符串数组的各元素间以指定字符分隔，如果字符串中已经包含指定字符则在字符串的两端加上双引号。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param delim
	 *            分隔符
	 * @param out
	 *            打印到的输出流
	 * @since 0.4
	 */
	public static void printStrings(String[] strings, String delim,
			OutputStream out) {
		try {
			if (strings != null) {
				int length = strings.length - 1;
				for (int i = 0; i < length; i++) {
					if (strings[i] != null) {
						if (strings[i].indexOf(delim) > -1) {
							out.write(("\"" + strings[i] + "\"" + delim)
									.getBytes());
						} else {
							out.write((strings[i] + delim).getBytes());
						}
					} else {
						out.write("null".getBytes());
					}
				}
				if (strings[length] != null) {
					if (strings[length].indexOf(delim) > -1) {
						out.write(("\"" + strings[length] + "\"").getBytes());
					} else {
						out.write(strings[length].getBytes());
					}
				} else {
					out.write("null".getBytes());
				}
			} else {
				out.write("null".getBytes());
			}
			out.write(Constants.LINE_SEPARATOR.getBytes());
		} catch (IOException e) {

		}
	}

	/**
	 * 循环打印字符串数组到标准输出。 字符串数组的各元素间以指定字符分隔，如果字符串中已经包含指定字符则在字符串的两端加上双引号。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param delim
	 *            分隔符
	 * @since 0.4
	 */
	public static void printStrings(String[] strings, String delim) {
		printStrings(strings, delim, System.out);
	}

	/**
	 * 循环打印字符串数组。 字符串数组的各元素间以逗号分隔，如果字符串中已经包含逗号则在字符串的两端加上双引号。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param out
	 *            打印到的输出流
	 * @since 0.2
	 */
	public static void printStrings(String[] strings, OutputStream out) {
		printStrings(strings, ",", out);
	}

	/**
	 * 循环打印字符串数组到系统标准输出流System.out。 字符串数组的各元素间以逗号分隔，如果字符串中已经包含逗号则在字符串的两端加上双引号。
	 * 
	 * @param strings
	 *            字符串数组
	 * @since 0.2
	 */
	public static void printStrings(String[] strings) {
		printStrings(strings, ",", System.out);
	}

	/**
	 * 将字符串中的变量使用values数组中的内容进行替换。 替换的过程是不进行嵌套的，即如果替换的内容中包含变量表达式时不会替换。
	 * 
	 * @param prefix
	 *            变量前缀字符串
	 * @param source
	 *            带参数的原字符串
	 * @param values
	 *            替换用的字符串数组
	 * @return 替换后的字符串。 如果前缀为null则使用“%”作为前缀；
	 *         如果source或者values为null或者values的长度为0则返回source；
	 *         如果values的长度大于参数的个数，多余的值将被忽略；
	 *         如果values的长度小于参数的个数，则后面的所有参数都使用最后一个值进行替换。
	 * @since 0.2
	 */
	public static String getReplaceString(String prefix, String source,
			String[] values) {
		String result = source;
		if (source == null || values == null || values.length < 1) {
			return source;
		}
		if (prefix == null) {
			prefix = "%";
		}

		for (int i = 0; i < values.length; i++) {
			String argument = prefix + Integer.toString(i + 1);
			int index = result.indexOf(argument);
			if (index != -1) {
				String temp = result.substring(0, index);
				if (i < values.length) {
					temp += values[i];
				} else {
					temp += values[values.length - 1];
				}
				temp += result.substring(index + 2);
				result = temp;
			}
		}
		return result;
	}

	/**
	 * 将字符串中的变量（以“%”为前导后接数字）使用values数组中的内容进行替换。
	 * 替换的过程是不进行嵌套的，即如果替换的内容中包含变量表达式时不会替换。
	 * 
	 * @param source
	 *            带参数的原字符串
	 * @param values
	 *            替换用的字符串数组
	 * @return 替换后的字符串
	 * @since 0.1
	 */
	public static String getReplaceString(String source, String[] values) {
		return getReplaceString("%", source, values);
	}

	/**
	 * 字符串数组中是否包含指定的字符串。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @param caseSensitive
	 *            是否大小写敏感
	 * @return 包含时返回true，否则返回false
	 * @since 0.4
	 */
	public static boolean contains(String[] strings, String string,
			boolean caseSensitive) {
		for (int i = 0; i < strings.length; i++) {
			if (caseSensitive == true) {
				if (strings[i].equals(string)) {
					return true;
				}
			} else {
				if (strings[i].equalsIgnoreCase(string)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 字符串数组中是否包含指定的字符串。大小写敏感。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @return 包含时返回true，否则返回false
	 * @since 0.4
	 */
	public static boolean contains(String[] strings, String string) {
		return contains(strings, string, true);
	}

	/**
	 * 不区分大小写判定字符串数组中是否包含指定的字符串。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @return 包含时返回true，否则返回false
	 * @since 0.4
	 */
	public static boolean containsIgnoreCase(String[] strings, String string) {
		return contains(strings, string, false);
	}

	/**
	 * 将字符串数组使用指定的分隔符合并成一个字符串。
	 * 
	 * @param array
	 *            字符串数组
	 * @param delim
	 *            分隔符，为null的时候使用""作为分隔符（即没有分隔符）
	 * @return 合并后的字符串
	 * @since 0.4
	 */
	public static String combineStringArray(String[] array, String delim) {
		int length = array.length - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	/**
	 * 字符串比较
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean equalsString(String s1, String s2) {
		if (s1 == null) {
			if (s2 == null) {
				return true;
			} else {
				return false;
			}
		} else {
			if (s2 == null) {
				return false;
			} else {
				if (s1.equals(s2)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public static String[] insertHeadToArray(String[] array, String headString) {
		StringBuffer sb;
		for (int i = 0; i < array.length; i++) {
			sb = new StringBuffer(headString.length() + 16);
			sb.append(headString);
			sb.append(File.separator);
			sb.append(array[i]);
			array[i] = sb.toString();
		}
		return array;
	}

	/**
	 * 去除所有的回车。
	 * 
	 * @param source
	 * @param String
	 */
	public static String replaceEnter(String source) {
		return source.replace('\n', ' ');
	}

	/**
	 * 转换所有的回车为HTML中的分行。
	 * 
	 * @param source
	 * @param String
	 */
	public static String replaceEnterToBr(String source) {
		return source.replaceAll("\n", "<br />");
	}

	/**
	 * 把字符串数据转化成Base64的格式
	 * 
	 * @param data
	 *            String
	 * @return String
	 */
	public static String encodeBase64(String data) {
		byte bytes[] = null;
		try {
			bytes = data.getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee.getMessage());
		}
		return encodeBase64(bytes);
	}

	/**
	 * 把btye数组数据转化成Base64的格式
	 * 
	 * @param data
	 *            byte[]
	 * @return String
	 */
	public static String encodeBase64(byte data[]) {
		int len = data.length;
		StringBuffer ret = new StringBuffer((len / 3 + 1) * 4);
		for (int i = 0; i < len; i++) {
			int c = data[i] >> 2 & 0x3f;
			ret
					.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
							.charAt(c));
			c = data[i] << 4 & 0x3f;
			if (++i < len) {
				c |= data[i] >> 4 & 0xf;
			}
			ret
					.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
							.charAt(c));
			if (i < len) {
				c = data[i] << 2 & 0x3f;
				if (++i < len) {
					c |= data[i] >> 6 & 3;
				}
				ret
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(c));
			} else {
				i++;
				ret.append('=');
			}
			if (i < len) {
				c = data[i] & 0x3f;
				ret
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(c));
			} else {
				ret.append('=');
			}
		}

		return ret.toString();
	}

	/**
	 * 把Base64的格式字符串数据解成原始数据
	 * 
	 * @param data
	 *            String
	 * @return String
	 */
	public static String decodeBase64(String data) {
		byte bytes[] = null;
		try {
			bytes = data.getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee.getMessage());
		}
		return decodeBase64(bytes);
	}

	/**
	 * 把Base64的格式btye数组数据解成原始数据
	 * 
	 * @param data
	 *            byte[]
	 * @return String
	 */
	public static String decodeBase64(byte data[]) {
		int len = data.length;
		StringBuffer ret = new StringBuffer((len * 3) / 4);
		for (int i = 0; i < len; i++) {
			int c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
					.indexOf(data[i]);
			i++;
			int c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
					.indexOf(data[i]);
			c = c << 2 | c1 >> 4 & 3;
			ret.append((char) c);
			if (++i < len) {
				c = data[i];
				if (61 == c) {
					break;
				}
				c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
						.indexOf(c);
				c1 = c1 << 4 & 0xf0 | c >> 2 & 0xf;
				ret.append((char) c1);
			}
			if (++i >= len) {
				continue;
			}
			c1 = data[i];
			if (61 == c1) {
				break;
			}
			c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
					.indexOf(c1);
			c = c << 6 & 0xc0 | c1;
			ret.append((char) c);
		}

		return ret.toString();
	}

	/**
	 * 把指定URL字符串按照指定的编码转换
	 * 
	 * @param original
	 *            指定的URL字符串
	 * @param charset
	 *            指定的编码方式
	 * @return 转换后的URL
	 * @throws UnsupportedEncodingException
	 */
	public static String URLEncode(String original, String charset)
			throws UnsupportedEncodingException {
		if (original == null) {
			return null;
		}
		byte octets[];
		try {
			octets = original.getBytes(charset);
		} catch (UnsupportedEncodingException error) {
			throw new UnsupportedEncodingException();
		}
		StringBuffer buf = new StringBuffer(octets.length);
		for (int i = 0; i < octets.length; i++) {
			char c = (char) octets[i];
			if (allowed_query.get(c)) {
				buf.append(c);
			} else {
				buf.append('%');
				byte b = octets[i];
				char hexadecimal = Character.forDigit(b >> 4 & 0xf, 16);
				buf.append(Character.toUpperCase(hexadecimal));
				hexadecimal = Character.forDigit(b & 0xf, 16);
				buf.append(Character.toUpperCase(hexadecimal));
			}
		}

		return buf.toString();
	}

	/**
	 * 哈希数据
	 * 
	 * @param data
	 *            原始数据
	 * @return 哈希后的数据
	 */
	public static final synchronized String hash(String data) {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				System.out
						.println("Failed to load the MD5 MessageDigest. LMS will be unable to function normally."
								+ nsae.getMessage());
			}
		}
		try {
			digest.update(data.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}
		return encodeHex(digest.digest());
	}

	/**
	 * 
	 * @param bytes
	 *            byte[]
	 * @return String
	 */
	public static final String encodeHex(byte bytes[]) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 16) {
				buf.append("0");
			}
			buf.append(Long.toString(bytes[i] & 0xff, 16));
		}

		return buf.toString();
	}

	/**
	 * 
	 * @param hex
	 *            String
	 * @return byte[]
	 */
	public static final byte[] decodeHex(String hex) {
		char chars[] = hex.toCharArray();
		byte bytes[] = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			int newByte = 0;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = (byte) newByte;
			byteCount++;
		}

		return bytes;
	}

	/**
	 * 把16位字符转换成字节吗
	 * 
	 * @param ch
	 *            16位字符
	 * @return 字节
	 */
	private static final byte hexCharToByte(char ch) {
		switch (ch) {
		case 48: // '0'
			return 0;

		case 49: // '1'
			return 1;

		case 50: // '2'
			return 2;

		case 51: // '3'
			return 3;

		case 52: // '4'
			return 4;

		case 53: // '5'
			return 5;

		case 54: // '6'
			return 6;

		case 55: // '7'
			return 7;

		case 56: // '8'
			return 8;

		case 57: // '9'
			return 9;

		case 97: // 'a'
			return 10;

		case 98: // 'b'
			return 11;

		case 99: // 'c'
			return 12;

		case 100: // 'd'
			return 13;

		case 101: // 'e'
			return 14;

		case 102: // 'f'
			return 15;

		case 58: // ':'
		case 59: // ';'
		case 60: // '<'
		case 61: // '='
		case 62: // '>'
		case 63: // '?'
		case 64: // '@'
		case 65: // 'A'
		case 66: // 'B'
		case 67: // 'C'
		case 68: // 'D'
		case 69: // 'E'
		case 70: // 'F'
		case 71: // 'G'
		case 72: // 'H'
		case 73: // 'I'
		case 74: // 'J'
		case 75: // 'K'
		case 76: // 'L'
		case 77: // 'M'
		case 78: // 'N'
		case 79: // 'O'
		case 80: // 'P'
		case 81: // 'Q'
		case 82: // 'R'
		case 83: // 'S'
		case 84: // 'T'
		case 85: // 'U'
		case 86: // 'V'
		case 87: // 'W'
		case 88: // 'X'
		case 89: // 'Y'
		case 90: // 'Z'
		case 91: // '['
		case 92: // '\\'
		case 93: // ']'
		case 94: // '^'
		case 95: // '_'
		case 96: // '`'
		default:
			return 0;
		}
	}

	/**
	 * 更改文件的扩展名
	 * 
	 * @param filename
	 *            文件名
	 * @param suffix
	 *            新的扩展名
	 * @return 新的文件名
	 */
	public static String changeFileNameSuffixTo(String filename, String suffix) {
		int dotPos = filename.lastIndexOf('.');
		if (dotPos != -1) {
			return filename.substring(0, dotPos + 1) + suffix;
		} else {
			return filename;
		}
	}

	/**
	 * 转换javascript中的符号
	 * 
	 * @param source
	 *            代码
	 * @return 转换后的代码
	 */
	public static String escapeJavaScript(String source) {
		source = substitute(source, "\\", "\\\\");
		source = substitute(source, "\"", "\\\"");
		source = substitute(source, "'", "\\'");
		source = substitute(source, "\r\n", "\\n");
		source = substitute(source, "\n", "\\n");
		return source;
	}

	/**
	 * 消除字符串为Pattern,是一个正则表达式变为一个非正则表达式
	 * 
	 * @param source
	 *            正则表达式字符串
	 * @return 转换后的字符串
	 */
	public static String escapePattern(String source) {
		if (source == null) {
			return null;
		}
		StringBuffer result = new StringBuffer(source.length() * 2);
		for (int i = 0; i < source.length(); i++) {
			char ch = source.charAt(i);
			switch (ch) {
			case 92: // '\\'
				result.append("\\\\");
				break;

			case 47: // '/'
				result.append("\\/");
				break;

			case 36: // '$'
				result.append("\\$");
				break;

			case 94: // '^'
				result.append("\\^");
				break;

			case 46: // '.'
				result.append("\\.");
				break;

			case 42: // '*'
				result.append("\\*");
				break;

			case 43: // '+'
				result.append("\\+");
				break;

			case 124: // '|'
				result.append("\\|");
				break;

			case 63: // '?'
				result.append("\\?");
				break;

			case 123: // '{'
				result.append("\\{");
				break;

			case 125: // '}'
				result.append("\\}");
				break;

			case 91: // '['
				result.append("\\[");
				break;

			case 93: // ']'
				result.append("\\]");
				break;

			case 40: // '('
				result.append("\\(");
				break;

			case 41: // ')'
				result.append("\\)");
				break;

			default:
				result.append(ch);
				break;
			}
		}

		return new String(result);
	}

	/**
	 * 把text文本中的attribute属性抽取出来,其中返回的map中包含两个元素,一个是键值text,一个是键值value.返回的text键值中的值将不再包含attribute
	 * 
	 * @param text
	 *            text文本
	 * @param attribute
	 *            text中的属性
	 * @param defValue
	 *            默认属性值
	 * @return 其中返回的map中包含两个元素,一个是键值text,一个是键值value
	 */
	public static Map extendAttribute(String text, String attribute,
			String defValue) {
		Map retValue = new HashMap();
		retValue.put("text", text);
		retValue.put("value", "'" + defValue + "'");
		if (text != null
				&& text.toLowerCase().indexOf(attribute.toLowerCase()) >= 0) {
			String quotation = "'";
			int pos1 = text.toLowerCase().indexOf(attribute.toLowerCase());
			int pos2 = text.indexOf(quotation, pos1);
			int test = text.indexOf("\"", pos1);
			if (test > -1 && (pos2 == -1 || test < pos2)) {
				quotation = "\"";
				pos2 = test;
			}
			int pos3 = text.indexOf(quotation, pos2 + 1);
			String newValue = quotation + defValue
					+ text.substring(pos2 + 1, pos3 + 1);
			String newText = text.substring(0, pos1);
			if (pos3 < text.length()) {
				newText = newText + text.substring(pos3 + 1);
			}
			retValue.put("text", newText);
			retValue.put("value", newValue);
		}
		return retValue;
	}

	/**
	 * 把HTML中的body部分提取出来
	 * 
	 * @param content
	 *            HTML文本内容
	 * @return HTML中的body内容
	 */
	public static String extractHtmlBody(String content) {
		Matcher startMatcher = C_BODY_START_PATTERN.matcher(content);
		Matcher endMatcher = C_BODY_END_PATTERN.matcher(content);
		int start = 0;
		int end = content.length();
		if (startMatcher.find()) {
			start = startMatcher.end();
		}
		if (endMatcher.find(start)) {
			end = endMatcher.start();
		}
		return content.substring(start, end);
	}

	/**
	 * 得到XML文件头部标示的编码方式
	 * 
	 * @param content
	 *            XML文本内容
	 * @return 编码方式
	 */
	public static String extractXmlEncoding(String content) {
		String result = null;
		Matcher xmlHeadMatcher = C_XML_HEAD_REGEX.matcher(content);
		if (xmlHeadMatcher.find()) {
			String xmlHead = xmlHeadMatcher.group();
			Matcher encodingMatcher = C_XML_ENCODING_REGEX.matcher(xmlHead);
			if (encodingMatcher.find()) {
				String encoding = encodingMatcher.group();
				int pos1 = encoding.indexOf('=') + 2;
				String charset = encoding
						.substring(pos1, encoding.length() - 1);
				if (Charset.isSupported(charset)) {
					result = charset;
				}
			}
		}
		return result;
	}


	/**
	 * 判断字符串是否为空
	 * 
	 * @param value
	 *            字符串
	 * @return 判断结果
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.length() == 0;
	}

	/**
	 * 判断字符串去掉空白是否为空
	 * 
	 * @param value
	 *            字符串
	 * @return 判断结果
	 */
	public static boolean isEmptyOrWhitespaceOnly(String value) {
		return isEmpty(value) || value.trim().length() == 0;
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param value
	 *            字符串
	 * @return 判断结果
	 */
	public static boolean isNotEmpty(String value) {
		return value != null && value.length() != 0;
	}

	/**
	 * 判断字符串去掉空白是否不为空
	 * 
	 * @param value
	 *            字符串
	 * @return 判断结果
	 */
	public static boolean isNotEmptyOrWhitespaceOnly(String value) {
		return value != null && value.trim().length() > 0;
	}

	/**
	 * 判断className是否符合java命名规则
	 * 
	 * @param className
	 *            类名
	 * @return 判断结果
	 */
	public static boolean isValidJavaClassName(String className) {
		if (isEmpty(className)) {
			return false;
		}
		int length = className.length();
		boolean nodot = true;
		for (int i = 0; i < length; i++) {
			char ch = className.charAt(i);
			if (nodot) {
				if (ch == '.') {
					return false;
				}
				if (Character.isJavaIdentifierStart(ch)) {
					nodot = false;
				} else {
					return false;
				}
				continue;
			}
			if (ch == '.') {
				nodot = true;
				continue;
			}
			if (Character.isJavaIdentifierPart(ch)) {
				nodot = false;
			} else {
				return false;
			}
		}

		return true;
	}

	public static boolean isIdentifier(String str) {
		if ((str == null) || (str.length() < 2))
			return false;
		if (!(Character.isJavaIdentifierStart(str.charAt(0))))
			return false;
		for (int i = 1; i < str.length(); ++i)
			if (!(Character.isJavaIdentifierPart(str.charAt(i))))
				return false;

		return true;
	}
	
	
	/**
	 * 根据分隔符分割字符串成字符串数组
	 * 
	 * @param source
	 *            字符串
	 * @param delimiter
	 *            分隔符
	 * @return 字符串数组
	 */
	public static String[] splitAsArray(String source, char delimiter) {
		List result = splitAsList(source, delimiter);
		return (String[]) result.toArray(new String[result.size()]);
	}

	/**
	 * 根据分隔符分割字符串成字符串数组
	 * 
	 * @param source
	 *            字符串
	 * @param delimiter
	 *            分隔符
	 * @return 字符串数组
	 */
	public static String[] splitAsArray(String source, String delimiter) {
		List result = splitAsList(source, delimiter);
		return (String[]) result.toArray(new String[result.size()]);
	}

	/**
	 * 根据分隔符分割字符串成List数组
	 * 
	 * @param source
	 *            字符串
	 * @param delimiter
	 *            分隔符
	 * @return 字符串数组
	 */
	public static List splitAsList(String source, char delimiter) {
		return splitAsList(source, delimiter, false);
	}

	/**
	 * 根据分隔符分割字符串成List数组
	 * 
	 * @param source
	 *            字符串
	 * @param delimiter
	 *            分隔符
	 * @return 字符串数组
	 */
	public static List splitAsList(String source, String delimiter) {
		return splitAsList(source, delimiter, false);
	}

	/**
	 * 根据分隔符分割字符串成List数组
	 * 
	 * @param source
	 *            字符串
	 * @param delimiter
	 *            分隔符
	 * @param trim
	 *            是否去除空白
	 * @return 字符串数组
	 */
	public static List splitAsList(String source, char delimiter, boolean trim) {
		List result = new ArrayList();
		int index = 0;
		for (int next = source.indexOf(delimiter); next != -1; next = source
				.indexOf(delimiter, index)) {
			String item = source.substring(index, next);
			if (trim) {
				result.add(item.trim());
			} else {
				result.add(item);
			}
			index = next + 1;
		}

		if (trim) {
			result.add(source.substring(index).trim());
		} else {
			result.add(source.substring(index));
		}
		return result;
	}

	/**
	 * 根据分隔符分割字符串成List数组
	 * 
	 * @param source
	 *            字符串
	 * @param delimiter
	 *            分隔符
	 * @param trim
	 *            是否去除空白
	 * @return 字符串数组
	 */
	public static List splitAsList(String source, String delimiter, boolean trim) {
		int len = delimiter.length();
		if (len == 1) {
			return splitAsList(source, delimiter.charAt(0), trim);
		}
		List result = new ArrayList();
		int index = 0;
		for (int next = source.indexOf(delimiter); next != -1; next = source
				.indexOf(delimiter, index)) {
			String item = source.substring(index, next);
			if (trim) {
				result.add(item.trim());
			} else {
				result.add(item);
			}
			index = next + len;
		}

		if (trim) {
			result.add(source.substring(index).trim());
		} else {
			result.add(source.substring(index));
		}
		return result;
	}

	/**
	 * 搜索content内容字符串中serarchString的字符串,并且把它替换成replaceItem字符串的内容
	 * 
	 * @param content
	 *            内容字符串
	 * @param searchString
	 *            要搜索的字符串
	 * @param replaceItem
	 *            要替换成的字符串
	 * @return 处理后的字符串
	 */
	public static String substitute(String content, String searchString,
			String replaceItem) {
		if (content == null) {
			return null;
		}
		int stringLength = content.length();
		int findLength;
		if (searchString == null || (findLength = searchString.length()) == 0) {
			return content;
		}
		if (replaceItem == null) {
			replaceItem = "";
		}
		int replaceLength = replaceItem.length();
		int length;
		if (findLength == replaceLength) {
			length = stringLength;
		} else {
			int count = 0;
			int end;
			for (int start = 0; (end = content.indexOf(searchString, start)) != -1; start = end
					+ findLength) {
				count++;
			}

			if (count == 0) {
				return content;
			}
			length = stringLength - count * (findLength - replaceLength);
		}
		int start = 0;
		int end = content.indexOf(searchString, start);
		if (end == -1) {
			return content;
		}
		StringBuffer sb = new StringBuffer(length);
		for (; end != -1; end = content.indexOf(searchString, start)) {
			sb.append(content.substring(start, end));
			sb.append(replaceItem);
			start = end + findLength;
		}

		end = stringLength;
		sb.append(content.substring(start, end));
		return sb.toString();
	}

	/**
	 * 验证资源文件名是否符合规范
	 * 
	 * @param 文件名
	 * @return 返回结果
	 */
	public static boolean validateResourceName(String name) {
		if (name == null) {
			return false;
		}
		int l = name.length();
		if (l == 0) {
			return false;
		}
		if (name.length() != name.trim().length()) {
			return false;
		}
		for (int i = 0; i < l; i++) {
			char ch = name.charAt(i);
			switch (ch) {
			case 47: // '/'
				return false;

			case 92: // '\\'
				return false;

			case 58: // ':'
				return false;

			case 42: // '*'
				return false;

			case 63: // '?'
				return false;

			case 34: // '"'
				return false;

			case 62: // '>'
				return false;

			case 60: // '<'
				return false;

			case 124: // '|'
				return false;
			}
			if (Character.isISOControl(ch)) {
				return false;
			}
			if (!Character.isDefined(ch)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 提供小数位四舍五入处理。
	 * 
	 * @param d
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double d, int scale) {
		long temp = 1;
		for (int i = scale; i > 0; i--) {
			temp *= 10;
		}
		d *= temp;
		long dl = Math.round(d);
		return (double) (dl) / temp;
	}

	public static String versionUpgrade(String versionString) {
		BigDecimal bd = new BigDecimal(versionString);
		return String.valueOf(bd.add(new BigDecimal(0.01)).doubleValue());
	}

	public static String replaceBlank(String str) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		return m.replaceAll("");

	}
	
	public static int stringFormat(String s) {
		String str = s;
		char ch = 32;
		int length = str.length();
		Boolean boo = false;
		int count = 0;
		for (int i = 27, j = 0; j < length;) {
			char cha = str.charAt(i);
			if (ch != cha && i != length - 1) {
				i--;
				continue;
			} else {
				// System.out.println(str.substring(j, i + 1));
				count++;
				if (length - (i + 1) > 27) {
					j = i + 1;
					i += 27;
				} else {
					if (boo)
						break;
					else {
						boo = true;
						j = i;
						i += (length - (i + 1));
					}
				}
			}
		}
		return count;
	}

	/**
	     * 判断是否为中文
	     * @param args
	     */
	    public static boolean isChinese(String str) {
	        if(isNotEmpty(str)) {
	            return str.getBytes().length != str.length();
	        } else {
	            return false;
	        }
	    }
	    
		  public static String getLineContainingCharPosition(String buffer, int charPos)
		  {
		    int lineStart = 0;
		    int pos = charPos;
		    while (pos > 0) {
		      char c = buffer.charAt(pos);
		      if ((c == '\n') || (c == '\r')) {
		        lineStart = pos + 1;
		        break;
		      }
		      --pos;
		    }

		    int length = buffer.length();
		    int lineEnd = length;
		    pos = charPos;
		    while (pos < length) {
		      char c = buffer.charAt(pos);
		      if ((c == '\n') || (c == '\r')) {
		        lineEnd = pos;
		        break;
		      }
		      ++pos;
		    }
		    return buffer.substring(lineStart, lineEnd);
		  }

		  public static int countChars(String s, char c)
		  {
		    StringReader sr = new StringReader(s);
		    int count = 0;
		    try
		    {
		      int x;
		      while ((x = sr.read()) != -1)
		        if (x == c) ++count;
		    } catch (IOException ignored) {
		    }
		    return count;
		  }

		  public static int countCompleteLines(String buffer) {
		    return countChars(buffer, '\n');
		  }

		  public static int countLines(String buffer, int toCharPos)
		  {
		    int pos = 0;
		    int lines = 1;
		    while (pos < toCharPos) {
		      char c = buffer.charAt(pos);
		      if (c == '\r') {
		        ++lines;

		        if ((pos + 1 < toCharPos) && (buffer.charAt(pos + 1) == '\n'))
		          pos += 2;
		      }
		      else
		      {
		        if (c == '\n') ++lines;
		        ++pos;
		      }
		    }
		    return lines;
		  }
	    
		  public static String randomString(int length)
		  {
		    byte[] bytes = new byte[length];

		    Base64 encoder = new Base64();
		    new Random().nextBytes(bytes);
		    bytes = encoder.encode(bytes);

		    return new String(bytes, 0, length);
		  }

		  public static String randomText(int length) throws Exception {
			    String[] pool = { "0123456789", "abcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ" };

			    StringBuffer sb = new StringBuffer();

			    for (int i = 0; i < length; ++i) {
			      int usePool = (int)Math.floor(Math.random() * 3);
			      int useChar = (int)Math.floor(Math.random() * pool[usePool].length());
			      sb.append(pool[usePool].charAt(useChar));
			    }

			    return sb.toString();
			  }
		  
		  public static String md5(String plaintext)
		    throws Exception
		  {
		    return hashValue(plaintext, "MD5");
		  }

		  public static String sha(String plaintext)
		    throws Exception
		  {
		    return hashValue(plaintext, "SHA");
		  }

		  public static String hashValue(String plaintext)
		    throws Exception
		  {
		    return hashValue(plaintext, "MD5");
		  }

		  public static String hashValue(String plaintext, String algorithm)
		    throws Exception
		  {
		    MessageDigest md = MessageDigest.getInstance(algorithm);
		    md.reset();
		    md.update(plaintext.getBytes());
		    byte[] cipherbytes = md.digest();

		    BigInteger work = new BigInteger(1, cipherbytes);
		    String ciphertext = work.toString(16);

		    if (algorithm.equals("MD5")) {
		      ciphertext = "00000000000000000000000000000000".substring(ciphertext.length()) + ciphertext;
		    }
		    else if (algorithm.equals("SHA")) {
		      ciphertext = "0000000000000000000000000000000000000000".substring(ciphertext.length()) + ciphertext;
		    }

		    return ciphertext;
		  }
		  
		  public static String deriveTitleFromName(String name)
		  {
		    String work = name.replaceAll("_", " ");

		    work = work.trim();
		    String title = "";
		    if ((work == work.toUpperCase()) || (work == work.toLowerCase()))
		    {
		      work = work.toLowerCase();
		      boolean capNext = true;
		      for (int i = 0; i < work.length(); ++i) {
		        String letter = work.substring(i, i + 1);
		        if (capNext) {
		          letter = letter.toUpperCase();
		          capNext = false;
		        }
		        if (" ".equals(letter)) capNext = true;
		        title = title + letter;
		      }

		    }
		    else
		    {
		      title = work.substring(0, 1).toUpperCase();
		      boolean capLast = work.substring(0, 1).equals(work.substring(0, 1).toUpperCase());
		      boolean capSeq = false;
		      for (int i = 1; i < work.length(); ++i) {
		        String letter = work.substring(i, i + 1);
		        if ((capSeq) && (letter.equals(letter.toLowerCase()))) {
		          capSeq = false;
		          title = title.substring(0, title.length() - 1) + ' ' + title.substring(title.length() - 1);
		        }

		        if ((capLast) && (letter.equals(letter.toUpperCase())))
		          capSeq = true;

		        if ((!(capLast)) && (letter.equals(letter.toUpperCase())))
		          title = title + ' ';

		        capLast = letter.equals(letter.toUpperCase());
		        title = title + letter;
		      }
		    }

		    return title;
		  }
	
		  public static boolean stringToBooleanValue(String s)
		  {
		    if (s == null) { return false;
		    }

		    return ((s.equalsIgnoreCase("yes")) || (s.equalsIgnoreCase("true")));
		  }
		  
	public static void main(String[] args) {
	}
}
