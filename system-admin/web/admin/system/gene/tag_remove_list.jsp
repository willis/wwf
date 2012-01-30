<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  
    <title>提交标签列表</title>
	<%@ include file="/include/taglibs.jsp"%>
	<%@ include file="/include/jquery.jsp"%>
	<script src="${cxp}/js/page.js"></script>
	<script>
		function removeTag(tag){
			$('#namecode').attr("value",tag);
			$("#tagForm").submit();
		}
		
	</script>
  </head>
  
  <body>
	<form id="tagForm" action="geneaction!removeTag.action" method="post">
		<input id="namecode" type="hidden" name="namecode" value=""/>
		<input id="status" type="hidden" name="status" value="<s:property value='status' />"/>
		<input id="mysubmit" type="submit" value="submit" style="display:none"/>
	</form>

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
						<h2 class="underline" id="loading">标签列表</h2>
						<div class="editor" style="width: 100%;">
							<div id="editor_left"></div>
							<div id="editor_contents">
								<ul class="editor_link">

									<li><a class="find"
										href="javascript:void($('#find_01').toggle())">查询</a>
									</li>

								</ul>
							</div>
							<div id="editor_right"></div>
						</div>
						<form id="searchform" action="geneaction!tagRemoveList.action" method="post">
							<table class="table" id="find_01" >
								<tr>
									<td>
									<input type="hidden" name="status" value="<s:property value='status' />"/>
									标签：<input type="text" name="name" value="<s:property value='name'/>"/>
									 <input type="button" class="button" value="查询" onclick="$('#searchform').submit()">
									</td>
								</tr>
							</table>
						</form>
		
							<table class="table odTable" id="idTable">
								<thead>
									<tr>
										<th>
											标签名
										</th>
										<th>
											订阅状态
										</th>
										<th>
											推荐状态
										</th>
										<th>
											操作
										</th>
									</tr>
								</thead>
								<tbody>
								<s:iterator value="tagList" status="stuts">  
       
								<tr  <s:if test="#stuts.odd != true"> class='td2'</s:if>	>
									
         							<td>
         							<s:property value="name" />
         							</td>
         							<td>
         							    <s:if test="tagSelected==0">
         									<span>不可订阅</span>
         							 	</s:if><s:else>
         							 		<span style="color:#FF0000">可订阅</span>
         							 	</s:else>
         							</td>
         							<td>
         							    <s:if test="commend==0">
         									<span>不推荐</span>
         							 	</s:if><s:else>
         							 		<span style="color:#FF0000">推荐</span>
         							 	</s:else>
         							</td>
         							<td>
         								<input type="button" onclick="removeTag('<s:property value="encode(name)"/>')" value="删除"/>
         							</td>
								</s:iterator>
								<tr>
									<td colspan="6">
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
