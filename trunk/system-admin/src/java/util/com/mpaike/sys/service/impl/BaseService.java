package com.mpaike.sys.service.impl;

import com.mpaike.bot.dao.IWebUrlDao;
import com.mpaike.member.dao.IMemberDao;
import com.mpaike.sys.service.IBaseService;

public class BaseService implements IBaseService{
	
	private IWebUrlDao webUrlDao;
	private IMemberDao memberDao;

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
	
	

}
