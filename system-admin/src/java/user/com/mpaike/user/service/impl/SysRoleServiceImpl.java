package com.mpaike.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.fins.gt.server.GridServerHandler;
import com.mpaike.user.model.SysMenu;
import com.mpaike.user.model.SysPopedom;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.service.SysRoleService;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.dao.GtGridCommonDao;


@SuppressWarnings("unchecked")
public class SysRoleServiceImpl extends GtGridCommonDao implements
		SysRoleService {


	public GridServerHandler listToGrid(GridServerHandler handler, SysRole role) {
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		createSQLWhere(role, sql, params);
		if (sql.length() > 0)
			sql.insert(0, " where ");
		sql.insert(0, " from SysRole u ");

		String select = " select new SysRole(u.id,u.name,u.describe) ";
		return super.list(handler, SysRole.class, sql.toString(), select
				+ sql.toString(), params.toArray());
	}

	public List<SysMenu> getSysMenusByRoleId(long roleId) {
		return super
				.find(" select new SysMenu(d.id,d.name,d.description,d.orderBy,d.img,d.link,d.alias) from SysMenu d inner join  d.sysRoles as e where e.id=  "
						+ roleId);
	}

	public void createSQLWhere(SysRole role, StringBuffer sql, List params) {
		if (role.getName() != null && !ParamHelper.isEmpty(role.getName())) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" u.name like ? ");
			params.add("%" + role.getName().trim() + "%");
		}
		if (role.getDescribe() != null
				&& !ParamHelper.isEmpty(role.getDescribe())) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" u.describe like ? ");
			params.add("%" + role.getDescribe().trim() + "%");
		}
	}

	public List<SysRole> getByGrid(GridServerHandler handler, SysRole role) {
		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		createSQLWhere(role, sql, params);
		if (sql.length() > 0)
			sql.insert(0, " where ");
		sql.insert(0, " from SysRole u ");

		String select = " select new SysRole(u.id,u.name,u.describe) ";
		return super.list(handler, sql.toString(), select + sql, params
				.toArray());
	}

	public SysRole getSysRole(long id) {
		return (SysRole) super.get(SysRole.class, id);
	}

	public void removeSysRole(long id) {
		super.remove(SysRole.class, id);
	}

	public void removeSysRole(SysRole sysRole) {
		super.remove(sysRole);
	}

	public void saveSysRole(SysRole sysRole) {
		super.save(sysRole);
	}

	public void updateSysRole(SysRole sysRole) {
		super.save(sysRole);
	}

	public int count() {
		return super.count("from SysRole", null).intValue();
	}

	public List<SysRole> getSysRoles(int pageSize, int Pg) {
		return super.find("from SysRole order by id", null, pageSize, Pg);
	}

	public void addSysMenu(long sysroleid, String[] cs) {
		SysRole sysRole = (SysRole) super.get(SysRole.class, Long
				.valueOf(sysroleid));
		sysRole.getSysMenus().clear();
		for (int i = 0; i < cs.length; i++){
	
			sysRole.getSysMenus().add(
					(SysMenu) super.get(SysMenu.class, new Long(cs[i])));
			
		}
		
	}

	public void addSysPopedom(long sysroleid, String[] cs) {
	    SysRole sysRole = (SysRole)super.get(SysRole.class, Long.valueOf(sysroleid));
	    for (int i = 0; i < cs.length; i++)
	      sysRole.getSysPopedoms().add((SysPopedom)super.get(SysPopedom.class, new Long(cs[i])));
		
	}

	public GridServerHandler listCheckPopedomsToGrid(
			GridServerHandler handler, SysRole role) {
		   StringBuffer sql = new StringBuffer();
		    List params = new ArrayList();
		    createSQLWhere(role, sql, params);
		    if (sql.length() > 0)
		      sql.insert(0, " and ");
		    sql.insert(0, " from SysPopedom u inner join u.sysRoles as r where r.id= " + role.getId());

		    String select = " select new SysPopedom(u.id,u.code,u.describe) ";
		    return super.list(handler, SysPopedom.class, sql.toString(), select + sql.toString(), params.toArray());
	}

	public GridServerHandler listNotCheckPopedomsToGrid(
			GridServerHandler handler, SysRole role) {
		StringBuffer sql = new StringBuffer();
	    List params = new ArrayList();
	    createSQLWhere(role, sql, params);
	    if (sql.length() > 0)
	      sql.append(" and ");
	    sql.append("  u.id not in (select n.id from SysPopedom n inner join n.sysRoles as r where r.id=" + role.getId() + ") ");

	    sql.insert(0, " from SysPopedom u  where ");

	    String select = " select new SysPopedom(u.id,u.code,u.describe) ";
	    return super.list(handler, SysPopedom.class, sql.toString(), select + sql.toString(), params.toArray());
	}

	public void removeSysPopedom(long sysroleid, String[] cs) {
		SysRole sysRole = (SysRole)super.get(SysRole.class, Long.valueOf(sysroleid));
	    for (int i = 0; i < cs.length; i++)
	      sysRole.getSysPopedoms().remove((SysPopedom)super.get(SysPopedom.class, new Long(cs[i])));
		
	}
	public List<SysRole> getSysRoleList(String name) {
		return super.find("from SysRole sr where sr.name=?", new Object[]{name.trim()});
	}

	
}
