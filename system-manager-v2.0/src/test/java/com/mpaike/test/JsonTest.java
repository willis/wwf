package com.mpaike.test;

import net.sf.json.JSONArray;

import java.util.HashSet;
import java.util.Set;

public class JsonTest {
	
	public  static void main(String args[]){
		JsonTest JT = new JsonTest();
		System.out.println("JSON");
		Set<T1> set = new  HashSet();
		T1 t = new T1();
	
		t.setId(1l);
		t.setName("t1");
		set.add(t);
		T1 t1 = new T1();
		t1.setId(1l);
		t1.setName("t1");
		set.add(t1);
		JSONArray jsonArray = JSONArray.fromObject(t);
		System.out.println(jsonArray.toString());
		System.out.println(set.contains(t));
		
	}

}
