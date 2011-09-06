$(document).ready(function() {
			$('#myForm').ajaxForm({
						beforeSubmit : validateForm,
						clearForm : true,
						dataType : 'json',
						success : processJson,
						error : function(response) {
							if(response.responseText.indexOf('loginwindow')!=-1){
								parent.location.reload();
							}else{
								alert(response.responseText);
								//图层解锁
								$("#myForm").unblock();
							}
						}
					});
		});
/**
 * 表单验证
 */
function validateForm(formData, jqForm, options) {
	if (checkFormMe($('#myForm')[0])) {
		wait("myForm", "提交中,请稍候...");
		return true;
	}
	return false;
}
function checkFormMe(form) {
		return Validator.Validate(form,2);
		//return checkForm(form);
}

/**
回调
**/
function processJson(data) {

if(data.status == SUCCESS){

	window.parent.parent.jAlert(data.message, "系统提示");
									
	//图层解锁
	$("#myForm").unblock();
	
	if(window.parent.frames["0"].query){
		window.parent.frames["0"].query();
	}else{
		
	
	    window.parent.frames["0"].reload();
	;
	}
	window.parent.closeWindow();
}else{
	
	$("#errorMsg").html(data.message);
	//图层解锁
	$("#myForm").unblock();
}
}	