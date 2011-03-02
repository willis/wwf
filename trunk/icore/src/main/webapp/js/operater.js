function myOpen(url,width,height){
        var w_left = screen.width/2-(width/2);
        var w_height = screen.height/2-(height/2);
        var changeWindow = window.open(url,'','scrollbars=yes,width='+width+',height='+height);
        changeWindow.moveTo(w_left,w_height);
}

function chageBgImg(obj,imgsrc){
       obj.background=imgsrc;

}

function openLanMu(lanmu)
	{
				lanmu.style.display=lanmu.style.display=="none"?"":"none";
	}
function selectAll(form,value,sibobj){
   
  var eobj = form.elements;
  if(value)
  sibobj.nodeValue = "取消全选";
  else
  sibobj.nodeValue = "全选";
  
  if(eobj.length)
  for(var i = 0 ;i<eobj.length;i++){
     
     if(eobj[i].type == "checkbox"){
        eobj[i].checked = value;
     }
  }
  
}

function delInfo(form){
doInfo(form,"请钩选要删除的信息","您确定要将这些信息删除吗?");
}

function addInfo(form){
doInfo(form,"请钩选要添加的信息","您确定要将添加这些信息吗?");
  
}

function doInfo(form,msg1,msg2){
var eobj = form.elements;
var hasChecked = false;

  for(var i = 0 ;i<eobj.length;i++){
     
     if(eobj[i].type == "checkbox" && eobj[i].checked == true && eobj[i].name == "c"){
         hasChecked = true;
     }
  }
  
  if(!hasChecked){
    alert(msg1);
    return;
  }else{
  

     if(confirm(msg2)){
       form.submit();
     }
   
  }
  
}

function delMe(url){
			var value = getCheckedValues('c');
			if(value.length == 0)
			 {
			 	alert("请钩选您要删除的记录！");
			 	return ;
			 }
			 if(confirm("您真的要删除该记录吗？")){
				 $.ajax({
					type : "POST",
					url : url,
					data : {c:value,version:'1.0'},
					dataType : "json",
					success : function(datas) {
					if(datas.status == SUCCESS){
							  query();
						}else{
							 alert(datas.message);
						}
					},
					error : function(response) {
							alert(response.responseText);
							}
						
					})
				}
}
function getCheckedValuesByContainer(elementName,container) {
	// 1.2.6可用
	// var txt = "input[@name=" + elementName + "][@checked]";
	var txt = "input[name=" + elementName + "]:checked"
	// 定义数组
	var stateStack = new Array();
	var tmp = $(txt,container);
	if (tmp) {
		// 遍历名称为state的checkbox,得到状态为checked的值
		tmp.each(function() {
					stateStack.push($(this).val());
				})
	}
	return stateStack;
}
