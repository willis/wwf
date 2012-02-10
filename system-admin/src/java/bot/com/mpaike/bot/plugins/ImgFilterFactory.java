package com.mpaike.bot.plugins;

import java.util.HashMap;
import java.util.Map;

public class ImgFilterFactory {
	
	private static Map<String,IImgFilterPlugin> map = new HashMap<String,IImgFilterPlugin>();

	static {
		map.put(ThumbnailFilterPlugin.getInstance().getName(), ThumbnailFilterPlugin.getInstance());
	}
	
	public static IImgFilterPlugin getImgFilterPlugin(String name){
		return map.get(name);
	}
}
