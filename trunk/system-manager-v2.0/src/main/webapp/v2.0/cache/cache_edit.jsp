<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="pageContent">
	<form method="post" action="cacheManagerAction!edit.action" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>主机：</label>
				<input name="item.host" readonly="readonly" type="text" size="30" value="<s:property value="item.host" />"/>
			</p>
			<p>
				<label>端口：</label>
				<input name="item.port" readonly="readonly" type="text" size="6" value="<s:property value="item.port" />"/>
			</p>
			<p>
				<label>用户名：</label>
				<input name="item.username" class="required" type="text" size="30" value="<s:property value="item.username" />"/>
			</p>
			<p>
				<label>超时：</label>
				<input name="item.timeout" class="required digits" type="text" size="5" value="<s:property value="item.timeout" />"/>
			</p>
			<p>
				<label>密码：</label>
				<input name="item.password" class="required" type="text" size="30" value="<s:property value="item.password" />"/>
			</p>
			<p>
				<label>最大连接数：</label>
				<input name="item.maxActive" class="required digits" type="text" size="4" value="<s:property value="item.maxActive" />"/>
				<span class="unit">个</span>
			</p>
			<p>
				<label>最小空闲数：</label>
				<input name="item.minIdle" class="required digits" type="text" size="4" value="<s:property value="item.minIdle" />"/>
				<span class="unit">个</span>
			</p>
			<p>
				<label>最大空闲数：</label>
				<input name="item.maxIdle" class="required digits" type="text" size="4" value="<s:property value="item.maxIdle" />"/>
				<span class="unit">个</span>
			</p>

			<p>
				<label>最大等待时间：</label>
				<input name="item.maxWait" class="required digits" type="text" size="4" value="<s:property value="item.maxWait" />"/>
				<span class="unit">ms</span>
			</p>
			<p>
				<label>连接用尽时动作：</label>
				<input name="item.whenExhaustedAction" class="required digits" type="text" size="4" value="<s:property value="item.whenExhaustedAction" />"/>
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