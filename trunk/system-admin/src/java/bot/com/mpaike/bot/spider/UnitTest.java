package com.mpaike.bot.spider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.mpaike.bot.plugins.IImgFilterPlugin;
import com.mpaike.bot.plugins.ImgFilterFactory;

/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class UnitTest {
	static String rule = "ThumbnailFilter=80_80,40_40;ALTFilter=90_90,50_50";
    /** Creates a new instance of UnitTest */
    public UnitTest() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //TestHTTP.test();
        //ruleConvertTestForms.test();
    	System.out.println(ruleConvert(rule));
    }
    public static Map<IImgFilterPlugin,Set<String>> ruleConvert(String rule){
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
    	}
    	return pluginsMap;
    }
}
