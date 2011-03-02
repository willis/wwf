
/***********
CMS扩展字段属性修改
author:tianbao
*********/
var CmsField = Class.create(); 
CmsField.prototype={
	initialize:function(){
	this.isEdit=true;
	this.oldValue="";
	this.id=-1;//ID
	this.tdObj=null;
	},
    //修改字段名
	editFname:function(tdObj,value,id){
		if(this.canEdit()){
			this.bindEdit(tdObj,value,id);
			tdObj.innerHTML="<input type='text'  onblur='jpManager.submit(this.parentNode,this.value,0)' value='"+this.oldValue+"' >";
			tdObj.firstChild.focus();
		}
	},
	    //修改字段描述
	editFdesc:function(tdObj,value,id){
		if(this.canEdit()){
			this.bindEdit(tdObj,value,id);
			tdObj.innerHTML="<input type='text' onblur='jpManager.submit(this.parentNode,this.value,1)' value='"+this.oldValue+"' >";
			tdObj.firstChild.focus();
		}
	},

	
	submit:function(tdObj,value,flag){
		if(this.tdObj != tdObj){
			alert("tdobj error !");
			return ;
		}
		if(this.oldValue==value.trim()  || value.trim()=="" ){
			this.cancelSubmit();
			return;
		}
		if(confirm("您真的要提交修改吗？")){
			//alert(this.id);
				this.sendAjaxCommand(value.trim(),flag);
				
		}else{
			this.cancelSubmit();
		}
	},
	sendAjaxCommand:function(value,flag){
				var me = this;
				new Ajax.Request("updateDocumentField.do?method=updateDocumentField",{
				parameters:"id="+this.id+"&type="+flag+"&value="+encodeURI(encodeURI(value)),
				onSuccess:function(response){
				//	alert(response.responseText);
				
					if(response.responseText.indexOf('ok')!=-1){
						me.tdObj.innerHTML=value;
					}else{
						alert("error!");
						alert(response.responseText);
					}
					me.freeEdit();
				},
				onFailure:function(response){
					alert("error!");
					alert(response.responseText);
				}
			});
	}
	,
	cancelSubmit:function(){
		this.tdObj.innerHTML=this.oldValue;
		this.freeEdit();
	},
	
	//修改类型
	editFtype:function(tdObj,value,id){
		if(this.canEdit()){
		this.bindEdit(tdObj,value,id);
		tdObj.innerHTML="<select onblur='jpManager.submitFtype(this.parentNode,this.value,2)'><option value='整型'>整型</option><option value='长整型'>长整型</option><option value='浮点型'>浮点型</option><option value='双精度型'>双精度型</option><option value='字符串型'>字符串型</option><option value='文本型'>文本型</option></select>";
		tdObj.firstChild.focus();
		tdObj.firstChild.value=this.oldValue;

	}
	}
	,
	submitFtype:function(tdObj,value,flag){
		if(this.tdObj != tdObj){
			alert("tdobj error !");
			return ;
		}
		if(this.oldValue==value.trim()  || value.trim()=="" ){
			this.cancelSubmit();
			return;
		}
		if(confirm("您真的要提交修改吗？")){
				var value22 = "0";
			
				if(value.trim()=="长整型"){
					value22 = "1";
				}else if(value.trim()=="浮点型"){
					value22 = "2";
				}else if(value.trim()=="双精度型"){
					value22 = "3";
				}else if(value.trim()=="字符串型"){
					value22 = "4";
				}else if(value.trim()=="文本型"){
					value22 = "5";
				}
				
				this.sendFtypeAjaxCommand(value,value22,flag);
				
		}else{
			this.cancelSubmit();
		}
	}
	,	sendFtypeAjaxCommand:function(value1,value2,flag){
				var me = this;
				new Ajax.Request("updateDocumentField.do?method=updateDocumentField",{
				parameters:"id="+this.id+"&type="+flag+"&value="+value2,
				onSuccess:function(response){
				//	alert(response.responseText);
				
					if(response.responseText.indexOf('ok')!=-1){
						me.tdObj.innerHTML=value1;
					}else{
						alert("error!");
						alert(response.responseText);
					}
					me.freeEdit();
				},
				onFailure:function(response){
					alert("error!");
					alert(response.responseText);
				}
			});
	}
	,
	canEdit:function(){
		return this.isEdit;
	},
	freeEdit:function(){
		this.isEdit=true;
		this.id=-1;
		this.tdObj=null;
	},
	lockEdit:function(){
		this.isEdit=false;
	}
	,
	bindEdit:function(tdObj,value,id){
			this.lockEdit();
			this.id = id;
			this.oldValue=value.trim();
			this.tdObj = tdObj;

			
	}

}

