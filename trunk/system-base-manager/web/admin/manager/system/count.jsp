<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  
    <title>系统日志</title>
	<%@ include file="/include/taglibs.jsp"%>
	<%@ include file="/include/jquery.jsp"%>
	<script src="${cxp}/js/page.js"></script>
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
		
								
		
							<table class="table odTable" id="idTable">
								<thead>
									<tr>
										<th>
										<span id="checkalltext">全选</span>
										</th>
										
										<th>
											姓名
										</th>
										<th>
											日志类型
										</th>
										<th>
											操作内容
										</th>
										<th>
											IP
										</th>
										<th>
											创建时间
										</th>
									</tr>
								</thead>
								<tbody>
								<s:iterator value="logList" status="stuts">  
       
								<tr  <s:if test="#stuts.odd != true"> class='td2'</s:if>	>
									
         							<td>
         							 <s:property value="id" />
         							</td>
         							<td>
         							<s:property value="username" />
         							</td>
         							<td>
         							 <s:property value="logtitle" />
         							</td>
         							<td>
         							
         							 <s:property value="logcontent" />
         							</td>
         							<td>
         							 <s:property value="ip" />
         							</td>
         							<td>
         							  <s:date name="createat" format="yyyy-MM-dd HH:mm:ss" />
         							 
         							</td>
         						
								</s:iterator>
								<tr id="page">
									<td colspan="6">
									<script language="JavaScript">
										<!--
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
										 $("#idTable tr").mouseover(function(){//如果鼠标移到class为stripe的表格的tr上时，执行函数
							   				if($(this).attr("id")!="page")
					           			 $(this).addClass("over");}).mouseout(function(){//给这行添加class值为over，并且当鼠标一出该行时执行函数
					            			if($(this).attr("id")!="page")
					           			 	$(this).removeClass("over");}); //移除该行的class
					           			//-->
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
