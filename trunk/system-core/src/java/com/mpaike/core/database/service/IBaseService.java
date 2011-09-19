package com.mpaike.core.database.service;

import java.io.Serializable;
import java.util.List;

import com.mpaike.core.database.hibernate.BaseDao;
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.database.hibernate.Updater;
import com.mpaike.core.exception.ParameterException;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.core.util.page.RecordInfo;



public interface IBaseService<T>{
	
	public List<T> findAllPagination(Pagination p,OrderBy... orders);
	
	public T findBean(Serializable s)throws ParameterException;
	
	public T findUniqueByProperty(String property,Object value)throws ParameterException;
	
	public List<T> findPaginationByProperty(String property,Object value,Pagination p,OrderBy... orders)throws ParameterException;
	
	public void updateBean(T o)throws ParameterException;
	
	public void updateForUpdater(Updater u)throws ParameterException;
	
	public void createBean(T o)throws ParameterException;
	
	public void removeBean(T o)throws ParameterException;
	
	public void removeBeanById(Serializable id)throws ParameterException;
	
	public void removeBeanById(Serializable[] ids);
	
}
