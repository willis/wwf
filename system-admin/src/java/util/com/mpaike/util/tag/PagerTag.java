package com.mpaike.util.tag;


import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.TagSupport;

import com.mpaike.util.pager.Pager;

@SuppressWarnings("unchecked")
public class PagerTag extends TagSupport {
	    private static final long serialVersionUID = 719669934024053141L;    
	    Pager pager;
		private String name;
		private String action;
		private String method;
		private String scope;

		public PagerTag()
		{
			pager = null;
			method = "post";
		}

		
		public Pager getPager() {
			return pager;
		}

		public void setPager(Pager pager) {
			this.pager = pager;
		}



		public int doStartTag()
			throws JspException
		{
			try
			{
				pager = (Pager)TagUtil.getInstance().lookup(pageContext, name, scope);

				JspWriter out = pageContext.getOut();
				out.println("<script>");
				out.println("function pageFormSetPageSize(form,value){ var p1 = /^\\d{1,}$/; if(!p1.test(value)){alert(\"分页只能是数字！\");return ; } form.submit();}");
				out.println("function pageFormGo(form,value){ form.pageNo.value = value; form.submit();}");
				out.println("function pageFormSetPg(form,value){ var p1 = /^\\d{1,}$/; if(!p1.test(value)){alert(\"分页只能是数字！\");return ; } form.pageNo.value = value;form.submit();}");
				out.println("</script>");
				out.println((new StringBuilder("<form name='formPage' action='")).append(action).append("' method='").append(method).append("'> ").toString());
				out.println((new StringBuilder("每页显示<input name=\"pageinfo.pageSize\" onblur=\"pageFormSetPageSize(this.form,this.value)\" value=\"")).append(pager.getPageSize()).append("\" type=\"text\" id=\"pageSize\"   style=\"width:20px; border:solid #CCC 1px\"/>").toString());
				out.println((new StringBuilder("条&nbsp;|&nbsp;共<font color='red'>")).append(pager.getRePages()).append("</font>页 , <font color='red'>").append(pager.getReCount()).append("</font>条数据").toString());
				out.println("&nbsp;|&nbsp;");
				out.println((new StringBuilder("<input type=\"button\"  ")).append(pager.getPage() != 1 ? " style=\"background:#FFF ; border:none;cursor:pointer\"" : "disabled=\"disabled\"").append(" onclick=\"pageFormGo(this.form,1)\" name=\"button\" id=\"button\" value=\"首页\"  style=\"background:#FFF ; border:none; \"/>&nbsp;").toString());
				out.println((new StringBuilder("<input type=\"button\" ")).append(pager.getPage() != 1 ? " style=\"background:#FFF ; border:none;cursor:pointer\"" : "disabled=\"disabled\"").append(" onclick=\"pageFormGo(this.form,").append(pager.getPage() - 1).append(")\" name=\"button\" id=\"button\" value=\"上一页\"  style=\"background:#FFF ; border:none;\"/>&nbsp;").toString());
				out.println((new StringBuilder("<input type=\"button\" ")).append(pager.getPage() < pager.getRePages() ? " style=\"background:#FFF ; border:none;cursor:pointer\"" : "disabled=\"disabled\"").append(" onclick=\"pageFormGo(this.form,").append(pager.getPage() + 1).append(")\" name=\"button\" id=\"button\" value=\"下一页\"  style=\"background:#FFF ; border:none;\"/>&nbsp;").toString());
				out.println((new StringBuilder("<input type=\"button\" ")).append(pager.getPage() < pager.getRePages() ? " style=\"background:#FFF ; border:none;cursor:pointer\"" : "disabled=\"disabled\"").append(" onclick=\"pageFormGo(this.form,").append(pager.getRePages()).append(")\" name=\"button\" id=\"button\" value=\"尾页\"  style=\"background:#FFF ; border:none; \"/>&nbsp;").toString());
				out.println((new StringBuilder("&nbsp;|&nbsp;第<input name=\"pageinfo.pageNo\" onblur=\"pageFormSetPg(this.form,this.value)\" value=\"")).append(pager.getPage()).append("\" type=\"text\" id=\"pageNo\" style=\"width:20px; border:solid #CCC 1px\"/>页").toString());
			}
			catch (Exception e)
			{
				throw new JspException((new StringBuilder("分页标签出错了...")).append(e).toString());
			}
			return 6;
		}

		public int doEndTag()
			throws JspException
		{
			try
			{
				JspWriter out = pageContext.getOut();
				out.println("</form> ");
			}
			catch (Exception e)
			{
				throw new JspException((new StringBuilder("分页标签出错了...")).append(e).toString());
			}
			return 0;
		}

		public void release()
		{
			name = null;
			action = null;
			method = null;
			pager = null;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getAction()
		{
			return action;
		}

		public void setAction(String action)
		{
			this.action = action;
		}

		public String getMethod()
		{
			return method;
		}

		public void setMethod(String method)
		{
			this.method = method;
		}

	
		public String getScope() {
			return scope;
		}

	
		public void setScope(String scope) {
			this.scope = scope;
		}

		
}
