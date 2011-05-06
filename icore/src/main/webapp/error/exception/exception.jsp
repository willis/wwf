<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.opensymphony.xwork2.util.ValueStack"%>
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page import="com.opensymphony.xwork2.interceptor.ExceptionHolder"%>

<%
	String abspath= request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Information</title>
<link href="<%=abspath%>/style/contactList_border.css" rel="stylesheet"
	type="text/css" />
<link href="<%=abspath%>/style/activity_right.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	这是一个异常。
	<%
Exception o = null;
String s1 = "";
try{
	
	ValueStack s = ActionContext.getContext().getValueStack();  
    ExceptionHolder e;
    
    
    for(int i = s.size();i>0;i--){ 
        Object obj = s.pop(); 
        if(obj instanceof ExceptionHolder){ 
            e = (ExceptionHolder)obj; 
            o = e.getException(); 
            s1 =e.getExceptionStack(); 
            break; 
        } 
    } 
}catch(Exception ex){
} 
%>
	<div style="display: none">
		<%
if(o!=null){
	out.print("<h1>"+o.getMessage()+"</h1>");
}
%>
		<font color="red">ERROR Information: <%
if(o!=null){
	if(o.getClass().getSimpleName().equals("ParameterException")){
		out.print("Action Parameter Error");
	}else{
		out.print(o.getClass().getSimpleName());
	}
}
%> </font>
		<%=s1%>
	</div>

</body>
</html>
