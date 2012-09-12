<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
.yes {
    height: 32px;
    padding-left: 15px;
    color: green;
}
.no {
    height: 32px;
    padding-left: 15px;
    color: red;
}
</style>
<form id="pagerForm" method="post" action="sysRole!sysrolePopedom.action">
    <input type="hidden" name="pageNum" value="${pageInfo.pageNo}" /><!--【必须】value=1可以写死-->
    <input type="hidden" name="numPerPage" value="${pageInfo.pageSize}" /><!--【可选】每页显示多少条-->
    <input type="hidden" name="id" value="${param.id}" />
</form>
<div class="pageContent">
    <table class="table" width="100%" layoutH="100">
        <thead>
        <tr>
            <th>权限代码</th>
            <th>权限描述</th>
            <th>当前状态(可点击改变)</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="all" status="stuts">
            <tr rel="${id}">
                <td>${code}</td>
                <td>${describe}</td>
                <td>
                    <s:if test="checked==1" >
                    <a href='javascript:void(0)' class="yes changeRolePopedom" title="${id}">[√]</a>
                    </s:if>
                    <s:else>
                    <a href='javascript:void(0)' class="no changeRolePopedom" title="${id}">[X]</a>
                    </s:else>
                </td>
            </tr>
        </s:iterator>
        </tbody>
    </table>
    <div class="panelBar">
        <div class="pages">
            <span>显示20</span>
            <span>条，共${pageInfo.totalCount}条</span>
        </div>

        <div class="pagination" targetType="dialog" totalCount="${pageInfo.totalCount}" numPerPage="${pageInfo.pageSize}" pageNumShown="10" currentPage="${pageInfo.pageNo}"></div>

    </div>
</div>

<div class="pageHeader">
        <div class="searchBar">

            <div class="formBar">
                <ul>
                    <li>
                        <div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
                    </li>
                </ul>
            </div>
        </div>
</div>
<script type="text/javascript">
    var changeRolePopedomFlag = false;
    $('a.changeRolePopedom').live('click',(function(){
        var roleId = '${param.id}';
        if(changeRolePopedomFlag)return;
        changeRolePopedomFlag = true;
        var currentA = $(this);
        var pid = currentA.attr('title');
        if(currentA.hasClass('yes')){
            $.post("sysRole!delRolePopedoms.action",{id:roleId,cs:pid},function(data){
                data = $.parseJSON(data)
                if(data.statusCode=='200'){
                    currentA.removeClass('yes').addClass('no').text('[X]');
                    alertMsg.correct('删除成功！');
                } else {
                    alertMsg.error('操作出错！');
                }
                changeRolePopedomFlag = false;
            });

        } else {
            $.post("sysRole!addRolePopedoms.action",{id:roleId,cs:pid},function(data){
                data = $.parseJSON(data)
                if(data.statusCode=='200'){
                    currentA.removeClass('no').addClass('yes').text('[√]');
                    alertMsg.correct('添加成功！');
                } else {
                    alertMsg.error('操作出错！');
                }
                changeRolePopedomFlag = false;
            });
        }
    }));

</script>
