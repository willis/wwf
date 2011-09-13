package com.mpaike.user.dao;

import java.util.List;

import com.mpaike.core.database.hibernate.BaseDao;
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.user.model.SysPopedom;

public interface ISysPopedomDao  extends BaseDao<SysPopedom>{
	
	  public  List<SysPopedom> listToGrid(SysPopedom paramSysPopedom, Pagination p, OrderBy ob);

	  public  List<SysPopedom> getByGrid(SysPopedom paramSysPopedom, Pagination p, OrderBy ob);

	  public  SysPopedom getSysPopedom(long paramSerializable);

	  public  void removeSysPopedom(long paramSerializable);

	  public  void removeSysPopedom(SysPopedom paramSysPopedom);

	  public  void saveSysPopedom(SysPopedom paramSysPopedom);

	  public  int count();

	  public  List<SysPopedom> getSysPopedoms(int paramInt1, int paramInt2);

	  public  void updateSysPopedom(SysPopedom paramSysPopedom);
}
