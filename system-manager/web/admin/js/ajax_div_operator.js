	//js操作图层v0.1版
	//@author：tianbao  
	//@created： 2007-08-24 @email：tianbaolc@163.com
	//功能描述：1）类似于google的关键字的智能查询帮助。
	//通过输入的关键字可以查询到相匹配的选项，将结果列在在DIV图层容器中，目前支持键盘的上下键移动选择与鼠标选择



	//定义一个全局类
	var globalVariable = new GlobalVariable(0,"-1");
	//定义一个全局类
	function GlobalVariable(l,i){
	//定义一个全局的长度
	this.length = l ;
	//定义一个全局的索引
	this.index = i;  
	}
	//目标对象
	var targeObj = null;
	//目标对象的xy坐标
	var xyObj = null;
	//容器对象
	var containerObj = null;
	//查询帮助初始化
function searchHelpInit(targeName){
		  targeObj = document.getElementById(targeName);
		  
			xyObj = getOffset(targeObj);
			containerObj = document.createElement("div");
			with(containerObj){
			   style.position="absolute";
			   style.width = targeObj.offsetWidth;
			   style.height = 80;
			   style.background = "#ffffff";
			   style.border="1px solid ";
			   style.borderColor="#cccccc";
			   style.display="none";
			   style.top=xyObj[0]+20;
			   style.left=xyObj[1];
			}
			document.body.appendChild(containerObj);
			document.attachEvent("onclick",function(){divHidden(containerObj)});
}
//查询帮助主要方法
function searchHelp(url,queryString){

			divShow(containerObj);
			
if(window.event.keyCode == 40){
		//如果值为40的话，表示按下的是键盘的往下翦头
		//当全局变量的长度大小0
		if(globalVariable.length >0 ){
		//索引加1
		   globalVariable.index++;
		   //如果索引等于长度中，那么索引等于长度减1
		 if(globalVariable.index >=globalVariable.length){
		    globalVariable.index = globalVariable.length-1;
		    return;
		  }
		  		  //选定索引的图层
		   selectOfDiv(containerObj);
		}

}else if(window.event.keyCode == 38)
{
		//如果值为38的话，表示按下的是键盘的往上翦头
		if(globalVariable.length >0 ){
		  //如果长度大于0;
		  //那么索引递减
		  globalVariable.index--;
		 // alert(globalVariable.index);
		  //如果索引小于-1时，那么索引等于-1
		  if(globalVariable.index <= -1){
		    globalVariable.index = 0;
		    return;
		  }
  	  //选定索引的图层
		   selectOfDiv(containerObj);		   
		  }
}else if(window.event.keyCode == 13){
     //如果用户回车了，那么索引的对象赋值
     	if(globalVariable.index<0)
     	return;	  
		  setValueOfDiv(targeObj,containerObj);
		  return;
}else{
     //否则，通过ajax去查询出匹配的选项
      sendPostAction(url,queryString,function(){ajax_fill_container(targeObj,containerObj)});
}

}

	//取得绝对位置
	function getOffset(e) 
	{  
	 var t=e.offsetTop;  
	 var l=e.offsetLeft;  
	 while(e=e.offsetParent) 
	 {  
	  t+=e.offsetTop;  
	  l+=e.offsetLeft;  
	 }  
	 var rec = new Array(1); 
	 rec[0]  = t; 
	 rec[1] = l; 
	 return rec 
	}
	
	//为对象获取焦点
	function foucsOfDiv(srcObj){
	  var eobj
	  if(srcObj.tagName == undefined){
	  event.cancelBubble=true;//取消事件的冒泡对象
	  eobj=event.srcElement;//获取触发事件的对象
	  }else{
	     eobj = srcObj
	  }
	  var parentObj = eobj.parentNode;//得父的对象
	  var childsObj = parentObj.childNodes;//得到子的对象
	  for(var i=0;i<childsObj.length;){
	    childsObj[i++].style.background="#ffffff";
	  }
	  eobj.style.background="#AAD5FB";
	  
	  globalVariable.index = eobj.id.substring(6);
	}
	//将div的值设置到指定对象中
	function divSetValue(targeObj,containerObj){
	  event.cancelBubble=true;//取消事件的冒泡对象
	  var eobj=event.srcElement;//获取触发事件的对象
	  targeObj.value=eobj.innerHTML;//赋值
	  divHidden(containerObj);
	}
	
	//ajax将查询值进行容器填充
	function ajax_fill_container(srcObj,containerObj){
	     if (xmlHttp.readyState==4)
	     { 
				//先清空容器中的组件
				var componentsObj = containerObj.childNodes;
				while(componentsObj.length>0){
				  containerObj.removeChild(componentsObj[0])
				};
	
				//得取xml对象中的值
	     var xmlObj = xmlHttp.responseXML;
	     var root = xmlObj.documentElement;
	     var objs = root.childNodes;
	     
	     //再将查询到的值加进去
	     for(var i=0;i<objs.length;){
	         var divObj = document.createElement("div");;
	         with(divObj){
	           id="child_"+i;
	           innerHTML = objs[i++].firstChild.nodeValue;
	           
	           attachEvent("onmouseover",foucsOfDiv);
	           attachEvent("onclick",function(){divSetValue(srcObj,containerObj)});
	         }
	         containerObj.appendChild(divObj);
	       }
	       //初始化全局变量
	       globalVariable.length=objs.length;
	       globalVariable.index="-1";
	     }
	}

	//选中DIV容器中的子DIV
	function selectOfDiv(containerObj){
	  var componentsObj = containerObj.childNodes;
	      foucsOfDiv(componentsObj(globalVariable.index));   
	  }
	
	//为DIV容器中的子DIV索引到的对象赋值
	
		function setValueOfDiv(targeObj,containerObj){
	  var componentsObj = containerObj.childNodes;
	       targeObj.value=componentsObj[globalVariable.index].innerHTML;//赋值
	       divHidden(containerObj);
  	}
	
	//隐藏指定的DIV
	function divHidden(divObj){
	   divObj.style.display="none";
	}
	//显示指定的DIV
	function divShow(divObj){
	   divObj.style.display = "";
	}