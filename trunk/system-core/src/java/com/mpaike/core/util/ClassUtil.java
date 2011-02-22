package com.mpaike.core.util;

import java.io.File;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ClassUtil
 * 
 * @author catty
 * @version 1.0, Created on 2008/3/14
 */
public class ClassUtil {

	private static Log logger = LogFactory.getLog(ClassUtil.class);

	public static Class primitiveTypes[] = new Class[] { boolean.class, byte.class, char.class, short.class, int.class,
			float.class, long.class, double.class, Boolean.class, Byte.class, Character.class, Short.class,
			Integer.class, Float.class, Long.class, Double.class, String.class, Number.class, BigDecimal.class,
			BigInteger.class };

	public static String getSimpleName(Class clazz) {
		String ary[] = clazz.getName().split("\\.");
		String name = ary[ary.length - 1];
		return name;
	}

	/**
	 * get Classes Dir
	 * 
	 * @return
	 */
	public static File getClassesDir() throws Exception {
		return getClassesDir(ClassUtil.class);
	}
	
	public static File getClassesDir(Class clazz) throws Exception {
		// logger.info("clazz = " + clazz);
		// logger.info("/ = " + clazz.getResource("/"));
		// logger.info(". = " + clazz.getResource("."));
		URL url = clazz.getResource("/");
		// logger.info("url = " + url);
		URI uri = url.toURI();
		// logger.info("uri = " + uri);

		//String package_path = clazz.getPackage().getName().replace('.', '/');
//		String curr_path = new File(uri).getAbsolutePath().replace('\\', '/');
//		int idx = curr_path.indexOf(package_path);
//		String root_path = curr_path.substring(0, idx);
		return new File(uri);
	}

//	/**
//	 * translate Value from originalType to destType
//	 * 
//	 * @param value
//	 * @param toType
//	 * @return
//	 */
//	public static Object translate(Object value, Class toType) throws Exception {
//		if (value.getClass().equals(toType)) {
//			return value;
//		} else {
//			logger.debug("[translate] value = " + value + ", from " + value.getClass().getName() + " to "
//					+ toType.getClass().getName());
//			String ss = value.toString();
//			// to Date
//			if (toType.equals(Date.class)) {
//				if (value instanceof Time || value instanceof Timestamp || value instanceof java.sql.Date) {
//					return (Date) value;
//				} else {
//					return DateUtil.parseToDate(ss);
//				}
//			} else if (toType.equals(Short.class)) {
//				return Short.parseShort(ss);
//			} else if (toType.equals(Integer.class)) {
//				return NumberUtils.toInt(ss);
//			} else if (toType.equals(Float.class)) {
//				return NumberUtils.toFloat(ss);
//			} else if (toType.equals(Long.class)) {
//				return NumberUtils.toLong(ss);
//			} else if (toType.equals(Double.class)) {
//				return NumberUtils.toDouble(ss);
//			} else if (toType.equals(Boolean.class)) {
//				if (value == null)
//					return Boolean.FALSE;
//				else if (ss.equalsIgnoreCase("true") || ss.equalsIgnoreCase("y") || ss.equalsIgnoreCase("on")
//						|| ss.equalsIgnoreCase("1"))
//					return Boolean.TRUE;
//				else
//					return Boolean.FALSE;
//			} else {
//				// TODO: should add other type
//				throw new Exception("[translateValue] not supoort this type(" + toType.getName() + ") yet...");
//			}
//		}
//	}

	public static boolean isMap(Object obj) {
		if (obj instanceof Map) {
			return true;
		} else {
			Class objClass = obj instanceof Class ? (Class) obj : obj.getClass();
			return isInterfaceOf(objClass, Map.class);
		}
	}

	public static boolean isDate(Object obj) {
		if (obj instanceof Date || obj instanceof java.sql.Date || obj instanceof Timestamp || obj instanceof Time) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPrimitiveType(Object obj) {
		return ArrayUtils.contains(primitiveTypes, obj.getClass());
	}

	public static boolean isString(Object obj) {
		if (obj instanceof String) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check mainClass is interfaceOf some interface
	 * 
	 * @param mainClass
	 * @param interfaceClazz
	 * @return
	 */
	public static boolean isInterfaceOf(Class mainClass, Class interfaceClazz) {
		if (mainClass.equals(interfaceClazz))
			return true;
		// check my interface
		Class interfaces[] = mainClass.getInterfaces();
		for (Class itf : interfaces) {
			if (isInterfaceOf(itf, interfaceClazz))
				return true;
		}
		// check my parent interface
		Class superclass = mainClass.getSuperclass();
		if (superclass != null)
			return isInterfaceOf(superclass, interfaceClazz);
		else
			return false;
	}

	public static List<Class> getInterfaces(Class mainClass) {
		List<Class> list = _getInterfaces(mainClass, new ArrayList<Class>());
		return list;
	}

	protected static List<Class> _getInterfaces(Class mainClass, List<Class> list) {
		Class interfaces[] = mainClass.getInterfaces();
		for (Class itf : interfaces) {
			if (!list.contains(itf))
				list.add(itf);
		}
		Class superclass = mainClass.getSuperclass();
		if (superclass != null) {
			return _getInterfaces(superclass, list);
		} else {
			return list;
		}

	}

	/**
	 * get Modifier Text
	 * 
	 * @param modifierId
	 * @return
	 */
	public static String getModifierText(int modifierId) {
		return Modifier.toString(modifierId);
	}

	/**
	 * isPublic
	 * 
	 * @param modifierId
	 * @return
	 */
	public static boolean isPublic(int modifierId) {
		return Modifier.isPublic(modifierId);
	}

	/**
	 * isProtected
	 * 
	 * @param modifierId
	 * @return
	 */
	public static boolean isProtected(int modifierId) {
		return Modifier.isProtected(modifierId);
	}

	/**
	 * isPrivate
	 * 
	 * @param modifierId
	 * @return
	 */
	public static boolean isPrivate(int modifierId) {
		return Modifier.isPrivate(modifierId);
	}

	/**
	 * isStatic
	 * 
	 * @param modifierId
	 * @return
	 */
	public static boolean isStatic(int modifierId) {
		return Modifier.isStatic(modifierId);
	}

	/**
	 * isAbstract
	 * 
	 * @param modifierId
	 * @return
	 */
	public static boolean isAbstract(int modifierId) {
		return Modifier.isAbstract(modifierId);
	}

	/**
	 * isTransient
	 * 
	 * @param modifierId
	 * @return
	 */
	public static boolean isTransient(int modifierId) {
		return Modifier.isTransient(modifierId);
	}

	/**
	 * isFinal
	 * 
	 * @param modifierId
	 * @return
	 */
	public static boolean isFinal(int modifierId) {
		return Modifier.isFinal(modifierId);
	}

	/**
	 * is Number type ??
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isNumber(Class clazz) {
		boolean flg = false;
		if (clazz.equals(Number.class))
			flg = true;
		if (clazz.getSuperclass() != null && clazz.getSuperclass().equals(Number.class))
			flg = true;
		return flg;
	}
}
