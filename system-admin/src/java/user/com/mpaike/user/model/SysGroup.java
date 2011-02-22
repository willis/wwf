package com.mpaike.user.model;

import java.util.Set;

import com.mpaike.util.dao.IBeanPrimaryKey;


/**
 * 
 * @author 陈海峰
 * @createDate 2011-1-25 上午09:30:28
 * @description
 */

public class SysGroup   implements IBeanPrimaryKey, java.io.Serializable{
	 private static final long serialVersionUID = 1L;
	 private Long id;
	 private String name;
	 private SysGroup parentGroup;
	 private String describe;
	 private Set<SysGroup> childGroups;
	 private Long orderby;
	 private Set<SysUser> sysUsers;
	 private String extendf1;
	 private String extendf2;
	 private String extendf3;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the parentGroup
	 */
	public SysGroup getParentGroup() {
		return parentGroup;
	}
	/**
	 * @param parentGroup the parentGroup to set
	 */
	public void setParentGroup(SysGroup parentGroup) {
		this.parentGroup = parentGroup;
	}
	/**
	 * @return the describe
	 */
	public String getDescribe() {
		return describe;
	}
	/**
	 * @param describe the describe to set
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	/**
	 * @return the childGroups
	 */
	public Set<SysGroup> getChildGroups() {
		return childGroups;
	}
	/**
	 * @param childGroups the childGroups to set
	 */
	public void setChildGroups(Set<SysGroup> childGroups) {
		this.childGroups = childGroups;
	}
	/**
	 * @return the orderby
	 */
	public Long getOrderby() {
		return orderby;
	}
	/**
	 * @param orderby the orderby to set
	 */
	public void setOrderby(Long orderby) {
		this.orderby = orderby;
	}
	/**
	 * @return the sysUsers
	 */
	public Set<SysUser> getSysUsers() {
		return sysUsers;
	}
	/**
	 * @param sysUsers the sysUsers to set
	 */
	public void setSysUsers(Set<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}
	/**
	 * @return the extendf1
	 */
	public String getExtendf1() {
		return extendf1;
	}
	/**
	 * @param extendf1 the extendf1 to set
	 */
	public void setExtendf1(String extendf1) {
		this.extendf1 = extendf1;
	}
	/**
	 * @return the extendf2
	 */
	public String getExtendf2() {
		return extendf2;
	}
	/**
	 * @param extendf2 the extendf2 to set
	 */
	public void setExtendf2(String extendf2) {
		this.extendf2 = extendf2;
	}
	/**
	 * @return the extendf3
	 */
	public String getExtendf3() {
		return extendf3;
	}
	/**
	 * @param extendf3 the extendf3 to set
	 */
	public void setExtendf3(String extendf3) {
		this.extendf3 = extendf3;
	}

}
