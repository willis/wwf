var pageSize = 10;// 格子页行数
var pageSizeList = [10, 15, 30, 50, 100, 200];
// 工具条,需要 几个单词 中间需要 用 ' | '作为分割,否则无法显示
var toolbarContent = 'pagesize | state | nav | goto |  reload |  print | filter  ';

// 加载 的图片
var loadimage = "/js/gt-grid/skin/default/images/s_loading.gif";

// grid 列的分组 是否开启
var groupTrue = true;
var groupFalse = false;
// align : 'right',

function alignText(align) {
	var alignTxt = (align) ? ((align == '') ? 'right' : align) : 'right'
	return alignTxt;
}
/**
 * 用于 gt grid 中,的日期的 格式化显示
 * 
 * @param {}
 *            id
 * @param {}
 *            header
 * @param {}
 *            formate
 * @param {}
 *            width
 * @return {}
 */
function displayDateRenderer(id, header, format, width, align, grouped) {

	var param = {
		id : id,
		header : (header) ? header : '日期',
		align : alignText(align),
		width : (width) ? width : 130,
		renderer : function(value, record, columnObj, grid, colNo, rowNo) {
			// alert(GT.$json(record));
			if (id) {
				var date = record[id];
				return ky.format.toDateByFormat(date, format);

			} else {
				return '';
			}
		},
		grouped : (grouped) ? grouped : false
	};
	return param;
}

/**
 * 要是有extends就好处理了
 * 
 * @param {}
 *            opt 额外的 信息 isDate ,format
 * @return {}
 */
function gtc(opt) {

	var c = createColumnConfig();
	if (opt != null) {
		c.id = opt.id;

		if (opt.width) {
			c.width = opt.width;
		} else {
			c.width = 90;
		}

		// 对日期的处理
		if (opt.isDate) {
			if (opt.header) {
				c.header = opt.header;
			} else {
				c.header = '日期';
			}
			c.renderer = function(value, record, columnObj, grid, colNo, rowNo) {
				if (id) {// alert(GT.$json(record));
					var date = record[id];
					return ky.format.toDateByFormat(date, format);
				} else {
					return '';
				}
			}

			if (opt.width) {
				c.width = opt.width;
			} else {
				c.width = 130;
			}

		}
		c.align = alignText(opt.align);
		if (opt.header)
			c.header = opt.header;
		if (opt.renderer) {
			c.renderer = opt.renderer;
		}
		if (opt.hdRenderer) {// 表头的render
			c.hdRenderer = opt.hdRenderer;
		}
		if (opt.fieldName)
			c.fieldName = opt.fieldName;

		if (opt.hdAlign)
			c.hdAlign = opt.hdAlign;

		if (opt.frozen)
			c.frozen = opt.frozen;
		if (opt.hidden)
			c.hidden = opt.hidden;
		if (opt.sortOrder)
			c.sortOrder = opt.sortOrder;

		if (opt.sortable)
			c.sortable = opt.sortable;
		if (opt.moveable)
			c.moveable = opt.moveable;
		if (opt.resizable)
			c.resizable = opt.resizable;
		if (opt.hideable)
			c.hideable = opt.hideable;
		if (opt.frozenable)
			c.frozenable = opt.frozenable;
		if (opt.groupable)
			c.groupable = opt.groupable;
		if (opt.filterable)
			c.filterable = opt.filterable;
		if (opt.printable)
			c.printable = opt.printable;
		if (opt.editable)
			c.editable = opt.editable;

		if (opt.toolTip)
			c.toolTip = opt.toolTip;

		if (opt.minWidth)
			c.minWidth = opt.minWidth;
		if (opt.styleClass)
			c.styleClass = opt.styleClass;
		if (opt.emptyText)
			c.emptyText = opt.emptyText;

		if (opt.editor) {
			c.editor = opt.editor;
			c.toolTip = true;
		}
		if (opt.filterField)
			c.filterField = opt.filterField;

	}
	return c;
}
/**
 * 显示列的基本信息
 * 
 * @param {}
 *            id
 * @param {}
 *            header
 * @param {}
 *            width
 * @return {}
 */
