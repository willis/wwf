<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<form id="pagerForm" method="post" action="cacheManagerAction!list.action">
	<input type="hidden" name="pageNum" value="1" />
</form>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="cacheManagerAction!addView.action" target="dialog" mask="true" width="800" height="300"><span>添加</span></a></li>
			<li><a class="edit" href="cacheManagerAction!editView.action?address={host_address}" target="navTab"><span>修改</span></a></li>
			<li><a class="delete" href="cacheManagerAction!remove.action?address={host_address}" target="ajaxTodo" title="确定要删除吗？"><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="120">主机</th>
					<th width="80">端口</th>
					<th width="100">用户名</th>
					<th width="150">密码</th>
					<th width="80">超时</th>
					<th width="80">最小空闲数</th>
					<th width="80">最大空闲数</th>
					<th width="80">最大连接数</th>
					<th width="80">最大等待时间</th>
					<th>连接用尽时动作</th>
				</tr>
			</thead>
			<tbody>
			<s:if test="cacheConfig.caches.size()==0">
				<tr>
					<td colspan="9">无记录</td>
				</tr>
			</s:if><s:else>
			<s:iterator value="cacheConfig.caches" status="stuts">
				<tr target="host_address" rel="<s:property value="host" />:<s:property value="port" />">
					<td><s:property value="host" /></td>
					<td><s:property value="port" /></td>
					<td><s:property value="username" /></td>
					<td><s:property value="password" /></td>
					<td><s:property value="timeout" /></td>
					<td><s:property value="minIdle" /></td>
					<td><s:property value="maxIdle" /></td>
					<td><s:property value="maxActive" /></td>
					<td><s:property value="maxWait" /></td>
					<td><s:property value="whenExhaustedAction" /></td>
				</tr>
			</s:iterator>
			</s:else>
			</tbody>
	</table>
</div>		