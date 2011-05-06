<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/include/taglibs.jsp"%>
<head>
<title>网站后台</title>

<style type="text/css">
label.checkbox {
	cursor: pointer;
}
</style>
</head>
<body style="background-color: transparent;">
	<table class="tableContent">
		<tbody>
			<tr id="topRow">
				<td id="topLeft"></td>
				<td id="topMiddle"></td>
				<td id="topRight"></td>
			</tr>
			<tr id="middleRow">
				<td id="middleLeft"></td>
				<td id="tdContent" bgColor="#ffffff">

					<h2 class="underline">组织管理</h2>
					<table class="table">
						<tr bgcolor="ffffff">
							<td width="160" height="550" valign="top" class="tableLeftDown"><iframe
									id="treeFrame" src="sysGroup!tree.action" scrolling="auto"
									frameborder="0" width=100% height=100%></iframe>
							</td>
							<td height="550"><iframe id="mainf" src=""
									scrolling="auto" frameborder="0" width=100% height=100%></iframe>
							</td>
						</tr>
					</table></td>
				<td id="middleRight"></td>
			</tr>
			<tr id="bottomRow">
				<td id="bottomLeft"></td>
				<td id="bottomMiddle"></td>
				<td id="bottomRight"></td>
			</tr>
		</tbody>
	</table>
</body>
</html>