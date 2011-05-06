<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>

<head>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<script type="text/javascript" src="${cxp }/js/TableTree4J.js"></script>
<link rel="StyleSheet" href="${cxp }/js/css/tabletree4j.css"
	type="text/css" />


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


<script> 
	
//GridTree
	var gridTree;	
	function showGridTree(){
	//init
		gridTree=new TableTree4J("gridTree","${cxp }/js/");	
		gridTree.config.useCookies=true;
		
		gridTree.tableDesc="<table border=\"1\" class=\"GridView\" width=\"100%\" id=\"table1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse\"  bordercolordark=\"#C0C0C0\" bordercolorlight=\"#C0C0C0\" >";	
		var headerDataList=new Array("菜单名称","更新日期","操作");
		var widthList=new Array("20%","20%","60%");
		//参数: arrayHeader,id,headerWidthList,booleanOpen,classStyle,hrefTip,hrefStatusText,icon,iconOpen
		gridTree.setHeader(headerDataList,-1,widthList,true,"GridHead","This is a tipTitle of head href!","header status text","","");				
		//设置列样式
		gridTree.gridHeaderColStyleArray=new Array("","","","centerClo");
		gridTree.gridDataCloStyleArray=new Array("","","","centerClo");	
		
		var dataList=new Array("${rootObj.name}","<s:date name="rootObj.curDate" format="yyyy-MM-dd" />","<a href=\"javascript:window.parent.showWindow('${cxp}/user/sysMenu!toAddSysMenu.action?pid=${rootObj.id}','添加节点',200,500)\">添加子节点</a>");
		//参数: dataList,id,pid,booleanOpen,order,url,target,hrefTip,hrefStatusText,classStyle,icon,iconOpen
		gridTree.addGirdNode(dataList,${rootObj.id},-1,null,1,"#",null,"${rootObj.name}","状态栏文字",null,null,null);
		
	    //add data
	    <s:if test="!tree.isEmpty()">
	    <s:iterator value="tree">
	
		var dataList=new Array("<s:property value="name" />"," <s:date name="curDate" format="yyyy-MM-dd" />","<a href=\"javascript:window.parent.showWindow('${cxp}/user/sysMenu!toAddSysMenu.action?pid=<s:property value="id" />','添加节点',200,500)\">添加子节点</a> | <a href=\"javascript:window.parent.showWindow('${cxp}/user/sysMenu!toEditSysMenu.action?id=<s:property value="id" />','修改节点',200,500)\">修改子节点</a>  |<a href=\"javascript:delMe(<s:property value="id" />)\">删除</a> ");
		//参数: dataList,id,pid,booleanOpen,order,url,target,hrefTip,hrefStatusText,classStyle,icon,iconOpen
		gridTree.addGirdNode(dataList,<s:property value="id" />,<s:property value="parentObj.id" />,null,<s:property value="orderBy" />,"#",null,"<s:property value="name" />","状态栏文字",null,null,null);
 		</s:iterator>
 		</s:if>
		
	   //print	
		gridTree.printTableTreeToElement("gridTreeDiv");		
	
	}
	
 
 
function delMe(value){
	window.parent.parent.jConfirm('你确定要进行删除吗?', '是否删除', function(r) {
		if (r) {
		  	var param = {

				id:value
			}
			doPost("sysMenu!del.action", param, function(data) {
						if (data.status) {
							 
							window.parent.parent.jAlert(data.message, "系统提示");
							window.location.reload();
						}else{
							window.parent.parent.jAlert(data.message, "系统提示");
						}
				});
	 	}
 	});
 }
</script>


</head>

<body style="background-color: transparent;"
	onload="showGridTree();gridTree.openAllNodes()">
	<table class="tableContent">
		<tbody>
			<tr id="topRow">
				<td id="topLeft"></td>
				<td id="topMiddle"></td>
				<td id="topRight"></td>
			</tr>
			<tr id="middleRow">
				<td id="middleLeft"></td>
				<td id="tdContent" bgColor="#ffffff">


					<h2 class="underline">系统菜单管理</h2>

					<div id="gridTreeDiv"></div> <!-- grid的容器. -->
					<div id="grid1_container"></div></td>
				<td id="middleRight"></td>
			</tr>
			<tr id="bottomRow">
				<td id="bottomLeft"></td>
				<td id="bottomMiddle"></td>
				<td id="bottomRight"></td>
			</tr>
		</tbody>
	</table>
</body>
</html>
