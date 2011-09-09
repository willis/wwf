package com.mpaike.core.util.json;

import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * 
 * @author niko
 *
 */
public class JsonConfigFactory {
	
	private static final String[] excludes = new String[]{};
	
	private static final DateJsonValueProcessor dataValueProcessor = new DateJsonValueProcessor();
	//private static final DateJsonValueProcessor sqldataValueProcessor = new DateJsonValueProcessor();
	private static final TimestampDateJsonValueProcessor timestampValueProcessor = new TimestampDateJsonValueProcessor();

	private static JsonConfig jsonConfig;
	private static JsonConfig timestampJsonConfig;
	static {
		jsonConfig = new JsonConfig(); 
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
		jsonConfig.setExcludes(excludes);
		
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, dataValueProcessor);
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, dataValueProcessor);
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, dataValueProcessor);

		//set PropertyFilter
		jsonConfig.setJsonPropertyFilter(new NullPropertyFilter());
		
		timestampJsonConfig = new JsonConfig(); 
		timestampJsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
		timestampJsonConfig.setExcludes(excludes);
		
		timestampJsonConfig.registerJsonValueProcessor(java.util.Date.class, timestampValueProcessor);
		timestampJsonConfig.registerJsonValueProcessor(java.sql.Date.class, timestampValueProcessor);
		timestampJsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, timestampValueProcessor);

		//set PropertyFilter
		timestampJsonConfig.setJsonPropertyFilter(new NullPropertyFilter());

	}

	public static JsonConfig getConfigJson(int type) {
		if(type==1){
			return timestampJsonConfig;
		}
		return jsonConfig;
	}
	
	public static void main(String[] argv){
		new JsonConfigFactory();
	}

}
