package com.mpaike.bot.plugins;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class ImgFilterFactory {
	
	private static Map<String,IImgFilterPlugin> map = new HashMap<String,IImgFilterPlugin>();

	static {
		map.put(ThumbnailFilterPlugin.getInstance().getName(), ThumbnailFilterPlugin.getInstance());
	}
	
	public static IImgFilterPlugin getImgFilterPlugin(String name){
		return map.get(name);
	}
	
	public static Map<IImgFilterPlugin, Set<String>> ruleConvert(String rule) {
		Map<IImgFilterPlugin,Set<String>> pluginsMap = new HashMap<IImgFilterPlugin,Set<String>>();
    	if(StringUtils.isNotBlank(rule)){
    		String[] ruleStrings = rule.split(";");
    		for(String str :ruleStrings){
    			String key =  str.substring(0,str.indexOf("="));
    			String value =  str.substring(str.indexOf("=")+1, str.length());
    			String[]  values = value.split(",");
    			Set<String> set = new HashSet<String>();
    			for(String v :values){
    				set.add(v);
    			}
   
    			pluginsMap.put(ImgFilterFactory.getImgFilterPlugin(key), set);
    			
    		}
    		return pluginsMap;
    	}else{
    		return java.util.Collections.EMPTY_MAP;
    	}
    	

	}
}
