package com.mpaike.user.service;

import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.user.model.SysMenu;
import com.mpaike.user.model.SysPopedom;
import com.mpaike.user.model.SysRole;


public interface SysRoleService {

	public static final String ID_NAME = "sysRoleService";

	public List<SysRole> listToGrid(SysRole paramSysRole, Pagination p, OrderBy ob);

	public List<SysMenu> getSysMenusByRoleId(long paramLong);
	
	public SysRole getSysRole(long paramSerializable);

	public void saveSysRole(SysRole sysRole);

	public void removeSysRole(long paramSerializable);

	public void removeSysRole(SysRole paramSysRole);

	public void addSysMenu(long paramLong, String[] paramArrayOfString);

	public void updateSysRole(SysRole paramSysRole);

	public List<SysPopedom> listCheckPopedomsToGrid(SysRole paramSysRole);

	public List<SysPopedom> listNotCheckPopedomsToGrid(SysRole paramSysRole, Pagination p);

	public void addSysPopedom(long paramLong, String[] paramArrayOfString);

	public void removeSysPopedom(long paramLong, String[] paramArrayOfString);
	
	public List<SysRole> getSysRoleList(String name);

}

