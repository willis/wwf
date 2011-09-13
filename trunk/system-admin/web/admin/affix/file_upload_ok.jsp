<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script>
<s:if test="${empty affix}">
	alert("上传错误");
</s:if>
<s:if test="${not empty affix}">
window.parent.location.reload();
</s:if>

</script>