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
  <script type="text/javascript" src="js/jquery.1.4.4.min.js"></script>
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
			asyncUrl: "geneaction!ajaxTree.action",  //获取节点数据的URL地址
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
			parent.reverseTree(treeNode);

			
		}

		var zNodes =[{'name':'基因树',iconSkin:"tagih",'type':4,'semProperty':2,'tagSelected':0,'commend':0,'tagModel':0,isParent:true}];

	$(document).ready(function(){
		zTree1 = $("#tagTree").zTree(setting, zNodes);
	});

	function getTime() {
		var now= new Date();
		var hour=now.getHours();
		var minute=now.getMinutes();
		var second=now.getSeconds();
		return (hour+":"+minute+":"+second);	
	}

	
	function removeConfirm(treeId, treeNode){
		if(confirm("确认是否删除该关系")){
			if(parent.getNodeName()===treeNode.name){
				parent.setNodeName("");
			}
			if(treeNode.name==='基因树'||treeNode.name==='标签库'||treeNode.name==='site:数据源'||treeNode.name==='addr:地区'){
				alert("不能删除固定节点");
				return false;
			}
			
			//alert(treeNode.name +" "+ treeNode.parentNode.name);
			var html = $.ajax({
				  type : "POST",
				  url: "geneaction!ajaxRemove.action",
				  data: {"tagRelation.tagName":treeNode.name,"tagRelation.parentTagName":treeNode.parentNode.name},
				  async: false
				 }).responseText;
			if(html.indexOf('true')!=-1){
				parent.reverseTree();
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	function reloadAsync(name) {
		var treeNode = zTree1.getSelectedNode();
		if (treeNode&&treeNode.name==name) {
			zTree1.reAsyncChildNodes(treeNode,"refresh");
		}else if($.trim(name)==''){
			
		}else{
			window.location.reload(true);
		}
	}

  //-->
  </SCRIPT>

</head>


<body>
		<div class="zTreeDemoBackground">
			<ul id="tagTree" class="tree"></ul>
		</div>
</body>
</html>