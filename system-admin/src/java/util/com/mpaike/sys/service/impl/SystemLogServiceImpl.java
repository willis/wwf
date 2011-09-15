package com.mpaike.sys.service.impl;

import java.util.List;

import com.mpaike.core.database.hibernate.Finder;
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.sys.dao.ISystemLogDao;
import com.mpaike.sys.model.SystemLog;
import com.mpaike.sys.service.SystemLogService;
import com.mpaike.util.pager.Pager;


@SuppressWarnings("unchecked")
public  class SystemLogServiceImpl extends BaseService implements  SystemLogService {

	public void add(SystemLog log) {
		
		getSysLogDao().save(log);
	}


	@Override
	public List<SystemLog> find(Pagination pager) {
		return getSysLogDao().findAllPagination(pager, OrderBy.desc("createat"));
	}





	

}
