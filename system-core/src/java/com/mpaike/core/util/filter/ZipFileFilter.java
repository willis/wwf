package com.mpaike.core.util.filter;

/**
 * <p>Copyright: Copyright (c) 2002-2003</p>
 * <p>Company: myniko(http://www.myniko.org)</p>
 * <p>最后更新日期:2003年2月12日
 * @author niko
 */

import java.io.File;

/**
 * 压缩文件类型过滤器。
 * @since  0.1
 */

public class ZipFileFilter
    extends javax.swing.filechooser.FileFilter {
  /**
   * 判断指定的文件是否可以被接受。
   * @param file 需要判断的文件
   * @return 文件的扩展名为zip、jar或者是一个目录时返回true，否则返回false。
   * @since  0.1
   */
  public boolean accept(File file) {
    if (file.getName().toLowerCase().endsWith(".zip") ||
        file.getName().toLowerCase().endsWith(".jar") || file.isDirectory()) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * 返回过滤器的描述字符串。
   * @return 过滤器的描述字符串“*.zip,*.jar”
   * @since  0.1
   */
  public String getDescription() {
    return "*.zip,*.jar";
  }
}