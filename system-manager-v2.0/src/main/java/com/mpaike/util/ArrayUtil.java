package com.mpaike.util;

public class ArrayUtil {

	public ArrayUtil() {

	}

	/**
	 * String to Long[] by type
	 * 
	 * @description
	 * @param ids
	 * @param splitType
	 * @return
	 */
	public static Long[] toLongArray(String ids, String splitType) {

		String[] strValue = ids.split(splitType);
		Long[] longValue = new Long[strValue.length];
		if (strValue.length > 0) {
			for (int i = 0; i < strValue.length; i++) {
				longValue[i] = Long.parseLong(strValue[i].trim());
			}
		}
		return longValue;
	}
}
