package cn.com.icore.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

public class ParamHelper {
	public static String ENCODING_DEFAULT = "UTF-8";
	public static String GET_ENCODING_DEFAULT = "UTF-8";
	public static String FILE_WRITING_ENCODING = "GBK";

	public ParamHelper()
	{
	}

	public static int getIntParamter(HttpServletRequest _request, String _param, int _defaultValue)
	{
		int dv = _defaultValue;
		int rdv = 0;
		String strIn = _request.getParameter(_param);
		if (isEmpty(strIn))
			return dv;
		try
		{
			rdv = Integer.parseInt(strIn);
		}
		catch (Exception e)
		{
			rdv = dv;
		}
		return rdv;
	}

	public static int getIntAttribute(HttpServletRequest _request, String _param, int _defaultValue)
	{
		int dv = _defaultValue;
		int rdv = 0;
		Integer iIn = (Integer)_request.getAttribute(_param);
		if (iIn == null)
			return dv;
		try
		{
			rdv = iIn.intValue();
		}
		catch (Exception e)
		{
			rdv = dv;
		}
		return rdv;
	}

	public static long getLongAttribute(HttpServletRequest _request, String _param, long _defaultValue)
	{
		long dv = _defaultValue;
		long rdv = 0L;
		Long lIn = (Long)_request.getAttribute(_param);
		if (lIn == null)
			return dv;
		try
		{
			rdv = lIn.longValue();
		}
		catch (Exception e)
		{
			rdv = dv;
		}
		return rdv;
	}

	public static long getLongParamter(HttpServletRequest _request, String _param, long _defaultValue)
	{
		long dv = _defaultValue;
		long rdv = 0L;
		String strIn = _request.getParameter(_param);
		if (isEmpty(strIn))
			return dv;
		try
		{
			rdv = Long.parseLong(strIn);
		}
		catch (Exception e)
		{
			rdv = dv;
		}
		return rdv;
	}

	public static String getStr(HttpServletRequest _request, String _param, String _defaultValue)
	{
		String dv = _defaultValue;
		String strIn = _request.getParameter(_param);
		if (isEmpty(strIn))
			return dv;
		else
			return getStr(strIn).trim();
	}

	public static String getStr(HttpServletRequest _request, String _param, String _defaultValue, String _encoding)
	{
		String dv = _defaultValue;
		String strIn = _request.getParameter(_param);
		if (isEmpty(strIn))
			return dv;
		else
			return getStr(strIn, _encoding);
	}

	public static boolean isEmpty(String _string)
	{
		return _string == null || _string.trim().length() == 0;
	}

	public static boolean isEmptyStr(String _string)
	{
		return _string == null || _string.trim().length() == 0;
	}

	public static String showObjNull(Object p_sValue)
	{
		return showObjNull(p_sValue, "");
	}

	public static String showObjNull(Object _sValue, String _sReplaceIfNull)
	{
		if (_sValue == null)
			return _sReplaceIfNull;
		else
			return _sValue.toString();
	}

	public static String showNull(String p_sValue)
	{
		return showNull(p_sValue, "");
	}

	public static String showNull(String _sValue, String _sReplaceIfNull)
	{
		return _sValue == null ? _sReplaceIfNull : _sValue;
	}

	public static String expandStr(String _string, int _length, char _chrFill, boolean _bFillOnLeft)
	{
		int nLen = _string.length();
		if (_length <= nLen)
			return _string;
		String sRet = _string;
		for (int i = 0; i < _length - nLen; i++)
			sRet = _bFillOnLeft ? (new StringBuilder(String.valueOf(_chrFill))).append(sRet).toString() : (new StringBuilder(String.valueOf(sRet))).append(_chrFill).toString();

		return sRet;
	}

	public static String setStrEndWith(String _string, char _chrEnd)
	{
		if (_string == null)
			return null;
		if (_string.charAt(_string.length() - 1) != _chrEnd)
			return (new StringBuilder(String.valueOf(_string))).append(_chrEnd).toString();
		else
			return _string;
	}

