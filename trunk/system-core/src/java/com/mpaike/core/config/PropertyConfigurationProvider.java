package com.mpaike.core.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import com.mpaike.core.exception.WWFException;
import com.mpaike.core.util.ObjectHelper;
import com.mpaike.core.util.resource.FileUtil;

public class PropertyConfigurationProvider {
	
    private static final Logger LOG = LoggerFactory.getLogger(PropertyConfigurationProvider.class);

    private String configFilename = "classpath:wwf.properties";
	private Properties properties;
	private String[] keyStringArray;
	private boolean isComplete;
	
	//设计为不可变类
	public PropertyConfigurationProvider(File file){
		init(file);
	}
	
	public PropertyConfigurationProvider(String propertiesName){
		this.configFilename = propertiesName;
		File file;
		try {
			file = ResourceUtils.getFile(configFilename);
			init(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new WWFException("properties file is not find, property is null",e);
		}
		
	}
	
	private void init(File file){
		this.configFilename = FileUtil.getFileName(file.getName());
		InputStream is = null;
		InputStreamReader isr = null;
		try{
			properties = new Properties();
			is = new BufferedInputStream(new FileInputStream(file));
			isr = new InputStreamReader(is, "UTF-8");
			properties.load(isr);

			Enumeration keys = properties.keys();
			int keyNum = 0;
			keyStringArray = new String[properties.size()];
	        while(keys.hasMoreElements()){
	        	keyStringArray[keyNum] = (String)keys.nextElement();
	        	keyNum++;
	        }
	        isComplete = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new WWFException("properties file is not find, property is null",e);
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(isr!=null){
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
	} 
	
	public String[] getKeys(){
		return keyStringArray;
	}
	
	public Map toMap(){
		return new HashMap(properties);
	} 
	
	public String getString(String key,String defaultValue){
		String value = properties.getProperty(key);
		if(value==null||"".equals(value))
			return defaultValue;
		else {
			return value;
		}
	}
	
	public boolean getBoolean(String key,boolean defaultValue){
		String value = properties.getProperty(key);
		if(value==null||"".equals(value)||!ObjectHelper.isBoolean(value))
			return defaultValue;
		else {
			return Boolean.valueOf(value);
		}
	}
	
	public long getLong(String key,long defaultValue){
		String value = properties.getProperty(key);
		if(value==null||"".equals(value)||!ObjectHelper.isLong(value))
			return defaultValue;
		else {
			return Long.valueOf(value);
		}
	}
	
	public int getInt(String key,int defaultValue){
		String value = properties.getProperty(key);
		if(value==null||"".equals(value)||!ObjectHelper.isInteger(value))
			return defaultValue;
		else {
			return Integer.valueOf(value);
		}
	}

}
