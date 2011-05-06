<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>

<head>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<script type="text/javascript" src="${cxp }/js/TableTree4J.js"></script>
<link rel="StyleSheet" href="${cxp }/js/css/tabletree4j.css"
	type="text/css" />

<script src="${cxp }/appjs/ajaxform.common.js"></script>

<style>
.body {
	font-size: 12px;
}

.btnDiv a {
	color: #0000FF;
	text-decoration: none;
}

.btnDiv a:hover {
	color: #CC3300;
	text-decoration: underline;
}

.items {
	color: #669999;
	font-size: 14px;
}

.title {
	font-size: 16px;
	font-weight: bold;
}

.copyrightdiv {
	font-size: 12px;
	font-family: "Arial";
	color: #C0C0C0;
}

.centerClo {
	text-align: center;
}
</style>


<script language="JavaScript"> 
	
//GridTree
	var menuTree;	
	function showMenuTree(){
	//init
		menuTree=new TableTree4J("menuTree","${cxp}/js/");	
		menuTree.config.useCookies=true;
		menuTree.toMenuMode();
		menuTree.setMenuRoot("<input type=\"checkbox\" id=\"c\" name=\"c\" value=\"${rootObj.id}\" >${rootObj.name}",${rootObj.id},true,"MenuRoot" );
	//add data //参数: menuName,id,pid,booleanOpen,order,url,target,hrefTip,hrefStatusText,classStyle,icon,iconOpen
	 	<s:if test="!tree.isEmpty()">
	    <s:iterator value="tree">
	
	     menuTree.addMenuNode("<input type=\"checkbox\" id=\"c\" name=\"c\" value=\"${id}\" >${name}",${id},${parentObj.id},true,${orderBy},"#","_self","${name}","",null,null,null);
	
		
 		</s:iterator>
 		</s:if>
	   
 		
		
	//print	
		menuTree.printTableTreeToElement("menuTreeDiv");		
	
	}
	
 

</script>


</head>

<body onload="showMenuTree();menuTree.openAllNodes()">
	<form name="myForm" id="myForm" method="post"
		action="sysRole!addSysMenus.action">
		<div id="main">

			<input type="hidden" name="roleId" value="${param.roleId }" />

			<div id="menuTreeDiv"></div>

			<div>
				<input type="button" value="全选" class="button"
					onclick="selectAllCheck()" /> <input type="button" value="返选"
					class="button" onclick="selectCheck()" /> <input type="submit"
					name="subOk" value="保存" class="button" /> <input type="button"
					name="backOk" value="关闭" class="button"
					onclick="window.parent.closeWindow()" />
			</div>

			<p id="errorMsg" style="color: red">&nbsp;</p>
		</div>
	</form>
	<script>
		$(document).ready(function() {
			$.ajax({
				type : "POST",
				url : "sysRole!getSysMenuJson.action",
				data : {roleId:${param.roleId}},
				dataType : "json",
				success : function(datas) {
		
					for (var i = 0; i < datas.length; i++) {
						$("input[value="+datas[i].id+"]").click();
					}
					
				},
				error : function(response) {
					alert(response.responseText);
				}


			});
			});
			
			/**
			回调
			**/
			function processJson(data) {
			if(data.status == SUCCESS){
				//图层解锁
				$("#myForm").unblock();
				window.parent.jAlert(data.message, "系统提示");
				
			//	window.parent.closeWindow();
			}else{
				$("#errorMsg").html(data.message);
				//图层解锁
				$("#myForm").unblock();
			}
			}
			
			function selectAllCheck(){
				$("input[type='checkbox']").each(function(i){
					$(this).attr("checked","true")
				});
			}
			
			function selectCheck(){
				$("input[type='checkbox']").each(function(i){
					this.click();
				});
			}
		</script>
</body>
</html>
