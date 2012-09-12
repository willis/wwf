<%--
  Created by IntelliJ IDEA.
  User: Chen
  Date: 12-8-30
  Time: 下午10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Viva 分布式管理平台</title>
    <link href="${cxp}/css/themes/css/login.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        var href = window.location.href;
        if(href.indexOf('index.jsp')==-1){
            window.location.href='${cxp}/index.jsp';
            //location.reload();
        }
    </script>
</head>

<body>
<div id="login">
    <div id="login_header">
        <h1 class="login_logo">
           <!-- logo -->  <img src="${cxp}/css/themes/default/images/login_logo.gif" />
        </h1>
        <div class="login_headerContent">
            <div class="navList">

            </div>
            <h2 class="login_title"><img src="${cxp}/css/themes/default/images/login_title.png" /></h2>
        </div>
    </div>
    <div id="login_content">
        <div class="loginForm">
            <form id="myform" action="${cxp}/login_do.jsp" method="post" onSubmit="return Validator.Validate(this,2);">
                <p>
                    <label>用户名：</label>
                    <input type="text"  name="username" id="username"  style="width:60%"  dataType="Require" msg="用户名不能为空"/>
                </p>
                <p>
                    <label>密码：</label>
                  <input type="password"  name="password" id="password"  style="width:60%" dataType="login_input" msg="密码不能为空"/>
                </p>
                <p>
                    <label>验证码：</label>
                    <input class="code" type="text" size="5" />
                    <span><img src="${cxp}/servlet/randomCodeImage"  alt="" width="75" height="24" /></span>
                </p>
                <div class="login_bar">
                    <input class="sub" type="submit" value=" " />
                </div>
            </form>
        </div>
        <div class="login_banner"><img src="${cxp}/css/themes/default/images/login_banner.png" /></div>
        <div class="login_main">
            <ul class="helpList">

            </ul>
            <div class="login_inner">

            </div>
        </div>
    </div>
    <div id="login_footer">
        Copyright &copy; 2012 www.vivame.cn Inc. All Rights Reserved.
    </div>
</div>
</body>
</html>