<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>基因库管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>

		<script src="${cxp }/appjs/ajaxform.common.js"></script>
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

						<table class="table">
							<tr bgcolor="ffffff">
								<td valign="top">
									<form id="myForm" name="myForm" action="geneaction!importRelation.action" method="post" >

											<h2 class="underline">批量导入标签关系</h2>
											<table id="inputTable" class="table">
												<tr>
												<td class="lefttd">
														文件上传：
													</td>
													<td style="padding:10px;">
															 <span id="mtag">
															 	<s:set var="object_id" value="name"/>
																<s:set var="annextype" value="'relation_files'"/>
																<s:set var="file_types" value="'*.xls'"/>
																<s:set var="systemType" value="'system_cms'"/>
																<s:set var="file_types_description" value="'请上传Excel2003类型的文件'"/>
																<%@include file="/swfupload/jsp/simple/index.jsp"%>
															</span>
													</td>
												</tr>
											</table>
											<div class="buttons" style="margin-top: 10px;">
												<input type="submit" class="button_big" id="submit" name="submit" value="保存" />
											</div>
											<br/>
											<h2 class="underline">失败的导入标签关系</h2>
											<div id="errorRelation"></div>
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
		  
			/**
			回调
			**/
			function processJson(data) {
				if(data.status){
					window.parent.parent.jAlert(data.message, "系统提示");
					$("#myForm").unblock();
					
				}else{
					for(var i=0,n=data.length;i<n;i++){
						$('#errorRelation').append('<div>'+data[i].parentTagName+' ---> '+ data[i].tagName +'</div>');
					}
					$("#myForm").unblock();
					window.parent.parent.jAlert("导入完成。", "系统提示");
				}
			}
		  
		  
		  
		//-->
		</SCRIPT>
</body>
</html>