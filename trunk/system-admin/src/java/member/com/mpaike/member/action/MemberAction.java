package com.mpaike.member.action;

import com.fins.gt.server.GridServerHandler;
import com.mpaike.member.model.Member;
import com.mpaike.member.service.MemberService;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.app.ApplictionContext;
import com.mpaike.util.app.BaseAction;

public class MemberAction extends BaseAction {
	/**
	 * @author 陈海峰
	 * @createDate 2011-2-24 下午03:31:29
	 * @description 
	 */
	private static final long serialVersionUID = 1L;
	private MemberService memberService = (MemberService)ApplictionContext.getInstance()
	.getBean(MemberService.ID_NAME);
	private Member member;
	
	public void list(){
		GridServerHandler handler = new GridServerHandler(request, response);
		member = new Member();
		member.setName(ParamHelper.getStr(request, "name", null));
		member.setUsername(ParamHelper.getStr(request, "username", null));
		member.setStatus(ParamHelper.getIntParamter(request, "status", -1));
		memberService.listToGrid(handler, member);
		handler.printLoadResponseText();
	}


	public void setMember(Member member) {
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

}
