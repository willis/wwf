<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>

<head>
<title>网站后台</title>


<style type="text/css">
table.form {
	width: 45em;
	margin: 20px auto 0px auto;
}

table.form tr {
	height: 2em;
}
</style>

</head>
<body>

	<table>

		<tr>
			<td id="loading" colspan="3"></td>
		</tr>
		<tr>
			<td align="center">当前可添加的用户</td>
			<td>&nbsp;</td>
			<td align="center">已添加用户</td>
		</tr>
		<tr>
			<td valign="top">
				<form>
					<table class="table">
						<thead>
							<tr>
								<th style="width: 80px"><label class="checkbox"> <input
										class="checkbox" type="checkbox" name="c_all"
										onClick="selectAll(this.form,this.checked,this.nextSibling)">
										全选 </label></th>
								<th>用户名</th>
								<th>姓名</th>


							</tr>
						</thead>

						<tbody id="myTable1">
						</tbody>
					</table>
				</form></td>
			<td>
				<div>
					<img src="${cxp }/images/arrow-1.gif" onclick="addSelect()"
						style="cursor: pointer" />
				</div>
				<div>&nbsp;</div>
				<div>
					<img src="${cxp }/images/arrow-2.gif" onclick="removeSelect()"
						style="cursor: pointer" />
				</div></td>
			<td valign="top">
				<form>
					<table class="table">
						<thead>
							<tr>
								<th style="width: 80px"><label class="checkbox"> <input
										class="checkbox" type="checkbox" name="c_all"
										onClick="selectAll(this.form,this.checked,this.nextSibling)">
										全选 </label></th>
								<th>用户名</th>
								<th>姓名</th>

							</tr>
						</thead>

						<tbody id="myTable2">
						</tbody>
					</table>
				</form></td>
		</tr>
	</table>

	<script>

		 var myTable1 =  new MaxTable();
		 
		 var myTable2 =  new MaxTable();
		 
		 myTable1.initialize(
		  	{
		  		table:'myTable1',
		  		loading:'loading',
		  		id:'id',
		  		isSort:false,
		  		queryUrl:'sysGroup!getNotCheckUsers.action',
		  		headerColumns:[{id:'id',name:'操作',renderer:IdCheckBoxRenderer},{id:'username',name:'用户名',width : 50},{id:'truename',name:'姓名',width : 50}]
		  	}
		  )
		   myTable2.initialize(
		  	{
		  		table:'myTable2',
		  		loading:'loading',
		  		id:'id',
		  		isSort:false,
		  		queryUrl:'sysGroup!getCheckUsers.action',
		  		headerColumns:[{id:'id',name:'操作',renderer:IdCheckBoxRenderer},{id:'username',name:'用户名',width : 50},{id:'truename',name:'姓名',width : 50}]
		  	}
		  )
		 
	     function query(){
	     	myTable1.page.totalRowNum = 0;
	    	myTable1.onLoad({ groupId:${param.id}});
	    	myTable2.page.totalRowNum = 0;
	    	myTable2.onLoad({ groupId:${param.id} });
	    	
	     }
		 query();
		 

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
	doPost("sysGroup!addSysUsers.action", param, function(data) {
				if (data.status) {
				    window.parent.parent.jAlert(data.message, "系统提示");
					query();
					 
				}else{
					window.parent.parent.jAlert(data.message, "系统提示");
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
	doPost("sysGroup!delSysUsers.action", param, function(data) {
				if (data.status) {
				    window.parent.parent.jAlert(data.message, "系统提示");
					query();
					 
				}else{
					window.parent.parent.jAlert(data.message, "系统提示");
				}
		});

}
		 
		</script>
</body>
</html>