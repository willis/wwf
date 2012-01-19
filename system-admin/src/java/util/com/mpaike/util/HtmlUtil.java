package com.mpaike.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * <p>Title: <font color=red>格式化文本</font></p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * @author <font color=red>niko</font>
 * @version 1.0
 */

public class HtmlUtil {

	public HtmlUtil() {
	}

	/**
	 * <font color=red>分割字串</font>
	 * @param source <font color=red>原始字符</font>
	 * @param div <font color=red>分割符</font>
	 * @return <font color=red>字符串数组</font>
	 */
	public static String[] split(String source, String div) {
		int arynum = 0, intIdx = 0, intIdex = 0, div_length = div.length();
		if (source.compareTo("") != 0) {
			if (source.indexOf(div) != -1) {
				intIdx = source.indexOf(div);
				for (int intCount = 1;; intCount++) {
					if (source.indexOf(div, intIdx + div_length) != -1) {
						intIdx = source.indexOf(div, intIdx + div_length);
						arynum = intCount;
					} else {
						arynum += 2;
						break;
					}
				}
			} else
				arynum = 1;
		} else
			arynum = 0;

		intIdx = 0;
		intIdex = 0;
		String[] returnStr = new String[arynum];

		if (source.compareTo("") != 0) {
			if (source.indexOf(div) != -1) {
				intIdx = (int) source.indexOf(div);
				returnStr[0] = (String) source.substring(0, intIdx);
				for (int intCount = 1;; intCount++) {
					if (source.indexOf(div, intIdx + div_length) != -1) {
						intIdex = (int) source
								.indexOf(div, intIdx + div_length);
						returnStr[intCount] = (String) source.substring(intIdx
								+ div_length, intIdex);
						intIdx = (int) source.indexOf(div, intIdx + div_length);
					} else {
						returnStr[intCount] = (String) source.substring(intIdx
								+ div_length, source.length());
						break;
					}
				}
			} else {
				returnStr[0] = (String) source.substring(0, source.length());
				return returnStr;
			}
		} else {
			return returnStr;
		}
		return returnStr;
	}

	public static String dealNull(String str) {
		String returnstr = null;
		if (str == null)
			returnstr = "";
		else
			returnstr = str;
		return returnstr;
	}

	public static Object dealNull(Object obj) {
		Object returnstr = null;
		if (obj == null)
			returnstr = (Object) ("");
		else
			returnstr = obj;
		return returnstr;
	}

	public static int dealEmpty(String s) {
		s = dealNull(s);
		if (s.equals(""))
			return 0;
		return Integer.parseInt(s);
	}

	/**
	 * <font color=red>字符串替换函数</font>
	 * @param str <font color=red>原始字符串</font>
	 * @param substr <font color=red>要替换的字符</font>
	 * @param restr <font color=red>替换后的字符</font>
	 * @return <font color=red>替换完成的字符串</font>
	 */
	public static String replace(String str, String substr, String restr) {
		String[] tmp = split(str, substr);
		String returnstr = null;
		if (tmp.length != 0) {
			returnstr = tmp[0];
			for (int i = 0; i < tmp.length - 1; i++)
				returnstr = dealNull(returnstr) + restr + tmp[i + 1];
		}
		return dealNull(returnstr);
	}

	/**
	 * <font color=red>替换SQL语句的危险字符串</font>
	 * @param str <font color=red>原始文本</font>
	 * @return <font color=red>替换之后的文本</font>
	 */
	public static String prepareSql(String str) {
		if (str != null) {
			str = replace(str, "'", "'||chr(39)||'").trim();
			str = replace(str, "&", "'||'&'||'").trim();
		} else {
			str = "";
		}
		return str;
	}
	
	/**
	 * <font color=red>替换HSQL语句的危险字符串</font>
	 * @param str <font color=red>原始文本</font>
	 * @return <font color=red>替换之后的文本</font>
	 */
	public static String prepareHsql(String str) {
		if (str != null) {
			str = replace(str, "/", "//").trim();
			str = replace(str, "'", "/'").trim();
			str = replace(str, "%", "/%").trim();
			str = replace(str, "_", "/_").trim();
		} else {
			str = "";
		}
		return str;
	}

	/**
	 * <font color=red>将回车符替换成Html中的换行符</font>
	 * @param txt <font color=red>原始文本</font>
	 * @return <font color=red>替换之后的文本</font>
	 */
	public static String addBr(String txt) {
		if (txt != null) {
			txt = replace(txt, "\n\r", "<br>");
			txt = replace(txt, "\n", "<br>");
		}
		return txt;
	}

