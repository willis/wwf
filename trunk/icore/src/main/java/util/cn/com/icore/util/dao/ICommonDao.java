package cn.com.icore.util.dao;

import java.util.List;

import com.fins.gt.model.PageInfo;

import cn.com.icore.util.pager.Pager;


@SuppressWarnings("unchecked")
public interface ICommonDao {
	/**
	 * 查询总数
	 * 示例 simpleDao.count("from SystemLog sl where sl.logtype=? and sl.createby=?",new Object[]{0l,1l})
	 * @param "from BeanName bn where bn.x1=?",new Object[]{x}
	 * @return Integer
	 */
	public  Integer count(String bean, Object aobj[]);
	/**
	 * 查询总数
	 * 示例 simpleDao.count("from SystemLog");
	 * @param from BeanName
	 * @return Integer
	 */
	public  Integer count(String s);
	
	public  List find(String s);

	public  List findToMapRow(String s);

	public  List find(String s, Object aobj[]);

	public  List findToMapRow(String s, Object aobj[]);

	public  List find(String s, int i, int j);

	public  List findToMapRow(String s, int i, int j);

	public  List find(String s, Object aobj[], int i, int j);

	public  List findToMapRow(String s, Object aobj[], int i, int j);

	public  Object get(Class clazz, Long serializable);

	public  void save(Object obj);

	public  void remove(Class clazz, Long serializable);

	public  void remove(Object obj);

	public  void update(Object obj);

	public  void add(Object obj);
	
	public  List findBySql(String s, Object aobj[], int i, int j);
	
	public  Integer countBySql(String s, Object aobj[]);
	
	public  List find(String s, String s1, Object aobj[], Pager pager);

	public  List find(String s, String s1, Pager pages);

	public  List findBySql(String s, String s1, Pager pager);

	public  List findBySql(String s, String s1, Object aobj[], Pager pager);
	
	public  List find(String s, String s1, Object aobj[], PageInfo pageInfo);

	public  List find(String s, String s1, PageInfo pageInfo);

	
}
