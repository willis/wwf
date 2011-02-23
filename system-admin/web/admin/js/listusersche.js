/**********根据部门动态列出用户
***********autor:luocan created:20080325
************/
//定义树形结构显示的图片
var image_plus = imagepath+"/plus.gif";
var image_folder = imagepath+"/drive_user.png";
var image_line = imagepath+"/line.gif";
var image_join = imagepath+"/join.gif";
var image_page = imagepath+"/user_gray.png";
var image_minusbottom = imagepath+"/minusbottom.gif"
var image_folderopen = imagepath+"/drive_user.png";
var ctype = 1;//0表示生成单选 radio 1表示生成多选checkbox
var display_dept = 0;//需显示的部门，如果为0那么就显示所有部门
function listDept(response){
//alert(ctype);
  var root = response.responseXML.documentElement;
  var depts = root.childNodes;
 for(var i=0;i<depts.length;i++){
 	if(display_dept != 0 ){
 		if(display_dept != depts[i].getAttribute("id"))
 			continue;
 	}
 	var deptDiv = document.createElement("DIV");
 	deptDiv.setAttribute("id","dept_"+depts[i].getAttribute("id"));
 	deptDiv.innerHTML="<img src='"+image_plus+"' style='cursor:pointer' align='absbottom'  onclick='listUser("+depts[i].getAttribute("id")+")'><img src='"+image_folder+"' align='absbottom' onclick='listUser("+depts[i].getAttribute("id")+")' style='cursor:pointer'>"+(ctype==0?"":"<input type='checkbox' name='deptId' border='0' style='height:18' value='"+depts[i].getAttribute("name")+"' onclick='selectAllUser(event,"+depts[i].getAttribute("id")+")'>")+depts[i].getAttribute("name");
 	$("depts").appendChild(deptDiv);
 }
 //加一个垫底的部门
 $("depts").appendChild(document.createElement("DIV"));
 depts = null;
 root = null;
}
//动态列出用户
function listUser(deptId){
if($("dept_"+deptId).nextSibling.id.match(/^users/)==null){
//如果用户还没有列出来，那么就把通过Ajax用户列出来
var userDivs = document.createElement("DIV");
userDivs.setAttribute("id","users");
userDivs.innerHTML="正在加载用户...";
$("depts").insertBefore(userDivs,$("dept_"+deptId).nextSibling);
//动态读取数据
new Ajax.Request('choosedept.do', {
method:  'post',
parameters:"method=choosebyuser&id="+deptId,
onSuccess: function(response){
userDivs.innerHTML="";

var root = response.responseXML.documentElement;
var users = root.childNodes;

 for(var i=0;i<users.length;i++){
    var userDiv = document.createElement("DIV");
    userDiv.innerHTML="<img src='"+image_line+"' align='absbottom' ><img src='"+image_join+"' onclick='listUser("+users[i].getAttribute("id")+")' align='absbottom'><img src='"+image_page+"' align='absbottom'><input type='"+(ctype==0?"radio":"checkbox")+"' name='userId' border='0' style='height:18' value='"+users[i].getAttribute("id")+"|"+users[i].getAttribute("name")+"'>"+users[i].getAttribute("name");
    userDivs.appendChild(userDiv);
 };

},
onFailure:function(response){
	alert(response.responseText);
alert("查询人员失败，请您联系管理员！");
}
});
//更改图片显示
$("dept_"+deptId).firstChild.src=image_minusbottom;
$("dept_"+deptId).firstChild.nextSibling.src=image_folderopen;

}else{
 //否则就把针对用户进行显示与隐藏操作

 if($("dept_"+deptId).firstChild.src.match(image_minusbottom) != null){
 	//隐藏
 	$("dept_"+deptId).nextSibling.style.display='none';
 	$("dept_"+deptId).firstChild.src=image_plus;
 	$("dept_"+deptId).firstChild.nextSibling.src=image_folder;
 	
 	
 }else{
 	//关闭
 	$("dept_"+deptId).nextSibling.style.display='';
 	$("dept_"+deptId).firstChild.src=image_minusbottom;
 	$("dept_"+deptId).firstChild.nextSibling.src=image_folderopen;
 }
 }
}


function selectAllUser(event,id){
var obj = event.srcElement;
if($("dept_"+id).nextSibling.id.match(/^users/)!=null){
var inputs = $("dept_"+id).nextSibling.getElementsByTagName("input");
for(var i=0;i<inputs.length;i++){
	inputs[i].checked = obj.checked;
}

}else{
//如果用户还没有列出来，那么就把通过Ajax用户列出来
var userDivs = document.createElement("DIV");
userDivs.innerHTML="正在加载用户...";
userDivs.setAttribute("id","users");
$("depts").insertBefore(userDivs,$("dept_"+id).nextSibling);

//动态读取数据
new Ajax.Request('choosedept.do', {
method:  'post',
parameters:"method=choosebyuser&id="+id,

onSuccess: function(response){

var root = response.responseXML.documentElement;
var users = root.childNodes;
alert('-----------'+root);
userDivs.innerHTML="";

 for(var i=0;i<users.length;i++){
    var userDiv = document.createElement("DIV");
    userDiv.innerHTML="<img src='"+image_line+"' align='absbottom' ><img src='"+image_join+"' onclick='listUser("+users[i].getAttribute("id")+")' align='absbottom'><img src='"+image_page+"' align='absbottom'><input type='"+(ctype==0?"radio":"checkbox")+"' name='userId' border='0' style='height:18' value='"+users[i].getAttribute("id")+"|"+users[i].getAttribute("name")+"'>"+users[i].getAttribute("name");
    userDivs.appendChild(userDiv);
 };
var inputs = userDivs.getElementsByTagName("input");
for(var i=0;i<inputs.length;i++){
	inputs[i].checked = obj.checked;
}
},
onFailure:function(response){
alert("查询人员失败，请您联系管理员！");
}
});
//更改图片显示
$("dept_"+id).firstChild.src=image_minusbottom;
$("dept_"+id).firstChild.nextSibling.src=image_folderopen;
}

}


/******
赋值操作 针对于型号干部的JS操作
********/
//设置用户
function setValue(){
var checkboxs = document.getElementsByName("userId");

var ids = '';
var names = '';

//将选中的用户名进行添加
for(var i=0;i<checkboxs.length;i++){
	if(checkboxs[i].checked){
		
			var valueStr = checkboxs[i].value.split('|');
			ids += valueStr[0]+',';
			names += valueStr[1]+',';
			
	}
}
if(ids.length>0){
    names=names.substring(0,names.length-1);
    window.opener.document.getElementById("person").value=names;
	window.opener.document.getElementById("personsId").value=ids;
	//window.opener[window.location.search.parseQuery()["objForm"]][window.location.search.parseQuery()["objId"]].value=ids;
	//window.opener[window.location.search.parseQuery()["objForm"]][window.location.search.parseQuery()["objName"]].value=names;
}
window.close();
}
