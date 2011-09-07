package com.mpaike.bot.dao;

import java.util.List;

import com.mpaike.bot.model.WebUrl;

public interface IWebUrlDao {

	public void save(WebUrl bean);
	
	public WebUrl find(Long id);
	
	public List<WebUrl> find();
}
