<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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

<link href="${cxp}/css/layout.css" rel="stylesheet" type="text/css" />
<head>
<title></title>

</head>
<body  id="mybody">

		<table class="tableContent">
			<tbody>
				<tr id="topRow">
					<td id="topLeft">
					</td>
					<td id="topMiddle">
					</td>
					<td id="topRight">
					</td>
				</tr>
				<tr id="middleRow">
					<td id="middleLeft">
					</td>
					<td id="tdContent" >

<% 
    int NUM_BLOCKS = 10;
    java.text.DecimalFormat mbFormat = new java.text.DecimalFormat("#0.00");
    java.text.DecimalFormat percentFormat = new java.text.DecimalFormat("#0.0");
   // The java runtime
    Runtime runtime = Runtime.getRuntime();

    double freeMemory = (double)runtime.freeMemory()/(1024*1024);
    double totalMemory = (double)runtime.totalMemory()/(1024*1024);
    double usedMemory = totalMemory - freeMemory;
    double percentFree = ((double)freeMemory/(double)totalMemory)*100.0;
    int free = 100-(int)Math.round(percentFree);
%>
    <table border=0>
    <tr><td><font size="-1">memory useing:</font></td>
        <td><font size="-1"><%= mbFormat.format(usedMemory) %> MB</font></td>
    </tr>
    <tr><td><font size="-1">memory total:</font></td>
        <td><font size="-1"><%= mbFormat.format(totalMemory) %> MB</font></td>
    </tr>
    </table>
    <br>
    <table border=0><tr><td>
    <table bgcolor="#000000" cellpadding="1" cellspacing="0" border="0" width="200" align=left>
    <tr>
    <td>
    <table bgcolor="#000000" cellpadding="1" cellspacing="1" border="0" width="100%">
<%    for (int i=0; i<NUM_BLOCKS; i++) {
        if ((i*(100/NUM_BLOCKS)) < free) {
    %>
        <td bgcolor="#00ff00" width="<%= (100/NUM_BLOCKS) %>%"><img src="${cxp }/images/blank.gif" width="1" height="15" border="0"></td>
<%        } else { %>
        <td bgcolor="#006600" width="<%= (100/NUM_BLOCKS) %>%"><img src="${cxp }/images/blank.gif" width="1" height="15" border="0"></td>
<%        }
    }
%>
    </table>
    </td>
    </tr>
    </table></td><td>
        <font size="-1">
        &nbsp;<b><%= percentFormat.format(percentFree) %>% free</b>
        </font>
    </td></tr></table>

					</td>
					<td id="middleRight">
					</td>
				</tr>
				<tr id="bottomRow">
					<td id="bottomLeft">
					</td>
					<td id="bottomMiddle">
					</td>
					<td id="bottomRight">
					</td>
				</tr>
			</tbody>
		</table>
<script language="JavaScript">
function myrefresh(){
window.location.reload();
}
//setTimeout('myrefresh()',5000); 
</script>
</body>
</html>
