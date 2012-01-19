package com.mpaike.image.service;

import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.image.model.Picture;
import com.mpaike.sys.service.IBaseService;

public interface IPictureService extends IBaseService{
	
	public static String ID_NAME = "pictureService"; 
	
	public List<Picture> find(Picture pic,Pagination p, OrderBy ob);
	
	public void remove(Long[] longValue);
	
	public Picture find(Long id);
	
	public Picture findPrev(Long id);
	
	public Picture findNext(Long id);

}