	public static String addSpace(String txt) {
		if (txt != null) {
			txt = replace(txt, "\n","<br>");
			txt = replace(txt, "\r","&#10;");
		}
		return txt;
	}
	
	public static String changeBr(String txt) {
		if (txt != null) {
			txt = replace(txt, "<br>", "\n");
		}
		return txt;
	}

	public static String addColon(String txt) {
		if (txt != null) {
			txt = replace(txt, "<br>", ":<br>");
		}
		return txt;
	}

	public static String changeColor(String txt) {
		if (txt != null) {
			txt = replace(txt, "<br>", "</font><br>");
			txt = replace(txt, "<br>:", "<br><font color=#408080>:");
		}
		return txt;
	}

	public static String formBr(String txt) {
		if (txt != null) {
			txt = replace(txt, "\n", "<br>");
			txt = replace(txt, "&nbsp;", " ");
		}
		return txt;
	}

	/**
	 * <font color=red>将Html中的换行符去掉</font>
	 * @param txt <font color=red>原始文本</font>
	 * @return <font color=red>替换之后的文本</font>
	 */
	public static String delBr(String txt) {
		if (txt != null) {
			txt = replace(txt, "<br>", "");
		}
		return txt;
	}

	/**
	 * <font color=red>为'和\增加转移符，以便加入数据库，'替换为\'，\替换为\\</font>
	 * @param txt <font color=red>原始文本</font>
	 * @return <font color=red>增加转移符后的文本</font>
	 */
	public static String addSlashes(String txt) {
		if (txt != null) {
			txt = replace(txt, "\\", "\\\\");
			txt = replace(txt, "\'", "\\\'");
			txt = replace(txt, "\"", "\\\"");
		}
		return txt;
	}

	/**
	 * <font color=red>取消转移符</font>
	 * @param txt <font color=red>原始文本</font>
	 * @return <font color=red>取消转移符后的文本</font>
	 */
	public static String stripslashes(String txt) {
		if (txt != null) {
			txt = replace(txt, "\\\\", "\\");
			txt = replace(txt, "\'", "'");
			txt = replace(txt, "\\\"", "\"");
			txt = replace(txt, "\\&quot;", "\"");
		}
		return txt;
	}

	/**
	 * <font color=red>取消Html标记</font>
	 * @param txt <font color=red>原始文本</font>
	 * @return <font color=red>取消Html标记后的文本</font>
	 */
	public static String htmlEncode(String txt) {
		if (txt != null) {
			txt = replace(txt.trim(), "&", "&amp;");
			txt = replace(txt.trim(), "&amp;amp;", "&amp;");
			txt = replace(txt.trim(), "&amp;quot;", "&quot;");
			txt = replace(txt.trim(), "\"", "&quot;");
			txt = replace(txt.trim(), "&amp;lt;", "&lt;");
			txt = replace(txt.trim(), "<", "&lt;");
			txt = replace(txt.trim(), "&amp;gt;", "&gt;");
			txt = replace(txt.trim(), ">", "&gt;");
			txt = replace(txt.trim(), "&amp;nbsp;", "&nbsp;");
			txt = replace(txt.trim(), " ", "&nbsp;");
			txt = replace(txt.trim(), "&amp;#8217;", "&#8217;");
			txt = replace(txt.trim(), "'", "&#8217;");
		} else if (txt == null) {
			txt = "";
		}
		return txt.trim();
	}
	
	/**
	 * <font color=red>替换Html标记为空</font>
	 * @param txt <font color=red>原始文本</font>
	 * @return <font color=red>取消Html标记后的文本</font>
	 */
	public static String HtmlEncodeNo(String txt) {
		if (txt != null) {
			txt = replace(txt, "&amp;", "");
			txt = replace(txt, "&quot;", "");
			txt = replace(txt, "&lt;", "");
			txt = replace(txt, "&gt;", "");
			txt = replace(txt, "&nbsp;", "");
			
			txt = replace(txt, "&cent;", "");
			txt = replace(txt, "&pound;", "");
			txt = replace(txt, "&yen;", "");
			txt = replace(txt, "&sect;", "");
			txt = replace(txt, "&copy;", "");
			
			txt = replace(txt, "&reg;", "");
			txt = replace(txt, "&times;", "");
			txt = replace(txt, "&divide;", "");
		}
		return txt;
	}
	/**
	 * <font color=red>返回Html标记</font>
	 * @param txt <font color=red>原始文本</font>
	 * @return <font color=red>返回Html后的文本</font>
	 */
	public static String unHtmlEncode(String txt) {
		if (txt != null) {
			txt = replace(txt, "&amp;", "&");
			txt = replace(txt, "&quot;", "\"");
			txt = replace(txt, "&lt;", "<");
			txt = replace(txt, "&gt;", ">");
			txt = replace(txt, "&nbsp;", " ");
		}
		return txt;
	}

