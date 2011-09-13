package com.mpaike.user.dao;

import java.util.List;

import com.mpaike.core.database.hibernate.BaseDao;
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.model.SysUser;

public interface ISysUserDao  extends BaseDao<SysUser>{

	public void changePassword(Long[] id, String password);
	
	public void remove(Long[] id, Long type);
	
	public List<SysUser> find(SysUser sysuser, Pagination p, OrderBy ob);
	
	public void removeSysRole(SysUser sysUser, SysRole sysRole);
	
	public SysUser loginUserByPassword(String username, String password);
	
	public void addSysRole(SysUser sysUser, SysRole sysRole);
	
	public List<SysRole> getSysRoles(SysUser sysUser);
	
	public List<SysRole> listNotCheckRolesToGrid(SysUser sysuser, Pagination p);
	
	public List<SysRole> listCheckRolesToGrid(SysUser sysuser, Pagination p);
	
}
