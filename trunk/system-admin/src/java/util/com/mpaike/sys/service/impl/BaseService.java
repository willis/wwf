package com.mpaike.sys.service.impl;

import com.mpaike.bot.dao.IWebUrlDao;
import com.mpaike.sys.service.IBaseService;

public class BaseService implements IBaseService{
	
	private IWebUrlDao webUrlDao;

	public IWebUrlDao getWebUrlDao() {
		return webUrlDao;
	}

	public void setWebUrlDao(IWebUrlDao webUrlDao) {
		this.webUrlDao = webUrlDao;
	}
	
	

}
