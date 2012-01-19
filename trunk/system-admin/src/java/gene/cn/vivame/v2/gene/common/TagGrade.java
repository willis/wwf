package cn.vivame.v2.gene.common;

import java.util.HashMap;
import java.util.Map;

public class TagGrade {
	
	public static Map<String,Integer> grade(String... tagsName){
		Map<String,Integer> tagGrade = new HashMap<String,Integer>();
		for(String tagName : tagsName){
			tagGrade.put(tagName, 1);
			
		}
		
		
		return tagGrade;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
