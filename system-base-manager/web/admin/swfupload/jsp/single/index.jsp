<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<title>Classic Form Demo</title>
<link href="${cxp}/swfupload/jsp/single/single.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${cxp}/swfupload/js/swfupload.js"></script>
<script type="text/javascript"
	src="${cxp}/swfupload/js/swfupload.swfobject.js"></script>
<script type="text/javascript"
	src="${cxp}/swfupload/js/fileprogress.js"></script>
<script type="text/javascript" src="${cxp}/swfupload/jsp/single/handlers.js"></script>
<script type="text/javascript">
var swfu;
window.onload = function () {
	swfu = new SWFUpload({
	    // Flash Settings
	    flash_url : "${cxp}/swfupload/flash/swfupload.swf",
	
	    // Backend settings
	    preserve_relative_urls: true,
	    upload_url: "${cxp}/uploadServlet", // servlet path
	             
	    // Flash file settings
	    file_types : "*.*",             // multi types like: "*.doc;*.wpd;*.pdf"
	    file_types_description : "viva pdf文件上传",
	    file_size_limit : "1000 MB",                
	    file_queue_limit : "1",
	
	    // Event handler settings
	    swfupload_loaded_handler : swfUploadLoaded,// fired after flash loaded.
	    file_dialog_start_handler: fileDialogStart,// fired after selectFile is called.
	    file_queued_handler : fileQueued,          // fired after file selection dialog close.
	    file_queue_error_handler : fileQueueError, // fired when file was not queued.
	    file_dialog_complete_handler : fileDialogComplete,// fired when all files queued.
	             
	    //upload_start_handler : uploadStart,   // I could do some client/JavaScript validation here, but I don't need to.
	    upload_progress_handler : uploadProgress,  // upload status
	    upload_error_handler : uploadError,        // fired when upload error thrown
	    upload_success_handler : uploadSuccess,    // fired when server return a 200 status
	    upload_complete_handler : uploadComplete,  // fired at the end of an upload cycle
	
	    // Button Settings
	    button_image_url : "${cxp}/swfupload/image/btnUpload.png",
	    button_placeholder_id : "spanButtonPlaceholder",
	    button_width: 61,
	    button_height: 22,
	    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILE,
	             
	    custom_settings : {
	        progress_target : "fsUploadProgress",
	        upload_successful : true
	    },
	    debug : false
	});
};

   function getAnnexList(object_id){
   		var url = "annexAction!getAnnexByObjectId.action?object_id="+object_id;
   		$.ajax({
	        type: "post",//使用get方法访问后台
	        dataType: "json",//返回json格式的数据
	        url: url,//要访问的后台地址
	        complete :function(){},//AJAX请求完成时隐藏loading提示
	        success: function(data){//data为返回的数据，在这里做数据绑定
	        	$("#dataTable").show();
				$("#uploadTable").hide();
	        	$("#annexList").append("<tr>"
										+"<td>"+data.fileName+"</td>"
										+"<td>"+getNiceFileSize(data.fileSize)+"</td>"
										+"<td> <a class='remove' href='javascript:' onclick='javascript:editdelete("+data.id+");'>删除</a></td>"
								     	+"</tr>").find("a.remove").click(function(){
								         $(this).parent().parent().remove();
				});
	        	
	        },
	        error: function(data){
	 
	        }
	     });
   }
    var _K = 1024;
    var _M = _K*1024;
    function getNiceFileSize(value){
			if(value<_M){
				if(value<_K){
					return value+'B';
		   		}else{
					return Math.ceil(value/_K)+'K';
				}
		
			}else{
				return Math.ceil(100*value/_M)/100+'M';
			}
	
	}
	function editdelete(value){
			 $.ajax({
		           type: "post",//使用get方法访问后台
		           dataType: "json",//返回json格式的数据
		           url: "annexAction!remove.action?id="+value,//要访问的后台地址
		          //complete :function(){$("#load").hide();},//AJAX请求完成时隐藏loading提示
		           success: function(data){//data为返回的数据，在这里做数据绑定
							window.parent.jAlert(data.message, "系统提示");
							window.location="${cxp}/swfupload/jsp/single/index.jsp";
							
		           },
		           error: function(data){
		           	     window.parent.jAlert("删除文件失败", "系统提示");
		           }
		      	});
		 }
		 //解除绑定
		$(function(){
			$(document).unblock();
		    
		});
    </script>
</head>
<body id="mybody">
		<script>wait("mybody", "页面加载中,请稍候...");</script>
				<table id="uploadTable">
					
					<tr>
						<td>
							<div>
							
									<input id="txtFileName" name="txtFileName" type="text"
										disabled="disabled"
										style="border: solid 1px; background-color: #FFFFFF;" /> <span
										id="spanButtonPlaceholder"></span><input id="btnSubmit" type="submit"
					value=" 上 传 " />
						
								<div id="fsUploadProgress" class="flash"></div>
								<input id="hidFileID" name="hidFileID" type="hidden" value="" />
							</div></td>
					</tr>
				</table>
				 <table class="table" id="dataTable" style="width: 250px;display:none;" >
		<thead>
			<tr>
 
				<th>名称</th>
				<th>大小</th>
				<th>操作</th>
 
			</tr>
		</thead>
		<tbody id="annexList">
 
 
		</tbody>
	</table>

</body>
</html>