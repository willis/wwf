<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>仿QQ相册javascript左右选择照片效果</title>
		<%@ include file="/include/taglibs.jsp"%>
		<%@ include file="/include/jquery.jsp"%>
		<script src="${cxp}/js/page.js"></script>
		<style type="text/css">
#pdfleft{ 
border:1px solid #C5C4CC; 
width:140px;
height:570px; 
text-align: center;
overflow: auto;
/*overflow-x:auto;
overflow:scroll;*/
} 

#pdfmiddle{ 
border:1px solid #C5C4CC; 
} 


</style>
		
	</head>
<body style="background-color: transparent;" id="mybody">
	<script>
		wait("mybody", "页面加载中,请稍候...");
	</script>
	<form action="" method="post" >
				<table class="tableContent">
				<tbody>
					<tr id="topRow">
						<td id="topLeft">
						</td>
						<td id="topMiddle">
						</td>
						<td id="topRight">
						</td>
					</tr>
					<tr id="middleRow">
						<td id="middleLeft">
						</td>
						<td id="tdContent" bgColor="#ffffff">
				
		<table style="width:100%">
				
			<tr>
	
				<td  id="pdfleft" > <s:if test="picture!=null"> <img id="pdfimg" src="${cxp }/<s:property value="picture.path" /><s:property value="picture.filename" />"  style="max-width: 550px; max-height: 550px; height:expression(this.height > 550px ? '550px' : this.height);width:expression(this.width > 600px ? '600px' : this.width);"/></s:if>
									
								
				</td> 
				<td  id="pdfmiddle">
	
		
							
					<table class="table" style="width:100%;" >
					<tr>
						<td  width="20%"  class="lefttd">
							<a href="javascript:;" onclick="getTextValue('title');" >标题</a>：
						</td>
						<td>
							<input type="text" id="title" name="articleMongo.title" value="<s:property value="articleMongo.title"/>"  dataType="Require" msg="标题不能为空" style="width:90%" 
								 class="EditBox" />
						</td>
						<td  width="20%"  class="lefttd">
							<a href="javascript:;" onclick="getTextValue('viceTitle');" >副标题</a>：
						</td>
						<td>
							<input type="text"  id="viceTitle" name="articleMongo.viceTitle" value="<s:property value="articleMongo.viceTitle"/>" style="width:90%" 
								 class="EditBox" />
						</td>	
					</tr>
					<tr>
						<td  width="20%"  class="lefttd">
							分类：
						</td>
						<td colspan="3">
							 <select name="articleMongo.type" id="articletype"   dataType="Require" msg="分类不能为空"  style="width:150px" ></select> 
						</td>	
					</tr>
					<tr>
						<td  width="20%"  class="lefttd">
							题图：
						</td>
						<td colspan="3">
						
					
						</td>	
					</tr>
					<tr>
						<td  width="20%"  class="lefttd">
							<a href="javascript:;" onclick="getTextValue('introduction');" >导语</a>：
						</td>
						<td colspan="3">
							<textarea id="introduction" name="articleMongo.introduction" class="MultiEditBox" style="width:90%;height:20px"><s:property value="articleMongo.introduction"/></textarea>
						</td>	
					</tr>
					
					<tr>
						<td  width="20%" class="lefttd">
							<a href="javascript:;" onclick="getReturnValue('body');" >正文</a>：
						</td>
						<td colspan="3">
						<textarea id="body"  name="articleMongo.content"  style="width:90%;height:180px"><s:property value="articleMongo.content"/></textarea>
						
						</td>
					</tr>
					<tr>
						<td  width="20%" class="lefttd">
							<a href="javascript:;" onclick="getTextValue('author');" >作者</a>：
						</td>
						<td colspan="3">
							<input type="text" id="author" name="articleMongo.author" value="<s:property value="articleMongo.author"/>" style="width:20%" 
								 class="EditBox" />
						</td>
					</tr>
					<tr>
						<td  width="20%" class="lefttd">
							编辑：
						</td>
						<td >
						<input type="hidden" name="articleMongo.editor" value="${userOBJ.truename}"/> 
							${userOBJ.truename}
						</td>
						<td  width="20%" class="lefttd">
							数据来源：
						</td>
						<td >
							<input type="hidden" name="articleMongo.datasources" value="<s:property value="pdf.bookitemname"/>"/> <s:property value="pdf.bookitemname"/></td>
					</tr>
					
				</table>
				
				<div class="buttons" style="margin-top: 10px;">
							 <s:if test="picture!=null"> <input type="button" class="button_big" id="prev" name="prev"  value="上一张" /></s:if>
								<input type="button" class="button_big" id="formReset" name="formReset" value="刷新" />
							 <s:if test="picture!=null"> <input type="button" class="button_big" id="next" name="next" value="下一张"/></s:if>
				</div>
				</td>
				</tr>
				</table>
				
			<td id="middleRight">
						</td>
					</tr>
					<tr id="bottomRow">
						<td id="bottomLeft">
						</td>
						<td id="bottomMiddle">
						</td>
						<td id="bottomRight">
						</td>
					</tr>
				</tbody>
			</table>
				</form>

	</body>
	<script type="text/javascript">
	$("#prev").click(function() {
		window.location='${cxp }/manager/bot/pictureAction!imagePrev.action?id=<s:property value="picture.id" />';
	});
	
	$("#next").click(function() {
		window.location='${cxp }/manager/bot/pictureAction!imageNext.action?id=<s:property value="picture.id" />';
	});
	$(function() {

		$(document).unblock();
	});
	</script>
</html>