<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>基因库管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@ include file="/common/taglibs.jsp"%>
		<%@ include file="/common/jquery.jsp"%>
		<%@ include file="/common/data.jsp"%>
		<script src="${cxp }/appjs/ajaxform.common.js"></script>
		<style>
		.intputWidth{
			width:50px;
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
							文章基因系数管理
						</h2>
									<form id="myForm" name="myForm1" action="geneaction!setting.action" method="post">
									<input type="hidden" name="status" value="1" />
											<table id="inputTable" class="table" >
												<tr>
													<td class="lefttd" width="180">
														实例化关系：
													</td>
													<td>
                                                         <input id="relation_instance" type="text" name="relationCountMap.relation_instance" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.relation_instance'/>" dataType="Double" msg="实例化必须是实数"/> 系数
													</td>
												</tr>
												<tr>
													<td>
														聚集关系：
													</td>
													<td>
                                                        <input id="relation_gather" type="text" name="relationCountMap.relation_gather" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.relation_gather'/>" dataType="Double" msg="聚集必须是实数"/> 系数
													</td>
												</tr>
												<tr>
													<td class="lefttd">
														属种关系：
													</td>
													<td>
                                                        <input id="relation_species" type="text" name="relationCountMap.relation_species" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.relation_species'/>" dataType="Double" msg="属种必须是实数"/> 系数
													</td>
												</tr>
												<tr>
													<td>
														关联关系：
													</td>
													<td>
														<input id="relation_relevance" type="text" name="relationCountMap.relation_relevance" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.relation_relevance'/>" dataType="Double" msg="关联必须是实数"/> 系数
													</td>
												</tr>
												<tr>
													<td class="lefttd">
														限制关系：
													</td>
													<td>
														<input id="relation_limit" type="text" name="relationCountMap.relation_limit" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.relation_limit'/>" dataType="Double" msg="限制必须是实数"/> 系数
													</td>
												</tr>
												<tr>
													<td>
														交叉节点：
													</td>
													<td>
														<input id="relation_cross" type="text" name="relationCountMap.relation_cross" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.relation_cross'/>" dataType="Double" msg="交叉必须是实数"/> 系数
													</td>
												</tr>
												<tr>
													<td class="lefttd">
														标签本身：
													</td>
													<td>
														<input id="relation_tagself" type="text" name="relationCountMap.relation_tagself" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.relation_tagself'/>" dataType="Double" msg="标签本身必须是实数"/> 系数
													</td>
												</tr>
												<tr>
													<td>
														其他标签：
													</td>
													<td>
														<input id="relation_tagother" type="text" name="relationCountMap.relation_tagother" class="intputWidth" maxlength="5" value="<s:property value='relationCountMap.relation_tagother'/>" dataType="Double" msg="标签本身必须是实数"/> 系数
													</td>
												</tr>
											</table>
											<div class="buttons" style="margin-top: 10px;">
												<input type="submit" class="button_big" id="submit" name="submit" value="保存"/>
											</div>
											
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
		  $(document).ready(function() {
				$('#myForm').ajaxForm({
							beforeSubmit : validateForm,
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
			});

		  Validator.createCheckForm(document.forms[0]);
			/**
			回调
			**/
			function processJson(data) {

			if(data.status == SUCCESS){
				//图层解锁
			window.parent.parent.jAlert(data.message, "系统提示");
				$("#myForm").unblock();

	
			}else{
			
				//图层解锁
				$("#myForm").unblock();
			}
			}
			

			
		//-->
		</SCRIPT>
</body>
</html>