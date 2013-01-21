package com.mpaike.core.database.service.impl;

import java.io.Serializable;
import java.util.List;

import com.mpaike.core.database.hibernate.BaseDao;
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.database.hibernate.Updater;
import com.mpaike.core.database.service.IBaseService;
import com.mpaike.core.exception.ParameterException;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.core.util.page.RecordInfo;


public abstract class BaseService<T> implements IBaseService<T> {
	
	protected abstract BaseDao getSelfDao();
	
	public List findAllPagination(Pagination p,OrderBy... orders){
		return getSelfDao().findAllPagination(p, orders).list();
	}

	
	public T findBean(Serializable s)throws ParameterException{
		if(s==null){
			throw new ParameterException();
		}
		return (T)getSelfDao().get(s);
	}
	
	public void updateBean(T o) throws ParameterException{
		// TODO Auto-generated method stub
		if(o==null){
			throw new ParameterException();
		}
		getSelfDao().update(o);
	}

	public void updateForUpdater(Updater u) throws ParameterException{
		// TODO Auto-generated method stub
		if(u==null||u.getBean()==null){
			throw new ParameterException();
		}
		getSelfDao().updateByUpdater(u);
	}

	public void createBean(T o) throws ParameterException{
		// TODO Auto-generated method stub
		if(o==null){
			throw new ParameterException();
		}
		getSelfDao().save((Serializable)o);
	}

	public void removeBean(T o) throws ParameterException{
		// TODO Auto-generated method stub
		if(o==null){
			throw new ParameterException();
		}
		getSelfDao().delete(o);
	}
	
	
	public void removeBeanById(Serializable id) throws ParameterException{
		// TODO Auto-generated method stub
		if(id==null){
			throw new ParameterException();
		}
		getSelfDao().deleteById(id);
	}
	
	public void removeBeanById(Serializable[] ids) {
		// TODO Auto-generated method stub
		if(ids!=null){
			for(int i=0,n=ids.length;i<n;i++){
				getSelfDao().deleteById(ids[i]);
			}
		}
	}

	public List findPaginationByProperty(String property, Object value, Pagination p,OrderBy... orders) throws ParameterException{
		// TODO Auto-generated method stub
		if(property==null||value==null){
			throw new ParameterException();
		}
		return getSelfDao().findByProperty(property, value, p, orders);
	}

	public T findUniqueByProperty(String property, Object value) throws ParameterException{
		// TODO Auto-generated method stub
		if(property==null||value==null){
			throw new ParameterException();
		}
		return (T)getSelfDao().findUniqueByProperty(property, value);
	}
}
