package cn.vivame.v2.gene.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

public class TagRelation implements Serializable,Comparable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int RELATION_INSTANCE = 1;//实例化
	public static final int RELATION_GATHER = 2;//聚集
	public static final int RELATION_SPECIES = 3;//属种
	public static final int RELATION_RELEVANCE = 4;//关联
	public static final int RELATION_LIMIT = 5;//限制
	public static final int RELATION_CROSS = 6;//交叉
	
	public static final Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
		
	private String parentTagName;
	private String tagName;
	private int relationType;
	private static JsonConfig config = new JsonConfig();
	{
		config.setExcludes(new String[]{"getRelationKey"});
	}
	
	public TagRelation(String tagName,String parentTagName,int relationType){
		setTagName(tagName);
		setParentTagName(parentTagName);
		this.relationType = relationType;
	}
	
	public TagRelation(){}
	
	public String getRelationKey() {
		StringBuilder sb = new StringBuilder();
		 sb.append(parentTagName.trim()).append("-").append(tagName.trim());
		return sb.toString().toLowerCase();
	}
	
	public void setRelationKey(String key) {
	}
	

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		if(tagName!=null){
			this.tagName = tagName.trim();
		}else{
			this.parentTagName=null;
		}
	}

	public int getRelationType() {
		return relationType;
	}

	public void setRelationType(int relationType) {
		this.relationType = relationType;
	}

	public String getParentTagName() {
		return parentTagName;
	}

	public void setParentTagName(String parentTagName) {
		if(parentTagName!=null){
			this.parentTagName = parentTagName.trim();
		}else{
			this.parentTagName=null;
		}
	}
	
	 public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj instanceof TagRelation){
			TagRelation tr = (TagRelation)obj;
			if(this.getRelationKey().equalsIgnoreCase(tr.getRelationKey())){
				return true;
			}
		}
		return false;
	 }
	 
	 public int hashCode(){
		 return getRelationKey().toLowerCase().hashCode();
	 }

	public String toString(){
		return JSONSerializer.toJSON(this,config).toString();
	}

	public int compareTo(Object arg0) {
		if(this.equals(arg0)){
			return 0;
		}
		return cmp.compare(getRelationKey(),((TagRelation)arg0).getRelationKey());
		
	}

}
