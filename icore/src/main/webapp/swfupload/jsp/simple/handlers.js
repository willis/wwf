/**
 * 打开文件选择窗口前触发
 * @return
 */
function fileDialogStart(){
	var txtFileName = document.getElementById("txtFileName");
	txtFileName.value = "";
	swfu.cancelUpload();
}
/**
 * 文件进入上传队列时触发
 * @param file
 * @return
 */
function fileQueued(file){
	var txtFileName = document.getElementById("txtFileName");
	txtFileName.value = file.name;
}
/**
 * 文件进入上传队列时发生异常
 * @param file
 * @param errorCode
 * @param message
 * @return
 */
function fileQueueError(file, errorCode, message){
	try {
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
			alert("上传文件个数超出限制");
			return;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			alert("所选文件大小超出限制.");
			return;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			alert("所选文件不能为空.");
			return;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			alert("所选文件类型不符.");
			return;
		default:
			alert("文件进入上传队列时发生异常，请稍后重试.");
			return;
		}
	} catch (e) {
	}
}
/**
 * 文件全部进入队列时触发
 * @param numberFileSelected
 * @param numberFileQueued
 * @param totalFileQueued
 * @return
 */
function fileDialogComplete(numberFileSelected, numberFileQueued,
		totalFileQueued){
	// 更新进度条
	var uploadProgress = document.getElementById("uploadProgress");
	var colorStatus = document.getElementById("colorStatus");
	var percentTxt = document.getElementById("percentTxt");
	colorStatus.style.width = '0%';
	percentTxt.innerHTML = '0%';
	percentTxt.style.color = 'black';
	uploadProgress.style.display = "block";
	document.getElementById("btnStartUpload").disabled = false;
}
/**
 * 文件上传进度
 * @param file
 * @param bytesComplete
 * @param bytesTotal
 * @return
 */
function uploadProgress(file, bytesComplete, bytesTotal){
	var percent = Math.ceil((bytesComplete/bytesTotal) * 100);
	//
	var colorStatus = document.getElementById("colorStatus");
	var percentTxt = document.getElementById("percentTxt");
	colorStatus.style.width = percent + '%';
	percentTxt.innerHTML = percent + '%';
}
/**
 * 文件上传时发生异常
 * @param file
 * @param errorCode
 * @param message
 * @return
 */
function uploadError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.UPLOAD_ERROR.FILE_CANCELLED) {
			// 取消上传
			return;
		}
		
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
			alert("缺少配置项: upload_url");
			return;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			alert("上传文件个数超过限制.");
			return;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			return;
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			alert("上传异常: " + message);
			return;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			alert("上传失败: [fileName: " + file.name 
					+ ",fileSize:" + file.size 
					+ ",message:" + message + "]");
			return;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			alert("Server (IO) Error");
			return;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			alert("Security Error");
			return;
		default:
			alert("文件上传时发生异常，请稍后重试.");
			return;
		}
	} catch (ex) {
	} finally{
		var percentTxt = document.getElementById("percentTxt");
		percentTxt.style.color = 'red';
		percentTxt.innerHTML = 'Error!';
	}
}
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
	alert("server back: " + serverData);
}
/**
 * 文件上传完成
 * @param file
 * @return
 */
function uploadComplete(file){
	// 上传完成
	document.getElementById("btnStartUpload").disabled = true;
}
// --------------------------------------------//
/**
 * 开始上传
 * @return
 */
function startUpload(){
	swfu.startUpload();
}