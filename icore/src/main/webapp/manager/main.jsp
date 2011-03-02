<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title></title>
		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>
		<script type="text/javascript"
			src="${cxp}/js/My97DatePicker/WdatePicker.js"></script>

		<link href="${cxp}/js/dtree.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${cxp}/js/dtree2.js"></script>


	</head>
	<body style="height: 100%;">

		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			id="mytabl">
			<tr>
				<td>
					<h1 id="header">
						<!--  <img src="${cxp }/images/head.gif" alt="header" title="header" class="header" />-->
					</h1>

					<div id="loginInfo">
						<div id="loginInfo_left">

						</div>
						<div id="loginInfo_content">
							<div id="webjx"></div>
							<script>
	  setInterval("document.getElementById('webjx').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new      Date().getDay());",1000);
	  </script>
						</div>
						<div id="loginInfo_right">
							<a href="${cxp }/login_out.jsp">退出</a>
						</div>
						<div
							style="position: absolute; top: 24px; right: -7px; width: 260px;">

						</div>
					</div>

					<div id="nav" class="navigation">
						<ul>

							<pager:eachMenu var="menu" alias="menu_system" levelLimit="1"
								varStatus="index">
								<s:if test="#attr.index>0">
									<li>
										<a href="${cxp}/manager/main.jsp?menuAlias=${menu.alias}"  ${param.menuAlias == menu.alias ? 'class=active' : '' } >${menu.name}</a>
									</li>
								</s:if>
							</pager:eachMenu>
						</ul>
					</div>
				</td>
			</tr>
			<tr>

				<td>
					<!-- tree begin管理 -->
	
<pager:menu var="sysMenuBean" alias="${param.menuAlias}" check="true"/>
					<div id="content">
						<div id="left" style="overflow: auto">

							<h2 class="person">
								${sysMenuBean.name}
							</h2>
							<span class="userName"> <!--  --> <FONT title="${userOBJ.truename}">

									${userOBJ.truename} </FONT> ，你好！ </span>
							<div class="dtree">




		<script type="text/javascript">
			<!--
			
			imagepath='${cxp}/'+imagepath;
		
			d = new dTree('d');
	 
			d.add("${sysMenuBean.id}",-1,'${sysMenuBean.name}');
			<pager:eachMenu var="menu" alias="${param.menuAlias}" varStatus="ind">
			<s:if test="#attr.ind==1">
				<s:if test="not empty(#attr.menu.link)">
				<s:set var="defaultLink" value="#attr.menu.link"/>
				</s:if>
				
			</s:if>
		
			d.add(${menu.id},${menu.parentObj.id},"${menu.name}","javascript:<s:if test="not empty(#attr.menu.link)">d.mys('${cxp}${menu.link}')</s:if>","","" );
			</pager:eachMenu>

			document.write(d);
		  
			//-->
		</script>


							</div>




						</div>
						<div id="rightFrame">
							<iframe id="rFrame" name="rFrame" width="100%" height="615px"
								frameborder="No" border="0" marginwidth="0" marginheight="0"
								scrolling="auto" src="${cxp }${not empty defaultLink?defaultLink:'/error/404.jsp'}"></iframe>
						</div>
					</div>
					<!-- tree end -->


				</td>
			</tr>
			<tr>
				<td>

				</td>
			</tr>
		</table>
		<div id="footer">
			<img src="${cxp }/images/footer.gif" alt="footer" title="footer"
				class="footer" />
		</div>
		<script>
	 $('#rightFrame').width(document.body.scrollWidth-220);
		
		</script>

	</body>

</html>

