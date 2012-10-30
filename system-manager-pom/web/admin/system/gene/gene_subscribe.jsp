<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>基因库管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>
		<script src="${cxp }/appjs/ajaxform.common.js"></script>
		<style>
			.taglist{

			}
		</style>
	</head>
	<body style="background-color: transparent;">
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
					
						<h2 class="underline">
							基因发布管理
						</h2>
						<form id="myForm" name="myForm1" action="geneaction!subscribeSave.action" method="post">
							<table id="inputTable" class="table" >
								<tr>
									<td width="200" height="550" valign="top" class="tableLeftDown">
										<iframe id="treeFrame" src="geneaction!tree.action" scrolling="auto" frameborder="0" width=100% height=100%></iframe>
									</td>
									<td width="200" height="550" valign="top" class="tableLeftDown">
										<iframe id="reverseTreeFrame" src="geneaction!reverseTree.action" scrolling="auto" frameborder="0" width=100% height=100%></iframe>
									</td>
									<td valign="top">
										<div id="tags" style="line-height: 10px;margin: 10px;width:98%; height: 500px; overflow-x: auto; overflow-y: auto; align: center;"  noWrap="true"></div>
										<div class="buttons" style="margin-top: 10px;">
											<input type="hidden" id="parentName" name="parentName" value=""/>
											<input type="submit" class="button_big" id="submit" name="submit" value="可订阅"/>
										</div>
									</td>
								</tr> 
							</table>	
						</form>
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
		
		 <SCRIPT LANGUAGE="JavaScript">
		  <!--
		  
		  var tags = $('#tags');
		  var currTag;
		  $(document).ready(function() {
				$('#myForm').ajaxForm({
							beforeSubmit : dataFrom,
							clearForm : false,
							dataType : 'json',
							success : processJson,
							error : function(response) {
								if(response.responseText.indexOf('loginwindow')!=-1){
									parent.location.reload();
								}else{
									alert("操作失败");
									//图层解锁
									$("#myForm").unblock();
								}
							}
						});
			});
		  
		  function dataFrom(){
			  wait("myForm", "提交中,请稍候...");
		  }
		  
			/**
			回调
			**/
			function processJson(data) {
				window.parent.parent.jAlert(data.message, "系统提示");
				$("#myForm").unblock();
			}
		  
			function setNodeName(node){
				$('#parentName').val(node.name);
				currTag = node;
				ajaxTagList(node.namecode);
		  	}
			
			function getNodeName(){
				return currTag;
			}
			
			function tagView(treeNode){}
			
			function reverseTree(node){
				if(node){
					$('#reverseTreeFrame').attr('src','geneaction!reverseTree.action?name='+node.namecode+"&iconSkin="+node.iconSkin+"&tag.type="+node.type+"&tag.semProperty="+node.semProperty);
				}else{
					$('#reverseTreeFrame').attr('src','');
				}
			}
			
			 function ajaxTagList(namecode){

						$.ajax({
							   type: "POST",
							   url: "geneaction!ajaxSubscribeTags.action",
							   data: {'namecode':namecode},
							   dataType : 'json',
							   success: function(tagArray){
								tags.html('');
								if(tagArray&&tagArray.length>0){
									for(var i=0,n=tagArray.length;i<n;i++){
										addTag(i,tagArray[i]);
									}
								}
							   },
							   error : function (XMLHttpRequest, textStatus, errorThrown) {
								   //alert("操作失败");
								    //window.location = '${cxp}/login.jsp';
								}
							});

			 }

		  	function addTag(index,tag){
		  		var s = "<span class='taglist'>&nbsp;&nbsp;&nbsp;<div><input type='checkbox' class='tagname' value='"+tag.name+"'";
		  		if(tag.type==1){
		  			s+="";
		  		}else{
		  			s+=" disabled='true'";
		  		}
		  		s+=" onclick='selectTag(this,\"tag"+index+"\","+tag.type+")'";
		  		if(tag.tagSelected){
		  			s+=" checked='true'/>";
		  		}else{
			  		s+=" />";		  			
		  		}
		  		s+="<input type='hidden' id='tag"+index+"' name='subscribeTags' value=''/> <span class='tagExist'>"+tag.name+"</span></div></span>";	
		  		tags.append(s);
			}
		  	
		  	function selectTag(tag,el,notSelectType){

		  		if(notSelectType==1){
		  			$("#"+el).val(tag.value+";"+tag.checked);
		  		}
		  		
		  	}


			

			
		//-->
		</SCRIPT>
</body>
</html>