package com.mpaike.view.filter;

import com.mpaike.user.service.LoginControl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class UserFilter implements Filter {

	
	private String login_url = "/login.jsp";

	  private String defaultEncode = "UTF-8";

	  private String[] notCheckJsp = new String[0];

	  private boolean isUsePopedom = false;

	  public void destroy()
	  {
	  }

	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException
	  {
	    request.setCharacterEncoding(this.defaultEncode);

	    String nowUrl = ((HttpServletRequest)request).getRequestURI();

          String path = ((HttpServletRequest) request).getContextPath();
          String prePath = request.getScheme()+"://"+request.getServerName();
          String basePath = prePath +":"+request.getServerPort()+path+"/";
          request.setAttribute("cxp",basePath);
	    boolean isNotCheckJsp = false;
	    for (String s : this.notCheckJsp) {
	    	
	      if (nowUrl.indexOf(s) != -1) {
	        isNotCheckJsp = true;
	        break;
	      }
	    }
	    if (isNotCheckJsp)
	    {
	      chain.doFilter(request, response);
	    }
	    else if (!LoginControl.hasLogin((HttpServletRequest) request))
	    {
	      request.getRequestDispatcher(this.login_url).forward(request, response);
	    }
	    else if (this.isUsePopedom)
	      try
	      {
	        //TransactionSynchronizationManager.bindResource(LoginControl.POPEDOM_OBJ, LoginControl.getPopedoms((HttpServletRequest)request));
	       // TransactionSynchronizationManager.bindResource(LoginControl.USER_OBJ, LoginControl.getSysUser((HttpServletRequest)request));
	        chain.doFilter(request, response);
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	      finally {
	       // TransactionSynchronizationManager.unbindResource("ICORE_POPEDOM");
	        //TransactionSynchronizationManager.unbindResource("userOBJ");
	      }
	    else
	      chain.doFilter(request, response);
	  }

	  public void init(FilterConfig config)
	    throws ServletException
	  {
	    String _login_url = config.getInitParameter("login_url");
	    String encode = config.getInitParameter("Encode");
	    String _notCheckJsp = config.getInitParameter("notCheckJsp");
	    String _isUsePopedom = config.getInitParameter("isUsePopedom");

	    if (_login_url != null) {
	      this.login_url = _login_url;
	    }
	    if (encode != null) {
	      this.defaultEncode = encode;
	    }
	    if (_notCheckJsp != null) {
	      this.notCheckJsp = _notCheckJsp.split(",");
	    }
	    if (_isUsePopedom != null) {
	      this.isUsePopedom = Boolean.parseBoolean(_isUsePopedom);
	    }
	    System.out.println("权限验证的Filter:" + this.login_url);

	    System.out.println("当前编码：" + this.defaultEncode);

	    System.out.println("不需要验证的页面：" + _notCheckJsp);

	    System.out.println("是否需要业务层权限验证：" + this.isUsePopedom);
	  }
}
