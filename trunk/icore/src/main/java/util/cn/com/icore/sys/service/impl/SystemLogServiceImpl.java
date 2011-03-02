package cn.com.icore.sys.service.impl;

import java.util.List;

import cn.com.icore.sys.model.SystemLog;
import cn.com.icore.sys.service.SystemLogService;
import cn.com.icore.util.dao.CommonDao;
import cn.com.icore.util.pager.Pager;

@SuppressWarnings("unchecked")
public class SystemLogServiceImpl extends CommonDao implements  SystemLogService {

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
