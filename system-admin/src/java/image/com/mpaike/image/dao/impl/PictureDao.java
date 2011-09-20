package com.mpaike.image.dao.impl;

import com.mpaike.core.database.hibernate.SpringBaseDaoImpl;
import com.mpaike.image.dao.IPictureDao;
import com.mpaike.image.model.Picture;

public class PictureDao extends SpringBaseDaoImpl<Picture> implements IPictureDao {

	@Override
	public void saveNow(Picture pic) {
		this.getHibernateTemplate().save(pic);
		this.getHibernateTemplate().flush();
	}

}
