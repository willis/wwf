package cn.com.icore.user.model;

import cn.com.icore.util.dao.IBeanPrimaryKey;

public class SysUserAndSysGroupToSysRole implements IBeanPrimaryKey, java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
