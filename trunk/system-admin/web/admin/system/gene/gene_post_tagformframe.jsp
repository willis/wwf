<%@ page contentType="text/html;charset=UTF-8"%>
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
		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>
		<link href="${cxp}/system/gene/js/styles.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${cxp}/cms/gene/js/jquery.autocomplete-min.js"></script>

		<style type="text/css">
			.tag span span{
				font-size: 14px;
			}
			.tag span{
				vertical-align:text-bottom;
				display: inline-block;
			}
			.tagExist{
				background-color: #ffffff;
			}
			.tagNot{
				color:#ffffff;
				background-color: #000000;
			}
		</style>
			 <SCRIPT LANGUAGE="JavaScript">

			 function ajaxAddTag(tags){
				 if(tags){
						$.ajax({
							   type: "POST",
							   url: "geneaction!ajaxAddTags.action",
							   data: {'tags':tags},
							   dataType : 'json',
							   success: function(tagArray){
								if(tagArray&&tagArray.length>0){
									for(var i=0,n=tagArray.length;i<n;i++){
										addTag(tagArray[i].name,tagArray[i].hasTag);
									}
								}
							   },
							   error : function (XMLHttpRequest, textStatus, errorThrown) {
								    window.location = '${cxp}/login.jsp';
								}
							});
						
					}else{
						 alert("标签不能为空");
					} 
			 }
			 
			 
		  	function addTag(tag,exist){
		  	  var tagsDiv = $('#tags');
		  	  if(!tagsDiv.data(tag.toLowerCase())){
		  		tagsDiv.data(tag.toLowerCase(),tag);
		  		var tagClass;
		  		if(exist){
		  			tagClass = 'tagExist';
		  		}else{
		  			tagClass = 'tagNot';
		  		}
		  		tagsDiv.append("<span style=''>&nbsp;&nbsp;&nbsp;<img src='../../images/remove_12.png' onclick=\"removeTag($(this).parent(),&quot;"+tag+"&quot;)\"/> <span class='"+tagClass+"'>"+tag+"</span></span>");
		  	  }
		  	}
		  	
		  	function removeTag(obj,name){
		  		$('#tags').removeData(name.toLowerCase());
		  		obj.remove();
		  	}
		  	function removeTags(){
		  		$('#tags > span').remove();
		  	}
		  	function getAllTags(){
		  		var tagStr = '';
		  		$('#tags > span > span').each(function (index, domEle) {
		  		  // domEle == this 
		  		  tagStr+=';'
		  		  tagStr+=$(domEle).text();
		  		}); 
		  		if(tagStr.length>0){
		  			return tagStr.substring(1);
		  		}else{
		  			return tagStr;
		  		}
		  	}
		  	
		  	function getNotTags(){
		  		var tagStr = '';
		  		$('#tags > span > span.tagNot').each(function (index, domEle) {
		  		  // domEle == this 
		  		  tagStr+=';'
		  		  tagStr+=$(domEle).text();
		  		}); 
		  		if(tagStr.length>0){
		  			return tagStr.substring(1);
		  		}else{
		  			return tagStr;
		  		}
		  	}
		  	
		  	function getTags(){
		  		var tagStr = '';
		  		$('#tags > span > span.tagExist').each(function (index, domEle) {
		  		  // domEle == this 
		  		  tagStr+=';'
		  		  tagStr+=$(domEle).text();
		  		}); 
		  		if(tagStr.length>0){
		  			return tagStr.substring(1);
		  		}else{
		  			return tagStr;
		  		}
		  	}
		  	
		  	var okBtnTxt = "提交给管理员";
		  	var cancelBtnTxt = "忽略";
		  	function formTag(callback) {
		  		var object = new Object();
		  		if(getNotTags()===''){
		  			object.hasAdmin = false;
		  			object.tags= getTags();
		  			if(callback) callback(object);
		  		}else{
		  
		  
					parent.jConfirm('标签 "'+getNotTags()+'" 为新标签，提交给管理员审核添加，文章被保存为草稿；如忽略则跳过进入文章待选库 ', '确定对话框', function(r) {
						if(r==true){
							object.hasAdmin = true;
				  			object.tags=getAllTags();
				  			object.notTags=getNotTags();
						}else{
							object.hasAdmin = false;
				  			object.tags=getTags();
				  			object.notTags=getNotTags();
						}
						if(callback) callback(object);
					}, okBtnTxt, cancelBtnTxt);
		  		}
			}
		  	$(document).ready(function(){
		  	 var a = $('#taginput').autocomplete({ 
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
		</SCRIPT>
	</head>
	<body style="background-color: transparent;">

		<table width="100%" height="330px;">
			<tr>
				<td valign="top">
					<div style="padding: 2px">标签名：<input type="text" id="taginput" name="taginput" value="" style="width:100px" maxlength="50" dataType="Require" msg="标签名不能为空" class="EditBox" /> <a href="javascript:void(0)" onClick="ajaxAddTag($('#taginput').val());$('#taginput').val('');">添加标签</a><!-- <a href="javascript:alert(getAllTags());alert(getNotTags());alert(getTags())">标签</a></div> -->
					<br/><br/>
					<div id="tags" class="tag"></div>
				</td>
				<td width="450" valign="top">

					<iframe id="relationFrame" src="geneaction!formTag.action" scrolling="auto" frameborder="0" width="98%" height="490px;"></iframe>	
				</td>
			</tr>
		</table>
</body>
</html>