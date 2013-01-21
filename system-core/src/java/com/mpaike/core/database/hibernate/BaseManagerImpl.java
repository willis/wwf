package com.mpaike.core.database.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.mpaike.core.util.page.Pagination;

@Transactional
public class BaseManagerImpl<T extends Serializable> implements BaseManager<T> {
	protected Logger log = LoggerFactory.getLogger(getClass());
	private BaseDao<T> dao;

	public void setDao(BaseDao<T> dao) {
		this.dao = dao;
	}

	protected BaseDao<T> getDao() {
		return this.dao;
	}

	@Transactional(readOnly = true)
	public T findById(Serializable id) {
		return dao.get(id);
	}

	@Transactional(readOnly = true)
	public T load(Serializable id) {
		return dao.load(id);
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		return dao.findAll();
	}

	public Object updateByUpdater(Updater updater) {
		return dao.updateByUpdater(updater);
	}

	public Object updateDefault(Object entity) {
		return updateByUpdater(Updater.create(entity));
	}

	public Object save(Object entity) {
		return dao.save(entity);
	}

	public T saveAndRefresh(T entity) {
		this.save(entity);
		getDao().refresh(entity);
		return entity;
	}

	public Object saveOrUpdate(Object o) {
		return getDao().saveOrUpdate(o);
	}

	public void delete(Object o) {
		getDao().delete(o);
	}

	public Object update(Object o) {
		return getDao().update(o);
	}

	public Object merge(Object o) {
		return getDao().merge(o);
	}

	public void deleteById(Serializable id) {
		if (id == null) {
			return;
		}
		dao.deleteById(id);
	}

	public void deleteById(Serializable[] ids) {
		if (ids != null && ids.length > 0) {
			for (Serializable id : ids) {
				deleteById(id);
			}
		}
	}
}
