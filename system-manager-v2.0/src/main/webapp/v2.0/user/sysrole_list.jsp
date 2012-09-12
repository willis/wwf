<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<form id="pagerForm" method="post" action="#rel#">
    <input type="hidden" name="pageNum" value="${pageInfo.pageNo}" /><!--【必须】value=1可以写死-->
    <input type="hidden" name="numPerPage" value="${pageInfo.pageSize}" /><!--【可选】每页显示多少条-->
</form>

<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="sysRole!list.action" method="post">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>角色名称：</label>
                    <input type="text" name="sysRole.name" value='${sysRole.name}' />
                </li>
                <li>
                    <label>角色描述：</label>
                    <input type="text" name="sysRole.describe" value='${sysRole.describe}'  />
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
            <li><a class="add" href="${cxp}v2.0/user/sysrole_edit.jsp" target="dialog" class="btnAdd"><span>添加</span></a></li>
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" href="sysRole!del.action" class="delete"><span>删除</span></a></li>
        </ul>
    </div>
    <table class="table" width="1200" layoutH="138">
        <thead>
        <tr>
            <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
            <th>角色名称</th>
            <th>角色描述</th>
            <th width="250">操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="roleList" status="stuts">
            <tr target="sid_user" rel="${id}">
                <td><input name="ids" value="${id}" type="checkbox"></td>
                <td>${name}</td>
                <td>${describe}</td>
                <td>
                    <a title="编辑" target="dialog" mask="true" href="sysRole!edit.action?id=${id}" class="" >编辑</a> |
                    <a title="删除" target="ajaxTodo" href="sysRole!del.action?ids=${id}" >删除</a> |
                    <a title="配置权限" target="dialog" href="sysRole!sysrolePopedom.action?id=${id}" >配置权限</a> |
                    <a title="配置系统菜单" target="dialog" href="sysRole!listSysMenu.action?id=${id}" >配置系统菜单 </a>
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
                <option value="50">50</option>
            </select>
            <span>条，共${pageInfo.totalCount}条</span>
        </div>

        <div class="pagination" targetType="navTab" totalCount="${pageInfo.totalCount}" numPerPage="${pageInfo.pageSize}" pageNumShown="10" currentPage="${pageInfo.pageNo}"></div>

    </div>
</div>
