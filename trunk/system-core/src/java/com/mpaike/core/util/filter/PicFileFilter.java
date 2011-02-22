package com.mpaike.core.util.filter;

/**
 * <p>Copyright: Copyright (c) 2002-2003</p>
 * <p>Company: myniko(http://www.myniko.org)</p>
 * <p>最后更新日期:2003年2月12日
 * @author niko
 */

import java.io.File;

/**
 * 图片文件类型过滤器。
 * @since  0.1
 */
public class PicFileFilter
    extends javax.swing.filechooser.FileFilter {
  /**
   * 判断指定的文件是否可以被接受。
   * @param file 需要判断的文件
   * @return 文件的扩展名为jpg,jpeg,bmp,png,gif，否则返回false。
   * @since  0.1
   */
  public boolean accept(File file) {
    if (file.getName().toLowerCase().endsWith(".jpg") ||
    	file.getName().toLowerCase().endsWith(".jpeg") ||
    	file.getName().toLowerCase().endsWith(".bmp") ||
    	file.getName().toLowerCase().endsWith(".png") ||
        file.getName().toLowerCase().endsWith(".gif")) {
      return true;
    }
    else {
      return false;
    }
  }

  public static boolean acceptFile(File file) {
	    if (file.getName().toLowerCase().endsWith(".jpg") ||
	    	file.getName().toLowerCase().endsWith(".jpeg") ||
	    	file.getName().toLowerCase().endsWith(".bmp") ||
	    	file.getName().toLowerCase().endsWith(".png") ||
	        file.getName().toLowerCase().endsWith(".gif")) {
	      return true;
	    }
	    else {
	      return false;
	    }
 }
  
  public static boolean acceptFile(String fileName) {
	    if (fileName.toLowerCase().endsWith(".jpg") ||
	    		fileName.toLowerCase().endsWith(".jpeg") ||
	    		fileName.toLowerCase().endsWith(".bmp") ||
	    		fileName.toLowerCase().endsWith(".png") ||
	    		fileName.toLowerCase().endsWith(".gif")) {
	      return true;
	    }
	    else {
	      return false;
	    }
}
  
  /**
   * 返回过滤器的描述字符串。
   * @return 过滤器的描述字符串“*.jpg,*.jpeg,*.bmp,*.png,*.gif”
   * @since  0.1
   */
  public String getDescription() {
    return "*.jpg,*.jpeg,*.bmp,*.png,*.gif";
  }
}