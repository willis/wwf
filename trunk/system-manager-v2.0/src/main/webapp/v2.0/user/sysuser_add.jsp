<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form id="myForm" name="myForm" action="sysUser!save.action"
	method="post" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<table>
			<tr>
				<td width="100px">用户名：</td>
				<td><input type="text" name="sysUser.username"
					value="${sysUser.username}" class="required" alt="用户名不能为空"
					class="EditBox" style="width: 90%" /></td>
			</tr>

			<tr>
				<td>姓名：</td>
				<td><input type="text" name="sysUser.truename"
					value="${sysUser.truename}" style="width: 90%" class="required"
					alt="用户名不能为空" class="EditBox" />
				</td>
			</tr>


			<tr>
				<td>性别：</td>
				<td><select name="sysUser.sex" class="selectButtonCss">
						<option value="男" ${sysUser.sex=="男" ? 'selected' : '' }>男</option>
						<option value="女" ${sysUser.sex=="女"? 'selected' : '' }>女</option>
				</select>
				</td>


			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="password" name="sysUser.password"
					id="password" style="width: 90%" dataType="Require" msg="密码不能为空"
					class="EditBox" /></td>

			</tr>
			<tr>
				<td>邮箱：</td>
				<td><input type="text" name="sysUser.email"
					value="${sysUser.email}" style="width: 90%" class="EditBox" />
				</td>
			</tr>
			<tr>
				<td>电话：</td>
				<td><input type="text" name="sysUser.tel" style="width: 90%"
					value="${sysUser.tel}" class="EditBox" class="required"
					alt="电话不能为空" />
				</td>


			</tr>


			<tr>
				<td style="vertical-align: top;">其它：</td>
				<td><textarea name="sysUser.other" class="MultiEditBox"
						style="width: 90%; height: 100px">
					${sysUser.other}
				</textarea>
				</td>

			</tr>
		</table>
	</div>
	<div class="buttonActive">
		<div class="buttonContent">
			<button type="submit">保存</button>
		</div>
	</div>
</form>
</html>
