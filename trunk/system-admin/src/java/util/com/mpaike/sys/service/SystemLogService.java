package com.mpaike.sys.service;

import java.util.List;

import com.mpaike.core.util.page.Pagination;
import com.mpaike.sys.model.SystemLog;


public interface SystemLogService {
	
	public static final String ID_NAME = "systemLogService";
	
	public void add(SystemLog log);
	
	public List<SystemLog> find(Pagination pager);

}
