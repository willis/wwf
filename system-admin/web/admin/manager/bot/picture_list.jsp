<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
	<%@ include file="/include/taglibs.jsp"%>
	<%@ include file="/include/jquery.jsp"%>

</head>
<body>
<form action="" method="post" >
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
								图片管理
							</h2>
							<div class="editor" style="width: 100%;">
								<div id="editor_left"></div>
								<div id="editor_contents">
									<ul class="editor_link">

										<li>
											<a class="delete" href="javascript:"
												onclick="removeSelect()">删除</a>
										</li>
									
										<li>
											<a class="find"
												href="javascript:void($('#find_01').toggle())">查询</a>
										</li>

									</ul>
								</div>
								<div id="editor_right"></div>
							</div>
							<table class="table" id="datagrid">
						<thead>
						<tr id="myHead">
							<th >
								<label class="checkbox">
									<input type="checkbox" name="c_all"
										onClick="selectAll(this.form,this.checked,this.nextSibling)">
								</label>
							</th>
						
							<th>
								缩略图
							</th>
							<th>
								文件大小
							</th>
							<th>
								图像高度（像素）
							</th>
							<th>
								图像宽度（像素）
							</th>
			
							<th>
								Exif版本
							</th>
						</tr>
					</thead>
						<tbody id="dataGrid" >
						
							 
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
</body>
<script>

		 var dataGrid =  new MaxTable();
		 dataGrid.initialize(
		  	{
		  		table:'dataGrid',
		  		loading:'loading',
		  		id:'id',
		  		queryUrl:'pictureAction!list.action',
		  		headerColumns:[{id:'id',renderer:IdCheckBoxRenderer},
		  		{id:'filename',renderer:picRenderer},
		  		{id:'fileSize',renderer:getNiceFileSize},
		  		{id:'srcHeight'},
		  		{id:'srcWidth'},
		  		{id:'exifVersion'}
		  		]
		  	}
		  );
		 function query(){
			 dataGrid.onLoad({});
		 } 	
		  query();
		  function picRenderer(idValue,value,record){
		 	  
	 	     	var txt="";
	 	     
	 
	 	     	txt += " <a href='"+record['url']+"' target=_blank><img src=${cxp}/"+record['path']+"85X85(crop)-"+record['filename']+"  border=0 title="+record['url']+"></a>";	
	 	  
	 	     	return txt;
	 	     }
		  function editRenderer(idValue,value,record){
			     	var txt="";
			     	//txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"weburlAction!edit.action?id="+idValue+"\",\"编辑\",400,500)'>编辑</a> ";
			     	//txt+= " <a href='javascript:;' onclick='start("+value+");'>启动</a>  <a href='javascript:;' onclick='stop("+value+");' >停止</a>";
			     	//txt+= " <a href='weburlAction!viewSpider.action?id="+idValue+"' >查看抓取</a>";
			     	return txt;
		 }
		  dataGrid.initSortHead(
			      {head:'myHead',cells:[{index:1,name:'id'},{index:2,name:'filename'}]}
		  );
		  function removeSelect(){
				var ids  = getCheckedValuesByContainer("c",$("#dataGrid"));
	
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
				
				var message = "您真的要删除<font color='red'>"+ids.length+"</font>个图片吗？";
			
						 
						window.parent.parent.jConfirm(message, '操作确认', function(r) {
						
							if (r) {
									var param = {
										ids:cs
									}
					
									doPost("pictureAction!remove.action", param, function(data) {
										
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


	 		var _K = 1024;
			 var _M = _K*1024;
			function getNiceFileSize(idValue,value){
				if(value<_M){
					if(value<_K){
						return value+'B';
			   		}else{
						return Math.ceil(value/_K)+'K';
					}
			
				}else{
					return Math.ceil(100*value/_M)/100+'M';
				}
		
			}
	</script>

</html>