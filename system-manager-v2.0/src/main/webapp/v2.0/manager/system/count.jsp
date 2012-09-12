<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<form id="pagerForm" action="systemLog!logList.action" method="post">
    <input type="hidden" name="pageNum" value="${pageInfo.pageNo}" /><!--【必须】value=1可以写死-->
    <input type="hidden" name="numPerPage" value="${pageInfo.pageSize}" /><!--【可选】每页显示多少条-->


</form>
<div class="pageContent">
<div  id="w_list_print">
<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc">
<thead>
<tr>
    <th width="80"  >id</th>
    <th width="80"  >姓名</th>
    <th width="100"  >日志类型</th>
    <th width="100 >操作内容</th>
    <th align="right" width="100">IP</th>
    <th width="100">创建时间</th>

</tr>
</thead>
<tbody>

<s:iterator value="logList" status="stuts">

    <tr>

    <td>
        <s:property value="id" />
    </td>
    <td>
        <s:property value="username" />
    </td>
    <td>
        <s:property value="logtitle" />.<s:property value="logcontent" />()
    </td>

    <td>
        <s:property value="ip" />
    </td>
    <td>
        <s:date name="createat" format="yyyy-MM-dd HH:mm:ss" />

    </td>

</s:iterator>
</tbody>
</table>
</div>

<div class="panelBar" >
    <div class="pages">
        <span>显示</span>
        <select name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
            <option value="20">20</option>

        </select>
        <span>条，共${pageInfo.totalCount}条</span>
    </div>

    <div class="pagination" targetType="navTab" totalCount="${pageInfo.totalCount}" numPerPage="20" pageNumShown="10" currentPage="${pageInfo.pageNo}"></div>

</div>

</div>
