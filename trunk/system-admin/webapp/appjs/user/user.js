function getEditArticleHref(idValue) {

	var param = {
		id : idValue
	}
	var vhttValue = jQuery.param(param); // A=1&B=2&C=3

	var tmpPath =  "sysUser!getSysUserInfo.action?id=" + idValue;
	var txt = "<a href='javascript:;' onclick=\"window.parent.showWindow('" + tmpPath + "','修改用户',300,500)\"\"> 编辑</a> ";


	txt += " <a href=\"javascript:void(0);\" onclick=\"window.parent.showWindow('sysuser_role_list.jsp?" + vhttValue + "','角色配置',400,800)\"\">角色配置</a>";

	
	return txt;

}

function areYouOkDel(idValue) {
	
	window.parent.parent.jConfirm('你确定要进行删除吗?', '是否删除', function(r) {
				// jAlert('是否确定 ' + r, '确定内容');
				if (r) {
					delMe(idValue);
				}
			});
}

function delMe(idValue) {
	var param = {
		c : idValue,
		method : 'del'
	}

	doPost("sysUserAction.do", param, function(data) {
				if (data) {
					query();
					window.parent.parent.jAlert(data.message, "系统提示");
				}

			});
}

/**
 * 弹出编辑对话框
 * 
 * @param {String
 *            ,int } recordedId 入款单id,唯一主键
 */
function showEditDialog(templateId) {

	var param = {
		templateId : templateId,
		action : 'process',
		op : 'editTemplate',
		to : 'define',
		randomid : Math.random()
	}



	var vhttValue = jQuery.param(param); // A=1&B=2&C=3

	document.location = vPath + "?" + vhttValue;

}

/**
 * 取得对象,通过id方式
 * 
 * @param {}
 *            id
 * @return {}
 */
function getObject(id) {
	var objDiv = document.getElementById(id);
	return objDiv;
}

var dsConfig = {
	uniqueField : 'id',
	fields : [makeFieldsArray('id','op', 'username', 'truename', 'sex', 'email', 'tel', 'other','status', 'regtime')]
};

var colsConfig = [
		{ id : 'id'  , header : "id" , width : 50 , 	editable:false ,  
			frozen : true,
			frozenable : true,
		  
		isCheckColumn : true
		},
		{
			id : 'op',
			header : "操作",
			width : 80,
			//frozen : true,
			//frozenable : true,
			renderer : function(value, record, columnObj, grid, colNo, rowNo) {
			var idValue = record['id'];
			
			return getEditArticleHref(idValue);

			}
		},
	    displayColumnByRenderer('username', "用户名", 80),
		displayColumnByRenderer('truename', "姓名", 80),
		displayColumnByRenderer('extendf5', "姓名缩写", 80),
		displayColumnByRenderer('sex', "性别", 40, GT.Grid.mappingRenderer( {'0': '未知' ,'1':'男', '2':'女'})),
		displayColumnByRenderer('email', "邮箱", 140),
		displayColumnByRenderer('tel', "电话", 100),
		displayColumnByRenderer('other', "其它", 140),
		displayColumnByRenderer('status', "状态", 80,GT.Grid.mappingRenderer({0:'<font color=green>正常</font>',1:'<font color=red>已删除</font>',2:'<font color=red>冻结</font>'},'未知')),
		displayDateRenderer('regtime', "创建日期", null, 130)

	

];
var loadURL = "sysUser!userList.action";
var exportURL="sysUserAction.do?method=exportXls";
var mygrid = createGrid( loadURL, dsConfig,
		colsConfig, "grid1", "grid1_container", 10, exportURL, "导出");
//查询
function query() {


	var param = {
		username : GT.U.getValue(GT.$('username')),
		truename : GT.U.getValue(GT.$('truename')),
		status : GT.U.getValue(GT.$('status')),

		randomid : Math.random()
	}

	gridQuery(mygrid, param);

}

// 界面中只读的时候被设置为 disable的 元素 id
var disableElementIds = new Array();
function init() {
	mygrid.render();
	query();

}
/**
 * 新建
 * 
 * @return {}
 */
function showNewDialog() {
	var templateId = -1;
	return showEditDialog(templateId);

}
GT.U.onLoad( init);

function removeSelect(value){
	var ids = "";
	var obs  = mygrid.getSelectedRecords();
	for(var i=0;i<obs.length;i++){
		if(ids!="")
			ids+=",";
		ids+=obs[i]["id"]
	}
	if(ids == "")
	{
		window.parent.parent.jAlert("请选择记录", "系统提示");
		return ;
	}
	var message = "您真的要还原这几个用户吗？";
	if(value==1){
		message = "您真的要删除这几个用户吗？";
	}else
	if(value==2){
		message = "您真的要冻结这几个用户吗？";
	}
	window.parent.parent.jConfirm(message, '操作确认', function(r) {
			 
				if (r) {
						var param = {
							method:"removeByIds",
							ids:ids,
							status:value
						}
					
						doPost("sysUser!removeByIds.action", param, function(data) {
						
									if (data.status) {
										query();
										window.parent.parent.jAlert(data.message, "系统提示");
									}else{
										window.parent.parent.jAlert(data.message, "系统提示");
									}
							});
				}
	});

}
function updateSelect(){
	var ids = "";
	var obs  = mygrid.getSelectedRecords();
	for(var i=0;i<obs.length;i++){
		if(ids!="")
			ids+=",";
		ids+=obs[i]["id"]
	}
	if(ids == "")
	{
		window.parent.parent.jAlert("请选择记录", "系统提示");
		return ;
	}
	window.parent.showWindow('user_password.jsp?ids=' + ids ,'修改密码',100,200);

}

