/*******************************************************************************
 * 简单表格v1.1 已经支持表头排序
 * author:Chen.H
 * 		 var myTable1 =  new MaxTable();
		 myTable1.initialize(
		  	{
		  		table:'myTable',
		  		loading:'loading',
		  		id:'id',
		  		queryUrl:'sysUserAction.do?method=index',
		  		headerColumns:[{id:'id',name:'操作',renderer:IdCheckBoxRenderer},
		  		{id:'id',name:'操作',renderer:editRenderer},
		  		{id:'username',name:'用户名'},
		  		],
		  		complete:function(table){
		  			alert(table.page.totalRowNum);
		  		}
		  	}
		  )
		  //表头排序
		  myTable1.initSortHead(
	      {head:'myHead',cells:[{index:1,name:'id'},{index:2,name:'username'},{index:3,name:'truename'},{index:4,name:'sex'},{index:5,name:'status'},{index:6,name:'regtime'}]}
	      );
	     function query(){
	     	myTable1.page.totalRowNum = 0;
	    	myTable1.onLoad({username:$("#username").val(),truename:$("#truename").val(),status:$("#status").val()});
	     } 
 ******************************************************************************/
var MaxTable =  function(){
	this.page = new Pages();
	this.defaultSortInfo ={field:'',fieldName:'',sortOrder:''};
	this.sortInfo =this.defaultSortInfo ;
	this.showPageInfo = true;//是否显示分页信息
	this.table = null;

};
MaxTable.prototype={
	initialize:function(opts){
		this.opts = $.extend({
				id:'id'
			}, opts || {});
		this._s  = $("#"+this.opts.table)[0];
		this.loading = $("#"+this.opts.loading);
		if(this.opts.isSort == false){
			this.sortInfo = this.defaultSortInfo;
		}
		this.showPageInfo = (this.opts.showPageInfo==undefined)?true:this.opts.showPageInfo;
		this.complete = this.opts.complete;
		this.table =this.opts.table;
	}
	,
	onLoad:function (parameters) {
		this.searchParameters = parameters;
		var opts = this.opts;
		var _s = this._s;
		var url = opts.queryUrl;
		var _parent = this;
		if(_parent.loading){

		}
			$.ajax({
				type : "POST",
				url : opts.queryUrl,
				data : this.getQueryData(parameters),
				dataType : "json",
				success : function(datas) {
					try 
					{ 
					var data = datas.data;
					_parent.page = datas.pageInfo;
					while (_s.rows.length > 0) {
						_s.deleteRow(0);
					}

					if (data.length == 0) {
						var trObj = _s.insertRow(0);
						trObj.id= "page";
						var tdObj = trObj.insertCell(0);
						tdObj.colSpan = opts.headerColumns.length;
						tdObj.innerHTML = "没有记录！";
					} else {
						for ( var i = 0; i < data.length; i++) {
							var trObj = _s.insertRow(_s.rows.length);
						
							trObj.id = i + 1;
							if (i % 2 == 0)
								trObj.className = "td1";
							else
								trObj.className = "td2";
							
							for ( var j = 0; j < opts.headerColumns.length; j++) {
								var tdObj = trObj.insertCell(j);
								if (opts.headerColumns[j].renderer) {
									tdObj.innerHTML = opts.headerColumns[j]
											.renderer(
													data[i][opts.id],
													data[i][opts.headerColumns[j].id],
													data[i],trObj.id);

								} else {
									tdObj.innerHTML = data[i][opts.headerColumns[j].id];
								}
							}
							
						}

						if(_parent.showPageInfo){
						if (_parent.page.totalCount != 0) {
						
							var trObj = _s.insertRow(_s.rows.length);
							trObj.id= "page";
							var tdObj = trObj.insertCell(0);
							tdObj.colSpan = opts.headerColumns.length;
							tdObj.innerHTML = _parent.createPage(_parent.page);
							$("#pageSize", tdObj).bind("blur", function() {
								var p1 = /^\d{1,}$/;
								if (!p1.test(this.value)) {
									alert("分页只能是数字！");
									return;
								}
								if (parseInt(this.value) <= 0) {
									alert("分页数字不正确！");
									return;
								}
								_parent.page.pageSize = this.value;
								_parent.page.totalCount = -1;

								_parent.query();
							});
							$("#firstPage", tdObj).bind("click",
									function() {
										_parent.page.pageNo = 1;
										_parent.query();
									});
							$("#prePage", tdObj)
									.bind(
											"click",
											function() {
												_parent.page.pageNo = _parent.page.pageNo - 1;
												_parent.query();
											});
							$("#nextPage", tdObj)
									.bind(
											"click",
											function() {
												_parent.page.pageNo = _parent.page.pageNo + 1;
												_parent.query();
											});
							$("#lastPage", tdObj)
									.bind(
											"click",
											function() {
												_parent.page.pageNo = _parent.page.totalPage;
												_parent.query();
											});
							$("#Pg", tdObj).bind("blur", function() {
								var p1 = /^\d{1,}$/;
								if (!p1.test(this.value)) {
									alert("分页只能是数字！");
									return;
								}
								if (parseInt(this.value) <= 0) {
									alert("分页数字不正确！");
									return;
								}
								_parent.page.pageNo = this.value;
								_parent.query();
							});
						}
					}
					}
					if(_parent.loading){
						_parent.loading.unblock();
					 }
					 if(_parent.complete){
					 	_parent.complete(_parent);
					 }
					}
					
					catch (e) 
					{ 
						alert(e);
						if(response.responseText.indexOf('loginwindow')!=-1){
							parent.location.reload();
						}else{
							alert(response.responseText);
						}
					}
					if(_parent.complete){
				
						   $("#"+_parent.table+" tr").mouseover(function(){//如果鼠标移到class为stripe的表格的tr上时，执行函数
							   		if($(this).attr("id")!="page")
					            $(this).addClass("over");}).mouseout(function(){//给这行添加class值为over，并且当鼠标一出该行时执行函数
					            	if($(this).attr("id")!="page")
					            $(this).removeClass("over");}); //移除该行的class
						
					}
				},
				error : function(response) {
					
					if(response!=null&&response!= undefined&&response.responseText.length>0){
						if(response.responseText.indexOf('loginwindow')!=-1){
							parent.location.reload();
						}else{
							alert(response.responseText);
						}
					}
				}

			});
			var temp = $('input[name="c_all"]')[0]+"";
			$('input[name="c_all"]').attr("checked",false);
			if(temp!='undefined'){
				$('input[name="c_all"]')[0].nextSibling.nodeValue = "全选";
			}
		}
	  ,
 	  createPage:function (pageInfo){
 	
 			var text = '';
 			text += '每页显示<input id="pageSize"   value="'
 					+ pageInfo.pageSize
 					+ '" type="text" id="pageSize"   style="width:20px; border:solid #CCC 1px"/>';
 			text += '条&nbsp;|&nbsp;共<font color=red>' + pageInfo.totalPage
 					+ '</font>页 , <font color=red>' + pageInfo.totalCount
 					+ '</font>条数据';
 			text += '&nbsp;|&nbsp;';
 			text += '<input id="firstPage"  type="button" '
 					+ (pageInfo.pageNo > 1 ? ' style="background:#FFF ; border:none;cursor:pointer"  '
 							: ' disabled="disabled" ')
 					+ '   name="button" id="button" value="首页"  style="background:#FFF ; border:none;"/>&nbsp;';
 			text += '<input id="prePage"  type="button" '
 					+ (pageInfo.pageNo > 1 ? '  style="background:#FFF ; border:none;cursor:pointer" '
 							: ' disabled="disabled" ')
 					+ '   name="button" id="button" value="上一页"  style="background:#FFF ; border:none;"/>&nbsp;';
 			text += '<input id="nextPage"  type="button" '
 					+ (pageInfo.totalPage > pageInfo.pageNo ? ' style="background:#FFF ; border:none;cursor:pointer"  '
 							: ' disabled="disabled" ')
 					+ '   name="button" id="button" value="下一页"  style="background:#FFF ; border:none;"/>&nbsp;';
 			text += '<input id="lastPage"  type="button"  '
 					+ (pageInfo.totalPage > pageInfo.pageNo ? ' style="background:#FFF ; border:none;cursor:pointer"  '
 							: ' disabled="disabled" ')
 					+ '  name="button" id="button" value="尾页"  style="background:#FFF ; border:none;"/>&nbsp;';
 			text += '&nbsp;|&nbsp;第<input id="Pg"   value="'
 					+ pageInfo.pageNo
 					+ '" type="text" id="Pg" style="width:20px; border:solid #CCC 1px"/>页';

 			return text;


 	  }
	,	
	getQueryData:function (parameters){
		  if(!this.showPageInfo){
		  	this.page.pageSize=100;
		  }
		//var jsonData={_gt_json:"{pageInfo:"+$.toJSON(this.page)+",action:'load',filterInfo:[],orderby:"+(this.sortInfo==null?'':$.toJSON(this.sortInfo))+"}"};
		var jsonData={"pageInfo.pageSize":this.page.pageSize,"pageInfo.pageNo":this.page.pageNo,"pageInfo.totalCount":this.page.totalCount,"field":this.sortInfo.field,"sortOrder":this.sortInfo.sortOrder};

		jsonData =$.extend(jsonData,parameters);
		return jsonData;
	},
	query:function(){
	
		this.onLoad(this.searchParameters);
	},
	initSortHead:function(opts){
		var _parent = this;
		var headTrobj = $("#"+opts.head)[0];
		this.headInfos= new Array();
		for(var i=0;i<opts.cells.length;i++){
			
			var fieldName = opts.cells[i].name;
			var sortSpan = document.createElement("span");

			sortSpan.innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;";
			$(headTrobj.cells[opts.cells[i].index]).append(sortSpan);
			this.headInfos[this.headInfos.length] = {index:opts.cells[i].index,name:opts.cells[i].name,sortSpan:sortSpan};
			$(headTrobj.cells[opts.cells[i].index]).bind("click", function() {
				_parent.setSortInfo(this.cellIndex);
				_parent.page.pageNo=1;
				_parent.query();
				 
			});
		}
	}
	,setSortInfo:function (cellIndex){
		for(var i=0;i<this.headInfos.length;i++){
			var fieldName= this.headInfos[i].name;
			var sortSpan =   this.headInfos[i].sortSpan;
			if(cellIndex == this.headInfos[i].index ){
 				if(!this.sortInfo){
				this.sortInfo = {field:fieldName,fieldName:fieldName,sortOrder:'desc'};
					sortSpan.className="sortDesc";
				}else{
					if(this.sortInfo.fieldName == fieldName){
						if(this.sortInfo.sortOrder==""){
							sortSpan.className="sortDesc";
							this.sortInfo = {field:fieldName,fieldName:fieldName,sortOrder:"desc"};
							
						}else if(this.sortInfo.sortOrder=="desc"){
							sortSpan.className="sortAsc";
							this.sortInfo = {field:fieldName,fieldName:fieldName,sortOrder:"asc"};
						}else {
							sortSpan.className="";
							this.sortInfo =  this.defaultSortInfo;
						}
						
					}else{
						sortSpan.className="sortDesc";
						this.sortInfo = {field:fieldName,fieldName:fieldName,sortOrder:'desc'};
					}
				}
			}else{
				sortSpan.className="";
			}
		}
		
		 
	}
		 
};

