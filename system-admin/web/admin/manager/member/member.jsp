<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<script src="${cxp }/appjs/ajaxform.common.js"></script>

	<head>
		<title>用户修改</title>

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
		
		
				
				<div class="buttons">
			
					<input type="button" name="backOk" value="关闭" class="button"
						onclick="window.parent.closeWindow()"/>
						
				</div>
				<p id="errorMsg" style="color:red">&nbsp;</p>
				<table class="table" >
					<tr>
						<td>
							用户名：
						</td>
						<td>
						${member.username}
						</td>
						<td>
							昵称：
						</td>
						<td>
						${member.name}
						</td>
							
						
					</tr>
					
					<tr>
					
		
					</tr>
					
					
					
				</table>
	
	
		
	
</html>
