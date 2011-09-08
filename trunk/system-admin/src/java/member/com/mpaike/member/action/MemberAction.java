package com.mpaike.member.action;

import com.fins.gt.server.GridServerHandler;
import com.mpaike.member.model.Member;
import com.mpaike.member.service.MemberService;
import com.mpaike.util.ArrayUtil;
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
	public MemberService memberService = (MemberService)ApplictionContext.getInstance()
	.getBean(MemberService.ID_NAME);
	private Member member;
	private String ids;
	private Long status;
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 
	 * @author 陈海峰
	 * @createDate 2011-2-25 下午12:56:26
	 * @description 会员列表
	 */
	public void list(){
		GridServerHandler handler = new GridServerHandler(request, response);
		member = new Member();
		member.setName(ParamHelper.getStr(request, "name", null));
		member.setUsername(ParamHelper.getStr(request, "username", null));
		member.setStatus(ParamHelper.getLongParamter(request, "status", -1l));
		memberService.listToGrid(handler, member);
		handler.printLoadResponseText();
	}
	/**
	 * 
	 * @author 陈海峰
	 * @createDate 2011-2-25 下午12:56:53
	 * @description 会员操作
	 */
	public void removeByIds(){
		
		Long[] longValue =  ArrayUtil.toLongArray(ids,",");
		memberService.remove(longValue,status);
		if(status==3)
			super.printSuccessJson("删除成功！");
		if(status==2)
			super.printSuccessJson("禁用成功！");
		if(status==1)
			super.printSuccessJson("还原成功！");
	}
	
	public String getById(){
		member = memberService.get(id);
		return "getById";
	}
	public void setMember(Member member) {
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
