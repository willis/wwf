package com.mpaike.user.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fins.gt.server.GridServerHandler;
import com.mpaike.user.model.SysMenu;
import com.mpaike.user.model.SysPopedom;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.service.SysMenuControl;
import com.mpaike.util.MyBeanUtils;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.app.BaseAction;


@SuppressWarnings("unchecked")
public class SysRoleAction extends BaseAction {

	private static final long serialVersionUID = 1L;


	private SysRole sysRole;
	private long id;
	private List<SysMenu> tree = new ArrayList<SysMenu>();
	private SysMenu rootObj;

	public void list() {
		sysRole = new SysRole();
		sysRole.setName(ParamHelper.getStr(request, "name", null));
		sysRole.setDescribe(ParamHelper.getStr(request, "describe", null));
		List<SysRole> datas = this.getSysRoleService().listToGrid(sysRole,this.pageInfo,this.getOrderby());
		this.printPageList(datas);
	}

	public String listSysMenu() {
		long rootId = Long.parseLong(super.getAppProps().get("sysMenuRootId")
				.toString());
		tree = this.getSysMenuService().getTree(rootId);
		rootObj = tree.remove(0);
		return "menulist";
	}

	public void getCheckPopedoms() throws Exception {


		sysRole = new SysRole();
		sysRole.setId(ParamHelper.getLongParamter(request, "id", -1l));
		List<SysPopedom> datas = this.getSysRoleService().listCheckPopedomsToGrid(sysRole,this.pageInfo);
		this.printPageList(datas);

	}

	public void getNotCheckPopedoms() throws Exception {

		sysRole = new SysRole();
		sysRole.setId(ParamHelper.getLongParamter(request, "id", -1l));
		List<SysPopedom> datas = this.getSysRoleService().listNotCheckPopedomsToGrid(sysRole,this.pageInfo);
		this.printPageList(datas);

	}

	public void addRolePopedoms() throws Exception {

		long id = ParamHelper.getLongParamter(request, "id", -1L);
		if (id == -1L) {
			super.printSuccessJson("请选择权限！");
		}


	    String[] cs = request.getParameterValues("cs");
	    if ((cs == null) || (cs.length == 0))
	    	super.printSuccessJson("请选择权限！");
	    if (cs.length == 1) {
	      cs = cs[0].split(",");
	    }
		this.getSysRoleService().addSysPopedom(id, cs);
		SysMenuControl.getInstance().putRootTree();
		super.printSuccessJson("添加成功！");

	}

	public void delRolePopedoms() throws Exception {

		long id = ParamHelper.getLongParamter(request, "id", -1L);
		if (id == -1L) {
			super.printSuccessJson("请选择权限！");
		}

		String[] cs = request.getParameterValues("cs");
		if ((cs == null) || (cs.length == 0))
	    	super.printSuccessJson("请选择权限！");
	    if (cs.length == 1) {
	      cs = cs[0].split(",");
	    }
		this.getSysRoleService().removeSysPopedom(id, cs);

		super.printSuccessJson("删除成功！");

	}

	public void addSysMenus() {
		try {
			long roleId = ParamHelper.getLongParamter(request, "roleId", -1L);
			if (roleId == -1L)
				super.printSuccessJson( "角色为空不能设置菜单！");
			String[] cs = request.getParameterValues("c");

			if (cs == null) {
				this.getSysRoleService().addSysMenu(roleId, new String[0]);
				super.printSuccessJson("清空了菜单！");
			}

			this.getSysRoleService().addSysMenu(roleId, cs);
			SysMenuControl.getInstance().putRootTree();
			super.printSuccessJson("设置成功！");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void getSysMenuJson() throws IOException {
		long roleId = ParamHelper.getLongParamter(request, "roleId", -1L);
		if (roleId == -1L) {

		}

		List beans = this.getSysRoleService().getSysMenusByRoleId(roleId);
		super.printBeansJson(beans);

	}

	public void del() {
		String result = "";
		String[] cs = request.getParameterValues("ids");
		if (cs == null)
			result = "请选择删除的记录!";
		for (String c : cs) {
			if (!"".equals(c.trim())) {
				this.getSysRoleService().removeSysRole(new Long(c));
			}
			result = "删除成功!";
		}
		super.printSuccessJson(result);
	}

	public void save() {
		String result = "";
		if (sysRole.getId() == null) {
			result = "添加成功!";
		} else {
			SysRole target = this.getSysRoleService().getSysRole(sysRole.getId());
			MyBeanUtils.fillForNotNullObject(target, sysRole);
			sysRole = target;
			result = "修改成功!";
		}
		if(this.getSysRoleService().getSysRoleList(sysRole.getName()).size()>0){
			result = "角色名已重复!";
		}else{
			this.getSysRoleService().saveSysRole(sysRole);
		}
		super.printSuccessJson( result);
	}

	public String getSysRoleInfo() {
		sysRole = this.getSysRoleService().getSysRole(id);
		return "get";
	}

	public SysRole getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<SysMenu> getTree() {
		return tree;
	}

	public void setTree(List<SysMenu> tree) {
		this.tree = tree;
	}

	public SysMenu getRootObj() {
		return rootObj;
	}

	public void setRootObj(SysMenu rootObj) {
		this.rootObj = rootObj;
	}
}
