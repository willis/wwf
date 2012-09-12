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
import com.mpaike.user.dao.ISysRoleDao;
import com.mpaike.user.model.SysMenu;
import com.mpaike.user.model.SysPopedom;
import com.mpaike.user.model.SysRole;
import com.mpaike.util.ParamHelper;

/**
 * @author Chen.H @Date 2011-9-14 com.mpaike.user.dao.impl system-admin
 */
public class SysRoleDao extends SpringBaseDaoImpl<SysRole> implements ISysRoleDao {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void createSQLWhere(SysRole role, StringBuffer sql, List params) {
		if (role.getName() != null && !ParamHelper.isEmpty(role.getName())) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" u.name like ? ");
			params.add("%" + role.getName().trim() + "%");
		}
		if (role.getDescribe() != null
				&& !ParamHelper.isEmpty(role.getDescribe())) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" u.describe like ? ");
			params.add("%" + role.getDescribe().trim() + "%");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SysRole> listToGrid(SysRole paramSysRole, Pagination p,
			OrderBy ob) {
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		createSQLWhere(paramSysRole, sql, params);
		if (sql.length() > 0)
			sql.insert(0, " where ");
		sql.insert(0, " from SysRole u ");

		String select = " select new SysRole(u.id,u.name,u.describe) ";
		return this.findList(select + sql.toString(), params.toArray(), p, ob);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SysPopedom> listCheckPopedomsToGrid(SysRole paramSysRole) {
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		createSQLWhere(paramSysRole, sql, params);
		if (sql.length() > 0)
			sql.insert(0, " and ");
		sql.insert(0,
				" from SysPopedom u inner join u.sysRoles as r where r.id= "
						+ paramSysRole.getId());

		String select = " select new SysPopedom(u.id,u.code,u.describe) ";
		return this.find(select+sql.toString(),params.toArray());
		//		.findList(select + sql.toString(), params.toArray(), p, null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SysPopedom> listNotCheckPopedomsToGrid(SysRole paramSysRole,
			Pagination p) {
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		createSQLWhere(paramSysRole, sql, params);
		if (sql.length() > 0)
			sql.append(" and ");
		sql.append("  u.id not in (select n.id from SysPopedom n inner join n.sysRoles as r where r.id="
				+ paramSysRole.getId() + ") ");

		sql.insert(0, " from SysPopedom u  where ");

		String select = " select new SysPopedom(u.id,u.code,u.describe) ";
		return this
				.findList(select + sql.toString(), params.toArray(), p, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getSysMenusByRoleId(long paramLong) {
		
		return this.find(" select new SysMenu(d.id,d.name,d.description,d.orderBy,d.img,d.link,d.alias) from SysMenu d inner join  d.sysRoles as e where e.id=?", new Object[]{paramLong});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysRole> getSysRoleList(String name) {
		
		return this.find("from SysRole sr where sr.name=?", new Object[]{name.trim()});
	}

}
