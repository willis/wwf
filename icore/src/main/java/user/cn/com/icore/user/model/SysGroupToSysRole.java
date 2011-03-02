package cn.com.icore.user.model;

public class SysGroupToSysRole extends SysUserAndSysGroupToSysRole{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SysGroup sysGroup;

	public SysGroup getSysGroup() {
		return sysGroup;
	}

	public void setSysGroup(SysGroup sysGroup) {
		this.sysGroup = sysGroup;
	}
}
