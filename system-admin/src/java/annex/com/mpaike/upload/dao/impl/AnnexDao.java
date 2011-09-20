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
package com.mpaike.upload.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.database.hibernate.SpringBaseDaoImpl;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.upload.dao.IAnnexDao;
import com.mpaike.upload.model.Annex;

/**
 * @author Chen.H @Date 2011-9-20
 * com.mpaike.upload.dao.impl system-admin
 */
public class AnnexDao extends SpringBaseDaoImpl<Annex> implements IAnnexDao{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Annex> find(String object_id, String type, Pagination p,
			OrderBy ob) {
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		if (StringUtils.isNotBlank(object_id)) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" a.object_id=?");
			params.add(object_id);
		}
		if (StringUtils.isNotBlank(object_id)&&StringUtils.isNotBlank(type)) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" a.type=?");
			params.add(type);
		}
		if (sql.length() > 0)
			sql.insert(0, " where ");
		
		String select =  " select new Annex(a.id,a.fileNames,a.fileSize,a.filePath,a.type,a.date,a.object_id) from Annex a ";
        return super.findList(select+ sql.toString(),params.toArray(), p,ob);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Annex> find(String object_id, String type) {
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		if (StringUtils.isNotBlank(object_id)) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" a.object_id=?");
			params.add(object_id);
		}
		if (StringUtils.isNotBlank(object_id)&&StringUtils.isNotBlank(type)) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" a.type=?");
			params.add(type);
		}
		if (sql.length() > 0)
			sql.insert(0, " where ");
		
		String select =  " select new Annex(a.id,a.fileNames,a.fileSize,a.filePath,a.type,a.date,a.object_id) from Annex a ";
        return super.find(select+ sql.toString(),params.toArray());
	}

	@Override
	public int saveBatch(String object_id, String real_id, String type) {
		
		return this.bulkUpdate(
				" update Annex a set object_id=? where object_id = ?",
				new Object[] { real_id, object_id });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Annex> findByObject_id(String object_id, String type) {
		StringBuilder stb = new StringBuilder("from Annex a where a.object_id ='" + object_id + "'");
		if (StringUtils.isNotBlank(type)) {
			stb.append("' and a.type ='"+type+"'");
		}
	
		return this.find(stb.toString());
	}

}
