<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>基因库管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>
		<link href="${cxp}/cms/gene/js/styles.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${cxp}/cms/gene/js/jquery.autocomplete-min.js"></script>
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
							基因库管理
						</h2>
						<table class="table">
							<tr bgcolor="ffffff">
								<td width="200" height="550" valign="top" class="tableLeftDown">
									<iframe id="treeFrame" src="geneaction!tree.action" scrolling="auto" frameborder="0" width=100% height=100%></iframe>
								</td>
								<td width="200" height="550" valign="top" class="tableLeftDown">
									<iframe id="reverseTreeFrame" src="geneaction!reverseTree.action" scrolling="auto" frameborder="0" width=100% height=100%></iframe>
								</td>
								<td valign="top">
									<form id="myForm" name="myForm1" action="geneaction!saveTag.action" method="post" >

											<h2 class="underline">标签关联</h2>
											<table id="inputTable" class="table" >
												<tr>
													<td width="150px"  class="lefttd">
														父标签：
													</td>
													<td>
														<input type="text" id="parentTagName" name="tagRelation.parentTagName" value="" dataType="Require" msg="父标签不能为空"
															class="EditBox" style="width:200px" readonly="readonly"/> <a href="javascript:void(0)" onClick="window.parent.showWindow('${cxp}/cms/gene/geneaction!search.action?t='+(new Date()).getTime(),'定位标签',400,400)">定位标签</a></td>
												</tr>
												<tr>
												<td>
														与父关系：
													</td>
													<td>
														<span id="rt1"><input id="relationType1" type="radio" name="tagRelation.relationType" value="1"/> 实例化</span>
                                                        <span id="rt2"><input id="relationType2" type="radio" name="tagRelation.relationType" value="2"/> 聚集</span>
                                                        <span id="rt3"><input id="relationType3" type="radio" name="tagRelation.relationType" value="3"/> 属种</span>
                                                        <span id="rt4"><input id="relationType4" type="radio" name="tagRelation.relationType" value="4"/> 关联</span>
                                                        <span id="rt5"><input id="relationType5" type="radio" name="tagRelation.relationType" value="5" dataType="Group"  msg="父关系不能为空"/> 限制</span>
                                                        <!--<span id="rt6"><input id="relationType6" type="radio" name="tagRelation.relationType" value="6" /> 交叉</span>-->
												</tr>
												<tr><td colspan="2" height="50">标签信息  <span id="alisaInfo" style="color:red"></span> <span style="float:right;"><span style="vertical-align:top;"><img src="js/zTreeStyle/img/sim/5.png"/>标签</span>  <span style="vertical-align:top;"><img src="js/zTreeStyle/img/sim/2.png"/>隐藏性标签</span>  <span style="vertical-align:top;"><img src="js/zTreeStyle/img/sim/4.png"/>归纳容器</span>  <span style="vertical-align:top;"><img src="js/zTreeStyle/img/sim/3.png"/>实例化容器</span></span></td></tr>
												<tr>
												<td class="lefttd">
														标签名：
													</td>
													<td>
														<span id="stag"><input type="text" id="tagname" name="tag.name" value="<s:property value='name'/>" style="width:200px" dataType="Require" msg="标签名不能为空"
															 class="EditBox" maxlength="50" /> <a href="javascript:void(0)" onClick="findTag($('#parentTagName').attr('value'),$('#tagname').attr('value'))">检查</a></span>
															 <span id="mtag" style="display:none">
															 	<s:set var="object_id" value="name"/>
																<s:set var="annextype" value="'tag_files'"/>
																<s:set var="systemType" value="'system_cms'"/>
																<s:set var="file_types" value="'*.xls'"/>
																<s:set var="file_types_description" value="'请上传Excel2003类型的文件'"/>
																<%@include file="/swfupload/jsp/simple/index.jsp"%>
															</span>
															<a id="tagupload" href="javascript:void(0)" onClick="switchUpload()">批量添加</a></td>
												</tr>
												<tr>
												<td>
														标签类型：
													</td>
													<td>
														<span id="tt1"><input id="type1" type="radio" name="tag.type" value="1" onClick="selectTagType($(this).val())"/> 标签</span>
														<span id="tt4"><input id="type4" type="radio" name="tag.type" value="4" onClick="selectTagType($(this).val())"/> 隐藏性标签</span>
														<span id="tt2"><input id="type2" type="radio" name="tag.type" value="2" onClick="selectTagType($(this).val())"/> 归纳容器</span>
                                                        <span id="tt3"><input id="type3" type="radio" name="tag.type" value="3" onClick="selectTagType($(this).val())" dataType="Group"  msg="必须选定标签类型"/> 实例化容器</span>
													</td>
												</tr>
												<tr>
												<td class="lefttd">
														标签属性：
													</td>
													<td>
                                                        <input id="semProperty1" type="radio" name="tag.semProperty" value="1" /> Who(person)
                                                        <input id="semProperty2" type="radio" name="tag.semProperty" value="2" /> Who(thing)
                                                        <input id="semProperty3" type="radio" name="tag.semProperty" value="3"/> Who(event)
                                                        <input id="semProperty4" type="radio" name="tag.semProperty" value="4"/> Where
                                                        <input id="semProperty5" type="radio" name="tag.semProperty" value="5" dataType="Group"  msg="必须选定标签属性"/> What
													</td>
												</tr>
												<!-- 
												<tr>
													<td>
														可订阅：
													</td>
													<td>
                                                        <span id="ts0"><input id="tagSelected0" type="radio" name="tag.tagSelected" value="0" checked onClick="selectDingyue()" /> 不可订阅</span>
                                                        <span id="ts1"><input id="tagSelected1" type="radio" name="tag.tagSelected" value="1" onClick="selectDingyue()" /> 可订阅</span>
													</td>
												</tr>
												<tr>
													<td class="lefttd">
														推荐：
													</td>
													<td>
                                                        <span id="co0"><input id="commend0" type="radio" name="tag.commend" value="0" checked/> 不推荐</span>
                                                        <span id="co1" style="display:none"><input id="commend1" type="radio" name="tag.commend" value="1"/> 推荐</span>
													</td>
												</tr>
												 -->
												<tr id="trAlias">
													<td>
														别名：
													</td>
													<td style="padding:5px;" id="aliasList"></td>
												</tr>
												<tr >
													<td>
													</td>
													<td>
													<input id="tagsave" type="hidden" name="tagsave" value="0" />
													<input type="button" class="button_big" id="tagsubmit" name="tagsubmit" value="保存修改" onClick="saveTag($('#tagname').val())"/></td>
												</tr>
											</table>
											<div class="buttons" style="margin-top: 10px;">
												<input type="button" class="button_big" id="submit" name="submit" value="保存" onClick="saveTagRelation($('#tagname').val())"/>
											</div>
											<br/><br/><br/>
											<table id="viewTable" class="table" >
												<tr>
													<td width="150px"  class="lefttd">
														标签名：
													</td>
													<td><span id="viewTagName"></span></td>
												</tr>
												<tr>
													<td>
														标签类型：
													</td>
													<td><span id="viewTagType"></span></td>
												</tr>
												<tr>
													<td class="lefttd">
														标签属性：
													</td>
													<td><span id="viewTagProperty"></span></td>
												</tr>
												<tr>
													<td>
														别名：
													</td>
													<td><span id="viewTagAlias"></span></td>
												</tr>
												<!-- 
												<tr>
													<td class="lefttd">
														客户端可订阅：
													</td>
													<td><span id="viewTagSelected"></span></td>
												</tr>
												<tr>
													<td>
														推荐：
													</td>
													<td><span id="viewCommend"></span></td>
												</tr>
												 -->
												<tr>
													<td class="lefttd">
														是否是来源标签：
													</td>
													<td><span id="viewTagModel"></span></td>
												</tr>
											</table>
											
									</form>	 
								</td>
							</tr>
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
		
		 <SCRIPT LANGUAGE="JavaScript">
		  <!--
		  
		 	var _parentNode;
		  
			$('#myForm').ajaxForm({
				beforeSubmit : null,
				clearForm : false,
				dataType : 'json',
				success : processJson,
				error : function(response) {
					if(response.responseText.indexOf('loginwindow')!=-1){
						parent.location.reload();
					}else{
						alert(response.responseText);
						//图层解锁
						$("#myForm").unblock();
					}
				}
			});

			function setNodeName(node){
				_parentNode = node;
			  $("#parentTagName").attr("value",node.name);
			  if($("#type2").attr('checked')||$("#type3").attr('checked')){
				  selectTagRelation(node.type,node.parentRelation);
				  $("#type1").attr('checked',true);
			  }else{
				  selectTagRelation(node.type,node.parentRelation);
			  }
			  
		  	}
			
			function selectTagType(type){
				if(type==3){
					$("span[id^='rt']").each(function (index, domEle) { 
						  // domEle == this 
						  $(domEle).hide();  
						});
					$('#relationType1').attr('checked',true)
					$('#rt1').show();
				}else{
					if(_parentNode&&(_parentNode.type==2||_parentNode.type==3)){
						
					}else{
						$("span[id^='rt']").each(function (index, domEle) { 
							  // domEle == this 
							  $(domEle).show();  
							});
					}
				}
			}
			
			function selectTagRelation(type,relationType){
				if(type==2){
					if(relationType){
						$("span[id^='rt']").each(function (index, domEle) { 
							  // domEle == this 
							  $(domEle).hide();  
							});
						$('#rt'+relationType).show();
						$('#rt'+relationType+' > input').attr('checked',true);
						$('#tt2').hide();
						$('#tt3').hide();
					}else{
						$("span[id^='rt']").each(function (index, domEle) { 
							  // domEle == this 
							  $(domEle).show();  
							});
					}
				}else if(type==3){
					$("span[id^='rt']").each(function (index, domEle) { 
						  // domEle == this 
						  $(domEle).hide();  
						});
					$('#rt1').show();
					$('#rt1 > input').attr('checked',true);
					$('#tt2').hide();
					$('#tt3').hide();
				}else if(type==-1){
					$('#tt2').show();
					$('#tt3').show();
					
				}else{
					$("span[id^='rt']").each(function (index, domEle) { 
						  // domEle == this 
						  $(domEle).show();  
						});
					$('#rt'+relationType+' > input').attr('checked',true);
					$('#tt2').show();
					$('#tt3').show();
				}
			}
			/*
			function selectDingyue(){
				var dingyue = $('input[name="tag.tagSelected"]:checked').val();
				var tuijian = $('input[name="tag.commend"]:checked').val();
				if(dingyue==1){
					$("span[id^='co']").each(function (index, domEle) { 
						  // domEle == this 
						  $(domEle).show();  
						});
					$('#commend'+tuijian).attr('checked',true);
				}else{
					$('#co1').hide();
					$('#commend0').attr('checked',true);
				}
			}*/
		  
			function getNodeName(){
				return $("#parentTagName").attr("value");
			}
			
			function tagView(node){
				//clear
				$("#viewTagName").text('');
				$("#viewTagType").text('');
				$("#viewTagProperty").text('');
				$("#viewTagAlias").text('');
				//$("#viewTagSelected").text('');
				//$("#viewCommend").text('');
				$("#viewTagModel").text('');
				//add
				$("#viewTagName").text(node.name);
				$("#viewTagType").text(typeConv(node.type));
				$("#viewTagProperty").text(propertyConv(node.semProperty));
				$("#viewTagAlias").text('');
				if(node.alias!='[]'){
					$("#viewTagAlias").text(node.alias);
				}
				/*
				if(node.tagSelected){
					$("#viewTagSelected").text('可订阅');
				}else{
					$("#viewTagSelected").text('不可订阅');
				}
				if(node.commend){
					$("#viewCommend").text('推荐');
				}else{
					$("#viewCommend").text('不推荐');
				}
				*/
				if(node.tagModel){
					if(node.tagModel==1){
						$("#viewTagModel").text('来源标签');
					}else if(node.tagModel==2){
						$("#viewTagModel").text('地址标签');
					}
					
				}else{
					$("#viewTagModel").text('文章标签');
				}
				
				
			}
			
			function reverseTree(node){
				if(node){
					$('#reverseTreeFrame').attr('src','geneaction!reverseTree.action?name='+node.namecode+"&iconSkin="+node.iconSkin+"&tag.type="+node.type+"&tag.semProperty="+node.semProperty);
				}else{
					$('#reverseTreeFrame').attr('src','');
				}
			}
			
			
			function addAliasLink(){
				inputNum = 0;
				document.getElementById("aliasList").innerHTML = '<a id="addInput" href="javascript:addAliasInput();">增加</a>';
			}
			
			var inputNum = 0;
			function addAliasInput(value){
				var aliasInput = '';
				if(inputNum!=0){
					aliasInput+='<br/>'
				}
				aliasInput+='<input id="alias'+inputNum+'" type="text" name="tag.alias" maxlength="50" style="width:200px;margin-top:5px;" class="EditBox" ';
				if(value){
					aliasInput+='value="'+value+'"';
				}
				aliasInput+='/> ';
				$('#addInput').before(aliasInput);
				inputNum++;
			}
			addAliasLink();
			addAliasInput();
			
			function findTag(parentName,name){
				if(name){
					$.ajax({
						   type: "POST",
						   url: "geneaction!findTag.action",
						   data: {'name':name,"parentName":parentName},
						   dataType : 'json',
						   success: function(msg){
							if(msg&&msg[0]&&msg[0].name){
								var tag = msg[0];
								if($.trim($("#tagname").val())!=tag.name){
									if($.trim($("#tagname").val()).toLowerCase()!=tag.name.toLowerCase()){
										$("#alisaInfo").html("(这是 '"+tag.name+"' 的别名)");
									}else{
										$("#alisaInfo").html("(这是 '"+tag.name+"' 的忽略大小写)");
									}
								}else{
									$("#alisaInfo").html("");
								}
								$("#type"+tag.type).attr("checked", true);
								$("#semProperty"+tag.semProperty).attr("checked", true);
								addAliasLink();
								if(tag.alias.length>0){
									for(var i=0,n=tag.alias.length;i<n;i++){
										addAliasInput(tag.alias[i]);
									}
								}else{
									addAliasInput();
								}
								if(msg[1]){
									$("#relationType"+msg[1].relationType).attr("checked", true);
								}else{
									$("input[name='tagRelation.relationType']").attr("checked", false);
								}
								selectTagType(tag.type);
								//$("#tagSelected"+tag.tagSelected).attr("checked", true);
								//$("#commend"+tag.commend).attr("checked", true);
								//selectDingyue();
							}else{
								$("#alisaInfo").html("");
								alert("没有该标签");
							}
						   },
						   error : function (XMLHttpRequest, textStatus, errorThrown) {
							    alert(textStatus);
							}
						});
					
				}else{
					 alert("标签名不能为空");
				}
				
			};

			Validator.createCheckForm(document.forms[0]);
			/**
			回调
			**/
			function processJson(data) {
				if(data.status == SUCCESS){
					window.parent.parent.jAlert(data.message, "系统提示");
					
					if($('#tagsave').val()==1||$("#type2").attr('checked')||$("#type3").attr('checked')){
						if(document.getElementById('treeFrame').contentWindow){
							document.getElementById('treeFrame').contentWindow.reloadAsync("-1");
						}else{
							treeFrame.reloadAsync("-1");
						}
					}else{
						if(document.getElementById('treeFrame').contentWindow){
							document.getElementById('treeFrame').contentWindow.reloadAsync(getNodeName());
						}else{
							treeFrame.reloadAsync(getNodeName());
						}
						
					}
					reverseTree();
					$("#tagsave").attr('value',0);
					var name;
					if(getNodeName()&&$.trim(getNodeName())!=''){
						name = getNodeName();
					}
					var type = $('input[name="tagRelation.relationType"]:checked').val();
					$("#myForm").clearForm();
					if(name){
						$("#parentTagName").attr('value',name);
					}
					if(type){
						$('#relationType'+type).attr('checked',true);
					}
					$("#alisaInfo").html('');
					//$("#tagSelected0").attr("checked", true);
					//$("#commend0").attr("checked", true);
					$("#co1").hide();
					//图层解锁
					$("#myForm").unblock();
					
				}else{

						window.parent.parent.jAlert(data.message, "系统提示");
						$("#tagsave").attr('value',0);
						//图层解锁
						$("#myForm").unblock();

					
				}
			}
			

			
			var uploadStatus = false;
			var tagnameInput = $('#stag').html();
			function switchUpload(){
				if(uploadStatus){
					uploadStatus = false;
					$('#stag').html(tagnameInput);
					$('#stag').show();
					$('#mtag').hide();
					$('#trAlias').show();
					$('#tagsubmit').show();
					$('#tagupload').text("批量添加");
				}else{
					uploadStatus = true;
					$('#stag').html('');
					$('#stag').hide();
					$('#mtag').show();
					$('#trAlias').hide();
					$('#tagsubmit').hide();
					$('#tagupload').text("单标签添加");
					
				}
			}
			
			function typeConv(type){
				switch (type) {
				case 1:
					return "标签";
				case 2:
					return "归纳容器";
				case 3:
					return "实例化容器";
				case 4:
					return "隐藏性标签";
				default:
					return "";
				}
			}
			
			function propertyConv(property){
				switch (property) {
				case 1:
					return "Who(person)";
				case 2:
					return "Who(thing)";
				case 3:
					return "Who(event)";
				case 4:
					return "Where";
				case 5:
					return "What";
				default:
					return "";
				}
			}

			
			function saveTag(tagName){
			    $('#tagsave').val(1);
				$('#parentTagName').val(" ");
				$('#relationType1').attr('checked',true);

				if(Validator.Validate($('#myForm')[0],2)){
					$.ajax({
						   type: "POST",
						   url: "geneaction!searchTag.action",
						   data: {'name':tagName},
						   dataType : 'json',
						   success: function(msg){
							   if(msg.hasTag){
								   window.parent.parent.jConfirm("已有该标签，是否覆盖", "系统提示",function(result){
									   if(result){
										   wait("myForm", "提交中,请稍候...");
											$('#myForm').submit();
									   }
	
								   });
							   }else{
								   window.parent.parent.jAlert("没有该标签，不能保存修改", "系统提示");
							   }
							}
						});
				}
			}
			
			function saveTagRelation(tagName){
				if($.trim($('#parentTagName').val())==''){
					$('#parentTagName').val('');
				}
				if(Validator.Validate($('#myForm')[0],2)){
					$.ajax({
				        type: "post",//使用get方法访问后台
				        dataType: "json",//返回json格式的数据
				        url: "geneaction!searchTag.action",
				        data: {'name':tagName},
				        success: function(msg){//data为返回的数据，在这里做数据绑定
				        	 if(msg.hasTag){
								   window.parent.parent.jConfirm("已有该标签，是否覆盖", "系统提示",function(result){
									   if(result){
										   wait("myForm", "提交中,请稍候...");
											$('#myForm').submit();
									   }
	
								   });
							   }else{
								   wait("myForm", "提交中,请稍候...");
									$('#myForm').submit();
							   }
				        	
				        }
				     });
			
				}

		}
			
			
		  	$(document).ready(function(){
			  	 var a = $('#tagname').autocomplete({ 
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