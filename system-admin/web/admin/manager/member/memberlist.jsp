<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<%@ include file="/include/taglibs.jsp"%>
	<%@ include file="/include/jquery.jsp"%>
	<head>
		<title>会员管理</title>

	</head>
	<body>
		<form action="memberAction!list.action" method="post" >
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
								会员管理
							</h2>
							<div class="editor" style="width: 100%;">
								<div id="editor_left"></div>
								<div id="editor_contents">
									<ul class="editor_link">

										<li>
											<a class="delete" href="javascript:"
												onclick="removeSelect(3)">删除</a>
										</li>
										<li>
											<a class="modify" href="javascript:"
												onclick="removeSelect(2)">禁用</a>
										</li>
										<li>
											<a class="modify" href="javascript:"
												onclick="removeSelect(1)">恢复正常</a>
										</li>
										<li>
											<a class="find"
												href="javascript:void($('#find_01').toggle())">查询</a>
										</li>

									</ul>
								</div>
								<div id="editor_right"></div>
							</div>
							<table class="table" id="find_01" style="display: none">


								<tr>
									<td>
										用户名：
										<input type="text" name="username" id="username">
										昵称：
										<input type="text" id="name" name="name">
										&nbsp;&nbsp;状态：
										<select id="status" name="status">
											<option value="-1">
												全部
											</option>
											<option value="1" selected>
												正常
											</option>
											<option value="2">
												已禁用
											</option>
											<option value="3">
												已删除
											</option>
										</select>
										<input type="button" class="button" value="查询"
											onclick="query()">
									</td>
								</tr>

							</table>

							<table id="datagrid">
								
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

		<script>
		$(function(){
			$('#datagrid').datagrid({
				nowrap: false,
				striped: true,
				collapsible:true,
				fitColumns:true,
				url:'memberAction!list.action',
				sortName: 'username',
				sortOrder: 'desc',
				remoteSort: true,
				loadMsg:'数据加载中，请稍后......',
				idField:'id',
				frozenColumns:[[
				                {field:'ck',checkbox:true},
				                {title:'操作',field:'id',width:80,sortable:true}
				]],
				columns:[[
					{field:'username',title:'用户名',width:120},
					{field:'name',title:'昵称',width:120,sortable:true},
					{field:'sex',title:'性别',width:120},
					{field:'status',title:'状态',width:120,formatter:function(value,rec){
						
						switch(value){
						
						case 1:
							return '<font color=green>正常</font>';
						case 2:
							return '<font color=red>已禁用</font>';
					    case 3:
					   	    return '<font color=red>已删除</font>';
					   	default:
					   		return '未知'+value;
					
					}

                    }},
					{field:'lastLoginDate',title:'最后登录时间',width:120}
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
			//var roomNum = $.trim($("#condition.roomNum").val());
			 // condition对应action的实例变量condition
			//queryParams["condition.roomNum"] = roomNum;
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
</form>
</body>
</html>