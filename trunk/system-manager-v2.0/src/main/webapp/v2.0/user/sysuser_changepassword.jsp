<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form id="myForm" name="myForm" action="sysUser!changePassword.action"
	method="post" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<input name="id" type="hidden" value="${param.userId}" />
		<table>
			<tr>
				<td>密码：</td>
				<td><input id="pwd" type="password" name="password"
					class="required" />
				</td>
			</tr>
			<tr>
				<td>确认密码：</td>
				<td><input type="password" name="password2" class="required"
					equalto="#pwd" /> <span class="info"></span>
				</td>
			</tr>
		</table>
	</div>
	<div class="buttonActive">
		<div class="buttonContent">
			<button type="submit">修改</button>
		</div>
	</div>
</form>
</html>