<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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

						<table class="table">
							<tr bgcolor="ffffff">
								<td valign="top">
									<h2 class="underline">抓取监控</h2>
									<table id="inputTable" class="table">
										<tr>
											<td class="lefttd">
												正在抓取 <span id="spider_url"></span>
											</td>
										</tr>
										<tr>
											<td style="padding:10px;">
												<span id="pic_url_list"></span>
											</td>
										</tr>
									</table>
									<div class="buttons" style="margin-top: 10px;">
										<input type="submit" class="button_big" id="submit" name="submit" value="保存" />
									</div>
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
		  
		  
		//-->
		</SCRIPT>
</body>
</html>