	public static String replaceStr(String _strSrc, String _strOld, String _strNew)
	{
		if (_strSrc == null || _strNew == null || _strOld == null)
			return _strSrc;
		char srcBuff[] = _strSrc.toCharArray();
		int nSrcLen = srcBuff.length;
		if (nSrcLen == 0)
			return "";
		char oldStrBuff[] = _strOld.toCharArray();
		int nOldStrLen = oldStrBuff.length;
		if (nOldStrLen == 0 || nOldStrLen > nSrcLen)
			return _strSrc;
		StringBuffer retBuff = new StringBuffer(nSrcLen * (1 + _strNew.length() / nOldStrLen));
		boolean bIsFound = false;
		for (int i = 0; i < nSrcLen;)
		{
			bIsFound = false;
			if (srcBuff[i] == oldStrBuff[0])
			{
				int j;
				for (j = 1; j < nOldStrLen; j++)
					if (i + j >= nSrcLen || srcBuff[i + j] != oldStrBuff[j])
						break;

				bIsFound = j == nOldStrLen;
			}
			if (bIsFound)
			{
				retBuff.append(_strNew);
				i += nOldStrLen;
			} else
			{
				int nSkipTo;
				if (i + nOldStrLen >= nSrcLen)
					nSkipTo = nSrcLen - 1;
				else
					nSkipTo = i;
				for (; i <= nSkipTo; i++)
					retBuff.append(srcBuff[i]);

			}
		}

		srcBuff = (char[])null;
		oldStrBuff = (char[])null;
		return retBuff.toString();
	}

	public static String replaceStr(StringBuffer _strSrc, String _strOld, String _strNew)
	{
		if (_strSrc == null)
			return null;
		int nSrcLen = _strSrc.length();
		if (nSrcLen == 0)
			return "";
		char oldStrBuff[] = _strOld.toCharArray();
		int nOldStrLen = oldStrBuff.length;
		if (nOldStrLen == 0 || nOldStrLen > nSrcLen)
			return _strSrc.toString();
		StringBuffer retBuff = new StringBuffer(nSrcLen * (1 + _strNew.length() / nOldStrLen));
		boolean bIsFound = false;
		for (int i = 0; i < nSrcLen;)
		{
			bIsFound = false;
			if (_strSrc.charAt(i) == oldStrBuff[0])
			{
				int j;
				for (j = 1; j < nOldStrLen; j++)
					if (i + j >= nSrcLen || _strSrc.charAt(i + j) != oldStrBuff[j])
						break;

				bIsFound = j == nOldStrLen;
			}
			if (bIsFound)
			{
				retBuff.append(_strNew);
				i += nOldStrLen;
			} else
			{
				int nSkipTo;
				if (i + nOldStrLen >= nSrcLen)
					nSkipTo = nSrcLen - 1;
				else
					nSkipTo = i;
				for (; i <= nSkipTo; i++)
					retBuff.append(_strSrc.charAt(i));

			}
		}

		oldStrBuff = (char[])null;
		return retBuff.toString();
	}

	public static String getStr(String _strSrc)
	{
		return getStr(_strSrc, ((String) (null)));
	}

	public static String getStr(String _strSrc, boolean _bPostMethod)
	{
		return getStr(_strSrc, _bPostMethod ? ENCODING_DEFAULT : GET_ENCODING_DEFAULT);
	}

	public static String getStr(String _strSrc, String _encoding)
	{
		if (_encoding == null || _encoding.length() == 0)
			return _strSrc;
		byte byteStr[];
		byteStr = new byte[_strSrc.length()];
		char charStr[] = _strSrc.toCharArray();
		for (int i = byteStr.length - 1; i >= 0; i--)
			byteStr[i] = (byte)charStr[i];
		try {
			return new String(byteStr, _encoding);
		} catch (UnsupportedEncodingException e) {
	
			e.printStackTrace();
		}
	

		return _strSrc;
	}

