<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<form id="pagerForm" method="post" action="#rel#">
    <input type="hidden" name="pageNum" value="${pageInfo.pageNo}" /><!--【必须】value=1可以写死-->
    <input type="hidden" name="numPerPage" value="${pageInfo.pageSize}" /><!--【可选】每页显示多少条-->
</form>

<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="sysPopedom!list.action" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>代码：</label>
                    <input type="text" name="sysPopedom.code" value='${sysPopedom.code}' />
                </li>
                <li>
                    <label>描述：</label>
                    <input type="text" name="sysPopedom.describe" value='${sysPopedom.describe}'  />
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
        <li><a class="add" href="${cxp}v2.0/user/syspopedom_edit.jsp" target="dialog" class="btnAdd"><span>添加</span></a></li>
        <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" href="sysPopedom!del.action" class="delete"><span>删除</span></a></li>
    </ul>
</div>
<table class="table" width="1200" layoutH="138">
<thead>
<tr>
    <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
    <th>权限代码</th>
    <th>权限描述</th>
    <th width="70">操作</th>
</tr>
</thead>
<tbody>
<s:iterator value="authList" status="stuts">
<tr target="sid_user" rel="<s:property value="id" />">
    <td><input name="ids" value="<s:property value="id" />" type="checkbox"></td>
    <td><s:property value="code" /></td>
    <td><s:property value="describe" /></td>
    <td>
        <a title="删除" target="ajaxTodo" href="sysPopedom!del.action?ids=${id}" class="btnDel">删除</a>
        <a title="编辑" target="dialog" href="sysPopedom!edit.action?id=${id}" class="btnEdit">编辑</a>
    </td>
</tr>
</s:iterator>
</tbody>
</table>
<div class="panelBar">
    <div class="pages">
        <span>显示</span>
        <select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
            <option value="20">20</option>
        </select>
        <span>条，共${pageInfo.totalCount}条</span>
    </div>

    <div class="pagination" targetType="navTab" totalCount="${pageInfo.totalCount}" numPerPage="${pageInfo.pageSize}" pageNumShown="10" currentPage="${pageInfo.pageNo}"></div>

</div>
</div>
