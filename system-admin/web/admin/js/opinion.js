/*****
**通用意思帮助JS类
**********/
function OpinionHelp(){
//意见集合对象
this. opinionArray = new Array();
//容器对象
this. containerObj = null;
//目标对象
this. targeObj = null;
//源对象
this. sourceObj = null;
//容器引入的CSS样式
this. containerClass = "opinion";
//子项DIV引入的CSS样式
this. elClass = "el";
//鼠标经过时的颜色
this. selectedColor = "#AAD5FB";
//鼠标走过后的颜色
this. unSelectedColor = "#FFFFFF";

this.bigcontainer=null;

};
//添加意见
OpinionHelp.prototype.putOpinion=function(opinion){
var divObj = document.createElement("DIV");

//divObj.innerText = opinion;
divObj.innerHTML = opinion;
this.opinionArray[this.opinionArray.length]=divObj;
divObj.className=this.elClass;
divObj.onclick = bindFunction(this,"setValue")
//divObj.onmouseover = bindFunction(this,"foucsOfDiv");
}
function  bindFunction(el, fucName) {
     return   function  () {
		 //el表示影响上下文的对象 通常用来替换调用方法中的this，arguments 该对象代表正在执行的函数和调用它的函数的参数
         //Call表示 调用某一对象的一个方法，用另一个对象替换当前对象 格式：call([thisObj[,arg1[, arg2[,   [,.argN]]]]])
		 //return  el[fucName].call(el);
		 //Apply表示 应用某一对象的一个方法，用另一个对象替换当前对象 格式：apply([thisObj[,argArray]])
		 return el[fucName].apply(el,arguments);
    };
};


//初始化目标对象与容器对象
OpinionHelp.prototype.init=function(_sourceObj,_targeObj,_bigcontainer){
if(this.targeObj == null){
  this. _sourceObj = _sourceObj;
  this. targetObj = _targeObj;
  this.bigcontainer = _bigcontainer;
  
}

if(this.containerObj == null){
   this.containerObj = document.createElement("DIV");
   this.containerObj.className = this.containerClass;
   for(var i=0 ;i<this.opinionArray.length;i++){
	this.containerObj.appendChild(this.opinionArray[i]);
   }
	
   this.containerObj.style.top=this.getOffset(_sourceObj,false)+20;
   this.containerObj.style.left=this.getOffset(_sourceObj,true);
   
    
   
   this.containerObj.style.display="none";
  
   this.bigcontainer.appendChild(this.containerObj);
   //document.body.appendChild(this.containerObj);
   
}
}
//显示帮助信息
OpinionHelp.prototype.show = function(){
this.containerObj.style.top=(this.getOffset(this._sourceObj,false)+20)+"px";
this.containerObj.style.left=this.getOffset(this._sourceObj,true)+"px";
 

this.containerObj.style.display=(this.containerObj.style.display==""?"none":"");

}
//隐藏帮助信息
OpinionHelp.prototype.hidden = function(){
this.containerObj.style.display="none";
}

//得到焦点
 OpinionHelp.prototype.foucsOfDiv = function(){
	  var eobj
	  event.cancelBubble=true;//取消事件的冒泡对象
	  eobj=event.srcElement;//获取触发事件的对象
	  for(var i=0;i<this.opinionArray.length;){
	    this.opinionArray[i++].style.background=this.unSelectedColor;
	  }
	  eobj.style.background = this.selectedColor;
}
//设值操作
OpinionHelp.prototype.setValue = function(){
	  
	var _event = (arguments.length == 0 ? event : arguments[0]);
	_event.cancelBubble = true;// 取消事件的冒泡对象
	var eobj = (_event.srcElement ? _event.srcElement : _event.target);// 获取触发事件的对象。
	 // alert(eobj.previousSibling);
	  // alert(eobj.tagName);
	 // this.targetObj.value = eobj.innerText;
	 // this.hidden();
	 if(eobj.tagName == "DIV")
		 eobj = eobj.firstChild;
	 if(eobj.tagName == "INPUT"){
		 eobj.click();// == (eobj.checked?false:true);
	 }else if(eobj.tagName == undefined) {
	 	
	 	this.targetObj.value=eobj.nodeValue;
	 	this.hidden();
	 }
	 
	 /********
	 var inputs = this.containerObj.getElementsByTagName("INPUT");
	 this.targetObj.value="";
	 for(var i=0;i<inputs.length;i++){
		  if(inputs[i].checked){
			 if(this.targetObj.value.length>0)
				 this.targetObj.value+=",";
			 this.targetObj.value +=inputs[i].value;
		  }
	 }
	 ********/
}
//获取对象的绝对位置
OpinionHelp.prototype.getOffset = function(el,isLeft){
	var retValue = 0;
	while(el != null){
	   retValue+=el["offset"+(isLeft ? "Left" : "Top")];
	   el = el.offsetParent;
	}
	return retValue;
}

