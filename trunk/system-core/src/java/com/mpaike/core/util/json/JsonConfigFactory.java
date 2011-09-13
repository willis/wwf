package com.mpaike.core.util.json;

import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * 
 * @author niko
 *
 */
public class JsonConfigFactory {
	
	private static final String[] excludes = new String[]{"beanClassSet","class","filterType","errorReport","lazyProperty","lazyHibernate","hibernateLazyInitializer","handler"};
	
	private static final DateJsonValueProcessor dataValueProcessor = new DateJsonValueProcessor();
	//private static final DateJsonValueProcessor sqldataValueProcessor = new DateJsonValueProcessor();
	private static final TimestampDateJsonValueProcessor timestampValueProcessor = new TimestampDateJsonValueProcessor();
	private static final HibernateJsonBeanProcessor hibernateJsonBeanProcessor = new HibernateJsonBeanProcessor();

	private static JsonConfig jsonConfig;
	private static JsonConfig timestampJsonConfig;
	static {
		jsonConfig = new JsonConfig(); 
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
		jsonConfig.setExcludes(excludes);
		
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, dataValueProcessor);
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, dataValueProcessor);
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, dataValueProcessor);
		jsonConfig.registerJsonBeanProcessor(org.hibernate.proxy.HibernateProxy.class,hibernateJsonBeanProcessor);


		//set PropertyFilter
		jsonConfig.setJsonPropertyFilter(new NullPropertyFilter());
		
		timestampJsonConfig = new JsonConfig(); 
		timestampJsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
		timestampJsonConfig.setExcludes(excludes);
		
		timestampJsonConfig.registerJsonValueProcessor(java.util.Date.class, timestampValueProcessor);
		timestampJsonConfig.registerJsonValueProcessor(java.sql.Date.class, timestampValueProcessor);
		timestampJsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, timestampValueProcessor);
		
		timestampJsonConfig.registerJsonBeanProcessor(org.hibernate.proxy.HibernateProxy.class,hibernateJsonBeanProcessor);

		//set PropertyFilter
		timestampJsonConfig.setJsonPropertyFilter(new NullPropertyFilter());

	}

	public static JsonConfig getConfigJson(int type) {
		if(type==1){
			return timestampJsonConfig;
		}
		return jsonConfig;
	}
	
	public static JsonConfig copyConfigJson(int type){
		JsonConfig jc = jsonConfig.copy();
		return jc;
	}
	
	public static void main(String[] argv){
		new JsonConfigFactory();
	}

}
