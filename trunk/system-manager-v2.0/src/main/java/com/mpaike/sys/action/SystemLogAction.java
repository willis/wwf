package com.mpaike.sys.action;


import java.util.ArrayList;
import java.util.List;

import com.mpaike.sys.model.SystemLog;
import com.mpaike.sys.service.SystemLogService;
import com.mpaike.util.app.ApplictionContext;
import com.mpaike.util.app.BaseAction;




@SuppressWarnings("unchecked")
public class SystemLogAction  extends BaseAction {
	/**
	 * @author 陈海峰
	 * @createDate 2010-12-16 上午11:07:32
	 * @description 
	 */
	private static final long serialVersionUID = 1L;
	private int count;
	private List<SystemLog> logList = new ArrayList();
	

	public SystemLogService getSystemService() {
		return (SystemLogService) ApplictionContext.getInstance()
				.getBean(SystemLogService.ID_NAME);
	}
	
	public String logList() {
		logList = getSystemService().find(this.getPageInfo());
		return "log_list";
	}
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	public List<SystemLog> getLogList() {
		return logList;
	}

	public void setLogList(List<SystemLog> logList) {
		this.logList = logList;
	}
}
