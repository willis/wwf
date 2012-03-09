package com.mpaike.user.service.impl;



import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.sys.service.impl.BaseService;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.model.SysUser;
import com.mpaike.user.model.SysUserToSysRole;
import com.mpaike.user.service.SysUserService;


public class SysUserServiceImpl extends BaseService implements SysUserService {
	@Override
	public boolean save(SysUser sysUser) {
		
		if ((sysUser.getId() == null)
				&& (this.getSysUserDao().countByProperty("username", new Object[] { sysUser.getUsername() }) > 0)) {
			return false;
		} else {
			this.getSysUserDao().save(sysUser);
			return true;
		}
		
	}



	@Override
	public SysUser get(Long id) {

		return this.getSysUserDao().get(id);
	}
	@Override
	public void remove(Long[] id, Long type) {
		
		this.getSysUserDao().remove(id, type);
	}
	@Override
	public void add(SysUser sysUser) {
		this.getSysUserDao().save(sysUser);
	}
	@Override
	public void changePassword(Long[] id, String password) {
		this.getSysUserDao().changePassword(id, password);
	}
	@Override
	public List<SysRole> listNotCheckRolesToGrid(SysUser sysuser, Pagination p){
		return this.getSysUserDao().listNotCheckRolesToGrid(sysuser, p);
	}

	
	@Override
	public void addSysRole(SysUser sysUser, SysRole sysRole) {
		
		this.getSysUserDao().addSysRole(sysUser, sysRole);
		

	}
	@Override
	public SysUser loginUserByPassword(String username, String password) {
		return this.getSysUserDao().loginUserByPassword(username, password);
	}
	@Override
	public List<SysRole> getSysRoles(SysUser sysUser)
	{
		 return this.getSysUserDao().getSysRoles(sysUser);
	}
	@Override
	public void removeSysRole(SysUser sysUser, SysRole sysRole)
	{
	   this.getSysUserDao().removeSysRole(sysUser, sysRole);
	}

	@Override
	public List<SysUser> find(SysUser sysuser, Pagination p, OrderBy ob) {
		
		return this.getSysUserDao().find(sysuser, p, ob);
	}

	@Override
	public List<SysRole> listCheckRolesToGrid(SysUser sysuser, Pagination p) {
		
		return this.getSysUserDao().listCheckRolesToGrid(sysuser, p);
	}



	@Override
	public List<SysUser> findList() {
		
		return this.getSysUserDao().findAll();
	}
}
