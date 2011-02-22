package com.mpaike.core.util;

/**
 * 此类中收集Java编程中常用的常量。
 * 不局限于某个包中，当然不包括Math类中的那些常量。
 * 为避免生成此类的实例，构造方法被申明为private类型的。
 * @since  0.1
 */

public class Constants {
	/**
	 * 换行符。
	 * @since  0.1
	 */
	public static String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * 文件分隔符。
	 * @since  0.1
	 */
	public static String FILE_SEPARATOR = System.getProperty("file.separator");

	/**
	 * 路径分隔符。
	 * @since  0.1
	 */
	public static final String PATH_SEPARATOR = System.getProperty("path.separator");
	public static final String TYPE_FACTORY= "f";
	public static final String TYPE_VENDOR= "v";
	
	public static final Integer VF_SNM = 4;
	public static final Integer VF_PM = 3;
	public static final Integer VF_MERCHANT = 2;
	public static final Integer VF_VENDOR = 1;

	public static final Integer STATUS_APPROVE_BY_USIS = 6;
	public static final Integer STATUS_APPROVE_BY_PM = 5;
	public static final Integer STATUS_APPROVE_BY_MERCHANT = 4;
	public static final Integer STATUS_NEW = 3;
	public static final Integer STATUS_DISABLE = 2;
	public static final Integer STATUS_ENABLE = 1;

	
	public static final Integer STATUS_TODO = 1;
	public static final Integer STATUS_PENDING = 0;
	
	/**
	 * 私有构造方法，防止类的实例化，因为工具类不需要实例化。
	 */
	private Constants() {
	}

	
}