function displayColumn(id, header, width, align, grouped, editor) {
	var param = {
		id : id,
		header : (header) ? header : '',
		width : (width) ? width : 90,
		align : alignText(align),
		grouped : (grouped) ? grouped : false,
		editor : editor,
		toolTip : true
	};
	return param;
}

/**
 * 列的默认配置
 * 
 * @return {}
 */
function createColumnConfig() {
	var p = {
		id : '',// {s} 列对象的id
		width : 80,// {i} 列的宽度(目前不支持%)
		header : '',// {s} 列的标题
		fieldName : null,// {s} 列对应的 Dataset中字段(field)的名字

		align : 'left', // {s} 列数据单元格水平对齐方式, 可选值: left(默认) center right
		hdAlign : 'left',// {s} 列表头水平对齐方式, 可选值: left(默认) center right

		frozen : false,// {b} 列是否被冻结
		hidden : false,// {b} 列是否被隐藏
		sortOrder : null, // {s} 列的排序状态

		sortable : true,// {b} 列是否可排序
		moveable : false,// {b} 列是否可手动移动
		resizable : true,// {b} 列是否可手动调节列宽
		hideable : true,// {b} 列是否可隐藏
		frozenable : true,// {b} 列是否可冻结
		groupable : true,// {b} 列是否可进行编组
		filterable : true,// {b} 列是否可参与过滤
		printable : true,// {b} 列是否可打印
		editable : false,// {b} 列是否可编辑

		toolTip : true,// {b} 列数据是否显示toopTip

		minWidth : 10,// {i} 列的最小宽度
		styleClass : null, // {s} 列的数据单元格的style className
		emptyText : '',// {s} 当该列对应的数据不纯在时,默认显示的内容

		// hdRenderer : function(header,cobj) { 列表头的渲染器
		// renderer function(value ,record,columnObj,grid,colNo,rowNo) 单元格渲染器
		editor : null,// {o} 对应的编辑器定义 ,如 : ,//{ type ... , validRule...
		// getDisplayValue... ,} 见例子
		filterField : null
		// {o} 过滤时使用的过滤器
	};
	return p;
}
/**
 * 通过渲染器的方式来显示列
 * 
 * @param {}
 *            id
 * @param {}
 *            header
 * @param {}
 *            width
 * @param {}
 *            renderer
 * @return {boolean } grouped <br>
 *         groupTrue=true; groupFalse=false;
 */
function displayColumnByRenderer(id, header, width, renderer, align, grouped,
		editor) {
	var param = {
		id : id,
		header : (header) ? header : '',
		width : (width) ? width : 90,
		align : alignText(align),
		renderer : renderer,
		grouped : (grouped) ? grouped : false,
		editor : editor
	};

	return param;
}

function oneColumnArray(name) {
	var param = {
		name : name
	};

	return param;
}
/**
 * 建立 gt 中用到的 字段的 name :数组
 * 
 * 形如: { {name:'column1'}, {name:'column2'} };
 * 
 * @param {}
 *            names 数组
 * @return {}
 */
function makeFieldsArray(names) {

	var param = new Array();

	if (names) {
		for (var i = 0; i < names.length; i++) {
			param.push(names[i]);
		}
	}
	return param;

}
function _____inArray(elem, array) {
	for (var i = 0, length = array.length; i < length; i++)
		// Use === because on IE, window == document
		if (array[i] === elem)
			return i;

	return -1;
}
/**
 * 创建记录数的选择框
 * 
 * @param {}
 *            pageSize
 * @return {}
 */
function createPageSizeList(pageSize) {

	var array = new Array();

	array.push(10);
	array.push(15);
	array.push(30);
	array.push(50);
	array.push(100);
	array.push(200);
	array.push(400);

	// 如果数组中,没有,就添加
	if (_____inArray(pageSize, array) < 0) {
		array.push(pageSize);
	}
	array.sort(_____AscSort);

	return array;
}
/**
 * 正序判断
 * @param {} x
 * @param {} y
 * @return {}
 */
