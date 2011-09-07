package com.mpaike.bot.service;

import java.util.List;

import com.mpaike.bot.model.WebUrl;

public interface IWebUrlService {
	
	public void save(WebUrl bean);
	
	public WebUrl find(Long id);
	
	public List<WebUrl> find();
}
