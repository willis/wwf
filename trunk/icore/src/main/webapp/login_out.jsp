<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="cn.com.icore.user.service.*"%>

<%
//退入登陆
LoginControl.loginOut(request);
response.sendRedirect("login.jsp");

%>