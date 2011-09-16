package com.mpaike.user.action;

import java.util.ArrayList;

import java.util.List;
import com.fins.gt.server.GridServerHandler;
import com.mpaike.user.model.SysGroup;
import com.mpaike.user.model.SysUser;
import com.mpaike.user.service.SysGroupService;
import com.mpaike.util.MyBeanUtils;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.app.ApplictionContext;
import com.mpaike.util.app.BaseAction;


@SuppressWarnings("unchecked")
public class SysGroupAction extends BaseAction {
	private static final long serialVersionUID = -2286431261254005556L;
	private SysGroup sysGroup;
	private List<String[]> trees = new ArrayList();

	public SysGroupService getSysGroupService() {
		return (SysGroupService) ApplictionContext.getInstance()
				.getBean(SysGroupService.ID_NAME);
	}

	public String tree() {
		long groupRootId = Long.parseLong(super.getAppProps()
				.get("groupRootId").toString());
		sysGroup = getSysGroupService().getSysGroup(Long.valueOf(groupRootId));
		getSysGroupService().getTrees(sysGroup, trees);
		return "sysGroupTree";
	}

	public void getNotCheckUsers() {
		GridServerHandler handler = new GridServerHandler(request, response);
		SysUser searchBean = new SysUser();
		long groupId = ParamHelper.getLongParamter(request, "groupId", -1L);
		getSysGroupService().listNotCheckUsersToGrid(handler, searchBean,
				groupId);
		handler.printLoadResponseText();
	}

	public void addSysUsers() {
		String msg = "";
		long id = ParamHelper.getLongParamter(request, "id", -1L);
		if (id == -1L)
			msg = "请选择组织机构！";
		String[] cs = request.getParameterValues("cs");

		if ((cs == null) || (cs.length == 0))
			msg = "请选择人员列表！";
		if (cs.length == 1)
			cs = cs[0].split(",");
		try {
			for (String c : cs)
				if (!"".equals(c.trim())) {
					getSysGroupService().addSysUserToGroup(
							new Long(c).longValue(), id);
					msg = "添加成功！";
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		super.printSuccessJson(msg);

	}

	public void delSysUsers() {
		String msg = "";
		long id = ParamHelper.getLongParamter(request, "id", -1L);
		if (id == -1L)
			msg = "请选择组织机构！";
		String[] cs = request.getParameterValues("cs");

		if ((cs == null) || (cs.length == 0))
			msg = "请选择人员列表！";
		if (cs.length == 1)
			cs = cs[0].split(",");

		for (String c : cs) {
			if (!"".equals(c.trim())) {
				getSysGroupService().removeSysUserToGroup(
						new Long(c).longValue(), id);
				msg = "删除成功！";
			}
		}
		super.printSuccessJson(msg);
	}

	public void getCheckUsers() {
		GridServerHandler handler = new GridServerHandler(request, response);
		SysUser searchBean = new SysUser();
		long groupId = ParamHelper.getLongParamter(request, "groupId", -1L);
		getSysGroupService().listCheckUsersToGrid(handler, searchBean, groupId);
		handler.printLoadResponseText();
		
	}

	public String getSysGroupInfo() {
		sysGroup = getSysGroupService().getSysGroup(
				ParamHelper.getLongParamter(request, "groupId", -1L));
		return "get";
	}

	public void save() {
		String result = "";
		if (sysGroup.getId() == null) {

			result = "添加成功!";
		} else {

			SysGroup target = getSysGroupService()
					.getSysGroup(sysGroup.getId());

			MyBeanUtils.fillForNotNullObject(target, sysGroup);
			sysGroup = target;

			result = "修改成功!";
		}

		getSysGroupService().saveSysGroup(sysGroup);
		super.printSuccessJson(result);

	}

	public void del() {
		String result = "请先删除子机构！";
		String[] cs = request.getParameterValues("c");
		if (cs == null)
			result = "请选择删除的组织机构!";
		for (String c : cs) {
			if(getSysGroupService().removeSysGroup(new Long(c)))
			{
				result = "删除成功!";
			}else
			{
				
				result = "请先删除子机构内的成员!";
			}
			
		}
		super.printSuccessJson(result);
	}

	public SysGroup getSysGroup() {
		return sysGroup;
	}

	public void setSysGroup(SysGroup sysGroup) {
		this.sysGroup = sysGroup;
	}

	public List<String[]> getTrees() {
		return trees;
	}

	public void setTrees(List<String[]> trees) {
		this.trees = trees;
	}

}
