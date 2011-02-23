package com.mpaike.user.action;

import com.fins.gt.server.GridServerHandler;
import com.mpaike.user.model.SysPopedom;
import com.mpaike.user.service.SysPopedomService;
import com.mpaike.util.MyBeanUtils;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.app.ApplictionContext;
import com.mpaike.util.app.BaseAction;


public class SysPopedomAction extends BaseAction {
	private SysPopedom sysPopedom;
	private Long id;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SysPopedomService sysPopedomService = (SysPopedomService) ApplictionContext
			.getInstance().getBean(SysPopedomService.ID_NAME);

	public void list() {

		GridServerHandler handler = new GridServerHandler(request, response);

		sysPopedom = new SysPopedom();
		sysPopedom.setCode(ParamHelper.getStr(request, "code", null));
		sysPopedom.setDescribe(ParamHelper.getStr(request, "describe", null));
		sysPopedomService.listToGrid(handler, sysPopedom);

		handler.printLoadResponseText();
	}

	public void del() {
		String[] cs = request.getParameterValues("c");

		if (cs == null)
			super.printSuccessJson(response, "请选择要删除的权限代码！");
		for (String c : cs) {
			if (!"".equals(c.trim())) {
				sysPopedomService.removeSysPopedom(new Long(c));
			}
		}
		super.printSuccessJson(response, "删除成功！");

	}

	public void save() {
		String result = "";
		if (sysPopedom.getId() == null) {
			result = "添加成功!";
		} else {
			SysPopedom target = sysPopedomService.getSysPopedom(sysPopedom
					.getId());
			MyBeanUtils.fillForNotNullObject(target, sysPopedom);
			sysPopedom = target;
			result = "修改成功!";
		}
		sysPopedomService.saveSysPopedom(sysPopedom);
		super.printSuccessJson(response, result);
	}

	public String edit() {
		sysPopedom = sysPopedomService.getSysPopedom(id);
		return "edit";
	}

	public SysPopedom getSysPopedom() {
		return sysPopedom;
	}

	public void setSysPopedom(SysPopedom sysPopedom) {
		this.sysPopedom = sysPopedom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
