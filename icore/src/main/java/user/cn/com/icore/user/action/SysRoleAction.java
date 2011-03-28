package cn.com.icore.user.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fins.gt.server.GridServerHandler;

import cn.com.icore.user.model.SysMenu;
import cn.com.icore.user.model.SysRole;
import cn.com.icore.user.service.SysMenuControl;
import cn.com.icore.user.service.SysMenuService;
import cn.com.icore.user.service.SysRoleService;
import cn.com.icore.util.MyBeanUtils;
import cn.com.icore.util.ParamHelper;
import cn.com.icore.util.app.ApplictionContext;
import cn.com.icore.util.app.BaseAction;

@SuppressWarnings("unchecked")
public class SysRoleAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	SysRoleService sysRoleService = (SysRoleService) ApplictionContext
			.getInstance().getBean(SysRoleService.ID_NAME);
	SysMenuService sysMenuService = (SysMenuService) ApplictionContext
			.getInstance().getBean(SysMenuService.ID_NAME);

	private SysRole sysRole;
	private long id;
	private List<SysMenu> tree = new ArrayList<SysMenu>();
	private SysMenu rootObj;

	public void list() {
		GridServerHandler handler = new GridServerHandler(request, response);
		sysRole = new SysRole();
		sysRole.setName(ParamHelper.getStr(request, "name", null));
		sysRole.setDescribe(ParamHelper.getStr(request, "describe", null));
		sysRoleService.listToGrid(handler, sysRole);
		handler.printLoadResponseText();
	}

	public String listSysMenu() {
		long rootId = Long.parseLong(super.getAppProps().get("sysMenuRootId")
				.toString());
		tree = sysMenuService.getTree(rootId);
		rootObj = tree.remove(0);
		return "menulist";
	}

	public void getCheckPopedoms() throws Exception {
		GridServerHandler handler = new GridServerHandler(request, response);

		sysRole = new SysRole();
		sysRole.setId(ParamHelper.getLongParamter(request, "id", -1l));
		sysRoleService.listCheckPopedomsToGrid(handler, sysRole);

		handler.printLoadResponseText();

	}

	public void getNotCheckPopedoms() throws Exception {
		GridServerHandler handler = new GridServerHandler(request, response);

		sysRole = new SysRole();
		sysRole.setId(ParamHelper.getLongParamter(request, "id", -1l));
		sysRoleService.listNotCheckPopedomsToGrid(handler, sysRole);

		handler.printLoadResponseText();

	}

	public void addRolePopedoms() throws Exception {

		long id = ParamHelper.getLongParamter(request, "id", -1L);
		if (id == -1L) {
			super.printSuccessJson(response, "请选择权限！");
		}


	    String[] cs = request.getParameterValues("cs");
	    if ((cs == null) || (cs.length == 0))
	    	super.printSuccessJson(response, "请选择权限！");
	    if (cs.length == 1) {
	      cs = cs[0].split(",");
	    }
		
		sysRoleService.addSysPopedom(id, cs);

		super.printSuccessJson(response, "添加成功！");

	}

	public void delRolePopedoms() throws Exception {

		long id = ParamHelper.getLongParamter(request, "id", -1L);
		if (id == -1L) {
			super.printSuccessJson(response, "请选择权限！");
		}

		String[] cs = request.getParameterValues("cs");
		if ((cs == null) || (cs.length == 0))
	    	super.printSuccessJson(response, "请选择权限！");
	    if (cs.length == 1) {
	      cs = cs[0].split(",");
	    }
		sysRoleService.removeSysPopedom(id, cs);

		super.printSuccessJson(response, "删除成功！");

	}

	public void addSysMenus() {
		try {
			long roleId = ParamHelper.getLongParamter(request, "roleId", -1L);
			if (roleId == -1L)
				super.printSuccessJson(response, "角色为空不能设置菜单！");
			String[] cs = request.getParameterValues("c");

			if (cs == null) {
				sysRoleService.addSysMenu(roleId, new String[0]);
				super.printSuccessJson(response, "清空了菜单！");
			}

			sysRoleService.addSysMenu(roleId, cs);
			SysMenuControl.getInstance().putRootTree();
			super.printSuccessJson(response, "设置成功！");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void getSysMenuJson() throws IOException {
		long roleId = ParamHelper.getLongParamter(request, "roleId", -1L);
		if (roleId == -1L) {

		}

		List beans = sysRoleService.getSysMenusByRoleId(roleId);
		super.printBeansJson(response, beans);

	}

	public void del() {
		String result = "";
		String[] cs = request.getParameterValues("ids");
		if (cs == null)
			result = "请选择删除的记录!";
		for (String c : cs) {
			if (!"".equals(c.trim())) {
				sysRoleService.removeSysRole(new Long(c));
			}
			result = "删除成功!";
		}
		super.printSuccessJson(response, result);
	}

	public void save() {
		String result = "";
		if (sysRole.getId() == null) {
			result = "添加成功!";
		} else {
			SysRole target = sysRoleService.getSysRole(sysRole.getId());
			MyBeanUtils.fillForNotNullObject(target, sysRole);
			sysRole = target;
			result = "修改成功!";
		}
		sysRoleService.saveSysRole(sysRole);
		super.printSuccessJson(response, result);
	}

	public String getSysRoleInfo() {
		sysRole = sysRoleService.getSysRole(id);
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
