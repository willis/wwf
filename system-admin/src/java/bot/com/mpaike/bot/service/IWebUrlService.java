package com.mpaike.bot.service;

import java.util.List;

import com.mpaike.bot.model.WebUrl;
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;

public interface IWebUrlService {
	
	
	public static final String ID_NAME = "webUrlService";
	
	public boolean startWebSpider(Long id,boolean restart);
	
	public boolean stopWebSpider(Long id);
	
	@SuppressWarnings("rawtypes")
	public List spiderLog(String url);
	
	public void save(WebUrl bean);
	
	public WebUrl find(Long id);
	
	public List<WebUrl> find(Pagination p,OrderBy ob);
	
	public List<WebUrl> findList(Long status);
	
}
