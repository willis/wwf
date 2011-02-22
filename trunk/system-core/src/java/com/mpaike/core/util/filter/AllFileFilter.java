package com.mpaike.core.util.filter;

/**
 * <p>Copyright: Copyright (c) 2002-2003</p>
 * <p>Company: myniko(http://www.myniko.org)</p>
 * <p>最后更新日期:2003年2月12日
 * @author niko
 */

import java.io.File;

/**
 * 全部文件过滤器。
 * @since  0.1
 */

public class AllFileFilter
    extends javax.swing.filechooser.FileFilter {
  /**
   * 判断指定的文件是否可以被接受。
   * @param file 需要判断的文件
   * @return 在任何情况都返回true。
   * @since  0.1
   */
  public boolean accept(File file) {
    return true;
  }

  /**
   * 返回过滤器的描述字符串。
   * @return 过滤器的描述字符串“All Files”
   * @since  0.1
   */
  public String getDescription() {
    return "All Files";
  }
}