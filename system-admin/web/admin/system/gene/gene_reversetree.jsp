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
	<title>基因库管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" href="js/zTreeStyle/zTreeStyle.css" type="text/css">
  <link rel="stylesheet" href="js/zTreeStyle/zTreeIcons.css" type="text/css">
  <script type="text/javascript" src="../../js/jquery/jquery-1.4.3.min.js"></script>
  <script type="text/javascript" src="js/jquery.ztree-2.6.min.js"></script>
	<style type="text/css">
	body {
		font-size: 12px;
	}
	</style>
 <SCRIPT LANGUAGE="JavaScript">
  <!--
	var zTree1;
	var setting;

		setting = {
			checkable: false,
			async: true,
			asyncUrl: "geneaction!ajaxReverseTree.action",  //获取节点数据的URL地址
			asyncParam: ["namecode"],  //获取节点数据时，必须的数据名称，例如：id、name
			editable: true,
			//编辑状态是否显示修改按钮
			edit_renameBtn:false,
			//编辑状态是否显示删除节点按钮
			edit_removeBtn:true,
			dragMove:false,
			keepParent: false,
			keepLeaf: false,
			callback:{
				click:	zTreeOnClick,
				beforeRemove :removeConfirm
			}
		};
		
		function zTreeOnClick(event, treeId, treeNode) {
			parent.setNodeName(treeNode);
			parent.tagView(treeNode);
		}

		var zNodes =[];
		var name = '<s:property value="addSlashes(name)" escape="false"/>';
		if(name==''||name=='基因树'){
			var zNodes =[];
		}else{
			zNodes =[{'name':'<s:property value="addSlashes(tag.name)" escape="false"/>','namecode':'<s:property value="encode(tag.name)"/>','iconSkin':'<s:property value="tag.typeIcon"/>','type':<s:property value="tag.type" default="0"/>,'semProperty':<s:property value="tag.semProperty" default="0"/>,'alias':'<s:property value="addSlashes(tag.alias.toString())" escape="false"/>','tagSelected':<s:property value="tag.tagSelected" default="0"/>,'commend':<s:property value="tag.commend" default="0"/>,'tagModel':<s:property value="tag.tagModel" default="0"/>,isParent:true,}];
		}
		
		function removeConfirm(treeId, treeNode){
			if(confirm("确认是否删除该关系")){
				if(treeNode.parentNode&&(parent.getNodeName()===treeNode.parentNode.name)){
					parent.setNodeName("");
				}
				if(name===treeNode.name||treeNode.parentNode.name==='基因树'||treeNode.parentNode.name==='标签库'||treeNode.parentNode.name==='site:数据源'||treeNode.parentNode.name==='addr:地区'){
					alert("不能删除固定节点");
					return false;
				}
				
				//alert(treeNode.name +" "+ treeNode.parentNode.name);
				var html = $.ajax({
					  type : "POST",
					  url: "geneaction!ajaxRemove.action",
					  data: {"tagRelation.tagName":treeNode.parentNode.name,"tagRelation.parentTagName":treeNode.name},
					  async: false
					 }).responseText;
				if(html.indexOf('true')!=-1){
					parent.treeFrame.reloadAsync("-1");
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		
	$(document).ready(function(){
		zTree1 = $("#tagTree").zTree(setting, zNodes);
	});

	function reloadAsync() {
		var treeNode = zTree1.getSelectedNode();
		if (!treeNode) {
			zTree1.selectNode(parent.getNodeName());
			return;
		}
		zTree1.reAsyncChildNodes(treeNode,"refresh");
	}
	
	function findTag(name){
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
					   $('#treeContent').html('查询中，请等待...');
					   parent.reverseTree(root.tag);
				   }else{
					   window.parent.parent.jAlert("没有该标签", "系统提示");
				   }
				}
				
			});
	}

  //-->
  </SCRIPT>

</head>


<body>
		<div>
			<input id="tagname" type="text" name="name" value="" maxlength="50"/> <input type="button" class="button_big" id="tagsubmit" name="tagsubmit" value="搜索" onClick="findTag($('#tagname').val())"/>
		</div>
		<span id="treeContent"></span>
		<div class="zTreeDemoBackground">
			<ul id="tagTree" class="tree"></ul>
		</div>
</body>
</html>