function _____AscSort(x, y) {
	return x == y ? 0 : (x > y ? 1 : -1);
}
// sort 方法将 Array 对象进行适当的排序；在执行过程中并不会创建新的 Array 对象。
//
// 如果为 sortfunction 参数提供了一个函数，那么该函数必须返回下列值之一：
// （1）负值，如果所传递的第一个参数比第二个参数小。
// （2）零，如果两个参数相等。
// （3）正值，如果第一个参数比第二个参数大。
//
// 示例
// <script type="text/javascript">
// function AscSort(x, y) {
// return x == y ? 0 : (x > y ? 1 : -1);
// }
//
// function DescSort(x, y) {
// return x == y ? 0 : (x > y ? -1 : 1);
// }
//
// function RandomSort(x, y) {
// return Math.floor(Math.random() * 2 - 1 );
// }
//
// var array = [2,4,3,5,1,6,9,0,8];
//
// document.write("<p>正序：" + array.sort(AscSort) + "</p>");
// document.write("<p>倒序：" + array.sort(DescSort) + "</p>");
// document.write("<p>随机排序：" + array.sort(RandomSort) + "</p>");
// document.write("<p>随机排序：" + array.sort(RandomSort) + "</p>");
// document.write("<p>随机排序：" + array.sort(RandomSort) + "</p>");
// </script>

/**
 * 创建默认的 配置项
 * 
 * @return {}
 */
function createDefaultConfig() {
	var _grid_default_config = {
		id : "grid1",
		loadURL : contextPath,
		remotePaging : true,
		remoteSort : true,// 服务器端排序
		height:'280px',
		submitColumnInfo : false,// 是否提交列数据到后台,导出的时候,需要有数据,否则导出会出错
		multiSort : false,// 是否支持多个排序,默认不支持

		resizable : true,// table支持改变大小操作
		lightOverRow : false,// 是否开启行的鼠标悬停指示

		toolTipDiv : 'toolTipDiv',
		dataset : [],// dataset,
		columns : [],// columns,
		stripeRows : true,
		exportURL : '',
		exportFileName : '',

		container : 'grid1_container',
		showIndexColumn : true,// 显示索引列,
		toolbarPosition : 'bottom',

		selectRowByCheck : true,
		toolbarContent : toolbarContent,

		pageSize : pageSize,

		pageSizeList : pageSizeList,

		showGridMenu : true,
		allowCustomSkin : false, // 是否开启主菜单上的皮肤选择选项
		allowGroup : true, // 是否开启主菜单上的列编组选项
		allowFreeze : true, // 是否开启主菜单上的冻结列选项
		allowHide : true, // 是否开启主菜单上的隐藏列选项

		autoLoad : false
	};
	return _grid_default_config;
}
/**
 * 根据配置文件创建grid对象
 * 
 * 
 */
