package com.mpaike.user.service;

import java.util.List;

import com.mpaike.user.model.SysMenu;

public interface SysMenuService {
	public static final String ID_NAME = "sysMenuService";

	public void addMenu(SysMenu paramSysMenu);

	public void updateMenu(SysMenu paramSysMenu);

	public boolean save(SysMenu bean);

	public boolean delMenu(Long paramLong);

	public SysMenu getMenu(Long paramLong);

	public List<SysMenu> getTree(Long paramLong);

	public List<SysMenu> getMenusByParentId(Long paramLong);
}