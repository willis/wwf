package com.mpaike.image.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.database.hibernate.SpringBaseDaoImpl;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.image.dao.IPictureDao;
import com.mpaike.image.model.Picture;

public class PictureDao extends SpringBaseDaoImpl<Picture> implements IPictureDao {

	@Override
	public void saveNow(Picture pic) {
		this.getHibernateTemplate().save(pic);
		this.getHibernateTemplate().flush();
	}


	@Override
	public List<Picture> find(Picture pic, Pagination p, OrderBy ob) {
	
		StringBuilder sql = new StringBuilder();
		List params = new ArrayList();
		createSQLWhere(pic, sql, params);

		if (sql.length() > 0)
			sql.insert(0, " where ");
		sql.insert(0, " from Picture p ");
		return this.findList(sql.toString(), params.toArray(), p, ob);
	}

	
	public void createSQLWhere(Picture pic, StringBuilder sql, List params) {
		if (pic.getId() != null &&pic.getId() != -1) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append((new StringBuilder(" p.id= ")).append(
					pic.getId()).toString());
		}

	}
	public void preSQLWhere(Picture pic, StringBuilder sql, List params) {
		if (pic.getId() != null &&pic.getId() != -1) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append((new StringBuilder(" p.id > ")).append(
					pic.getId()).toString());
		}

	}
	public void nextSQLWhere(Picture pic, StringBuilder sql, List params) {
		if (pic.getId() != null &&pic.getId() != -1) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append((new StringBuilder(" p.id < ")).append(
					pic.getId()).toString());
		}

	}
	@SuppressWarnings("unchecked")
	@Override
	public Picture findPrev(Long id) {
		StringBuilder sql = new StringBuilder();
		List params = new ArrayList();
		Picture pic = new Picture();
		pic.setId(id);
		preSQLWhere(pic, sql, params);

		if (sql.length() > 0)
			sql.insert(0, " where ");
		sql.insert(0, " from Picture p ");
		List<Picture> datas =  this.findList(sql.toString(), params.toArray(), new Pagination(), OrderBy.asc("id"));
		if(datas.isEmpty()){
			return null;
		}else{
			return datas.get(0);
		}
	}


	@Override
	public Picture findNext(Long id) {
		StringBuilder sql = new StringBuilder();
		List params = new ArrayList();
		Picture pic = new Picture();
		pic.setId(id);
		nextSQLWhere(pic, sql, params);

		if (sql.length() > 0)
			sql.insert(0, " where ");
		sql.insert(0, " from Picture p ");
		List<Picture> datas =  this.findList(sql.toString(), params.toArray(), new Pagination(), OrderBy.desc("id"));
		if(datas.isEmpty()){
			return null;
		}else{
			return datas.get(0);
		}
	}
}
