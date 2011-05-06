<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<script type="text/javascript"
	src="${cxp}/appjs/plugin/table/jquery.table-min.js"></script>

<head>
<title>网站后台</title>

<style type="text/css">
label.checkbox {
	cursor: pointer;
}
</style>
</head>
<body>

	<table id="loading">
		<tr>
			<td colspan="3"></td>
		</tr>
		<tr>
			<td align="center">当前可添加的权限</td>
			<td>&nbsp;</td>
			<td align="center">已添加权限</td>
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
								<th>权限代码</th>
								<th>权限描述</th>


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
								<th>权限代码</th>
								<th>权限描述</th>

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
		  		queryUrl:'sysRole!getNotCheckPopedoms.action',
		  		headerColumns:[{id:'id',name:'操作',renderer:IdCheckBoxRenderer},{id:'code',name:'权限代码'},{id:'describe',name:'权限描述'}]
		  	}
		  )
		   myTable2.initialize(
		  	{
		  		table:'myTable2',
		  		loading:'loading',
		  		id:'id',
		  		isSort:false,
		  		queryUrl:'sysRole!getCheckPopedoms.action',
		  		headerColumns:[{id:'id',name:'操作',renderer:IdCheckBoxRenderer},{id:'code',name:'权限代码'},{id:'describe',name:'权限描述'}]
		  	}
		  )
		 
	     function query(){
	     	myTable1.page.totalRowNum = 0;
	    	myTable1.onLoad({ id:${param.id}});
	    	myTable2.page.totalRowNum = 0;
	    	myTable2.onLoad({ id:${param.id}});
	    	
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
	doPost("sysRole!addRolePopedoms.action", param, function(data) {
				if (data.status) {
				 window.parent.parent.jAlert(data.message, "系统提示");
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
	doPost("sysRole!delRolePopedoms.action", param, function(data) {
				if (data.status) {
				window.parent.parent.jAlert(data.message, "系统提示");
					query();
					 
				}else{
					window.parent.parent.jAlert(data.message);
				}
		});

}
		 
		</script>
</body>
</html>