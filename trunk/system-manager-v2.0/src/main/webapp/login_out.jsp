<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import= "com.mpaike.user.service.*" %>

<%
//退入登陆
LoginControl.loginOut(request);
response.sendRedirect("login.jsp");

%>