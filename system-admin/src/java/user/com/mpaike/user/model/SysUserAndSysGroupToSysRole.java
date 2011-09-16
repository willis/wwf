package com.mpaike.user.model;

import com.mpaike.core.database.hibernate.AnnotationObjectKey;

public class SysUserAndSysGroupToSysRole implements  java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@AnnotationObjectKey
	private Long id;
	private SysRole sysRole;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SysRole getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

}
