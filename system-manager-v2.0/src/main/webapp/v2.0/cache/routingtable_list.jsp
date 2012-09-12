<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
function testConfirmMsg(url, data){
	alertMsg.confirm("请确定是否重新初始化路由信息?", {
		okCall: function(){
			$.post(url, data, DWZ.ajaxDone, "json");
		}
	});
}
</script>
<form id="pagerForm" method="post" action="routingTableAction!list.action">
	<input type="hidden" name="pageNum" value="<s:property value="pageInfo.pageNo"/>" />
	<input type="hidden" name="pageInfo.pageNo" value="<s:property value="pageInfo.pageNo"/>" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="routingTableAction!search.action" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					搜索类型
				</td>
				<td>
					<s:select label="搜索类型" list="#{'1':'分区号', '2':'主机','3':'转移主机'}" name="searchType" cssClass="combox" />
				</td>
				<td>
					值：<input type="text" name="searchValue" value="<s:property value="searchValue"/>"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit" href="routingTableAction!editView.action?regionNum={regionNum}" target="navTab"><span>修改</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="135">
			<thead>
				<tr>
					<th width="80">分区号</th>
					<th width="120">主机</th>
					<th width="80">端口</th>
					<th>状态</th>
					<th width="120">转移主机</th>
					<th width="80">转移端口</th>
					<th width="120">存储主机</th>
					<th width="80">存储端口</th>
					<th width="80">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:if test="routings.length==0">
					<tr>
						<td colspan="9">无记录</td>
					</tr>
				</s:if><s:else>
				<s:iterator value="routings" status="stuts">
					<tr target="regionNum" rel="<s:property value="regionNum" />">
						<td><s:property value="regionNum" /></td>
						<td><s:property value="host" /></td>
						<td><s:property value="port" /></td>
						<td><s:property value="status" /></td>
						<td><s:property value="moveHost" /></td>
						<td><s:property value="movePort" /></td>
						<td><s:property value="storeHost" /></td>
						<td><s:property value="storePort" /></td>
						<td><a href="routingTableAction!move.action?regionNum=<s:property value="regionNum" />" target="ajaxTodo" title="转移会导致线上缓存服务器压力增大，确定要转移吗？"><span>转移</span></a></td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
	</table>
	<s:if test="searchValue==null||searchValue==''">
	<div class="panelBar">

		<div class="pagination" targetType="navTab" totalCount="<s:property value="pageInfo.totalCount"/>" numPerPage="128" pageNumShown="10" currentPage="<s:property value="pageInfo.pageNo"/>">

	</div>
	</s:if>
</div>		