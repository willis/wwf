<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>jQuery EasyUI</title>
	<%@ include file="/include/taglibs.jsp"%>
	<%@ include file="/include/jquery.jsp"%>

</head>
<body>

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
								数据源管理
							</h2>
							<div class="editor" style="width: 100%;">
								<div id="editor_left"></div>
								<div id="editor_contents">
									<ul class="editor_link">

										<li>
											<a class="delete" href="javascript:"
												onclick="removeSelect(3)">删除</a>
										</li>
										<li>
											<a class="add" href="javascript:"
												onclick="">添加</a>
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
							<th style="width: 80px;">
								<label class="checkbox">
									<input type="checkbox" name="c_all"
										onClick="selectAll(this.form,this.checked,this.nextSibling)">
									全选
								</label>
							</th>
							<th style="width: 100px;">
								操作
							</th>
							<th>
								网站名称
							</th>
							<th>
								网站链接
							</th>
							<th>
								网站英文名称
							</th>
							
						</tr>
					</thead>
						<tbody id="myTable" >
						
							 
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
</body>
<script>

		 var dataGrid =  new MaxTable();
		 dataGrid.initialize(
		  	{
		  		table:'dataGrid',
		  		loading:'loading',
		  		id:'id',
		  		queryUrl:'weburlAction!list.action',
		  		headerColumns:[{id:'id'},
		  		{id:'id'},
		  		{id:'siteName'},
		  		{id:'enName'}
		  		]
		  	}
		  );
		  function query(){
			  dataGrid.page.totalRowNum = 0;
			  dataGrid.onLoad({});
		   } 
		  query();
	</script>

</html>