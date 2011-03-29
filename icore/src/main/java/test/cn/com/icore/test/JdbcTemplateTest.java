package cn.com.icore.test;


import java.util.ArrayList;
import java.util.List;

import com.fins.gt.model.PageInfo;

import cn.com.icore.sys.model.SystemLog;
import cn.com.icore.user.model.SysUser;
import cn.com.icore.user.service.SysUserService;
import cn.com.icore.util.app.ApplictionContext;
import cn.com.icore.util.dao.ICommonDao;
import cn.com.icore.util.pager.Pager;
import junit.framework.TestCase;

public class JdbcTemplateTest {
	public SysUserService getSysUserService() {
		return (SysUserService) ApplictionContext.getInstance().getBeanTest(SysUserService.ID_NAME);
	}
	public static ICommonDao  getCommonDao() {
		return (ICommonDao) ApplictionContext.getInstance().getBeanTest("commonDao");
	}
	public static  void main(String args[]) {
//		CommonJdbcDao jdbc =  (CommonJdbcDao) ApplictionContext
//		.getInstance().getBean("commonJdbcDao");
//		SysUser su = new SysUser();
//		su.setAsk("我的出生地是？");
//		jdbc.add(su);
//		System.out.println(jdbc);
		JdbcTemplateTest jtt = new JdbcTemplateTest();
		Pager pager = new Pager();
		pager.setPageSize(10);
		pager.setPage(1);
		List<SysUser> data = jtt.getSysUserService().find(pager);
		
		ICommonDao simpleDao = getCommonDao();
		System.out.println(simpleDao.count("from SystemLog"));

		System.out.println(simpleDao.count("from SystemLog sl where sl.logtype=? and sl.createby=?",new Object[]{0l,1l}));
	
		System.out.println(simpleDao.find("from SystemLog"));
		
		System.out.println(simpleDao.findToMapRow("Select sl.id from SystemLog  sl"));
		
		System.out.println(simpleDao.find("from SystemLog sl where sl.logtype=? and sl.createby=?",new Object[]{0l,1l}));
		
		System.out.println(simpleDao.findToMapRow("Select sl.logtype,sl.createby from SystemLog  sl where  sl.logtype=? and sl.createby=? ",new Object[]{0l,1l}));
		
		System.out.println(simpleDao.find("from SystemLog sl ",0,10));
		
		System.out.println(simpleDao.findToMapRow("Select sl.id from SystemLog  sl",0,10));
		
		System.out.println(simpleDao.find("from SystemLog sl where sl.logtype=? and sl.createby=?",new Object[]{0l,1l},0,10));
		
		System.out.println(simpleDao.findToMapRow("Select sl.logtype,sl.createby from SystemLog  sl where  sl.logtype=? and sl.createby=? ",new Object[]{0l,1l},0,10));
		
		System.out.println(simpleDao.countBySql("from system_log as sl where sl.logtype=? and sl.createby=?",new Object[]{0l,1l}));
		
		System.out.println(simpleDao.find(" from SystemLog s  where s.logtype=? and s.createby=?"," from SystemLog s  where s.logtype=? and s.createby=? order by s.createat desc",new Object[]{0l,1l}, pager));
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPageNum(1);
		pageInfo.setPageSize(10);
		System.out.println(simpleDao.find(" from SystemLog s  where s.logtype=? and s.createby=?"," from SystemLog s  where s.logtype=? and s.createby=? order by s.createat desc",new Object[]{0l,1l}, pageInfo));
		//System.out.println(simpleDao.get(SystemLog.class, 1l));
		
		//System.out.println(simpleDao.findBySql("select s.* from system_log as s where s.logtype=? and s.createby=?",new Object[]{0l,1l},0,10));
		
		System.out.println(simpleDao.findBySql("from system_log as s","select s.* from system_log as s",pager));
	}
}
