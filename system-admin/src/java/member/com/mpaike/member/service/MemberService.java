package com.mpaike.member.service;

import com.fins.gt.server.GridServerHandler;
import com.mpaike.member.model.Member;
/**
 * 
 * @author 陈海峰
 * @createDate 2011-2-25 下午12:56:01
 * @description 会员Service
 */
public interface MemberService {
	
	  public static String ID_NAME = "memberService";
	  
	  public  GridServerHandler listToGrid(GridServerHandler handler, Member member);
	  
	  public void remove(Long[] id, Long type);
	  
	  public Member get(Long id);

}
