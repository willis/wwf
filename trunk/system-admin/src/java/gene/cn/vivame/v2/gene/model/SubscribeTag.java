package cn.vivame.v2.gene.model;

import java.util.Date;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;


public class SubscribeTag implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int TYPE_NONE = 0;//不显示特殊标记
	public static final int TYPE_NEW = 1;//显示标记为NEW
	
	public static final int SPECIAL_NO = 0;//不是专题
	public static final int SPECIAL_YES = 1;//是专题

	private static JsonConfig config = new JsonConfig();
	{
		config.setExcludes(new String[]{"commend","createdDate"});
	}

	private long typeId;
	private String displayName;
	private String tagName;
	private int tagModel;
	private int commend;
	private int displayType;
	private int special;
	private long attCount;
	private long attCancelCount;
	private long hitCount;
	private int sortCode;
	private String icon;
	private String cover;
	private Date createdDate;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public int getDisplayType() {
		return displayType;
	}
	public void setDisplayType(int displayType) {
		this.displayType = displayType;
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
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public int getSpecial() {
		return special;
	}
	public void setSpecial(int special) {
		this.special = special;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public long getHitCount() {
		return hitCount;
	}
	public void setHitCount(long hitCount) {
		this.hitCount = hitCount;
	}
	public long getAttCount() {
		return attCount;
	}
	public void setAttCount(long attCount) {
		this.attCount = attCount;
	}
	public int getSortCode() {
		return sortCode;
	}
	public void setSortCode(int sortCode) {
		this.sortCode = sortCode;
	}
	
	public long getAttCancelCount() {
		return attCancelCount;
	}
	public void setAttCancelCount(long attCancelCount) {
		this.attCancelCount = attCancelCount;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String toString(){
		return JSONSerializer.toJSON(this).toString();
	}
	
	public static SubscribeTag createCommendTag(Tag tag){
		SubscribeTag ct = new SubscribeTag();
		if(tag.getBaseName().equals("")){
			ct.setDisplayName(tag.getName());
		}else{
			ct.setDisplayName(tag.getBaseName());
		}
		ct.setTagName(tag.getName());
		ct.setTagModel(tag.getTagModel());
		ct.setSpecial(SPECIAL_NO);
		ct.setCommend(Tag.COMMEND_FALSE);
		ct.setDisplayType(TYPE_NONE);
		ct.setHitCount(0);
		ct.setAttCount(0);
		ct.setAttCancelCount(0);
		ct.setSortCode(0);
		ct.setCreatedDate(new Date());
		return ct;
	}

}
