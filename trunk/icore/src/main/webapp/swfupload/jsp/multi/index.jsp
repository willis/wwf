<%@page language="java" contentType="text/html;charset=utf-8"%>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<title>Multi Upload Demo</title>
<link type="text/css" rel="stylesheet" href="multi.css"/>
<script type="text/javascript" src="<%=contextPath%>/swfupload/js/swfupload.js"></script>
<script type="text/javascript" src="<%=contextPath%>/swfupload/js/swfupload.queue.js"></script>
<script type="text/javascript" src="<%=contextPath%>/swfupload/js/fileprogress.js"></script>
<script type="text/javascript" src="handlers.js"></script>
<script type="text/javascript">
var swfu;
window.onload = function(){
    var settings = {
    	preserve_relative_urls:true,
        flash_url:"<%=contextPath%>/swfupload/flash/swfupload.swf",
        upload_url:"<%=contextPath%>/uploadServlet",         // servlet path
        file_size_limit:"1000 MB",
        file_types:"*.*",
        file_types_description:"viva pdf文件上传",
        file_upload_limit:10,                // 允许上传的文件个数
        file_queue_limit:10,                 // 上传文件的队列大小
        custom_settings:{
            progressTarget:"uploadProgress",
            cancelButtonId:"btnCancel"
        },
        debug:true,
        // button settings
        button_width:"80",
        button_height:"29",
        button_image_url:"<%=contextPath%>/swfupload/image/btnBackground.png",
        button_text:'<span class="theFont">选择文件...</span>',
        button_text_style:".theFont{font-size:12px;font-weight:bold;}",
        button_text_left_padding:12,
        button_text_top_padding:3,
        button_placeholder_id:"btnPlaceHolder",
        // The event handler funtion
        file_queued_handler:fileQueued,
        file_queue_error_handler:fileQueueError,
        file_dialog_complete_handler:fileDialogComplete,
        upload_start_handler:uploadStart,
        upload_progress_handler:uploadProgress,
        upload_error_handler:uploadError,
        upload_success_handler:uploadSuccess,
        upload_complete_handler:uploadComplete,
        queue_complete_handler:queueComplete
    };
    swfu = new SWFUpload(settings);
};
</script>
</head>
<body>
    <div id="uploadProgress" class="fieldset">
        <span class="legend">Upload Queue</span>
    </div>
    <div id="divStatus">0 Files Uploaded</div>
    <div>
        <span id="btnPlaceHolder"></span>
        <input id="btnCancel" type="button" value="取消上传" onclick="swfu.cancelQueue()" disabled="disabled"/>
    </div>
</body>
</html>