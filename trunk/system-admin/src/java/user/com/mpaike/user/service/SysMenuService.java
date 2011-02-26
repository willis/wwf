package com.mpaike.user.service;

import java.util.List;

import com.mpaike.user.model.SysMenu;


public interface SysMenuService {
	 public static final String ID_NAME = "sysMenuService";
	  public  void addMenu(SysMenu paramSysMenu);

	  public  void updateMenu(SysMenu paramSysMenu);
	  
	  public boolean save(SysMenu bean);

	  public  boolean delMenu(long paramLong);

	  public  SysMenu getMenu(long paramLong);

	  public  List<SysMenu> listTree(long paramLong);

	  public  List<SysMenu> getMenusByParentId(long paramLong);
}
