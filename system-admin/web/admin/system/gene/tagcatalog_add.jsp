<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>基因库管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>

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
								<td valign="top">
									<form id="myForm" name="myForm1" action="geneaction!saveTagCatalog.action" method="post" onsubmit="return Validator.Validate(this,2)">
										<input id="tagModel" type="hidden" name="tagcatalog.tagModel" value="1"/>
										<input id="tagcatalogid" type="hidden" name="tagcatalog.id" value="<s:property value="tagcatalog.id"/>"/>
											<h2 class="underline">标签关联</h2>
											<div style="color:red"><s:property value="errorMsg"/></div>
											<table id="inputTable" class="table" >
												<tr>
													<td width="150px"  class="lefttd">
														分类名称：
													</td>
													<td>
														<input type="text" id="name" name="tagcatalog.name" value="<s:property value="tagcatalog.name"/>" dataType="Require" msg="分类名称不能为空"
															class="EditBox" style="width:200px"/> <font color="red">*</font></td>
												</tr>
												<tr>
													<td>
														绑定标签：
													</td>
													<td>
														<input type="text" name="tagcatalog.tagName" value="<s:property value="tagcatalog.tagName"/>" require="true" dataType="Custom" regexp="site:(.)+" msg="绑定标签不能为空且必须为媒体标签"/> <font color="red">*</font>
													</td>
												</tr>
												<tr>
													<td  class="lefttd">
														推荐：
													</td>
													<td>
														<input id="type1" type="radio" name="tagcatalog.commend" value="0" <s:if test="tagcatalog==null||tagcatalog.commend==0">checked="checked"</s:if>/> 否
														<input id="type4" type="radio" name="tagcatalog.commend" value="1" <s:if test="tagcatalog.commend==1">checked="checked"</s:if>/> 是
													</td>
												</tr>
												<tr>
													<td>
														显示类型：
													</td>
													<td>
                                                        <input id="semProperty1" type="radio" name="tagcatalog.displayType" value="0" <s:if test="tagcatalog==null||tagcatalog.displayType==0">checked="checked"</s:if>/> 普通
                                                        <input id="semProperty2" type="radio" name="tagcatalog.displayType" value="1" <s:if test="tagcatalog.displayType==1">checked="checked"</s:if>/> 新的
													</td>
												</tr>
												<!-- 
												<tr>
													<td class="lefttd">
														位置：
													</td>
													<td>
														<input type="text" name="tagcatalog.sortValue" value="<s:property value="tagcatalog.sortValue"/>" dataType="Integer" msg="位置不能为空且必须为数字"/> 
													</td>
												</tr>
												 -->
											</table>
											<div class="buttons" style="margin-top: 10px;">
												<input type="submit" class="button_big" id="submit" name="submit" value="保存"/>
											</div>

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
</body>
</html>