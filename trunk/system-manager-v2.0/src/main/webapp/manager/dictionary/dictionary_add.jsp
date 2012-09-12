<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    request.setAttribute("cxp",request.getContextPath());
%>
	 <form id="myForm" action="dictionaryAction!save.action" method="post">
				
		
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
					<input type="text" name="dictionary.name"
					
						style="width: 80%" value="<s:property value="dictionary.name"/>" checkInfo="名称;NOTNULL;No" />
				</td>
			</tr>

			<tr>
				<td bgcolor="#EEF2F2" class="leftAndTop">
					描述：
				</td>
				<td>
					<input type="text" name="dictionary.describe"
						
						style="width: 80%" value="<s:property value="dictionary.describe"/>" checkInfo="描述;NOTNULL;No" />
				</td>
			</tr>

			<tr>
				<td bgcolor="#EEF2F2" class="leftAndTop">
					索引：
				</td>
				<td>
					<input type="text" name="dictionary.orderby"
						
						style="width: 80%" value="<s:property value="dictionary.orderby"/>" checkInfo="索引;ISNULL;No" />
				</td>
			</tr>

		

		</table>
		<input type="hidden" name="dictionary.parentObj.id" value="${rootObj.id }" />

			<input type="hidden" value="<s:property value="dictionary.id"/>" name="dictionary.id"/ >
		</form>
