package cn.com.icore.test;


import cn.com.icore.user.model.SysUser;
import cn.com.icore.util.app.ApplictionContext;
import cn.com.icore.util.jdbc.dao.CommonJdbcDao;
import junit.framework.TestCase;

public class JdbcTemplateTest  extends TestCase{
	public void testCreateDb() {
		CommonJdbcDao jdbc =  (CommonJdbcDao) ApplictionContext
		.getInstance().getBean("commonJdbcDao");
		SysUser su = new SysUser();
		su.setAsk("我的出生地是？");
		jdbc.add(su);
		System.out.println(jdbc);
	}
}
