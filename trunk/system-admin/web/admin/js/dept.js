/**********根据部门动态列出用户
***********autor:luocan created:20080325
************/
//定义树形结构显示的图片
var image_plus = "../images/images_tree/plus.gif";
var image_folder = "../images/images_tree/folder.gif";
var image_line = "../images/images_tree/line.gif";
var image_join = "../images/images_tree/join.gif";
var image_page = "../images/images_tree/page.gif";
var image_minusbottom = "../images/images_tree/minusbottom.gif"
var image_folderopen = "../images/images_tree/folderopen.gif";
var ctype = 1;//0表示生成单选 radio 1表示生成多选checkbox
var display_dept = 0;//需显示的部门，如果为0那么就显示所有部门
function listDept(depts){
//alert(ctype);
  
 for(var i=0;i<depts.length;i++){
 	if(display_dept != 0 ){
 		if(display_dept != depts[i].id);
 			continue;
 	}
 	var deptDiv = document.createElement("DIV");
 	deptDiv.setAttribute("id","dept_"+depts[i].id);
 	deptDiv.innerHTML="<img src='"+image_plus+"' style='cursor:pointer' align='absbottom'  onclick='listUser("+depts[i].id+")'><img src='"+image_folder+"' align='absbottom' onclick='listUser("+depts[i].id+")' style='cursor:pointer'>"+(ctype==0?"":"<input type='checkbox' name='deptId' border='0' style='height:18' value='"+depts[i].name+"' onclick='selectAllUser(event,"+depts[i].id+")'>")+depts[i].name;
 	$("#depts").append(deptDiv);
 }
 //加一个垫底的部门
 $("#depts").append(document.createElement("DIV"));
 depts = null;
 root = null;
}
//动态列出用户
function listUser(deptId){
if($("#dept_"+deptId).next()[0].id.match(/^users/)==null){
//如果用户还没有列出来，那么就把通过Ajax用户列出来
var userDivs = document.createElement("DIV");
userDivs.setAttribute("id","users");
userDivs.innerHTML="正在加载用户...";
$("#dept_"+deptId).after(userDivs);

$.ajax({
				type : "POST",
				url : "sysGroupAction.do?method=getSysUserByGroupIdToJson",
				data : {groupId:deptId},
				dataType : "json",
				success : function(users){
				
					 
					userDivs.innerHTML="";
					 for(var i=0;i<users.length;i++){
					    var userDiv = document.createElement("DIV");
					    userDiv.innerHTML="<img src='"+image_line+"' align='absbottom' ><img src='"+image_join+"' onclick='listUser("+users[i].id+")' align='absbottom'><img src='"+image_page+"' align='absbottom'><input type='"+(ctype==0?"radio":"checkbox")+"' name='userId' border='0' style='height:18' value='"+users[i].id+";"+users[i].truename+"'>"+users[i].truename;
					    userDivs.appendChild(userDiv);
					 };
					
 				
					},
					error:function(response){
					alert("查询人员失败，请您联系管理员！");
					}
					});
					
//更改图片显示
$("#dept_"+deptId).children()[0].src=image_minusbottom;
$("#dept_"+deptId).children()[1].src=image_folderopen;

}else{
 //否则就把针对用户进行显示与隐藏操作

 if($("#dept_"+deptId).children()[0].src.match(image_minusbottom) != null){
 	//隐藏
 	$("#dept_"+deptId).next().hide();
 	$("#dept_"+deptId).children()[0].src=image_plus;
 	$("#dept_"+deptId).children()[1].src=image_folder;
 	
 	
 }else{
 	//关闭
 	$("#dept_"+deptId).next().show();
 	$("#dept_"+deptId).children()[0].src=image_minusbottom;
 	$("#dept_"+deptId).children()[1].src=image_folderopen;
 }
 }
}


function selectAllUser(event,id){
var obj = (event.srcElement ? event.srcElement : event.target);// 获取触发事件的对象。
if($("#dept_"+id).next()[0].id.match(/^users/)!=null){
var inputs = $("input:checkbox",$("#dept_"+id).next());
for(var i=0;i<inputs.length;i++){
	inputs[i].checked = obj.checked;
}

}else{
//如果用户还没有列出来，那么就把通过Ajax用户列出来
var userDivs = document.createElement("DIV");
userDivs.innerHTML="正在加载用户...";
userDivs.setAttribute("id","users");
$("#dept_"+id).after(userDivs);
 

$.ajax({		type : "POST",
				url : "sysGroupAction.do?method=getSysUserByGroupIdToJson",
				data : {groupId:id},
				dataType : "json",
				success : function(users){

					 
					userDivs.innerHTML="";
					 for(var i=0;i<users.length;i++){
					    var userDiv = document.createElement("DIV");
					    userDiv.innerHTML="<img src='"+image_line+"' align='absbottom' ><img src='"+image_join+"' onclick='listUser("+users[i].id+")' align='absbottom'><img src='"+image_page+"' align='absbottom'><input type='"+(ctype==0?"radio":"checkbox")+"' name='userId' border='0' style='height:18' value='"+users[i].id+";"+users[i].truename+"'>"+users[i].truename;
					    userDivs.appendChild(userDiv);
					 };
					
					var inputs = $("input:checkbox",userDivs);
					for(var i=0;i<inputs.length;i++){
						inputs[i].checked = obj.checked;
					}
 
					
					},
					error:function(response){
					alert("查询人员失败，请您联系管理员！");
					}
					});
 
 
//更改图片显示
$("#dept_"+id).children()[0].src=image_minusbottom;
$("#dept_"+id).children()[1].src=image_folderopen;
}
}


	function userSelect(form){
		var ids = "";
		var names = "";
		var cs = form.userId;
		if(cs){
			if(cs.length){
				for(var i=0;i<cs.length;i++){
						if(cs[i].checked){
							if(ids!=""){
							ids+=";";
							names+=";";
							}
							 
							ids+=cs[i].value.split(";")[0]
							names+=cs[i].value.split(";")[1]
						}
				}
			}else{
				if(cs.checked){
					ids+=cs.value.split(";")[0]
					names+=cs.value.split(";")[1]
					
					}
			}
		}
		if(ids == ""){
			alert("请选择用户！");
			return ;
		}
		window.opener.setSelectIds(ids);
		window.opener.setSelectNames(names);
		window.close();
		
	}
	
