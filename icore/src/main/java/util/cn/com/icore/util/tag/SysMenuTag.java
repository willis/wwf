package cn.com.icore.util.tag;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.icore.user.model.SysMenu;
import cn.com.icore.user.service.LoginControl;
import cn.com.icore.user.service.SysMenuControl;

@SuppressWarnings("unchecked")
public class SysMenuTag extends TagSupport {
	/**
	 * @author 陈海峰
	 * @createDate 2011-2-18 下午03:42:50
	 * @description
	 */
	private static final long serialVersionUID = 1L;
	private String var;
	private String alias;
	private boolean check;

	public int doStartTag() throws JspException {
		Set<SysMenu> userMenus = LoginControl
				.getSysMenus((HttpServletRequest) this.pageContext.getRequest());
		if (userMenus == null)
			return 0;
		SysMenu sysMenu = SysMenuControl.getInstance().getSysMenuByAlias(
				this.alias);
		if (sysMenu == null)
			return 0;

		if (this.check) {

			for (SysMenu sm : userMenus) {

				if (sm.getId().equals(sysMenu.getId())) {

					this.pageContext.getRequest().setAttribute(this.var,
							sysMenu);
				}
			}

		} else {

			this.pageContext.getRequest().setAttribute(this.var, sysMenu);
		}
		return 0;
	}

	public void release() {
		super.release();
		this.alias = null;
		this.var = null;
		this.check = false;
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

	public boolean isCheck() {
		return this.check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
}
