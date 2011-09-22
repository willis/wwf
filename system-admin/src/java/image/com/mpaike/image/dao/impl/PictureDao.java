package com.mpaike.image.dao.impl;

import java.util.List;

import com.mpaike.core.database.hibernate.Condition;
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

		String[] params = {"sourceName","model"};
	

		return this.findByEgList(pic, true, p,new Condition[]{ob}, params);
	}

}
