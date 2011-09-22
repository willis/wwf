package com.mpaike.core.database.hibernate;

import static org.hibernate.EntityMode.POJO;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.mpaike.core.exception.WWFException;
import com.mpaike.core.util.MyBeanUtils;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.core.util.page.RecordInfo;

/**
 * DAO基类。
 * 
 * 提供hql分页查询，example分页查询，拷贝更新等功能。
 * 
 * @param <T>
 */
@Repository
public  class BaseDaoImpl<T extends Serializable> implements BaseDao<T> {
	protected Logger log = LoggerFactory.getLogger(getClass());

	private Class<T> persistentClass;
	private Map<String,Field> properitesMap;
	//private Set<Field> properitesSet;
	protected String _key;
	protected String keyType;
	
	protected SessionFactory sessionFactory;
	
	

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {

		java.lang.reflect.Type t = (java.lang.reflect.Type)getClass().getGenericSuperclass();
		
        if (t instanceof ParameterizedType) {
        	java.lang.reflect.Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.persistentClass = (Class<T>) p[0];

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

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Object save(Object entity) {
		Assert.notNull(entity);
		if(_key!=null){
		
			if(MyBeanUtils.getSimpleProperty(entity, _key)==null){
				
				if(keyType.equals("java.lang.Long")){
					MyBeanUtils.setSimpleProperty(entity, _key, SequenceManager.nextID(100));
				}
			}
		}
		getSession().save(entity);
		return entity;
	}

	public Object update(Object entity) {
		Assert.notNull(entity);
		getSession().update(entity);
		return entity;
	}

	public Object saveOrUpdate(Object entity) {
		Assert.notNull(entity);
		if(_key!=null){
		
			if(MyBeanUtils.getSimpleProperty(entity, _key)==null){
				if(keyType.equals("java.lang.Long")){
					MyBeanUtils.setSimpleProperty(entity, _key, SequenceManager.nextID(100));
				}
			}
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public Object merge(Object entity) {
		Assert.notNull(entity);
		return getSession().merge(entity);
	}

	public void delete(Object entity) {
		Assert.notNull(entity);
		getSession().delete(entity);
	}

	public T deleteById(Serializable id) {
		Assert.notNull(id);
		T entity = load(id);
		getSession().delete(entity);
		return entity;
	}

	public T load(Serializable id) {
		Assert.notNull(id);
		return load(id, false);
	}

	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		Assert.notNull(id);
		return (T) getSession().get(getPersistentClass(), id);
	}

	@SuppressWarnings("unchecked")
	public T load(Serializable id, boolean lock) {
		Assert.notNull(id);
		T entity = null;
		if (lock) {
			entity = (T) getSession().load(getPersistentClass(), id,
					LockMode.UPGRADE);
		} else {
			entity = (T) getSession().load(getPersistentClass(), id);
		}
		return entity;
	}

	public List<T> findAll() {
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(OrderBy... orders) {
		Criteria crit = createCriteria();
		if (orders != null) {
			for (OrderBy order : orders) {
				crit.addOrder(order.getOrder());
			}
		}
		return crit.list();
	}

	public Pagination findAllForPagination(int pageNo, int pageSize, OrderBy... orders) {
		Criteria crit = createCriteria();
		return findByCriteria(crit, pageNo, pageSize, null, OrderBy
				.asOrders(orders));
	}

	public List<T> findAllPagination(Pagination p, OrderBy... orders) {
		Criteria crit = createCriteria();
		return findByCriteria(crit, p, null, OrderBy.asOrders(orders)).list();
	}
	
	public List<T> findAllRecordInfo(RecordInfo recordInfo, OrderBy... orders) {
		// TODO Auto-generated method stub
		Criteria crit = createCriteria();
		return findByCriteria(crit, recordInfo, null, OrderBy.asOrders(orders));
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
	
	@SuppressWarnings("unchecked")
	protected List findList(String hql, RecordInfo r, Object... values) {
		int totalCount = countQueryResult(Finder.create(hql),values);
		Query query = createQuery(hql, values);
		query.setFirstResult((int)r.getStartRow());
		query.setMaxResults(r.resultCount());
		r.setTotalRows(totalCount);
		return query.list();
	}

	/**
	 * 按HQL查询唯一对象.
	 */
	protected Object findUnique(String hql, Object... values) {
		return createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按属性查找对象列表.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String property, Object value) {
		Assert.hasText(property);
		return createCriteria(Restrictions.eq(property, value)).list();
	}
	
	/**
	 * 按属性查找对象列表.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String property, Object value, Pagination p,
			OrderBy... orders) {
		// TODO Auto-generated method stub
		Assert.hasText(property);
		this.findByCriteria(createCriteria(Restrictions.eq(property, value)),p,null,OrderBy.asOrders(orders));
		return p.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String property, Object value, RecordInfo r,
			OrderBy... orders) {
		// TODO Auto-generated method stub
		Assert.hasText(property);
		return findByCriteria(createCriteria(Restrictions.eq(property, value)),r,null,OrderBy.asOrders(orders));
	}
	
	@SuppressWarnings("unchecked")
	protected List findList(String hsql, Object[] value, Pagination p,
			OrderBy... orders) {
		// TODO Auto-generated method stub
		Assert.hasText(hsql);
		return findList(hsql+OrderBy.asOrdersString(orders),p,value);
	}

	/**
	 * 按属性查找唯一对象.
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return (T) createCriteria(Restrictions.eq(property, value))
				.uniqueResult();
	}

	public int countByProperty(String property, Object value) {
		Assert.hasText(property);
		Assert.notNull(value);
		return ((Number) (createCriteria(Restrictions.eq(property, value))
				.setProjection(Projections.rowCount()).uniqueResult()))
				.intValue();
	}

	@SuppressWarnings("unchecked")
	protected Pagination findForPaination(Finder finder, int pageNo, int pageSize) {
		Pagination p = new Pagination(1, Integer.MAX_VALUE, 0);
		return findForPaination(finder, p);
	}
	
	@SuppressWarnings("unchecked")
	protected Pagination findForPaination(Finder finder, Pagination p) {
		int totalCount = countQueryResult(finder);
		if(p==null){
			p = new Pagination(1, Integer.MAX_VALUE, totalCount);
		}else{
			p.setTotalCount(totalCount);
		}
		if (totalCount < 1) {
			p.setList(new ArrayList());
			return p;
		}
		Query query = getSession().createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		query.setFirstResult(p.getFirstResult());
		query.setMaxResults(p.getPageSize());
		List list = query.list();
		p.setList(list);
		return p;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> findList(Finder finder, RecordInfo r) {
		int totalCount = countQueryResult(finder);
		r.setTotalRows(totalCount);
		Query query = getSession().createQuery(finder.getOrigHql());
		finder.setParamsToQuery(query);
		query.setFirstResult((int)r.getStartRow());
		query.setMaxResults(r.resultCount());
		return query.list();
	}

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public List<T> findByEgList(T eg, boolean anyWhere, Condition[] conds,
			String... exclude) {
		Criteria crit = getCritByEg(eg, anyWhere, conds, exclude);
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByEgList(T eg, boolean anyWhere, Condition[] conds,
			int firstResult, int maxResult, String... exclude) {
		Criteria crit = getCritByEg(eg, anyWhere, conds, exclude);
		crit.setFirstResult(firstResult);
		crit.setMaxResults(maxResult);
		return crit.list();
	}
	
	@Override
	public List<T> findByEgList(T eg, boolean anyWhere, Pagination p,
			Condition[] conds, String... exclude) {
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
		return findByCriteria(crit, p, null, orderArr).list();
	}

	public Pagination findByEg(T eg, boolean anyWhere, Condition[] conds,
			int page, int pageSize, String... exclude) {
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
		return findByCriteria(crit, page, pageSize, null, orderArr);
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
	 * 按Criterion查询对象列表.
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		return createCriteria(criterion).list();
	}
	
	protected Pagination findByCriteria(Criteria crit, 	int pageNo,
			int pageSize, Projection projection, Order... orders) {
		Pagination p = new Pagination(pageNo, pageSize, 0);
		return findByCriteria(crit,p,projection,orders);
	}

	@SuppressWarnings("unchecked")
	protected Pagination findByCriteria(Criteria crit, Pagination p, Projection projection, Order... orders) {
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
	
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criteria crit, RecordInfo recordInfo, Projection projection, Order... orders) {
		int totalCount = ((Number) crit.setProjection(Projections.rowCount())
				.uniqueResult()).intValue();
		if(recordInfo==null){
			recordInfo = new RecordInfo();
		}else{
			recordInfo.setTotalRows(totalCount);
		}
		execCriteria((int)recordInfo.getStartRow(),recordInfo.resultCount(),crit,projection,orders);
		return crit.list();
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

	/**
	 * 通过count查询获得本次查询所能获得的对象总数.
	 * 
	 * @param finder
	 * @return
	 */
	protected int countQueryResult(Finder finder) {
		Query query = getSession().createQuery(finder.getRowCountHql());
		finder.setParamsToQuery(query);
		return ((Number) query.iterate().next()).intValue();
	}
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
	 * 通过count查询获得本次查询所能获得的对象总数.
	 * 
	 * @return page对象中的totalCount属性将赋值.
	 */
	@SuppressWarnings("unchecked")
	protected int countQueryResult(Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;
		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();
		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) MyBeanUtils.getFieldValue(impl,
					"orderEntries");
			MyBeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			log.error("不可能抛出的异常:{}", e.getMessage());
		}
		// 执行Count查询
		int totalCount = (Integer) c.setProjection(Projections.rowCount())
				.uniqueResult();
		if (totalCount < 1) {
			return 0;
		}
		// 将之前的Projection和OrderBy条件重新设回去
		c.setProjection(projection);
		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			MyBeanUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			log.error("不可能抛出的异常:{}", e.getMessage());
		}
		return totalCount;
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

	public void refresh(Object entity) {
		getSession().refresh(entity);
	}

	public Object updateDefault(Object entity) {
		return updateByUpdater(Updater.create(entity));
	}

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

	/**
	 * 批量操作方法
	 * @param sql
	 * @param value
	 * @return
	 */
	protected int updateAndDelBeanForSQL(String sql, Object... value) {
		Query query = this.getSession().createQuery(sql);
		if(value!=null){
			for(int i=0,n=value.length;i<n;i++){
				query.setParameter(i+1, value[i]);
			}
		}
		return new Integer(query.executeUpdate());
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



	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public T createNewEntiey() {
		try {
			return getPersistentClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("不能创建实体对象："
					+ getPersistentClass().getName());
		}
	}

	@SuppressWarnings("unchecked")
	private ClassMetadata getCmd(Class clazz) {
		return (ClassMetadata) sessionFactory.getClassMetadata(clazz);
	}

	public static final NotBlankPropertySelector NOT_BLANK = new NotBlankPropertySelector();

	/**
	 * 不为空的EXAMPLE属性选择方式
	 * 
	 * @author liufang
	 * 
	 */
	static final class NotBlankPropertySelector implements PropertySelector {
		private static final long serialVersionUID = 1L;

		public boolean include(Object object, String property, Type type) {
			return object != null
					&& !(object instanceof String && StringUtils
							.isBlank((String) object));
		}
	}
	
    /** This just catches and wraps IllegalArgumentException. */
    private Object invokeMethod(
                        Method method, 
                        Object bean, 
                        Object[] values) 
                            throws
                                IllegalAccessException,
                                InvocationTargetException {
        try {
            
            return method.invoke(bean, values);
        
        } catch (IllegalArgumentException e) {
            
            log.error("Method invocation failed.", e);
            throw new IllegalArgumentException(
                "Cannot invoke " + method.getDeclaringClass().getName() + "." 
                + method.getName() + " - " + e.getMessage());
            
        }
    }





}
