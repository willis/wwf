<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<form action="sysRole!addSysMenus.action"  method="post" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">

<div layoutH="50">
    <ul id='popedom-tree' class="tree treeFolder treeCheck expand" >
        <s:iterator value="tree">
            <li><a tname="c" tvalue="${id}" <s:if test='checked=="checked"' >checked</s:if>>${name}</a>
                <s:if test='childs.size!=0'>
                    <ul>
                        <s:iterator value="childs">
                            <li><a tname="c" tvalue="${id}"  <s:if test='checked=="checked"' >checked</s:if> >${name}</a>
                                <s:if test='childs.size!=0'>
                                    <ul>
                                        <s:iterator value="childs">
                                            <li><a tname="c" tvalue="${id}"  <s:if test='checked=="checked"' >checked</s:if> >${name}</a></li>
                                        </s:iterator>
                                    </ul>
                                </s:if>
                            </li>
                        </s:iterator>
                    </ul>
                </s:if>
            </li>
        </s:iterator>
    </ul>
</div>
<div class="pageHeader">
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit" >保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
                </li>
            </ul>
        </div>
</div>
<input type="hidden" name="roleId" value="${param.id}">
</form>

