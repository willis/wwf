package com.mpaike.user.service.impl;

import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.sys.service.impl.BaseService;
import com.mpaike.user.model.SysMenu;
import com.mpaike.user.model.SysPopedom;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.service.SysRoleService;



public class SysRoleServiceImpl  extends BaseService implements
		SysRoleService {

	@Override
	public List<SysRole> listToGrid(SysRole paramSysRole, Pagination p,
			OrderBy ob) {
		
		return this.getSysRoleDao().listToGrid(paramSysRole, p, ob);
	}

	@Override
	public List<SysPopedom> listCheckPopedomsToGrid(SysRole paramSysRole) {
	
		return this.getSysRoleDao().listCheckPopedomsToGrid(paramSysRole);
	}

	@Override
	public List<SysPopedom> listNotCheckPopedomsToGrid(SysRole paramSysRole,
			Pagination p) {
		
		return this.getSysRoleDao().listNotCheckPopedomsToGrid(paramSysRole, p);
	}



	@Override
	public SysRole getSysRole(long paramSerializable) {
		
		return this.getSysRoleDao().get(paramSerializable);
	}



	@Override
	public void saveSysRole(SysRole sysRole) {
		this.getSysRoleDao().saveOrUpdate(sysRole);
		
	}



	@Override
	public void removeSysRole(long paramSerializable) {
		this.getSysRoleDao().deleteById(paramSerializable);
	}



	@Override
	public void removeSysRole(SysRole paramSysRole) {
		this.getSysRoleDao().delete(paramSysRole);
	}



	@Override
	public void updateSysRole(SysRole paramSysRole) {
		this.getSysRoleDao().update(paramSysRole);
	}

	@Override
	public List<SysMenu> getSysMenusByRoleId(long paramLong) {
		
		return this.getSysRoleDao().getSysMenusByRoleId(paramLong);
	}


	@Override
	public void addSysMenu(long paramLong, String[] paramArrayOfString) {
		SysRole sysRole =  this.getSysRoleDao().get(paramLong);
		sysRole.getSysMenus().clear();
		for (int i = 0; i < paramArrayOfString.length; i++){
			
			sysRole.getSysMenus().add(this.getSysMenuDao().get(Long.parseLong(paramArrayOfString[i])));
			
		}
		
	}

	@Override
	public void addSysPopedom(long paramLong, String[] paramArrayOfString) {
		SysRole sysRole = this.getSysRoleDao().get(paramLong);
	    for (int i = 0; i < paramArrayOfString.length; i++)
	      sysRole.getSysPopedoms().add(this.getSysPopedomDao().get(Long.parseLong(paramArrayOfString[i])));
		
	}

	@Override
	public void removeSysPopedom(long paramLong, String[] paramArrayOfString) {
		SysRole sysRole = this.getSysRoleDao().get(paramLong);
	    for (int i = 0; i < paramArrayOfString.length; i++)
	      sysRole.getSysPopedoms().remove(this.getSysPopedomDao().get(Long.parseLong(paramArrayOfString[i])));
		
	}

	@Override
	public List<SysRole> getSysRoleList(String name) {
		
		return this.getSysRoleDao().getSysRoleList(name);
	}

	
}
