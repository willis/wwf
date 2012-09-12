<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="pageContent">
    <form class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);"
          action="dictionaryAction!save.action" method="post">

        <div class="pageFormContent" layoutH="56">
            <p>
                <label>名称：</label>

                <input type="text" name="dictionary.name"

                       size="30" value="<s:property value="dictionary.name"/>" class="required"/>
            </p>

            <p>
                <label>
                    描述： </label>

                <input type="text" name="dictionary.describe"

                       size="30" value="<s:property value="dictionary.describe"/>" />

            </p>

            <p>
                <label>
                    索引： </label>

                <input type="text" name="dictionary.orderby"

                       size="30" value="<s:property value="dictionary.orderby"/>"/>
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
        <input type="hidden" name="dictionary.parentObj.id" value="${rootObj.id }"/>

        <input type="hidden" value="<s:property value="dictionary.id"/>" name="dictionary.id"/>
    </form>
</div>