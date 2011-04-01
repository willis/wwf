package cn.com.icore.sys.service.impl;

import java.util.List;

import com.fins.gt.model.PageInfo;

import cn.com.icore.sys.model.SystemLog;
import cn.com.icore.sys.service.SystemLogService;
import cn.com.icore.util.dao.hibernate.CommonDao;
import cn.com.icore.util.pager.Pager;

@SuppressWarnings("unchecked")
public class SystemLogServiceImpl extends CommonDao implements SystemLogService {

	public void toSave(SystemLog log) {

		super.save(log);
	}

	public int count() {

		return super.count(" from SystemLog");
	}

	public List<SystemLog> find(Pager pager) {

		return super.find(" from SystemLog",
				" from SystemLog s order by s.createat desc", pager);
	}

	public List<SystemLog> find(PageInfo pager) {

		return super.find(" from SystemLog"," from SystemLog s order by s.createat desc", pager);
	}

}
