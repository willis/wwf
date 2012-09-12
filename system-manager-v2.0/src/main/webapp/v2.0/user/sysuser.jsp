<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form id="pagerForm" method="post" action="sysUser!userList.action">
	<input type="hidden" name="pageNum" value="${pageInfo.pageNo}" />
	<!--【必须】value=1可以写死-->
	<input type="hidden" name="numPerPage" value="${pageInfo.pageSize}" />
	<!--【可选】每页显示多少条-->
</form>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${cxp}v2.0/user/sysuser_edit.jsp"
				target="dialog" class="btnAdd"><span>添加</span> </a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
				href="sysUser!removeByIds.action" class="delete" postType="String"><span>删除</span>
			</a>
			</li>
			<li><a title="确实要还原这些记录吗?" target="selectedTodo" rel="ids"
				href="sysUser!reductionByIds.action" class="delete"><span>还原</span>
			</a>
			</li>
		</ul>
	</div>
	<table class="table" width="1200" layoutH="138">
		<thead>
			<tr>
				<th width="22"><input name="ids" type="checkbox"
					class="checkboxCtrl"></th>
				<th>用户名</th>
				<th>姓名</th>
				<th>性别</th>
				<th>状态</th>
				<th width="105">操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="sysUsers" status="stuts">
				<tr target="ownUser_id" rel="${id}">
					<td><input name="ids" value="${id}" type="checkbox"
						class="checkboxCtrl"></td>
					<td><s:property value="username" />
					</td>
					<td><s:property value="truename" />
					</td>
					<td><s:property value="sex" />
					</td>
					<td><s:if test="status == 0">
							<font color=green>正常</font>
						</s:if> <s:if test="status == 1">
							<font color=red>删除</font>
						</s:if> <s:if test="status == 2">
							<font color=red>冻结</font>
						</s:if>
					</td>
					<td><a title="编辑" target="dialog"
						href="sysUser!getSysUserInfo.action?id=${id}" class="btnEdit"></a>
						<a title="角色配置" target="dialog"
						href="sysUser!getCheckRoles.action?userId=${id}" class="btnEdit"></a>
						<a title="修改密码" target="dialog"
						href="sysUser!toChangePwd.action?userId=${id}" class="btnEdit"></a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select class="combox" name="numPerPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
			</select> <span>条，共${pageInfo.totalCount}条</span>
		</div>

		<div class="pagination" targetType="navTab"
			totalCount="${pageInfo.totalCount}" numPerPage="${pageInfo.pageSize}"
			pageNumShown="10" currentPage="${pageInfo.pageNo}"></div>
	</div>
</div>
