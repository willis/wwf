<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/include/taglibs.jsp"%>
<%@ include file="/include/jquery.jsp"%>
<head>
<title>Multi Upload Demo</title>
<link type="text/css" rel="stylesheet" href="simple.css" />
<script type="text/javascript" src="${cxp}/swfupload/js/swfupload.js"></script>
<script type="text/javascript"
	src="${cxp}/swfupload/jsp/simple/handlers.js"></script>
<script type="text/javascript">
var swfu;
window.onload = function () {

    swfu = new SWFUpload({
        // Flash Settings
        flash_url : "${cxp}/swfupload/flash/swfupload.swf",
    
        // Backend settings
        preserve_relative_urls: true,                 // 保留相对路径不做转换
        upload_url: "${cxp}/uploadServlet", // servlet path
        // Flash file settings
        file_types : "*.*",
        file_types_description : "viva pdf文件上传",
        file_size_limit : "1000 MB",
        file_queue_limit : "1",
    
        // Event handler settings
        file_dialog_start_handler: fileDialogStart, // fired after selectFile is called.
        file_queued_handler : fileQueued,           // fired after file selection dialog close.
        file_queue_error_handler : fileQueueError,  // fired when file was not queued.
        file_dialog_complete_handler : fileDialogComplete,// fired when all files queued.
                 
        upload_progress_handler : uploadProgress,  // upload status
        upload_error_handler : uploadError,        // fired when upload error thrown
        upload_success_handler : uploadSuccess,    // fired when server return a 200 status
        upload_complete_handler : uploadComplete,  // fired at the end of an upload cycle
    
        // Button Settings
        button_image_url : "${cxp}/swfupload/image/btnUpload.png",
        button_placeholder_id : "btnPlaceHolder",
        button_width: 61,
        button_height: 22,
        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILE, // 单选
		use_query_string:true,
        // debug infor
        debug:false
    });
};
/**
 * 文件上传成功
 * @param file
 * @param serverData
 * @param response
 * @return
 */
function uploadSuccess(file, serverData, response){
	var colorStatus = document.getElementById("colorStatus");
	var percentTxt = document.getElementById("percentTxt");
	colorStatus.style.width = '100%';
	percentTxt.innerHTML = '100%';
	$("#uuid").attr("value",serverData);
	query(serverData);
	
}

/**
 * 开始上传
 * @return
 */
function startUpload(){
    swfu.setPostParams({"object_id": $("#uuid").val()});
	swfu.startUpload();
}
    </script>
</head>
<body id="loading">

	<div id="uploadContent">
		<input id="txtFileName" type="text" disabled="disabled" value=""
			style="width: 181px;" /> <span id="btnPlaceHolder"></span>
		<div id="uploadProgress">
			<div id="colorStatus"></div>
			<div id="percentTxt"></div>
		</div>
	</div>
	<input type="hidden" id="uuid" name="uuid" value="${ object_id}">
	<input id="btnStartUpload" type="button" value="开始上传"
		onclick="startUpload()" disabled="disabled"
		style="position: relative; left: 200px;" />
	<table class="table" style="width: 250px;">
		<thead>
			<tr>

				<th>名称</th>
				<th>大小</th>
				<th>操作</th>

			</tr>
		</thead>
		<tbody id="myTable">


		</tbody>
	</table>
	<script>

		 var myTable1 =  new MaxTable();
		 myTable1.initialize(
		  	{
		  		table:'myTable',
		  		loading:'loading',
		  		id:'id',
		  		queryUrl:'annexAction!annexList.action',
		  		headerColumns:
		  		[
		  		{id:'fileNames',renderer:fileRenderer},
		  		{id:'fileSize',renderer:getNiceFileSize},
		  		{id:'id',name:'操作',renderer:editRenderer}
		  		]
		  	}
		  )
		  
	      
	     function editRenderer(idValue,value){
	     	var txt="";
	     	txt+= " <a href='javascript:' onclick='javascript:editdelete("+value+");'>删除</a>"
	     	return txt;
	     }
	     function query(object_id){
	     	myTable1.page.totalRowNum = 0;
	     	myTable1.page.pageSize = 5;
	    	myTable1.onLoad({object_id:object_id});
	     } 	
	  
		 query($("#uuid").val());
		 function editdelete(value){
			 $.ajax({
		           type: "post",//使用get方法访问后台
		           dataType: "json",//返回json格式的数据
		           url: "annexAction!remove.action?id="+value,//要访问的后台地址
		          //complete :function(){$("#load").hide();},//AJAX请求完成时隐藏loading提示
		           success: function(data){//data为返回的数据，在这里做数据绑定
		           
		     				query($("#uuid").val());
							window.parent.jAlert(data.message, "系统提示");
		           },
		           error: function(data){
		           	     window.parent.jAlert("删除文件失败", "系统提示");
		           }
		      	});
		 }
		  function fileRenderer(idValue,value){
		 	var zh = value.match(/[^ -~]/g); 
		 	var fileName = value.length +(zh ? zh.length : 0);
			if((zh ? zh.length : 0) >10)
				 value= value.substring(0,10)+"...";
			else if(value.length>20)
				value= value.substring(0,20)+"...";
				 
	     	return value;
	     }
		 var _K = 1024;
		 var _M = _K*1024;
		function getNiceFileSize(idValue,value){
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
		</script>


</body>
</html>