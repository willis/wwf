package com.mpaike.util.app;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.mpaike.bot.service.IWebUrlService;
import com.mpaike.bot.spider.BotSpider;
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.json.DateJsonValueProcessor;
import com.mpaike.core.util.json.JsonConfigFactory;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.member.service.MemberService;
import com.mpaike.util.ParamHelper;
import com.mpaike.util.pager.Pager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("unchecked")
public class BaseAction extends ActionSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	// easyUI前台传过来的请求页数，故必须以此命名，当然你也可以不这样，但set方法必须是setPage
    private int page;
    // easyUI前台传过来的请求记录数，故必须以此命名，原因同上
    private int rows;
    // easyUI前台传过来的排序字段，故必须以此命名，原因同上
    private String sort;
    // easyUI前台传过来的排序方式(desc?asc)，故必须以此命名，原因同上
    private String order;
    
    protected Pagination pageinfo;
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
		JsonPage jp = new JsonPage();
		if(pageinfo!=null){
			jp.setTotal(pageinfo.getTotalCount());
		}else{
			jp.setTotal(0);
		}
		jp.setRows(beans);
		try {
			JSONObject json = JSONObject.fromObject(jp,JsonConfigFactory.getConfigJson());
			response.setCharacterEncoding("utf-8");
			System.out.println(json);
			response.getWriter().println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public Pagination pageToPageinfo() {
		pageinfo = new Pagination();
		pageinfo.setPageNo(page);
		if(rows!=0){
			pageinfo.setPageSize(rows);
		}
		return pageinfo;
	}

	public OrderBy getOrderby() {
		if(order!=null&&sort!=null){
			if("asc".equals(order)){
				orderby = OrderBy.asc(sort);
			}else{
				orderby = OrderBy.desc(sort);
			}
		}
		return orderby;
	}

	public class JsonPage{
		
		private long total;
		@SuppressWarnings("rawtypes")
		private List rows;
		public long getTotal() {
			return total;
		}
		public void setTotal(long total) {
			this.total = total;
		}
		public List getRows() {
			return rows;
		}
		public void setRows(List rows) {
			this.rows = rows;
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
	
}
