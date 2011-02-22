function getEditArticleHref(idValue) {

	var param = {
		id : idValue,
		method : 'get',
		randomid : Math.random()
	}
	var vhttValue = jQuery.param(param); // A=1&B=2&C=3
	var tmpPath =  "sysRoleAction.do?" + vhttValue;
	var txt = "<a href='javascript:' onclick=\"window.parent.showWindow('" + tmpPath + "','修改角色',300,500)\"\"> 编辑</a> ";

	txt += "<a href=\"javascript:void(0);\" onclick=\"areYouOkDel('"
			+ idValue + "');\">删除</a>";
    txt += " <a href='javascript:' onclick=\"window.parent.showWindow('sysrole_popedom_list.jsp?id=" + idValue + "','配置权限',400,800)\"\">配置权限</a> ";
	
    txt += " <a href='javascript:' onclick=\"window.parent.showWindow('sysRoleToMenu.do?method=listSysMenu&roleId=" + idValue + "','配置系统菜单',600,800)\"\">配置系统菜单</a> ";
			
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

	doPost("sysRoleAction.do", param, function(data) {
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
	fields : [makeFieldsArray('id', 'name', 'describe')]
};

var colsConfig = [
		{
			id : 'id',
			header : "操作",
			width : 200,
			frozen : true,
			frozenable : true,
			// column.renderer 支持字符串模板
			// renderer : '操作'
			// renderer : ' <a href="javascript:void(0);"
			// onclick="showEditDialog(\'@{lcid}\');">操作</a>'
			// >&#160;@{name} 的详细信息&#160;</a>'
			renderer : function(value, record, columnObj, grid, colNo, rowNo) {

			var idValue = record['id'];
			
			return getEditArticleHref(idValue);

			}
		},
	    displayColumnByRenderer('name', "角色名称", 145),
		displayColumnByRenderer('describe', "角色描述", 200)
		 
		

	 

];
var loadURL = "sysRoleAction.do?method=list";
var exportURL="sysRoleAction.do?method=exportXls";
var mygrid = createGrid( loadURL, dsConfig,
		colsConfig, "grid1", "grid1_container", 10, exportURL, "导出");
//查询
function query() {

//	var isDelete = getCheckedValues("query_isDelete");
//	var typeEnum = getCheckedValues("query_type");

	var param = {
		name : GT.U.getValue(GT.$('name')),
		describe : GT.U.getValue(GT.$('describe')),
//		isDelete : "0",
//		beginDate:GT.U.getValue(GT.$('beginDate')),
//		endDate:GT.U.getValue(GT.$('endDate')),
	//	content : GT.U.getValue(GT.$('query_content')),
	//	sp : sp,
		randomid : Math.random()
	}
 	//alert(mygrid);
	// alert( "在查询数据的request中, 将会有如下参数:\n\n"+GT.toQueryString(param) );
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


