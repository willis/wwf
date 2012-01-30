<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  
    <title>提交标签列表</title>
	<%@ include file="/include/taglibs.jsp"%>
	<%@ include file="/include/jquery.jsp"%>
	<script src="${cxp}/js/page.js"></script>
	<link href="${cxp}/js/jquery/plugin/loadmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${cxp}/js/jquery/plugin/loadmask/jquery.loadmask.min.js"></script>
	<script>
	
	var _save = <s:property value='tagsave'/>;
	if(_save){
		alert("操作成功");
	}
	
	function ajaxUpdate(id){
		window.location = 'geneaction!tagCatalogView.action?tagcatalog.id='+id+'&status=<s:property value="status"/>';
	}
		
		function ajaxRemove(id){
			if(confirm('你确定要删除该分类吗?')){
				$(document.body).mask("Save ...");
				$.ajax({
					   type: "POST",
					   url: "geneaction!removeTagCatalog.action",
					   data: {'tagcatalog.id':id},
					   dataType : 'json',
					   success: function(msg){
						   window.location.reload();
					   },
					   error : function (XMLHttpRequest, textStatus, errorThrown) {
							alert("操作失败");
						   $(document.body).unmask();
						}
					});
			}
		}
	</script>
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
						<h2 class="underline" id="loading">媒体分类列表</h2>
						<div class="editor" style="width: 100%;">
							<div id="editor_left"></div>
							<div id="editor_contents">
								<ul class="editor_link">
									<li>
										<a class="add" href="geneaction!tagCatalogView.action?status=<s:property value="status"/>">添加</a>
									</li>

								</ul>
							</div>
							<div id="editor_right"></div>
						</div>
							<table class="table odTable" id="idTable">
								<thead>
									<tr>
										<th width="30">
											推荐
										</th>
										<th>
											名称
										</th>
										<th>
											显示类型
										</th>
										<th width="100">
											操作
										</th>
									</tr>
								</thead>
								<tbody>
								<s:iterator value="tagcatalogList" status="stuts">  
       
								<tr  <s:if test="#stuts.odd != true"> class='td2'</s:if>	>
									<td>
         								<input id="commend<s:property value="#stuts.index"/>" type="checkbox" <s:if test="commend==1">checked="true"</s:if> />
         							</td>
         							<td>
         								<a href='geneaction!siteSubscribeList.action?status=<s:property value="status"/>&typeId=<s:property value="id"/>'><s:property value="name" /></a>
         							</td>
         							<td>
         								<s:if test="displayType==0">普通目录</s:if>
         								<s:else>新目录</s:else>
         							</td>
         							<td>
         								<input type="button" value="修改" onclick="ajaxUpdate('<s:property value="id"/>')"/> <input type="button" value="删除" onclick="ajaxRemove('<s:property value="id"/>')"/>
         							</td>
								</s:iterator>
								<tr>
									<td colspan="4">
									<script type="text/javascript">
									var pg = new showPages('pg');
									pg.pageSize = <s:property value="pageInfo.pageSize"/>;
									pg.firstPage = <s:property value="pageInfo.firstPage"/>;
									pg.lastPage = <s:property value="pageInfo.lastPage"/>;
									pg.prePage = <s:property value="pageInfo.prePage"/>;
									pg.nextPage = <s:property value="pageInfo.nextPage"/>;
									pg.pageNo = <s:property value="pageInfo.pageNo"/>;
									pg.totalPage = <s:property value="pageInfo.totalPage"/>;
									pg.totalCount = <s:property value="pageInfo.totalCount"/>;
									pg.argName = 'pageInfo.pageNo';
									pg.printHtml(2);
									</script>
									</td>
								</tr>
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
</html>
