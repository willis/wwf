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

import com.mpaike.core.database.hibernate.BaseDaoImpl;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.user.dao.ISysGroupDao;
import com.mpaike.user.model.SysGroup;
import com.mpaike.user.model.SysGroupToSysRole;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.model.SysUser;

/**
 * @author Chen.H @Date 2011-9-16
 * com.mpaike.user.dao.impl system-admin
 */
public class SysGroupDao extends BaseDaoImpl<SysGroup> implements ISysGroupDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUser> listCheckUsersToGrid(SysUser paramSysUser,
			long paramLong, Pagination p) {
		StringBuffer sql = new StringBuffer();

		if (sql.length() > 0)
			sql.insert(0, " and ");
		sql.insert(0,
				" from SysUser u inner join u.sysGroups as g where g.id= "
						+ paramLong + " and u.status=" + 0);

		String select = " select new SysUser(u.id, u.username, u.truename, u.sex, u.email, u.tel, u.mark, u.ask, u.answer, u.other, u.regtime, u.logintime, u.password, u.status)";

		return this.findList(select +sql.toString(),p);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUser> listNotCheckUsersToGrid(SysUser paramSysUser,
			long paramLong, Pagination p) {
		StringBuffer sql = new StringBuffer();

		if (sql.length() > 0)
			sql.append(" and ");
		sql
				.append("  u.id not in (select n.id from SysUser n  inner join n.sysGroups as g where g.id="
						+ paramLong + ") and   u.status=" + 0);

		sql.insert(0, " from SysUser u  where ");

		String select = " select new SysUser(u.id, u.username, u.truename, u.sex, u.email, u.tel, u.mark, u.ask, u.answer, u.other, u.regtime, u.logintime, u.password, u.status) ";

		return this.findList(select +sql.toString(),p);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List<SysRole> getSysRoles(SysGroup sysGroup) {
		List<SysRole> sysRoles = new ArrayList<SysRole>();

	    List l1 = this.find("from SysGroupToSysRole s where s.sysGroup=?", new Object[] { sysGroup });
	    for (int i = 0; i < l1.size(); i++)
	    {
	      SysGroupToSysRole sysGroupToPopedom = (SysGroupToSysRole)l1.get(i);
	      sysRoles.add(sysGroupToPopedom.getSysRole());
	    }
	    return sysRoles;
	}

	
}
