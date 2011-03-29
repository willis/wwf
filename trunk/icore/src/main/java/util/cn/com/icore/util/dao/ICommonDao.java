package cn.com.icore.util.dao;

import java.util.List;

import com.fins.gt.model.PageInfo;

import cn.com.icore.util.pager.Pager;


@SuppressWarnings("unchecked")
public interface ICommonDao {
	/**
	 * hql查询总数
	 * 示例 simpleDao.count("from SystemLog sl where sl.logtype=? and sl.createby=?",new Object[]{0l,1l});
	 * @param "from BeanName bn where bn.x1=?",new Object[]{x}
	 * @return Integer
	 */
	public  Integer count(String bean, Object aobj[]);
	/**
	 * hql查询总数
	 * 示例 simpleDao.count("from SystemLog");
	 * @param "from BeanName"
	 * @return Integer
	 */
	public  Integer count(String s);
	/**
	 * hql查询总数
	 * 示例 simpleDao.find("from SystemLog");
	 * @param "from BeanName"
	 * @return List<T>
	 */
	public  List find(String s);
	/**
	 * hql查询返回Map行记录 
	 * 示例 simpleDao.findToMapRow("Select sl.id from SystemLog  sl");
	 * @param "Select bn.x1 from BeanName  bn"
	 * @return List
	 */
	public  List findToMapRow(String s);
	/**
	 * hql取得结果集 
	 * 示例 simpleDao.find("from SystemLog sl where sl.logtype=? and sl.createby=?",new Object[]{0l,1l});
	 * @param "from BeanName bn where bn.x1=?",new Object[]{x}
	 * @return List<T>
	 */
	public  List find(String s, Object aobj[]);
	/**
	 * hql查询返回Map行记录 
	 * 示例 simpleDao.findToMapRow("Select sl.logtype,sl.createby from SystemLog  sl where  sl.logtype=? and sl.createby=? ",new Object[]{0l,1l});
	 * @param "Select bn.x1,bn.x2 from BeanName bn where bn.x1=? and bn.x2=?",new Object[]{x1,x2}
	 * @return List
	 */
	public  List findToMapRow(String s, Object aobj[]);
	/**
	 * hql分页取得结果集
	 * 示例 simpleDao.find("from SystemLog sl ",0,10);
	 * @param "from BeanName bn",num1,num2
	 * @return List<T>
	 */
	public  List find(String s, int i, int j);
	/**
	 * hql分页查询返回Map行记录 
	 * 示例 simpleDao.findToMapRow("Select sl.id from SystemLog  sl",0,10);
	 * @param "Select bn.x1 from BeanName  bn",num1,num2
	 * @return List
	 */
	public  List findToMapRow(String s, int i, int j);
	/**
	 * hql分页取得结果集 
	 * 示例 simpleDao.find("from SystemLog sl where sl.logtype=? and sl.createby=?",new Object[]{0l,1l},0,10);
	 * @param "from BeanName bn where bn.x1=?",new Object[]{x},num1,num2
	 * @return List<T>
	 */
	public  List find(String s, Object aobj[], int i, int j);
	/**
	 * hql分页查询返回Map行记录 
	 * 示例 simpleDao.findToMapRow("Select sl.logtype,sl.createby from SystemLog  sl where  sl.logtype=? and sl.createby=? ",new Object[]{0l,1l},0,10);
	 * @param "Select bn.x1,bn.x2 from BeanName bn where bn.x1=? and bn.x2=?",new Object[]{x1,x2},num1,num2
	 * @return List
	 */
	public  List findToMapRow(String s, Object aobj[], int i, int j);
	/**
	 * 通过id取得实体类的实例
	 * 示例 simpleDao.get(SystemLog.class, 1l);
	 * @param BeanName.class,num
	 * @return Object
	 */
	public  Object get(Class clazz, Long serializable);
	/**
	 * 保存实体类的实例。根据不同情况执行insert或update
	 * 示例 simpleDao.get(new System(1l));
	 * @param  new Bean();
	 * @return void
	 */
	public  void save(Object obj);
	/**
	 * 删除实体类的指定id的记录
	 * 示例 simpleDao.remove(SystemLog.class, 1l);
	 * @param  BeanName.class,num
	 * @return void
	 */
	public  void remove(Class clazz, Long serializable);
	/**
	 * 删除实体类的记录
	 * 示例 simpleDao.remove(SystemLog.class);
	 * @param  BeanName.class
	 * @return void
	 */
	public  void remove(Object obj);
	/**
	 * 更新实体类的记录
	 * 示例 simpleDao.update(new SystemLog(1l));
	 * @param  Bean
	 * @return void
	 */
	public  void update(Object obj);
	/**
	 * 添加实体类的记录
	 * 示例 simpleDao.add(new SystemLog(1l));
	 * @param  Bean
	 * @return void
	 */
	public  void add(Object obj);
	/**
	 * sql 分页语句查询
	 * 示例 simpleDao.findBySql("select s.* from system_log as s where s.logtype=? and s.createby=?",new Object[]{0l,1l},0,10);
	 * @param "select tn.* from TableName as tn where tn.x1=?",new Object[]{x},num1,num2
	 * @return List
	 */
	public  List findBySql(String s, Object aobj[], int i, int j);
	/**
	 * sql 查询总数
	 * 示例 simpleDao.countBySql("from system_log as sl where sl.logtype=?",new Object[]{0l});
	 * @param "from TableName tn where tn.x1=?",new Object[]{x}
	 * @return Integer
	 */
	public  Integer countBySql(String s, Object aobj[]);
	/**
	 * hql分页取得结果集 
	 * 示例 
	 * Pager pager = new Pager();
	 * pager.setPageSize(10);
	 * pager.setPage(1);
	 * simpleDao.find(" from SystemLog"," from SystemLog s  where order by s.createat desc", pager);
	 * @param "from BeanName","from BeanName bn where order by bn.x1 desc",new Pager()
	 * @return List<T>
	 */
	public  List find(String s, String s1, Object aobj[], Pager pager);
	/**
	 * hql分页取得结果集 
	 * 示例 
	 * Pager pager = new Pager();
	 * pager.setPageSize(10);
	 * pager.setPage(1);
	 * simpleDao.find(" from SystemLog"," from SystemLog s  where order by s.createat desc", pager);
	 * @param "from BeanName","from BeanName bn where order by bn.x1 desc",new Pager()
	 * @return List<T>
	 */
	public  List find(String s, String s1, Pager pages);
	/**
	 * sql分页取得结果集
	 * 示例 
	 * Pager pager = new Pager();
	 * pager.setPageSize(10);
	 * pager.setPage(1);
	 * simpleDao.findBySql("from system_log as s","select s.* from system_log as s",pager);
	 * @param "from TableName tn","from BeanName bn where order by bn.x1 desc",new Pager()
	 * @return List<T>
	 */
	public  List findBySql(String s, String s1, Pager pager);
	/**
	 * sql分页取得结果集
	 * 示例 
	 * Pager pager = new Pager();
	 * pager.setPageSize(10);
	 * pager.setPage(1);
	 * simpleDao.findBySql("from system_log as s where s.logtype=? and s.createby=?","select s.* from system_log as s where s.logtype=? and s.createby=?",new Object[]{0l,1l},pager)
	 * @param "from TableName tn where tn.x1=? and tn.x2=?","select tn.* from tableName tn where tn.x1=? and tn.x2=? order by tn.x1 desc",new Object[]{x1,x2},new Pager()
	 * @return List<T>
	 */
	public  List findBySql(String s, String s1, Object aobj[], Pager pager);
	/**
	 * hql分页取得结果集 用于Gt-Grid
	 * 示例 
	 * PageInfo pagerInfo = new PageInfo();
	 * pagerInfo.setPageSize(10);
	 * pagerInfo.setPage(1);
	 * simpleDao.find(" from SystemLog s  where s.logtype=? and s.createby=?"," from SystemLog s  where s.logtype=? and s.createby=? order by s.createat desc",new Object[]{0l,1l}, pageInfo);
	 * @param "from BeanName bn where bn.x1=? and bn.x2=?","from BeanName bn where bn.x1=? and bn.x2=? order by bn.x1 desc",new PageInfo()
	 * @return List<T>
	 */
	public  List find(String s, String s1, Object aobj[], PageInfo pageInfo);
	/**
	 * hql分页取得结果集 用于Gt-Grid
	 * 示例 
	 * PageInfo pagerInfo = new PageInfo();
	 * pagerInfo.setPageSize(10);
	 * pagerInfo.setPage(1);
	 * simpleDao.find(" from SystemLog"," from SystemLog s  where order by s.createat desc", pagerInfo);
	 * @param "from BeanName","from BeanName bn where order by bn.x1 desc",new PageInfo()
	 * @return List<T>
	 */
	public  List find(String s, String s1, PageInfo pageInfo);

	
}
