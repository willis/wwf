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
		List<String> list =  new ArrayList<String>();
		list.add(url);
		if(regexs!=null){
			for(String key : regexs){
				if(url.contains(key)){
					list.add(url.replaceAll(key, ""));
				}
			}
		}
		return (String[])list.toArray(new String[0]);
	}

	@Override
	public String getName() {
		return "ThumbnailFilter";
	}
 
	public static IImgFilterPlugin getInstance() {
		return tfp;
	}

}
