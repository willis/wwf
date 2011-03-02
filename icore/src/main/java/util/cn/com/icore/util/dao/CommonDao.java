package cn.com.icore.util.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.com.icore.util.pager.Pager;



@SuppressWarnings("unchecked")
public abstract class CommonDao extends HibernateDaoSupport implements ICommonDao{
	
	protected final Log logger = LogFactory.getLog(getClass());

	public CommonDao()
	{
	}

	public  Integer count(String sql)
	{
		return count(sql, new Object[0]);
	}

	public  List find(String queryString, int pageCount, int pageNum)
	{
		return find(queryString, new Object[0], pageCount, pageNum);
	}

	public List findToMapRow(String queryString, int pageCount, int pageNum)
	{
		return findToMapRow(queryString, new Object[0], pageCount, pageNum);
	}

	public  List find(String queryString)
	{
		return find(queryString, new Object[0], -1, -1);
	}

	public  List findToMapRow(String queryString)
	{
		return findToMapRow(queryString, new Object[0], -1, -1);
	}

	public  List find(String countString, String queryString, Object args[], Pager pager)
	{
		int count = count(countString, args).intValue();
		pager.setReCount(count);
		pager.account();
		if (count == 0)
			return Collections.EMPTY_LIST;
		else
			return find(queryString, args, pager.getPageSize(), pager.getPage());
	}

	public  List findToMapRow(String countString, String queryString, Object args[], Pager pager)
	{
		int count = count(countString, args).intValue();
		pager.setReCount(count);
		pager.account();
		if (count == 0)
			return Collections.EMPTY_LIST;
		else
			return findToMapRow(queryString, args, pager.getPageSize(), pager.getPage());
	}

	public  List find(String countString, String queryString, Pager pager)
	{
		return find(countString, queryString, new Object[0], pager);
	}

	public  List findToMapRow(String countString, String queryString, Pager pager)
	{
		return findToMapRow(countString, queryString, new Object[0], pager);
	}

	public final Integer count(final String queryString, final Object args[])
	{
		return (Integer)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
				throws HibernateException
			{
				String newQueryString = (new StringBuilder("select count(*) ")).append(queryString).toString();
				Query query = session.createQuery(newQueryString);
				for (int i = 0; args != null && i < args.length; i++)
					query.setParameter(i, args[i]);

				return new Integer(query.iterate().next().toString());
			}

			
			
		});
	}

	public  List find(String queryString, Object args[])
	{
		return find(queryString, args, -1, -1);
	}

	public List findToMapRow(String queryString, Object args[])
	{
		return findToMapRow(queryString, args, -1, -1);
	}

	public  List find(final String queryString, final Object args[], final int pageCount, final int pageNum)
	{
		return getHibernateTemplate().executeFind(new HibernateCallback() {



			public Object doInHibernate(Session session)
				throws HibernateException
			{
				Query query = session.createQuery(queryString);
				for (int i = 0; args != null && i < args.length; i++)
					query.setParameter(i, args[i]);

				if (pageCount > 0 && pageNum > 0)
				{
					query.setMaxResults(pageCount);
					query.setFirstResult((pageNum - 1) * pageCount);
				}
				return query.list();
			}

			
		
		});
	}

	public  List findToMapRow(final String queryString, final Object args[], final int pageCount, final int pageNum)
	{
		return getHibernateTemplate().executeFind(new HibernateCallback() {


			public Object doInHibernate(Session session)
				throws HibernateException
			{
				Query query = session.createQuery(queryString);
				
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				for (int i = 0; args != null && i < args.length; i++)
					query.setParameter(i, args[i]);

				if (pageCount > 0 && pageNum > 0)
				{
					query.setMaxResults(pageCount);
					query.setFirstResult((pageNum - 1) * pageCount);
				}
				return query.list();
			}

			
		
		});
	}

	public  Object get(Class clazz, Long id)
	{
		Object obj = getHibernateTemplate().get(clazz, id);
		if (obj == null)
			throw new ObjectRetrievalFailureException(clazz, id);
		else
			return obj;
	}

	public final  Object load(Class clazz, Serializable id)
	{
		Object obj = getHibernateTemplate().load(clazz, id);
		return obj;
	}

	public   void save(Object object)
	{
	
		if (object instanceof IBeanPrimaryKey) {
			try {
				if (((IBeanPrimaryKey) object).getId() == null
						|| ((IBeanPrimaryKey) object).getId().longValue() == 0) {
					((IBeanPrimaryKey) object).setId(Long.valueOf(SequenceManager
							.nextID(100)));
				}
				getHibernateTemplate().saveOrUpdate(object);
				
			} catch (Exception dae) {
			    System.out.println(dae);
			}
		} else {
			throw new ClassCastException("not implements IBeanPrimaryKey");
		}
		
	}

	public  void remove(Class clazz, Long id)
	{
		remove(get(clazz, id));
	}

	public  void remove(Object object)
	{
		getHibernateTemplate().delete(object);
	}

	public  void add(Object object)
	{
		
		if (object instanceof IBeanPrimaryKey) {
			try {
				if (((IBeanPrimaryKey) object).getId() == null
						|| ((IBeanPrimaryKey) object).getId().longValue() == 0) {
					((IBeanPrimaryKey) object).setId(Long.valueOf(SequenceManager
							.nextID(100)));
				}
				getHibernateTemplate().save(object);
				
			} catch (Exception dae) {
				logger.error(dae);

			}
		} else {
			throw new ClassCastException("not implements IBeanPrimaryKey");
		}
	}

	public  void update(Object object)
	{
		super.getHibernateTemplate().update(object);
	}

	public  List findBySql(final String queryString, final Object args[], final int pageCount, final int pageNum)
	{
		return getHibernateTemplate().executeFind(new HibernateCallback() {



			public Object doInHibernate(Session session)
				throws HibernateException
			{
				Query query = session.createSQLQuery(queryString);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				for (int i = 0; args != null && i < args.length; i++)
					query.setParameter(i, args[i]);

				if (pageCount > 0 && pageNum > 0)
				{
					query.setMaxResults(pageCount);
					query.setFirstResult((pageNum - 1) * pageCount);
				}
				return query.list();
			}

			
			
		});
	}

	public  Integer countBySql(final String countString, final Object args[])
	{
		return (Integer)getHibernateTemplate().execute(new HibernateCallback() {


			public Object doInHibernate(Session session)
				throws HibernateException
			{
				String newQueryString = (new StringBuilder("select count(*) ")).append(countString).toString();
				SQLQuery query = session.createSQLQuery(newQueryString);
				for (int i = 0; args != null && i < args.length; i++)
					query.setParameter(i, args[i]);

				return new Integer(query.list().iterator().next().toString());
			}

		
		});
	}

	public   List findBySql(String countString, String queryString, Object args[], Pager pager)
	{
		int count = countBySql(countString, args).intValue();
		pager.setReCount(count);
		pager.account();
		if (count == 0)
			return new ArrayList();
		else
			return findBySql(queryString, args, pager.getPageSize(), pager.getPage());
	}

	public  List findBySql(String countString, String queryString, Pager pager)
	{
		return findBySql(countString, queryString, new Object[0], pager);
	}
  
}
