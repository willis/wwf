package cn.com.icore.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.fins.gt.server.GridServerHandler;

import cn.com.icore.user.model.SysRole;
import cn.com.icore.user.model.SysUser;
import cn.com.icore.user.model.SysUserToSysRole;
import cn.com.icore.user.service.SysUserService;
import cn.com.icore.util.MD5;
import cn.com.icore.util.ParamHelper;
import cn.com.icore.util.dao.hibernate.GtGridCommonDao;
import cn.com.icore.util.pager.Pager;

@SuppressWarnings("unchecked")
public class SysUserServiceImpl extends GtGridCommonDao implements
		SysUserService {

	public boolean save(SysUser sysUser) {

		if ((super.count(" from SysUser u where u.username=? ",
						new Object[] { sysUser.getUsername() }).intValue() > 0)) {
			return false;
		} else {
			super.save(sysUser);
			return true;
		}
	}

	public GridServerHandler listToGrid(GridServerHandler handler, SysUser user) {

		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		createSQLWhere(user, sql, params);

		if (sql.length() > 0)
			sql.insert(0, " where ");
		sql.insert(0, " from SysUser u ");
		String select = " select new SysUser(u.id, u.username, u.truename, u.sex, u.email, u.tel, u.mark, u.ask, u.answer, u.other, u.regtime, u.logintime, u.password, u.status) ";
		
		return super.list(handler, SysUser.class, sql.toString(), select
				+ sql.toString(), params.toArray());
	}

	public void createSQLWhere(SysUser user, StringBuffer sql, List params) {

		if (user.getUsername() != null
				&& !ParamHelper.isEmpty(user.getUsername())) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" u.username like ? ");
			params.add((new StringBuilder("%")).append(
					user.getUsername().trim()).append("%").toString());
		}
		if (user.getTruename() != null
				&& !ParamHelper.isEmpty(user.getTruename())) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" u.truename like ? ");
			params.add((new StringBuilder("%")).append(
					user.getTruename().trim()).append("%").toString());
		}
		if (user.getStatus() != null && user.getStatus().intValue() != -1) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append((new StringBuilder(" u.status= ")).append(
					user.getStatus()).toString());
		}

	}

	public SysUser get(Long id) {

		return (SysUser) super.get(SysUser.class, id);
	}

	public void remove(Long[] id, Long type) {
		for (int i = 0, n = id.length; i < n; i++) {

			super.getHibernateTemplate().bulkUpdate("update SysUser  u set u.status=? where u.id=?",new Object[]{type,id[i]});
		}

	}

	public void add(SysUser sysUser) {
		super.add(sysUser);
	}

	public void changePassword(Long[] id, String password) {

		for (int i = 0, n = id.length; i < n; i++) {
			super.getHibernateTemplate().bulkUpdate("update SysUser u set u.password=? where u.id=?",new Object[]{MD5.toMD5(password),id[i]});
		}
	}

	public GridServerHandler listNotCheckRolesToGrid(GridServerHandler handler,
			SysUser user) {
		StringBuffer sql = new StringBuffer();
		sql
				.insert(
						0,
						"  from SysRole u where u.id not in(  select u.sysRole.id from SysUserToSysRole u where u.sysUser.id= "
								+ user.getId() + ")");
		String select = " select new SysRole(u.id,u.name,u.describe) ";
		return super.list(handler, SysRole.class, sql.toString(), select
				+ sql.toString());
	}

	public GridServerHandler listCheckRolesToGrid(GridServerHandler handler,
			SysUser user) {
		StringBuffer sql = new StringBuffer();

		sql.insert(0, "  from SysUserToSysRole u where u.sysUser.id= "
				+ user.getId());

		String select = " select new SysRole(u.sysRole.id,u.sysRole.name,u.sysRole.describe) ";
		return super.list(handler, SysRole.class, sql.toString(), select
				+ sql.toString());
	}

	public void addSysRole(SysUser sysUser, SysRole sysRole) {
		List l1 = super.find(
				"from SysUserToSysRole s where s.sysUser=? and s.sysRole=?",
				new Object[] { sysUser, sysRole }, -1, -1);
		if (l1.size() == 0) {

			SysUserToSysRole info = new SysUserToSysRole();
			info.setSysRole(sysRole);
			info.setSysUser(sysUser);
			super.save(info);
		}
	}

	public SysUser loginUserByPassword(String username, String password) {
		List beans = super
				.find(
						" from SysUser where username=? and password=? and status=? ",
						new Object[] { username, password, Long.valueOf(0) },
						-1, -1);
		if (beans.isEmpty())
			return null;
		return (SysUser) beans.get(0);
	}
	
	  public List<SysRole> getSysRoles(SysUser sysUser)
	  {
	    List sysRoles = new ArrayList();

	    List l1 = super.find("from SysUserToSysRole s where s.sysUser=?", new Object[] { sysUser }, -1, -1);
	    for (int i = 0; i < l1.size(); i++)
	    {
	      SysUserToSysRole sysUserToRole = (SysUserToSysRole)l1.get(i);
	      sysUserToRole.getSysRole().getSysMenus().size();//
	      sysUserToRole.getSysRole().getSysPopedoms().size();//
	      sysRoles.add(sysUserToRole.getSysRole());
	     
	    }

	    return sysRoles;
	  }

	
	public void removeSysRole(SysUser paramSysUser, SysRole paramSysRole) {
		List l1 = super.find("from SysUserToSysRole s where s.sysUser=? and s.sysRole=?", new Object[] { paramSysUser, paramSysRole }, -1, -1);
	    for (int i = 0; i < l1.size(); i++)
	    {
	      super.remove(l1.get(i));
	    }
		
	}
	
	public List<SysUser> find(Pager pager) {
		
		String select = " select new SysUser(u.id, u.username, u.truename, u.sex, u.email, u.tel, u.mark, u.ask, u.answer, u.other, u.regtime, u.logintime, u.password, u.status) ";
		
		String queryString =" from SysUser u";
	
		return super.find(select+queryString,null,pager.getPageSize(),pager.getPage());
	}


}
