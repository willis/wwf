package com.mpaike.member.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.fins.gt.server.GridServerHandler;
import com.mpaike.member.model.Member;
import com.mpaike.member.service.MemberService;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.dao.GtGridCommonDao;

@SuppressWarnings("unchecked")
public class MemberServiceImpl extends GtGridCommonDao implements MemberService {

	public GridServerHandler listToGrid(
			GridServerHandler handler, Member member) {

		StringBuffer sql = new StringBuffer();
		List params = new ArrayList();
		createSQLWhere(member, sql, params);
		if (sql.length() > 0)
			sql.insert(0, " where ");
		sql.insert(0, " from Member m ");

		String select = " ";
		return super.list(handler, Member.class, sql.toString(), select
				+ sql.toString(), params.toArray());
	}
	public void createSQLWhere(Member member, StringBuffer sql, List params) {
		if (member.getUsername() != null
				&& !ParamHelper.isEmpty(member.getUsername())) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" m.username like ? ");
			params.add((new StringBuilder("%")).append(
					member.getUsername().trim()).append("%").toString());
		}
		if (member.getName() != null
				&& !ParamHelper.isEmpty(member.getName())) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append(" m.name like ? ");
			params.add((new StringBuilder("%")).append(
					member.getName().trim()).append("%").toString());
		}
		if (member.getStatus() != null && member.getStatus() != -1) {
			if (sql.length() > 0)
				sql.append(" and ");
			sql.append((new StringBuilder(" m.status= ")).append(
					member.getStatus()).toString());
		}

	}
	public void remove(Long[] id, Long status) {
		
		for (int i = 0, n = id.length; i < n; i++) {
			
			super.getHibernateTemplate().bulkUpdate("update Member m set m.status=? where m.id=?",new Object[]{status,id[i]});
		}

		
	}
}
