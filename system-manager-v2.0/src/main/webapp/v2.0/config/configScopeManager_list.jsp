<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit" href="configScopeManager!toAdd.action?prePath=<s:property value="prePath" />" target="dialog" mask="true" title="新增域节点"><span>新增</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="150">域节点名称</th>
					<th>描述</th>
                    <th width="120">创建时间</th>
                    <th width="120">修改时间</th>
                    <th width="120">操作</th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="configList">
				<tr>
					<td><s:property value="name" /></td>
					<td><s:property value="data" /></td>
					<td><s:date name="createdAt" format="yyyy-MM-dd HH:mm:ss" /></td>
                    <td><s:date name="updatedAt" format="yyyy-MM-dd HH:mm:ss" /></td>
                    <td>
						<a href="configScopeManager!toEdit.action?config.name=<s:property value="name" />" target="dialog" mask="true" title="修改域"><span>修改</span></a>
					</td>
				</tr>
				</s:iterator>
			</tbody>
	</table>
</div>		