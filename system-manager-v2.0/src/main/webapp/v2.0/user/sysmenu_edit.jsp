<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="pageContent">
    <form action="sysMenu!save.action" class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);" method="post">
        <div class="pageFormContent" layoutH="56">
        <p>
            <label>
                名称：
            </label>
            <input type="text" name="sysMenu.name"
                   value="<s:property value="sysMenu.name"/>"
                   size="30" class="required"/>
        </p>

        <p>
            <label>
                描述：
            </label>
            <input type="text" name="sysMenu.description"

                   value="<s:property value="sysMenu.description"/>"
                   size="30"  />
        </p>

        <p>
            <label>
                别名：
            </label>
            <input type="text" name="sysMenu.alias"
                   value="<s:property value="sysMenu.alias"/>"
                   size="30" class="required" />
        </p>

        <p>
            <label>
                IMG图片：
            </label>
            <input type="text" name="sysMenu.img"
                   value="<s:property value="sysMenu.img"/>"
                   size="30"/>
        </p>

        <p>
            <label>
                链接地址：
            </label>
            <input type="text" name="sysMenu.link"
            value="<s:property value="sysMenu.link"/>"
            size="30" />
        </p>

        <p>
            <label>
                索引：
            </label>
            <input type="text" name="sysMenu.orderBy"
                   value="<s:property value="sysMenu.orderBy"/>"
                   size="30" />
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
        <input type="hidden" name="sysMenu.parentObj.id"
               value="<s:property value="rootObj.id"/>"/>
        <input type="hidden" name="sysMenu.id"
               value="<s:property value="sysMenu.id"/>"/>

    </form>
</div>