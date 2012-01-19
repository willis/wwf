<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>标签搜索</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/taglibs.jsp"%>
	<link rel="stylesheet" href="js/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="js/zTreeStyle/zTreeIcons.css" type="text/css">
	<script type="text/javascript" src="js/jquery.1.4.4.min.js"></script>
	<script type="text/javascript" src="js/jquery.ztree-2.6.min.js"></script>
	<link href="${cxp}/cms/gene/js/styles.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${cxp}/cms/gene/js/jquery.autocomplete-min.js"></script>

</head>
	<body style="background-color: transparent;" id="mybody">
		

				<table class="table" width="60%">
					<tr>
						<td width="150px"  class="lefttd">
							标签：
						</td>
						<td>
							<input type="text" id="name" name="name" value="" class="EditBox" style="width:200px" /></td>
							
					</tr>
				</table>
				<div class="buttons" style="margin-top: 10px;">
					<input type="submit" class="button_big" id="submit" name="submit" value="定位" onclick="search($('#name').attr('value'))"/>
				</div>
		<div class="zTreeDemoBackground">
			<ul id="tagTree" class="tree"></ul>
		</div>
			 <SCRIPT LANGUAGE="JavaScript">
			  <!--
				var zTree1;
				var setting;

					setting = {
						checkable: false,
						async: true,
						asyncUrl: "geneaction!ajaxTree.action",  //获取节点数据的URL地址
						asyncParam: ["namecode"],  //获取节点数据时，必须的数据名称，例如：id、name
						editable: false,
						dragMove:false,
						keepParent: false,
						keepLeaf: false,
						callback:{
							click:	zTreeOnClick
						}
					};
					
					function zTreeOnClick(event, treeId, treeNode) {
						parent.rFrame.setNodeName(treeNode);
						parent.rFrame.tagView(treeNode);
						parent.closeWindow();
					}
			
					var zNodes =[];
					var name = '<s:property value="addSlashes(name)" escape="false"/>';
					if(name==''||name=='基因树'){
						var zNodes =[];
					}else{
						zNodes =[{'name':'<s:property value="addSlashes(tag.name)" escape="false"/>','namecode':'<s:property value="encode(tag.name)"/>','iconSkin':'<s:property value="tag.typeIcon"/>','type':<s:property value="tag.type" default="0"/>,'semProperty':<s:property value="tag.semProperty" default="0"/>,'alias':'<s:property value="addSlashes(tag.alias.toString())" escape="false"/>','tagSelected':<s:property value="tag.tagSelected" default="0"/>,'commend':<s:property value="tag.commend" default="0"/>,'tagModel':<s:property value="tag.tagModel" default="0"/>,isParent:true}];
					} 
			
				function search(name){
					if(name){
						$.ajax({
							   type: "POST",
							   url: "geneaction!searchTag.action",
							   data: {'name':name},
							   success: function(msg){
								   var root;
								   if (typeof msg == "string") {
										root = eval("(" + msg + ")");
								   }else{
									   root = msg;
								   }
								   if(root.hasTag){
									    window.location = 'geneaction!search.action?namecode='+root.tag.namecode;
								   }else{
									   window.parent.parent.jAlert("没有该标签", "系统提示");
								   }
							   }
							});
						
					}else{
						zNodes = [];
					}
					
				};
			
			
				function reloadAsync() {
					var treeNode = zTree1.getSelectedNode();
					if (!treeNode) {
						alert("请先选中一个节点");
						return;
					}
					zTree1.reAsyncChildNodes(treeNode,"refresh");
				}
				
				zTree1 = $("#tagTree").zTree(setting, zNodes);

			  	$(document).ready(function(){
				  	 var a = $('#name').autocomplete({ 
				  	    serviceUrl:'geneaction!likeTag.action',
				  	    minChars:1, 
				  	    delimiter: /(,|;)\s*/, // regex or character
				  	    maxHeight:400,
				  	    width:260,
				  	    zIndex: 9999,
				  	    deferRequestBy: 0, //miliseconds
				  	    //params: { country:'Yes' }, //aditional parameters
				  	    noCache: false //default is false, set to true to disable caching
				  	    // callback function:
				  	    //onSelect: function(value, data){ alert('You selected: ' + value + ', ' + data); }
				  	  });
				  	});
			
			  //-->
			  </SCRIPT>
	</body>
</html>
