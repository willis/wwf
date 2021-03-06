package com.mpaike.sys.service.impl;

import cn.vivame.v2.gene.dao.IGeneDao;
import cn.vivame.v2.gene.dao.IGeneSettingDao;
import cn.vivame.v2.gene.dao.IGeneSqlDao;
import cn.vivame.v2.gene.dao.IPicGeneDao;
import cn.vivame.v2.gene.dao.ISubscribeTagDao;
import cn.vivame.v2.gene.dao.ITagCatalogDao;
import cn.vivame.v2.gene.dao.IUserGeneDao;

import com.mpaike.bot.dao.IWebUrlDao;
import com.mpaike.bot.spider.BotSpider;
import com.mpaike.image.dao.IPictureDao;
import com.mpaike.member.dao.IMemberDao;
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
	private IWebUrlDao webUrlDao;
	private IMemberDao memberDao;
	private IPictureDao pictureDao;
	private BotSpider botSpider;
	//gene
	private IGeneSqlDao geneSqlDao;
	private IGeneDao geneDao;
	private ISubscribeTagDao subscribeTagDao;
	private ITagCatalogDao tagCatalogDao;
	private IUserGeneDao userGeneDao;
	private IGeneSettingDao geneSettingDao;
	private IPicGeneDao picGeneDao;

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


	public BotSpider getBotSpider() {
		return botSpider;
	}

	public void setBotSpider(BotSpider botSpider) {
		this.botSpider = botSpider;
	}

	public IAnnexDao getAnnexDao() {
		return annexDao;
	}

	public void setAnnexDao(IAnnexDao annexDao) {
		this.annexDao = annexDao;
	}

	public IGeneSqlDao getGeneSqlDao() {
		return geneSqlDao;
	}

	public void setGeneSqlDao(IGeneSqlDao geneSqlDao) {
		this.geneSqlDao = geneSqlDao;
	}

	public IGeneDao getGeneDao() {
		return geneDao;
	}

	public void setGeneDao(IGeneDao geneDao) {
		this.geneDao = geneDao;
	}

	public ISubscribeTagDao getSubscribeTagDao() {
		return subscribeTagDao;
	}

	public void setSubscribeTagDao(ISubscribeTagDao subscribeTagDao) {
		this.subscribeTagDao = subscribeTagDao;
	}

	public ITagCatalogDao getTagCatalogDao() {
		return tagCatalogDao;
	}

	public void setTagCatalogDao(ITagCatalogDao tagCatalogDao) {
		this.tagCatalogDao = tagCatalogDao;
	}

	public IUserGeneDao getUserGeneDao() {
		return userGeneDao;
	}

	public void setUserGeneDao(IUserGeneDao userGeneDao) {
		this.userGeneDao = userGeneDao;
	}

	public IGeneSettingDao getGeneSettingDao() {
		return geneSettingDao;
	}

	public void setGeneSettingDao(IGeneSettingDao geneSettingDao) {
		this.geneSettingDao = geneSettingDao;
	}

	public IPicGeneDao getPicGeneDao() {
		return picGeneDao;
	}

	public void setPicGeneDao(IPicGeneDao picGeneDao) {
		this.picGeneDao = picGeneDao;
	}
	
}
