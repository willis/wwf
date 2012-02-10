package com.mpaike.bot.plugins;

import java.util.Map;
import java.util.Set;

public interface IImgFilterPlugin {
	
	public String getName();
	
	public String[] filterUrl(String url,Set<String> regexs);
	
    public  Map<IImgFilterPlugin,Set<String>> ruleConvert(String rule);

}
