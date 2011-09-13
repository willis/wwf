<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
	<body >

		<table  >
		<tr><td  id="loading" colspan="3"></td></tr>
		
		<tr><td align="center" >当前可添加的角色</td><td>&nbsp;</td><td align="center">已添加角色</td></tr>
		<tr><td valign="top">
		<form>
				<table class="table" id="noCheckTable">
					
				</table>
				</form>
		</td><td >
		<div>
		<img src="${cxp }/images/arrow-1.gif" onclick="addSelect()" style="cursor:pointer"/>
		</div>
		<div>&nbsp;</div>
		<div>
		<img src="${cxp }/images/arrow-2.gif"  onclick="removeSelect()" style="cursor:pointer"/>
		</div>
			</td><td valign="top">
			<form>
				<table class="table" id="checkTable">
					
				</table>
				</form>
		</td></tr></table>

		<script>
		$(function(){
			$('#noCheckTable').datagrid({
				nowrap: false,
				striped: true,
				collapsible:true,
				fitColumns:true,
				url:'sysUser!getNotCheckRoles.action?id=${param.id}',
				sortName: 'id',
				sortOrder: 'desc',
				width : 400,
				remoteSort: true,
				loadMsg:'数据加载中，请稍后......',
				idField:'id',
				frozenColumns:[[
				                {field:'ck',checkbox:true},
				                {title:'操作',field:'id',width:60,formatter:function(value,rec){
				                 	var txt="";
				        	     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/user/sysUser!getSysUserInfo.action?id="+value+"\",\"修改\",300,400)'>编辑</a>"
				        	     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/user/sysuser_role.jsp?id="+value+"&method=get\",\"角色配置\",400,600)'>角色配置</a>"
				        	     	return txt;
				                	
				                }}
				]],
				columns:[[
					{field:'name',title:'名称',width:120},
					{field:'describe',title:'描述',width:120}
		
				]],
				pagination:true,
				rownumbers:true
			
			});
			var p = $('#noCheckTable').datagrid('getPager');
			if (p){
				$(p).pagination({
					onBeforeRefresh:function(){
						
					}
				});
			}
		});
		$(function(){
			$('#checkTable').datagrid({
				nowrap: false,
				striped: true,
				collapsible:true,
				fitColumns:true,
				width : 400,
				url:'sysUser!getCheckRoles.action?id=${param.id}',
				sortName: 'id',
				sortOrder: 'desc',
				remoteSort: true,
				loadMsg:'数据加载中，请稍后......',
				idField:'id',
				frozenColumns:[[
				                {field:'ck',checkbox:true},
				                {title:'操作',field:'id',width:60,formatter:function(value,rec){
				                 	var txt="";
				        	     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/user/sysUser!getSysUserInfo.action?id="+value+"\",\"修改\",300,400)'>编辑</a>"
				        	     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/user/sysuser_role.jsp?id="+value+"&method=get\",\"角色配置\",400,600)'>角色配置</a>"
				        	     	return txt;
				                	
				                }}
				]],
				columns:[[
					{field:'name',title:'名称',width:120},
					{field:'describe',title:'描述',width:120}
		
				]],
				pagination:true,
				rownumbers:true
			
			});
			var p = $('#checkTable').datagrid('getPager');
			if (p){
				$(p).pagination({
					onBeforeRefresh:function(){
						
					}
				});
			}
		});

		 

function addSelect(){
	 
	var ids  = getCheckedValuesByContainer("c",$("#myTable1"));
	if(ids.length == 0){
		window.parent.parent.jAlert("请选择添加项", "系统提示");
		return ;
	};
	var param = {
		cs:ids,
		id:${param.id}
	}
	doPost("sysUser!addSysRoles.action", param, function(data) {
				if (data.status) {
				window.parent.parent.jAlert(data.message);
					query();
					 
				}else{
					window.parent.parent.jAlert(data.message);
				}
		});

}

function removeSelect(){
	var ids = "";
	var ids  = getCheckedValuesByContainer("c",$("#myTable2"));
	if(ids.length == 0){
		window.parent.parent.jAlert("请选择删除项", "系统提示");
		return ;
	};
	var param = {
		cs:ids,
		id:${param.id}
	}
	doPost("sysUser!delSysRoles.action", param, function(data) {
				if (data.status) {
				window.parent.parent.jAlert(data.message);
					query();
					 
				}else{
					window.parent.parent.jAlert(data.message);
				}
		});

}
		 
		</script>
	</body>
</html>