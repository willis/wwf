package com.mpaike.core.database.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mpaike.core.util.page.Pagination;
import com.mpaike.core.util.page.RecordInfo;

public interface BaseDao<T extends Serializable> {
	/**
	 * 通过ID查找对象
	 * 
	 * @param id
	 *            记录的ID
	 * @param lock
	 *            是否锁定对象
	 * @return 实体对象
	 */
	public T load(Serializable id, boolean lock);

	public T get(Serializable id);

	/**
	 * 通过ID查找对象,不锁定对象
	 * 
	 * @param id
	 *            记录的ID
	 * @return 实体对象
	 */
	public T load(Serializable id);

	/**
	 * 查找所有对象
	 * 
	 * @return 对象列表
	 */
	public List<T> findAll();

	public List<T> findAll(OrderBy... orders);
	
	public Pagination findAllPagination(Pagination p, OrderBy... orders);
	

	public Pagination findByList(String hql,Pagination p, Object... values);

	
	public Pagination findByList(String hql,Object[] values,Pagination p,OrderBy... orders);
	

	/**
	 * 按属性查找对象列表.
	 */
	public List<T> findByProperty(String property, Object value);
	
	/**
	 * 按属性查找对象列表.
	 */
	public List<T> findByProperty(String property, Object value,Pagination p,OrderBy... orders);

	/**
	 * 按属性查找唯一对象.
	 */
	public T findUniqueByProperty(String property, Object value);

	/**
	 * 按属性查找对象的数量
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public int countByProperty(String property, Object value);

	/**
	 * 根据Updater更新对象
	 * 
	 * @param updater
	 * @return 持久化对象
	 */
	public Object updateByUpdater(Updater updater);

	public Object updateDefault(Object entity);

	/**
	 * 保存对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	public Object save(Object entity);

	/**
	 * 更新对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	public Object update(Object entity);

	/**
	 * 保存或更新对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	public Object saveOrUpdate(Object entity);

	/**
	 * 保存或更新对象拷贝
	 * 
	 * @param entity
	 * @return 已更新的持久化对象
	 */
	public Object merge(Object entity);

	/**
	 * 删除对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void delete(Object entity);
	
	public void deleteById(Serializable id);


	/**
	 * 刷新对象
	 * 
	 * @param entity
	 */
	public void refresh(Object entity);

	/**
	 * 获得实体Class
	 * 
	 * @return
	 */
	public Class<T> getPersistentClass();

	/**
	 * 创建实体类的对象
	 * 
	 * @return
	 */
	public T createNewEntiey();
}
