package com.mpaike.sys.service.impl;

import java.util.List;

import com.mpaike.sys.model.SystemLog;
import com.mpaike.sys.service.SystemLogService;
import com.mpaike.util.dao.CommonDao;
import com.mpaike.util.pager.Pager;


@SuppressWarnings("unchecked")
public  class SystemLogServiceImpl extends CommonDao implements  SystemLogService {

	public void add(SystemLog log) {
		
		 super.add(log);
	}

	public int count() {
		
		return super.count(" from SystemLog");
	}

	public List<SystemLog> find(Pager pager) {
		
		return super.find(" from SystemLog", " from SystemLog s order by s.createat desc", pager);
	}





	

}
