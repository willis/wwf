<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
function confirmMsg(url, data){
	alertMsg.confirm("删除次节点可能会导致依赖的相关服务异常，请选择确认或取消！", {
		okCall: function(){
			$.post(url, data, DWZ.ajaxDone, "json");
		}
	});
}
</script>
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="configManager!list.action" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>域节点：</label>
                    <s:select  list="scopeList" listKey="name" listValue="data" name="searchConfigScope" headerKey="0" headerValue="请选择..." cssClass="combox" />
                </li>
            </ul>
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
			<li><a class="add" href="configManager!toAdd.action" target="dialog" mask="true" title="新增配置数据"><span>新增</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="180">配置节点名称</th>
                    <th width="120">创建时间</th>
                    <th width="120">修改时间</th>
					<th>描述</th>
                    <th width="80">操作</th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="configList">
				<tr target="id_springConfig">
					<td><s:property value="name" /></td>
					<td><s:date name="createdAt" format="yyyy-MM-dd HH:mm:ss" /></td>
                    <td><s:date name="updatedAt" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td><s:property value="extAttribute.desc" /></td>
					<td>
						<a href="configManager!toEdit.action?config.name=<s:property value="name" />&prePath=<s:property value="prePath" />" target="dialog" mask="true" title="修改配置数据"><span>修改</span></a>
						<a callback="navTabReload" target="ajaxTodo" href="configManager!delete.action?config.name=<s:property value="name" />&prePath=<s:property value="prePath" />" title="删除次节点可能会导致依赖的相关服务异常,确定要删除吗?"><span>删除</span></a>
					</td>
				</tr>
				</s:iterator>
			</tbody>
	</table>
</div>		