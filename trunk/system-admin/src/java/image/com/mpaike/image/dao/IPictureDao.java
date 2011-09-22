package com.mpaike.image.dao;

import java.util.List;

import com.mpaike.core.database.hibernate.BaseDao;
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.image.model.Picture;

public interface IPictureDao extends BaseDao<Picture>{
	
	public void saveNow(Picture pic);
	
	public List<Picture> find(Picture pic, Pagination p, OrderBy ob);

}
