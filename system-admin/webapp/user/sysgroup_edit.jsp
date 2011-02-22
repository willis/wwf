<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>网站后台</title>
		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>
		<script src="${cxp }/appjs/ajaxform.common.js"></script>
	</head>
	<body>

			<form  id="myForm" name="myForm" action="sysGroup!save.action" method="post">
				<div class="buttons">
					<input type="submit" name="subOk" value="保存" class="button" />
					<input type="button" name="backOk" value="关闭" class="button"
						onclick="window.parent.parent.closeWindow()" />
				
				</div>
				<p id="errorMsg" style="color: red">
			&nbsp;
		</p>
				<table class="table" >

					<tr>
						<td width="161" bgcolor="#EEF2F2" class="leftAndTop">
							组名称：
						</td>
						<td>
						
							<input type="text" name="sysGroup.name"
								checkInfo="组名称;NOTNULL;No"  value="<s:property value="sysGroup.name"/>" style="width:90%"/>
						</td>
					</tr>
					<tr>
						<td bgcolor="#EEF2F2" class="leftAndTop">
							组描述：
						</td>
						<td>
							<input type="text" name="sysGroup.describe" 
								checkInfo="组描述;NOTNULL;No"  value="<s:property value="sysGroup.describe"/>" style="width:90%" />
						</td>
					</tr>
					<tr>
						<td bgcolor="#EEF2F2" class="leftAndTop">
							索引：
						</td>
						<td>
							<input type="text" name="sysGroup.orderby"
								checkInfo="索引;ISNULL;No"  value="<s:property value="sysGroup.orderby"/>" style="width:90%" />
						</td>
					</tr>
						
			

				</table>
				<input type="hidden" value="<s:property value="sysGroup.parentGroup.id"/>" name="sysGroup.parentGroup.id" />
				<input type="hidden" value="<s:property value="sysGroup.id"/>" name="sysGroup.id"/ >
			</form>
		
		<script>


			FormCheck.createCheckForm(document.forms[0]);
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

