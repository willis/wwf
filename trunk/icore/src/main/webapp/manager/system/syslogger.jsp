<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
<body>
	<form action="systemLog!loginfoList.action" method="post">
		<table class="tableContent">
			<tbody>
				<tr id="topRow">
					<td id="topLeft"></td>
					<td id="topMiddle"></td>
					<td id="topRight"></td>
				</tr>
				<tr id="middleRow">
					<td id="middleLeft"></td>
					<td id="tdContent" bgColor="#ffffff">

						<h2 class="underline" id="loading">系统日志</h2>


						<table class="table" id="senfe">
							<thead>
								<tr>
									<th style="width: 80px;"><label class="checkbox">
											<input type="checkbox" name="c_all"
											onClick="selectAll(this.form,this.checked,this.nextSibling)">
											全选 </label></th>

									<th>姓名</th>
									<th>日志类型</th>
									<th>操作内容</th>

									<th>IP</th>
									<th>创建时间</th>

								</tr>
							</thead>
							<tbody id="myTable">


							</tbody>
						</table></td>
					<td id="middleRight"></td>
				</tr>
				<tr id="bottomRow">
					<td id="bottomLeft"></td>
					<td id="bottomMiddle"></td>
					<td id="bottomRight"></td>
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
		  		queryUrl:'systemLog!loginfoList.action',
		  		headerColumns:[{id:'id',name:'操作',renderer:IdCheckBoxRenderer},
		  		{id:'username',name:'用户名'},
		  		{id:'logtitle',name:'姓名'},
		  		{id:'logcontent',name:'性别'},
		  		{id:'ip',name:'状态'},
		  		{id:'createat',name:'注册时间',renderer:regtimeRenderer}
		  		]
		  	}
		  )
		  
	      
	    
	     function query(){
	     	myTable1.page.totalRowNum = 0;
	    	myTable1.onLoad({username:$("#username").val(),truename:$("#truename").val(),status:$("#status").val()});
	     } 	
	     function editRenderer(idValue,value){
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