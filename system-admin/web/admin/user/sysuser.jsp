<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
		<head>
		<title>网站后台</title>

		<style type="text/css">
			label.checkbox {
				cursor: pointer;
			}
		</style>
		
		
	</head>
	<body  >
	<form action="sysUser!userList.action" method="post" >
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
							用户管理
						</h2>
				<div class="editor" style="width: 100%;">
					<div id="editor_left"></div>
					<div id="editor_contents">
						<ul class="editor_link">
					<li>
					<pager:checkPopedom code="resource_add"> <a   name="add" class="add"
									href="javascript:" onclick="window.parent.showWindow('${cxp}/user/sysuser_add.jsp','添加用户',400,500)">添加</a></pager:checkPopedom>
					 </li>
								 
					<li><a class="delete" href="javascript:" onclick="removeSelect(1)">删除</a></li>
					<li><a class="modify" href="javascript:" onclick="removeSelect(2)">冻结</a></li>
					<li><a class="modify" href="javascript:" onclick="removeSelect(0)">恢复正常</a></li>
					<li><a class="modify" href="javascript:" onclick="updateSelect()">修改密码</a></li>
					<li><a class="find" href="javascript:void($('#find_01').toggle())">查询</a></li>
						
						</ul>
					</div>
					<div id="editor_right"></div>
				</div>
							<table class="table" id="find_01" style="display:none">
			
	
			<tr><td>
			用户名：<input type="text" name="username" id="username"   >姓名：<input type="text" id="truename" name="truename"   >&nbsp;&nbsp;状态：<select id="status" name="status">
			<option value="-1">全部</option>
			<option value="0" selected>正常</option>
			<option value="1">已删除</option>
			<option value="2">已冻结</option>
			</select>
<input type="button"  class="button" value="查询"  onclick="query()">
			</td></tr>
	
			</table>
				<table id="datagrid" >
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
				url:'sysUser!userList.action',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: true,
				loadMsg:'数据加载中，请稍后......',
				idField:'id',
				frozenColumns:[[
				                {field:'ck',checkbox:true},
				                {title:'操作',field:'id',width:140,formatter:function(value,rec){
				                 	var txt="";
				        	     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/user/sysUser!getSysUserInfo.action?id="+value+"\",\"修改\",300,400)'>编辑</a>"
				        	     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/user/sysuser_role.jsp?id="+value+"&method=get\",\"角色配置\",400,800)'>角色配置</a>"
				        	     	return txt;
				                	
				                }}
				]],
				columns:[[
					{field:'username',title:'用户名',width:120},
					{field:'truename',title:'姓名',width:120,sortable:true},
					{field:'sex',title:'性别',width:120},
					{field:'status',title:'状态',width:120,formatter:function(value,rec){
						switch(value){

						case 0:
							return '<font color=green>正常</font>';
						case 1:
							return '<font color=red>已删除</font>';
					    case 2:
					   	    return '<font color=red>冻结</font>';
					   	default:
					   		return '未知'+value

					}
						
					}},
					{field:'regtime',title:'注册时间',width:120}
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
	
		

    function removeSelect(value){
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
		var message = "您真的要还原这<font color='red'>"+ids.length+"</font>个用户吗？";
		if(value==1){
			message = "您真的要删除这<font color='red'>"+ids.length+"</font>个用户吗？";
		}else
		if(value==2){
			message = "您真的要冻结这<font color='red'>"+ids.length+"</font>个用户吗？";
		} 
	
				 
				window.parent.parent.jConfirm(message, '操作确认', function(r) {
				
					if (r) {
							var param = {
							
								ids:cs,
								status:value
							}
			
							doPost("${cxp}/user/sysUser!removeByIds.action", param, function(data) {
								
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


		function updateSelect(){
		
			var ids  = $('#datagrid').datagrid('getSelections');
			if(ids.length == 0){
				window.parent.parent.jAlert("请选择记录", "系统提示");
				return ;
			};
			
			var cs = "";
			for(var i=0;i<ids.length;i++){
				if(i>0){
					cs+=",";
				}
				cs+=ids[i].id;
			}
			window.parent.showWindow('${cxp}/user/user_password.jsp?ids=' + cs ,'修改密码',100,200);
		
		}
		function query (){
			// 获取查询参数
			var queryParams = $('#datagrid').datagrid('options').queryParams;
		
			var status = $.trim($("#status").val());
			var truename = $.trim($("#truename").val());
			var username = $.trim($("#username").val());
			 // condition对应action的实例变量condition
			queryParams["status"] = status;
			queryParams["truename"] = truename;
			queryParams["username"] = username;
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
	 
		 
		</script>	
	
	

	</body>
</html>