/*
	A simple class for displaying file information and progress
	Note: This is a demonstration only and not part of SWFUpload.
	Note: Some have had problems adapting this class in IE7. It may not be suitable for your application.
*/

// Constructor
// file is a SWFUpload file object
// targetID is the HTML element id attribute that the FileProgress HTML structure will be added to.
// Instantiating a new FileProgress object with an existing file will reuse/update the existing DOM elements
/*
*	选择文件后执行的方法.
*	@param file 选择的文件对象，已经经过flash控件处理过了
*	@param fileListID WEB界面显示已选文件信息的位置区域
*	@param swfUploadInstance flash控件对象
*	@reference [handler.js].fileQueued(file)
*	@reference [handler.js].fileQueueError(file)
*/
var totalSize = 0;
function FileProgress(file,fileListID,swfUploadInstance) {
	totalSize+=file.size;
	
	var listingfiles = document.getElementById(fileListID);	
	
	var tr = listingfiles.insertRow(listingfiles.rows.length);
	tr.id=file.id;
	
	var flag = tr.insertCell(0); 
	flag.className='flag2';
	flag.id = "cells_"+file.id;
	
	var fileName = tr.insertCell(1);
	fileName.className = 'nameTd';
	fileName.innerHTML=file.name;
	
	var progressTD = tr.insertCell(2); 
	progressTD.className='progressTD';
	
	progressTD.innerHTML='<span id="' + file.id +'progress" class="progressBar"></span><span>'+getNiceFileSize(file.size)+'</span>';
	
	var del = tr.insertCell(3); 
	del.className='delectBtn';
	del.id=file.id + 'deleteGif'; 
	del.innerHTML='<a href="javascript:swfu.cancelFile(\'' + file.id + '\')"><img src="../../images/del_ico.gif" alt="清除" width="13" height="13" border="0" /></a>';
	//showInfo(file, listingfiles.rows.length);
}
/**
	顯示信息
**/
function showInfo(file,queuelength){
	var fileCount = document.getElementById("fileCount");
	fileCount.innerText=queuelength;
	var fileTotleSize = document.getElementById("fileTotleSize");
	fileTotleSize.innerText = getNiceFileSize(totalSize);
}
SWFUpload.prototype.cancelFile = function(file_id) {

	var listingfiles = document.getElementById("fileList");
	
	var rows = listingfiles.rows;
	for(var i=rows.length-1;i>=0;i--){
		if(file_id==rows[i].id){
			listingfiles.deleteRow(i);
			//扣除Filesize 
			//totalSize = totalSize -file.size;
			break;
		}
	}	
	//showInfo(file, listingfiles.rows.length);
};
/**
清除所有上傳檔案
**/
SWFUpload.prototype.cancelQueue = function () {
	//isCancelAll  = true; 
	totalSize = 0;
	//swfu.cancelQueue();	
	clearhQueue();
}
/**
	處理UI，刪除表格的所有行
**/
function  clearhQueue(){
	var listingfiles = document.getElementById("fileList");
	var rowLength = listingfiles.rows.length;
	while(rowLength>0){
		listingfiles.deleteRow(0);
		rowLength--;
	}
}
FileProgress.prototype.setProgress = function (percentage) {
	//this.fileProgressElement.className = "progressContainer green";
	//this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
	//this.fileProgressElement.childNodes[3].style.width = percentage + "%";
};
FileProgress.prototype.setComplete = function () {
	//this.fileProgressElement.className = "progressContainer blue";
	//this.fileProgressElement.childNodes[3].className = "progressBarComplete";
	//this.fileProgressElement.childNodes[3].style.width = "";
	
	/* comment by stephen
	var oSelf = this;
	setTimeout(function () {
		oSelf.disappear();
	}, 10000);
	*/
};
FileProgress.prototype.setError = function () {
	//this.fileProgressElement.className = "progressContainer red";
	//this.fileProgressElement.childNodes[3].className = "progressBarError";
	//this.fileProgressElement.childNodes[3].style.width = "";
	
	/* comment by stephen
	var oSelf = this;
	setTimeout(function () {
		oSelf.disappear();
	}, 5000);
	*/
};
FileProgress.prototype.setCancelled = function () {
	//this.fileProgressElement.className = "progressContainer";
	//this.fileProgressElement.childNodes[3].className = "progressBarError";
	//this.fileProgressElement.childNodes[3].style.width = "";
	
	/* comment by stephen
	var oSelf = this;
	setTimeout(function () {
		oSelf.disappear();
	}, 2000);
	*/
};
FileProgress.prototype.setStatus = function (status) {
	this.fileProgressElement.childNodes[2].innerHTML = status;
};

// Show/Hide the cancel button
FileProgress.prototype.toggleCancel = function (show, swfUploadInstance) {
	/**
	this.fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
	if (swfUploadInstance) {
		var fileID = this.fileProgressID;
		this.fileProgressElement.childNodes[0].onclick = function () {
			swfUploadInstance.cancelUpload(fileID);
			return false;
		};
	}**/
};

// Fades out and clips away the FileProgress box.
FileProgress.prototype.disappear = function () {

	var reduceOpacityBy = 15;
	var reduceHeightBy = 4;
	var rate = 30;	// 15 fps

	if (this.opacity > 0) {
		this.opacity -= reduceOpacityBy;
		if (this.opacity < 0) {
			this.opacity = 0;
		}

		if (this.fileProgressWrapper.filters) {
			try {
				this.fileProgressWrapper.filters.item("DXImageTransform.Microsoft.Alpha").opacity = this.opacity;
			} catch (e) {
				// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
				this.fileProgressWrapper.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=" + this.opacity + ")";
			}
		} else {
			this.fileProgressWrapper.style.opacity = this.opacity / 100;
		}
	}

	if (this.height > 0) {
		this.height -= reduceHeightBy;
		if (this.height < 0) {
			this.height = 0;
		}

		this.fileProgressWrapper.style.height = this.height + "px";
	}

	if (this.height > 0 || this.opacity > 0) {
		var oSelf = this;
		setTimeout(function () {
			oSelf.disappear();
		}, rate);
	} else {
		this.fileProgressWrapper.style.display = "none";
	}
};