package com.mpaike.bot.dao.impl;

import java.util.List;

import com.mpaike.bot.dao.IWebUrlDao;
import com.mpaike.bot.model.WebUrl;
import com.mpaike.util.dao.CommonDao;

public abstract class WebUrlDao extends CommonDao implements IWebUrlDao{

	@Override
	public void save(WebUrl bean) {
		this.save(bean);
	}

	@Override
	public WebUrl find(Long id) {
		
		return (WebUrl) this.get(WebUrl.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WebUrl> find() {
		
		return this.find("from WebUrl");
	}

	

}
