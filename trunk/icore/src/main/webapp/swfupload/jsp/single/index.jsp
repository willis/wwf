<%@page language="java" contentType="text/html;charset=utf-8"%>
<% 
    String contextPath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<title>Classic Form Demo</title>
<link href="single.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/swfupload/js/swfupload.js"></script>
<script type="text/javascript" src="<%=contextPath%>/swfupload/js/swfupload.swfobject.js"></script>
<script type="text/javascript" src="<%=contextPath%>/swfupload/js/fileprogress.js"></script>
<script type="text/javascript" src="handlers.js"></script>
<script type="text/javascript">
var swfu;
window.onload = function () {
	swfu = new SWFUpload({
	    // Flash Settings
	    flash_url : "<%=contextPath%>/swfupload/flash/swfupload.swf",
	
	    // Backend settings
	    preserve_relative_urls: true,
	    upload_url: "<%=contextPath%>/uploadServlet", // servlet path
	             
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
	    button_image_url : "<%=contextPath%>/swfupload/image/btnUpload.png",
	    button_placeholder_id : "spanButtonPlaceholder",
	    button_width: 61,
	    button_height: 22,
	    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILE,
	             
	    custom_settings : {
	        progress_target : "fsUploadProgress",
	        upload_successful : false
	    },
	    debug:false
	});
};
    </script>
</head>
<body>
 
<div id="content">
    <h2>Classic Form Demo</h2>
    <form id="form1" action="" enctype="multipart/form-data" method="post">        
        <div class="fieldset">
            <span class="legend">Submit your Application</span>
            <table>
                <tr>
                    <td><label for="lastname">Last Name:</label></td>
                    <td><input id="lastname" name="lastname" type="text" style="width: 200px"/></td>
                </tr>
                <tr>
                    <td><label for="firstname">First Name:</label></td>
                    <td><input id="firstname" name="firstname" type="text" style="width: 200px"/></td>
                </tr>
                <tr>
                    <td><label for="txtFileName">Resume:</label></td>
                    <td>
                        <div>
                            <div>
                                <input id="txtFileName" name="txtFileName" type="text" disabled="disabled" style="border: solid 1px; background-color: #FFFFFF;" />
                                <span id="spanButtonPlaceholder"></span>(10 MB max)
                            </div>
                            <div id="fsUploadProgress" class="flash"></div>
                            <input id="hidFileID" name="hidFileID" type="hidden" value=""/>
                        </div>
                    </td>
                </tr>
            </table>
            <br />
            <input id="btnSubmit" type="submit" value="Submit Application"/>
        </div>
    </form>
</div>

</body>
</html>