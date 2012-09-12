<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="pageContent">
    <form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);"
          action="configScopeManager!edit.action" method="post">

        <div class="pageFormContent" layoutH="56">
            <p>
                <label>节点名称：</label>

                <input type="text" name="config.name"

                       size="30" value="<s:property value="config.name"/>" class="required"  readonly="readonly"/>
            </p>

            <p>
                <label>
                    描述： </label>

                <input type="text" name="config.data"

                       size="30" value="<s:property value="config.data"/>" class="required"/>

            </p>
        </div>
        <div class="formBar">
            <ul>

                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">保存</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>