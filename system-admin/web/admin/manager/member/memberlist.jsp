<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<%@ include file="/include/taglibs.jsp"%>
	<%@ include file="/include/jquery.jsp"%>
	<script type="text/javascript"
		src="${cxp}/appjs/plugin/table/jquery.table-min.js"></script>
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

							<table class="table" id="senfe">
								<thead>
									<tr>
										<th style="width: 80px;">
											<label class="checkbox">
												<input type="checkbox" name="c_all"
													onClick="selectAll(this.form,this.checked,this.nextSibling)">
												全选
											</label>
										</th>
										<th style="width: 100px;">
											操作
										</th>
										<th>
											用户名
										</th>
										<th>
											昵称
										</th>
										<th>
											性别
										</th>

										<th>
											状态
										</th>
										<th>
											最后登录时间
										</th>

									</tr>
								</thead>
								<tbody id="myTable">


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

		<script>

		 var myTable1 =  new MaxTable();
		 myTable1.initialize(
		  	{
		  		table:'myTable',
		  		loading:'loading',
		  		id:'id',
		  		queryUrl:'memberAction!list.action',
		  		headerColumns:[{id:'id',name:'操作',renderer:IdCheckBoxRenderer},
		  		{id:'id',name:'操作',renderer:editRenderer},
		  		{id:'username',name:'用户名'},
		  		{id:'name',name:'昵称'},
		  		{id:'sex',name:'性别'},
		  		{id:'status',name:'状态',renderer:statusRenderer},
		  		{id:'lastLoginDate',name:'最后登录时间',renderer:regtimeRenderer}
		  		]
		  	}
		  )
		  
	      
	    
	     function query(){
	     	myTable1.page.totalRowNum = 0;
	    	myTable1.onLoad({username:$("#username").val(),name:$("#name").val(),status:$("#status").val()});
	     } 	
	     function editRenderer(idValue,value){

	     	var txt="";
	     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/manager/member/memberAction!getById.action?id="+idValue+"\",\"查看\",600,600)'>查看</a>"
	    // 	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/user/sysuser_role.jsp?id="+idValue+"&method=get\",\"角色配置\",400,600)'>角色配置</a>"
	     	return txt;
	     }	 
	      function regtimeRenderer(idValue,value){
	     	if(value!=null)
	     	return value.substring(0,value.length-2);
	     	else
	     	return '';
	     }	   
		 query();
		 

    function removeSelect(value){
	var ids  = getCheckedValuesByContainer("c",$("#myTable"));
	
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
		cs+=ids[i];
	}
	var message = "您真的要还原这<font color='red'>"+ids.length+"</font>个用户吗？";
	if(value==3){
		message = "您真的要删除这<font color='red'>"+ids.length+"</font>个用户吗？";
	}else
	if(value==2){
		message = "您真的要禁用这<font color='red'>"+ids.length+"</font>个用户吗？";
	} 

			 
			window.parent.parent.jConfirm(message, '操作确认', function(r) {
			
				if (r) {
						var param = {
						
							ids:cs,
							status:value
						}
		
						doPost("${cxp}/manager/member/memberAction!removeByIds.action", param, function(data) {
							
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



function statusRenderer(idValue,value){
switch(value){

	case 1:
		return '<font color=green>正常</font>';
	case 2:
		return '<font color=red>已禁用</font>';
    case 3:
   	    return '<font color=red>已删除</font>';
   	default:
   		return '未知'+value

}
 
}

</script>
</form>
</body>
</html>