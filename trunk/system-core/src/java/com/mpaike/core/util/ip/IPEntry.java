package com.mpaike.core.util.ip;

/**
 * <p>Title: IP查询</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class IPEntry {

  public String beginIp;
  public String endIp;
  public String country;
  public String area;

  /**
   * 构造函数
   */
  public IPEntry() {
    beginIp = endIp = country = area = "";
  }

}
