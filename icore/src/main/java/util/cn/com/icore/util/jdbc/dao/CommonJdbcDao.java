package cn.com.icore.util.jdbc.dao;


import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fins.gt.model.PageInfo;



import cn.com.icore.util.MyBeanUtils;
import cn.com.icore.util.dao.IBeanPrimaryKey;
import cn.com.icore.util.dao.ICommonDao;
import cn.com.icore.util.dao.SequenceManager;
import cn.com.icore.util.pager.Pager;

public  class CommonJdbcDao  implements ICommonDao{
	private JdbcTemplate jdbcTemplate;
	protected Log logger = LogFactory.getLog(CommonJdbcDao.class);
	
	@Override
	public final void add(Object o) {

		if (o == null) {
			logger.error("BeanManger manager createBean object not's can null");
		
		}
		
		if (o instanceof IBeanPrimaryKey) {
			try {
				if (((IBeanPrimaryKey) o).getId() == null
						|| ((IBeanPrimaryKey) o).getId().longValue() == 0) {
					((IBeanPrimaryKey) o).setId(Long.valueOf(SequenceManager
							.nextID(100)));
				}
				Map list = MyBeanUtils.describe(o.getClass());
				String table = o.getClass().getName();
				StringBuffer sb = new StringBuffer();
				sb.append("insert into " +table.toLowerCase() );
				Set set = list.entrySet();
				Iterator iterator = set.iterator();
				while(iterator.hasNext()){
					Entry entry = (Entry) iterator.next();
					System.out.println(entry.getValue()+":"+entry.getValue());
				}
				
				List<PropertyDescriptor> lp =MyBeanUtils.propertyDescriptorList(o);
				for (PropertyDescriptor  pd :lp){
					System.out.println(pd.getName());
					try {
						System.out.println(PropertyUtils.getProperty(o, pd.getName()));
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
	
			} catch (DataAccessException dae) {
				logger.error(dae);

			}
		} else {
			throw new ClassCastException("not implements IBeanPrimaryKey");
		}
		o = null;
		
		
	}

	@Override
	public Integer count(String s, Object[] aobj) {
		
		return null;
	}

	@Override
	public Integer count(String s) {
		
		return null;
	}

	@Override
	public Integer countBySql(String s, Object[] aobj) {
		
		return null;
	}

	@Override
	public List find(String s) {
		
		return null;
	}

	@Override
	public List find(String s, Object[] aobj) {
		
		return null;
	}

	@Override
	public List find(String s, int i, int j) {
		
		return null;
	}

	@Override
	public List find(String s, Object[] aobj, int i, int j) {
		
		return null;
	}

	@Override
	public List find(String s, String s1, Object[] aobj, Pager pager) {
		
		return null;
	}

	@Override
	public List find(String s, String s1, Pager pages) {
		
		return null;
	}

	@Override
	public List findBySql(String s, Object[] aobj, int i, int j) {
		
		return null;
	}

	@Override
	public List findBySql(String s, String s1, Pager pager) {
		
		return null;
	}

	@Override
	public List findBySql(String s, String s1, Object[] aobj, Pager pager) {
		
		return null;
	}

	@Override
	public List findToMapRow(String s) {
		
		return null;
	}

	@Override
	public List findToMapRow(String s, Object[] aobj) {
		
		return null;
	}

	@Override
	public List findToMapRow(String s, int i, int j) {
		
		return null;
	}

	@Override
	public List findToMapRow(String s, Object[] aobj, int i, int j) {
		
		return null;
	}

	@Override
	public Object get(Class clazz, Long serializable) {
		
		return null;
	}

	@Override
	public void remove(Class clazz, Long serializable) {
		
		
	}

	@Override
	public void remove(Object obj) {
		
		
	}

	@Override
	public void save(Object obj) {
		
		
	}

	@Override
	public void update(Object obj) {
		
		
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List find(String s, String s1, Object[] aobj, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List find(String s, String s1, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
