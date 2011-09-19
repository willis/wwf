package com.mpaike.core.database.hibernate;

import static org.hibernate.EntityMode.POJO;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.mpaike.core.database.hibernate.BaseDaoImpl.NotBlankPropertySelector;
import com.mpaike.core.exception.WWFException;
import com.mpaike.core.util.MyBeanUtils;
import com.mpaike.core.util.page.Pagination;

public class SpringBaseDaoImpl<T extends Serializable> extends HibernateDaoSupport implements BaseDao<T>{
		
	protected Logger log = LoggerFactory.getLogger(getClass());

	private String selectAllSql;
	private Class<T> persistentClass;
	private Map<String,Field> properitesMap;
	protected String _key;
	protected String keyType;
	
	public static final NotBlankPropertySelector NOT_BLANK = new NotBlankPropertySelector();
	
	public SpringBaseDaoImpl() {

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
        		throw new WWFException("类型错误 (泛型构造错误，请在BaseDaoImpl<T>方式继承): "+this.getClass().getName());
        }
	}

	@Override
	public T load(Serializable id, boolean lock) {
		Assert.notNull(id);
		T entity = null;
		if (lock) {
			entity = (T) this.getHibernateTemplate().load(getPersistentClass(), id,
					LockMode.UPGRADE);
		} else {
			entity = (T) this.getHibernateTemplate().load(getPersistentClass(), id);
		}
		return entity;
	}

	@Override
	public T get(Serializable id) {
		return (T)this.getHibernateTemplate().get(getPersistentClass(), id);
	}

	@Override
	public T load(Serializable id) {
		return (T)this.getHibernateTemplate().load(getPersistentClass(), id);
	}

	@Override
	public List<T> findAll() {
		return this.getHibernateTemplate().find(selectAllSql);
	}

	@Override
	public List<T> findAll(OrderBy... orders) {
		return this.getHibernateTemplate().find(Finder.create(selectAllSql, orders).getOrigHql());
	}


	@Override
	public List<T> findAllPagination(Pagination p, OrderBy... orders) {
		Finder finder = Finder.create(selectAllSql, orders);
		int totalCount = countQueryResult(finder);
		p.setTotalCount(totalCount);
		Query query = getSession().createQuery(finder.getOrigHql());
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		List list = query.list();
		p.setList(list);
		return list;
	}

	@Override
	public List<T> findByEgList(T eg, boolean anyWhere, Condition[] conds,
			String... exclude) {
		Criteria crit = getCritByEg(eg, anyWhere, conds, exclude);
		return crit.list();
	}

	@Override
	public Pagination findByEg(T eg, boolean anyWhere,
			Condition[] conds, int pageNo, int pageSize, String... exclude) {
		Order[] orderArr = null;
		Condition[] condArr = null;
		if (conds != null && conds.length > 0) {
			List<Order> orderList = new ArrayList<Order>();
			List<Condition> condList = new ArrayList<Condition>();
			for (Condition c : conds) {
				if (c instanceof OrderBy) {
					orderList.add(((OrderBy) c).getOrder());
				} else {
					condList.add(c);
				}
			}
			orderArr = new Order[orderList.size()];
			condArr = new Condition[condList.size()];
			orderArr = orderList.toArray(orderArr);
			condArr = condList.toArray(condArr);
		}
		Criteria crit = getCritByEg(eg, anyWhere, condArr, exclude);
		Pagination p = new Pagination();
		p.setPageNo(pageNo);
		p.setPageSize(pageSize);
		return findByCriteria(crit, p, null, orderArr);
	}

	@Override
	public List<T> findByProperty(String property, Object value) {
		Assert.hasText(property);
		return createCriteria(Restrictions.eq(property, value)).list();
	}

	@Override
	public List<T> findByProperty(String property, Object value, Pagination p,
			OrderBy... orders) {
		Assert.hasText(property);
		this.findByCriteria(createCriteria(Restrictions.eq(property, value)),p,null,OrderBy.asOrders(orders));
		return p.list();
	}

	@Override
	public T findUniqueByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return (T) createCriteria(Restrictions.eq(property, value))
				.uniqueResult();
	}

	@Override
	public int countByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return ((Number) (createCriteria(Restrictions.eq(property, value))
				.setProjection(Projections.rowCount()).uniqueResult()))
				.intValue();
	}

	@Override
	public Object updateByUpdater(Updater updater) {
		ClassMetadata cm = getCmd(updater.getBean().getClass());
		if (cm == null) {
			throw new RuntimeException("所更新的对象没有映射或不是实体对象");
		}
		Object bean = updater.getBean();
		Object po = getSession().load(bean.getClass(),
				cm.getIdentifier(bean, POJO));
		updaterCopyToPersistentObject(updater, po);
		return po;
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
		return this.getHibernateTemplate().save(entity);
	}

	@Override
	public Object update(Object entity) {
		Assert.notNull(entity);
		this.getHibernateTemplate().update(entity);
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
		this.getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public Object merge(Object entity) {
		return this.getHibernateTemplate().merge(entity);
	}

	@Override
	public void delete(Object entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public T deleteById(Serializable id) {
		Assert.notNull(id);
		T entity = load(id);
		getSession().delete(entity);
		return entity;
	}

	@Override
	public void refresh(Object entity) {
		this.getHibernateTemplate().refresh(entity);
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
	
	/**
	 * 按HQL查询对象列表.
	 * @param hql HQL语句
	 * @param values 数量可变的参数
	 */
	@SuppressWarnings("unchecked")
	protected List find(String hql, Object... values) {
		return createQuery(hql, values).list();
	}

	@SuppressWarnings("unchecked")
	protected List findList(String hsql, Object[] value, Pagination p,
			OrderBy... orders) {
		// TODO Auto-generated method stub
		Assert.hasText(hsql);
		return findList(hsql+OrderBy.asOrdersString(orders),p,value);
	}
	
	/**
	 * 按HQL查询对象列表
	 * @param hql HQL语句
	 * @param p 分页
	 * @param values 参数
	 * @return 查询列表
	 */
	@SuppressWarnings("unchecked")
	protected List findList(String hql, Pagination p, Object... values) {
		int totalCount = countQueryResult(Finder.create(hql),values);
		Query query = createQuery(hql, values);
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		p.setTotalCount(totalCount);
		p.setList(query.list());
		return p.list();
	}
	
	protected List findList(Finder finder) {
		Query query = getSession().createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		query.setFirstResult(finder.getFirstResult());
		if (finder.getMaxResults() > 0) {
			query.setMaxResults(finder.getMaxResults());
		}
		List list = query.list();
		return list;
	}
	
	/**
	 * 按HQL查询唯一对象.
	 */
	protected Object findUnique(String hql, Object... values) {
		return createQuery(hql, values).uniqueResult();
	}
	
	/**
	 * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
	 */
	protected Query createQuery(String queryString, Object... values) {
		Assert.hasText(queryString);
		Query queryObject = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}
	
	/**
	 * 批量操作方法
	 * @param sql
	 * @param value
	 * @return
	 */
	protected int bulkUpdate(String sql, Object... values) {
		return this.getHibernateTemplate().bulkUpdate(sql, values);
	}
	
	protected int countQueryResult(Finder finder) {
		Query query = getSession().createQuery(finder.getRowCountHql());
		finder.setParamsToQuery(query);
		return ((Number) query.iterate().next()).intValue();
	}
	
	/**
	 * 通过count查询获得本次查询所能获得的对象总数.
	 * 
	 * @param finder
	 * @return
	 */
	protected int countQueryResult(Finder finder,Object... values) {
		Query query = getSession().createQuery(finder.getRowCountHql());
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return ((Number) query.iterate().next()).intValue();
	}
	
	/**
	 * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
	 */
	protected Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	protected Criteria getCritByEg(T bean, boolean anyWhere, Condition[] conds,
			String... exclude) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(bean);
		example.setPropertySelector(NOT_BLANK);
		if (anyWhere) {
			example.enableLike(MatchMode.ANYWHERE);
			example.ignoreCase();
		}
		for (String p : exclude) {
			example.excludeProperty(p);
		}
		crit.add(example);
		// 处理排序和is null字段
		if (conds != null) {
			for (Condition o : conds) {
				if (o instanceof OrderBy) {
					OrderBy order = (OrderBy) o;
					crit.addOrder(order.getOrder());
				} else if (o instanceof Nullable) {
					Nullable isNull = (Nullable) o;
					if (isNull.isNull()) {
						crit.add(Restrictions.isNull(isNull.getField()));
					} else {
						crit.add(Restrictions.isNotNull(isNull.getField()));
					}
				} else {
					// never
				}
			}
		}
		// 处理many to one查询
		ClassMetadata cm = getCmd(bean.getClass());
		String[] fieldNames = cm.getPropertyNames();
		for (String field : fieldNames) {
			Object o = cm.getPropertyValue(bean, field, POJO);
			if (o == null) {
				continue;
			}
			ClassMetadata subCm = getCmd(o.getClass());
			if (subCm == null) {
				continue;
			}
			Serializable id = subCm.getIdentifier(o, POJO);
			if (id != null) {
				Serializable idName = subCm.getIdentifierPropertyName();
				crit.add(Restrictions.eq(field + "." + idName, id));
			} else {
				crit.createCriteria(field).add(Example.create(o));
			}
		}
		return crit;
	}
	
	private ClassMetadata getCmd(Class clazz) {
		return (ClassMetadata) this.getSessionFactory().getClassMetadata(clazz);
	}
	
	private Pagination findByCriteria(Criteria crit, Pagination p, Projection projection, Order... orders) {
		int totalCount = ((Number) crit.setProjection(Projections.rowCount())
				.uniqueResult()).intValue();
		if(p==null){
			p = new Pagination(1, Integer.MAX_VALUE, totalCount);
		}else{
			p.setTotalCount(totalCount);
		}
		if (totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}
		execCriteria(p.getFirstResult(),p.getPageSize(),crit,projection,orders);
		p.setList(crit.list());
		return p;
	}
	
	private void execCriteria(int firstResult,int maxResults,Criteria crit,Projection projection, Order... orders){
		crit.setProjection(projection);
		if (projection == null) {
			crit.setResultTransformer(Criteria.ROOT_ENTITY);
		}
		if (orders != null) {
			for (Order order : orders) {
				crit.addOrder(order);
			}
		}
		crit.setFirstResult(firstResult);
		crit.setMaxResults(maxResults);
	}
	
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
						value = getSession().load(valueClass, vid);
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
}
