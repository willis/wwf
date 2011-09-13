package com.mpaike.core.util.json;

import static java.util.Collections.EMPTY_MAP;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.apache.commons.beanutils.PropertyUtils;

import com.mpaike.core.util.MyBeanUtils;

public class HibernateJsonBeanProcessor implements JsonBeanProcessor {
	
	private int levelMax = 2;
	static private Map<String,String> typeMap = new HashMap<String, String>();
	static {
		typeMap.put(java.lang.String.class.getName(), "true");
		typeMap.put(java.lang.Integer.class.getName(), "true");
		typeMap.put(java.util.Date.class.getName(), "true");
		typeMap.put(java.lang.Float.class.getName(), "true");
		typeMap.put(java.lang.Double.class.getName(), "true");
		typeMap.put(java.lang.Boolean.class.getName(), "true");
		typeMap.put(java.lang.Byte.class.getName(), "true");
		typeMap.put(java.lang.Character.class.getName(), "true");
		typeMap.put(java.lang.Enum.class.getName(), "true");
		typeMap.put(java.lang.Long.class.getName(), "true");
		typeMap.put(java.lang.Short.class.getName(), "true");
		typeMap.put(java.sql.Date.class.getName(), "true");
		typeMap.put(java.sql.Timestamp.class.getName(), "true");

	}

	public JSONObject processBean(Object obj, JsonConfig jsonConfig) {
		JSONObject jsonObject = null;
		List<PropertyDescriptor> fieldList = MyBeanUtils.propertyDescriptorList(obj);
		if(fieldList.size()>0){
			jsonObject = new JSONObject();
			Object fieldValueObject = null;
			for(PropertyDescriptor pd : fieldList){
				try {
					fieldValueObject = PropertyUtils.getProperty(obj, pd.getName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					fieldValueObject = null;
				}
				if(fieldValueObject!=null){
					jsonObject.element(pd.getName(), parseLazyObject(fieldValueObject,jsonConfig,0));
				}
			}
		}
		return jsonObject;
	}
	
	private Object parseLazyObject(Object fieldValueObject,JsonConfig jsonConfig,int level){
		if(level > levelMax){
			return null;
		}
		if(isBaseType(fieldValueObject)){
			return fieldValueObject;
		}
		JSONObject lazyObject = null;
		List<PropertyDescriptor> lazyObjectFields = MyBeanUtils.propertyDescriptorList(fieldValueObject);
		if(lazyObjectFields.size()>0){
			lazyObject = new JSONObject();
			for(PropertyDescriptor pd : lazyObjectFields){
				try {
					fieldValueObject = PropertyUtils.getProperty(fieldValueObject, pd.getName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					fieldValueObject = null;
				}
				if(fieldValueObject!=null){
//					if(isLazy(fieldValueObject.getClass())){
						lazyObject.element(pd.getName(), parseLazyObject(fieldValueObject,jsonConfig,level+1));
//					}else{
//						lazyObject.element(pd.getName(), fieldValueObject);
//					}
				}
			}
		}

		return lazyObject;
	} 
	
	private static boolean isLazy(Class target){
		if(target.getName().contains("$$EnhancerByCGLIB$$")||target.getName().contains("$$_javassist")){
			return true;
		}else{
			return false;
		}
	}
	
	private static boolean isBaseType(Object obj){
		return typeMap.get(obj.getClass().getName())!=null;
	}
	
}