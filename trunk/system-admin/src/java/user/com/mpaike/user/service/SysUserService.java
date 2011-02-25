package com.mpaike.user.service;

import java.util.List;

import com.fins.gt.server.GridServerHandler;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.model.SysUser;


public interface SysUserService {

	public static final String ID_NAME = "sysUserService";

	public boolean save(SysUser sysUser);

	public void add(SysUser sysUser);

	public SysUser get(Long id);

	public void remove(Long[] id, Long type);

	public void changePassword(Long[] id, String password);

	public GridServerHandler listToGrid(GridServerHandler gridserverhandler,
			SysUser sysuser);

	public GridServerHandler listNotCheckRolesToGrid(
			GridServerHandler paramGridServerHandler, SysUser paramSysUser);

	public GridServerHandler listCheckRolesToGrid(
			GridServerHandler paramGridServerHandler, SysUser paramSysUser);

	public void addSysRole(SysUser paramSysUser, SysRole paramSysRole);

	public SysUser loginUserByPassword(String username, String pwd);

	public List<SysRole> getSysRoles(SysUser paramSysUser);
	
    public  void removeSysRole(SysUser paramSysUser, SysRole paramSysRole);
}
