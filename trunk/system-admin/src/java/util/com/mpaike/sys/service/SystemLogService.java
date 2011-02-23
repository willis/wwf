package com.mpaike.sys.service;

import java.util.List;

import com.mpaike.sys.model.SystemLog;
import com.mpaike.util.pager.Pager;


public interface SystemLogService {
	
	public static final String ID_NAME = "systemLogService";
	
	public void add(SystemLog log);
	
	public List<SystemLog> find(Pager pager);



}
