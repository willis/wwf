<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>网站后台</title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<script src="${cxp }/appjs/ajaxform.common.js"></script>
<style type="text/css">
table.form {
	width: 45em;
	margin: 20px auto 0px auto;
}

table.form tr {
	height: 2em;
}
</style>

</head>
<body>

	<form id="myForm" action="dictionaryAction!save.action" method="post">


		<div class="buttons">
			<input type="submit" name="subOk" value="提交" class="button" /> <input
				type="button" name="backOk" value="关闭" class="button"
				onclick="window.parent.closeWindow()" />

		</div>
		<p id="errorMsg" style="color: red">&nbsp;</p>
		<table class="table">


			<tr>
				<td width="161" bgcolor="#EEF2F2" class="leftAndTop">名称：</td>
				<td><input type="text" name="dictionary.name"
					style="width: 80%" value="<s:property value="dictionary.name"/>"
					dataType="Require" msg="名称不能为空" /></td>
			</tr>

			<tr>
				<td bgcolor="#EEF2F2" class="leftAndTop">描述：</td>
				<td><input type="text" name="dictionary.describe"
					style="width: 80%"
					value="<s:property value="dictionary.describe"/>"
					dataType="Require" msg="描述不能为空" /></td>
			</tr>

			<tr>
				<td bgcolor="#EEF2F2" class="leftAndTop">索引：</td>
				<td><input type="text" name="dictionary.orderby"
					style="width: 80%" value="<s:property value="dictionary.orderby"/>" />
				</td>
			</tr>



		</table>
		<input type="hidden" name="dictionary.parentObj.id"
			value="${rootObj.id }" /> <input type="hidden"
			value="<s:property value="dictionary.id"/>" name="dictionary.id"/ >
	</form>
</body>

<script>


			Validator.createCheckForm(document.forms[0]);
			/**
			回调
			**/
			function processJson(data) {
			if(data.status == SUCCESS){
				//图层解锁
			window.parent.parent.jAlert(data.message, "系统提示");
				$("#myForm").unblock();
				window.parent.frames["0"].location.reload();
				window.parent.parent.closeWindow();
			}else{
				window.parent.parent.jAlert(data.message, "系统提示");
				//图层解锁
				$("#myForm").unblock();
			}
			}
			
		</script>
</html>

