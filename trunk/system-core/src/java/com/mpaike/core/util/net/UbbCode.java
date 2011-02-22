package com.mpaike.core.util.net;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 *  使网页支持ubbcode.
 *  @author  shanwei
 *  @version  1.0
  */ 
 
public final class UbbCode {
 
     private static final String ImagePath  =   " images/ " ;  // 定义图片路径 
     private static final String emotImagePath  =   " images/emot/ " ;
 
     /** 
     * @description 转换代码，使之支持ubbcode.
     *  @param  strContent 转换前的代码.
     *  @return  result 转换后的内容.
      */ 
     public static String UBBCode(String strContent) {  //
        strContent  =  dvHTMLEncode(strContent);
        strContent  =  FilterJS(strContent);
        String re;
         // re.IgnoreCase =true
         // re.Global=True 
        Pattern pattern  =   null ;  //
        Matcher matcher  =   null ;
        // 转换Definition 
        //re  =   " \\[d\\](.[^\\[]*)\\[\\/d\\] " ;
        re  =   "(\\[d\\])(.[^\\[]*)(\\[/d\\])";   
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <a href=\"#\" onMouseover=\"definition.show('$2')\" onMouseout=\"definition.hide('$2')\">$1</a> " );
         // 转换IMG 
        re  =   "(\\[IMG\\])(http|https|ftp):\\/\\/(.[^\\[]*)(\\[/IMG\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " < a onfocus=this.blur() href=\" $ 1 : // $2\" target=_blank><IMG SRC=\"$1:  // $2\" border=0 alt=按此在新窗口浏览图片 onload=\"javascript:if(this.width> screen.width-333)this.width=screen.width-333\"></a>");
         // 转换UPLOAD 
        re  =   "(\\[UPLOAD=(gif|jpg|jpeg|bmp|png)\\])(.[^\\[]*)(gif|jpg|jpeg|bmp|png)(\\[/UPLOAD\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <br><IMG SRC=\""  + ImagePath +  " $ 1 .gif \"  border=0>此主题相关图片如下：<br><A HREF=\" $ 2 $ 1 \"  TARGET=_blank> <IMG SRC=\" $ 2 $ 1 \"  border=0 alt=按此在新窗口浏览图片 onload=\" javascript: if  ( this .width > screen.width - 333 ) this .width = screen.width - 333 \" ></A> " );
        re  =   "(\\[UPLOAD=(.[^\\[]*)\\])(.[^\\[]*)(\\[/UPLOAD\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <br><IMG SRC=\""  + ImagePath + " $1.gif\"  border = 0 >   < a href = \" $2\" > 点击浏览该文件 </ a > " ); 
         // 转换DIR 
        re  =   "(\\[DIR=*([0-9]*),*([0-9]*)\\])(.[^\\[]*)(\\[/DIR])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <object classid=clsid:166B1BCA-3F9C-11CF-8075-444553540000 codebase=http://download.macromedia.com/pub/shockwave/cabs/director/sw.cab#version=7,0,2,0 width=$1 height=$2><param name=src value=$3><embed src=$3 pluginspage=http://www.macromedia.com/shockwave/download/ width=$1 height=$2></embed></object> " );
         // 转换QT 
        re  =   "(\\[QT=*([0-9]*),*([0-9]*)\\])(.[^\\[]*)(\\[/QT])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <embed src=$3 width=$1 height=$2 autoplay=true loop=false controller=true playeveryframe=false cache=false scale=TOFIT bgcolor=#000000 kioskmode=false targetcache=false pluginspage=http://www.apple.com/quicktime/>; " );
         // 转换MP 
        re  =   "(\\[MP=*([0-9]*),*([0-9]*)\\])(.[^\\[]*)(\\[/MP])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <object align=middle classid=CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95 class=OBJECT id=MediaPlayer width=$1 height=$2 ><param name=ShowStatusBar value=-1><param name=Filename value=$3><embed type=application/x-oleobject codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701 flename=mp src=$3  width=$1 height=$2></embed></object> " );
         // 转换RM 
        re  =   "(\\[RM=*([0-9]*),*([0-9]*)\\])(.[^\\[]*)(\\[/RM])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <OBJECT classid=clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA class=OBJECT id=RAOCX width=$1 height=$2><PARAM NAME=SRC VALUE=$3><PARAM NAME=CONSOLE VALUE=Clip1><PARAM NAME=CONTROLS VALUE=imagewindow><PARAM NAME=AUTOSTART VALUE=true></OBJECT><br><OBJECT classid=CLSID:CFCDAA03-8BE4-11CF-B84B-0020AFBBCCFA height=32 id=video2 width=$1><PARAM NAME=SRC VALUE=$3><PARAM NAME=AUTOSTART VALUE=-1><PARAM NAME=CONTROLS VALUE=controlpanel><PARAM NAME=CONSOLE VALUE=Clip1></OBJECT> " );
         // 转换FLASH 
        re  =   "(\\[FLASH\\])(.[^\\[]*)(\\[/FLASH\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll(
                 " <a href=\" $ 2 \"  TARGET=_blank><IMG SRC=\""  + ImagePath +  " swf.gif\"  border=0 alt=点击开新窗口欣赏该FLASH动画! height=16 width=16>[全屏欣赏]</a><br><OBJECT codeBase=http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0 classid=clsid:D27CDB6E-AE6D-11cf-96B8-444553540000 width=500 height=400><PARAM NAME=movie VALUE=\" $ 2 \" ><PARAM NAME=quality VALUE=high><embed src=\" $ 2 \"  quality=high pluginspage='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' type='application/x-shockwave-flash' width=500 height=400>$2</embed></OBJECT> " );
        re  =   "(\\[FLASH=*([0-9]*),*([0-9]*)\\])(.[^\\[]*)(\\[\\/FLASH\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll(
                 " <a href=\" $ 4 \"  TARGET=_blank><IMG SRC=\""  + ImagePath +  " swf.gif \"  border=0 alt=点击开新窗口欣赏该FLASH动画! height=16 width=16>[全屏欣赏]</a> <br><OBJECT codeBase=http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0 classid=clsid:D27CDB6E-AE6D-11cf-96B8-444553540000 width=$2 height=$3><PARAM NAME=movie VALUE=\" $ 4 \" ><PARAM NAME=quality VALUE=high><embed src=\" $ 4 \"  quality=high pluginspage='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' type='application/x-shockwave-flash' width=$2 height=$3>$4</embed></OBJECT> " );
         // 转换URL 
        re  =   "(\\[URL\\])(.[^\\[]*)(\\[/URL\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <A HREF=\" $ 2 \"  TARGET=_blank>$2</A> " );
        re  =   "(\\[URL=(.[^\\[]*)\\])(.[^\\[]*)(\\[/URL\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <A HREF=\" $ 2 \"  TARGET=_blank>$3</A> " );
         // 转换EMAIL 
        re  =   "(\\[EMAIL\\])(\\S+\\@.[^\\[]*)(\\[/EMAIL\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <img align=absmiddle src=\""  + ImagePath  + " email1.gif\" >< A HREF = \" mailto:$2\" > $ 2 </ A > " ); 
        re  =   "(\\[EMAIL=(\\S+\\@.[^\\[]*)\\])(.[^\\[]*)(\\[/EMAIL\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <img align=absmiddle src=\""  + ImagePath  + " email1.gif\" >< A HREF = \" mailto:$2\"  TARGET = _blank > $ 3 </ A > " ); 
 
         // 自动识别网址 
        re  = "^((http|https|ftp|rtsp|mms):(\\/\\/|\\\\)[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll(
                 " <img align=absmiddle src=pic/url.gif border=0><a target=_blank href=$1>$1</a> " );
        re  =  "((http|https|ftp|rtsp|mms):(\\/\\/|\\\\)[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)$ ";
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll(
                 " <img align=absmiddle src=pic/url.gif border=0><a target=_blank href=$1>$1</a> " );
        re  =   "([^>=\" ])((http | https | ftp | rtsp | mms):(\\ / \\ /| \\\\)[A - Za - z0 - 9 \\. /= \\ ?% \\ -& _ ~ `@ ' :+!]+)"; 
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " $1<img align=absmiddle src=pic/url.gif border=0><a target=_blank href=$2>$2</a> " );
 
         // 自动识别www等开头的网址 
        re  =   "([^(http://|http:\\\\)])((www|cn)[.](\\w)+[.]{1,}(net|com|cn|org|cc)(((\\/[\\~]*|\\[\\~]*)(\\w)+)|[.](\\w)+)*(((([?](\\w)+){1}[=]*))*((\\w)+){1}([\\&](\\w)+[\\=](\\w)+)*)*)" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <img align=absmiddle src=pic/url.gif border=0><a target=_blank href=http://$2>$2</a> " );
 
         // 自动识别Email地址，如打开本功能在浏览内容很多的帖子会引起服务器停顿 
        re  =   "([^(=)])((\\w)+[@]{1}((\\w)+[.]){1,3}(\\w)+)" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <img align=absmiddle src=pic/url.gif border=0><a target=_blank href=\" mailto:$ 2 \" >$2</a> " );
         // 转换EM 
        re  =   "\\[em(.[^\\[]*)\\]" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <img src=\""  + emotImagePath + " em$1.gif\"  border = 0  align = middle > " ); 
         // 转换HTML 
        re  =   "(\\[HTML\\])(.[^\\[]*)(\\[/HTML\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " < table width='100%' border='0' cellspacing='0' cellpadding='6' class=tableborder1> <td><b>以下内容为程序代码:</b><br>$1</td>< /table> " );
         // 转换CODE 
        re  =   "(\\[code\\])(.[^\\[]*)(\\[/code\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " < table width='100%' border='0' cellspacing='0' cellpadding='6' class=tableborder1> <td><b>以下内容为程序代码:</b><br>$1</td>< /table> " );
         // 转换COLOR 
        re  =   "(\\[color=(.[^\\[]*)\\])(.[^\\[]*)(\\[/color\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <font color=$1>$2</font> " );
         // 转换FACE 
        re  =   "(\\[face=(.[^\\[]*)\\])(.[^\\[]*)(\\[/face\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <font face=$1>$2</font> " );
         // 转换ALIGN 
        re  =   "(\\[align=(center|left|right)\\])(.*)(\\[/align\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <div align=$1>$2</div> " );
         // 转换QUOTE 
        re  =   "(\\[QUOTE\\])(.*)(\\[/QUOTE\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <table style=\" width: 80 % \"  cellpadding=5 cellspacing=1 class=tableborder1><TR><TD class=tableborder1>$1</td></tr></table><br> " );
         // 转换FLY 
        re  =   "(\\[fly\\])(.*)(\\[/fly\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll(
                 " <marquee width=90% behavior=alternate scrollamount=3>$1</marquee> " );
         // 转换MOVE 
        re  =   "(\\[move\\])(.*)(\\[/move\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <MARQUEE scrollamount=3>$1</marquee> " );
         // 转换GLOW 
        re  = "(\\[GLOW=*([0-9]*),*(#*[a-z0-9]*),*([0-9]*)\\])(.[^\\[]*)(\\[/GLOW])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll(
                 " <table width=$1 style=\" filter:glow(color = $ 2 , strength = $ 3 )\" >$4</table> " );
         // 转换SHADOW 
        re  = "(\\[SHADOW=*([0-9]*),*(#*[a-z0-9]*),*([0-9]*)\\])(.[^\\[]*)(\\[/SHADOW])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll(
                 " <table width=$1 style=\" filter:shadow(color = $ 2 , strength = $ 3 )\" >$4</table> " );
 
        re  =   "(\\[i\\])(.[^\\[]*)(\\[/i\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <i>$1</i> " );
        re  =   "(\\[u\\])(.[^\\[]*)(\\[/u\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <u>$1</u> " );
        re  =   "(\\[b\\])(.[^\\[]*)(\\[/b\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <b>$1</b> " );
        re  =   "(\\[size=([1-4])\\])(.[^\\[]*)(\\[/size\\])" ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(strContent);
        strContent  =  matcher.replaceAll( " <font size=$1>$2</font> " );
         /* re ="(\\s)";
                 matcher = pattern.matcher(strContent);  
                 strContent=matcher.replaceAll("<I>$1</I>"); */ 
        re  =   null ;
         return  strContent;
    }
 