function createGridByDef(opt) {
	var cofg = createDefaultConfig();
	if (opt != null) {
		if (opt.id)
			cofg.id = opt.id;
		if (opt.loadURL)
			cofg.loadURL = opt.loadURL;
		if (opt.remotePaging)
			cofg.remotePaging = opt.remotePaging;
		if (opt.remoteSort)
			cofg.remoteSort = opt.remoteSort;// 服务器端排序
		if (opt.submitColumnInfo)
			cofg.submitColumnInfo = opt.submitColumnInfo;// 是否提交列数据到后台;导出的时候;需要有数据;否则导出会出错
		if (opt.multiSort)
			cofg.multiSort = opt.multiSort;// 是否支持多个排序;默认不支持
		if (opt.resizable)
			cofg.resizable = opt.resizable;// table支持改变大小操作
		if (opt.lightOverRow)
			cofg.lightOverRow = opt.lightOverRow;// 是否开启行的鼠标悬停指示
		if (opt.toolTipDiv)
			cofg.toolTipDiv = opt.toolTipDiv;
		if (opt.dataset)
			cofg.dataset = opt.dataset;
		if (opt.columns)
			cofg.columns = opt.columns;
		if (opt.stripeRows)
			cofg.stripeRows = opt.stripeRows;
		if (opt.exportURL)
			cofg.exportURL = opt.exportURL;
		if (opt.exportFileName)
			cofg.exportFileName = opt.exportFileName;
		if (opt.container)
			cofg.container = opt.container;
		if (opt.showIndexColumn)
			cofg.showIndexColumn = opt.showIndexColumn;// 显示索引列;
		if (opt.toolbarPosition)
			cofg.toolbarPosition = opt.toolbarPosition;
		if (opt.selectRowByCheck)
			cofg.selectRowByCheck = opt.selectRowByCheck;
		if (opt.toolbarContent)
			cofg.toolbarContent = opt.toolbarContent;
		if (opt.pageSize)
			cofg.pageSize = opt.pageSize;
		if (opt.pageSizeList) {
			cofg.pageSizeList = opt.pageSizeList;
		} else {

			if (opt.hasOtherData) {
				// 如果有其他数据,就不创建 pageSize,用默认来显示

			} else {
				cofg.pageSizeList = createPageSizeList(opt.pageSize);
			}

		}
		if (opt.showGridMenu)
			cofg.showGridMenu = opt.showGridMenu;
		if (opt.allowCustomSkin)
			cofg.allowCustomSkin = opt.allowCustomSkin; // 是否开启主菜单上的皮肤选择选项
		if (opt.allowGroup)
			cofg.allowGroup = opt.allowGroup; // 是否开启主菜单上的列编组选项
		if (opt.allowFreeze)
			cofg.allowFreeze = opt.allowFreeze; // 是否开启主菜单上的冻结列选项
		if (opt.allowHide)
			cofg.allowHide = opt.allowHide; // 是否开启主菜单上的隐藏列选项
		if (opt.autoLoad)
			cofg.autoLoad = opt.autoLoad;

	}

	// pageSizeList

	if (false) {// 方式1
		// cofg = {
		// id : (opt.id) ? opt.id : _grid_default_config.id,
		// loadURL : (opt.loadURL)
		// ? opt.loadURL
		// : _grid_default_config.loadURL,
		// remotePaging : (opt.remotePaging)
		// ? opt.remotePaging
		// : _grid_default_config.remotePaging,
		// remoteSort : (opt.remoteSort)
		// ? opt.remoteSort
		// : _grid_default_config.remoteSort,// 服务器端排序
		//
		// submitColumnInfo : (opt.submitColumnInfo)
		// ? opt.submitColumnInfo
		// : _grid_default_config.submitColumnInfo,//
		// 是否提交列数据到后台,导出的时候,需要有数据,否则导出会出错
		// // multiSort : (opt.multiSort )?opt.multiSort :
		// // _grid_default_config.multiSort ,//是否支持多个排序,默认不支持
		//
		// resizable : (opt.resizable)
		// ? opt.resizable
		// : _grid_default_config.resizable,// table支持改变大小操作
		// lightOverRow : (opt.lightOverRow)
		// ? opt.lightOverRow
		// : _grid_default_config.lightOverRow,// 是否开启行的鼠标悬停指示
		//
		// toolTipDiv : (opt.toolTipDiv)
		// ? opt.toolTipDiv
		// : _grid_default_config.toolTipDiv,
		// dataset : (opt.dataset)
		// ? opt.dataset
		// : _grid_default_config.dataset,
		// columns : (opt.columns)
		// ? opt.columns
		// : _grid_default_config.columns,
		// stripeRows : (opt.stripeRows)
		// ? opt.stripeRows
		// : _grid_default_config.stripeRows,
		// exportURL : (opt.exportURL)
		// ? opt.exportURL
		// : _grid_default_config.exportURL,
		// exportFileName : (opt.exportFileName)
		// ? opt.exportFileName
		// : _grid_default_config.exportFileName,
		//
		// container : (opt.container)
		// ? opt.container
		// : _grid_default_config.container,
		// showIndexColumn : (opt.showIndexColumn)
		// ? opt.showIndexColumn
		// : _grid_default_config.showIndexColumn,// 显示索引列,
		// toolbarPosition : (opt.toolbarPosition)
		// ? opt.toolbarPosition
		// : _grid_default_config.toolbarPosition,
		//
		// selectRowByCheck : (opt.selectRowByCheck)
		// ? opt.selectRowByCheck
		// : _grid_default_config.selectRowByCheck,
		// toolbarContent : (opt.toolbarContent)
		// ? opt.toolbarContent
		// : _grid_default_config.toolbarContent,
		//
		// pageSize : (opt.pageSize)
		// ? opt.pageSize
		// : _grid_default_config.pageSize,
		//
		// pageSizeList : (opt.pageSizeList)
		// ? opt.pageSizeList
		// : _grid_default_config.pageSizeList,
		//
		// showGridMenu : (opt.showGridMenu)
		// ? opt.showGridMenu
		// : _grid_default_config.showGridMenu,
		// // allowCustomSkin : (opt. allowCustomSkin )?opt.allowCustomSkin :
		// // _grid_default_config.allowCustomSkin , // 是否开启主菜单上的皮肤选择选项
		// allowGroup : (opt.allowGroup)
		// ? opt.allowGroup
		// : _grid_default_config.allowGroup, // 是否开启主菜单上的列编组选项
		// allowFreeze : (opt.allowFreeze)
		// ? opt.allowFreeze
		// : _grid_default_config.allowFreeze, // 是否开启主菜单上的冻结列选项
		// allowHide : (opt.allowHide)
		// ? opt.allowHide
		// : _grid_default_config.allowHide, // 是否开启主菜单上的隐藏列选项
		//
		// autoLoad : (opt.autoLoad)
		// ? opt.autoLoad
		// : _grid_default_config.autoLoad
		// };

	}

	return createGridByConfig(cofg);
}

