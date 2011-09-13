package com.mpaike.affix.model;

import java.util.Date;

/**
 * 附件
 * @author max
 *
 * Aug 9, 2010
 */
public class Affix {
	private Long id;// id

	private String name;// 名称

	private String source;// 源

	private String type;// 类型，对应affix.spring.xml里的dirs下的map,一个类型一个文件夹

	private String size;// 大小

	private String extname;// 扩展名

	private Date createDate;// 时间
	
	private String objectId;//对像id
	
	private Integer objectType;//对像类别 例如 1：人员照片 2：人员档案 3：部门档案 4:上传资料
	
	private Long hotNum;

	public Long getHotNum() {
		return hotNum;
	}

	public void setHotNum(Long hotNum) {
		this.hotNum = hotNum;
	}

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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getExtname() {
		return extname;
	}

	public void setExtname(String extname) {
		this.extname = extname;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Integer getObjectType() {
		return objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

}
