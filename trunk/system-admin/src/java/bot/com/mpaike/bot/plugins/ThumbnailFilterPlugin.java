package com.mpaike.bot.plugins;

import static java.util.Collections.EMPTY_LIST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class ThumbnailFilterPlugin implements IImgFilterPlugin{
	
	private static final ThumbnailFilterPlugin tfp = new ThumbnailFilterPlugin();

	@Override
	public String[] filterUrl(String url,Set<String> regexs) {
		List<String> list = EMPTY_LIST;
		if(regexs!=null){
			list = new ArrayList<String>();
			for(String key : regexs){
				if(url.contains(key)){
					list.add(url.replaceAll(key, ""));
				}
			}
		}
		return (String[])list.toArray();
	}

	@Override
	public String getName() {
		return "ThumbnailFilter";
	}

	public static IImgFilterPlugin getInstance() {
		return tfp;
	}

	@Override
	public Map<IImgFilterPlugin, Set<String>> ruleConvert(String rule) {
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
