package com.mpaike.core.util;

/**
 * <p>Copyright: Copyright (c) 2002-2003</p>
 * <p>Company: myniko(http://www.myniko.org)</p>
 * <p>最后更新日期:2003年3月4日
 * @author niko
 */

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * 此类中封装一些常用的List操作方法。 所有方法都是静态方法，不需要生成此类的实例， 为避免生成此类的实例，构造方法被申明为private类型的。
 * 
 * @since 0.5
 */

public class ListUtil {
	/**
	 * 私有构造方法，防止类的实例化，因为工具类不需要实例化。
	 */
	private ListUtil() {
	}

	/**
	 * 将数组转换为一个List，实际上是一个ArrayList。
	 * 
	 * @param array
	 *            原数组
	 * @return 原数组不为null的时候返回包含数组内容的ArrayList，否则返回null
	 * @since 0.5
	 */
	public static List ArrayToList(Object[] array) {
		if (array != null) {
			ArrayList list = new ArrayList(array.length);
			for (int i = 0; i < array.length; i++) {
				list.add(array[i]);
			}
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 将数组转换为一个List，实际上是一个ArrayList。
	 * 
	 * @param array
	 *            原数组
	 * @return 原数组不为null的时候返回包含数组内容的ArrayList，否则返回null
	 * @since 0.5
	 */
	public static List ArrayToList(Object[] array, int type) {
		if (array != null) {
			ArrayList list = new ArrayList(array.length);
			if (type == 0) {
				for (int i = 0; i < array.length; i++) {
					list.add(array[i]);
				}
			} else if (type == 1) {
				for (int i = 0; i < array.length; i++) {
					list.add(Long.parseLong((String) array[i]));
				}
			}
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 将数组中的内容全部添加到列表中。
	 * 
	 * @param array
	 *            数组
	 * @param list
	 *            列表
	 * @since 0.5
	 */
	public static void addArrayToList(Object[] array, List list) {
		if (array == null || list == null || array.length == 0) {
			return;
		}
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
	}

	/**
	 * 将数组中的内容全部添加到列表中。
	 * 
	 * @param array
	 *            数组
	 * @param list
	 *            列表
	 * @param start
	 *            开始位置
	 * @since 0.5
	 */
	public static void addArrayToList(Object[] array, List list, int start) {
		if (array == null || list == null || array.length == 0) {
			return;
		}
		for (int i = 0; i < array.length; i++) {
			list.add(start + i, array[i]);
		}
	}

	/**
	 * 移动列表中的元素
	 * 
	 * @param list
	 *            列表
	 * @param start
	 *            移动的元素的开始索引
	 * @param end
	 *            移动的元素的最后索引，不包括这个
	 * @param to
	 *            移动到的位置
	 * @since 0.5
	 */
	public static void moveElements(List list, int start, int end, int to) {
		List subList = new ArrayList(list.subList(start, end));
		list.removeAll(subList);
		list.addAll(to, subList);
	}

	public static Long[] toArray(Collection co) {
		if (co != null) {
			List list = new ArrayList();
			for (Object obj : co) {
				list.add(obj);
			}
			Long[] objects = (Long[]) list.toArray(new Long[0]);
			return objects;
		} else {
			return null;
		}
	}

	public static boolean isEmpty(List l) {
		if (l == null || l.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(Object[] array) {
		if (array == null || array.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static List removeEmptyStrings(List list) {
		if (list == null)
			return null;

		Iterator i = list.iterator();
		if (i.hasNext()) {
			Object elem = i.next();
			if ((elem instanceof String) && (((String) elem).equals(""))) {
				i.remove();
			}
		}
		return list;
	}

	public static Map removeNullValuedKeys(Map map) {
		if (map == null) {
			return null;
		}

		Iterator i = map.keySet().iterator();
		while (i.hasNext())
			if (map.get(i.next()) == null)
				i.remove();

		return map;
	}

	public static Map removeEmptyStringValuedKeys(Map map) {
		if (map == null)
			return null;

		Iterator i = map.keySet().iterator();
		while (i.hasNext()) {
			Object value = map.get(i.next());
			if ((value instanceof String) && (((String) value).length() == 0))
				i.remove();
		}
		return map;
	}

	public static List keysNotPresent(Map map, List keys) {
		List missingList = null;
		Iterator keysEnum = keys.iterator();
		while (keysEnum.hasNext()) {
			Object key = keysEnum.next();
			if (!(map.containsKey(key))) {
				if (missingList == null)
					missingList = new ArrayList();
				missingList.add(key);
			}
		}
		return missingList;
	}

	public static Map subsetMap(Map origMap, List keys) {
		if ((origMap == null) || (keys == null))
			return null;

		Map newMap = new HashMap();
		Iterator keysEnum = keys.iterator();
		while (keysEnum.hasNext()) {
			Object key = keysEnum.next();
			if (origMap.containsKey(key))
				newMap.put(key, origMap.get(key));
		}

		return newMap;
	}

	public static List subsetByPrefix(List list, String prefix) {
		if (list == null)
			return null;
		List newList = new ArrayList();
		Iterator i = list.iterator();
		if (i.hasNext()) {
			Object elem = i.next();
			if ((elem instanceof String)
					&& (((String) elem).startsWith(prefix))) {
				newList.add(elem);
			}
		}
		return newList;
	}

	public static Map divideMap(Map origMap, List retainKeys) {
		if ((origMap == null) || (retainKeys == null))
			return null;

		Map removedKeys = null;
		Iterator origKeys = origMap.keySet().iterator();
		while (origKeys.hasNext()) {
			Object key = origKeys.next();
			if (!(retainKeys.contains(key))) {
				if (removedKeys == null)
					removedKeys = new HashMap();
				removedKeys.put(key, origMap.get(key));
				origKeys.remove();
			}
		}
		return removedKeys;
	}

	public static Map mapUnion(Map one, Map two) {
		return orderedMapUnion(one, two);
	}

	public static Map orderedMapUnion(Map primary, Map secondary) {
		Object key;
		if ((primary == null) && (secondary == null))
			return new HashMap();
		if (primary == null)
			return new HashMap(secondary);
		if (secondary == null)
			return new HashMap(primary);

		Map newMap = new HashMap();

		Iterator keysEnum = secondary.keySet().iterator();
		while (keysEnum.hasNext()) {
			key = keysEnum.next();
			newMap.put(key, secondary.get(key));
		}

		keysEnum = primary.keySet().iterator();
		while (keysEnum.hasNext()) {
			key = keysEnum.next();
			newMap.put(key, primary.get(key));
		}
		return newMap;
	}

	public static Map mapMerge(Map source, Map target) {
		if (target == null)
			return null;
		if (source == null)
			return target;
		Iterator sourceKeys = source.keySet().iterator();
		while (sourceKeys.hasNext()) {
			Object key = sourceKeys.next();
			target.put(key, source.get(key));
		}
		return target;
	}

	public static void deepRemoveKey(Object keyToRemove, Object data)
			throws Exception {
		Iterator i;
		if (data instanceof Map) {
			Map dataMap = (Map) data;
			for (i = dataMap.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				if (key == keyToRemove) {
					i.remove();
				} else {
					deepRemoveKey(keyToRemove, dataMap.get(key));
				}
			}
		} else if (data instanceof Collection) {
			Collection dataCollection = (Collection) data;
			for (i = dataCollection.iterator(); i.hasNext();)
				deepRemoveKey(keyToRemove, i.next());
		}
	}

	public static void deepRemoveNullValues(Object data) throws Exception {
		Iterator i;
		if (data instanceof Map) {
			Map dataMap = (Map) data;
			for (i = dataMap.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				Object value = dataMap.get(key);
				if (value == null) {
					i.remove();
				} else {
					deepRemoveNullValues(value);
				}
			}
		} else if (data instanceof Collection) {
			Collection dataCollection = (Collection) data;
			for (i = dataCollection.iterator(); i.hasNext();) {
				Object value = i.next();
				if (value == null) {
					i.remove();
				} else {
					deepRemoveNullValues(value);
				}
			}
		}
	}

	public static void deepRemoveEmptyCollections(Object data) throws Exception {
		Iterator i;
		if (data instanceof Map) {
			Map dataMap = (Map) data;
			for (i = dataMap.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				Object value = dataMap.get(key);
				if ((value instanceof Collection)
						&& (((Collection) value).size() == 0)) {
					i.remove();
				} else {
					deepRemoveEmptyCollections(value);
				}
			}
		} else if (data instanceof Collection) {
			Collection dataCollection = (Collection) data;
			for (i = dataCollection.iterator(); i.hasNext();) {
				Object value = i.next();
				if ((value instanceof Collection)
						&& (((Collection) value).size() == 0)) {
					i.remove();
				} else {
					deepRemoveEmptyCollections(value);
				}
			}
		}
	}

	public static Map mapMergeNonNull(Map source, Map target) {
		if (target == null)
			return null;
		if (source == null)
			return target;
		Iterator sourceKeys = source.keySet().iterator();
		while (sourceKeys.hasNext()) {
			Object key = sourceKeys.next();
			Object value = source.get(key);
			if (value != null)
				target.put(key, value);
		}
		return target;
	}

	public static Map putAllNotPresent(Map target, Map source) {
		if (target == null)
			return null;
		if (source == null)
			return target;
		Iterator sourceKeys = source.keySet().iterator();
		while (sourceKeys.hasNext()) {
			Object key = sourceKeys.next();
			if (!(target.containsKey(key)))
				target.put(key, source.get(key));
		}

		return target;
	}

	public static Map mapIntersection(Map primary, Map secondary) {
		if ((primary == null) && (secondary == null))
			return null;
		if (primary == null)
			return secondary;
		if (secondary == null)
			return primary;

		Map result = new HashMap();

		for (Iterator e = primary.keySet().iterator(); e.hasNext();) {
			Object key = e.next();
			if (secondary.get(key) != null) {
				Object value = primary.get(key);
				if (value != null) {
					result.put(key, value);
				}

			}
		}
		return result;
	}

	public static List mapIntersectionKeys(Map one, Map two) {
		List result = new ArrayList();

		if ((one == null) || (two == null)) {
			return result;
		}

		Map iterMap = one;
		Map compMap = two;
		if (two.size() < one.size()) {
			iterMap = two;
			compMap = one;
		}

		for (Iterator e = iterMap.keySet().iterator(); e.hasNext();) {
			Object key = e.next();
			if (compMap.get(key) != null) {
				result.add(compMap.get(key));
			}
		}
		return result;
	}

	public static Map substringKeyMap(String prefix, Map source) {
		if (prefix == null)
			return source;
		if (source == null)
			return null;

		Map newMap = new HashMap();
		Iterator keysEnum = source.keySet().iterator();
		while (keysEnum.hasNext()) {
			String key = (String) keysEnum.next();
			if (key.startsWith(prefix)) {
				String newKey = key.substring(prefix.length(), key.length());
				newMap.put(newKey, source.get(key));
			}
		}
		return newMap;
	}

	public static Map identityMap(List list) {
		if (list == null)
			return null;
		Map map = new HashMap();
		Iterator elems = list.iterator();
		while (elems.hasNext()) {
			Object element = elems.next();
			map.put(element, element);
		}
		return map;
	}

	public static Map reverseMap(Map origMap) {
		Map reverseMap = new HashMap();
		Iterator origKeys = origMap.keySet().iterator();
		while (origKeys.hasNext()) {
			Object origKey = origKeys.next();
			Object origValue = origMap.get(origKey);
			putMultiple(reverseMap, origValue, origKey);
		}
		return reverseMap;
	}

	public static Map putMultiple(Map map, Object key, Object value) {
		Object existingValue = map.get(key);
		if (existingValue == null) {
			map.put(key, value);
		} else if (existingValue instanceof List)
			((List) existingValue).add(value);
		else {
			map.put(key, buildList(existingValue, value));
		}

		return map;
	}

	public static Map putMultipleAsList(Map map, Object key, Object value) {
		Object existingValue = map.get(key);
		if (existingValue == null) {
			map.put(key, buildList(value));
		} else if (existingValue instanceof List)
			((List) existingValue).add(value);
		else {
			map.put(key, buildList(existingValue, value));
		}

		return map;
	}

	public static List addAll(List target, List source) {
		if (source == null)
			return target;
		if (target == null)
			return null;
		Iterator elems = source.iterator();
		return addAll(target, elems);
	}

	public static List addAll(List target, Iterator source) {
		if (source == null)
			return target;
		while (source.hasNext())
			target.add(source.next());

		return target;
	}

	public static List addAsList(List targetList, Object sourceList) {
		if (sourceList == null)
			return targetList;
		if (!(sourceList instanceof List)) {
			targetList.add(sourceList);
			return targetList;
		}
		return addAll(targetList, (List) sourceList);
	}

	public static Object combineAsLists(Object one, Object two) {
		if (one == null)
			return two;
		if (two == null)
			return one;
		List combinedList = new ArrayList();
		addAsList(combinedList, one);
		addAsList(combinedList, two);
		return combinedList;
	}

	public static Map putCombinedList(Map map, Object key, Object value) {
		if (key == null)
			throw new IllegalArgumentException(
					"putCombinedList passed null key");
		Object existingValue = map.get(key);
		Object combinedList = combineAsLists(existingValue, value);
		if (combinedList != null)
			map.put(key, combinedList);
		return map;
	}

	public static List setUnion(List one, List two) {
		if (one == null)
			return new ArrayList(two);
		List result = new ArrayList(one);
		if (two == null)
			return result;
		Iterator elems = two.iterator();
		while (elems.hasNext())
			result.add(elems.next());

		return result;
	}

	public static void addDisjunctionToSet(List one, List two) {
		if ((one == null) || (two == null))
			return;

		for (Iterator e = two.iterator(); e.hasNext();) {
			Object value = e.next();
			if (!one.contains(value)) {
				one.add(value);
			}
		}
	}

	public static Object[] arrayUnion(Object[] one, Object[] two) {
		if (one == null)
			return ((Object[]) two.clone());
		if (two == null)
			return ((Object[]) one.clone());
		Object[] result = new Object[one.length + two.length];
		int resultIndex = 0;

		for (int i = 0; i < one.length;) {
			result[resultIndex] = one[i];

			++i;
			++resultIndex;
		}

		for (int i = 0; i < two.length;) {
			result[resultIndex] = two[i];

			++i;
			++resultIndex;
		}

		return result;
	}

	public static List setIntersection(Collection one, Collection two) {
		List result = new ArrayList();
		if ((one == null) || (two == null))
			return result;

		Iterator oneElems = one.iterator();
		while (oneElems.hasNext()) {
			Object oneElem = oneElems.next();
			if (two.contains(oneElem))
				result.add(oneElem);
		}

		return result;
	}

	public static Map mapDisjunction(Map one, Map two) {
		Object key;
		Map result = new HashMap();

		for (Iterator e = one.keySet().iterator(); e.hasNext();) {
			key = e.next();
			if (two.get(key) == null)
				result.put(key, one.get(key));
		}

		for (Iterator e = two.keySet().iterator(); e.hasNext();) {
			key = e.next();
			if (one.get(key) == null)
				result.put(key, two.get(key));
		}

		return result;
	}

	public static List setDisjunction(Collection one, Collection two) {
		if (two == null)
			return new ArrayList(one);
		List result = new ArrayList();

		if (one == null)
			return result;

		Iterator oneElems = one.iterator();
		while (oneElems.hasNext()) {
			Object oneElem = oneElems.next();
			if (!(two.contains(oneElem)))
				result.add(oneElem);
		}

		return result;
	}

	public static List enumToList(Iterator i) {
		if (i == null)
			return null;

		List list = new ArrayList();
		while (i.hasNext())
			list.add(i.next());

		return list;
	}

	public static List arrayToList(Object[] arr, int from, int length) {
		if (arr == null)
			return null;
		List list = new ArrayList();
		int i = from;
		for (; i < length; ++i)
			list.add(arr[i]);

		return list;
	}

	public static List arrayToList(Object[] arr) {
		if (arr == null)
			return null;
		return arrayToList(arr, 0, arr.length);
	}

	public static List arrayToList(Object[] arr, int from) {
		if (arr == null)
			return null;
		return arrayToList(arr, from, arr.length - from);
	}

	public static Object[] listToArray(List list) {
		if (list == null)
			return null;
		Object[] valueArr = new Object[list.size()];
		list.toArray(valueArr);
		return valueArr;
	}

	public static String[] listToStringArray(Collection list) {
		if (list == null)
			return null;
		String[] valueArr = new String[list.size()];
		list.toArray(valueArr);
		return valueArr;
	}

	public static Properties mapToProperties(Map map) {
		Properties props = new Properties();
		props.putAll(map);
		return props;
	}

	public static List keysAsList(Map map) {
		if (map == null)
			return null;
		return enumToList(map.keySet().iterator());
	}

	public static List makeList(Object element) {
		List result = new ArrayList();
		if (element == null)
			return result;
		result.add(element);
		return result;
	}

	public static List makeListIfSingle(Object obj) {
		if (obj instanceof List)
			return ((List) obj);

		return makeList(obj);
	}

	public static Map mapFromLists(List keys, List values) {
		if (keys == null)
			throw new IllegalArgumentException("Keys cannot be null");

		if (values == null)
			throw new IllegalArgumentException("Values cannot be null");

		if (keys.size() != values.size()) {
			String message = "Mismatched keys (" + keys.size()
					+ ") and values (" + values.size() + ") lists\nKeys: "
					+ keys.toString() + "\nValues: " + values.toString();

			throw new IllegalArgumentException(message);
		}
		Map map = new HashMap();

		for (int i = 0; i < keys.size(); ++i) {
			Object value = values.get(i);
			if (value != null)
				map.put(keys.get(i), value);
		}

		return map;
	}

	public static String join(Object strings, String delimiter) {
		if (strings instanceof String)
			return ((String) strings);
		return join((List) strings, delimiter);
	}

	public static String join(List strings, String delimiter) {
		if (strings == null)
			return null;
		if (delimiter == null)
			delimiter = "";

		String output = "";
		for (Iterator e = strings.iterator(); e.hasNext();) {
			Object next = e.next();
			if (next != null) {
				output = output + next;
			} else {
				if (e.hasNext())
					output = output + delimiter;
			}
		}
		return output;
	}

	public static List simpleSplit(String toSplit, String delimiter) {
		String token;
		if (toSplit == null)
			return null;
		List output = new ArrayList();

		StringTokenizer tokens = new StringTokenizer(toSplit, delimiter, true);

		boolean lastTokenWasDelimiter = false;
		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			if (!(token.equals(delimiter))) {
				output.add(token);
				lastTokenWasDelimiter = false;
			} else {
				if (lastTokenWasDelimiter)
					output.add("");
				lastTokenWasDelimiter = true;
			}
		}
		return output;
	}

	public static boolean contains(String str, String substr) {
		return (str.indexOf(substr) != -1);
	}
	
	public static Object nestedGet(Object struct, String fetch) {
		Object currentStruct = struct;
		List components = simpleSplit(fetch, ".");
		String pathSoFar = "[start]";
		Iterator compEnum = components.iterator();
		while (compEnum.hasNext()) {
			String nextKey = (String) compEnum.next();
			if (currentStruct instanceof Map) {
				currentStruct = ((Map) currentStruct).get(nextKey);
			} else if (currentStruct instanceof List) {
				int index = 0;
				try {
					index = Integer.parseInt(nextKey);
					currentStruct = ((List) currentStruct).get(index);
				} catch (NumberFormatException e) {
					throw new NumberFormatException("NestedGet: Bad index "
							+ nextKey + " for List at path '" + pathSoFar
							+ '\'');
				} catch (ArrayIndexOutOfBoundsException e) {
					throw new ArrayIndexOutOfBoundsException(
							"NestedGet: Bad index " + index
									+ " for List at path '" + pathSoFar + '\'');
				}
			} else {
				throw new ClassCastException("NestedGet: at '" + pathSoFar
						+ "' structure is neither List nor Map: " + "null");
			}

			pathSoFar = pathSoFar + '.' + nextKey;
		}
		return currentStruct;
	}

	public static Map getSubtreePrefixed(String prefix, Map data) {
		if ((prefix == null) || (data == null))
			return null;

		if (prefix.length() == 0)
			return data;

		Map result = new HashMap();
		for (Iterator ii = data.keySet().iterator(); ii.hasNext();) {
			String key = (String) ii.next();
			if (key.startsWith(prefix + '.')) {
				result.put(key.substring(prefix.length() + 1), data.get(key));
			}
		}
		return result;
	}

	public static Map getPrefixed(String prefix, Map data) {
		if ((prefix == null) || (data == null))
			return null;

		if (prefix.length() == 0)
			return data;

		Map result = new HashMap();
		for (Iterator ii = data.keySet().iterator(); ii.hasNext();) {
			String key = (String) ii.next();
			if (key.startsWith(prefix + '.')) {
				result.put(key, data.get(key));
			}
		}
		return result;
	}

	public static List buildList(Object one) {
		return buildList(one, null, null, null);
	}

	public static List buildList(Object one, Object two) {
		return buildList(one, two, null, null);
	}

	public static List buildList(Object one, Object two, Object three) {
		return buildList(one, two, three, null);
	}

	public static List buildList(Object one, Object two, Object three,
			Object four) {
		List result = new ArrayList();
		if (one != null)
			result.add(one);
		if (two != null)
			result.add(two);
		if (three != null)
			result.add(three);
		if (four != null)
			result.add(four);
		return result;
	}

	public static Map buildMap(Object key, Object value) {
		return buildMap(key, value, null, null, null, null, null, null, null,
				null, null, null);
	}

	public static Map buildMap(Object key, Object value, Object key2,
			Object value2) {
		return buildMap(key, value, key2, value2, null, null, null, null, null,
				null, null, null);
	}

	public static Map buildMap(Object key, Object value, Object key2,
			Object value2, Object key3, Object value3) {
		return buildMap(key, value, key2, value2, key3, value3, null, null,
				null, null, null, null);
	}

	public static Map buildMap(Object key, Object value, Object key2,
			Object value2, Object key3, Object value3, Object key4,
			Object value4) {
		return buildMap(key, value, key2, value2, key3, value3, key4, value4,
				null, null, null, null);
	}

	public static Map buildMap(Object key, Object value, Object key2,
			Object value2, Object key3, Object value3, Object key4,
			Object value4, Object key5, Object value5) {
		return buildMap(key, value, key2, value2, key3, value3, key4, value4,
				key5, value5, null, null);
	}

	public static Map buildMap(Object key, Object value, Object key2,
			Object value2, Object key3, Object value3, Object key4,
			Object value4, Object key5, Object value5, Object key6,
			Object value6) {
		Map result = new HashMap();
		if (key != null)
			result.put(key, value);
		if (key2 != null)
			result.put(key2, value2);
		if (key3 != null)
			result.put(key3, value3);
		if (key4 != null)
			result.put(key4, value4);
		if (key5 != null)
			result.put(key5, value5);
		if (key6 != null)
			result.put(key6, value6);

		return result;
	}

	public static Map buildMap(Object key, Object value, Object key2,
			Object value2, Object key3, Object value3, Object key4,
			Object value4, Object key5, Object value5, Object key6,
			Object value6, Object key7, Object value7) {
		Map result = new HashMap();
		if (key != null)
			result.put(key, value);
		if (key2 != null)
			result.put(key2, value2);
		if (key3 != null)
			result.put(key3, value3);
		if (key4 != null)
			result.put(key4, value4);
		if (key5 != null)
			result.put(key5, value5);
		if (key6 != null)
			result.put(key6, value6);
		if (key7 != null)
			result.put(key7, value7);

		return result;
	}
	
	public static Object getSingle(Object toFetchFrom) {
		if (toFetchFrom instanceof List) {
			if (((List) toFetchFrom).size() == 1)
				return ((List) toFetchFrom).get(0);
		} else if ((toFetchFrom instanceof Map)
				&& (((Map) toFetchFrom).size() == 1)) {
			Iterator e = ((Map) toFetchFrom).keySet().iterator();
			return e.next();
		}

		return null;
	}

	public static int checkSize(Object obj) {
		if (obj instanceof List)
			return ((List) obj).size();
		if (obj instanceof Map)
			return ((Map) obj).size();
		if (obj instanceof String)
			return ((String) obj).length();

		return 0;
	}
	
	public static Map remapRow(Map row, Map remap, boolean keepNonRemapped) {
		if (remap == null)
			return row;
		Map newRow = new HashMap();
		for (Iterator e = row.keySet().iterator(); e.hasNext();) {
			Object oldKey = e.next();
			Object newKey = remap.get(oldKey);
			Object data = row.get(oldKey);

			if (newKey == null) {
				if (!keepNonRemapped) {
					newRow.put(oldKey, data);
				}
			} else {
				newRow.put(newKey, data);
			}
		}

		return newRow;
	}

	public static Map remapRow(Map row, Map remap) {
		return remapRow(row, remap, true);
	}

	public static List remapRows(List rows, Map remap, boolean keepNonRemapped) {
		if (remap == null)
			return rows;
		List newRows = new ArrayList();
		for (Iterator rowsEnum = rows.iterator(); rowsEnum.hasNext();) {
			Map oldRow = (Map) rowsEnum.next();
			Map newRow = remapRow(oldRow, remap, keepNonRemapped);

			if (newRow.size() > 0)
				newRows.add(newRow);
		}
		return newRows;
	}

	public static List remapRows(List rows, Map remap) {
		return remapRows(rows, remap, true);
	}

	public static Object remapObject(Object rows, Map remap) throws Exception {
		return remapObject(rows, remap, true);
	}

	public static Object remapObject(Object rows, Map remap,
			boolean keepNonRemapped) throws Exception {
		if (rows instanceof Map) {
			return remapRow((Map) rows, remap, keepNonRemapped);
		}
		if (rows instanceof List)
			return remapRows((List) rows, remap, keepNonRemapped);
		if (rows == null)
			return null;

		throw new Exception("Cannot remap column names for type: "
				+ rows.getClass().getName());
	}
	
	  public static List sortByExplicitOrder(List toSort, List ordered)
	  {
	    Object item;
	    if ((ordered == null) || (ordered.size() == 0)) return toSort;
	    List result = new ArrayList();

	    for (Iterator i = ordered.iterator(); i.hasNext(); ) {
	      item = i.next();
	      if (toSort.contains(item))
	    	  result.add(item);
	    }

	    for (Iterator i = toSort.iterator(); i.hasNext(); ) {
	      item = i.next();
	      if (!ordered.contains(item)) 
	    	  	result.add(item);
	    }

	    return result;
	  }
	  
	  public static List commaSeparatedStringToList(String value)
	  {
	    List result = new ArrayList();
	    StringTokenizer st = new StringTokenizer(value, ",");
	    for (; st.hasMoreTokens(); result.add(st.nextToken().toString().trim()));
	    return result;
	  }

		public static List toLowerCaseList(List list) {
			if (list == null)
				return null;

			List results = new ArrayList();
			for (Iterator e = list.iterator(); e.hasNext();) {
				String string = (String) e.next();
				results.add(string.toLowerCase());
			}
			return results;
		}

	  public static int cardinality(BitSet set) {
	    int cardinality = 0;
	    int length = set.length();
	    for (int i = 0; i < length; ++i)
	      if (set.get(i)) ++cardinality;

	    return cardinality;
	  }

}