package com.mpaike.user.action;

import java.util.List;

import com.mpaike.user.model.SysPopedom;
import com.mpaike.util.MyBeanUtils;
import com.mpaike.util.app.BaseAction;


public class SysPopedomAction extends BaseAction {
	private SysPopedom sysPopedom;
	private Long id;
	private String[] c;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void list() {

		List<SysPopedom> datas = this.getSysPopedomService().listToGrid(sysPopedom, this.pageInfo, this.getOrderby());
		this.printPageList(datas);
	}

	public void del() {
		String[] cs = c;

		if (cs == null)
			super.printSuccessJson("请选择要删除的权限代码！");
		for (String c : cs) {
			if (!"".equals(c.trim())) {
				this.getSysPopedomService().removeSysPopedom(new Long(c));
			}
		}
		super.printSuccessJson( "删除成功！");

	}

	public void save() {
		String result = "";
		if (sysPopedom.getId() == null) {
			result = "添加成功!";
		} else {
			SysPopedom target = this.getSysPopedomService().getSysPopedom(sysPopedom
					.getId());
			MyBeanUtils.fillForNotNullObject(target, sysPopedom);
			sysPopedom = target;
			result = "修改成功!";
		}
		this.getSysPopedomService().saveSysPopedom(sysPopedom);
		super.printSuccessJson(result);
	}

	public String edit() {
		sysPopedom = this.getSysPopedomService().getSysPopedom(id);
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
