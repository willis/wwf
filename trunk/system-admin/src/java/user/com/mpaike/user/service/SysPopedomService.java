package com.mpaike.user.service;

import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.user.model.SysPopedom;

public interface SysPopedomService {
	
	  public static String ID_NAME = "sysPopedomService";
	  
	  public  List<SysPopedom> listToGrid(SysPopedom paramSysPopedom, Pagination p, OrderBy ob);


	  public  SysPopedom getSysPopedom(long paramSerializable);

	  public  void removeSysPopedom(long paramSerializable);

	  public  void removeSysPopedom(SysPopedom paramSysPopedom);

	  public  void saveSysPopedom(SysPopedom paramSysPopedom);

	  public  void updateSysPopedom(SysPopedom paramSysPopedom);
}
