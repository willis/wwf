package com.mpaike.user.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mpaike.user.model.SysMenu;
import com.mpaike.user.service.SysMenuControl;
import com.mpaike.util.Message;
import com.mpaike.util.MessageType;
import com.mpaike.util.MyBeanUtils;
import com.mpaike.util.app.BaseAction;


public class SysMenuAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SysMenu> tree= new ArrayList<SysMenu>();
    private SysMenu rootObj;
    private SysMenu sysMenu;
    private Long pid;
    private Long id;
	public String listSysMenu() {
		long rootId = Long.parseLong(super.getAppProps().get("sysMenuRootId")
				.toString());
	    tree = this.getSysMenuService().getTree(rootId);

	    rootObj = tree.remove(0);
		return "menulist";
	}
	
	public String toAddSysMenu(){
		rootObj = this.getSysMenuService().getMenu(pid);
		return "edit";
	}
	
	public String toEditSysMenu(){
		sysMenu = this.getSysMenuService().getMenu(id);
		rootObj = sysMenu.getParentObj();
		return "edit";
		
	}
	
	public void del(){
        Message message = new Message();

		String result = "";
		 if (id == -1L)
			 result ="请选择要删除的数据!";
		 
		  if(getSysMenuService().delMenu(id)){
			  SysMenuControl.getInstance().putRootTree();
			  result ="删除数据成功!";
              message.setStatusCode(MessageType.SUCCESS.value);
		  }else{
			  result ="删除数据失败!";
              message.setStatusCode(MessageType.ERROR.value);
		  }


        message.setMessage(result);
        this.printMessage(message);

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
        Message message = new Message();
        message.setStatusCode(MessageType.SUCCESS.value);
        message.setMessage(result);

        this.printMessage(message);
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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
