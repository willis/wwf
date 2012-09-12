package com.mpaike.user.service;

import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.model.SysUser;


public interface SysUserService {

	public static final String ID_NAME = "sysUserService";

	public boolean save(SysUser sysUser);

	public void add(SysUser sysUser);

	public SysUser get(Long id);

	public void remove(Long[] id, Long type);

	public void changePassword(Long[] id, String password);

	public List<SysUser> find(SysUser sysuser, Pagination p, OrderBy ob);

	public List<SysRole> listNotCheckRolesToGrid(SysUser sysuser, Pagination p);

	public List<SysRole> listCheckRolesToGrid(SysUser sysuser, Pagination p);

	public void addSysRole(SysUser paramSysUser, SysRole paramSysRole);

	public SysUser loginUserByPassword(String username, String pwd);

	public List<SysRole> getSysRoles(SysUser paramSysUser);
	
    public  void removeSysRole(SysUser paramSysUser, SysRole paramSysRole);
}
