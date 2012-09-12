<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<div class="pageContent">
    <form method="post" action="sysRole!saveOrUpdate.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>角色名称：</label>
                <input name="sysRole.name" value="${sysRole.name}" class="required" type="text" size="30" alt="请输入角色名称"/>
            </p>
            <p>
                <label>角色描述：</label>
                <input name="sysRole.describe" value="${sysRole.describe}" class="required" type="text" size="30"  alt="请输入角色描述"/>
            </p>
            <input name="sysRole.id" type="hidden" value="${sysRole.id}"  />
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>
