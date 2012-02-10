package com.mpaike.bot.plugins;

import static java.util.Collections.EMPTY_LIST;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

}
