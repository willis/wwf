package cn.com.icore.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.fins.gt.server.GridServerHandler;

import cn.com.icore.user.model.SysGroup;
import cn.com.icore.user.model.SysGroupToSysRole;
import cn.com.icore.user.model.SysRole;
import cn.com.icore.user.model.SysUser;
import cn.com.icore.user.service.SysGroupService;
import cn.com.icore.util.dao.hibernate.GtGridCommonDao;

@SuppressWarnings("unchecked")
public class SysGroupServiceImpl extends GtGridCommonDao implements
		SysGroupService {
	 
	public GridServerHandler listCheckUsersToGrid(GridServerHandler handler,
			SysUser user, long groupId) {
		StringBuffer sql = new StringBuffer();

		if (sql.length() > 0)
			sql.insert(0, " and ");
		sql.insert(0,
				" from SysUser u inner join u.sysGroups as g where g.id= "
						+ groupId + " and u.status=" + 0);

		String select = " select new  SysUser(u.id, u.username, u.truename, u.sex, u.email, u.tel, u.mark, u.ask, u.answer, u.other, u.regtime, u.logintime, u.password, u.status)";

		return super.list(handler, SysUser.class, sql.toString(), select
				+ sql.toString());
	}

	public List<Map> getSysGroupsToMap(long rootId) {
		return super
				.findToMapRow(" select g.id as id,g.name as name,g.describe as describe,g.orderby from SysGroup g where  g.parentGroup.id="
						+ rootId + " order by g.orderby asc ");
	}

	public List<Map> getSysUserByGroupIdToMap(long groupId) {
		return super
				.findToMapRow(" select u.id as id,u.username as username,u.truename as truename from SysUser u inner join u.sysGroups as g where g.id="
						+ groupId
						+ " and u.status="
						+ 0
						+ " order by u.id desc ");
	}

	public GridServerHandler listNotCheckUsersToGrid(GridServerHandler handler,
			SysUser user, long groupId) {
		StringBuffer sql = new StringBuffer();

		if (sql.length() > 0)
			sql.append(" and ");
		sql
				.append("  u.id not in (select n.id from SysUser n  inner join n.sysGroups as g where g.id="
						+ groupId + ") and   u.status=" + 0);

		sql.insert(0, " from SysUser u  where ");

		String select = " select new SysUser(u.id, u.username, u.truename, u.sex, u.email, u.tel, u.mark, u.ask, u.answer, u.other, u.regtime, u.logintime, u.password, u.status) ";

		return super.list(handler, SysUser.class, sql.toString(), select
				+ sql.toString());
	}

	public SysGroup getSysGroup(Long id) {
		return (SysGroup) super.get(SysGroup.class, id);
	}

	public boolean removeSysGroup(Long id) {
		boolean result = false;
		try{
		super.getHibernateTemplate().bulkUpdate(
				"delete from SysGroup s where s.parentGroup.id=" + id);
		super.remove(SysGroup.class, id);
		result = true;
		}catch(Exception e){
			result = false;
			
		}
		return result;
	}

	public void removeSysGroup(SysGroup sysGroup) {
		super.remove(sysGroup);
	}

	public void saveSysGroup(SysGroup sysGroup) {
		super.save(sysGroup);
	}


	public void addSysUserToGroup(long userid, long groupid) {
		SysUser sysUser = (SysUser) super.get(SysUser.class, new Long(userid));
		SysGroup sysGroup = (SysGroup) super.get(SysGroup.class, new Long(
				groupid));

		sysGroup.getSysUsers().add(sysUser);
	}

	public void removeSysUserToGroup(long userid, long groupid) {
		SysUser sysUser = (SysUser) super.get(SysUser.class, new Long(userid));
		SysGroup sysGroup = (SysGroup) super.get(SysGroup.class, new Long(
				groupid));
		sysUser.getSysGroups().remove(sysGroup);
		sysGroup.getSysUsers().remove(sysUser);
	}

	public List<SysGroup> getSysGroups(Long parentid) {
		Session sesison = super.getSession();
		StringBuffer sb = new StringBuffer("from SysGroup  ");
		if (parentid == null) {
			sb.append(" where parentGroup = null");
		} else {
			sb.append(" where parentGroup.id=");
			sb.append(parentid);
		}
		sb.append(" order by orderby desc");
		Query query = sesison.createQuery(sb.toString());
		return query.list();
	}

	public List<SysGroup> findSysGroupByName(String name) {
		return super.find("from SysGroup g where g.name=?",
				new String[] { name }, -1, -1);
	}

	public List<String[]> getTrees(SysGroup sysGroup, List<String[]> trees) {
		Set<SysGroup> childGroups = sysGroup.getChildGroups();
     
		for (SysGroup cg : childGroups) {
		
			String nodeId = cg.getId().toString();
			String nodeName = cg.getName();
			String nodePId = cg.getParentGroup().getId().toString();
			if (trees == null)
				trees = new ArrayList();
			trees.add(new String[] { nodeId, nodeName, nodePId });

			if (!cg.getChildGroups().isEmpty()) {
				getTrees(cg, trees);
			}
		}

		return trees;
	}

	public List<SysRole> getSysRoles(SysGroup sysGroup) {
		List sysRoles = new ArrayList();

	    List l1 = super.find("from SysGroupToSysRole s where s.sysGroup=?", new Object[] { sysGroup }, -1, -1);
	    for (int i = 0; i < l1.size(); i++)
	    {
	      SysGroupToSysRole sysGroupToPopedom = (SysGroupToSysRole)l1.get(i);
	      sysRoles.add(sysGroupToPopedom.getSysRole());
	    }
	    return sysRoles;
	}

}
