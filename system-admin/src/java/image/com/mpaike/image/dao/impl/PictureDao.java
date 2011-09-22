package com.mpaike.image.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.database.hibernate.SpringBaseDaoImpl;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.image.dao.IPictureDao;
import com.mpaike.image.model.Picture;
import com.mpaike.util.ParamHelper;

public class PictureDao extends SpringBaseDaoImpl<Picture> implements IPictureDao {

	@Override
	public void saveNow(Picture pic) {
		this.getHibernateTemplate().save(pic);
		this.getHibernateTemplate().flush();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void createSQLWhere(Picture pic, StringBuffer sql, List params) {
		if (pic.getSourceName() != null && !ParamHelper.isEmpty(pic.getSourceName())) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" p.sourceName like ? ");
			params.add("%" + pic.getSourceName().trim() + "%");
		}
		
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Picture> find(Picture pic, Pagination p, OrderBy ob) {
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		createSQLWhere(pic, sql, params);
		if (sql.length() > 0)
			sql.insert(0, " where ");
		sql.insert(0, " from Picture p ");

		return this.findList(sql.toString(), params.toArray(), p, ob);
	}

}
