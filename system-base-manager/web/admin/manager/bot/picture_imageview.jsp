<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<script src="${cxp}/js/page.js"></script>
<style type="text/css">
	html,body,div,ul,li,span,form,input{
		margin:0px;
		padding:0px;}
	div.div_main{
		min-width:400px;
		width:auto; 
		background-repeat:no-repeat;
		position:relative;}

    .div_mr_bottom{
		position:relative;
		background-repeat:no-repeat;}


	span.brand_sapn{
		font-style:normal;
		font-family:Arial, Helvetica, sans-serif;
		font-size:13px;
		color:#999;}		
	#fanye a{
		color:#666;
		text-decoration:none;}
	#fanye a:hover{
		color:#9C0;}
	.div_middle{
		float:left; 
		width:auto; 
		height:auto; 
		margin-top:10px; 
		margin-left:25px!important; margin-left:15px;}
	.div_table{
		width: 170px; 
		font-size: 12px; 
		margin-left: 30px!important; margin-left:-10px; 
		margin-top: 20px;}
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
				

   
        <div class="div_middle">

           <s:iterator value="datas" status="stuts">  
          	<div style="float:left;">
            <table style="width: 170px; font-size: 12px; margin-left: 10px; margin-top: 10px;" border="0">
                <tbody>
                    <tr >												
                        <td align="center" valign="middle" width="25%">
                            <div style="height:110px; width:120px; border:#999 2px solid;">
                            
                                <a href="${cxp }/manager/bot/pictureAction!imageProcess.action?id=<s:property value="id" />">
                                    <div style='margin-top:5px;'>		
                                         <img src="${cxp }/<s:property value="path" />85X85(crop)-<s:property value="filename" />"  style="border:#999 1px solid;" />
                                    </div>	
                                 </a>
                                 
                            </div>
                        </td>
                    </tr>
                    <tr> 
                        <td align="center" style="color:#666;" width="25%">文件大小:
                       <script language="JavaScript">
			<!--
                     	 var _K = 1024;
var _M = _K*1024;
function getNiceFileSize(value){
	if(value<_M){
		if(value<_K){
			document.write( value+'B');
  		}else{
			document.write( Math.ceil(value/_K)+'K');
		}

	}else{
		document.write( Math.ceil(100*value/_M)/100+'M');
	}

}
getNiceFileSize(<s:property value="fileSize" />);	
//-->
</script></td>
                    </tr>
                    <tr> 
                        <td align="center" style="color: gray; font-size:12px;" width="25%">  <s:date name="storeDate" format="yyyy-MM-dd HH:mm:ss" /></td>
                    </tr>
                    <tr> 
                        <td align="center"><a href="javascript:;" onclick="removeSelect(<s:property value="id" />);">删除</a></td>
                    </tr>
                </tbody>
             </table>
           </div>  
           	</s:iterator> 

        </div>  
  


             <table style="width: 90%; font-size: 12px; margin-left: 10px; margin-top: 10px;" border="0">
									<tr><td >
            <script language="JavaScript">
			<!--
			var pg = new showPages('pg');

			pg.pageSize = <s:property value="pageInfo.pageSize"/>;
			pg.firstPage = <s:property value="pageInfo.firstPage"/>;
			pg.lastPage = <s:property value="pageInfo.lastPage"/>;
			pg.prePage = <s:property value="pageInfo.prePage"/>;
			pg.nextPage = <s:property value="pageInfo.nextPage"/>;
			pg.pageNo = <s:property value="pageInfo.pageNo"/>;
			pg.totalPage = <s:property value="pageInfo.totalPage"/>;
			pg.totalCount = <s:property value="pageInfo.totalCount"/>;
			pg.argName = 'pageInfo.pageNo';
			pg.printHtml(2);
		

   			//-->
									</script>
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
<script type="text/javascript">
	$(function() {

		$(document).unblock();
	});
	  function removeSelect(id){
	
			var cs = id;
			
			var message = "您真的要删除吗？";
		
					 
					window.parent.parent.jConfirm(message, '操作确认', function(r) {
					
						if (r) {
								var param = {
									ids:cs
								}
				
								doPost("pictureAction!remove.action", param, function(data) {
									
											if (data.status) {
												window.location.reload();
												window.parent.parent.jAlert(data.message, "系统提示");
											}else{
											   
												window.parent.parent.jAlert(data.message, "系统提示");
											}
									});
						}
			});

		}

	</script>
</body>
</html>