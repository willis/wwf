<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import= "com.mpaike.util.*" %>
<%@ page import= "com.mpaike.user.service.*" %>
<%@page import="com.mpaike.util.app.ApplictionContext"%>

<%
//用户名
String username = ParamHelper.getStr(request,"username","");
//密码
String password = ParamHelper.getStr(request,"password","");

String randomCode = ParamHelper.getStr(request,"randomCode","");
//if(!randomCode.equals(session.getAttribute(RandomCodeImage.RANDOMCODENAME))){
//	out.println("<script>alert('对不起，您的验证码不正确！');window.location.href='login.jsp';</script>");
//	return ;
//}

//登陆控制器
LoginControl loginControl = (LoginControl)ApplictionContext.getInstance().getBean(LoginControl.ID_NAME);
try{
if(loginControl.login(username,password,request)){
  //如果登陆成功
 out.println("<script>window.location.href='manager/main_2.0.jsp';</script>");
  
}else{
		out.println("<script>alert('对不起，您的用户名或密码不正确！');window.location.href='login.jsp';</script>");
 
}
}catch(Exception e){
	e.printStackTrace();
}
%>