<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  
    <title>提交标签列表</title>
	<%@ include file="/include/taglibs.jsp"%>
	<%@ include file="/include/jquery.jsp"%>
	<script type="text/javascript" src="${cxp}/js/jquery/plugin/ui/js/jquery-ui-1.7.2.custom.min.js"></script>
	<link href="${cxp}/js/jquery/plugin/ui/css/smoothness/jquery-ui-1.7.2.custom.css" rel="stylesheet" type="text/css" />
	<link href="${cxp}/js/jquery/plugin/loadmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${cxp}/js/jquery/plugin/loadmask/jquery.loadmask.min.js"></script>
	<style>
		#sortable { list-style-type: none; margin: 0; padding: 0; width: 60%; }
		#sortable li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; height: 18px; }
		#sortable li span { position: absolute; margin-left: -1.3em; }
	</style>
	<script>
	
		$(function() {
			$( "#sortable" ).sortable();
			$( "#sortable" ).disableSelection();
		});

		function ajaxSave(){
			var list = $(".ui-state-default");
			if(list.length==0){
				alert("列表不能为空。");
			}else{
				$(document.body).mask("Save ...");
				var tagsList = [];
				list.each(function (index, domEle) {
					tagsList[index]=$(domEle).attr("tagname");
				});
				$.ajax({
					   type: "POST",
					   url: "geneaction!saveCommendSort.action",
					   data: {'subscribeTags':tagsList},
					   dataType : 'json',
					   success: function(msg){
						   alert("保存成功");
						   $(document.body).unmask();
					   },
					   error : function (XMLHttpRequest, textStatus, errorThrown) {
							alert("操作失败:"+textStatus);
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

						<h2 class="underline" id="loading">推荐列表排序</h2>
						<div class="buttons" style="margin-top: 10px;">
							<input type="button" class="button_big" id="submit" name="submit" value="保存" onClick="ajaxSave()"/>
						</div>
						<ul id="sortable">
							<s:iterator value="subscribeTagList" status="stuts">
								<li class="ui-state-default" tagname="<s:property value="encode(tagName)"/>"><s:property value="displayName"/></li>
							</s:iterator>
						</ul>
						<div class="buttons" style="margin-top: 10px;">
							<input type="button" class="button_big" id="submit" name="submit" value="保存" onClick="ajaxSave()"/>
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
