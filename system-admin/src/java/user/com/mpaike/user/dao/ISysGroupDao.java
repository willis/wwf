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
package com.mpaike.user.dao;

import java.util.List;

import com.mpaike.core.database.hibernate.BaseDao;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.user.model.SysGroup;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.model.SysUser;

/**
 * @author Chen.H @Date 2011-9-16
 * com.mpaike.user.dao system-admin
 */
public interface ISysGroupDao extends BaseDao<SysGroup>{
	
	public  List<SysUser> listCheckUsersToGrid(SysUser paramSysUser,
			long paramLong, Pagination p);

	public  List<SysUser> listNotCheckUsersToGrid(SysUser paramSysUser,
			long paramLong ,Pagination p);
	
	public  List<SysGroup> getSysGroups(Long paramLong);

	public  List<SysRole> getSysRoles(SysGroup paramSysGroup);

}
