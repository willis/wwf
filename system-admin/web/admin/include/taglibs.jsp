<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="pager" uri="/WEB-INF/tld/PagerTag.tld" %>
<% String cxp =  request.getContextPath();
		request.setAttribute("cxp",cxp);
%>
<script>
var vPath = "${cxp}/";
var contextPath = "${cxp}/";
</script>
<link href="${cxp}/css/widget.css" rel="stylesheet" type="text/css" />
<link href="${cxp}/css/layout.css" rel="stylesheet" type="text/css" />
<link href="${cxp}/css/fonts.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${cxp}/js/Validator-min.js"></script>
