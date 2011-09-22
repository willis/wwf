package com.mpaike.image.service.impl;

import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.image.model.Picture;
import com.mpaike.image.service.IPictureService;
import com.mpaike.sys.service.impl.BaseService;

public class PictureService extends BaseService implements IPictureService {

	@Override
	public List<Picture> find(Picture pic, Pagination p, OrderBy ob) {
	
		return this.getPictureDao().find(pic, p, ob);
	}
	
	

}
