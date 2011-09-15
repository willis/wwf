package com.mpaike.sys.service.impl;

import com.mpaike.bot.dao.IWebUrlDao;
import com.mpaike.image.dao.IPictureDao;
import com.mpaike.member.dao.IMemberDao;
import com.mpaike.sys.service.IBaseService;
import com.mpaike.user.dao.ISysMenuDao;
import com.mpaike.user.dao.ISysPopedomDao;
import com.mpaike.user.dao.ISysRoleDao;
import com.mpaike.user.dao.ISysUserDao;

public class BaseService implements IBaseService{
	private ISysUserDao sysUserDao;
	private ISysPopedomDao sysPopedomDao;
	private ISysRoleDao sysRoleDao;
	private ISysMenuDao sysMenuDao;
	private IWebUrlDao webUrlDao;
	private IMemberDao memberDao;

	private IPictureDao pictureDao;

	public IWebUrlDao getWebUrlDao() {
		return webUrlDao;
	}

	public void setWebUrlDao(IWebUrlDao webUrlDao) {
		this.webUrlDao = webUrlDao;
	}

	public IMemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(IMemberDao memberDao) {
		this.memberDao = memberDao;
	}

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

	public IPictureDao getPictureDao() {
		return pictureDao;
	}

	public void setPictureDao(IPictureDao pictureDao) {
		this.pictureDao = pictureDao;
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
	
	

}