/** 
 *  过滤javascript代码.
 *  @param  v转换前的代码.
 *  @return  v转换后的内容.
  */ 
 
     public   static  String FilterJS(String v) {
         if  ( ! v.equals( " null " )  &&  v  !=   null ) {
            String t, re;
            re  =   " (javascript) " ;
            t  =  re.replace(v,  " javascript " );
            re  =   " (jscript:) " ;
            t  =  re.replace(t,  " jscript: " );
            re  =   " (js:) " ;
            t  =  re.replace(t,  " js: " );
            re  =   " (value) " ;
            t  =  re.replace(t,  " value " );
            re  =   " (about:) " ;
            t  =  re.replace(t,  " about: " );
            re  =   " (file:) " ;
            t  =  re.replace(t,  " file: " );
            re  =   " (document.cookie) " ;
            t  =  re.replace(t,  " documents.cookie " );
            re  =   " (vbscript:) " ;
            t  =  re.replace(t,  " vbscript: " );
            re  =   " (vbs:) " ;
            t  =  re.replace(t,  " vbs: " );
            re  =   " (on(mouse|exit|error|click|key)) " ;
            t  =  re.replace(t,  " on$2 " );
            re  =   " (&#) " ;
            t  =  re.replace(t,  " ＆# " );
            re  =   null ;
        }
         return  v;
    }
 
