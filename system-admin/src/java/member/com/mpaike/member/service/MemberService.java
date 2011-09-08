package com.mpaike.member.service;

import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.member.model.Member;
/**
 * 
 * @author 陈海峰
 * @createDate 2011-2-25 下午12:56:01
 * @description 会员Service
 */
public interface MemberService {
	
	  public static String ID_NAME = "memberService";
	  
	  public List<Member> find(Pagination p,OrderBy ob);
	  
	  public void remove(Long[] id, Long type);
	  
	  public Member get(Long id);

}
