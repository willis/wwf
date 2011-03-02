package cn.com.icore.sys.service;

import java.util.List;

import cn.com.icore.sys.model.SystemLog;
import cn.com.icore.util.pager.Pager;

public interface SystemLogService {
	
	public static final String ID_NAME = "systemLogService";
	
	public void add(SystemLog log);
	
	public List<SystemLog> find(Pager pager);



}
