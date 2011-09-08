package com.mpaike.bot.service.impl;

import java.util.List;

import com.mpaike.bot.model.WebUrl;
import com.mpaike.bot.service.IWebUrlService;
import com.mpaike.sys.service.impl.BaseService;



public class WebUrlService extends BaseService implements IWebUrlService{

	@Override
	public void save(WebUrl bean) {
		
		super.getWebUrlDao().save(bean);
		
	}

	@Override
	public WebUrl find(Long id) {

		return super.getWebUrlDao().get(id);
	}

	@Override
	public List<WebUrl> find() {
		
		return super.getWebUrlDao().findAll();
	}

	
}
