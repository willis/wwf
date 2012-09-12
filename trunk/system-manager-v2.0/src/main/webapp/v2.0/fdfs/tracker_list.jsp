<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<form id="pagerForm" method="post" action="#rel#">
    <input type="hidden" name="pageNum" value="${pageInfo.pageNo}" /><!--【必须】value=1可以写死-->
    <input type="hidden" name="numPerPage" value="${pageInfo.pageSize}" /><!--【可选】每页显示多少条-->
</form>

<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="sysPopedom!list.action" method="post">

    </form>
</div>
<div class="pageContent">
    <table class="table" width="1200" layoutH="0">
        <thead>
        <tr>
            <th>IP</th>
            <th width="250">操作</th>
        </tr>
        </thead>
        <tbody>
        <s:iterator value="trackers">
                <tr>
                    <td>${ip}</td>
                    <td>
                        <a href="tracker!download.action?ip=${ip}" >下载配置文件</a>|
                        <a href="${cxp}v2.0/fdfs/tracker_upload.jsp?ip=${ip}" target="dialog" >上传配置文件</a>
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
