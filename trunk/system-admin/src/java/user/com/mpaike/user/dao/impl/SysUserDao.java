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
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.user.dao.ISysUserDao;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.model.SysUser;
import com.mpaike.user.model.SysUserToSysRole;
import com.mpaike.util.ParamHelper;

/**
 * @author Chen.H @Date 2011-9-9
 * com.mpaike.user.dao.impl system-admin
 */
public class SysUserDao extends BaseDaoImpl<SysUser> implements ISysUserDao{

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createSQLWhere(SysUser user, StringBuffer sql, List params) {

		if (user.getUsername() != null
				&& !ParamHelper.isEmpty(user.getUsername())) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" u.username like ? ");
			params.add((new StringBuilder("%")).append(
					user.getUsername().trim()).append("%").toString());
		}
		if (user.getTruename() != null
				&& !ParamHelper.isEmpty(user.getTruename())) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" u.truename like ? ");
			params.add((new StringBuilder("%")).append(
					user.getTruename().trim()).append("%").toString());
		}
		if (user.getStatus() != null && user.getStatus().intValue() != -1) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append((new StringBuilder(" u.status= ")).append(
					user.getStatus()).toString());
		}

	}
	@Override
	public void changePassword(Long[] id, String password) {
		
		for (int i = 0, n = id.length; i < n; i++) {
			
			this.updateAndDelBeanForSQL("update SysUser  u set u.status=? where u.id=?",new Object[]{password,id[i]});
		}
		
	}

	@Override
	public void remove(Long[] id, Long type) {
		for (int i = 0, n = id.length; i < n; i++) {
			this.updateAndDelBeanForSQL("update SysUser  u set u.status=? where u.id=?",new Object[]{type,id[i]});
		}
		
	}

	@Override
	public List<SysUser> find(SysUser sysuser, Pagination p, OrderBy ob) {
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		createSQLWhere(sysuser, sql, params);

		if (sql.length() > 0)
			sql.insert(0, " where ");
		sql.insert(0, " from SysUser u ");
		String select = " select u.*  SysUser";
		return this.findList(select + sql.toString(), params.toArray(), p, ob);
	}

	@Override
	public void removeSysRole(SysUser sysUser, SysRole sysRole) {
		this.updateAndDelBeanForSQL("from SysUserToSysRole s where s.sysUser=? and s.sysRole=?" ,new Object[]{sysUser,sysRole});
		
	}

	@Override
	public SysUser loginUserByPassword(String username, String password) {
		
		return (SysUser) this.findUnique("from SysUser where username=? and password=? and status=? ", new Object[] { username, password, Long.valueOf(0) });
	}

	@Override
	public void addSysRole(SysUser sysUser, SysRole sysRole) {
		List datas = this.find(
				"from SysUserToSysRole s where s.sysUser=? and s.sysRole=?",
				new Object[] { sysUser, sysRole });
		if(datas.isEmpty()){
			SysUserToSysRole info = new SysUserToSysRole();
			info.setSysRole(sysRole);
			info.setSysUser(sysUser);
			this.getSession().save(info);
			
		}
		
	}

	@Override
	public List<SysRole> getSysRoles(SysUser sysUser) {
		List<SysRole> sysRoles = new ArrayList<SysRole>();
		@SuppressWarnings("unchecked")
		List<SysUserToSysRole> datas = this.find("from SysUserToSysRole s where s.sysUser=?", new Object[] { sysUser });
		for (int i = 0; i < datas.size(); i++)
		{
		    SysUserToSysRole sysUserToRole = (SysUserToSysRole)datas.get(i);
		    //sysUserToRole.getSysRole().getSysMenus().size();//
		    //sysUserToRole.getSysRole().getSysPopedoms().size();//
		    sysRoles.add(sysUserToRole.getSysRole());
		     
		 }
		
		return sysRoles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysRole> listNotCheckRolesToGrid(SysUser sysuser, Pagination p) {
		return this.findList(" select new SysRole(u.id,u.name,u.describe)  from SysRole u where u.id not in(  select u.sysRole.id from SysUserToSysRole u where u.sysUser.id= ? )",p, new Object[]{sysuser.getId()});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysRole> listCheckRolesToGrid(SysUser sysuser, Pagination p) {
		return this.findList(" select new SysRole(u.id,u.name,u.describe)  from SysUserToSysRole u where u.sysUser.id=?",p, new Object[]{sysuser.getId()});
	}



}
