package cn.com.icore.user.model;

public class SysUserToSysRole  extends SysUserAndSysGroupToSysRole{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SysUser sysUser;

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

}