	/**
	 * <font color=red>去除Html中脚本标记</font>
	 * @param txt <font color=red>原始文本</font>
	 * @return <font color=red>去除脚本后的文本</font>
	 */
	public static String ScriptEncode(String txt) {
		if (txt != null) {
			txt = replace(txt, "script", " ");
			txt = replace(txt, "SCRIPT", " ");
			txt = replace(txt, "Script", " ");
			txt = replace(txt, "SCript", " ");
		}
		return txt;
	}

	/**
	 * <font color=red>生成一个三位的随机数</font>
	 * @return <font color=red>返回一个字符串</font>
	 */
	public static String getRandom() {
		Date now = new Date();
		java.security.SecureRandom random = new java.security.SecureRandom();
		int ra = random.nextInt(999);
		if (ra < 100) {
			ra = random.nextInt(999);
		}
		String randomend = String.valueOf(now.getTime());
		randomend = randomend.concat(String.valueOf(ra));
		return randomend;
	}

	/**
	 * <font color=red>生成一个三位的随机数</font>
	 * @param i <font color=red>三位随机数的最后一位</font>
	 * @return <font color=red>返回一个字符串</font>
	 */
	public static String getRandom(int i) {
		Date now = new Date();
		java.security.SecureRandom random = new java.security.SecureRandom();
		String randomend = String.valueOf(now.getTime());
		int ra = random.nextInt(99);
		if (ra < 10) {
			ra = random.nextInt(99);
		}
		randomend = randomend.concat(String.valueOf(ra)).concat(
				String.valueOf(i));
		return randomend;
	}
	
	public static String encode(String html) {
		try {
			return java.net.URLEncoder.encode(html,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return html;
		}
	}
	public static String decode(String html) {
		try {
			return java.net.URLDecoder.decode(html,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return html;
		}
		
	}
	
	/**
	 * <font color=red>取消Html标记</font>
	 * @param txt <font color=red>原始文本</font>
	 * @return <font color=red>取消Html标记后的文本</font>
	 */
	public static String htmlcode(String txt) {
		if (txt != null) {
			txt = replace(txt.trim(), "&", "&amp;");
			txt = replace(txt.trim(), "&amp;amp;", "&amp;");
			txt = replace(txt.trim(), "&amp;quot;", "&quot;");
			txt = replace(txt.trim(), "\"", "&quot;");
			txt = replace(txt.trim(), "&amp;lt;", "&lt;");
			txt = replace(txt.trim(), "<", "&lt;");
			txt = replace(txt.trim(), "&amp;gt;", "&gt;");
			txt = replace(txt.trim(), ">", "&gt;");
			txt = replace(txt.trim(), "&amp;nbsp;", "&nbsp;");
		    // txt = replace(txt.trim(), " ", "&nbsp;");
			txt = replace(txt.trim(), "&amp;#8217;", "&#8217;");
			txt = replace(txt.trim(), "'", "&#8217;");
			txt = replace(txt.trim(), "\r\n", "&lt;br/&gt;");
		} else if (txt == null) {
			txt = "";
		}
		return txt.trim();
	}
	
	public static String patternHtml(String html,String patternString){
        Pattern pattern  =   null ;  //
        Matcher matcher  =   null ;
        pattern = Pattern.compile(patternString);
        matcher  =  pattern.matcher(html);
        try{
	        matcher.find();
	        if(matcher.groupCount()==1){
	        	return matcher.group(1);
	        }
        }catch(Exception ex){
        	
        }
        return "";
	}
	
	/**
	 * 消除字符串为Pattern,是一个正则表达式变为一个非正则表达式
	 * 
	 * @param source 正则表达式字符串
	 * @return 转换后的字符串
	 */
	public static String escapePattern(String source) {
		if (source == null) {
			return null;
		}
		StringBuilder result = new StringBuilder(source.length() * 2);
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
	
	public static String clearHtml(String text){
		if(text!=null){
			text = text.replaceAll("<[^>]*>", "");
		}
		return text;
	}
	
	public static String clearJavaScript(String text){
		if(text!=null){
			text = text.replaceAll("<script[^>]*?>.*?</script>", "");
		}
		return text;
	}
	
	public static void main(String[] argv){
		String a = "&quot;&quot;&quot;&gt;12324";
		System.out.print(HtmlEncodeNo(a));
		//System.out.print(clearHtml(a));
	}

}