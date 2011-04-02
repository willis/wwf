<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Login</title>
<%@ include file="/include/taglibs.jsp" %>
<link href="${cxp}/css/layout.css" rel="stylesheet" type="text/css" />
<link href="${cxp}/css/fonts.css" rel="stylesheet" type="text/css" />
<link href="${cxp}/css/widget.css" rel="stylesheet" type="text/css" />
<link href="${cxp}/css/login.css" rel="stylesheet" type="text/css" />
<script src="${cxp}/js/myformcheck.js"></script>
<script src="${cxp}/js/operater.js"></script>
<script src="${cxp}/js/prototype.js"></script>
<script type="text/javascript">
	window.onload = function () {
		document.getElementById("username").focus();
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
    <form action="${cxp}/login_do.jsp" method="post" onSubmit="return checkForm(this)">
      <label>用户名：</label>
      <input type="text"  name="username" id="username"  checkInfo="用户名;NOTNULL;no"/>
      <label><br />密　码：</label>
      <input type="password"  name="password" id="password"  checkInfo="密码;NOTNULL;no" />
      <label><br />
      <!--  验证码：</label>
     <input type="text" name="randomCode" class="txtBox_3" />
      <label><img src="${cxp}/servlet/randomCodeImage"  ></label> -->
      <input type="submit" name="apply" value="登录" class="apply" />
    </form>
  </div>
</div>
<!---content end--->
<!---footer start--->
<div id="footer"><img src="${cxp}/images/footer.gif" alt="footer" title="footer" class="footer" /></div>
<!---footer end--->
</body>
</html>