/**
 * 根据配置信息来创建一个 grid 对象
 * 
 * @param {}
 *            gridConfig
 * @return {}
 */
function createGridByConfig(gridConfig) {
	var mygrid = new GT.Grid(gridConfig);
	mygrid.beforeExport = function() {
		mygrid.submitColumnInfo = true;// 在导出的时候,把列信息提交到服务器
	 
	}
	return mygrid;
};
/**
 * 创建一个格子 对象
 * 
 * @param {}dataset
 * @param {}columns
 * @param {}
 *            loadURL 加载数据的url
 * @param {}
 *            gridId 格子的id
 * @param {}
 *            containerId 显示的容器
 * @param {}
 *            paramPageSize 每页数量
 * @return {}
 */
function createGrid(loadURL, dataset, columns, gridId, containerId, pageSize,
		exportURL, exportFileName) {
	if (!dataset) {
		alert("dataset 不允许为空!");
		return null;
	}
	if (!columns) {
		alert("columns 不允许为空!");
		return null;
	}

	var gridConfig = createDefaultConfig();
	gridConfig.dataset = dataset;
	gridConfig.columns = columns;
	if (gridId)
		gridConfig.id = gridId;
	if (loadURL)
		gridConfig.loadURL = loadURL;

	if (exportURL) {
		gridConfig.exportURL = exportURL;
	} else {
		gridConfig.exportURL = loadURL;
	}
	if (exportFileName) {
		gridConfig.exportFileName = exportFileName;
	} else {
		gridConfig.exportFileName = "导出查询_";
	}

	if (containerId)
		gridConfig.container = containerId;

	if (pageSize) {

		gridConfig.pageSizeList = createPageSizeList(pageSize);

		gridConfig.pageSize = pageSize;

	}
	// {
	//
	// id : (gridId) ? gridId : "grid1",
	//
	// loadURL : (loadURL) ? loadURL : contextPath,
	// remotePaging : true,
	// remoteSort : true,// 服务器端排序
	//
	// submitColumnInfo : false,// 是否提交列数据到后台,导出的时候,需要有数据,否则导出会出错
	// // multiSort :true,//是否支持多个排序,默认不支持
	//
	// resizable : true,// table支持改变大小操作
	// lightOverRow : true,// 是否开启行的鼠标悬停指示
	//
	// toolTipDiv : 'toolTipDiv',
	// dataset : dataset,
	// columns : columns,
	// stripeRows : true,
	// exportURL : (exportURL) ? exportURL : '',
	// exportFileName : (exportFileName) ? exportFileName : '',
	//
	// container : (containerId) ? containerId : 'grid1_container',
	// showIndexColumn : true,// 显示索引列,
	// toolbarPosition : 'bottom',
	//
	// selectRowByCheck : true,
	// toolbarContent : toolbarContent,
	//
	// pageSize : (paramPageSize) ? paramPageSize : pageSize,
	//
	// pageSizeList : pageSizeList,
	//
	// showGridMenu : true,
	// // allowCustomSkin : true, // 是否开启主菜单上的皮肤选择选项
	// allowGroup : true, // 是否开启主菜单上的列编组选项
	// allowFreeze : true, // 是否开启主菜单上的冻结列选项
	// allowHide : true, // 是否开启主菜单上的隐藏列选项
	//
	// autoLoad : false
	// };

	return createGridByConfig(gridConfig);
}

