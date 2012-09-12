<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="pageContent">
	<form method="post" action="routingTableAction!edit.action" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>分区号：</label>
				<input name="routing.regionNum" readonly="readonly" type="text" size="4" value="<s:property value="routing.regionNum" />"/>
			</p>
			<p>
				<label>状态：</label>
				<input name="routing.status" readonly="readonly" type="text" size="4" value="<s:property value="routing.status" />"/>
			</p>
			<p>
				<label>主机：</label>
				<input name="routing.host" readonly="readonly" type="text" size="30" value="<s:property value="routing.host" />"/>
			</p>
			<p>
				<label>端口：</label>
				<input name="routing.port" readonly="readonly" type="text" size="4" value="<s:property value="routing.port" />"/>
			</p>

			<p>
				<label>转移主机：</label>
				<input name="routing.moveHost" type="text" size="30" value="<s:property value="routing.moveHost" />"/>
			</p>
			<p>
				<label>转移端口：</label>
				<input name="routing.movePort" class="digits" type="text" size="4" value="<s:property value="routing.movePort" />"/>
			</p>
			<p>
				<label>存储主机：</label>
				<input name="routing.storeHost" class="required" type="text" size="30" value="<s:property value="routing.storeHost" />"/>
			</p>
			<p>
				<label>存储端口：</label>
				<input name="routing.storePort" class="required digits" type="text" size="4" value="<s:property value="routing.storePort" />"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>