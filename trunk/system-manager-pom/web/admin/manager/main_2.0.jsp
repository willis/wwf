<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="pager" uri="/WEB-INF/tld/PagerTag.tld" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% 
		request.setAttribute("cxp",request.getContextPath());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=7" />
    <title>VIVA 分布式管理平台</title>
    <link href="${cxp}/css/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="${cxp}/css/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="${cxp}/css/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
    <!--[if IE]>
    <link href="${cxp}/css/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
    <![endif]-->
    <script src="${cxp}/script/jquery.min.js" type="text/javascript"></script>
    <script src="${cxp}/script/jquery.cookie.js" type="text/javascript"></script>
    <script src="${cxp}/script/jquery.validate.js" type="text/javascript"></script>
    <script src="${cxp}/script/jquery.bgiframe.js" type="text/javascript"></script>

    <script src="${cxp}/script/dwz.min.js" type="text/javascript"></script>
    <script src="${cxp}/script/dwz.regional.zh.js" type="text/javascript"></script>
    <script src="${cxp}/script/highcharts.js" type="text/javascript"></script>
<script type="text/javascript" src="${cxp}/appjs/plugin/table/jquery.table.js"></script>
    <script type="text/javascript">
        $(function(){
            DWZ.init("${cxp}/script/dwz.frag.xml", {
                loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
                statusCode:{ok:200, error:300, timeout:301}, //【可选】
                pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
                debug:false,	// 调试模式 【true|false】
                callback:function(){
                    initEnv();
                    $("#themeList").theme({themeBase:"${cxp}/css/themes"}); // themeBase 相对于index页面的主题base路径
                }
            });
        });
    </script>
</head>
<body>
<div id="layout">
    <div id="header">
        <div class="headerNav">
            <a class="logo" href="javascript:void(0)" style="height: 50px;margin-left:10px;margin-top:10px;">标志</a>
            <ul class="nav">
                  <!-- <li id="switchEnvBox"><a href="javascript:">（<span>北京</span>）切换城市</a>
                    <ul>
                        <li><a href="sidebar_1.html">北京</a></li>
                    </ul>
                </li>-->
                <li ><FONT title="${userOBJ.truename}" color="white" >${userOBJ.truename} ，你好！ </FONT> </li>
           
                <li><a href="login.html">退出</a></li>
            </ul>
            <!-- <ul class="themeList" id="themeList">
                <li theme="default"><div class="selected">蓝色</div></li>
                <li theme="green"><div>绿色</div></li>
                <li theme="purple"><div>紫色</div></li>
                <li theme="silver"><div>银色</div></li>
                <li theme="azure"><div>天蓝</div></li>
            </ul> -->
        </div>

        <!-- navMenu -->
    </div>

    <div id="leftside">
        <div id="sidebar_s">
            <div class="collapse">
                <div class="toggleCollapse"><div></div></div>
            </div>
        </div>
        <div id="sidebar">
            <div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
            <div class="accordion" fillSpace="sidebar">

               		<pager:eachMenu var="submenu" alias="menu_system" levelLimit="1"
								varStatus="index">
								<s:if test="#attr.index>0">
									       <div class="accordionHeader">
                   							 <h2><span>Folder</span>${submenu.name}</h2>
                   							 
                						   </div>
                						      <div class="accordionContent">
                						        <ul class="tree">
                						   		<pager:eachMenu var="menu" alias="${submenu.alias}" varStatus="ind">
                						   	
							                  		<s:if test="#attr.ind>0">
							                        <li><a href="${cxp}/v2.0/${menu.link}" target="navTab" rel="dlg_page">${menu.name}</a></li>
							               			</s:if>
						                		</pager:eachMenu>
						                		   </ul>
						                		</div>
								</s:if>
					</pager:eachMenu>
         
               
            </div>
        </div>
    </div>
    <div id="container">
        <div id="navTab" class="tabsPage">
            <div class="tabsPageHeader">
                <div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
                    <ul class="navTab-tab">
                        <li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
                    </ul>
                </div>
                <div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
                <div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
                <div class="tabsMore">more</div>
            </div>
            <ul class="tabsMoreList">
                <li><a href="javascript:;">我的主页</a></li>
            </ul>
            <div class="navTab-panel tabsPageContent layoutBox">
                <div class="page unitBox">
                    <div class="accountInfo">
                        <p><span>欢迎使用VIVA 分布式管理平台</span></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<div id="footer">Copyright &copy; 2012 <a href="http://www.vivame.cn" target="_blank">北京唯旺明信息技术有限公司 </a></div>

</body>
</html>