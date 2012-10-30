<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
	<head>
		<title>网站后台</title>

		<style>
th {
	vertical-align: middle;
}

label.checkbox {
	cursor: pointer;
	min-width: 5em;
	overflow: auto;
	vertical-align: middle;
}

label.checkbox * {
	vertical-align: middle;
}

label.checkbox input {
	float: left;
	display: inline-block;
	margin-top: 1px;
	margin-right: 2px; *
	float: none; *
	margin-top: 0px; *
	margin-right: 0px;
}
</style>
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

			

				<table class="table">
					<thead>
						<tr>
							<th>
								<label class="checkbox">
									<input class="checkbox" type="checkbox" name="c_all"
										onClick="selectAll(this.form,this.checked,this.nextSibling)">
								
								</label>
							</th>
							<th style="width: 45%">
								权限代码
							</th>
							<th style="width: 45%">
								权限描述
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
	</form>
		<script>

		 var myTable1 =  new MaxTable();
		 myTable1.initialize(
		  	{
		  		table:'myTable',
		  		loading:'loading',
		  		id:'id',
		  		queryUrl:'sysPopedom!list.action',
		  		headerColumns:[{id:'id',name:'操作',renderer:IdCheckBoxRenderer},{id:'code',name:'权限代码',renderer:editRenderer},{id:'describe',name:'权限描述'}]
		  	}
		  )
		  
	      
	    
	     function query(){
	     	myTable1.page.totalRowNum = 0;
	    	myTable1.onLoad({"sysPopedom.code":$("#code").val(),"sysPopedom.describe":$("#describe").val()});
	     } 	
	     function editRenderer(idValue,value){
	     	return "<a href='javascript:' onclick='window.parent.showWindow(\"${cxp }/user/sysPopedom!edit.action?id="+idValue+"\",\"修改权限代码\",200,400)'>"+value+"</a>"
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