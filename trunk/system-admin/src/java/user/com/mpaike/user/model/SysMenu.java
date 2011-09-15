package com.mpaike.user.model;

import java.util.Date;
import java.util.Set;

import com.mpaike.core.database.hibernate.AnnotationObjectKey;
import com.mpaike.util.dao.IBeanPrimaryKey;


/**
 * 
 * @author coolmind
 * 
 */
public class SysMenu implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	@AnnotationObjectKey
	private Long id;
	private String name;
	private String description;
	private Long orderBy;
	private String img;
	private String link;
	private SysMenu parentObj;
	private Set<SysMenu> childs;
	private Date curDate;
	private String alias;
	private Integer level;
	private Set<SysRole> sysRoles;
	
	
	public SysMenu() {
		super();
	}

	public SysMenu(Long id, String name, String description, Long orderBy,
			String img, String link, String alias) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.orderBy = orderBy;
		this.img = img;
		this.link = link;
		this.alias = alias;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Long orderBy) {
		this.orderBy = orderBy;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public SysMenu getParentObj() {
		return parentObj;
	}

	public void setParentObj(SysMenu parentObj) {
		this.parentObj = parentObj;
	}

	public Set<SysMenu> getChilds() {
		return childs;
	}

	public void setChilds(Set<SysMenu> childs) {
		this.childs = childs;
	}

	public Date getCurDate() {
		return curDate;
	}

	public void setCurDate(Date curDate) {
		this.curDate = curDate;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Set<SysRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}
}
