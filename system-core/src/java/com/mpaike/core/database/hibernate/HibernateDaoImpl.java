package com.mpaike.core.database.hibernate;

import static org.hibernate.EntityMode.POJO;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.mpaike.core.exception.WWFException;
import com.mpaike.core.util.MyBeanUtils;
import com.mpaike.core.util.page.Pagination;

public class HibernateDaoImpl<T extends Serializable> implements BaseDao<T>{
		
	
	protected Logger log = LoggerFactory.getLogger(getClass());

	private String selectAllSql;
	private Class<T> persistentClass;
	private Map<String,Field> properitesMap;
	protected String _key;
	protected String keyType;
	private HibernateDaoSupport support;
	
	
	public HibernateDaoImpl() {

		java.lang.reflect.Type t = (java.lang.reflect.Type)getClass().getGenericSuperclass();
		
        if (t instanceof ParameterizedType) {
        	java.lang.reflect.Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.persistentClass = (Class<T>) p[0];
            selectAllSql = "from "+persistentClass.getSimpleName();
            Set<Field> properitesSet = MyBeanUtils.propertysSet(persistentClass);
            properitesMap = new HashMap<String,Field>();
            for(Field f : properitesSet){
            	properitesMap.put(f.getName(), f);
            }
			
			Iterator<Field> iteratorKey = properitesSet.iterator();
			Field field;
			Annotation anno;
			while(iteratorKey.hasNext()){
				field = iteratorKey.next();
				anno = field.getAnnotation(AnnotationObjectKey.class);
				if(anno!=null){
					_key = field.getName();
					keyType = field.getType().getName();
				}
			}
        }

        if(persistentClass==null){
        		throw new WWFException("类型错误 (泛型构造错误，请在HibernateDaoImpl<T>方式继承): "+this.getClass().getName());
        }
	}

	@Override
	public T load(Serializable id, boolean lock) {
		Assert.notNull(id);
		T entity = null;
		if (lock) {
			entity = (T) support.getHibernateTemplate().load(getPersistentClass(), id,
					LockMode.UPGRADE);
		} else {
			entity = (T) support.getHibernateTemplate().load(getPersistentClass(), id);
		}
		return entity;
	}

	@Override
	public T get(Serializable id) {
		return (T)support.getHibernateTemplate().get(getPersistentClass(), id);
	}

	@Override
	public T load(Serializable id) {
		return (T)support.getHibernateTemplate().load(getPersistentClass(), id);
	}

	@Override
	public List<T> findAll() {
		return support.getHibernateTemplate().find(selectAllSql);
	}

	@Override
	public List<T> findAll(OrderBy... orders) {
		return support.getHibernateTemplate().find(Finder.create(selectAllSql, orders).getOrigHql());
	}
	
	@Override
	public List findAllPagination(String hql, Object... values) {
		return support.getHibernateTemplate().find(hql, values);
	}


	@Override
	public List<T> findAllPagination(final Pagination p, OrderBy... orders) {
        return findByList(selectAllSql,null,p,orders); 
	}


	@Override
	public List<T> findByProperty(String property, Object value) {
		Assert.hasText(property);
		return findByProperty(property, value,null,null);
	}

