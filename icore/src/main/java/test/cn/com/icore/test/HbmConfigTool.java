package cn.com.icore.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import cn.com.icore.sys.model.SystemLog;


public class HbmConfigTool {

	public static void main(String args[]) {

		try {
			SystemLog log = new SystemLog();
			testReflect(log);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testReflect(Object model) throws NoSuchMethodException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		for (int j = 0; j < field.length; j++) { // 遍历所有属性
			String name = field[j].getName(); // 获取属性的名字

			System.out.println("attribute name:" + name);
			String type = field[j].getGenericType().toString(); // 获取属性的类型
			System.out.println(type);

		}
	}

}
