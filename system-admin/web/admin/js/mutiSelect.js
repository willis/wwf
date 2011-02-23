/**
 * @author ljNing
 * @description 初始化选择的项的多选框
 */
function initSelection() {
	//添加左边所选项到右边所选
	$("a[id^='add']") .click(function() {
		var thisId = $(this).attr("id")+"";
		var num = thisId.substring(3,thisId.length);

		if ($("#leftSelection"+num+" option:selected").length > 0) {
			$("#leftSelection"+num+" option:selected").each(function() {//循环左边中选中的项
				var leftValue = $(this).val();
				var flag = true;
				$("#rightSelection"+num+" option").each(function(){//循环右边已经选择的项,查询当前选择的和已经选择的是否有重复
					if(leftValue+"" == $(this).val()+"") {
						flag = false;
					}
				});
				if(flag) {//如果不重复则添加
					var item = new Option($(this).text(),$(this).val());
					$("#rightSelection"+num)[0].options.add(item);
				}
			})
		} else {
			alert("请选择要添加的项！");
		}
	});
	
	//双击选中
	$("select[id^='leftSelection']").dblclick(function (){
		var thisId = $(this).attr("id")+"";
		var num = thisId.substring(13,thisId.length);
		
		$("#leftSelection"+num+" option:selected").each(function (){
				var leftValue = $(this).val();
				var flag = true;
				$("#rightSelection"+num+" option").each(function(){//循环右边已经选择的项,查询当前选择的和已经选择的是否有重复
					if(leftValue+"" == $(this).val()+"") {
						flag = false;
					}
				});
				if(flag) {//如果不重复则添加
					var item = new Option($(this).text(),$(this).val());
					$(item).attr("title",$(this).text());
					$("#rightSelection"+num)[0].options.add(item);
				}
		});
	});
	
	//双击删除
	$("select[id^='rightSelection']").dblclick(function (){
		var thisId = $(this).attr("id")+"";
		var num = thisId.substring(14,thisId.length);
		if ($("#rightSelection"+num+" option:selected").length > 0) {
			$("#rightSelection"+num+" option:selected").each(function() {
				$(this).remove();
			})
		} else {
			
		}
	});
	
	//添加全部右边所选
	$("a[id^='fullAdd']") .click(function() {
		var thisId = $(this).attr("id")+"";
		var num = thisId.substring(7,thisId.length);
		
		if ($("#leftSelection"+num+" option").length > 0) {
			$("#leftSelection"+num+" option").each(function() {
				var leftValue = $(this).val();
				var flag = true;
				$("#rightSelection"+num+" option").each(function(){//循环右边已经选择的项,查询当前选择的和已经选择的是否有重复
					if(leftValue+"" == $(this).val()+"") {
						flag = false;
					}
				});
				if(flag) {//如果不重复则添加
					var item = new Option($(this).text(),$(this).val());
					$(item).attr("title",$(this).text());
					$("#rightSelection"+num)[0].options.add(item);
				}
			})
			
		} else {
			alert("请选择要添加的项！");
		}
	});
	//删除所选
	$("a[id^='remove']").click(function() {
		var thisId = $(this).attr("id")+"";
		var num = thisId.substring(6,thisId.length);
		
		if ($("#rightSelection"+num+" option:selected").length > 0) {
			$("#rightSelection"+num+" option:selected").each(function() {
				$(this).remove();
			})
		} else {
			alert("请选择要删除的项！");
		}
	});
	//删除全部
	$("a[id^='fullRemove']").click(function() {
		var thisId = $(this).attr("id")+"";
		var num = thisId.substring(10,thisId.length);
		if ($("#rightSelection"+num+" option").length > 0) {
			$("#rightSelection"+num+" option").each(function() {
				$(this).remove();
			})
		} else {
			alert("请选择要删除的项！");
		}
	})
}