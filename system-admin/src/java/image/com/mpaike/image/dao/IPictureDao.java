package com.mpaike.image.dao;

import com.mpaike.core.database.hibernate.BaseDao;
import com.mpaike.image.model.Picture;

public interface IPictureDao extends BaseDao<Picture>{
	
	public void saveNow(Picture pic);

}
