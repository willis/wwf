<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
	<head>
		<title>网站后台</title>
		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>
		<script src="${cxp }/appjs/ajaxform.common.js"></script>

	</head>
	<body>
		
	<form action="sysMenu!save.action" id="myForm" method="post">

		
		<div class="buttons">
			<input type="submit" name="subOk" value="提交" class="button" />
			<input type="button" name="backOk" value="关闭" class="button"
				onclick="window.parent.closeWindow()" />
		

		</div>
		<p id="errorMsg" style="color: red">
			&nbsp;
		</p>
		<table class="table">


			<tr>
				<td width="161" bgcolor="#EEF2F2" class="leftAndTop">
					名称：
				</td>
				<td>
					<input type="text" name="sysMenu.name"
						value="<s:property value="sysMenu.name"/>" 
						style="width: 80%" dataType="Require" msg="名称不能为空" />
				</td>
			</tr>

			<tr>
				<td bgcolor="#EEF2F2" class="leftAndTop">
					描述：
				</td>
				<td>
					<input type="text" name="sysMenu.description"
					
						value="<s:property value="sysMenu.description"/>" 
						style="width: 80%" dataType="Require" msg="描述不能为空"/>
				</td>
			</tr>
						<tr>
				<td bgcolor="#EEF2F2" class="leftAndTop">
					别名：
				</td>
				<td>
					<input type="text" name="sysMenu.alias"
						value="<s:property value="sysMenu.alias"/>"
						style="width: 80%" />
				</td>
			</tr>
			<tr>
				<td bgcolor="#EEF2F2" class="leftAndTop">
					IMG图片：
				</td>
				<td>
					<input type="text" name="sysMenu.img"
						value="<s:property value="sysMenu.img"/>"
						style="width: 80%" />
				</td>
			</tr>
			
						<tr>
				<td bgcolor="#EEF2F2" class="leftAndTop">
					链接地址：
				</td>
				<td>
					<TEXTAREA name="sysMenu.link"  style="width: 80%" ><s:property value="sysMenu.link"/></TEXTAREA>
				</td>
			</tr>
			
			<tr>
				<td bgcolor="#EEF2F2" class="leftAndTop">
					索引：
				</td>
				<td>
					<input type="text" name="sysMenu.orderBy"
						value="<s:property value="sysMenu.orderBy"/>" 
						style="width: 80%" />
				</td>
			</tr>

			


		</table>
		<input type="hidden" name="sysMenu.parentObj.id"
				value="<s:property value="rootObj.id"/>"/>
				<input type="hidden" name="sysMenu.id"
				value="<s:property value="sysMenu.id"/>"/>
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

