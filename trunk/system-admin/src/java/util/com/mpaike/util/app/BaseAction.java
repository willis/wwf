package com.mpaike.util.app;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.mpaike.bot.service.IWebUrlService;
import com.mpaike.bot.spider.BotSpider;
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.json.JsonConfigFactory;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.member.service.MemberService;
import com.mpaike.user.service.SysMenuService;
import com.mpaike.user.service.SysPopedomService;
import com.mpaike.user.service.SysRoleService;
import com.mpaike.user.service.SysUserService;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.pager.Pager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("unchecked")
public class BaseAction extends ActionSupport {
	protected final Log logger = LogFactory.getLog(getClass());

    private String field;
    // easyUI前台传过来的排序方式(desc?asc)，故必须以此命名，原因同上
    private String sortOrder;
    
    protected Pagination pageInfo = new Pagination();
    protected OrderBy orderby;

	/**
	 * @author 陈海峰
	 * @createDate 2010-12-17 下午02:39:34
	 * @description
	 */
	private static final long serialVersionUID = -2713334070045301162L;
	ActionContext context = ActionContext.getContext();
	protected HttpServletRequest request = (HttpServletRequest) context
			.get(ServletActionContext.HTTP_REQUEST);
	protected HttpServletResponse response = (HttpServletResponse) context
			.get(ServletActionContext.HTTP_RESPONSE);

	public Pager getPager(HttpServletRequest request) {
		Pager pager = new Pager();
		try {

			int Pg = ParamHelper.getIntParamter(request, "Pg", 1);

			int pageSize = ParamHelper.getIntParamter(request, "pageSize",
					Integer.parseInt(getAppProps().get("pageSize").toString()));
			pager.setPage(Pg);
			pager.setPageSize(pageSize);
		} catch (Exception e) {

			logger.error(e);
		}
		return pager;
	}

	public AppProps getAppProps() {

		return (AppProps) ApplictionContext.getInstance()
				.getBean("appProps");
	}

	public void printSuccessJson(String message) {
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(
					"{status:1,message:'" + ParamHelper.toJsString(message)
							+ "'}");
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public void printBeansJson(List beans) {
		try {
			JSONArray jsonArray = JSONArray.fromObject(beans);
			response.setCharacterEncoding("utf-8");
			
			response.getWriter().println(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printJson(String message){
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(
					 ParamHelper.toJsString(message)
							);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	public void printPageList(List beans){
		printPageList(beans,null);
	}
	
	@SuppressWarnings("rawtypes")
	public void printPageList(List beans,JsonConfig jsonConfig){
		JsonPage jp = new JsonPage();
		jp.setPageInfo(this.pageInfo);
		jp.setData(beans);
		try {
			
			JSONObject json;
			if(jsonConfig!=null){
				json = JSONObject.fromObject(jp,jsonConfig);
			}else{
				json = JSONObject.fromObject(jp,JsonConfigFactory.getConfigJson(1));
			}
			response.setCharacterEncoding("utf-8");
			System.out.println(json);
			response.getWriter().println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Pagination getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(Pagination pageInfo) {
		this.pageInfo = pageInfo;
	}



	public OrderBy getOrderby() {
		if(field!=null&&!"".equals(field)&&sortOrder!=null){
			if("asc".equals(sortOrder)){
				orderby = OrderBy.asc(field);
			}else{
				orderby = OrderBy.desc(field);
			}
		}
		return orderby;
	}
	@SuppressWarnings("rawtypes")
	public class JsonPage{
		
		private Pagination pageInfo;
		private List data;
		
		public Pagination getPageInfo() {
			return pageInfo;
		}
		public void setPageInfo(Pagination pageInfo) {
			this.pageInfo = pageInfo;
		}
		public List getData() {
			return data;
		}
		public void setData(List data) {
			this.data = data;
		}


	}
	
	//service
	public BotSpider getBotSpider() {
		return (BotSpider) ApplictionContext.getInstance().getBean("botSpider");
	}
	
	
	public IWebUrlService getWebUrlService() {
		return (IWebUrlService) ApplictionContext.getInstance().getBean(IWebUrlService.ID_NAME);
	}
	
	public MemberService getMemberService() {
		return (MemberService) ApplictionContext.getInstance().getBean(MemberService.ID_NAME);
	}
	
	public SysPopedomService getSysPopedomService() {
		return (SysPopedomService) ApplictionContext
		.getInstance().getBean(SysPopedomService.ID_NAME);
	}
	public SysUserService getSysUserService() {
		return (SysUserService) ApplictionContext.getInstance().getBean(
				SysUserService.ID_NAME);
	}
	public SysRoleService getSysRoleService() {
		return (SysRoleService) ApplictionContext.getInstance().getBean(
				SysRoleService.ID_NAME);
	}
	public SysMenuService getSysMenuService() {
		return (SysMenuService) ApplictionContext.getInstance().getBean(
				SysMenuService.ID_NAME);
	}
}
