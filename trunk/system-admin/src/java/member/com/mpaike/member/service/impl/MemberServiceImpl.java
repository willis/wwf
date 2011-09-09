package com.mpaike.member.service.impl;

import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.member.model.Member;
import com.mpaike.member.service.MemberService;
import com.mpaike.sys.service.impl.BaseService;

public class MemberServiceImpl extends BaseService implements  MemberService {

	
	

	@Override
	public List<Member> find(Pagination p, OrderBy ob) {

		return this.getMemberDao().findAllPagination(p, ob);
	}

	@Override
	public void remove(Long[] id, Long type) {
		
		this.getMemberDao().remove(id, type);
		
	}

	@Override
	public Member get(Long id) {
		
		return this.getMemberDao().get(id);
	}
}
