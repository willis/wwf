<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
	<%@ include file="/include/taglibs.jsp"%>
	<%@ include file="/include/jquery.jsp"%>

</head>
<body>
<form action="" method="post" >
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
												onclick="">删除</a>
										</li>
										<li>
											<a class="add" href="javascript:"
												 onclick="window.parent.showWindow('${cxp}/manager/bot/bot_edit.jsp','添加',400,500)">添加</a>
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
							<table class="table" id="datagrid">
						<thead>
						<tr id="myHead">
							<th style="width: 80px;">
								<label class="checkbox">
									<input type="checkbox" name="c_all"
										onClick="selectAll(this.form,this.checked,this.nextSibling)">
									全选
								</label>
							</th>
						
							<th>
								操作
							</th>
							<th>
								网站名称
							</th>
							<th>
								网站链接
							</th>
							<th>
								网站英文名称
							</th>
							<th>
								启动状态
							</th>
							<th>
								启动线程数
							</th>
						</tr>
					</thead>
						<tbody id="dataGrid" >
						
							 
						</tbody>
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
</body>
<script>

		 var dataGrid =  new MaxTable();
		 dataGrid.initialize(
		  	{
		  		table:'dataGrid',
		  		loading:'loading',
		  		id:'id',
		  		queryUrl:'weburlAction!list.action',
		  		headerColumns:[{id:'id',renderer:IdCheckBoxRenderer},
		  		{id:'id',renderer:editRenderer},
		  		{id:'siteName'},
		  		{id:'url'},
		  		{id:'enName'},
		  		{id:'status',renderer:statusRenderer},
		  		{id:'threadNum'}
		  		]
		  	}
		  );
		 function query(){
			 dataGrid.onLoad({});
		 } 	
		  query();
		  function editRenderer(idValue,value,record){
			     	var txt="";
			     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"weburlAction!edit.action?id="+idValue+"\",\"编辑\",400,500)'>编辑</a> ";
			     	txt+= " <a href='javascript:;' onclick='start("+value+");'>启动</a>  <a href='javascript:;' onclick='stop("+value+");' >停止</a>";
			  
			     	return txt;
		 }
		  function statusRenderer(idValue,value,record){
		     	switch(value){
		     	case 1:
		     		return '<font color=green>已启动</font>';
		     	case 0:
		     		return '<font color=red>已停止</font>';
		     	default:
		     		return '<font color=red>已停止</font>';
		     	}
	    }
		  function start(id){
			
				var message = "你确认要启动对这个URL的抓取吗？";
				window.parent.parent.jConfirm(message, '操作确认', function(r) {
						
							if (r) {
									var param = {id:id};
					
									doPost("weburlAction!start.action", param, function(data) {
										
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
		  function stop(id){
				
				var message = "你确认要停止对这个URL的抓取吗？";
				window.parent.parent.jConfirm(message, '操作确认', function(r) {
						
							if (r) {
									var param = {id:id};
					
									doPost("weburlAction!stop.action", param, function(data) {
										
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

</html>