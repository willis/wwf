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
												onclick="">删除</a>
										</li>
										<li>
											<a class="add" href="javascript:"
												 onclick="window.parent.showWindow('${cxp}/manager/bot/bot_edit.jsp','添加',400,500)">添加</a>
										</li>
										<li>
											<a class="modify" href="javascript:"
												onclick="removeSelect(1)">恢复正常</a>
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
						
							<th width="200px;">
								文件名
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
								型号
							</th>
							<th>
								创建软件
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
		  		{id:'filename'},
		  		{id:'fileSize',renderer:getNiceFileSize},
		  		{id:'srcHeight'},
		  		{id:'srcWidth'},
		  		{id:'model'},
		  		{id:'software'},
		  		{id:'exifVersion'}
		  		]
		  	}
		  );
		 function query(){
			 dataGrid.onLoad({});
		 } 	
		  query();
		  function editRenderer(idValue,value,record){
			     	var txt="";
			     	//txt+= " <a href='javascript:' onclick='window.parent.showWindow(\"weburlAction!edit.action?id="+idValue+"\",\"编辑\",400,500)'>编辑</a> ";
			     	//txt+= " <a href='javascript:;' onclick='start("+value+");'>启动</a>  <a href='javascript:;' onclick='stop("+value+");' >停止</a>";
			     	//txt+= " <a href='weburlAction!viewSpider.action?id="+idValue+"' >查看抓取</a>";
			     	return txt;
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