	public static String toISO_8859(String _strSrc)
	{
		if (_strSrc == null)
			return null;
		try {
			return new String(_strSrc.getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
		
			e.printStackTrace();
		}

		return _strSrc;
	}

	public static String toUnicode(String _strSrc)
	{
		return toISO_8859(_strSrc);
	}

	public static byte[] getUTF8Bytes(String _string)
	{
		char c[] = _string.toCharArray();
		int len = c.length;
		int count = 0;
		for (int i = 0; i < len; i++)
		{
			int ch = c[i];
			if (ch <= 127)
				count++;
			else
			if (ch <= 2047)
				count += 2;
			else
				count += 3;
		}

		byte b[] = new byte[count];
		int off = 0;
		for (int i = 0; i < len; i++)
		{
			int ch = c[i];
			if (ch <= 127)
				b[off++] = (byte)ch;
			else
			if (ch <= 2047)
			{
				b[off++] = (byte)(ch >> 6 | 0xc0);
				b[off++] = (byte)(ch & 0x3f | 0x80);
			} else
			{
				b[off++] = (byte)(ch >> 12 | 0xe0);
				b[off++] = (byte)(ch >> 6 & 0x3f | 0x80);
				b[off++] = (byte)(ch & 0x3f | 0x80);
			}
		}

		return b;
	}

	public static String getUTF8String(byte b[])
	{
		return getUTF8String(b, 0, b.length);
	}

	public static String getUTF8String(byte b[], int off, int len)
	{
		int count = 0;
		int max = off + len;
		int i;
		for (i = off; i < max;)
		{
			int c = b[i++] & 0xff;
			switch (c >> 4)
			{
			case 0: // '\0'
			case 1: // '\001'
			case 2: // '\002'
			case 3: // '\003'
			case 4: // '\004'
			case 5: // '\005'
			case 6: // '\006'
			case 7: // '\007'
				count++;
				break;

			case 12: // '\f'
			case 13: // '\r'
				if ((b[i++] & 0xc0) != 128)
					throw new IllegalArgumentException();
				count++;
				break;

			case 14: // '\016'
				if ((b[i++] & 0xc0) != 128 || (b[i++] & 0xc0) != 128)
					throw new IllegalArgumentException();
				count++;
				break;

			case 8: // '\b'
			case 9: // '\t'
			case 10: // '\n'
			case 11: // '\013'
			default:
				throw new IllegalArgumentException();
			}
		}

		if (i != max)
			throw new IllegalArgumentException();
		char cs[] = new char[count];
		i = 0;
		while (off < max) 
		{
			int c = b[off++] & 0xff;
			switch (c >> 4)
			{
			case 0: // '\0'
			case 1: // '\001'
			case 2: // '\002'
			case 3: // '\003'
			case 4: // '\004'
			case 5: // '\005'
			case 6: // '\006'
			case 7: // '\007'
				cs[i++] = (char)c;
				break;

			case 12: // '\f'
			case 13: // '\r'
				cs[i++] = (char)((c & 0x1f) << 6 | b[off++] & 0x3f);
				break;

			case 14: // '\016'
				int t = (b[off++] & 0x3f) << 6;
				cs[i++] = (char)((c & 0xf) << 12 | t | b[off++] & 0x3f);
				break;

			case 8: // '\b'
			case 9: // '\t'
			case 10: // '\n'
			case 11: // '\013'
			default:
				throw new IllegalArgumentException();
			}
		}
		return new String(cs, 0, count);
	}

	public static String byteToString(byte _bytes[], char _delim, int _radix)
	{
		String sRet = "";
		for (int i = 0; i < _bytes.length; i++)
		{
			if (i > 0)
				sRet = (new StringBuilder(String.valueOf(sRet))).append(_delim).toString();
			sRet = (new StringBuilder(String.valueOf(sRet))).append(Integer.toString(_bytes[i], _radix)).toString();
		}

		return sRet;
	}

	public static String filterForXML(String _sContent)
	{
		if (_sContent == null)
			return "";
		char srcBuff[] = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";
		StringBuffer retBuff = new StringBuffer((int)((double)nLen * 1.8D));
		for (int i = 0; i < nLen; i++)
		{
			char cTemp = srcBuff[i];
			switch (cTemp)
			{
			case 38: // '&'
				retBuff.append("&amp;");
				break;

			case 60: // '<'
				retBuff.append("&lt;");
				break;

			case 62: // '>'
				retBuff.append("&gt;");
				break;

			case 34: // '"'
				retBuff.append("&quot;");
				break;

			case 39: // '\''
				retBuff.append("&apos;");
				break;

			default:
				retBuff.append(cTemp);
				break;
			}
		}

		return retBuff.toString();
	}

	public static String filterForHTMLValue(String _sContent)
	{
		if (_sContent == null)
			return "";
		char srcBuff[] = _sContent.toCharArray();
		int nLen = srcBuff.length;
		if (nLen == 0)
			return "";
		StringBuffer retBuff = new StringBuffer((int)((double)nLen * 1.8D));
		for (int i = 0; i < nLen; i++)
		{
			char cTemp = srcBuff[i];
			switch (cTemp)
			{
			case 38: // '&'
				if (i + 1 < nLen)
				{
					cTemp = srcBuff[i + 1];
					if (cTemp == '#')
						retBuff.append("&");
					else
						retBuff.append("&amp;");
				} else
				{
					retBuff.append("&amp;");
				}
				break;

			case 60: // '<'
				retBuff.append("&lt;");
				break;

			case 62: // '>'
				retBuff.append("&gt;");
				break;

			case 34: // '"'
				retBuff.append("&quot;");
				break;

			default:
				retBuff.append(cTemp);
				break;
			}
		}

		return retBuff.toString();
	}

	public static final boolean isChineseChar(int c)
	{
		return c > 127;
	}

	public static int getBytesLength(String _string)
	{
		if (_string == null)
			return 0;
		char srcBuff[] = _string.toCharArray();
		int nGet = 0;
		for (int i = 0; i < srcBuff.length; i++)
		{
			char aChar = srcBuff[i];
			nGet += aChar <= '\177' ? 1 : 2;
		}

		return nGet;
	}

	public static String URLEncode(String s)
	{
		try {
			return URLEncoder.encode(s, GET_ENCODING_DEFAULT);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return s;
	}

	public boolean isChinese(char c)
	{
		return Character.UnicodeBlock.of(c).equals(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
	}

	public static char byteToChar(byte b[])
	{
		int s = 0;
		if (b[0] > 0)
			s += b[0];
		else
			s += 256 + b[0];
		s *= 256;
		if (b[1] > 0)
			s += b[1];
		else
			s += 256 + b[1];
		char ch = (char)s;
		return ch;
	}



	



	public static String emptyStr(String strIn, String defaultString)
	{
		if (strIn == null && "".equals(strIn))
			return defaultString;
		else
			return strIn;
	}

	public static String cutString(String title, int num)
	{
		if (title == null)
			return "";
		if (title.length() > num)
			return (new StringBuilder(String.valueOf(title.substring(0, num)))).append("...").toString();
		else
			return title;
	}

	public static String filterHtml(String strIn)
	{
		if (strIn == null || strIn.trim().length() == 0)
		{
			return strIn;
		} else
		{
			String regEx_html = "</?[^<]*>";
			return strIn.replaceAll(regEx_html, "");
		}
	}

	public static String toJsString(String strIn)
	{
		if (strIn == null)
			return "";
		if ("".equals(strIn.trim()))
		{
			return "";
		} else
		{
			strIn = strIn.replaceAll("\\\\", "\\\\\\\\");
			strIn = strIn.replaceAll("\"", "\\\\\"");
			strIn = strIn.replaceAll("\t", "\\\\t");
			strIn = strIn.replaceAll("\n", "\\\\n");
			strIn = strIn.replaceAll("\f", "\\\\f");
			strIn = strIn.replaceAll("\r", "\\\\r");
			strIn = strIn.replaceAll("\b", "\\\\b");
			return strIn;
		}
	}




	
	public static void main(String args[])
	{
		System.out.println(filterHtml("<b>feww<be></b>dsf<be></be>"));
	}
}
