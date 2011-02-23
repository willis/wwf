<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<script type="text/javascript"
		src="${cxp}/appjs/plugin/table/jquery.table-min.js"></script>
		<head>
		<title>角色维护</title>

		<style type="text/css">
			label.checkbox {
				cursor: pointer;
			}
		</style>
		
		
	</head>
	<body>
	<form action="${cxp}/user/sysRole!list.action.action" method="post" >
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
							角色维护
						</h2>
				<div class="editor" style="width: 100%;">
					<div id="editor_left"></div>
					<div id="editor_contents">
						<ul class="editor_link">
					<li>
					 <a   name="add" class="add"
									href="javascript:" onclick="window.parent.showWindow('${cxp}/user/sysrole_edit.jsp','添加角色',200,400)">添加</a>
					 </li>
								 
					<li><a class="delete" href="javascript:" onclick="removeSelect()">删除</a></li>
				
					<li><a class="find" href="javascript:void($('#find_01').toggle())">查询</a></li>
						
						</ul>
					</div>
					<div id="editor_right"></div>
				</div>
							<table class="table" id="find_01" style="display:none">
			
	
			<tr><td>
			角色名称：<input type="text" name="name" id="name"   >角色描述：<input type="text" id="describe" name="describe"   >
<input type="button"  class="button" value="查询"  onclick="query()">
			</td></tr>
	
			</table>

				<table class="table" id="senfe" >
					<thead>
						<tr>
							<th>
								<label class="checkbox">
									<input type="checkbox" name="c_all"
										onClick="selectAll(this.form,this.checked,this.nextSibling)">
									全选
								</label>
							</th>
							<th >
								操作
							</th>
							<th>
								角色名称
							</th>
							<th>
								角色描述
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
		  		queryUrl:'sysRole!list.action',
		  		headerColumns:[{id:'id',name:'操作',renderer:IdCheckBoxRenderer},
		  		{id:'id',name:'操作',renderer:editRenderer},
		  		{id:'name',name:'角色名称'},
		  		{id:'describe',name:'角色描述'}
		  		]
		  	}
		  )
		  
	      
	    
	     function query(){
	     	myTable1.page.totalRowNum = 0;
	    	myTable1.onLoad({name:$("#name").val(),describe:$("#describe").val()});
	     } 	
	     function editRenderer(idValue,value){
	     	var txt="";
	     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/user/sysRole!getSysRoleInfo.action?id="+idValue+"\",\"修改\",200,400)'>编辑</a>"
	     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/user/sysrole_popedom.jsp?id="+idValue+"\",\"配置权限\",400,600)'>配置权限</a>"
	     	txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"${cxp}/user/sysRole!listSysMenu.action?roleId="+idValue+"\",\"配置系统菜单\",400,600)'>配置系统菜单</a>"
	     	return txt;
	     }	 
	     
		 query();
		 

    function removeSelect(){
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
	

    message = "您真的要删除这<font color='red'>"+ids.length+"</font>个用户吗？";
	

			 
			window.parent.parent.jConfirm(message, '操作确认', function(r) {
			
				if (r) {
						var param = {
							ids:cs
						}
		
						doPost("${cxp}/user/sysRole!del.action", param, function(data) {
							
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