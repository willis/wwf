<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
	<head>
	<title></title>


	</head>
	<body  >
		<form action="${cxp}/user/sysPopedom!list.action" method="post" >
		<table class="tableContent">
			<tbody>
				<tr id="topRow">
					<td id="topLeft">
					</td>
					<td id="topMiddle">
					</td>
					<td id="topRight">
					</td>
				</tr>
				<tr id="middleRow">
					<td id="middleLeft">
					</td>
					<td id="tdContent" bgColor="#ffffff">

						<h2 class="underline" id="loading">
							权限代码管理
						</h2>
			<div class="editor" style="width: 100%;">
				<div id="editor_left"></div>
				<div id="editor_contents">
					<ul class="editor_link">
						<li>
							<a name="add" class="add" href="javascript:"
								onclick="window.parent.showWindow('${cxp }/user/syspopedom_edit.jsp','添加权限',200,400)">添加</a>

						</li>
						<li>
							<a class="delete" href="javascript:removeSelect();" name="delSub">删除</a>
						</li>
						<li>
							<a class="find" href="javascript:void($('#find_01').toggle())">查询</a>
						</li>


						
					</ul>
				</div>
				<div id="editor_right"></div>
			</div>
			<table class="table" id="find_01" style="display: none">


				<tr>
					<td>
						代码：
						<input id="code" type="text" name="code"
							value="${ param.code}">
						描述：
						<input type="text" id="describe" name="describe"
							value="${ param.describe}">
						&nbsp;&nbsp;
						<input type="button" class="button" value="查询" onclick="query()">
					</td>
				</tr>

			</table>

			

				<table class="table" id="datagrid">
				

				</table>
			

					</td>
					<td id="middleRight">
					</td>
				</tr>
				<tr id="bottomRow">
					<td id="bottomLeft">
					</td>
					<td id="bottomMiddle">
					</td>
					<td id="bottomRight">
					</td>
				</tr>
			</tbody>
		</table>
	</form>
		<script>
		$(function(){
			$('#datagrid').datagrid({
				nowrap: false,
				striped: true,
				collapsible:true,
				fitColumns:true,
				url:'sysPopedom!list.action',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: true,
				loadMsg:'数据加载中，请稍后......',
				idField:'id',
				frozenColumns:[[
				                {field:'ck',checkbox:true},
				                {title:'操作',field:'id',width:140,formatter:function(value,rec){
				                	return "<a href='javascript:' onclick='window.parent.showWindow(\"${cxp }/user/sysPopedom!edit.action?id="+value+"\",\"修改权限代码\",200,400)'>修改权限代码</a>";
				                	
				                }}
				]],
				columns:[[
					{field:'code',title:'权限代码',width:120},
					{field:'describe',title:'权限描述',width:120}
				]],
				pagination:true,
				rownumbers:true
			
			});
			var p = $('#datagrid').datagrid('getPager');
			if (p){
				$(p).pagination({
					onBeforeRefresh:function(){
						
					}
				});
			}
		});
	
		
		
		function query (){
			// 获取查询参数
			var queryParams = $('#datagrid').datagrid('options').queryParams;
		
			var code = $.trim($("#code").val());
			var describe = $.trim($("#describe").val());
			 // condition对应action的实例变量condition
			queryParams["code"] = code;
			queryParams["describe"] = describe;

			 // 重置查询页数为1
			$('#datagrid').datagrid('options').pageNumber = 1;
			 
			var p = $('#datagrid').datagrid('getPager');
			 
			if (p){
				$(p).pagination({
					pageNumber:1
					});
				}
			// 刷新列表数据
			$('#datagrid').datagrid('reload');
		}
	    

	     function editRenderer(idValue,value){
	     	return "<a href='javascript:' onclick='window.parent.showWindow(\"${cxp }/user/sysPopedom!edit.action?id="+idValue+"\",\"修改权限代码\",200,400)'>"+value+"</a>"
	     }	  
	
		 

	 
    function removeSelect(){
	var ids  = $('#datagrid').datagrid('getSelections');
	
	if(ids.length == 0)
	{
		window.parent.parent.jAlert("请选择记录", "系统提示");
		return ;
	}
	var cs = "";
	for(var i=0;i<ids.length;i++){
		if(i>0){
			cs+=",";
		}
		cs+=ids[i].id;
	}
	

    message = "您真的要删除这<font color='red'>"+ids.length+"</font>个权限代码吗？";
	

			 
			window.parent.parent.jConfirm(message, '操作确认', function(r) {
			
				if (r) {
						var param = {
							c:cs
						}
		
						doPost("${cxp}/user/sysPopedom!del.action", param, function(data) {
							
									if (data.status) {
										query();
										window.parent.parent.jAlert(data.message, "系统提示");
									}else{
									    query();
										window.parent.parent.jAlert(data.message, "系统提示");
									}
							});
				}
	});

}
	 
		 
		</script>
	</body>
</html>