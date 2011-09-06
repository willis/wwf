<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>用户密码-修改</title>
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
	<body style="background-color: transparent;">
			<form id="myForm" name="myForm" action="sysUser!changePassword.action" method="post"  
				>
				

			<table class="table">
			
			<tr>
				<td width="50"   >
					新密码：
				</td>
				<td  >
					<input type="password" name="password"  dataType="Require" msg="新密码不能为空"
						style="width:90%">
				</td>
				
			</tr>
			
			
		</table>
		<div class="buttons">
					<input type="submit" name="subOk" value="修改" class="button"/>
					<input type="button" name="backOk" value="关闭" class="button"
						onclick="window.parent.closeWindow()"/>
				</div>
		<input type="hidden" name="ids" value="${param.ids}" />
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
				window.parent.frames["0"].location.reload();
				//图层解锁
				$("#myForm").unblock();
			}
			}
		</script>		
	</body>
</html>

