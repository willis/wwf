package com.mpaike.user.model;

import java.util.Set;

import com.mpaike.core.database.hibernate.AnnotationObjectKey;


public class SysPopedom implements  java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @AnnotationObjectKey
	private Long id;
	private String code;
	private String describe;
	private Set<SysRole> sysRoles;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Set<SysRole> getSysRoles() {
		return sysRoles;
	}
	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}
	public SysPopedom(Long id, String code, String describe) {
		super();
		this.id = id;
		this.code = code;
		this.describe = describe;
	}
	public SysPopedom() {
		super();
	}


}
