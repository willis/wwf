<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  
    <title>提交标签列表</title>
	<%@ include file="/common/taglibs.jsp"%>
	<%@ include file="/common/jquery.jsp"%>
	<%@ include file="/common/data.jsp"%>
	<link href="${cxp}/js/jquery/plugin/loadmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${cxp}/js/jquery/plugin/loadmask/jquery.loadmask.min.js"></script>
	<script>
	
	function editFrom(name,namecode,commend,special,newType,displayName,typeId){
		$('#namecode').val(namecode);
		$('#name').html(name);
		if(commend){
			$('#commend').attr('checked',true);
		}else{
			$('#commend').attr('checked',false);
		}
		if(special){
			$('#special').attr('checked',true);
		}else{
			$('#special').attr('checked',false);
		}
		
		$('#typeId').val(typeId);
		$('#displayType').val(newType);
		$('#displayName').val(displayName);
		$('#typeIdSelect').val(typeId);

		$('#subscribeList').hide();
		$('#editFrom').show();
		
	}
	
	function goback(){
		$('#editFrom').hide();
		$('#subscribeList').show();
	}

		function saveCommend(namecode,commendId,specialId,newType,displayNameId,typeId){
			if($("#"+displayNameId).val()==''){
				alert("显示名称不能为空。");
			}else{
				$(document.body).mask("Save ...");
				$.ajax({
					   type: "POST",
					   url: "geneaction!commendSave.action",
					   data: {'namecode':namecode,'opType':$("#"+commendId).attr('checked'),'special':$("#"+specialId).attr('checked'),'status':$("#"+newType).val(),'displayName':$("#"+displayNameId).val(),'typeId':typeId},
					   dataType : 'json',
					   success: function(msg){
						   if(msg.status){
							   alert(msg.message);
							   $("form[name='formPage']").submit();
						   }else{
							   alert(msg.message);
							   $(document.body).unmask();
						   }
					   },
					   error : function (XMLHttpRequest, textStatus, errorThrown) {
							alert("操作失败");
						   $(document.body).unmask();
						}
					});
			}
		}
		
		function ajaxCommend(namecode,selected){
			$(document.body).mask("Save ...");
			$.ajax({
				   type: "POST",
				   url: "geneaction!ajaxCommend.action",
				   data: {'namecode':namecode,'opType':selected},
				   dataType : 'json',
				   success: function(msg){
					   $(document.body).unmask();
				   },
				   error : function (XMLHttpRequest, textStatus, errorThrown) {
						alert("操作失败:"+textStatus);
					   $(document.body).unmask();
					}
				});
	}
		
		function ajaxRemoveCommend(namecode){
			if(confirm('你确定要取消该标签的"可订阅"功能吗?')){
				$(document.body).mask("Save ...");
					$.ajax({
						   type: "POST",
						   url: "geneaction!commendSave.action",
						   data: {'namecode':namecode},
						   dataType : 'json',
						   success: function(msg){
							   $("form[name='formPage']").submit();
						   },
						   error : function (XMLHttpRequest, textStatus, errorThrown) {
								alert("操作失败");
							   $(document.body).unmask();
							}
						});
			}
		}
		
		function removeList(){
			var tags = '';
			$(".tagsselected").each(function(i, domEle){
				if(domEle.checked){
					tags+=","+domEle.value;
				}
			});
			if(tags==''){
				alert("请选择要删除的标签");
				return;
			}else{
				tags = tags.substring(1);
			}
			if(confirm('你确定要删除操作吗?')){
				$(document.body).mask("Delete ...");
				$.ajax({
					   type: "POST",
					   url: "geneaction!removeSubscribeList.action",
					   data: {'tags':tags},
					   dataType : 'json',
					   success: function(msg){
						   $("form[name='formPage']").submit();
					   },
					   error : function (XMLHttpRequest, textStatus, errorThrown) {
							alert("操作失败");
						   $(document.body).unmask();
						}
					});
			}
		}
		
		function search(){
			$('#searchform').submit();
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
					<div id="subscribeList">
						<h2 class="underline" id="loading">可订阅标签列表</h2>
						<div class="editor" style="width: 100%;">
							<div id="editor_left"></div>
							<div id="editor_contents">
								<ul class="editor_link">
									<li><a class="find" href="javascript:void($('#find_01').toggle())">查询</a></li>
									<li><a class="delete" href="javascript:void(removeList())">删除</a></li>
								</ul>
								
							</div>
							<div id="editor_right"></div>
						</div>
						<form id="searchform" action="geneaction!siteSubscribeList.action" method="post">
							<table class="table" id="find_01" >
								<tr>
									<td>
									<input type="hidden" name="status" value="<s:property value='status' />"/>
									标签：<input type="text" name="name" value="<s:property value='name'/>"/>
									分类：<select id="searchTypeId" name="typeId">
											<option value="-1">请选择分类</option>	
         									<s:iterator value="tagcatalogList" status="stuts">
         										<option value="<s:property value="id" />" <s:if test="typeId==id">selected</s:if>><s:property value="name" /></option>
         									</s:iterator>
         								</select>
									 <input type="button" class="button" value="查询" onclick="search()">
									</td>
								</tr>
							</table>
						</form>
		
							<table class="table odTable" id="idTable">
								<thead>
									<tr>
										<th width="30">
											选
										</th>
										<th>
											名称
										</th>
										<th>
											显示名称
										</th>
										<th width="120">
											订阅时间
										</th>
										<th>
											分类目录
										</th>
										<th>
											显示类型
										</th>
										<th width="30">
											推荐
										</th>
										<th width="100">
											操作
										</th>
									</tr>
								</thead>
								<tbody>
								<s:if test="subscribeTagList.size()==0">
									<tr>
										<td colspan="8">无记录</td>
									</tr>
								</s:if><s:else>
								<s:iterator id="subscribeTagList" value="subscribeTagList" status="stuts">  
       
								<tr  <s:if test="#stuts.odd != true"> class='td2'</s:if>	>
									<td>
         								<input class="tagsselected" type="checkbox" value="<s:property value="encode(tagName)"/>"/>
         							</td>
         							<td>
         								<span id="tagName_<s:property value='#stuts.index'/>"><s:property value="tagName" /></span><s:if test="special==1"><span><img width="14" height="14" src="../../images/special.png"/></span></s:if>
         							</td>
         							<td>
         								<span id="displayNameId_<s:property value='#stuts.index'/>"><s:property value="displayName" /></span>
         							</td>
         							<td>
         							<s:property value="formatDate(createdDate)"/>
         							</td>
         							<td>
         									<s:iterator value="tagcatalogList" status="stuts">
         										<s:if test="#subscribeTagList.typeId==id"><s:property value="name" /></s:if>
         									</s:iterator>
         									<span id="typeId<s:property value='#stuts.index'/>" style="display:none"><s:property value="typeId" /></span>
         							</td>
         							<td>
         								<s:if test="displayType==0">普通频道</s:if><s:else>新频道</s:else>
         							</td>
         							<td>
         								<input id="commend<s:property value='#stuts.index'/>" type="checkbox" <s:if test="commend==1">checked="true"</s:if> onClick="ajaxCommend('<s:property value="encode(tagName)"/>',this.checked)"/>
         							</td>
         							<td>
         								<input type="button" value="编辑" onclick="editFrom($('#tagName_<s:property value='#stuts.index'/>').html(),'<s:property value="encode(tagName)"/>',$('#commend<s:property value='#stuts.index'/>').attr('checked'),<s:property value="special"/>,<s:property value="displayType" />,$('#displayNameId_<s:property value='#stuts.index'/>').html(),$('#typeId<s:property value='#stuts.index'/>').html())"/> <!-- <input type="button" value="删除" onclick="ajaxRemoveCommend('<s:property value="encode(tagName)"/>')"/> -->
         							</td>
         							</tr>
								</s:iterator>
								</s:else>
								<tr>
									<td colspan="8">

									<pager:pageForm name="myPage" action="geneaction!siteSubscribeList.action" method="post">
									</pager:pageForm>
									<script>
										$("form[name='formPage']").append('<input type="hidden" name="status" value="<s:property value='status'/>"/>').append('<input type="hidden" name="name" value="<s:property value='name'/>"/><input type="hidden" name="typeId" value="<s:property value='typeId'/>"/>');
									</script>
									</td>
								</tr>
								</tbody>
	
							</table>
						</div>	
							<div id="editFrom"  style="display:none">
									<input type="hidden" name="namecode" id="namecode" value="" />
										<h2 class="underline" id="loading">可订阅标签编辑</h2>
											<table id="inputTable" class="table">
												<tr>
													<td class="lefttd" width="180">
														标签本身：
													</td>
													<td>
														<div id="name"></div>
													</td>
												</tr>
												<tr>
													<td>
														是否推荐：
													</td>
													<td>
														<input id="commend" type="checkbox" value="false" />
													</td>
												</tr>
												<tr>
													<td class="lefttd">
														显示名称：
													</td>
													<td>
														<input id="displayName" type="text" name="displayName" class="intputWidth" maxlength="20" value="" dataType="Require" msg="显示名称必须输入"/> <font color="red">*</font>
													</td>
												</tr>
												<tr>
													<td >
														显示类型：
													</td>
													<td>
														<select id="displayType">
				         									<option value="0" >普通频道</option>
				         									<option value="1" >新频道</option>
				         								</select>
													</td>
												</tr>
												<tr>
													<td class="lefttd">
														目录：
													</td>
													<td>
														<select id="typeIdSelect" onChange="$('#typeId').val(this.value)">
															<option value="0">请选择目录</option>	
				         									<s:iterator value="tagcatalogList" status="stuts">
				         										<option value="<s:property value="id" />"><s:property value="name" /></option>
				         									</s:iterator>
				         								</select>
				         								<input id="typeId" type="hidden" value="0"/>
													</td>
												</tr>
												<tr>
													<td class="lefttd">
														是否专题：
													</td>
													<td>
														<input id="special" type="checkbox" value="true" />
													</td>
												</tr>
											</table>
											<div class="buttons" style="margin-top: 10px;">
												<input type="button" class="button_big" id="submit" name="submit" value="保存" onClick="saveCommend($('#namecode').val(),'commend','special','displayType','displayName',$('#typeId').val())"/> <input type="button" class="button_big" id="goback" name="goback" value="后退" onClick="goback()"/> 
											</div>
						</div>
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
