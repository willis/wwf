package cn.vivame.v2.gene.model;

import com.mpaike.core.database.hibernate.AnnotationObjectKey;

public class TagCatalog implements java.io.Serializable {
	
	public static final int TYPE_NONE = 0;//不显示特殊标记
	public static final int TYPE_NEW = 1;//显示标记为NEW
	
	@AnnotationObjectKey
	private Long id;
	private String name;
	private String tagName;
	private int tagModel;
	private int commend;
	private int displayType;
	private int sortValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTagModel() {
		return tagModel;
	}
	public void setTagModel(int tagModel) {
		this.tagModel = tagModel;
	}
	public int getCommend() {
		return commend;
	}
	public void setCommend(int commend) {
		this.commend = commend;
	}
	public int getDisplayType() {
		return displayType;
	}
	public void setDisplayType(int displayType) {
		this.displayType = displayType;
	}
	public int getSortValue() {
		return sortValue;
	}
	public void setSortValue(int sortValue) {
		this.sortValue = sortValue;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
