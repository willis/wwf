<ul>
	<% 
    int NUM_BLOCKS = 50;
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
		<tr>
			<td><font size="-1">memory useing:</font>
			</td>
			<td><font size="-1"><%= mbFormat.format(usedMemory) %> MB</font>
			</td>
		</tr>
		<tr>
			<td><font size="-1">memory total:</font>
			</td>
			<td><font size="-1"><%= mbFormat.format(totalMemory) %>
					MB</font>
			</td>
		</tr>
	</table>
	<br>
	<table border=0>
		<td>
			<table bgcolor="#000000" cellpadding="1" cellspacing="0" border="0"
				width="200" align=left>
				<td>
					<table bgcolor="#000000" cellpadding="1" cellspacing="1" border="0"
						width="100%">
						<%    for (int i=0; i<NUM_BLOCKS; i++) {
        if ((i*(100/NUM_BLOCKS)) < free) {
    %>
						<td bgcolor="#00ff00" width="<%= (100/NUM_BLOCKS) %>%"><img
							src="../images/blank.gif" width="1" height="15" border="0">
						</td>
						<%        } else { %>
						<td bgcolor="#006600" width="<%= (100/NUM_BLOCKS) %>%"><img
							src="../images/blank.gif" width="1" height="15" border="0">
						</td>
						<%        }
    }
%>
					</table></td>
			</table>
		</td>
		<td><font size="-1"> &nbsp;<b><%= percentFormat.format(percentFree) %>%
					free</b> </font></td>
	</table>
</ul>
<script language="JavaScript">
function myrefresh(){
window.location.reload();
}
setTimeout('myrefresh()',1000); //指定1秒刷新一次
</script>