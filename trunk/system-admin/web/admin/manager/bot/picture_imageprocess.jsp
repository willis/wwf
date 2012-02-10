<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title></title>
		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>
		<script src="${cxp }/appjs/ajaxform.common.js"></script>
		<script src="${cxp}/js/page.js"></script>
		<style type="text/css">
#pdfleft{ 
border:1px solid #C5C4CC; 
width:140px;
height:570px; 
text-align: center;
overflow: auto;
/*overflow-x:auto;
overflow:scroll;*/
} 

#pdfmiddle{ 
border:1px solid #C5C4CC; 
} 


</style>
		
	</head>
<body style="background-color: transparent;" id="mybody">
	<script>
		wait("mybody", "页面加载中,请稍候...");
	</script>
	<form id="myForm" name="myForm" action="pictureAction!process.action" method="post" >
	<input type="hidden" name="picture.id" value="<s:property value="picture.id" />" />
	<input type="hidden" id="pictureTags" name="picture.tags" value="" />
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
				
		<table style="width:100%">
				
			<tr>
	
				<td  id="pdfleft" > <s:if test="picture!=null"> <img id="pdfimg" src="${cxp }/<s:property value="picture.path" /><s:property value="picture.filename" />"  style="max-width: 550px; max-height: 550px; height:expression(this.height > 550px ? '550px' : this.height);width:expression(this.width > 600px ? '600px' : this.width);"/></s:if>
									
								
				</td> 
				<td  id="pdfmiddle">
	
		
							
					<table class="table" style="width:100%;" >
					
					<tr>
						<td  width="20%"  class="lefttd">
							分类：
						</td>
						<td colspan="3">
							 <select name="picture.type" id="webtype"   dataType="Require" msg="分类不能为空"  style="width:150px" ></select> 
						</td>	
					</tr>
					<tr>
								<td width="20%" class="lefttd">标题：</td>
								<td><input type="text" id="title" name="picture.title"
									value="<s:property value="picture.title"/>"
									dataType="LimitChEn"  require="true" msg="标题必须在[1-17.5]个汉字之内" min="1"  max="35" style="width: 80%"
									class="EditBox" /> </td>
					</tr>
					<tr>
					
						<td colspan="4">
						<iframe id="relationFrame"
										name="relationFrame"
										src="${cxp }/system/gene/gene_post_tagformframe.jsp"
										scrolling="auto" frameborder="0" width="98%" height="400px"></iframe>
						
						</td>
					</tr>
			
					<tr>
						<td  width="20%" class="lefttd">
							说明：
						</td>
						<td colspan="3">
						<textarea id="remark"
										name="picture.remark" class="MultiEditBox"
										dataType="LimitChEn"  min="0" max="200"  msg="说明的长度不能超过100个 汉字"
										style="width: 80%; height: 40px" ><s:property value="picture.remark" /></textarea>
						</td>
					</tr>
		
					
				</table>
				
				<div class="buttons" style="margin-top: 10px;">
				
							<input type="button" class="button_big" id="prev" name="prev"  value="上一张" />
							<input type="button" class="button_big" id="mysubmit" name="mysubmit" value="发布" />
								<input type="button" class="button_big" id="remove" name="remove" value="删除" />
							  <input type="button" class="button_big" id="next" name="next" value="下一张"/>
				</div>
				</td>
				</tr>
				</table>
				
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
	
	$("#mysubmit").click(function() {
		
		if(""==relationFrame.getNotTags()){
			relationFrame.formTag(function(tags){
				$("#pictureTags").attr("value",tags.tags);
				if(tags.tags.length==0){
					window.parent.parent.jAlert("请添加标签库中已存在的标签", "系统提示");
					return ;
				}else{
					
					window.parent.parent.jConfirm('确定要发布<font color=red>'+$("#title").val()+'</font>吗？', '操作确认', function(r) {
						if (r) {
						$("#myForm").submit();
						}
						});
				}
				
			});
			
			
		}else{
			window.parent.parent.jAlert("文章中存在陌生标签，文章不能保存，<br/>请去掉陌生标签！", "系统提示");
			return;
		}

		
	});
	$("#prev").click(function() {
		window.location='${cxp }/manager/bot/pictureAction!imagePrev.action?type=prev&id=<s:property value="picture.id" />';
	});
	
	$("#next").click(function() {
		window.location='${cxp }/manager/bot/pictureAction!imageNext.action?type=next&id=<s:property value="picture.id" />';
	});
	$("#remove").click(function() {
		removeSelect(<s:property value="picture.id" />);
	});
	 function removeSelect(id){
			
			var cs = id;
			
			var message = "您真的要删除吗？";
		
					 
					window.parent.parent.jConfirm(message, '操作确认', function(r) {
					
						if (r) {
								var param = {
									ids:cs
								}
				
								doPost("pictureAction!remove.action", param, function(data) {
									
											if (data.status) {
												window.location='${cxp }/manager/bot/pictureAction!imagePrev.action?type=prev&id=<s:property value="picture.id" />';
												window.parent.parent.jAlert(data.message, "系统提示");
											}else{
											   
												window.parent.parent.jAlert(data.message, "系统提示");
											}
									});
						}
			});

		}
	 $("#webtype")
		.dictionary(
				{
					url : '${cxp }/manager/dictionary/dictionaryAction!getDictionarysByParentId.action',
					data : {
						id : '301'
					},
					defaultOp : [ {
						name : '请选择信息',
						value : ''
					} ],
					defaultValue : '${picture.type}',
					c1 : "name"//列名1
				});

	$(function() {
		<s:if test="picture==null && type=='next'">$("#next").hide();</s:if>
		<s:if test="picture==null && type=='prev'">$("#prev").hide();</s:if>
		 <s:if test="picture.tags!=null && picture.tags!=''">
			$(relationFrame.document).ready(function(){
				if(navigator.appName.indexOf("Microsoft Internet Explorer")!=-1 && document.all){
					relationFrame.ajaxAddTag("<s:property value='picture.tags' escape='false'/>"); 
				}else{
					relationFrame.onload = function(){        
						relationFrame.ajaxAddTag("<s:property value='picture.tags' escape='false'/>"); 
					};
				}
			});
	
	 </s:if>
		$(document).unblock();
	});
	</script>
</html>