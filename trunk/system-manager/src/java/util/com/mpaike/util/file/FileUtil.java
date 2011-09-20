package com.mpaike.util.file;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FileUtil
{
  public static boolean copyFile(File srcFile, File targetFile)
  {
    BufferedInputStream bin = null;
    BufferedOutputStream bout = null;
    boolean isCopied = false;
    if (srcFile.exists())
      try {
        bin = new BufferedInputStream(new FileInputStream(srcFile));
        byte[] bf = new byte[1048576];
        bout = new BufferedOutputStream(
          new FileOutputStream(targetFile));
        int len = -1;
        while ((len = bin.read(bf)) != -1) {
          bout.write(bf, 0, len);
        }
        isCopied = true;
      } catch (FileNotFoundException fne) {
        System.out
          .println("原文件[" + srcFile.getName() + "]找不到。。。无法复制文件");
        fne.printStackTrace();
      } catch (IOException ioe) {
        System.out.println("原文件拷贝出错了...");
        ioe.printStackTrace();
      } finally {
        try {
          if (bin != null)
            bin.close();
          if (bout != null)
            bout.close();
        }
        catch (IOException localIOException3)
        {
        }
      }
    return isCopied;
  }

  public static void createFile(InputStream in, OutputStream out)
    throws IOException
  {
    try
    {
      byte[] bf = new byte[1048576];
      int len = -1;
      while ((len = in.read(bf)) != -1)
        out.write(bf, 0, len);
    }
    catch (IOException e) {
      throw e;
    } finally {
      try {
        if (in != null)
          in.close();
        if (out != null)
          out.close();
      } catch (IOException e) {
        throw e;
      }
    }
  }

  public static String getFileSizeStr(long fileSize)
  {
    NumberFormat formater = DecimalFormat.getInstance();
    formater.setMaximumFractionDigits(2);
    formater.setMinimumFractionDigits(2);
    String fileSizeStr;
    if (fileSize < 1048576L)
      fileSizeStr = String.valueOf(formater.format(fileSize / 1024L)) + 
        "KB";
    else {
      fileSizeStr = 
        String.valueOf(formater
        .format(fileSize / 
        1048576L)) + 
        "MB";
    }
    return fileSizeStr;
  }

  public static void createFile(String filePath, String content, String charsetName)
    throws UnsupportedEncodingException, IOException
  {
    OutputStreamWriter out = null;
    try {
      out = new OutputStreamWriter(
        new FileOutputStream(filePath), charsetName);
    } catch (IOException e) {
      throw e;
    } finally {
      if (out != null)
        out.close();
    }
  }

  public static void zipFile()
  {
  }
}