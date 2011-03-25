package cn.com.icore.test;


import java.util.List;

import net.sf.json.JSONArray;

import com.fins.gt.util.JSONUtils;

import cn.com.icore.user.model.SysUser;
import cn.com.icore.user.service.SysUserService;
import cn.com.icore.util.app.ApplictionContext;
import cn.com.icore.util.pager.Pager;
import junit.framework.TestCase;

public class JdbcTemplateTest {
	public SysUserService getSysUserService() {
		return (SysUserService) ApplictionContext.getInstance().getBeanTest(SysUserService.ID_NAME);
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
		
	}
}
