package com.mpaike.user.service;

import java.util.List;


import com.fins.gt.server.GridServerHandler;
import com.mpaike.user.model.SysPopedom;

public interface SysPopedomService {
	
	  public static String ID_NAME = "sysPopedomService";
	  
	  public  GridServerHandler listToGrid(GridServerHandler paramGridServerHandler, SysPopedom paramSysPopedom);

	  public  List<SysPopedom> getByGrid(GridServerHandler paramGridServerHandler, SysPopedom paramSysPopedom);

	  public  SysPopedom getSysPopedom(long paramSerializable);

	  public  void removeSysPopedom(long paramSerializable);

	  public  void removeSysPopedom(SysPopedom paramSysPopedom);

	  public  void saveSysPopedom(SysPopedom paramSysPopedom);

	  public  int count();

	  public  List<SysPopedom> getSysPopedoms(int paramInt1, int paramInt2);

	  public  void updateSysPopedom(SysPopedom paramSysPopedom);
}
