package com.mpaike.util.tag;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.mpaike.user.model.SysMenu;
import com.mpaike.user.service.LoginControl;
import com.mpaike.user.service.SysMenuControl;

public class SysMenuEachTagV2 extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String var;
	private String alias;
	private int index = 0;

	private int end = 0;

	private int levelLimit = -1;

	private String varStatus = null;

	private List<SysMenu> tree = null;

	public int doStartTag() throws JspException {
		Set<SysMenu>  userMenus = LoginControl
				.getSysMenus((HttpServletRequest) this.pageContext.getRequest());
		if (userMenus == null) {
			return 0;
		}
		this.tree = SysMenuControl.getInstance().getSysMenusByAlias(this.alias,
				userMenus, this.levelLimit);

		if (this.tree.isEmpty())
			return 0;
		this.end = this.tree.size();
		this.index = 0;

		if (this.index < this.end) {
	
			if (this.varStatus != null) {
				this.pageContext.getRequest().setAttribute(this.varStatus,
						Integer.valueOf(this.index));
			}
			this.pageContext.getRequest().setAttribute(this.var,
					this.tree.get(this.index));
			return 1;
		}
		return 0;
	}

	public int doAfterBody() throws JspException {
		this.index += 1;
		if (this.index < this.end) {
			if (this.varStatus != null) {
				this.pageContext.getRequest().setAttribute(this.varStatus,
						Integer.valueOf(this.index));
			}
			this.pageContext.getRequest().setAttribute(this.var,
					this.tree.get(this.index));
			return 2;
		}
		return 0;
	}

	public void release() {
		super.release();
		this.index = 0;
		this.end = 0;
		this.alias = null;
		this.var = null;
		this.tree = null;
		this.levelLimit = -1;
		this.varStatus = null;
	}

	public String getVar() {
		return this.var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getLevelLimit() {
		return this.levelLimit;
	}

	public void setLevelLimit(int levelLimit) {
		this.levelLimit = levelLimit;
	}

	public String getVarStatus() {
		return this.varStatus;
	}

	public void setVarStatus(String varStatus) {
		this.varStatus = varStatus;
	}

}
