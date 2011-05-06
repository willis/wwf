<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>系统日志</title>
<%@ include file="/include/taglibs.jsp"%>

</head>

<body>


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


					<h2 class="underline" id="loading">系统日志</h2>



					<table class="table odTable" id="idTable">
						<thead>
							<tr>
								<th><input type="checkbox" id="checkall" /><span
									id="checkalltext">全选</span></th>

								<th>姓名</th>
								<th>日志类型</th>
								<th>操作内容</th>
								<th>IP</th>
								<th>创建时间</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="logList" status="stuts">


								<tr <s:if test="#stuts.odd != true"> class='td2'</s:if>>

									<td><s:property value="id" /></td>
									<td><s:property value="username" /></td>
									<td><s:property value="logtitle" /></td>
									<td><s:property value="logcontent" /></td>
									<td><s:property value="ip" /></td>
									<td><s:date name="createat" format="yyyy-MM-dd HH:MM:ss" />

									</td>
							</s:iterator>
							<tr>
								<td colspan="6"><pager:pageForm name="myPage"
										action="systemLog!logList.action" method="post">


									</pager:pageForm></td>
							</tr>
						</tbody>

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
