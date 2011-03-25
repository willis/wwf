/****
 * 简单表格v1.0
 * author:tianbao
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
		  		]
		  	}
		  )
	     function query(){
	     	myTable1.page.totalRowNum = 0;
	    	myTable1.onLoad({username:$("#username").val(),truename:$("#truename").val(),status:$("#status").val()});
	     } 
 * ***/
var MaxTable =  function(){
	this.page = new Pages();
	this.sortInfo ={columnId:'id',fieldName:'id',sortOrder:'desc'}
	
	
}

MaxTable.prototype={
	initialize:function(opts){
		this.opts = $.extend({
				id:'id'
			}, opts || {});
		this._s  = $("#"+this.opts.table)[0];
		this.loading = $("#"+this.opts.loading);
		if(this.opts.isSort == false){
			this.sortInfo = null;
		}
		 
	}
	,
	onLoad:function (parameters) {
		this.searchParameters = parameters;
		var opts = this.opts;
		var _s = this._s;
		
		var url = opts.queryUrl;
		
		var _parent = this;
		
		_parent.loading.block({
				message : "数据加载中...",
				css : {
					width : '70%'
					// ,border : '1px solid #a00'
				}
			});

			$.ajax({
				type : "POST",
				url : opts.queryUrl,
				data : this.getQueryData(parameters),
				dataType : "json",
				success : function(datas) {
				
					
					
					var data = datas.data;
					_parent.page = datas.pageInfo;
				
				 
					while(_s.rows.length>0){
					
						_s.deleteRow(0);
					}
				 	
					 if(_parent.page.totalRowNum == 0){
					 	
					 	var trObj = _s.insertRow(0);
					 	var tdObj = trObj.insertCell(0);
					    tdObj.colSpan=opts.headerColumns.length;
					    tdObj.innerHTML="没有记录！";
					 }else{
					 for(var i=0;i<data.length;i++){
						 
						var trObj = _s.insertRow(_s.rows.length);
						trObj.id=i+1;
						if(i%2 == 0)
							trObj.className="td1"
						else
							trObj.className="td2"
							
						
						for(var j=0;j<opts.headerColumns.length;j++){
							var tdObj = trObj.insertCell(j);
							
							if(opts.headerColumns[j].renderer){
							 
								tdObj.innerHTML=opts.headerColumns[j].renderer(data[i][opts.id],data[i][opts.headerColumns[j].id],data[i]);
								
							}else{
								tdObj.innerHTML=data[i][opts.headerColumns[j].id];
							}
						}
					}
					
					var trObj = _s.insertRow(_s.rows.length);
					
					var tdObj = trObj.insertCell(0);
					 tdObj.colSpan=opts.headerColumns.length;
					 
					
					  tdObj.innerHTML=_parent.createPage(datas.pageInfo);
					  
					  
							$("#pageSize",tdObj).bind("blur", function() {
								 var p1 = /^\d{1,}$/;
								 if(!p1.test(this.value))
								 	{alert("分页只能是数字！");return ; 
								 	}
								 if(parseInt(this.value)<=0){
								 	alert("分页数字不正确！");return ; 
								 }
								_parent.page.pageSize = this.value;
								_parent.page.totalRowNum = -1;
								 
								_parent.query();
							});  
							$("#firstPage",tdObj).bind("click", function() {
								_parent.page.pageNum = 1;
								_parent.query();
							});
							$("#prePage",tdObj).bind("click", function() {
								_parent.page.pageNum = _parent.page.pageNum-1;
								_parent.query();
							}); 
							$("#nextPage",tdObj).bind("click", function() {
								_parent.page.pageNum = _parent.page.pageNum+1;
								_parent.query();
							}); 
							$("#lastPage",tdObj).bind("click", function() {
								_parent.page.pageNum = _parent.page.totalPageNum;
								_parent.query();
							}); 
							$("#Pg",tdObj).bind("blur", function() {
								var p1 = /^\d{1,}$/;
								 if(!p1.test(this.value))
								 	{alert("分页只能是数字！");return ; 
								 	}
								 if(parseInt(this.value)<=0){
								 	alert("分页数字不正确！");return ; 
								 }
								_parent.page.pageNum = this.value
								_parent.query();
							}); 
					 }
					_parent.loading.unblock()		 
							
				},
				error : function(response) {
					alert(response.responseText);
				}
				
			});
			
		}
 
	  ,
 	  createPage:function (pageInfo){
 	  	var text = '';
 	  	
 	  	text+='每页显示<input id="pageSize"   value="'+pageInfo.pageSize+'" type="text" name="pageSize"   style="width:20px; border:solid #CCC 1px"/>'
	    text+='条&nbsp;|&nbsp;共<font color=red>'+pageInfo.totalPageNum+'</font>页 , <font color=red>'+pageInfo.totalRowNum+'</font>条数据';
		text+='&nbsp;|&nbsp;'
		text+='<input id="firstPage"  type="button" '+(pageInfo.pageNum>1?' style="background:#FFF ; border:none;cursor:pointer"  ':' disabled="disabled" ')+'   name="button" id="button" value="首页"  style="background:#FFF ; border:none;"/>&nbsp;';
		text+='<input id="prePage"  type="button" '+(pageInfo.pageNum>1?'  style="background:#FFF ; border:none;cursor:pointer" ':' disabled="disabled" ')+'   name="button" id="button" value="上一页"  style="background:#FFF ; border:none;"/>&nbsp;';
		text+='<input id="nextPage"  type="button" '+(pageInfo.totalPageNum>pageInfo.pageNum?' style="background:#FFF ; border:none;cursor:pointer"  ':' disabled="disabled" ')+'   name="button" id="button" value="下一页"  style="background:#FFF ; border:none;"/>&nbsp;';
		text+='<input id="lastPage"  type="button"  '+(pageInfo.totalPageNum>pageInfo.pageNum?' style="background:#FFF ; border:none;cursor:pointer"  ':' disabled="disabled" ')+'  name="button" id="button" value="尾页"  style="background:#FFF ; border:none;"/>&nbsp;';
		text+='&nbsp;|&nbsp;第<input id="Pg"   value="'+pageInfo.pageNum+'" type="text" name="Pg" style="width:20px; border:solid #CCC 1px"/>页';
		
 
		return text;

 	  }
	,	
	getQueryData:function (parameters){
		  
		// alert(param_str);
		var jsonData={_gt_json:"{pageInfo:"+$.toJSON(this.page)+",action:'load',filterInfo:[],sortInfo:["+(this.sortInfo==null?'':$.toJSON(this.sortInfo))+"]}"};
		jsonData =$.extend(jsonData,parameters);
		return jsonData;
	}
	,
	query:function(){
		
		this.onLoad(this.searchParameters);
	}
		 
}

function Pages(){
	this.endRowNum = -1;
	this.pageNum = 1;
	this.startRowNum = 1;
	this.totalPageNum = 0;
	this.totalRowNum = -1;
	this.pageSize  = 10;

 
	
}
 
function IdCheckBoxRenderer(idValue,value){
		  	return '<input type="checkbox" name="c"	value=\"'+value+'\"">';
}
function IdRadioRenderer(idValue,value){
		  	return '<input type="checkbox" name="c"	value=\"'+value+'\"">';
}