/**
 * 清空下拉菜单
 * 
 * @param {}
 *            select1 对象,通过 document.getElementById();来得到
 */
function clearSelect(select1) {
	for (var i = select1.length - 1; i >= 0; i--) {
		select1.remove(i);
	}
	// var oOption = new Option("请选择...", "");
	// select1.options[select1.options.length] = oOption;
}

/**
 * 添加数据到 select1 列表框对象
 * 
 * @param {}
 *            select1 对象 通过 document.getElementById();来得到
 * @param {}
 *            dataArray 数据数组,json方式
 * @param {}
 *            json_key 从json中取得的key值
 * @param {}
 *            json_value 从json中取得的value值
 */
function addDatas(select1, dataArray, json_key, json_value) {
	if (dataArray == null || json_key == null || json_value == null
			|| select1 == null)
		return;

	var length = dataArray.length;
	for (var i = 0; i < length; i++) {
		var tmpArray = dataArray[i];
		var oOption = new Option(tmpArray[json_key], tmpArray[json_value]);
		select1.options[select1.options.length] = oOption;
	}
}

/**
 * 执行查询操作,同时把分页值改为1,显示第一页,
 * 
 * @param {}
 *            mygrid
 * @param {}
 *            param
 */
function gridQuery(gridObjOrId, param) {
	var mygrid = findGridById(gridObjOrId);
	if (mygrid == null) {
		alert("grid不允许为空哦!");

		return false;
	}
	try {

		mygrid.submitColumnInfo = false;// 在查询的情况下,不提交列信息到服务器

		// 清空选择
		mygrid.clearCheckedRows();
		// alert('gridQuery...');
		// 把页号改为第一页
		mygrid.getPageInfo().pageNum = 1;
		// alert(GT.$json(mygrid.getPageInfo() ) );
		// 动态生成最后一次查询的 字符串

		var tmpparam = '';

		if (param && typeof param != "string") {
			tmpparam = GT.toQueryString(param);//JQuery.param(param);//
		} else {
			tmpparam = param;
		}
		var exportUrl = exportURL + '&' + tmpparam;
		mygrid.exportURL = exportUrl;
	 	
		//log(exportUrl);
		// alert("mygrid.exportURL=" + mygrid.exportURL);
		//alert( "在查询数据的request中, 将会有如下参数:\n\n"+mygrid.exportURL );
		mygrid.query(param);
	} catch (err) {
		showErr(err);
	}
}
/**
 * 在列的定义中,添加 editor : createDialogEditor('gridId','columnName');
 * 
 * @param {}
 *            grid
 * @param {}
 *            showName
 * @return {}
 */
