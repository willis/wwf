<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Login</title>
<%@ include file="/include/taglibs.jsp" %>
<link href="${cxp}/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	window.onload = function () {
		document.getElementById("username").focus();
	}
	var openerIsIndex=false;

	if(opener!=null && !openerIsIndex){
		opener.top.location= "${cxp}/index.jsp";
		window.close();
	}else if(top!=self){
		top.location= "${cxp}/index.jsp";
	}
	
</script>
</head>

<body>
<!---header start--->
<h1 id="header"></h1>
<!---header end--->
<!---nav start--->
<div id="nav">
</div>
<!---nav end--->
<!---content start--->
<div id="content">

  <div id="login"><h2>系统登录      <font color="red">默认账号：admin/123</font></h2>
    <form id="myform" action="${cxp}/login_do.jsp" method="post" onSubmit="return Validator.Validate(this,3);">
    <table>
    <tr>
   	 <td>
      <label>用户名：</label>
      <input type="text"  name="username" id="username"  style="width:60%"  dataType="Require" msg="用户名不能为空"/>
      </td>
      </tr>
      
      <tr>
       <td>
      <label>密　码：</label>
      <input type="password"  name="password" id="password"  style="width:60%" dataType="Require" msg="密码不能为空"/>
      <label>
      </td>
      </tr>
       <tr>
       <td>
      <!--  验证码：</label>
     <input type="text" name="randomCode" class="txtBox_3" />
      <label><img src="${cxp}/servlet/randomCodeImage"  ></label> -->
      <input type="submit" name="apply" value="登录" class="apply" />
      </td>
      </tr>
      </table>
    </form>
  </div>
</div>
<script>
Validator.createCheckForm(document.forms[0]);
</script>
<!---content end--->
<!---footer start--->
<div id="footer"><img src="${cxp}/images/footer.gif" alt="footer" title="footer" class="footer" /></div>
<!---footer end--->
</body>
</html>