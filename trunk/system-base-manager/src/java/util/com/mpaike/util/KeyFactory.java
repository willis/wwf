package com.mpaike.util;

import java.util.HashMap;
import java.util.Map;

public class KeyFactory {
	private static Map<String,Blowfish> keyMap = new HashMap<String, Blowfish>();
	
	public static Blowfish  mapKey(String key){
		Blowfish bf = keyMap.get(key);
		if(bf==null){
			bf  =  new Blowfish(key);
			keyMap.put(key, bf);
		}
		return bf;
	}
}
