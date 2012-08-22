package com.mpaike.sys.service.impl;


import com.mpaike.sys.dao.ISystemLogDao;
import com.mpaike.sys.service.IBaseService;
import com.mpaike.upload.dao.IAnnexDao;
import com.mpaike.user.dao.ISysGroupDao;
import com.mpaike.user.dao.ISysMenuDao;
import com.mpaike.user.dao.ISysPopedomDao;
import com.mpaike.user.dao.ISysRoleDao;
import com.mpaike.user.dao.ISysUserDao;

public class BaseService implements IBaseService{
	private ISysUserDao sysUserDao;
	private ISysPopedomDao sysPopedomDao;
	private ISysRoleDao sysRoleDao;
	private ISysMenuDao sysMenuDao;
	private ISysGroupDao sysGroupDao;
	private ISystemLogDao sysLogDao;
	private IAnnexDao annexDao;

	public ISysUserDao getSysUserDao() {
		return sysUserDao;
	}

	public void setSysUserDao(ISysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}
	
	public ISysPopedomDao getSysPopedomDao() {
		return sysPopedomDao;
	}

	public void setSysPopedomDao(ISysPopedomDao sysPopedomDao) {
		this.sysPopedomDao = sysPopedomDao;
	}

	public ISysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

	public void setSysRoleDao(ISysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
	}

	public ISysMenuDao getSysMenuDao() {
		return sysMenuDao;
	}

	public void setSysMenuDao(ISysMenuDao sysMenuDao) {
		this.sysMenuDao = sysMenuDao;
	}

	public ISystemLogDao getSysLogDao() {
		return sysLogDao;
	}

	public void setSysLogDao(ISystemLogDao sysLogDao) {
		this.sysLogDao = sysLogDao;
	}

	public ISysGroupDao getSysGroupDao() {
		return sysGroupDao;
	}

	public void setSysGroupDao(ISysGroupDao sysGroupDao) {
		this.sysGroupDao = sysGroupDao;
	}

	public IAnnexDao getAnnexDao() {
		return annexDao;
	}

	public void setAnnexDao(IAnnexDao annexDao) {
		this.annexDao = annexDao;
	}


	
}
