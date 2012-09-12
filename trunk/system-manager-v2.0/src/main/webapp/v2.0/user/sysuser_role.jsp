<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<div>
	<table class="table" class="table" width="100%" layoutH="100%">
		<thead>
			<tr>
				<th>角色名称</th>
				<th>角色描述</th>
				<th>当前状态(可点击改变)</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="checkedRolesList">
				<tr>
					<td>${name}</td>
					<td>${describe}</td>
					<td><a href='javascript:void(0)' class="yes changeRolePopedom"
						title="${id}">已選中（點擊删除）</a></td>
			</s:iterator>
			<s:iterator value="notCheckRolesList">
				<tr>
					<td>${name}</td>
					<td>${describe}</td>
					<td><a href='javascript:void(0)' class="no changeRolePopedom"
						title="${id}">未選中（點擊添加）</a></td>
			</s:iterator>
		</tbody>
	</table>
</div>
</body>
<script type="text/javascript">
    var changeRolePopedomFlag = false;
    $('a.changeRolePopedom').live('click',(function(){
        var uid = ${uid};
        changeRolePopedomFlag = true;
        var currentA = $(this);
        var pid = currentA.attr('title');
        if(currentA.hasClass('yes')){
            $.post("sysUser!delSysRoles.action",{id:uid,cs:pid},function(data){
                data = $.parseJSON(data)
                if(data.statusCode=='200'){
                    currentA.removeClass('yes').addClass('no').text('未選中（點擊添加）');
                    alertMsg.correct('删除成功！');
                } else {
                    alertMsg.error('操作出错！');
                }
                changeRolePopedomFlag = false;
            });

        } else {
            $.post("sysUser!addSysRoles.action",{id:uid,cs:pid},function(data){
                data = $.parseJSON(data)
                if(data.statusCode=='200'){
                    currentA.removeClass('no').addClass('yes').text('已選中（點擊删除）');
                    alertMsg.correct('添加成功！');
                } else {
                    alertMsg.error('操作出错！');
                }
                changeRolePopedomFlag = false;
            });
        }
    }));

</script>
</html>