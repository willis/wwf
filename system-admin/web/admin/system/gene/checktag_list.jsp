<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  
    <title>提交标签列表</title>
	<%@ include file="/common/taglibs.jsp"%>
	<%@ include file="/common/jquery.jsp"%>
	<%@ include file="/common/data.jsp"%>
	
	<script>

		function addView(value){
			$("#tagForm").attr("action","geneaction!addView.action");
			$('#name').attr("value",value);
			$("#tagForm").submit();
		}
		function removeCheckTag(value){
			jConfirm("确定要放弃吗？","放弃标签",function(r) {
				if(r){
					$("#tagForm").attr("action","geneaction!removeCheckTag.action");
					$('#name').attr("value",value);
					$("#tagForm").submit();
				}
			});
		}
	</script>
  </head>
  
  <body>
	<form id="tagForm" action="" method="post">
		<input id="name" type="hidden" name="name" value=""/>
		<input id="status" type="hidden" name="status" value="<s:property value='status' />"/>
		<input id="createDate" type="hidden" name="createDate" value="<s:property value='createDate' />"/>
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
						<h2 class="underline" id="loading">标签审核管理</h2>
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
						<form id="searchform" action="geneaction!searchCheckTag.action" method="post">
							<table class="table" id="find_01" >
								<tr>
									<td>
									<input type="hidden" name="status" value="<s:property value='status' />"/>
									提交人：<input type="text" name="userName" value="<s:property value='userName'/>"/>
									提交时间：<input type="text" id="no" name="createDate" readOnly onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<s:property value='createDate'/>"/>
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
											提交人
										</th>
										<th>
											状态
										</th>
										<th>
											提交时间
										</th>
										<th>
											完成时间
										</th>
										<th>
											操作
										</th>
									</tr>
								</thead>
								<tbody>
								<s:iterator value="checkTagList" status="stuts">  
       
								<tr  <s:if test="#stuts.odd != true"> class='td2'</s:if>	>
									
         							<td>
         							<s:property value="name" />
         							</td>
         							<td>
         							 <s:property value="user.username" />
         							</td>
         							<td>
         								<s:if test="status==1">
         									<span style="color:red">未添加</span>
         								</s:if><s:elseif test="status==2">
         							 		<span style="color:green">已添加</span>
         							 	</s:elseif><s:else>
         							 		<span style="color:#000000">已放弃</span>
         							 	</s:else>
         							</td>
         							<td>
         							  <s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" />
         							</td>
         							<td>
         							  <s:date name="completeDate" format="yyyy-MM-dd HH:mm:ss" />
         							</td>
         							<td>
         							 <s:if test="status==1"><a href="javascript:addView('<s:property value='name' />')">添加</a> <a href="javascript:removeCheckTag('<s:property value='name' />')">放弃</a></s:if><s:else>无</s:else>
         							</td>
         						
								</s:iterator>
								<tr>
									<td colspan="7">

									<pager:pageForm name="myPage" action="geneaction!searchCheckTag.action" method="post">
									</pager:pageForm>
									<script>
										$("form[name='formPage']").append('<input type="hidden" name="status" value="<s:property value='status'/>"/>').append('<input type="hidden" name="userName" value="<s:property value='userName'/>"/>').append('<input type="hidden" name="createDate" value="<s:property value='createDate'/>"/>');
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
		<script>
			var opType = <s:property value="opType"/>;
		if(opType){
			jAlert("操作成功","信息提示");
		}
		</script>
  </body>
</html>