function createDialogEditor(gridId, showName) {

	var tmpId = gridId + '_' + showName + "_input";
	var myDialogEditorCreater = new GT.DialogEditor({
		id : gridId + '_' + showName + "DialogEditor",
		gridId : gridId,
		width : 350,
		height : 216,
		title : '查看',
		body : [
				'<textarea id="'
						+ tmpId
						+ '" rows="10" cols="60" style="width:99%" readonly="readonly"></textarea><br/>',
				'<input type="button" value="确定" onclick="GT.$grid(\'' + gridId
						+ '\').activeDialog.confirm()"/>'].join(''),

		/***********************************************************************
		 * 指定存放编辑器值的 页面元素 或 该元素的id 或可以取得该元素的函数, 有时候它未必是直接被用户操作的元素,也许是一个hidden
		 * input.
		 * 
		 * 元素形式: valueDom : GT.$("my_name_input")
		 * 
		 * 函数形式: 函数形式的作用: 有时候在创建editor时,那个valueDom 元素也许还并不存在,
		 * 或者是不同环境下对valueDom有不同的需求,需要通过if 来做判断,那么 函数形式就派上用场了.
		 * 
		 * valueDom : function(){ return GT.$("my_name_input") }
		 * 
		 **********************************************************************/

		// valueDom :"my_name_input" ,
		// 更高级的用法是 重写 setValue getValue方法,这样你就可以"为所欲为"了. 例子如下:
		getValue : function() {
			return GT.$(tmpId).value;
		},

		setValue : function(value) {
			GT.$(tmpId).value = value;
		},

		// 其实 仅仅重写这两个方法是不够的, 有时候你希望打开编辑器的时候,自动让某个元素得到焦点,所以还应该重新 active 方法.

		active : function() {
			GT.U.focus(GT.$(tmpId)); // 你可以指定任何一个可以得到焦点的元素得到焦点,不一定非要是
			// valueDom
		}

			// 如果你的 valueDom 可以取得, 那么 你完全没有必要重写 getValue setValue....
			// 而且 Dialog 是有 beforeShow afterShow beforeHide afterHide 方法的
			// 可以通过重写这些方法 做一些before拦截和 after处理 以及对 Dialog 的数据校验

	});
	return myDialogEditorCreater;
}

/**
 * 根据gridId来查找对应的grid
 * 
 * @param {}
 *            gridId
 */
function findGridById(gridObjOrId) {
	if (isEmpty(gridObjOrId))
		return null;
	var mygrid = '';
	if (typeof gridObjOrId === 'string') {
		mygrid = GT.$grid(gridObjOrId);
	} else {
		mygrid = gridObjOrId;
	}
	return mygrid;
}

/**
 * 根据指定的key,来查找对应的选择的 列
 * 
 * @param {}
 *            gridObjOrId
 * @return {String}key 返回数据中的数据的key
 */
function getGridSelectByKey(gridObjOrId, key) {
	if (gridObjOrId === null)
		return null;
	var mygrid = findGridById(gridObjOrId);

	if (mygrid == null)
		return null;
	var selected = mygrid.getSelectedRecords();

	var length = selected.length;
	if (length <= 0) {
		return null;
	}

	// 需要发布的id
	var stateStack = new Array();
	for (var i = 0; i < length; i++) {
		var publishCode = selected[i][key];
		stateStack.push(publishCode)
	}

	return stateStack;
}

/**
 * 查找 和 targetValue 一致的 key 列数据的相同行 数据
 * 
 * @param {}
 *            gridObjOrId
 * @param {}
 *            key 列id一般为主键
 * @param {}
 *            targetValue 需要匹配的数据
 * @return {record} 这一行数据
 */
function findSameRecord(gridObjOrId, key, targetValue) {

	if (gridObjOrId === null)
		return null;
	var mygrid = findGridById(gridObjOrId);
	if (mygrid === null)
		return null;
	var record = null;
	var trArray = mygrid.getAllRows();

	for (var i = 0; i < trArray.length; i++) {
		var data = mygrid.getRecord(i);

		if (data[key] == targetValue) {
			// alert("child:取得的数据是:" + GT.$json(data));
			record = data;
			break;
		}
	}

	return record;
}
