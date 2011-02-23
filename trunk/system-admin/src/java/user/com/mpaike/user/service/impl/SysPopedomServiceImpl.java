package com.mpaike.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.fins.gt.server.GridServerHandler;
import com.mpaike.user.model.SysPopedom;
import com.mpaike.user.service.SysPopedomService;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.dao.GtGridCommonDao;

@SuppressWarnings("unchecked")
public class SysPopedomServiceImpl extends GtGridCommonDao implements SysPopedomService{

	 public GridServerHandler listToGrid(GridServerHandler handler, SysPopedom popedom)
	  {
	    StringBuffer sql = new StringBuffer();
	    List params = new ArrayList();
	    createSQLWhere(popedom, sql, params);
	    if (sql.length() > 0)
	      sql.insert(0, " where ");
	    sql.insert(0, " from SysPopedom u ");

	    String select = " select  new SysPopedom(u.id,u.code,u.describe)  ";
	    return super.list(handler, SysPopedom.class, sql.toString(), select + sql.toString(), params.toArray());
	  }

	  public void createSQLWhere(SysPopedom popedom, StringBuffer sql, List params)
	  {
	    if (!ParamHelper.isEmpty(popedom.getCode())) {
	      if (sql.length() > 0)
	        sql.append(" and ");
	      sql.append(" u.code like ? ");
	      params.add("%" + popedom.getCode().trim() + "%");
	    }
	    if (!ParamHelper.isEmpty(popedom.getDescribe())) {
	      if (sql.length() > 0)
	        sql.append(" and ");
	      sql.append(" u.describe like ? ");
	      params.add("%" + popedom.getDescribe().trim() + "%");
	    }
	  }

	  public List<SysPopedom> getByGrid(GridServerHandler handler, SysPopedom popedom)
	  {
	    StringBuffer sql = new StringBuffer();
	    List params = new ArrayList();
	    createSQLWhere(popedom, sql, params);
	    if (sql.length() > 0)
	      sql.insert(0, " where ");
	    sql.insert(0, " from SysPopedom u ");

	    String select = " select new SysPopedom(u.id,u.code,u.describe) ";

	    return super.list(handler, sql.toString(), select + sql, params.toArray());
	  }

	  public SysPopedom getSysPopedom(long id)
	  {
	    return (SysPopedom)super.get(SysPopedom.class, id);
	  }

	  public void removeSysPopedom(long id)
	  {
	    super.remove(SysPopedom.class, id);
	  }

	  public void removeSysPopedom(SysPopedom sysPopedom)
	  {
	    super.remove(sysPopedom);
	  }

	  public void saveSysPopedom(SysPopedom sysPopedom)
	  {
	    super.save(sysPopedom);
	  }

	  public void updateSysPopedom(SysPopedom sysPopedom)
	  {
	    super.save(sysPopedom);
	  }

	  public int count()
	  {
	    return super.count("from SysPopedom s order by s.id desc", null).intValue();
	  }

	  public List<SysPopedom> getSysPopedoms(int pageSize, int Pg)
	  {
	    return super.find("from SysPopedom s order by s.id desc", null, pageSize, Pg);
	  }

}