function Pages(){
	this.pageNo = 1;
	this.totalPage = 0;
	this.totalCount = -1;
	this.pageSize  = 17;
}
function changeBackgroundColor(_val){
	if($(_val).attr("checked") == true)    // 判断是否有选中checkbox
    {
    	$(_val).parent('td').parent('tr').addClass("checked");
    }else{	  
    	$(_val).parent('td').parent('tr').removeClass("checked");
    }
}
function radioBackgroundColor(){
	var check = $("input:name='c'"); //得到所有被选中的checkbox
	check.each(function(){         //循环拼装被选中项的值
		if($(this).attr("checked") == true)    // 判断是否有选中checkbox
	    {
	    	$(this).parent('td').parent('tr').addClass("checked");
	    }else{	  
	    	$(this).parent('td').parent('tr').removeClass("checked");
	    }
	});
}

function IdCheckBoxRenderer(idValue,value){
		  	return '<input type="checkbox" name="c"	value=\"'+value+'\" onclick="changeBackgroundColor(this);" >';
}
function IdRadioRenderer(idValue,value){
		  	return '<input type="radio" name="c"	value=\"'+value+'\" onclick="radioBackgroundColor();">';
}
function numRenderer(idValue,value,record,num){
		  	return (num+1);
}
function getCheckedValuesByContainer(elementName,container){
		var txt = "input[name=" + elementName + "]:checked";
	// 定义数组
	var stateStack = new Array();
	var tmp = $(txt,container);
	if (tmp) {
		tmp.each(function() {
					stateStack.push($(this).val());
				});
	}
	return stateStack;
}