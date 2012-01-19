package cn.vivame.v2.gene.model;

import java.io.Serializable;

public class RelationCount implements Serializable{
	
	public static final String INSTANCE_RELATION = "relation_instance";
	public static final String GATHER_RELATION = "relation_gather";
	public static final String SPECIES_RELATION = "relation_species";
	public static final String RELEVANCE_RELATION = "relation_relevance";
	public static final String LIMIT_RELATION = "relation_limit";
	public static final String CROSS_RELATION = "relation_cross";
	
	public static final String TAG_SELF = "relation_tagself";
	public static final String TAG_OTHER = "relation_tagother";
	
	private String keyName;
	private Double value;
	
	public RelationCount(){
		
	}
	
	public RelationCount(String keyName, Double value){
		this.keyName = keyName;
		this.value = value;
	}
	

	public String getKeyName() {
		return keyName;
	}


	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}



}
