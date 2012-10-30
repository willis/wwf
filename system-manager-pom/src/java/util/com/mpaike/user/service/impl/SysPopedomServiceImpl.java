package com.mpaike.user.service.impl;

import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.sys.service.impl.BaseService;
import com.mpaike.user.model.SysPopedom;
import com.mpaike.user.service.SysPopedomService;


public class SysPopedomServiceImpl extends BaseService implements SysPopedomService{

	@Override
	public SysPopedom getSysPopedom(long paramSerializable) {
		
		return this.getSysPopedomDao().get(paramSerializable);
	}

	@Override
	public void removeSysPopedom(long paramSerializable) {
		this.getSysPopedomDao().removeSysPopedom(paramSerializable);
		
	}

	@Override
	public void removeSysPopedom(SysPopedom paramSysPopedom) {
		
		this.getSysPopedomDao().removeSysPopedom(paramSysPopedom);
		
	}

	@Override
	public void saveSysPopedom(SysPopedom paramSysPopedom) {
		this.getSysPopedomDao().saveOrUpdate(paramSysPopedom);
	}


	@Override
	public void updateSysPopedom(SysPopedom paramSysPopedom) {
		
		this.getSysPopedomDao().updateSysPopedom(paramSysPopedom);
		
	}

	@Override
	public List<SysPopedom> listToGrid(SysPopedom paramSysPopedom,
			Pagination p, OrderBy ob) {
		
		return this.getSysPopedomDao().listToGrid(paramSysPopedom, p, ob);
	}



}
