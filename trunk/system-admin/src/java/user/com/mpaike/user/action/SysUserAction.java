package com.mpaike.user.action;

import java.util.Date;
import java.util.List;

import com.mpaike.user.model.SysRole;
import com.mpaike.user.model.SysUser;
import com.mpaike.user.service.SysRoleService;
import com.mpaike.user.service.SysUserService;
import com.mpaike.util.ArrayUtil;
import com.mpaike.util.MD5;
import com.mpaike.util.MyBeanUtils;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.app.ApplictionContext;
import com.mpaike.util.app.BaseAction;

public class SysUserAction extends BaseAction  {
	public SysUserService getSysUserService() {
		return (SysUserService) ApplictionContext.getInstance()
				.getBean(SysUserService.ID_NAME);
	}
	
    public SysRoleService roleService = (SysRoleService)ApplictionContext.getInstance()
	.getBean(SysRoleService.ID_NAME);
	private SysUser sysUser;
	private Long id;
	private String ids;
	private Long status;
	private String password;

	/**
	 * @author 陈海峰
	 * @createDate 2010-12-17 下午02:22:56
	 * @description 
	 */
	private static final long serialVersionUID = -2286431261254005556L;

	public void save(){
		String result = "";
		if(sysUser.getId()==null){
			sysUser.setRegtime(new Date());
			sysUser.setStatus(0l);
			sysUser.setPassword(MD5.toMD5(sysUser.getPassword()));
			result ="添加成功!";
		}else{
			
			SysUser target = getSysUserService().get(sysUser.getId());
	
		    MyBeanUtils.fillForNotNullObject(target, sysUser);
			sysUser = target;
			
			result ="修改成功!";
		}
		
		if(!getSysUserService().save(sysUser)){
			result ="用户名重复!";
		}
		super.printSuccessJson(result);
		
	}
	
	public String getSysUserInfo(){
		sysUser = getSysUserService().get(id);
		return "get";
	}
	
	public void removeByIds(){
	
		Long[] longValue =  ArrayUtil.toLongArray(ids,",");
		getSysUserService().remove(longValue,status);
		if(status==1l)
			super.printSuccessJson("删除成功！");
		if(status==2l)
			super.printSuccessJson("冻结成功！");
		if(status==0l)
			super.printSuccessJson("还原成功！");
	}
	
	
	public void userList() {
		List<SysUser> datas = getSysUserService().find(sysUser,this.pageInfo,this.getOrderby());
		this.printPageList(datas);

	}

	public void getNotCheckRoles() {


		sysUser = new SysUser();
		sysUser.setId(id);
		List<SysRole> datas =getSysUserService().listNotCheckRolesToGrid(sysUser,this.pageInfo);
		this.printPageList(datas);
	}

	public void getCheckRoles() {
		sysUser = new SysUser();
		sysUser.setId(id);
		List<SysRole> datas =getSysUserService().listCheckRolesToGrid(sysUser,this.pageInfo);
		this.printPageList(datas);
	}
	
	public void addSysRoles(){

	    long id = ParamHelper.getLongParamter(request, "id", -1L);
	    if (id == -1L)
	    	super.printSuccessJson("请选择用户！");
	    String[] cs = request.getParameterValues("cs");
	    if ((cs == null) || (cs.length == 0))
	    	super.printSuccessJson("请选择要添加的角色！");
	    if (cs.length == 1) {
	      cs = cs[0].split(",");
	    }
	    SysUser sysUser = getSysUserService().get(Long.valueOf(id)); 
	    for (String c : cs) {
	      if (!"".equals(c.trim())) {

	    	  getSysUserService().addSysRole(sysUser, roleService.getSysRole(new Long(c)));
	      }
	    }
	    super.printSuccessJson("添加成功！");
	}
	

	  public void delSysRoles()
	 
	  {
	   
		    long id = ParamHelper.getLongParamter(request, "id", -1L);
		    if (id == -1L)
		    	super.printSuccessJson("请选择用户！");
		    String[] cs = request.getParameterValues("cs");
		    if ((cs == null) || (cs.length == 0))
		    	super.printSuccessJson("请选择要删除的角色！");
		    if (cs.length == 1) {
		      cs = cs[0].split(",");
		    }
		    SysUser sysUser = getSysUserService().get(Long.valueOf(id)); 
	  
	    for (String c : cs) {
	      if (!"".equals(c.trim())) {
	    	  getSysUserService().removeSysRole(sysUser, roleService.getSysRole(new Long(c)));
	      }
	    }
	    super.printSuccessJson("删除成功！");
	  
	  }
	public void changePassword(){
		Long[] longValue =  ArrayUtil.toLongArray(ids,",");
		getSysUserService().changePassword(longValue, password);
		super.printSuccessJson("修改密码成功！");
	}

	public SysUser getSysUser() {
		return sysUser;
	}



	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
}
