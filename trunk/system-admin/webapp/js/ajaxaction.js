//定义全局的xml对象
var xmlHttp = null;
var oPopup = window.createPopup();
  function showPopup(value){
  with (oPopup.document.body) {
	style.backgroundColor="#FFFFFF";
	style.border="solid green 1px";
	style.fontSize="14px"
	style.color="0000FF"
	style.fontWeight="bold"
	innerHTML="<br><center>"+value+"</center>";
	}
	var width = 200;
	var height = 50
	oPopup.show((screen.width/2)-height-100, (screen.height/2)-width-100, width, height, document.body);
  }

function GetXmlHttpObject()
{
  var xmlHttp=null;
  try
    {
    // Firefox, Opera 8.0+, Safari
    xmlHttp=new XMLHttpRequest();
    }
  catch (e)
    {
    // Internet Explorer
    try
      {
      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
      }
    catch (e)
      {
      xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
      }
    }
	if(xmlHttp == null)
		alert("对不起,您的浏览器不支持Ajax");
  return xmlHttp;
} 

function sendGetAction(url,myStateChanged){
  //get传送
 
	if (xmlHttp==null)
  {
   xmlHttp=GetXmlHttpObject();

  }
  
  //指定响应函数
  xmlHttp.onreadystatechange=myStateChanged;
  xmlHttp.open("GET",url,true);
  xmlHttp.send(null);
}

function getQuery(form){
//将表单元数拼成Query条件
    queryString="";
    
	
    var numberElements =  form.elements.length;
    for(var i = 0; i < numberElements; i++) {
    
        if(i < numberElements-1) {
           if(form.elements[i].type=="checkbox" && form.elements[i].checked == false)
            continue;
            queryString += form.elements[i].name+"="+
                           encodeURI(encodeURI(form.elements[i].value))+"&";
                           
        } else {
            if(form.elements[i].type=="checkbox" && form.elements[i].checked == false)
            continue;
            queryString += form.elements[i].name+"="+
                           encodeURI(encodeURI(form.elements[i].value));
        }

    }
	return queryString;
    
}

function sendGetForm(form,myStateChanged){
  //表单GET提交
  var queryString = getQuery(form);
  var url = form.action;
  url = url+"?"+queryString+"&sid="+Math.random();
  sendGetAction(url,myStateChanged);
}

function sendPostAction(url,strQuery,myStateChanged){
//post传送

  	if (xmlHttp==null)
  {
  xmlHttp=GetXmlHttpObject();
 
  }
  xmlHttp.open("POST",url,true);
  xmlHttp.onreadystatechange=myStateChanged;
  xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  xmlHttp.send(strQuery);//发送请求
};

function sendPostForm(form,myStateChanged){
//表单post提交
  var queryString = getQuery(form);
  var url = form.action;
  sendPostAction(url,queryString,myStateChanged);

}



