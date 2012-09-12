package com.mpaike.user.action;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.user.model.SysPopedom;
import com.mpaike.user.model.SysRole;
import com.mpaike.user.model.SysUser;
import com.mpaike.user.service.SysRoleService;
import com.mpaike.user.service.SysUserService;
import com.mpaike.util.ArrayUtil;
import com.mpaike.util.MD5;
import com.mpaike.util.Message;
import com.mpaike.util.MessageType;
import com.mpaike.util.MyBeanUtils;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.app.ApplictionContext;
import com.mpaike.util.app.BaseAction;

public class SysUserAction extends BaseAction {
	public SysUserService getSysUserService() {
		return (SysUserService) ApplictionContext.getInstance().getBean(
				SysUserService.ID_NAME);
	}

	public SysRoleService roleService = (SysRoleService) ApplictionContext
			.getInstance().getBean(SysRoleService.ID_NAME);
	private SysUser sysUser;
	private Long id;
	private String ids;
	private Long status;
	private String password;
	private List<SysUser> sysUsers = new ArrayList<SysUser>();

	/**
	 * @author 陈海峰
	 * @createDate 2010-12-17 下午02:22:56
	 * @description
	 */
	private static final long serialVersionUID = -2286431261254005556L;

	public void save() {
		Message message = new Message();
		if (sysUser.getId() == null) {
			sysUser.setRegtime(new Date());
			sysUser.setStatus(0L);
			sysUser.setPassword(MD5.toMD5(sysUser.getPassword()).toString());
			getSysUserService().add(sysUser);
			message.setStatusCode(MessageType.SUCCESS.value);
			message.setMessage("添加成功!");
		} else {
			SysUser target = getSysUserService().get(sysUser.getId());
			MyBeanUtils.fillForNotNullObject(target, sysUser);
			sysUser = target;
			getSysUserService().save(sysUser);
			message.setStatusCode(MessageType.SUCCESS.value);
			message.setMessage("修改成功!");
		}
		printMessage(message);

	}

	public String getSysUserInfo() {
		sysUser = getSysUserService().get(id);
		return "get";
	}

	public void removeByIds() {
		Message message = new Message();
		Long[] longValue = ArrayUtil.toLongArray(ids.trim(), ",");
		getSysUserService().remove(longValue, 1L);
		message.setStatusCode(MessageType.SUCCESS.value);
		message.setMessage("删除成功");
		printMessage(message);
	}

	public void reductionByIds() {
		Message message = new Message();
		Long[] longValue = ArrayUtil.toLongArray(ids, ",");
		getSysUserService().remove(longValue, 0L);
		message.setStatusCode(MessageType.SUCCESS.value);
		message.setMessage("还原成功");
		printMessage(message);
	}

	public String userList() {

		OrderBy od = this.getOrderby();
		if (null == od) {
			od = OrderBy.desc("id");
		}
		sysUsers = getSysUserService().find(sysUser, this.getPageInfo(), od);
		return "userList";
	}

	public List<SysRole> getCheckedRolesList() {
		return checkedRolesList;
	}

	public void setCheckedRolesList(List<SysRole> checkedRolesList) {
		this.checkedRolesList = checkedRolesList;
	}

	public List<SysRole> getNotCheckRolesList() {
		return notCheckRolesList;
	}

	public void setNotCheckRolesList(List<SysRole> notCheckRolesList) {
		this.notCheckRolesList = notCheckRolesList;
	}

	private List<SysRole> checkedRolesList;
	private List<SysRole> notCheckRolesList;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCheckRoles() {
		sysUser = new SysUser();
		sysUser.setId(Long.parseLong(userId));
		uid = userId;
		checkedRolesList = getSysUserService().listCheckRolesToGrid(sysUser,
				this.pageInfo);

		notCheckRolesList = getSysUserService().listNotCheckRolesToGrid(
				sysUser, this.pageInfo);
		return "getRoles";
	}

	public void addSysRoles() {
		Message message = new Message();
		String cs = request.getParameter("cs");
		SysUser sysUser = getSysUserService().get(Long.valueOf(id));
		getSysUserService().addSysRole(sysUser,
				roleService.getSysRole(new Long(cs)));
		message.setStatusCode(MessageType.SUCCESS.value);
		message.setMessage("操作成功");
		printMessage(message);
	}

	public void delSysRoles() {
		Message message = new Message();
		String cs = request.getParameter("cs");
		SysUser sysUser = getSysUserService().get(Long.valueOf(id));
		getSysUserService().removeSysRole(sysUser,
				roleService.getSysRole(new Long(cs)));
		message.setStatusCode(MessageType.SUCCESS.value);
		message.setMessage("操作成功");
		printMessage(message);
	}

	public void changePassword() {
		getSysUserService().changePassword(new Long[] { id }, password);
		Message message = new Message();
		message.setStatusCode(MessageType.SUCCESS.value);
		message.setMessage("修改成功");
		printMessage(message);
	}

	public String toChangePwd() {
		id = Long.parseLong(userId.trim());
		return "toChange";
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

	public List<SysUser> getSysUsers() {
		return sysUsers;
	}

	public void setSysUsers(List<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
