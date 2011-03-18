package cn.com.icore.user.model;

import java.util.Set;

import cn.com.icore.util.hibernate.dao.IBeanPrimaryKey;

public class SysPopedom implements IBeanPrimaryKey, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
