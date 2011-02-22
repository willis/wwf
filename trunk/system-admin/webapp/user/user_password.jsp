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
			<form action="sysUser!changePassword.action" method="post"  
				>
				<div class="buttons">
					<input type="submit" name="subOk" value="修改" class="button"/>
					<input type="button" name="backOk" value="关闭" class="button"
						onclick="window.parent.closeWindow()"/>
				</div>

			<table class="table" >
			
			<tr>
				<td width="50"   >
					新密码：
				</td>
				<td  >
					<input type="password" name="password" checkInfo="新密码;NOTNULL;No"
						style="width:90%">
				</td>
				
			</tr>
			<input type="hidden" name="ids" value="${param.ids}" />
			
		</table>
		</form>

		<script>
			function checkFormMe(form){

			       return checkForm(form);
			
			}
			document.forms[0].setAttribute("id","myForm")
			FormCheck.createCheckForm(document.forms[0]);
			
		</script>		
	</body>
</html>

