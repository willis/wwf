package com.mpaike.member.service;

import com.fins.gt.server.GridServerHandler;
import com.mpaike.member.model.Member;


public interface MemberService {
	
	  public static String ID_NAME = "memberService";
	  
	  public  GridServerHandler listToGrid(GridServerHandler handler, Member member);

}
