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
			asyncUrl: "geneaction!ajaxTree.action",  //获取节点数据的URL地址
			asyncParam: ["namecode"],  //获取节点数据时，必须的数据名称，例如：id、name
			editable: false,
			//编辑状态是否显示修改按钮
			edit_renameBtn:false,
			//编辑状态是否显示删除节点按钮
			edit_removeBtn:false,
			dragMove:false,
			keepParent: false,
			keepLeaf: false,
			callback:{
				click:	zTreeOnClick
			}
		};
		
		function zTreeOnClick(event, treeId, treeNode) {
			if(treeNode.type&&treeNode.type==1){
				parent.addTag(treeNode.name,true);
			}else{
				alert("该类型标签不能打到文章上");
			}
		}

		var zNodes =[{'name':'基因树',iconSkin:"tagih",'type':4,tagSelected:0,commend:0,tagModel:0,isParent:true}];

	$(document).ready(function(){
		zTree1 = $("#tagTree").zTree(setting, zNodes);
	});


  //-->
  </SCRIPT>

	</head>
	<body style="background-color: transparent;">


		<div class="zTreeDemoBackground">
			<ul id="tagTree" class="tree"></ul>
		</div>

</body>
</html>