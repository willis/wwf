<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit" href="demo_page4.html?uid={sid_user}" target="navTab"><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="120">消息ID</th>
                    <th width="80">创建时间</th>
                    <th width="80">修改时间</th>
					<th>描述</th>
                    <th width="80">操作</th>
				</tr>
			</thead>
			<tbody>
				<tr target="id_springConfig" rel="0">
					<td>:192.168.40.50-42359-1346738868366-5:1:1:1:1</td>
					<td>2012-09-03 15:30:23</td>
                    <td>2012-09-03 16:10:12</td>
					<td>Vivame V2.0 Spring统一配置</td>
					<td>转移  删除</td>
				</tr>
				<tr target="id_springConfig" rel="1">
                    <td>:192.168.40.50-42359-1346738812366-5:1:5:1:9</td>
                    <td>2012-09-03 16:03:57</td>
                    <td>2012-09-03 16:10:56</td>
                    <td>Vivame V2.0 Spring统一配置</td>
                    <td>转移  删除</td>
				</tr>
			</tbody>
	</table>
</div>		