/*
 * Copyright (C) 2009-2011 WWF Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in WWF's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 */
package com.mpaike.user.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.database.hibernate.SpringBaseDaoImpl;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.user.dao.ISysPopedomDao;
import com.mpaike.user.model.SysPopedom;
import com.mpaike.util.ParamHelper;

/**
 * @author Chen.H @Date 2011-9-13
 * com.mpaike.user.dao.impl system-admin
 */
public class SysPopedomDao extends SpringBaseDaoImpl<SysPopedom> implements ISysPopedomDao{
	
	public SysPopedomDao(){
		super();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void createSQLWhere(SysPopedom popedom, StringBuffer sql, List params)
	  {
	    if (!ParamHelper.isEmpty(popedom.getCode())) {
	      if (sql.length() > 0)
	        sql.append(" and ");
	      sql.append(" p.code like ? ");
	      params.add("%" + popedom.getCode().trim() + "%");
	    }
	    if (!ParamHelper.isEmpty(popedom.getDescribe())) {
	      if (sql.length() > 0)
	        sql.append(" and ");
	      sql.append(" p.describe like ? ");
	      params.add("%" + popedom.getDescribe().trim() + "%");
	    }
	  }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SysPopedom> listToGrid(SysPopedom paramSysPopedom,
			Pagination p, OrderBy ob) {
		StringBuffer sql = new StringBuffer();
	    List params = new ArrayList();
	    createSQLWhere(paramSysPopedom, sql, params);
	    if (sql.length() > 0)
	      sql.insert(0, " where ");
	    sql.insert(0, " from SysPopedom p ");
	    String select = " select new SysPopedom(p.id,p.code,p.describe)  ";
	    return this.findList(select + sql.toString(), params.toArray(), p, ob);
	}

	@Override
	public void removeSysPopedom(long paramSerializable) {
		this.deleteById(paramSerializable);
		
	}

	@Override
	public void removeSysPopedom(SysPopedom paramSysPopedom) {
		this.delete(paramSysPopedom);
		
	}



	@Override
	public void updateSysPopedom(SysPopedom paramSysPopedom) {
		this.update(paramSysPopedom);
		
	}

}
