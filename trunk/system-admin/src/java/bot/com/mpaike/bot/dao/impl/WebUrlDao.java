package com.mpaike.bot.dao.impl;

import com.mpaike.bot.dao.IWebUrlDao;
import com.mpaike.bot.model.WebUrl;
import com.mpaike.core.database.hibernate.SpringBaseDaoImpl;

public class WebUrlDao   extends SpringBaseDaoImpl<WebUrl> implements IWebUrlDao{

	@Override
	public WebUrl findByUrl(String url) {
		return (WebUrl)this.findUnique("from WebUrl where url = ? ", new Object[] {url});
	}

	
	

}
