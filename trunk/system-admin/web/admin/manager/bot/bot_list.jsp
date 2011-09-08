<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>jQuery EasyUI</title>
	<%@ include file="/include/taglibs.jsp"%>
	<%@ include file="/include/jquery.jsp"%>

</head>
<body>

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
								数据源管理
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
</body>
<script>
		$(function(){
			$('#datagrid').datagrid({
				nowrap: false,
				striped: true,
				collapsible:true,
				fitColumns:true,
				url:'weburlAction!list.action',
				sortName: 'url',
				sortOrder: 'desc',
				remoteSort: true,
				loadMsg:'数据加载中，请稍后......',
				idField:'id',
				columns:[[
					{field:'siteName',title:'网站名称',width:120},
					{field:'url',title:'网站链接',width:120,sortable:true},
					{field:'enName',title:'网站英文名称',width:120}
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
		function resize(){
			$('#datagrid').datagrid('resize', {
				width:$(window).width(),
				height:400
			});
		}
		function getSelected(){
			var selected = $('#datagrid').datagrid('getSelected');
			if (selected){
				alert(selected.code+":"+selected.name+":"+selected.addr+":"+selected.col4);
			}
		}
		function getSelections(){
			var ids = [];
			var rows = $('#datagrid').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].code);
			}
			alert(ids.join(':'));
		}
		function clearSelections(){
			$('#datagrid').datagrid('clearSelections');
		}
		function selectRow(){
			$('#datagrid').datagrid('selectRow',2);
		}
		function selectRecord(){
			$('#datagrid').datagrid('selectRecord','002');
		}
		function unselectRow(){
			$('#datagrid').datagrid('unselectRow',2);
		}
		function mergeCells(){
			$('#datagrid').datagrid('mergeCells',{
				index:2,
				field:'addr',
				rowspan:2,
				colspan:2
			});
		}
	</script>

</html>