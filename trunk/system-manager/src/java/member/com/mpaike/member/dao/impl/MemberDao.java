package com.mpaike.member.dao.impl;

import com.mpaike.core.database.hibernate.SpringBaseDaoImpl;
import com.mpaike.member.dao.IMemberDao;
import com.mpaike.member.model.Member;

public class MemberDao extends SpringBaseDaoImpl<Member> implements IMemberDao{

	@Override
	public void remove(Long[] id, Long type) {

		for (int i = 0, n = id.length; i < n; i++) {
			
			this.bulkUpdate("update Member m set m.status=? where m.id=?",new Object[]{type,id[i]});
		}

		
	}

}