/** 
 *  替换一些特殊符号为html语法标记.
 *  @param  fString转换前的代码.
 *  @return  fString转换后的内容.
  */  
 
   public   static  String dvHTMLEncode(String fString) {
         if  ( ! fString.equals( " null " )  &&  fString  !=   null ) {
            fString  =  replace(fString,  " > " ,  " > " );
            fString  =  replace(fString,  " < " ,  " < " );
            fString  =  replace(fString,  " & " ,  " & " );
            fString  =  replace(fString,  "   " ,  "   " );
            fString  =  replace(fString,  " \\\"" ,  " \" " );
            fString  =  replace(fString,  " \' " ,  " ' " );
            fString  =  replace(fString,  " \r " ,  "" );
            fString  =  replace(fString,  " \n " ,  " <BR>  " );
            fString  =  replace(fString,  " \\\\ " ,  " \\ " );
        }
         return  fString;
    }
 
/** 
 *  html语法标记转换符号本身.
 *  @param  fString转换前的代码.
 *  @return  fString转换后的内容.
  */   
 
   public   static  String dvHTMLCode(String fString) {
         if  ( ! fString.equals( " null " )  &&  fString  !=   null ) {
            fString  =  replace(fString,  " > " ,  " > " );
            fString  =  replace(fString,  " < " ,  " < " );
            fString  =  replace(fString,  " & " ,  " & " );
            fString  =  replace(fString,  "   " ,  "   " );
            fString  =  replace(fString,  "\"" ,  " \\\"" );
            fString  =  replace(fString,  " ' " ,  " \' " );
            fString  =  replace(fString,  " \\ " ,  " \\\\ " );
            fString  =  replace(fString,  " <BR> " ,  " \n " );
        }
         return  fString;
    }
 
     public   static  String nohtml(String str) {
        String re;
        Pattern pattern  =   null ;
        Matcher matcher  =   null ;
        re  =   " (\\<.[^\\<]*\\>) " ;
        pattern  =  Pattern.compile(re);
        matcher  =  pattern.matcher(str);
        str  =  matcher.replaceAll( "   " );
        re  =   " (\\<\\/[^\\<]*\\>) " ;
        str  =  re.replace(str,  "   " );
        re  =   null ;
         return  str;
    }
 
