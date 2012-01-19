package cn.vivame.v2.gene.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;


public class Tag implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_INSTANCE = 1;//标签
	public static final int TYPE_INSTANCE_HIDE = 4;//隐藏标签
	public static final int TYPE_CONTAINER = 2;//归纳容器
	public static final int TYPE_INSTANCE_CONTAINER = 3;//实例化标签
	
	public static final int PROPERTY_WHO_PERSON = 1;//who(person)
	public static final int PROPERTY_WHO_THING = 2;//who(thing)
	public static final int PROPERTY_WHO_EVENT = 3;//who(event)
	public static final int PROPERTY_WHERE = 4;//where
	public static final int PROPERTY_WHAT = 5;//what
	
	public static final int SELECTED_FALSE = 0;//未选标签
	public static final int SELECTED_TRUE = 1;//选中标签
	
	public static final int COMMEND_FALSE = 0;//不推荐
	public static final int COMMEND_TRUE = 1;//推荐
	
	public static final int TAGMODEL_TAG = 0;//名称标签
	public static final int TAGMODEL_ORIGIN = 1;//来源标签
	public static final int TAGMODEL_ADDR = 2;//地址标签
	
	public static final String TAG_ROOT = "基因树";
	public static final String TAG_TAG_ROOT = "标签库";
	public static final String TAG_ADDR_ROOT = "addr:地区";
	public static final String TAG_SITE_ROOT = "site:数据源";
	
	private String name;
	private Set<String> alias = new HashSet<String>();
	private int type;
	private int semProperty;
	private int tagSelected;
	private int commend;
	private int tagModel;
	
	private Set<TagRelation> parentRelationSet = new TreeSet<TagRelation>();
	private Set<TagRelation> relationSet = new TreeSet<TagRelation>();
	
	private static JsonConfig config = new JsonConfig();
	{
		config.setExcludes(new String[]{"relationKey"});
	}
	
	public Tag(){}
	
	public Tag(String name,int type,int semProperty){
		setName(name);
		this.type = type;
		this.semProperty = semProperty;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(name!=null){
			this.name=name.trim();
		}else{
			this.name = null;
		}
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public Set<String> getAlias() {
		return alias;
	}

	public void setAlias(Set<String> alias) {
		this.alias = alias;
	}

	public Set<TagRelation> getParentRelationSet() {
		return parentRelationSet;
	}

	public void setParentRelationSet(Set<TagRelation> parentRelationSet) {
		this.parentRelationSet = parentRelationSet;
	}

	public Set<TagRelation> getRelationSet() {
		return relationSet;
	}

	public void setRelationSet(Set<TagRelation> relationSet) {
		this.relationSet = relationSet;
	}

	public int getSemProperty() {
		return semProperty;
	}

	public void setSemProperty(int semProperty) {
		this.semProperty = semProperty;
	}

	public int getTagSelected() {
		return tagSelected;
	}

	public void setTagSelected(int tagSelected) {
		this.tagSelected = tagSelected;
	}

	public int getCommend() {
		return commend;
	}

	public void setCommend(int commend) {
		this.commend = commend;
	}

	public int getTagModel() {
		return tagModel;
	}

	public void setTagModel(int tagModel) {
		this.tagModel = tagModel;
	}
	
	public String getBaseName(){
		if(tagModel==TAGMODEL_ORIGIN){
			return name.substring(5);
		}else if(tagModel==TAGMODEL_ADDR){
			return name.substring(5);
		}else{
			return "";
		}
	}

	public String toString(){
		return JSONSerializer.toJSON(this,config).toString();
	}
	
	public String getTypeIcon(){
			if(Tag.TYPE_CONTAINER==type){
				return "tagc";
			}else if(Tag.TYPE_INSTANCE==type){
				return "tagi";
			}else if(Tag.TYPE_INSTANCE_HIDE==type){
				return "tagih";
			}else{
				return "tagic";
			}
	}
	
	public boolean isTag(){
		return tagModel==TAGMODEL_TAG;
	}

}
