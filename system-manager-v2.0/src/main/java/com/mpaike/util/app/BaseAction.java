package com.mpaike.util.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mpaike.util.Message;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.mpaike.core.database.hibernate.OrderBy;
import com.mpaike.core.util.json.JsonConfigFactory;
import com.mpaike.core.util.page.Pagination;
import com.mpaike.user.service.SysMenuService;
import com.mpaike.user.service.SysPopedomService;
import com.mpaike.user.service.SysRoleService;
import com.mpaike.user.service.SysUserService;
import com.mpaike.util.HtmlUtil;
import com.mpaike.util.KeyFactory;
import com.mpaike.util.ParamHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

    protected static int version = 0x01;

    protected final Log logger = LogFactory.getLog(getClass());

    private String field;
    // easyUI前台传过来的排序方式(desc?asc)，故必须以此命名，原因同上
    private String sortOrder;
    private int pageNum;//<!--【必须】value=1可以写死-->
    private int numPerPage;//<!--【可选】每页显示多少条-->
    private String orderField;// 排序字段
    private String orderDirection;//前台传过来的排序方式(desc?asc)，故必须以此命名，原因同上

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

    public void printMessage(Message message) {
        try {
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(JSONObject.fromObject(message));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public void printBeansJson(List beans) {
        try {
            JSONArray jsonArray = JSONArray.fromObject(beans);
            response.setCharacterEncoding("utf-8");

            response.getWriter().println(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printJson(String message) {
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(
                    ParamHelper.toJsString(message)
            );
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void printBeanToJson(String message) {
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(message);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    @SuppressWarnings("rawtypes")
    public void printPageList(List beans) {
        printPageList(beans, null);
    }

    @SuppressWarnings("rawtypes")
    public void printPageList(List beans, JsonConfig jsonConfig) {
        JsonPage jp = new JsonPage();
        jp.setPageInfo(this.pageInfo);
        jp.setData(beans);
        try {

            JSONObject json;
            if (jsonConfig != null) {
                json = JSONObject.fromObject(jp, jsonConfig);
            } else {
                json = JSONObject.fromObject(jp, JsonConfigFactory.getConfigJson(1));
            }
            response.setCharacterEncoding("utf-8");
            //System.out.println(json);
            response.getWriter().println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String addSlashes(String html) {
        return HtmlUtil.addSlashes(html);
    }

    public String htmlEncode(String html) {
        return HtmlUtil.htmlEncode(html);
    }

    public String encode(String text) {
        return KeyFactory.mapKey("VIVA X2").encryptString(text);
    }

    public String decode(String text) {
        return KeyFactory.mapKey("VIVA X2").decryptString(text);
    }

    public Pagination getPageInfo() {
        if (pageNum == 0) {
            pageNum = 1;
        }
        if (numPerPage == 0)
            numPerPage = 20;
        pageInfo.setPageSize(numPerPage);
        pageInfo.setPageNo(pageNum);
        return pageInfo;
    }

    public void setPageInfo(Pagination pageInfo) {

        this.pageInfo = pageInfo;
    }

    public OrderBy getOrderby() {
        if (orderField != null && !"".equals(orderField) && orderDirection != null) {
            if ("asc".equals(orderDirection)) {
                orderby = OrderBy.asc(orderField);
            } else {
                orderby = OrderBy.desc(orderField);
            }
        }
        return orderby;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }


    @SuppressWarnings("rawtypes")
    public class JsonPage {

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
