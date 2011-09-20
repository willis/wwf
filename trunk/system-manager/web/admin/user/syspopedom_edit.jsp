<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<script src="${cxp }/appjs/ajaxform.common.js"></script>
	<head>
		<title>网站后台</title>

	</head>
	<body>
		<form action="sysPopedom!save.action" 
			method="post"  id="myForm">

				<div class="buttons">
					<input type="submit" name="subOk" value="保存" class="button">
					<input type="button" name="backOk" value="关闭" class="button"
						onclick="window.parent.closeWindow()"/>
				</div>
				<p id="errorMsg" style="color:red">&nbsp;</p>
				
				<table class="table">
					<tr> 
						<td width="161" bgcolor="#EEF2F2" >
							权限代码：
						</td>
						<td >
							<input type="text" name="sysPopedom.code" 
								dataType="Require" msg="权限描述不能为空"  size="40"   value="<s:property value="sysPopedom.code"/>" />
						</td>
						
					</tr>

					<tr>
						<td  bgcolor="#EEF2F2" >
							权限描述：
						</td>
						<td >
							<input type="text" name="sysPopedom.describe" 
								dataType="Require" msg="权限描述不能为空" size="40"  value="<s:property value="sysPopedom.describe"/>"  />
						</td>
						
					</tr>
					
				</table>
<input type="hidden" value="<s:property value="sysPopedom.id"/>" name="sysPopedom.id"/ >
		</form>
		
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
		
	</body>
</html>

