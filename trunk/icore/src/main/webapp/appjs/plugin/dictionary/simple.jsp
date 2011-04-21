<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>dictionary-Helper</title>

		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>
		<script src="${cxp }/appjs/plugin/dictionary/jquery.dictionary.js"></script>

	</head>
<body>
<div class="Ejo2">
<div class="EjoBox">
  <div class="Etitle">基于数据字典写的级联菜单，支持N级</div>
  <div class="Ebox">
  
<br/>
	<select name="s1" id="s1">
	<option value="">请选择</option>
	</select>
	<select name="s2" id="s2">
	<option value="">请选择</option>
	</select>
	<select name="s3" id="s3">
	<option value="">请选择</option>
	</select>
	
	<script >
	$("#s1").dictionary({
	url:'${cxp }/manager/dictionary/dictionaryAction!getDictionarysByParentId.action',
	   data:{id:'1'},
	   
	   
		  callback:function(value){
		     $("#s2").dictionary({
				url:'${cxp }/manager/dictionary/dictionaryAction!getDictionarysByParentId.action',
				   data:{id:value},
				   callback:function(value){
				  	     $("#s3").dictionary({
							url:'${cxp }/manager/dictionary/dictionaryAction!getDictionarysByParentId.action',
							   data:{id:value},
							   callback:function(value){
							   		
							   },
							   defaultOp:[{name:'请选择信息',value:''}]
							    
							});
				   },
				   defaultOp:[{name:'请选择信息',value:''}]
				    
				});
		   },
		   defaultOp:[{name:'请选择信息',value:''}]
	    
	});
	
	
	
	</script>

</div>
</div>
<div class="yrepeat"></div>
<div class="Efooter"></div>
</div>
</body>
</html>

