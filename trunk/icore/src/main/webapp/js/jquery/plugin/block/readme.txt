http://www.malsup.com/jquery/block/#download



详细说明请参考 http://www.malsup.com/jquery/block/#page
BlockUI
 1,基于jquery进行操作,因此需要进行jquery的导入, BlockUI requires jQuery v1.2.3 or later!
 2,导入:<script type="text/javascript" src="<%=path%>/js/jquery/plugin/block/jquery.blockUI.js" charset="utf-8"></script>
 3,注册当 ajax结束后,就不进行block的操作了
   $().ajaxStop($.unblockUI);
 4,进行提交的过程中,提交之前进行展现
 $('#layer1_form').ajaxForm({
		// target: '#content',
		dataType : 'json',
		beforeSubmit : validateInputForm,

		success : processJson
			// success: function() {$("#layer1").hide();}
		});

这里在 validateInputForm 中进行定义


5,validateInputForm中的几种展现方式
function validateInputForm(formData, jqForm, options) {
	
	
	// 校验完成后,显示等待对话框
	//方式1, 全屏的对话框
	// $.blockUI();
	//方式2, 显示进度条,效果不好待debug
	// $("#spaceused1").progressBar(90);
	// $.blockUI({
	// message : $('#layer1_content')
	// });
	//方式3,在这个编辑层上 锁定
	$('#layer1').block({
				message : '正在提交,请稍候...',
				css : {
					width : '100%'
					// ,border : '1px solid #a00'
				}

			});

}




6,注意,如果界面中已经导入了 js/gt-grid/gt_common.js 包,就不需要进行上面3-5步的定义了,
只需要在需要进行 锁定窗口的地方 调用 :

	wait('layer1');

	即可,layer1 为需要锁定的窗口的id
