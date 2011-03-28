<%@page language="java" contentType="text/html;charset=utf-8"%>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<title>Simple Upload Demo</title>
<link type="text/css" rel="stylesheet" href="simple.css"/>
<script type="text/javascript" src="<%=contextPath%>/swfupload/js/swfupload.js"></script>
<script type="text/javascript" src="<%=contextPath%>/swfupload/jsp/simple/handlers.js"></script>
<script type="text/javascript">
var swfu;
window.onload = function () {
    swfu = new SWFUpload({
        // Flash Settings
        flash_url : "<%=contextPath%>/swfupload/flash/swfupload.swf",
    
        // Backend settings
        preserve_relative_urls: true,                 // 保留相对路径不做转换
        upload_url: "<%=contextPath%>/uploadServlet", // servlet path
                 
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
        button_image_url : "<%=contextPath%>/swfupload/image/btnUpload.png",
        button_placeholder_id : "btnPlaceHolder",
        button_width: 61,
        button_height: 22,
        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILE, // 单选

        // debug infor
        debug:true
    });
};
    </script>
</head>
<body>

<div id="uploadContent">
    <input id="txtFileName" type="text" disabled="disabled" value="" style="width:151px;"/>
    <span id="btnPlaceHolder"></span>
    <div id="uploadProgress">
        <div id="colorStatus"></div>
        <div id="percentTxt"></div>
    </div>
</div>

<input id="btnStartUpload" type="button" value="开始上传" onclick="startUpload()" disabled="disabled" style="position:relative;left:200px;"/>

</body>
</html>