	@Override
	public List<T> findByProperty(final String property, final Object value, final Pagination p,
			OrderBy... orders) {
		Assert.hasText(property);
		final Finder finder = Finder.create(persistentClass.getSimpleName(),property,value,orders);
		
		List<T> l = support.getHibernateTemplate().executeFind(new HibernateCallback(){

            @Override
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(finder.getOrigHql());
                //finder.setParamsToQuery(query);
                query.setParameter(property, value);
                if(p!=null){
                	query.setFirstResult(p.getFirstResult());
                	query.setMaxResults(p.getPageSize());
                }
                List list = query.list();
                return list;
            }
		    
		});
		return l;
	}

	@Override
	public T findUniqueByProperty(final String property, final Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		final Finder finder = Finder.create(persistentClass.getSimpleName(),property,value,null);
        T o = (T)support.getHibernateTemplate().execute(new HibernateCallback(){

            @Override
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(finder.getOrigHql());
                query.setParameter(property, value);
                Object obj = query.uniqueResult();
                return obj;
            }
            
        });
        return o;
	}

	@Override
	public int countByProperty(final String property, final Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
        final Finder finder = Finder.create(persistentClass.getSimpleName(),property,value,null);
        T o = (T)support.getHibernateTemplate().execute(new HibernateCallback(){

            @Override
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(finder.getRowCountHql());
                query.setParameter(property, value);
                Object obj = query.uniqueResult();
                return obj;
            }
            
        });
        return ((Long)o).intValue();
	}

	@Override
	public Object updateByUpdater(Updater updater) {
		ClassMetadata cm = getCmd(updater.getBean().getClass());
		if (cm == null) {
			throw new RuntimeException("所更新的对象没有映射或不是实体对象");
		}
		Object bean = updater.getBean();
		support.getHibernateTemplate().update(bean);
		Object po = support.getHibernateTemplate().load(bean.getClass(),
				cm.getIdentifier(bean, POJO));
		updaterCopyToPersistentObject(updater, po);
		return po;
	}
	
	   /**
     * 将更新对象拷贝至实体对象，并处理many-to-one的更新。
     * 
     * @param updater
     * @param po
     */
    @SuppressWarnings("unchecked")
    private void updaterCopyToPersistentObject(Updater updater, Object po) {
        Map map = MyBeanUtils.describe(updater.getBean());
        Set<Map.Entry<String, Object>> set = map.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (!updater.isUpdate(name, value)) {
                continue;
            }
            if (value != null) {
                Class valueClass = value.getClass();
                ClassMetadata cm = getCmd(valueClass);
                if (cm != null) {
                    Serializable vid = cm.getIdentifier(value, POJO);
                    // 如果更新的many to one的对象的id为空，则将many to one设置为null。
                    if (vid != null) {
                        value = support.getHibernateTemplate().load(valueClass, vid);
                    } else {
                        value = null;
                    }
                }
            }
            try {
                PropertyUtils.setProperty(po, name, value);
            } catch (Exception e) {
                // never
                log.warn("更新对象时，拷贝属性异常", e);
            }
        }
    }

	@Override
	public Object updateDefault(Object entity) {
		return updateByUpdater(Updater.create(entity));
	}

	@Override
	public Object save(Object entity) {
		Assert.notNull(entity);
		if(_key!=null){
		
			if(MyBeanUtils.getSimpleProperty(entity, _key)==null){
				if(keyType.equals("java.lang.Long")){
					MyBeanUtils.setSimpleProperty(entity, _key, SequenceManager.nextID(100));
				}
			}
		}
		return support.getHibernateTemplate().save(entity);
	}

	@Override
	public Object update(Object entity) {
		Assert.notNull(entity);
		support.getHibernateTemplate().update(entity);
		return entity;
	}

	@Override
	public Object saveOrUpdate(Object entity) {
		Assert.notNull(entity);
		if(_key!=null){
		
			if(MyBeanUtils.getSimpleProperty(entity, _key)==null){
				if(keyType.equals("java.lang.Long")){
					MyBeanUtils.setSimpleProperty(entity, _key, SequenceManager.nextID(100));
				}
			}
		}
		support.getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public Object merge(Object entity) {
		return support.getHibernateTemplate().merge(entity);
	}

	@Override
	public void delete(Object entity) {
	    support.getHibernateTemplate().delete(entity);
	}

	@Override
	public void refresh(Object entity) {
	    support.getHibernateTemplate().refresh(entity);
	}

	@Override
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@Override
	public T createNewEntiey() {
		try {
			return getPersistentClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("不能创建实体对象："
					+ getPersistentClass().getName());
		}
	}
	
   private ClassMetadata getCmd(Class clazz) {
        return (ClassMetadata) support.getHibernateTemplate().getSessionFactory().getClassMetadata(clazz);
    }
   
   
	
	
	/**
	 * 按HQL查询唯一对象.
	 */
	protected T findUnique(String hql,final Object... values) {
        final Finder finder = Finder.create(hql);
        T o = (T)support.getHibernateTemplate().execute(new HibernateCallback(){

            @Override
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(finder.getOrigHql());
                if(values!=null){
                    for(int i=0,n=values.length;i<n;i++){
                        query.setParameter(i, values[i]);
                    }
                }
                Object obj = query.uniqueResult();
                return obj;
            }
            
        });
        return o;
	}

	/**
	 * 批量操作方法
	 * @param sql
	 * @param value
	 * @return
	 */
	protected int bulkUpdate(String sql, Object... values) {
		return support.getHibernateTemplate().bulkUpdate(sql, values);
	}
	
	protected int countQueryResult(Finder finder) {
		List list = support.getHibernateTemplate().find(finder.getRowCountHql());
		if(list!=null&&list.size()>0){
		    return ((Long)list.get(0)).intValue();
		}else{
		    return 0;
		}
	}
	
	/**
	 * 通过count查询获得本次查询所能获得的对象总数.
	 * 
	 * @param finder
	 * @return
	 */
	protected int countQueryResult(Finder finder,Object... values) {
		List list = support.getHibernateTemplate().find(finder.getRowCountHql(), values);
		if (list != null && list.size()>0) {
		    return ((Long)list.get(0)).intValue();
		}
		return 0;
	}


    @Override
    public List findByList(String hql, Pagination p, Object... values) {
        return findByList(hql,values,p,null); 
    }


    @Override
    public List findByList(String hql,final Object[] values,final Pagination p,
            OrderBy... orders) {
        final Finder finder = Finder.create(hql, orders);
        if(p!=null){
        	int totalCount = countQueryResult(finder,values);
        	p.setTotalCount(totalCount);
        }
          List<T> l = support.getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(org.hibernate.Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(finder.getOrigHql());
                if(values!=null){
                    for(int i=0,n=values.length;i<n;i++){
                        query.setParameter(i, values[i]);
                    }
                }
                if(p!=null){
                	query.setFirstResult(p.getFirstResult());
                    query.setMaxResults(p.getPageSize());
                }
                List list = query.list();
                return list;
            }
              
          });
        return l;
    }

    public void deleteById(Serializable id) {
        support.getHibernateTemplate().delete(this.get(id));
    }

    public HibernateDaoSupport getSupport() {
		return support;
	}

	public void setSupport(HibernateDaoSupport support) {
		this.support = support;
	}



	
}
