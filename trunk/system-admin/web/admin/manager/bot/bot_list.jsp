<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title></title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include//jquery.jsp"%>
</head>
<body>
	<form action="" method="post">
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
						<h2 class="underline" id="loading">审核未通过列表</h2>
						<!-- <div class="editor" style="width: 100%;">
							<div id="editor_left"></div>
							<div id="editor_contents">
								<ul class="editor_link">
									<li><a name="add" class="add" href="javascript:"
										>批量进行模板匹配</a>
									</li>

								</ul>
							</div>
							<div id="editor_right"></div>
						</div> -->
						<div class="editor" style="width: 100%;">
							<table class="table" id="find_01">
								<tr>
									<td>文章标题：<input type="text" name="title" id="title">
										分类： <select name="type" id="type" style="width: 150px"><option
												value="">请选择信息</option>
									</select>
										发布时间： <input type="text" name="issueDateB" id="issueDateB"  readOnly	onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" /> - <input type="text" name="issueDateE" id="issueDateE"  readOnly	onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
										<input type="button" class="button" value="查询" id="query">
									</td>
								</tr>

							</table>
						</div>
						<table class="table">
							<thead>
								<tr>
									<th>文章标题</th>
									<th>分类</th>
									<th>状态</th>
									<th>发布时间</th>
									<th>操作</th>
								</tr>
							</thead>

							<tbody id="myTable">


							</tbody>
						</table>
					</td>
					<td id="middleRight"></td>
				</tr>
				<tr id="bottomRow">
					<td id="bottomLeft"></td>
					<td id="bottomMiddle"></td>
					<td id="bottomRight"></td>
				</tr>
			</tbody>
		</table>
<input type="hidden" id="sortBy" name="sortBy" value="-1"/>
	</form>
	<script>

		var myTable1 = new MaxTable();
		myTable1.initialize({
			table : 'myTable',
			loading : 'loading',
			id : 'articleId',
			queryUrl : 'article!articleVN.action',
			headerColumns : 
			[
			{
				id : 'title',
				renderer : rendererTitle
			}, 
			{
				id : 'type'
			}, 
			{
				id : 'status',
				renderer : handllerRenderer
			}, 
			{
				id : 'issueDate',
				renderer : dataFormat
			},
			{
				id : 'articleId',
				renderer : handllerRenderer
				
			}
			]
		});
		
		function query() {
			myTable1.page.totalRowNum = 0;
			myTable1.page.pageNum = 1;
			myTable1.onLoad({sortBy:$("#sortBy").val(),title:$("#title").val(),type:$("#type").val(),issueDateB:$("#issueDateB").val(),issueDateE:$("#issueDateE").val()});
		}
		
	  query();
	  $('#query').click(function() {
			if($('#issueDate').val()!=""){
				$("#sortBy").val(1);
			}
			 query();
		});
		 function rendererTitle(idValue,value,record) {
			   var loadingStatus = record['loadingStatus'];
			   var callback = record['callback'];
			   if(loadingStatus=="YES"){
				   loadingStatus ="<img src='${cxp}/images/widgets/tui.png' alt='推荐' title='推荐' />";
			   }else{
				   loadingStatus="";
			   }
			   if(callback=="TOEDIT"){
				   loadingStatus +="<img src='${cxp}/images/widgets/arrow_undo.png' alt='重编辑' title='重编辑' />";
			   }
		      return loadingStatus+value;
		 }
	  function handllerRenderer(idValue,value,record){
			 
			var handller = '';
			handller += "<a href='${cxp}/cms/article/articleAction!templateListByArticleID.action?articleId="+value+"'>模板查看</a>";
			return handller;
	   }
	  function dataFormat(idValue,value){
		  try{
		  if(value!=null||value!="")
			{
			  
			  var d = new Date(value);
			  if(value.indexOf("CST")!=-1){
				  d.setHours(d.getHours()-14);
			  }
			  
			  return d.format('yyyy-MM-dd');
			}
		  }catch(e){
			  return "";
		  }

	  }
	  Date.prototype.format = function(format)
	  {
	      var o =
	      {
	          "M+" : this.getMonth()+1, //month
	          "d+" : this.getDate(),    //day
	          "h+" : this.getHours(),   //hour
	          "m+" : this.getMinutes(), //minute
	          "s+" : this.getSeconds(), //second
	          "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
	          "S" : this.getMilliseconds() //millisecond
	      }
	      if(/(y+)/.test(format))
	      format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
	      for(var k in o)
	      if(new RegExp("("+ k +")").test(format))
	      format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
	      return format;
	  }

	  
	   $("#type").dictionary(
				{
					url:'${cxp }/manager/dictionary/dictionaryAction!getDictionarysByParentId.action',
				  	data:{id:'7813'},  
					defaultOp:[{name:'请选择信息',value:''}],
					c1:"name"//列名1
				}
		);
	 
	</script>
</body>
</html>