/** 
 *  分割函数.
 *  @param  source原字符串,div将要被分割的字符串.
 *  @return  returnStr分割后的字符串.
  */   
 
    public   static  String[] split(String source, String div) {
         int  arynum  =   0 , intIdx  =   0 , intIdex  =   0 , div_length  =  div.length();
         if  (source.compareTo( "" )  !=   0 ) {
             if  (source.indexOf(div)  !=   - 1 ) {
                intIdx  =  source.indexOf(div);
                 for  ( int  intCount  =   1 ; ; intCount ++ ) {
                     if  (source.indexOf(div, intIdx  +  div_length)  !=   - 1 ) {
                        intIdx  =  source.indexOf(div, intIdx  +  div_length);
                        arynum  =  intCount;
                    }  else  {
                        arynum  +=   2 ;
                         break ;
                    }
                }
            }  else  {
                arynum  =   1 ;
            }
        }  else  {
            arynum  =   0 ;
 
        }
        intIdx  =   0 ;
        intIdex  =   0 ;
        String[] returnStr  =   new  String[arynum];
 
   if  (source.compareTo( "" )  !=   0 ) {
             if  (source.indexOf(div)  !=   - 1 ) {
                intIdx  =  ( int ) source.indexOf(div);
                returnStr[ 0 ]  =  (String) source.substring( 0 , intIdx);
                 for  ( int  intCount  =   1 ; ; intCount ++ ) {
                     if  (source.indexOf(div, intIdx  +  div_length)  !=   - 1 ) {
                        intIdex  =  ( int ) source.indexOf(div, intIdx  +  div_length);
                        returnStr[intCount]  =  (String) source.substring(intIdx  + 
                                div_length,
                                intIdex);
                        intIdx  =  ( int ) source.indexOf(div, intIdx  +  div_length);
                    }  else  {
                        returnStr[intCount]  =  (String) source.substring(intIdx  + 
                                div_length,
                                source.length());
                         break ;
                    }
                }
            }  else  {
                returnStr[ 0 ]  =  (String) source.substring( 0 , source.length());
                 return  returnStr;
            }
        }  else  {
             return  returnStr;
        }
         return  returnStr;
    }
 
/** 
 *  替换函数.
 *  @param  str替换前的字符串,substr被替换的字符,restr替换的字符.
 *  @return  替换后并且经过除空处理的字符串.
  */   
     public   static  String replace(String str, String substr, String restr) {
        String[] tmp  =  split(str, substr);
        String returnstr  =   null ;
         if  (tmp.length  !=   0 ) {
            returnstr  =  tmp[ 0 ];
             for  ( int  i  =   0 ; i  <  tmp.length  -   1 ; i ++ ) {
                returnstr  =  dealNull(returnstr)  +  restr  +  tmp[i  +   1 ];
            }
        }
         return  dealNull(returnstr);
    }
 
/** 
 *  除空处理函数.
 *  @param  str原字符串.
 *  @return  处理后的字符串.
  */   
     public   static  String dealNull(String str) {
        String returnstr  =   null ;
         if  (str  ==   null ) {
            returnstr  =   "" ;
        }  else  {
            returnstr  =  str;
        }
         return  returnstr;
    }
 
     public   static  String tostring(Character i) {
        Character c  =  i;
         return  c.toString();
    }
 
}
