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

				<table class="table" id="senfe" >
					<thead>
						<tr id="myHead">
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
								姓名
							</th>
							<th>
								性别
							</th>
							
							<th>
								状态
							</th>
							<th>
								注册时间
							</th>
							 
						</tr>
					</thead>
					<tbody id="myTable" >
					
						 
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
		<script>

		 var myTable1 =  new MaxTable();
		 myTable1.initialize(
		  	{
		  		table:'myTable',
		  		loading:'loading',
		  		id:'id',
		  		queryUrl:'sysUser!userList.action',
		  		headerColumns:[{id:'id',renderer:IdCheckBoxRenderer},
		  		{id:'id',renderer:editRenderer},
		  		{id:'username'},
		  		{id:'truename'},
		  		{id:'sex'},
		  		{id:'status',renderer:statusRenderer},
		  		{id:'regtime',renderer:regtimeRenderer}
		  		]
		  	}
		  )
		  
	       myTable1.initSortHead(
	      {head:'myHead',cells:[{index:1,name:'id'},{index:2,name:'username'},{index:3,name:'truename'},{index:4,name:'sex'},{index:5,name:'status'},{index:6,name:'regtime'}]}
	      );
	    
	     function query(){
	     	myTable1.page.totalRowNum = 0;
	    	myTable1.onLoad({username:$("#username").val(),truename:$("#truename").val(),status:$("#status").val()});
	     } 	
	     function editRenderer(idValue,value,record){
	    	//record["id"]
	     	var txt="";
	     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/user/sysUser!getSysUserInfo.action?id="+idValue+"\",\"修改\",300,400)'>编辑</a>"
	     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/user/sysuser_role.jsp?id="+idValue+"&method=get\",\"角色配置\",400,600)'>角色配置</a>"
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



function statusRenderer(idValue,value){
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
 
}

function updateSelect(){
	var ids  = getCheckedValuesByContainer("c",$("#myTable"));
	if(ids.length == 0){
		window.parent.parent.jAlert("请选择记录", "系统提示");
		return ;
	};
	
	var cs = "";
	for(var i=0;i<ids.length;i++){
		if(i>0){
			cs+=",";
		}
		cs+=ids[i];
	}
	window.parent.showWindow('${cxp}/user/user_password.jsp?ids=' + cs ,'修改密码',100,200);

}

	 
		 
		</script>	
	
	

	</body>
</html>