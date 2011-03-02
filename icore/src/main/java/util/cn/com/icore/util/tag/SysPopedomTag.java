package cn.com.icore.util.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.icore.user.service.LoginControl;

public class SysPopedomTag extends TagSupport {

	/**
	 * @author 陈海峰
	 * @createDate 2011-2-23 上午09:59:14
	 * @description 
	 */
	private static final long serialVersionUID = 1L;
	private String code = null;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int doStartTag() throws JspException {
	
		if (this.code == null)
			return 0;
		if (LoginControl.checkPopedom(this.code.split(","),
				(HttpServletRequest) this.pageContext.getRequest())) {
			return 1;
		}
		return 0;
	}

	public void release() {
		this.code = null;
		super.release();
	}

}
