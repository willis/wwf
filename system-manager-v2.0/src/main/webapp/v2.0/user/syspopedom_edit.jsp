<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<div class="pageContent">
    <form method="post" action="sysPopedom!saveOrUpdate.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>权限代码：</label>
                <input name="sysPopedom.code" value="${sysPopedom.code}" class="required" type="text" size="30" alt="请输入权限代码"/>
            </p>
            <p>
                <label>权限描述：</label>
                <input name="sysPopedom.describe" value="${sysPopedom.code}" class="required" type="text" size="30"  alt="请输入权限描述"/>
            </p>
            <input name="sysPopedom.id" type="hidden" value="${sysPopedom.id}"  />
        </div>
        <div class="formBar">
            <ul>
                <!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>

