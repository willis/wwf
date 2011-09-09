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
	private static final DateJsonValueProcessor sqldataValueProcessor = new DateJsonValueProcessor();
	private static final TimestampDateJsonValueProcessor timestampValueProcessor = new TimestampDateJsonValueProcessor();

	private static JsonConfig jsonConfig;
	static {
		jsonConfig = new JsonConfig(); 
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
		jsonConfig.setExcludes(excludes);
		
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, dataValueProcessor);
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, sqldataValueProcessor);
		jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, timestampValueProcessor);

		//set PropertyFilter
		jsonConfig.setJsonPropertyFilter(new NullPropertyFilter());

	}

	public static JsonConfig getConfigJson() {
		return jsonConfig;
	}
	
	public static void main(String[] argv){
		new JsonConfigFactory();
	}

}
