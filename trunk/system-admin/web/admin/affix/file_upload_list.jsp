<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<html>
	<head></head>
	<body>
			<s:iterator value="beans">
				<div>
					<s:property value="name" />
					：
					<a href="${cxp}/affix/affixAction!download.action?id=${affix.id }">下载</a>
				<s:if test="#parameters.action=='update'">
					<a href='javascript:delAffix(<s:property value="id"/>)'>删除</a>
				</s:if>
			</div>
			</s:iterator>
	
			<s:if test="#parameters.action=='update' && (empty (#parameters.sigin) || (not empty (#parameters.sigin) && beans.isEmpty()))">

				<s:if test="empty #parameters.num || #parameters.num>affixSize">
					<iframe src="" id="uploadFrame" name="uploadFrame" height="0"
						width="0" style="border: 0px;"></iframe>
					<form name="form" id="photoForm" target="uploadFrame"
						action="${cxp}/affix/uploadFile!addFile.action" method="post"
						enctype="multipart/form-data" onsubmit="return upload(this)">
						<input type="hidden" name="objectType" value="${param.objectType}">
						<input type="hidden" name="objectId" value="${param.objectId}">
						<input type="file" name="file" style="width: 90%">
						<input type="submit" value="上传" />
					</form>
				</s:if>
			</s:if>





			<script>
function upload(form){
	 var value = form.file.value;
	if(value !=""){
		var filetype = value.substring(value.lastIndexOf(".")+1);
		 
 		if("${param.types}"!=""){
 			
 			if("${param.types}".indexOf(filetype)<0){
 				alert("请上传后缀名为${param.types}的文件!");
 				return false;
 			}
 			
 		}
		
		$("#photoForm").block({
				message : "文件上传中！",
				css : {
					width : '70%'
					// ,border : '1px solid #a00'
				}
			});

		 
		 return true;
 
	} 
	return false;
}
function uploadPhoto_ok(idv,source){
 
	$("#photoForm").unblock();
	$("#photoUrl").val(source);
	$("#photoImg").val("${cxp}/affix/affixAction!showImage.action?id="+idv);
}
function delAffix(value){
if(confirm("您要的要删除该附件吗？")){
	$.ajax({
				type : "POST",
				url : "${cxp}/affix/affixAction.do!delete.action",
				data : {id:value},
				dataType : "json",
				success : function(data){
				   window.location.reload();
				
				},
				error:function(response){
					alert("出错了，请联系管理员");
					alert(response.responseText);
				}	 

			});
}
}


function iframeAutoFit() {
  try {
    if(window != parent) {
      var a = parent.document.getElementsByTagName("IFRAME");
      for(var i = 0; i < a.length; i++) {
        if(a[i].contentWindow == window) {
          var h1 = 0, h2 = 0, d = document, dd = d.documentElement;
          a[i].parentNode.style.height = a[i].offsetHeight +"px";
          a[i].style.height = "10px";
          if(dd && dd.scrollHeight) {
            h1=dd.scrollHeight;
          }
          if(d.body) {
            h2 = d.body.scrollHeight;
          }
          var h = Math.max(h1, h2);
          if(document.all) {
            h += 4;
          }
          if(window.opera) {
            h += 1;
          }
          a[i].style.height = a[i].parentNode.style.height = h +"px";
        }
      }
    }
  } catch(ex) {}
}


</script>
	</body>
</html>