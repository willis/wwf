<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
out.println("{userName:'"+request.getParameter("userName")+"',password:'"+request.getParameter("password")+"'}");
%>