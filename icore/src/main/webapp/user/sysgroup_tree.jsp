<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<title>组维护</title>

		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>
		<link rel="StyleSheet" href="${cxp}/js/dtree.css" type="text/css" />
		<link rel="StyleSheet" href="${cxp}/css/css.css" type="text/css" />
		<script type="text/javascript" src="${cxp}/js/dtree.js"></script>


		<style type="text/css">
<!--
.mydivbgcolor {
	color: #000000;
	background: #BCE0FE;
	FILTER: progid :       DXImageTransform .       Microsoft .   
		  
		gradient(startColorStr :       #0099FF, EndColorStr :       #9FC7FB,
		GradientType :   
		   0);
	overflow: visible;
}

-->
div {
	font-size: 12px;
}

.menuTd {
	height: 20px;
	font-size: 12px;
	cursor: pointer;
}
</style>
<script>
	
	var cid = -1;


function showMyDiv(event){

   // event.cancelBubble=true;//以取消在该对象上的事件冒泡
    var myChild=(event.srcElement!=undefined?event.srcElement:event.target);//获取触发事件的对象

    var myDiv = $("#myDiv");
	//alert(myChild.cid);
	cid = myChild.getAttribute("cid");

	if(myChild.getAttribute("isroot")=="true"){
		$("#myDiv2").html($("#dm_2").html());
	}else{
		$("#myDiv2").html($("#dm_1").html());
	}
	
	var offset = $(myChild).offset();
	
	myDiv[0].style.top=offset.top+"px";//设置纵坐标
    myDiv[0].style.left=offset.left+30+"px";//设置横坐标

	myDiv.show();
	return   false; 
  }
function hideMyDiv(){
   $("#myDiv").hide();
}
 
function addChannel(){

if(cid == -1  ){
	window.parent.parent.jAlert("参数不正确.cid="+cid) ;
}

window.parent.parent.showWindow('${cxp}/user/sysgroup_add.jsp?id='+cid,'添加组',200,400);
}
function editChannel(){
if(cid == -1 ){
	window.parent.parent.jAlert("参数不正确.cid="+cid );
}
window.parent.parent.showWindow('sysGroup!getSysGroupInfo.action?groupId='+cid,'修改组',200,400);

}
function delChannel(){
if(cid == -1 ){
window.parent.parent.jAlert("参数不正确.cid="+cid, "系统提示");
	return ;
}
window.parent.parent.jConfirm('您真的要删除该组吗[子组也会一起被删除]?', '是否删除', function(r) {
	if (r) {
				var param = {
					c : cid
				}
				doPost("sysGroup!del.action", param, function(data) {
				
						window.parent.parent.jAlert(data.message, "系统提示");
						window.parent.location.reload();
					
				});
				}
	});
}

function tdFocus(obj){
	obj.bgColor="#006BB3";
	obj.style.color="#ffffff";
}
function tdBlur(obj){
	obj.bgColor="";
	obj.style.color="#000000";
}
</script>

	</head>


	<body onclick="hideMyDiv();">

		<div id="dm_1" style="display: none">
			<table width="95%" border="0" cellspacing="1" cellpadding="1"
				align="center">
				<tr class="menuTd">
					<td onmouseover="tdFocus(this)" onmouseout="tdBlur(this)"
						onclick="addChannel()">
						&nbsp;&nbsp;添加组
					</td>
				</tr>
				<tr class="menuTd">
					<td onmouseover="tdFocus(this)" onmouseout="tdBlur(this)"
						onclick="editChannel()">
						&nbsp;&nbsp;修改组
					</td>
				</tr>
				<tr class="menuTd" style="">
					<td onmouseover="tdFocus(this)" onmouseout="tdBlur(this)"
						onclick="delChannel()">
						&nbsp;&nbsp;删除组
					</td>
				</tr>
			</table>
		</div>

		<div id="dm_2" style="display: none">
			<table width="95%" border="0" cellspacing="1" cellpadding="1"
				align="center">
				<tr class="menuTd">
					<td onmouseover="tdFocus(this)" onmouseout="tdBlur(this)"
						onclick="addChannel()">
						&nbsp;&nbsp;添加组
					</td>
				</tr>
				<tr class="menuTd">
					<td style="color: #cccccc">
						&nbsp;&nbsp;修改组
					</td>
				</tr>
				<tr class="menuTd" style="color: #cccccc">
					<td>
						&nbsp;&nbsp;删除组
					</td>
				</tr>
			</table>
		</div>


		<div
			style="width: 100px; height: 100px; border-right: #e2e2e2 solid 2px; border-bottom: #e2e2e2 solid 2px; background-color: #ffffff; display: none; position: absolute"
			id="myDiv">
			<div style="width: 100%; height: 100%; border: #cccccc solid 1px;"
				id="myDiv2">
			</div>
		</div>

<script>

 imagepath="${cxp}/images/img";

</script>

		<div class="dtree" id="dtree" onclick="hideMyDiv()">



			<script>
d = new dTree('d');
d.add(<s:property value="sysGroup.id"/>,-1,'<span oncontextmenu="return showMyDiv(event)" isroot="true" cid="<s:property value="sysGroup.id"/>" ><s:property value="sysGroup.name"/></span>','javascript:');


<s:iterator id="info" value="trees">
d.add(<%=((String[]) pageContext.findAttribute("info"))[0]%>,<%=((String[]) pageContext.findAttribute("info"))[2]%>,'<span oncontextmenu="return showMyDiv(event)" cid="${info[0]}" ><%=((String[]) pageContext.findAttribute("info"))[1]%></span>','javascript:ahref(\'sysgroup_user.jsp?id=<%=((String[]) pageContext.findAttribute("info"))[0]%>&method=getSysGroupUsers\')');		

</s:iterator> 

document.write(d);



</script>
			<script>
var main = parent.window.main;

function myOpen(url){
        var w_left = screen.width/2-250;
        var w_height = screen.height/2-150;
        var changeWindow = window.open(url,'','width=500,height=300');
        changeWindow.moveTo(w_left,w_height);
}

function ahref(url){

    var mainf = parent.document.getElementById("mainf").contentWindow;
    var mainHref = mainf.location.href;
  	mainf.location.href=url;
  	/********
  //得取到主框架的地址栏搜索条件,?param=value....
  var msearch = main.location.search;
  //得到主框架的地址栏值
  var mhref = main.location.href;
  if(msearch == ""){
  //如果搜索条件为空,那么就直接打开URL
  main.location.href = url
  }else{
  //否则,只是将主框架地址栏中的搜索条件进行变更
  mhref = mhref.substring(0,mhref.indexOf("?"));
  main.location.href = mhref+url.substring(url.indexOf("?"));

  }
  ********/
  
}

</script>

		</div>
	</body>
</html>