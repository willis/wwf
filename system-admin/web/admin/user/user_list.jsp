<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<%@ include file="/include/gt-grid.jsp"%>
<script type="text/javascript" src="${cxp }/appjs/user/user.js"></script>
	<head>
		<title>系统后台</title>

		<style type="text/css">
			label.checkbox {
				cursor: pointer;
			}
		</style>
	</head>
	<body>
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
					用户管理
				</h2>
				<div class="editor" style="width: 100%;">
					<div id="editor_left"></div>
					<div id="editor_contents">
						<ul class="editor_link">
							<li>
								<a   name="add" class="add"
									href="javascript:;" onclick="window.parent.showWindow('${cxp }/user/sysuser_add.jsp','添加用户',400,500)">添加</a>
							</li>
												
					<li><a class="modify" href="javascript:;" onclick="removeSelect(1)">删除</a></li>
					<li><a class="modify" href="javascript:;" onclick="removeSelect(2)">冻结</a></li>
					<li><a class="modify" href="javascript:;" onclick="removeSelect(0)">恢复正常</a></li>
					<li><a class="modify" href="javascript:;" onclick="updateSelect()">修改密码</a></li>
					<li>
						<a class="find" href="javascript:void($('#find_01').toggle())">查询</a>

					</li>	
							
						</ul>
					</div>
					<div id="editor_right"></div>
				</div><form action="sysUserAction.do" method="post" >
							<table class="table" id="find_01" style="display:none">
			


			<tr><td>
			用户名：<input type="text" name="username"  >姓名：<input type="text" name="truename"  >&nbsp;
			
			状态：<select name="status">
			<option value="-1">全部</option>
			<option value="0" selected>正常</option>
			<option value="1">已删除</option>
			<option value="2">已冻结</option>
			</select>
			<input type="button" onclick="query()"  class="button" value="查询" >
			</td></tr>
			
			</table></form>
		<!-- grid的容器. -->
						<div id="grid1_container">
						</div>


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