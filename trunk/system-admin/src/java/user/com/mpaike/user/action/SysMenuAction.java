package com.mpaike.user.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mpaike.user.model.SysMenu;
import com.mpaike.user.service.SysMenuControl;
import com.mpaike.user.service.SysMenuService;
import com.mpaike.util.MyBeanUtils;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.app.ApplictionContext;
import com.mpaike.util.app.BaseAction;


public class SysMenuAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SysMenu> tree= new ArrayList<SysMenu>();
    private SysMenu rootObj;
    private SysMenu sysMenu;
	public String listSysMenu() {
		long rootId = Long.parseLong(super.getAppProps().get("sysMenuRootId")
				.toString());
	    tree = this.getSysMenuService().getTree(rootId);

	    rootObj = tree.remove(0);
		return "menulist";
	}
	
	public String toAddSysMenu(){
		long pid = ParamHelper.getLongParamter(request, "pid", -1L);
		rootObj = this.getSysMenuService().getMenu(pid);
		return "edit";
	}
	
	public String toEditSysMenu(){
		long id = ParamHelper.getLongParamter(request, "id", -1L);
		sysMenu = this.getSysMenuService().getMenu(id);
		rootObj = sysMenu.getParentObj();
		return "edit";
		
	}
	
	public void del(){
		
		String result = "";
		 long id = ParamHelper.getLongParamter(request, "id", -1L);
		 if (id == -1L)
			 result ="请选择要删除的数据!";
		 
		  if(getSysMenuService().delMenu(id)){
			  SysMenuControl.getInstance().putRootTree();
			  result ="删除数据成功!";
		  }else{
			  result ="删除数据失败!";
		  }
		    super.printSuccessJson(result);
	}
	public void save(){
		String result = "";
		boolean type = false;
		if(sysMenu.getId()==null){
			result ="添加成功!";
			type = false;
		}else{
			
			SysMenu target = getSysMenuService().getMenu(sysMenu.getId());
			
		    MyBeanUtils.fillForNotNullObject(target, sysMenu);
		    sysMenu = target;
			result ="修改成功!";
			type = true;
		}
		sysMenu.setOrderBy(sysMenu.getOrderBy()!=null?sysMenu.getOrderBy():0l);
		sysMenu.setCurDate(new Date());
		getSysMenuService().save(sysMenu);
		if(type)
		SysMenuControl.getInstance().putRootTree();
		super.printSuccessJson(result);
		
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

	public SysMenu getSysMenu() {
		return sysMenu;
	}

	public void setSysMenu(SysMenu sysMenu) {
		this.sysMenu = sysMenu;
	}
}
