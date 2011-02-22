/**
 * 页面中的随机数的值
 * 
 * @type Number
 */
var SYS_RANDOM_NEXT_ID = 0;
var contextPath = vPath;
var SUCCESS = 1;//成功
var FAILURE = 0;//失败
// html/images/enroImages/ajax-loader.gif
var _global_loading_img = contextPath+"/images/load/loadingIcon.gif";
var loadDateImage = "<img src=\"" + _global_loading_img + "\" border='0'/>";

var txt_querying = "&nbsp;&nbsp;" + loadDateImage + "&nbsp;数据查询中...";

/**
 * 方法:Array.removeElement(index) 功能:删除数组元素. 参数:index删除元素的下标. 返回:在原数组上修改数组
 */
Array.prototype.removeElement = function(index) {
	if (isNaN(index) || index > this.length) {
		return false;
	}
	for (var i = 0, n = 0; i < this.length; i++) {
		if (this[i] != this[index]) {
			this[n++] = this[i];
		}
	}
	this.length -= 1
}
/**
 * 打开弹开层
 * @param {} url
 * @param {} title
 * @param {} height
 * @param {} width
 */
function showWindow(url,title,height,width){
	url+=(url.indexOf("?")==-1)?"?":"&";
	url+="KeepThis=true&TB_iframe=true&height="+height+"&width="+width;	
	tb_show(title, url)
}
function closeWindow(){

	tb_remove();
}

/**
 * 如果需要启用 禁用 鼠标右键和F5的属性功能,请在 应用本js之前定义:
 * 
 * <script type="text/javascript"><!--
 * 
 * var isDisabledKeyDown=true;
 * 
 * var isDisabledContextMenu=true; //-->
 * 
 * 
 * </script>
 */
$(document).ready(function() {
			initUnblockUi();
			initDisableKeyDown();
			initDisableContextMenu();
		});
/** 年月日的日期格式化:yyyy-MM-dd* */
var DATE_FORMATE_YMD = "yyyy-MM-dd";
/** 年月日s时间的日期格式化:yyyy-MM-dd hh:mm* */
var DATE_FORMATE_YMD_HM = DATE_FORMATE_YMD + " hh:mm";
/** 年月日s时间分秒的日期格式化:yyyy-MM-dd hh:mm:ss* */
var DATE_FORMATE_YMD_HMS = DATE_FORMATE_YMD_HM + ":ss";
var json__m = {
	'\b' : '\\b',
	'\t' : '\\t',
	'\n' : '\\n',
	'\f' : '\\f',
	'\r' : '\\r',
	'"' : '\\"',
	'\\' : '\\\\'
};
/**
 * 注册当 ajax结束后,就不进行block了
 */
function initUnblockUi() {

	if ($.unblockUI) {
		// 注册当 ajax结束后,就不进行block了
		$().ajaxStop($.unblockUI);
	} else {

	}
}
/**
 * 
 * <code>
 IE中：
 document.body.clientWidth ==》 BODY对象宽度
 document.body.clientHeight ==》 BODY对象高度
 document.documentElement.clientWidth ==》 可见区域宽度
 document.documentElement.clientHeight ==》 可见区域高度
 FireFox中：
 document.body.clientWidth ==》 BODY对象宽度
 document.body.clientHeight ==》 BODY对象高度
 document.documentElement.clientWidth ==》 可见区域宽度
 document.documentElement.clientHeight ==》 可见区域高度

 Opera中：
 document.body.clientWidth ==》 可见区域宽度
 document.body.clientHeight ==》 可见区域高度
 document.documentElement.clientWidth ==》 页面对象宽度（即BODY对象宽度加上Margin宽）
 document.documentElement.clientHeight ==》 页面对象高度（即BODY对象高度加上Margin高）
 没有定义W3C的标准，则
 IE为：
 document.documentElement.clientWidth ==》 0
 document.documentElement.clientHeight ==》 0
 FireFox为：
 document.documentElement.clientWidth ==》 页面对象宽度（即BODY对象宽度加上Margin宽）document.documentElement.clientHeight ==》 页面对象高度（即BODY对象高度加上Margin高）
 Opera为：
 document.documentElement.clientWidth ==》 页面对象宽度（即BODY对象宽度加上Margin宽）document.documentElement.clientHeight ==》 页面对象高度（即BODY对象高度加上Margin高）
 www_cnblogs_com__jswidth

 网页可见区域宽： document.body.clientWidth
 网页可见区域高： document.body.clientHeight
 网页可见区域宽： document.body.offsetWidth (包括边线的宽)
 网页可见区域高： document.body.offsetHeight (包括边线的高)
 网页正文全文宽： document.body.scrollWidth
 网页正文全文高： document.body.scrollHeight
 网页被卷去的高： document.body.scrollTop
 网页被卷去的左： document.body.scrollLeft
 网页正文部分上： window.screenTop
 网页正文部分左： window.screenLeft
 屏幕分辨率的高： window.screen.height
 屏幕分辨率的宽： window.screen.width
 屏幕可用工作区高度： window.screen.availHeight
 屏幕可用工作区宽度： window.screen.availWidth

 网页可见区域宽： document.body.clientWidth
 网页可见区域高： document.body.clientHeight
 网页可见区域宽： document.body.offsetWidth (包括边线的宽)
 网页可见区域高： document.body.offsetHeight (包括边线的高)
 网页正文全文宽： document.body.scrollWidth
 网页正文全文高： document.body.scrollHeight
 网页被卷去的高： document.body.scrollTop
 网页被卷去的左： document.body.scrollLeft
 网页正文部分上： window.screenTop
 网页正文部分左： window.screenLeft
 屏幕分辨率的高： window.screen.height
 屏幕分辨率的宽： window.screen.width
 屏幕可用工作区高度： window.screen.availHeight
 屏幕可用工作区宽度： window.screen.availWidth
 网页可见区域宽： document.body.clientWidth
 网页可见区域高： document.body.clientHeight
 网页可见区域宽： document.body.offsetWidth (包括边线的宽)
 网页可见区域高： document.body.offsetHeight (包括边线的高)
 网页正文全文宽： document.body.scrollWidth
 网页正文全文高： document.body.scrollHeight
 网页被卷去的高： document.body.scrollTop
 网页被卷去的左： document.body.scrollLeft
 网页正文部分上： window.screenTop
 网页正文部分左： window.screenLeft
 屏幕分辨率的高： window.screen.height
 屏幕分辨率的宽： window.screen.width
 屏幕可用工作区高度： window.screen.availHeight
 屏幕可用工作区宽度： window.screen.availWidth

 HTML精确定位:scrollLeft,scrollWidth,clientWidth,offsetWidth
 scrollHeight: 获取对象的滚动高度。
 scrollLeft:设置或获取位于对象左边界和窗口中目前可见内容的最左端之间的距离
 scrollTop:设置或获取位于对象最顶端和窗口中可见内容的最顶端之间的距离
 scrollWidth:获取对象的滚动宽度
 offsetHeight:获取对象相对于版面或由父坐标 offsetParent 属性指定的父坐标的高度
 offsetLeft:获取对象相对于版面或由 offsetParent 属性指定的父坐标的计算左侧位置
 offsetTop:获取对象相对于版面或由 offsetTop 属性指定的父坐标的计算顶端位置
 event.clientX 相对文档的水平座标
 event.clientY 相对文档的垂直座标
 event.offsetX 相对容器的水平坐标
 event.offsetY 相对容器的垂直坐标
 document.documentElement.scrollTop 垂直方向滚动的值
 event.clientX+document.documentElement.scrollTop 相对文档的水平座标+垂直方向滚动的量

 实现代码


 《 !DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"》

 * </code>
 * 
 */
function getHeight() {

}
// 
/**
 * 注册禁止刷新等关键键的事件,
 */
function initDisableKeyDown() {
	// 判断这个变量是否定义了

	var type = typeof(isDisabledKeyDown);
	if (type == "object" || type == 'boolean') {

		if (isDisabledKeyDown && isDisabledKeyDown == true) {
			// 注册键盘点击事件
			document.onkeydown = common_onKeyDown;// ie7不管用
			// document.onkeyup = common_onKeyDown;
			// document.onkeypress = common_onKeyDown;

			// $(document).bind("keypress", common_onKeyDown);

		}
	} else {
		// alert("没定义");
	}

}
// 禁止刷新，回退
/**
 * 0X后面的部分是 十进制表示 A=0X65 65 ; B=0X66 66 ;C=0X67 D=0X68 E=0X69 F=0X70 G=0X71
 * H=0X72 I=0X73 J=0X74 K=0X75 L=0X76 M=0X77 N=0X78 O=0X79 P=0X80 Q=0X81 R=0X82
 * S=0X83 T=0X84 U=0X85 V=0X86 W=0X87 X=0X88 Y=0X89 Z=0X90
 * 
 * 0=0X48 1=0X49 2=0X50 3=0X51 4=0X52 5=0X53 6=0X54 7=0X55 8=0X56 9=0X57
 * 
 * ESC=0X1B 27 CTRL=0X11 17 SHIFT=0X10 16 ENTER=0XD（用十进制就是13）
 * 
 * CAPSLOCK=20
 * 
 * 
 * ←=37 ↑=38 →=39 ↓=40
 * 
 * F1键=112 F2键=113 F3键=114 F4键=115 F5键=116 F6键=117 F7键=118
 * 
 * F8键=119 F9键=120 F11键=122 F12键=123 退格删除键=8 TAB键=40
 * 
 * @param {}
 *            event
 * @return {Boolean}
 */
function common_onKeyDown(event) {
	var isIe = false;
	if ($.browser.msie) {
		event = window.event;
		isIe = true;

		// alert('是ie');
	} else {// if($.browser.mozilla )
		// 对应非ie浏览器,重新重新定义
		event = event;
		event.srcElement = event.target;

		// alert('不是ie');
	}

	/**
	 * 8=退格 78=N 82=R 116=F5
	 */
	if ((event.altKey)
			|| ((event.keyCode == 8) && (event.srcElement.type != "text"
					&& event.srcElement.type != "textarea" && event.srcElement.type != "password"))
			|| ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)))
			|| (event.keyCode == 116)) {

		if (event.srcElement) {
			event.returnValue = false;

			if (isIe) {
				// 对ie起作用
				event.keyCode = 0;

				window.event.returnvalue = false;
			}
		}

		if (event.target) {
			event.preventDefault();
			return false;
		} else {
			if (event.returnValue == false) {
				return false;
			}
		}

	}
}

/**
 * 初始化 禁止 鼠标右键
 */
function initDisableContextMenu() {
	// 判断这个变量是否定义了
	var type = typeof(isDisabledContextMenu);
	if (type == "object" || type == 'boolean') {

		if (isDisabledContextMenu && isDisabledContextMenu == true) {
			// 注册 事件
			document.oncontextmenu = common_disable_contextMenu;
		}
	} else {
		// alert("没定义");
	}

}
/**
 * 这个是禁用鼠标右键
 * 
 * @return {Boolean}
 */
function common_disable_contextMenu() {
	return false;
}

/**
 * 注意: 此block需要ajaxStop的时候才会关闭,因此需要进行界面初始化的时候initUnblockUi();操作<br>
 * 调用此控件之前需要进行 页面注册 弹出等待对话框
 * 
 * 解除锁定 : $('#layer1').unblock();
 * 
 * @param {String}
 *            componentId 这个对话框需要锁定在的 目的控件的id
 */
function wait(componentObjOrId, txt) {
	if (ky.util.isEmpty(txt)) {
		txt = '正在提交,请稍候...';
	}
	var comp;
	if (typeof componentObjOrId === 'string') {
		comp = getS(componentObjOrId);
	} else {
		comp = componentObjOrId;
	}

	comp.block({
				message : txt,
				css : {
					width : '70%'
					// ,border : '1px solid #a00'
				}
			});

}
/**
 * 删除元素的 disabed 属性
 * 
 * @param {}
 *            disableElementIds,字符的数组
 */
function removeAttr(disableElementIds) {
	if (disableElementIds) {
		for (var i = disableElementIds.length - 1; i >= 0; i--) {
			// 从数组中删除
			var shifted = disableElementIds.pop();

			var tmp = getS(shifted);
			if (tmp)
				tmp.removeAttr("disabled");

			// alert(shifted);
		}
	}
}

/**
 * 得到 这个 select 元素 选取的 值 <br>
 * 选取的文本的值 var txt = $("#abc option[selected]").text();<br>
 * 
 * 选取的 option 的value var v = $("#abc option[selected]").val();
 * 
 * @param {String}
 *            elementId 元素的id
 */
function getSelectVal(elementId) {
	var obj = getS(elementId);

	if (obj) {
		return obj.val();
	}

	return '';

}

/**
 * 得到控件显示的 内容
 * 
 * @param {}
 *            elementId
 * @return {String}
 */
function getHtml(elementId) {
	var obj = getS(elementId);

	if (obj) {
		return obj.html();
	}

	return '';
}

/**
 * 给元素 设置 disabled 属性
 * 
 * @param {String}
 *            或者 array disableElementIds 元素的id
 */
function addDisabledAttr(disableElementIds) {
	if (disableElementIds) {
		if (isArray(disableElementIds)) {
			for (var i = disableElementIds.length - 1; i >= 0; i--) {
				// 从数组中删除
				var shifted = disableElementIds.pop();
				addDisabledAttrToElement(shifted);

				// alert(shifted);
			}
		} else if (isString(disableElementIds)) {
			addDisabledAttrToElement(disableElementIds);
		}
	}
}
/**
 * 给元素对象设置 disabled 属性
 * 
 * @param {}
 *            id
 */
function addDisabledAttrToElement(id) {
	var tmp = getS(id);
	if (tmp)
		tmp.attr("disabled", "disabled");
}
/**
 * 控制 Input 对象 的 disabled
 * 
 * @param {String}
 *            elementId 元素的id
 * @param {boolean,String}
 *            boolanValue true=设置disabled属性, false=删除disabled属性
 * 
 * 
 */
function disabledElement(elementId, boolanValue) {
	var tmp = getS(elementId);
	if (tmp) {
		var d = "disabled";
		// tmp.attr("disabled", "disabled");

		if ((typeof boolanValue) == 'boolean') {
			if (boolanValue == true) {
				tmp.attr(d, boolanValue);
			} else {
				tmp.removeAttr(d);
			}
		}

	}
}
/**
 * 控制 Input 对象 的 disabled
 * 
 * @param {String}
 *            elementName 元素的名字
 * @param {boolean,String}
 *            boolanValue true=设置disabled属性, false=删除disabled属性
 * 
 * 也可以是字符串 值为 :disabled 或者 ""
 */
function disableInputByName(elementName, boolanValue) {
	changeInputElementAttrByName('disabled', elementName, boolanValue);

}
/**
 * 改变 input 类型的 对象的 attr 属性,
 * 
 * @param {String}
 *            attrName 包括:enabled disabled checked selected
 * @param {String}
 *            elementName 元素的name,而不是id
 * @param {boolean}
 *            boolanValue boolean 类型的值
 */
function changeInputElementAttrByName(attrName, elementName, boolanValue) {
	if (ky.util.isEmpty(attrName))
		return;
	if (ky.util.isEmpty(elementName))
		return;
	if (!boolanValue) {
		boolanValue = false;
	}
	$("input[name=" + elementName + "]").each(function() {
				if (isString(boolanValue)) {
					$(this).attr(attrName, boolanValue);
				} else {
					if (boolanValue == true) {
						$(this).attr(attrName, boolanValue);
					} else {
						$(this).removeAttr(attrName);
					}
				}
			})
}
/**
 * 根据元素的name来得到这个原始 选中的值,用于checkbox 中
 * 
 * @param {String}
 *            elementName 元素的name,而不是 id
 * @return {array} 值的数组
 * 
 * @see #isChecked
 */
function getCheckedValues(elementName) {
	// 1.2.6可用
	// var txt = "input[@name=" + elementName + "][@checked]";

	var txt = "input[name=" + elementName + "]:checked"

	// 定义数组
	var stateStack = new Array();
	var tmp = $(txt);
	if (tmp) {
		// 遍历名称为state的checkbox,得到状态为checked的值
		tmp.each(function() {
					stateStack.push($(this).val());
				})
	}
	return stateStack;
}

/**
 * 取得文本框对象的内容
 * 
 * @param {}
 *            elementId 对象的id
 */
function getTextValue(elementId) {
	// 验证码
	var obj = getS(elementId);

	if (obj) {
		var val = obj.attr("value");

		return ky.util.sNull(val);
	}
	return '';

}
function getRadioValueByDocument(elementName) {
	var l = document.getElementsByName(elementName);

	var txt = '';
	for (var i = 0; i < l.length; i++) {
		// alert(l[i].value);
		if (l[i].checked) {

			txt += (l[i].value);
		}
	}

	return txt;

}

/**
 * 得到选取的 radio button 选中的值
 * 
 * @param {String}
 *            elementName 元素的name,而不是 id
 * @return {value} 值
 */
function getRadioValue(elementName) {
	// var gender = $('input[@name=gender][@checked]').val();
	// 现代来讲,radio还document更加安全
	var value = getRadioValueByDocument(elementName);

	if (!value) {// 避免得不到 radio的值

		value = $("input[name=" + elementName + "][checked]").val();
	}
	return value;
}

/**
 * 判断 对象的 checked 属性 时候被选取,方法 boolan 而不是 checkbox, radio的 value
 * 
 * @param
 * @return {Boolean } 这个对象是否被选取
 */
function isChecked(id) {
	return isInputElementAttrBooleanValue('checked', id);
}
/**
 * 判断 元素的attr属性的 boolean
 * 
 * @param {String}
 *            attrName 包括:enabled disabled checked selected
 * @param {String}
 *            elementId 元素的id
 * @param {Boolean}
 */
function isInputElementAttrBooleanValue(attrName, elementId) {
	return getS(elementId).is(':' + attrName)
}

/**
 * 
 * @param {}
 *            id
 * @return {}
 */
function getS(id) {
	return $('#' + id);
}
/**
 * jQuery 判断 checkbox 是否被选中的几种方法收藏
 * 
 * 方法一： if ($("#checkbox-id")get(0).checked) { // do something }
 * 
 * 
 * 方法二：
 * 
 * if($('#checkbox-id').is(':checked')) { // do something }
 * 
 * 
 * 方法三： if ($('#checkbox-id').attr('checked')) { // do something }
 */
function isCheckedElement($) {
	if ($) {
		return $.attr('checked');
	} else {
		return false;
	}

}

// return the value of the radio button that is checked
// return an empty string if none are checked, or
// there are no radio buttons
function getCheckedValue(radioObj) {
	if (!radioObj)
		return "";
	var radioLength = radioObj.length;
	if (radioLength == undefined)
		if (radioObj.checked)
			return radioObj.value;
		else
			return "";
	for (var i = 0; i < radioLength; i++) {
		if (radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}

// set the radio button with the given value as being checked
// do nothing if there are no radio buttons
// if the given value does not exist, all the radio buttons
// are reset to unchecked
function setCheckedValue(radioObj, newValue) {
	if (!radioObj)
		return;
	var radioLength = radioObj.length;
	if (radioLength == undefined) {
		radioObj.checked = (radioObj.value == newValue.toString());
		return;
	}
	for (var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
		if (radioObj[i].value == newValue.toString()) {
			radioObj[i].checked = true;
		}
	}
}

/**
 * 得到 select multiple 的option的值
 * 
 * @param {String}元素的id
 *            elementId
 * @return {Array} 或者null
 */
function getMulitySelectValueArrary(elementId) {
	// 定义一个数组来存在 box的值

	var obj = document.getElementById(elementId);

	if (obj == null)
		return null;

	var stack = new Array();
	var column = "";
	var length = obj.length;
	if (length < 1) {
		return null;
	}
	var i = 0;
	// for (i = 0; i < length - 1; i++) {
	// column += obj.options[i].value + ",";
	// }
	// column += obj.options[i].value;

	for (i = 0; i < length; i++) {
		stack.push(obj.options[i].value);
	}
	return stack;
}

/**
 * 对单选按钮进行赋值选中操作. var checkboxs =
 * document.getElementsByName("record_pay_accountId");
 * changeRadio(checkboxs,val);
 * 
 * @param {}
 *            oRadio 通过byName方式来得到对象数组
 * @param {}
 *            oRadioValue 值
 */
function changeRadio(oRadio, oRadioValue) { // 传入一个对象
	if (oRadio == null)
		return;

	// alert("长度=" + oRadio.length);
	for (var i = 0; i < oRadio.length; i++) { // 循环

		// alert("每个的值是:" + i + "=" + oRadio[i].value);

		if (oRadio[i].value == oRadioValue) { // 比较值
			oRadio[i].checked = true; // 修改选中状态
			break; // 停止循环
		} else {
			oRadio[i].checked = false;
		}

	}

}

/**
 * 
 * @param {}
 *            checkName checkbox 的name名称,不是id
 * @param {}
 *            valueArray value的值
 */
function changeCheckboxValue(checkName, valueArray) {
	try {

		var obj = $("input[type=checkbox][name=" + checkName + "]");

		if (obj) {
			// alert(obj);

			var arrayLen = valueArray.length;
			// alert(arrayLen + " " + valueArray);
			for (var i = 0; i < obj.length; i++) { // 循环

				var tmp = obj[i];

				var tmpVal = tmp.value;
				// alert("每个的值是:" + i + "=" + tmpVal);

				for (var j = 0; j < arrayLen; j++) {

					var tmpB = valueArray[j];

					// alert(tmpB);
					if (tmpVal == tmpB) { // 比较值
						// tmp.attr("checked", 'true');// 修改选中状态
						// alert("值批评");
						tmp.checked = true;
						break; // 停止循环
					} else {
						// tmp.attr("checked", false);
						tmp.checked = false;
					}

				}

			}

		}

	} catch (err) {
		showErr(err);
	}
}
/**
 * 
 * @param {}
 *            radioName 单选按钮的名称
 * @param {}
 *            oRadioValue 需要设置的值
 */
function changeRadioValue(radioName, oRadioValue) { // 传入一个对象
	try {

		var obj = $("input[name=" + radioName + "][value=" + oRadioValue + "]");

		if (obj) {
			// alert(obj.length)
			obj.attr("checked", true);
		}

	} catch (err) {
		showErr(err);
	}
}
/**
 * 更新文本的值
 * 
 * @param {}
 *            id
 * @param {}
 *            value
 */
function changeTxtValue(id, value) {
	var obj = $("#" + id);
	if (obj) {
		obj.val(value);
	}
}
/**
 * 设置下拉列表的选择值 <code>
 * <br/>
 * 获取Select ：

 获取select 选中的 text:

 $("#ddlRegType").find("option:selected").text();

 获取select选中的 value:

 $("#ddlRegType ").val();

 获取select选中的索引:

 $("#ddlRegType ").get(0).selectedIndex;

 设置select:

 设置select 选中的索引：

 $("#ddlRegType ").get(0).selectedIndex=index;//index为索引值

 设置select 选中的value：

 $("#ddlRegType ").attr("value","Normal“);

 $("#ddlRegType ").val("Normal");

 $("#ddlRegType ").get(0).value = value;

 设置select 选中的text:

 var count=$("#ddlRegType ").size();

 for(var i=0;i<count;i++)  
 {           if($("#ddlRegType ").get(0).options[i].text == text)  
 {  
 $("#ddlRegType ").get(0).options[i].selected = true;  

 break;  
 }  
 } 

 清空 Select:

 $("#ddlRegType ").empty();


 </br></code>
 * 
 * @param {String
 *            or jqueryObj} elementObjOrId
 * @param {}
 *            selectValue
 */
function changeSelectValue(elementObjOrId, selectValue) {

	var obj;
	if (typeof elementObjOrId === 'string') {
		obj = getS(elementObjOrId);
	} else {
		obj = elementObjOrId;
	}
	if (obj) {
		obj.attr("value", selectValue);
	} else {
		log(elementObjOrId + '对应的obj不存在');
	}
}

/**
 * 更新整体的元素的值
 * 
 * 
 * @param {}
 *            id
 * @param {}
 *            value
 * @param {function}
 *            callback(id, value);回调
 */
function changeInputElementValue(id, value, callback) {
	var obj = $("#" + id);

	var dom = obj.get(0);
	if (dom) {

		// log('type= ' + dom.type);
		// log('dom.value==' + dom.value);
		switch (dom.type) {// 因为是基于id来获取的,所以只工作一个值
			case 'checkbox' :
			case 'radio' :

				if (dom.value == value) { // 比较值
					dom.checked = true; // 修改选中状态
				} else {
					dom.checked = false;
				}

				break;

			case 'select' :
				changeSelectValue(obj, value);
				break;
			default :
				obj.val(value);

				break;

		}

	} else {

		// 通过如果id的不到,就通过id方式来获取

		obj = $("input[name=" + id + "]");
		dom = obj.get(0);
		if (dom) {
			var type = dom.type;
			switch (type) {

				case 'radio' :
					// 遍历每一个 radio,判断相同的!
					for (var i = 0; i < obj.length; i++) {

						var oneRadio = obj[i];
						// log('oneRadio.value=' + oneRadio.value);
						if (oneRadio.value == value) { // 比较值
							oneRadio.checked = true; // 修改选中状态
							break; // 停止循环
						} else {
							oneRadio.checked = false;
						}

						// log("hh+" + i + "\t" + oneRadio);
					}

					break;
				case 'checkbox' :
					for (var i = 0; i < obj.length; i++) {

						var oneRadio = obj[i];
						// log('oneRadio.value=' + oneRadio.value);
						if (oneRadio.value == value) { // 比较值
							oneRadio.checked = true; // 修改选中状态
							break; // 停止循环
						} else {
							// oneRadio.checked = false;因为是复选框,所以不置为false
						}

						// log("hh+" + i + "\t" + oneRadio);
					}

					break;
				default :
					log('-----------这里需要做一个 对 default 的工作 哦');
			}

			// changeCheckboxValue(id, value);
			// log('存在哦 type=' + type);
		} else {
			// log('无 ' + id);
		}

	}

	if (typeof(callback) == 'function') {
		// 去除前缀,节点的真实id
		callback(id, value);
	}

}

/**
 * 改变控件的显示的内容
 * 
 * @param {}
 *            id
 * @param {}
 *            htmlValue
 */
function changeHtml(id, htmlValue, callback) {
	var obj = $("#" + id);
	if (obj) {
		// 即使为0,也显示
		var val = (htmlValue || '0' == htmlValue || 0 == htmlValue)
				? ('' + htmlValue)
				: '';

		// alert('需要显示的值是'+val);
		obj.html(val)
	} else {
		// alert('没得到对象');
	}

	if (typeof(callback) == 'function') {
		// 去除前缀,节点的真实id
		callback(id, htmlValue);
	}
}

/**
 * 校验文本是否为数字,包括小数点
 * 
 * @param {}
 *            data
 * @return {Boolean} true:是数字,false:不是数字
 */
function checknumber(data) {
	var tmp;
	if (data == "")
		return false;
	var re = /^[\-\+]?([0-9]\d*|0|[1-9]\d{0,2}(,\d{3})*)(\.\d+)?$/;
	var result = re.test(data);

	return result;
}

var htmlCheckCode; // 在全局 定义验证码
/**
 * 创建 html 的验证码,验证码的值 存放在 htmlCheckCode 对象中,便于 进行校验操作. <code>
 *  <input type="button" name="checkCode" id="checkCode" readonly="readonly" />
 *  
 *  
 *  js:
 *  createCode(4, 'checkCode');
 * </code>
 * 
 * @param {int}
 *            codeLength 验证码的长度 ,默认为4
 * @param {String}
 *            displayElementId 显示验证码的 元素id
 */
function createCode(codeLength, displayElementId, cssData) {
	htmlCheckCode = "";
	if (!codeLength) {
		codeLength = 4;
	}
	if (codeLength <= 0) {
		codeLength = 4;
	}

	var checkCode = getS(displayElementId);
	checkCode.val('');

	var domid = {
		'font-family' : 'Arial',
		'font-style' : 'italic',
		'color' : 'Red',
		'border' : '0',
		// 'padding' : '2px 3px',
		'letter-spacing' : '2px',
		'font-weight' : 'bolder',
		// 'width':'55px',
		'height' : '25px',
		'cursor' : 'pointer'
	};
	cssData = jQuery.extend({}, domid, cssData);
	checkCode.css(cssData);
	// 点击事件

	checkCode.bind('click', function() {
				htmlCheckCode = __createHtmlCheckCode(codeLength);
				$(this).val(htmlCheckCode);
			});

	checkCode.attr('title', '验证码 看不清楚？请点击刷新验证码！');
	// var checkCode = document.getElementById(displayElementId);
	//
	// if (!checkCode)
	// return;
	// checkCode.value = "";

	htmlCheckCode = __createHtmlCheckCode(codeLength);
	// checkCode.value = htmlCheckCode;
	checkCode.val(htmlCheckCode);
}
/**
 * 创建随机的验证码
 * 
 * @param {}
 *            codeLength
 * @return {}
 */
function __createHtmlCheckCode(codeLength) {
	var tmp = '';
	var selectChar = new Array(2, 3, 4, 5, 6, 7, 8, 9, 0);
	// ,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');

	for (var i = 0; i < codeLength; i++) {
		var charIndex = Math.floor(Math.random() * 9);
		tmp += selectChar[charIndex];
	}

	// log('codeLength='+codeLength+" "+tmp);
	if (tmp.length != codeLength) {
		return __createHtmlCheckCode(codeLength);
	}
	return tmp;
}

function showErr(err, message) {
	if (!err)
		return;
	var txt = "There was an error on this page.\n";

	if (message) {
		txt += message + "\n";
	}
	if (!isNaN(err.description)) {
		txt += "\nError description : " + err.description;
	}
	if (!isNaN(err.number)) {
		txt += "\nError number : " + err.number;
	}

	txt += "\nerr.name : " + err.name;
	txt += "\nerr.message : " + err.message;
	txt += "\n\nClick OK to continue.\n";
	alert(txt);
	// Error.name的取值一共有六种，如下：
	// EvalError：eval()的使用与定义不一致
	// RangeError：数值越界
	// ReferenceError：非法或不能识别的引用数值
	// SyntaxError：发生语法解析错误
	// TypeError：操作数类型错误
	// URIError：URI处理函数使用不当
}

/**
 * 判断这个 radio 或者 checkbox 是否被选中了
 * 
 * @param {元素id}
 *            id
 * @return boolean true:被选中,false:没有选中
 */
function isChecked(id) {
	var tmp = $("#" + id).attr('checked');
	if (tmp == true) {
		return true;
	} else {
		return false;
	}

}
function hiddenLayer(id) {
	var tmp = $("#" + id);
	if (tmp) {
		tmp.hide();
	}
}

/**
 * 创建不显示的span 对象
 * 
 * @param {}
 *            id
 * @param {}
 *            value
 * @return {}
 */
function makeHidden(id, value) {
	var txt = '';

	txt += '<input type="hidden" id="' + id + '" name="' + id + '" value="'
			+ value + '"/>';

	return txt;
}
/**
 * 创建一个复选框
 * 
 * @param {}
 *            boxName 复选框的名字,如果为空,默认是 checkbox
 * @param {}
 *            value 复选框的值 ,默认是 ''
 * @param {}
 *            id 可以为空,默认会是 boxName_value 的形式
 * 
 * @return {String} 创建一个checkbox 的文本
 * 
 */
function createCheckBox(boxName, value, id) {

	var name = (boxName) ? boxName : 'checkbox';
	var tmpVal = (value) ? value : '';

	var tmpid = '';
	if (id) {
		tmpid = id;
	} else {
		tmpid = name + '_' + tmpVal;
	}

	var txt = '';
	txt += "<input type='checkbox' ";

	txt += " id='" + tmpid + "' ";

	txt += " name='" + name + "' ";
	txt += " value='" + tmpVal + "' />";
	return txt;
}
/**
 * 创建一个复选框,并且带有label标记
 * 
 * <label for="table_select_all_box"> <input type='checkbox'
 * id='table_select_all_box' name='table_select_all_box' value='1' /> 全选
 * </label>
 * 
 * @param {}
 *            boxName 复选框的名字,如果为空,默认是 checkbox
 * @param {}
 *            value 复选框的值 ,默认是 ''
 * @param {}
 *            id 可以为空,默认会是 boxName_value 的形式
 * @param {}
 *            displayValue 显示的内容可以为空
 * @return {String} 创建一个checkbox 的文本
 * 
 */
function createCheckBoxWithLabel(boxName, value, displayValue, id) {
	var tmpShowVal = (displayValue) ? displayValue : '';
	var name = (boxName) ? boxName : 'checkbox';
	var tmpVal = (value) ? value : '';

	var tmpid = '';

	var tmpid = '';
	if (id) {
		tmpid = id;
	} else {
		tmpid = name + '_' + tmpVal;
	}

	var txt = '';
	txt += '<label for="' + tmpid + '">';
	txt += createCheckBox(name, tmpVal, tmpid);
	txt += tmpShowVal;
	txt += '</label>';

	return txt;
}
// $("#deselectall").click(function() {
// $("input[@name='shareuser[]']").each(function() {
// $(this).attr("checked", false);
// });
// });

/**
 * 进行 post 提交
 * 
 * doPost(vPath, vhttValue, function(data) { // alert(ky.json.json(data));
 * 
 * 
 * });
 * 
 * 
 * @param {}
 *            url
 * @param {}
 *            data
 * @param {}
 *            callback
 * @param async
 *            是否进行同步操作(false) ,默认为异步操作(true), (默认:
 *            true)默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为
 *            false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
 *            这里进行一个控制,用来避免浏览器缓存的,而每次请求进行一次 随机值的改变
 */
function doPost(url, data, callback, async) {
	var dataType = 'json';
	if (jQuery.isFunction(data)) {
		callback = data;
		data = {};
	}

	var _async = true;// 
	// (默认: true)默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为
	// false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
	// 这里进行一个控制,用来避免浏览器缓存的,而每次请求进行一次 随机值的改变
	if (!ky.util.isEmpty(async)) {
		if (async === false) {
			_async = false;
		}
	}
	if (data && typeof data != "string") {
		var domid = {
			domid : ky.util.getNextId()
		}
		data = jQuery.extend({}, domid, data);
	}
	// alert(jQuery.param(data));
	try {
		// $.post(url, data, callback, type);
		$.ajax({
					url : url,
					type : "POST",
					dataType : dataType,
					data : data,
					success : callback,
					// complete : function(xmlHttp, status) {
					// for(var i in xmlHttp){
					// if(i=='responseText'||i=='channel'){
					// }else{
					// log(i+"\t="+xmlHttp[i]);
					// }
					// }
					//						
					// log('statusText ='+xmlHttp.statusText );
					// if (false) {
					// log('status=' + status);
					// log('xmlHttp.url=' + xmlHttp.src);
					//
					// log('xmlHttp.status='
					// + xmlHttp.status.toString()[0]);
					// log('xmlHttp.status=' + xmlHttp.status);
					//
					// log(" response.getResponseHeader('Location') ="
					// + xmlHttp.getResponseHeader('Location'));
					//
					// log(" response.getResponseHeader('REQUIRES_AUTH') ="
					// + xmlHttp
					// .getResponseHeader('REQUIRES_AUTH'));
					// log(json(xmlHttp));
					// }
					// if (xmlHttp.status != 200) {
					// // top.location.href = '/some/other/page';
					// log(json(xmlHttp));
					// }
					// // if (xmlHttp.status.toString()[0] == '3') {
					// // var href = xmlHttp.getResponseHeader('Location');
					// // jumpToHref(href);
					// // }
					// },

					async : _async,// (默认: true)
					// 默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为
					// false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
					error : function(request, message, e) {
						var txt = "通信出错了,请稍候再试.\n" + "\n\n";
						txt += "message:\t" + message + "\n";
						if (e) {
							txt += "e:\t" + e + "\n";
						}

						// if (request) {
						// txt += "request:\t" + request;
						// }
						// 这里把 map 对象转换为字符串对象
						var vhttValue = data;
						if (data && typeof data != "string") {
							vhttValue = jQuery.param(data);
						}
						txt += "url:\t" + url + "\n";
						txt += "data:\t" + vhttValue + "\n";
						if (isdebug()) {
							showErr(e, txt);
						}
						log(txt);
					}

				});
	} catch (err) {
		// showErr(err, "执行查询 doPost 过程中");
		log(err);
	}
}

/**
 * 根据时间来产生一个随机数,尽可能的不重复
 * 
 * @return {}
 */
function getTimeRndString() {
	var tm = new Date();
	var str = tm.getMilliseconds() + tm.getSeconds() * 60 + tm.getMinutes()
			* 3600 + tm.getHours() * 60 * 3600 + tm.getDay() * 3600 * 24
			+ tm.getMonth() * 3600 * 24 * 31 + tm.getYear() * 3600 * 24 * 31
			* 12 * Math.random();
	return str;
};

/**
 * 显示时间和日期的 选择框
 * 
 * @param {String}
 *            targetId 需要把 选取的值 返回的 目的控件id
 */
function showDateTime(targetId) {
	if (targetId == '')
		return;
	Calendar.setup({
		inputField : targetId, // id of the input field
		ifFormat : "%Y-%m-%d %H:%M", // format of the input field
		showsTime : true, // will display a time selector
		// button : "f_trigger_b", // trigger for the calendar (button ID)
		singleClick : true,
		step : 1
			// show all years in drop-down boxes (instead of every other year as
			// default)
		});
}
/**
 * 显示 选择 日期的 文本框
 * 
 * @param {String}
 *            targetId 需要把 选取的值 返回的 目的控件id
 */
function showDate(targetId) {
	if (targetId == '')
		return;
	Calendar.setup({
		inputField : targetId, // id of the input field
		ifFormat : "%Y-%m-%d", // format of the input field
		showsTime : false, // will display a time selector
		// button : "f_trigger_only", // trigger for the calendar (button ID)
		singleClick : true,
		step : 1
			// show all years in drop-down boxes (instead of every other year as
			// default)
		});
}

/**
 * 判断对象是否为字符类型
 * 
 * @param {}
 *            str
 * @return {}
 */
function isString(str) {
	if (str == null)
		return false;
	return (typeof str == 'string') && str.constructor == String;
}

/**
 * 判断对象是否是 数字
 * 
 * @param {}
 *            obj
 * @return {}
 */
function isNumber(obj) {
	if (obj == null)
		return false;
	return (typeof obj == 'number') && obj.constructor == Number;
}
/**
 * 判断对象是否是日期类型
 * 
 * @param {}
 *            obj
 * @return {}
 */
function _isDate(obj) {
	if (obj == null)
		return false;
	return (typeof obj == 'object') && obj.constructor == Date;
}

/**
 * 判断对象是否是数组
 * 
 * @param {}
 *            obj
 * @return {}
 */
function isArray(obj) {
	if (obj == null)
		return false;
	return (typeof obj == 'object') && obj.constructor == Array;
}

/**
 * 进行页面跳转
 * 
 * @param {}
 *            to 跳转的目的名称,action名字或者 view的name
 * @param {}
 *            jumpParam 跳过去传入的参数串
 */
function jump(to, jumpParam) {
	try {
		if (to) {
			$("#jumpto").val(to);
		}
		if (!ky.util.isEmpty(jumpParam)) {
			changeTxtValue('_jumpParam', jumpParam);
		}
		// alert('kais tij ');
		// document.form_jump.submit();
		$("#form_jump").submit();
	} catch (err) {
		showErr(err);
	}
}
/**
 * 进行页面跳转
 * 
 * @param {String}
 *            href 需要跳转过去的页面
 */
function jumpToHref(href) {
	try {
		window.location.href = href;
	} catch (err) {
		showErr(err);
	}
}

ky = {

	table : {

		/**
		 * td的内容
		 * 
		 * @param {String}
		 *            showTxt 显示的内容
		 * @param {String}
		 *            tdId
		 * @param {String}
		 *            tdClass
		 * @param {String}
		 *            tdStyle
		 * @return {String}
		 */
		oneTd : function(showTxt, tdId, tdClass, tdStyle) {
			var t = "<td ";
			if (!ky.util.isEmpty(tdId)) {
				t += " id='" + tdId + "'";
			}

			if (!ky.util.isEmpty(tdClass)) {
				t += " class='" + tdClass + "'";
			}
			if (!ky.util.isEmpty(tdStyle)) {
				t += " style='" + tdStyle + "'";
			}

			t += '>' + ky.util.sNull(showTxt) + '</td>';
			return t;
		},

		/**
		 * 创建一个 table 的 行
		 * 
		 * @param {json}
		 *            tdDataJson json的数据,
		 * @param {String}
		 *            trId 行id
		 * @param {String}
		 *            trClass 行 class
		 * @param {String}
		 *            trStyle 行 style
		 * @param {Array}
		 *            keyArray 行头 ,可以为空
		 * @param{json} tdStyleJson 格子的其他属性,
		 *              %name%_index_style,%name%_index_id,%name%_index_class,
		 * @return {String} 拼凑的行对象
		 */
		createTrRow : function(tdDataJson, trId, trClass, trStyle, keyArray,
				tdJsonStyleClassId, tdClass) {
			var t = "<tr ";

			if (!ky.util.isEmpty(trId)) {
				t += " id='" + trId + "'";
			}

			if (!ky.util.isEmpty(trClass)) {
				t += " class='" + trClass + "'";
			}
			if (!ky.util.isEmpty(trStyle)) {
				t += " style='" + trStyle + "'";
			}
			t += ">"

			var hasTdAttr = '';// 判断是否有 td 的属性
			// alert("isNan==" + isNaN(tdJsonStyleClassId))
			hasTdAttr = !isNaN(tdJsonStyleClassId);
			// alert("hasTdAttr=" + hasTdAttr);

			if (keyArray && $.isArray(keyArray) && keyArray.length > 0) {
				// 只有是数组的情况下
				$.each(keyArray, function(i, n) {
							// alert("Item #" + i + ": " + n);

							var key = n;
							var tmp = tdDataJson[key];

							var tdId = '', tdStyle = '';
							if (hasTdAttr) {
								try {
									tdId = tdJsonStyleClassId[key + i + "_id"];
								} catch (Err) {
									showErr(Err);
								}
								if (!tdClass)
									tdClass = tdJsonStyleClassId[key + i
											+ "_class"];
								tdStyle = tdJsonStyleClassId[key + i + "_style"];
							}
							t += ky.table.oneTd(tmp, tdId, tdClass, tdStyle);

						});

			} else {

				$.each(tdDataJson, function(i, n) {
							var value = n;
							var tdId = '', tdClass = '', tdStyle = '';
							if (hasTdAtt) {
								tdId = tdJsonStyleClassId(key + i + "_id");
								tdClass = tdJsonStyleClassId(key + i + "_class");
								tdStyle = tdJsonStyleClassId(key + i + "_style");
							}

							// alert("Name: " + i + ", Value: " + n);
							t += ky.table.oneTd(value, tdId, tdClass, tdStyle);
						});
			}
			t += "</tr>"
			// <tr id='node--1' class=''><td><span
			// class='file'>Acknowledgements.rtf</span></td><td>File</td><td>480.95
			// KB</td></tr>
			return t;
		},
		/**
		 * 得到 用于 treetable 的 父节点的 class 标记
		 * 
		 * @param {}
		 *            partnerId
		 * @return {}
		 */
		getTreeTablePartnerClass : function(partnerId) {
			return 'child-of-' + partnerId;
		}
	},

	// ------------------以下是分页的操作---------------------------------
	pages : {
		/**
		 * 显示查询的结果!
		 * 
		 * @param {}
		 *            page_id
		 * @param {String}
		 *            targetId 目的对象的 id
		 * @return {Boolean}
		 */
		displayPageMessage : function(page_id, targetId, perPageSize) {// , jq

			if (!perPageSize) {
				perPageSize = 10;
			}

			if (targetId) {
				var obj = $('#' + targetId);
				if (obj) {
					var start = ((page_id - 1) * perPageSize)
					obj.text("Showing search results " + (start + 1) + "-"
							+ (start + perPageSize));
				}
			}
			// (page_id);

			return true;
		},

		/**
		 * 进行分页操作的 公共处理方法:
		 * 
		 * 通信后的结果集需要包括: listData,totalResult ,pageNum ,items_per_page(不是必须) 记录数据
		 * 
		 * @param {Map}
		 *            options 包括:
		 * @field {String}url 查询的url
		 * @field {int} page 请求的分页的页索引
		 * @field {String}dispPaginaExpression jquery能够识别的 ,分页控件的表达式
		 *        ,比如:div[alt="ts"]
		 * @field {function函数的String名称}paginationCallbackByPageNo(int_pageId) 根据页号进行翻页操作的函数
		 * @field {String}divTotalPageResultId 显示查询 的记录数的 显示对象的 ID
		 * @field {function函数的String名称}createResultRowCallbackName(index, record) 进行创建 查询结果的
		 *        行的方法的名字,回调的时候会 2个参数 :index, json的一行对象
		 * @field {String}queryResultDispId 查询结果的显示的对象的 id ,
		 * @field {String}queryParameterTxt 查询条件的字符串,可以使用 jQuery.param(param) 或者
		 *        用$("#query_logistics_form").serialize() 来得到
		 * @field {int}perPage 每页的记录数量,默认为10
		 * @field {function函数的String名称}processResultCallbackName 需要有返回值
		 *        系统通信得到结果集后,首先进行回调操作,然后再进行展现数据的操作,通过判断返回值,来决定是否进行
		 *        行的解析,true==代表需要进行行数据的通用生成,false=不再进行解析和生成显示的操作
		 * @field {function函数的String名称}paginationComplete(ajaxJsonData)
		 *        整体执行完后,的回调函数,ajaxJsonData是通信的结果集
		 * @field {String}disableClickAction分页控件中,禁用点击事件,而是采用url的方式来进行分页,也就是（页面会刷新，url地址改为？page=目的页id）
		 * 
		 * @return {Boolean}
		 */
		paginationQuery : function(options) {
			var empty = {}
			var defaults = {
				url : '',
				pageIndex : 0,
				dispPaginaExpression : 'div[alt="ts"]',
				paginationCallbackByPageNo : '',
				divTotalPageResultId : '',
				createResultRowCallbackName : null,
				queryResultDispId : '',
				queryParameterTxt : '',
				perPage : 10,
				processResultCallbackName : null,
				paginationCompleteCallbackName : null,
				disableClickAction : false
			};

			var settings = jQuery.extend(empty, defaults, options);
			{
				if (isEmpty(settings.pageIndex)) {
					settings.pageIndex = 0;
				}
				if (isEmpty(settings.url)) {
					alert('url 参数,通信请求的url,不允许为空');
					return false;
				}

				if (isEmpty(settings.paginationCallbackByPageNo)) {
					alert('paginationCallbackByPageNo参数进行 分页action的回调,不允许为空');
					return false;
				}

				if (isEmpty(settings.queryResultDispId)) {
					alert('queryResultDispId 参数进行 结果集处理后的数据的显示,不允许为空');
					return false;
				}

				if (isEmpty(settings.queryParameterTxt)) {
					alert('queryParameterTxt 参数,用来描述action等通信请求,不允许为空');
					return false;
				}
			}
			// -------------------------------
			var items_per_page = settings.perPage;

			// 同后台的gt的handler相对应
			var gtParam = {
				"recordType" : "object",
				"pageInfo" : {
					"pageSize" : items_per_page,
					"pageNum" : settings.pageIndex,
					"totalRowNum" : -1,
					"totalPageNum" : 0,
					"startRowNum" : 1,
					"endRowNum" : -1
				},
				"sortInfo" : [],
				"filterInfo" : [],
				"remotePaging" : true,
				"parameters" : {},
				"action" : "load"
			};

			var param = {
				p_toPageNo : settings.pageIndex,
				items_per_page : items_per_page,
				domid : ky.util.getNextId(),
				// _gt_json1 : GT.$json(gtParam),
				_gt_json : json(gtParam),
				_c_pagi_q : 1
				// 用来描述是客户端的自定义分页查询

			};
			// alert(GT.$json);==jsonEncode

			// 拼凑查询条件
			var vhttValue = jQuery.param(param) + '&'
					+ settings.queryParameterTxt;

			var tbody = getS(settings.queryResultDispId);
			// 标记为正在查询
			tbody.html("<tr><td colspan='10'>" + txt_querying + "</td></tr>")
			doPost(settings.url, vhttValue, function(data) {

				var result = [];
				var pageNum = 1;// 页号
				var tmpPerpage = items_per_page;// 每页记录数量
				var totalResult = 0;
				if (data) {
					result = data.listData;

					pageNum = data.pageNum;

					tmpPerpage = (data.items_per_page)
							? data.items_per_page
							: items_per_page
					totalResult = data.totalResult;
				} else {
					data = [];
				}
				// 默认默认值为1
				pageNum = (pageNum) ? ((pageNum <= 0) ? 1 : pageNum) : 1;

				// alert(json(data));

				var toOpRow = true;// 是否进行 行处理
				if (!isEmpty(settings.processResultCallbackName)) {
					toOpRow = eval(settings.processResultCallbackName)(data);
				}

				if (toOpRow) {
					if (isEmpty(settings.createResultRowCallbackName)) {
						alert('createResultRowCallbackName参数进行 行数据的构建,不允许为空');
						return false;
					}
				}

				var txt_tr_no_result = "<tr><td colspan='10'> &nbsp;暂无对应查询结果! </td></tr>";
				// log("result=" + GT.$json(result));
				if (result && toOpRow) {
					var length = result.length;

					// log(length);
					// 判断记录数量,是否显示 无查询结果的提示
					var txt = "";

					if (length > 0) {
						tbody.html(txt);
						for (var index = 0; index < length; index++) {
							var one = result[index];
							// 创建一行结果集
							txt = eval(settings.createResultRowCallbackName)(
									index, one);
							tbody.append(txt);
						}

						var cha = items_per_page - length;
						if (cha > 0) {
							var index = length
							for (var i = 0; i < cha; i++) {
								// log('index===' + index);

								// 因为无数据,所以不传递参数过去
								// 这个操作一般由于:军火库的页面固定是2了区域显示查询结果,如果只有一条记录,那么就需要把
								// 另一个区域的数据clear掉,所以这个进行了一个 特别的处理
								txt = eval(settings.createResultRowCallbackName)(
										index, null);
								index++;

								tbody.append(txt);
							}
						}
						// 判断 如果 length的长度<每页显示的几个数量,还需要操作一下 结果,工作其他未显示的记录也能够被替换
					} else {
						tbody.html(txt_tr_no_result);
					}
					//
				} else {// 暂无对应查询结果

					tbody.html(txt_tr_no_result);
				}

				// log(txt);

				// 分页事件的跳转 方法
				var paginationPageCallback = 'ky.pages.gotoThePage';

				$(settings.dispPaginaExpression).pagination(totalResult,
						pageNum, {
							perPage : tmpPerpage,
							callback : function(page_id) {
								// 翻页的时候回调方法,因为要额外传递2个参数,所以用动态调用
								eval(paginationPageCallback)(page_id,
										settings.paginationCallbackByPageNo,
										settings.divTotalPageResultId,
										tmpPerpage);
							},
							disableClickAction : settings.disableClickAction
						});

				// 回调函数,用来描述以及完成
				if (settings.paginationCompleteCallbackName) {
					eval(settings.paginationCompleteCallbackName)(data);
				}

			});

		},

		/**
		 * 分页的回调方法
		 * 
		 * @param {}
		 *            page_id
		 * @param {}
		 *            paginationCallbackByPageNo
		 * @param {}
		 *            divTotalPageResultId
		 * @return {Boolean}
		 */
		gotoThePage : function(page_id, paginationCallbackByPageNo,
				divTotalPageResultId, perPageSize) {
			// 调用查询 方式 来分页
			eval(paginationCallbackByPageNo)(page_id);

			// 显示查询描述
			ky.pages.displayPageMessage(page_id, divTotalPageResultId,
					perPageSize);

			return true;
		}
	},
	format : {
		/**
		 * 功能:格式化时间 示例:ky.format.dateFormat("Thu Nov 9 20:30:37 UTC+0800 2006
		 * ","yyyy/MM/dd"); 返回:2006/11/09
		 */
		dateFormat : function(date, fmtCode) {

			if (date) {
			} else {
				return '';
			}

			if (date == '') {
				return '';
			}

			var result, d, arr_d;

			var patrn_now_1 = /^y{4}-M{2}-d{2}\sh{2}:m{2}:s{2}$/;
			var patrn_now_11 = /^y{4}-M{1,2}-d{1,2}\sh{1,2}:m{1,2}:s{1,2}$/;

			// 没有秒的 时间 2009-05-02 13:43
			var patrn_now_no_second = /^y{4}-M{2}-d{2}\sh{2}:m{2}$/;

			var patrn_now_2 = /^y{4}\/M{2}\/d{2}\sh{2}:m{2}:s{2}$/;
			var patrn_now_22 = /^y{4}\/M{1,2}\/d{1,2}\sh{1,2}:m{1,2}:s{1,2}$/;

			var patrn_now_3 = /^y{4}年M{2}月d{2}日\sh{2}时m{2}分s{2}秒$/;
			var patrn_now_33 = /^y{4}年M{1,2}月d{1,2}日\sh{1,2}时m{1,2}分s{1,2}秒$/;

			var patrn_date_1 = /^y{4}-M{2}-d{2}$/;
			var patrn_date_11 = /^y{4}-M{1,2}-d{1,2}$/;

			var patrn_date_2 = /^y{4}\/M{2}\/d{2}$/;
			var patrn_date_22 = /^y{4}\/M{1,2}\/d{1,2}$/;

			var patrn_date_3 = /^y{4}年M{2}月d{2}日$/;
			var patrn_date_33 = /^y{4}年M{1,2}月d{1,2}日$/;

			var patrn_time_1 = /^h{2}:m{2}:s{2}$/;
			var patrn_time_11 = /^h{1,2}:m{1,2}:s{1,2}$/;
			var patrn_time_2 = /^h{2}时m{2}分s{2}秒$/;
			var patrn_time_22 = /^h{1,2}时m{1,2}分s{1,2}秒$/;

			var patrn_time_3 = /^h{2}$/;
			var patrn_time_33 = /^h{1,2}$/;

			if (!fmtCode) {
				fmtCode = DATE_FORMATE_YMD_HMS;
			}

			d = new Date(date);

			if (isNaN(d)) {
				this
						.msgBox(date
								+ ",解析错误,\n时间参数非法\n正确的时间示例:\nThu Nov 9 20:30:37 UTC+0800 2006\n或\n2006/10/17");
				return;

			}
			if (patrn_now_1.test(fmtCode)) {
				arr_d = this.splitDate(d, true);
				result = arr_d.yyyy + "-" + arr_d.MM + "-" + arr_d.dd + " "
						+ arr_d.hh + ":" + arr_d.mm + ":" + arr_d.ss;
			} else if (patrn_now_11.test(fmtCode)) {
				arr_d = this.splitDate(d);
				result = arr_d.yyyy + "-" + arr_d.MM + "-" + arr_d.dd + " "
						+ arr_d.hh + ":" + arr_d.mm + ":" + arr_d.ss;
			} else if (patrn_now_2.test(fmtCode)) {
				arr_d = this.splitDate(d, true);
				result = arr_d.yyyy + "/" + arr_d.MM + "/" + arr_d.dd + " "
						+ arr_d.hh + ":" + arr_d.mm + ":" + arr_d.ss;
			} else if (patrn_now_22.test(fmtCode)) {
				arr_d = this.splitDate(d);
				result = arr_d.yyyy + "/" + arr_d.MM + "/" + arr_d.dd + " "
						+ arr_d.hh + ":" + arr_d.mm + ":" + arr_d.ss;
			} else if (patrn_now_3.test(fmtCode)) {
				arr_d = this.splitDate(d, true);
				result = arr_d.yyyy + "年" + arr_d.MM + "月" + arr_d.dd + "日"
						+ " " + arr_d.hh + "时" + arr_d.mm + "分" + arr_d.ss
						+ "秒";
			} else if (patrn_now_33.test(fmtCode)) {
				arr_d = this.splitDate(d);
				result = arr_d.yyyy + "年" + arr_d.MM + "月" + arr_d.dd + "日"
						+ " " + arr_d.hh + "时" + arr_d.mm + "分" + arr_d.ss
						+ "秒";
			}

			else if (patrn_date_1.test(fmtCode)) {
				arr_d = this.splitDate(d, true);
				result = arr_d.yyyy + "-" + arr_d.MM + "-" + arr_d.dd;
			} else if (patrn_date_11.test(fmtCode)) {
				arr_d = this.splitDate(d);
				result = arr_d.yyyy + "-" + arr_d.MM + "-" + arr_d.dd;
			} else if (patrn_date_2.test(fmtCode)) {
				arr_d = this.splitDate(d, true);
				result = arr_d.yyyy + "/" + arr_d.MM + "/" + arr_d.dd;
			} else if (patrn_date_22.test(fmtCode)) {
				arr_d = this.splitDate(d);
				result = arr_d.yyyy + "/" + arr_d.MM + "/" + arr_d.dd;
			} else if (patrn_date_3.test(fmtCode)) {
				arr_d = this.splitDate(d, true);
				result = arr_d.yyyy + "年" + arr_d.MM + "月" + arr_d.dd + "日";
			} else if (patrn_date_33.test(fmtCode)) {
				arr_d = this.splitDate(d);
				result = arr_d.yyyy + "年" + arr_d.MM + "月" + arr_d.dd + "日";
			} else if (patrn_time_1.test(fmtCode)) {
				arr_d = this.splitDate(d, true);
				result = arr_d.hh + ":" + arr_d.mm + ":" + arr_d.ss;
			} else if (patrn_time_11.test(fmtCode)) {
				arr_d = this.splitDate(d);
				result = arr_d.hh + ":" + arr_d.mm + ":" + arr_d.ss;
			} else if (patrn_time_2.test(fmtCode)) {
				arr_d = this.splitDate(d, true);
				result = arr_d.hh + "时" + arr_d.mm + "分" + arr_d.ss + "秒";
			} else if (patrn_time_22.test(fmtCode)) {
				arr_d = this.splitDate(d);
				result = arr_d.hh + "时" + arr_d.mm + "分" + arr_d.ss + "秒";

			} else if (patrn_time_3.test(fmtCode)) {
				arr_d = this.splitDate(d, true);
				result = arr_d.hh + "时";
			} else if (patrn_time_33.test(fmtCode)) {
				arr_d = this.splitDate(d);
				result = arr_d.hh + "时";

			} else if (patrn_now_no_second.test(fmtCode)) {
				arr_d = this.splitDate(d, true);
				result = arr_d.yyyy + "-" + arr_d.MM + "-" + arr_d.dd + " "
						+ arr_d.hh + ":" + arr_d.mm;
			}

			else {
				this.msgBox("没有匹配的时间格式!\n" + date);
				return;
			}

			return result;
		},
		splitDate : function(d, isZero) {
			var yyyy, MM, dd, hh, mm, ss;
			if (isZero) {
				// mawm 09.03.25: 原来是 getYear(),但是 操作timstamp 是,得到的year是209
				// ,所以改为
				// getFullYear()来操作
				yyyy = d.getFullYear();// d.getYear();
				MM = (d.getMonth() + 1) < 10 ? "0" + (d.getMonth() + 1) : d
						.getMonth()
						+ 1;
				dd = d.getDate() < 10 ? "0" + d.getDate() : d.getDate();
				hh = d.getHours() < 10 ? "0" + d.getHours() : d.getHours();
				mm = d.getMinutes() < 10 ? "0" + d.getMinutes() : d
						.getMinutes();
				ss = d.getSeconds() < 10 ? "0" + d.getSeconds() : d
						.getSeconds();
			} else {
				yyyy = d.getFullYear();// d.getYear();
				MM = d.getMonth() + 1;
				dd = d.getDate();
				hh = d.getHours();
				mm = d.getMinutes();
				ss = d.getSeconds();
			}
			var obj = {
				"yyyy" : yyyy,
				"MM" : MM,
				"dd" : dd,
				"hh" : hh,
				"mm" : mm,
				"ss" : ss
			};
			// alert(jQuery.param(obj) );
			return obj;
		},
		msgBox : function(msg) {
			window.alert(msg);
		},
		/**
		 * 格式化数字,例子:formatNum(1234005651.789, 2)
		 * 
		 * @param {}
		 *            srcStr 要格式化的数字
		 * @param {}
		 *            nAfterDot 保留小数位
		 * @return {}
		 */
		toNumber : function(srcStr, nAfterDot) {
			var srcStr, nAfterDot;
			var resultStr, nTen;
			srcStr = "" + srcStr + "";
			var strLen = srcStr.length;
			var dotPos = srcStr.indexOf(".", 0);
			if (dotPos == -1) {
				resultStr = srcStr + ".";
				for (var i = 0; i < nAfterDot; i++) {
					resultStr = resultStr + "0";
				}
				return resultStr;
			} else {
				if ((strLen - dotPos - 1) >= nAfterDot) {
					var nAfter = dotPos + nAfterDot + 1;
					nTen = 1;
					for (var j = 0; j < nAfterDot; j++) {
						nTen = nTen * 10;
					}
					resultStr = Math.round(parseFloat(srcStr) * nTen) / nTen;
					return resultStr;
				} else {
					resultStr = srcStr;
					for (i = 0; i < (nAfterDot - strLen + dotPos + 1); i++) {
						resultStr = resultStr + "0";
					}
					return resultStr;
				}
			}
		},
		/**
		 * 格式化 金额为 3位一个逗号的方式
		 * 
		 * @param {}
		 *            s 需要格式化的文本
		 * @param {}
		 *            nAfterDot ,小数位数,默认为2
		 * @return {}
		 */
		toMoney : function(s, nAfterDot) {
			var n = n > 0 && n <= 20 ? n : 2;
			s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
			var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
			var t = "";
			for (var i = 0; i < l.length; i++) {
				t += l[i]
						+ ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
			}
			return t.split("").reverse().join("") + "." + r;
		},
		/**
		 * 把moneyTxt 转换为数字的样式,moneyTxt 一般为 000,000.00 的形式
		 * 
		 * @param {}
		 *            s
		 * @return {}
		 */
		rmoney : function(moneyTxt) {
			if (moneyTxt) {
				if (isString(moneyTxt)) {
					return parseFloat(moneyTxt.replace(/[^\d\.-]/g, ""));
				} else {
					return parseFloat(moneyTxt);
				}
			} else {
				return '';
			}
		},
		/**
		 * 格式化日期,只显示 年月日
		 * 
		 * @param {}
		 *            date
		 */
		toDateTxt : function(date) {
			if (date == null)
				return '';
			if (isString(date)) {
				return ky.format.dateFormat(date, DATE_FORMATE_YMD);

			} else if (date.time) {
				// alert(date.time);
				return ky.format.dateFormat(date.time, DATE_FORMATE_YMD);
			} else {
				return date;
			}
		},
		/**
		 * 根据格式化 字符.来对日期对象进行格式化
		 * 
		 * @param {}
		 *            date
		 * @param {}
		 *            formate
		 * @return {String}
		 */
		toDateByFormat : function(date, formate) {
			if (!date)
				return '';
			if (formate) {
				if ('' == formate) {
					formate = DATE_FORMATE_YMD_HMS;
				}
			} else {
				formate = DATE_FORMATE_YMD_HMS;
			}

			if (isString(date)) {
				return date;
			} else if (date.time) {
				return ky.format.dateFormat(date.time, formate);
			} else if (_isDate(date)) {
				var retTxt = '';
				if (date) {
					retTxt = ky.format.dateFormat(date, formate);
				} else {
					retTxt = "";
				}
				// alert(udpateData+"\n"+retTxt+"\n"+timestamp);
				return retTxt;
			} else {
				return date;
			}

		},
		/**
		 * 格式化一个完整的日期
		 * 
		 * @param {}
		 *            date
		 */
		toFullDate : function(date) {
			if (date == null)
				return '';
			return this.toDateByFormat(date, DATE_FORMATE_YMD_HMS);
		},

		/**
		 * 格式化一个没有 秒的 日期
		 * 
		 * @param {}
		 *            date
		 * @param {String}
		 *            2009-09-19 18:34
		 */
		toNoSecondDate : function(date) {
			if (date == null)
				return '';
			if (isString(date)) {
				// 截取时间的长度
				// log(date.length);
				// '2009-10-21 18:53'16
				if (date.length > 16) {// 16是没有分钟的长度
					return date.substring(0, 16);
				} else {
					return date;
				}
			}
			return this.toDateByFormat(date, DATE_FORMATE_YMD_HM);
		}
	},

	/** 扩展方法 */
	ext : {
		/**
		 * 进行复制操作
		 * 
		 * @param {}
		 *            txt
		 * @return {Boolean} true:代表复制成功,false:代表复制不成功!
		 */
		copyToClipBoard : function(txt) {
			if (!txt)
				return false;

			if (window.clipboardData) {
				window.clipboardData.clearData();
				window.clipboardData.setData("Text", txt);
				return true;
			} else if (navigator.userAgent.indexOf("Opera") != -1) {
				window.location = txt;
				return true;
			} else if (window.netscape) {

				try {
					netscape.security.PrivilegeManager
							.enablePrivilege("UniversalXPConnect");
				} catch (e) {
					alert("你的firefox安全限制限制你进行剪贴板操作，请打开'about:config'将signed.applets.codebase_principal_support'设置为true'之后重试");
					return false;
				}
				var clip = Components.classes["@mozilla.org/widget/clipboard;1"]
						.createInstance(Components.interfaces.nsIClipboard);
				if (!clip)
					return false;
				var trans = Components.classes["@mozilla.org/widget/transferable;1"]
						.createInstance(Components.interfaces.nsITransferable);
				if (!trans)
					return false;
				trans.addDataFlavor("text/unicode");
				var str = new Object();
				var len = new Object();
				var str = Components.classes["@mozilla.org/supports-string;1"]
						.createInstance(Components.interfaces.nsISupportsString);
				var copytext = txt;
				str.data = copytext;
				trans.setTransferData("text/unicode", str, copytext.length * 2);
				var clipid = Components.interfaces.nsIClipboard;
				if (!clip)
					return false;
				clip.setData(trans, null, clipid.kGlobalClipboard);
				return true;
			} else {
				try {
					var flashcopier = 'flashcopier';
					if (!document.getElementById(flashcopier)) {
						var divholder = document.createElement('div');
						divholder.id = flashcopier;
						document.body.appendChild(divholder);
					}
					document.getElementById(flashcopier).innerHTML = '';
					var divinfo = '<embed src="/html/inc/_clipboard.swf" FlashVars="clipboard='
							+ encodeURIComponent(txt)
							+ '" width="0" height="0" type="application/x-shockwave-flash"></embed>';
					document.getElementById(flashcopier).innerHTML = divinfo;
					return true;
				} catch (err) {
					alert("抱歉,未实现针对该浏览器的复制操作！");
					return false;
				}

			}
			return false;
		}
	},

	/** 作用于组件的方法 */
	component : {
		/**
		 * 记录文本的录入的 指数
		 * 
		 * @param {}
		 *            文字的内容
		 * @param {}
		 *            elementId 文本的元素id
		 * @param {}
		 *            limited 总字数
		 */
		textCounter : function(content, messageElementId, limited) {
			if (!limited) {
				limited = 1000;
			}
			if (!content)
				return;

			if (!messageElementId) {
				return;
			}
			try {
				if (content.value.length > limited) {
					content.value = content.value.substring(0, limited);
				}
				var txt = "剩余" + (limited - content.value.length) + "字";
				changeHtml(messageElementId, txt)

			} catch (e) {
			}
		},

		/**
		 * 日期控件
		 * 
		 * @param {}
		 *            returnToId 返回选择的日期 到 这个控件中
		 */
		showPickerDateTime : function(returnToId) {
			// {el:'demospan'} 返回指定 对象中显示

			var def = {
				dateFmt : DATE_FORMATE_YMD_HMS
			};

			if (returnToId) {
				if (!ky.util.isEmpty(returnToId)) {
					// 把这个返回控件扩展到参数中
					def.el = returnToId;
				}
			}
			this.showPicker(def);

		},

		showPickerDate : function(returnToId) {
			var def = {
				dateFmt : DATE_FORMATE_YMD
			};
			if (returnToId) {
				if (!ky.util.isEmpty(returnToId)) {
					// 把这个返回控件扩展到参数中
					def.el = returnToId;
				}
			}
			this.showPicker(def);

		},

		showPicker : function(param) {
			var def = {
				isShowWeek : true,
				highLineWeekDay : true
			};

			def = jQuery.extend({}, def, param);
			WdatePicker(def);
			// WdatePicker({
			// dateFmt : 'yyyy-MM-dd',
			// isShowWeek : true,
			// highLineWeekDay : true
			// });
		},

		/**
		 * <input id="cb_1940238" name="message_ids" type="checkbox"
		 * value="1940238" /><br>
		 * <input id="cb_1940239" name="message_ids" type="checkbox"
		 * value="1940239" /><br>
		 * <input id="cb_1940240" name="message_ids" type="checkbox"
		 * value="1940240" /><br>
		 * <input id="cb_1940241" name="message_ids" type="checkbox"
		 * value="1940241" /><br>
		 * <a href="#"
		 * onclick="ky.component.selectAllCheckBox('message_ids',true);return
		 * false;">全选</a><br>
		 * <a href="#"
		 * onclick="ky.component.selectAllCheckBox('message_ids',false);return
		 * false;">不选</a>
		 * 
		 * 
		 * @param {}
		 *            elementName 需要被 check 的控件的 name
		 * @param {boolean}
		 *            boolanValue
		 * @return {Boolean}
		 */
		selectAllCheckBox : function(elementName, boolanValue) {
			changeInputElementAttrByName('checked', elementName, boolanValue);
			return false;
		},

		/**
		 * 复选框的三种选择状态 全选,全不选,反选,
		 * 
		 * @param {String}
		 *            elementName 需要被 check 的控件的 name
		 * @param {String}
		 *            state 三种状态{all:全选,none:全不选,toggle:反选}
		 */
		checkBox3state : function(elementName, state) {
			// alert(elementName + "\n" + state);
			$("input[name='" + elementName + "']").each(function() {
						switch (state) {
							case 'all' :
								$(this).attr("checked", true);
								break;
							case 'none' :
								$(this).attr("checked", false);
								break;
							case 'toggle' :
								$(this).attr("checked", !this.checked);
								break;
						}
					});
		},

		/**
		 * 给一个对象注册 点击事件,可以控制 name为targetElemetnName的其他对象 进行选取操作 <br>
		 * 比如: regSelectAllCheckBoxAction("table_select_all_box",
		 * "commonType_commonid");
		 * 
		 * @param {}
		 *            clickElementId 需要被注册click事件的控件id
		 * @param {}
		 *            targetCheckedElemetnName 需要被 check 的控件的 name
		 */
		regSelectAllCheckBoxAction : function(clickElementId,
				targetCheckedElemetnName) {
			$("#" + clickElementId).click(function() {
				var isChecked = $(this).attr("checked");
				// alert("对象的选取值是:" + isChecked);
				ky.component.selectAllCheckBox(targetCheckedElemetnName,
						isChecked);
			});
		},
		/**
		 * 
		 * @param {}
		 *            clickElementId
		 * @param {String}
		 *            targetCheckedElemetnName 需要被 check 的控件的 name
		 * @param {String}
		 *            selectState 三种状态{all:全选,none:全不选,toggle:反选}
		 * 
		 * 
		 * 
		 */
		regCheckBox : function(clickElementId, targetCheckedElemetnName,
				selectState) {
			$("#" + clickElementId).click(function() {
				ky.component.checkBox3state(targetCheckedElemetnName,
						selectState);
			});
		},

		/**
		 * 清空下拉菜单
		 * 
		 * @param {}
		 *            select1 对象,通过 document.getElementById();来得到
		 */
		clearSelect : function(select1) {
			if (select1) {
				for (var i = select1.length - 1; i >= 0; i--) {
					select1.remove(i);
				}
			} else {
				logTrace();

				log(new Error(100, '错误:参数select1为空!程序返回不进行操作!'));
			}
			// var oOption = new Option("请选择...", "");
			// select1.options[select1.options.length] = oOption;
		},

		/**
		 * 添加数据到 select1 列表框对象
		 * 
		 * @param {}
		 *            select1 对象 通过 document.getElementById();来得到
		 * @param {}
		 *            dataArray 数据数组,json方式
		 * @param {}
		 *            json_displayKey 从json中取得的key值,显示值
		 * @param {}
		 *            json_value 从json中取得的value值
		 */
		addDatas : function(select1, dataArray, json_displayKey, json_value) {
			if (dataArray == null || json_displayKey == null
					|| json_value == null || select1 == null) {

				log(new Error('错误:参数不允许为空!程序返回不进行操作!'));
				return;
			}
			var length = dataArray.length;
			for (var i = 0; i < length; i++) {
				var tmpArray = dataArray[i];
				var oOption = new Option(tmpArray[json_displayKey],
						tmpArray[json_value]);
				select1.options[select1.options.length] = oOption;
			}
		}

	},
	/** 字符串的相关操作* */
	string : {

		/** * 统计指定字符出现的次数 ** */
		occurs : function(text, ch) {
			// var re = eval("/[^"+ch+"]/g");
			// return this.replace(re, "").length;
			return text.split(ch).length - 1;
		},

		/** * 检查是否由数字组成 ** */
		isDigit : function(text) {
			var s = text.Trim();
			return (s.replace(/\d/g, "").length == 0);
		},

		/** * 检查是否由数字字母和下划线组成 ** */
		isAlpha : function(text) {
			return (text.replace(/\w/g, "").length == 0);
		},

		/** * 返回字节数 ** */
		lenb : function(text) {
			return text.replace(/[^\x00-\xff]/g, "**").length;
		},

		/** * 检查是否包含汉字 ** */
		isInChinese : function(text) {
			return (text.length != text.replace(/[^\x00-\xff]/g, "**").length);
		},

		/**
		 * 替换
		 * 
		 * @param {}
		 *            s1
		 * @param {}
		 *            s2
		 * @return {}
		 */
		replaceAll : function(text, s1, s2) {
			return text.replace(new RegExp(s1, "gm"), s2);
		},

		/** * 检查是否有列表中的字符字符 ** */
		isInList : function(text, list) {
			var re = eval("/[" + list + "]/");
			return re.test(text);
		}
	},
	/** 工具方法 */
	util : {

		isEmpty : function(v, allowBlank) {
			return v === null || v === undefined
					|| (!allowBlank ? v === "" : false)
		},

		/**
		 * 判断如果 text 字符串 为空就返回 ''
		 * 
		 * @param {String}
		 *            text
		 */
		sNull : function(text) {
			return this.isEmpty(text) ? "" : text;
		},
		/**
		 * 把戴华 和 刘克亚 2个名字 替换成为 红色
		 * 
		 * @param {}
		 *            text
		 */
		replaceToAdmin : function(text) {
			var t = ky.util.sNull(text);
			t = ky.string.replaceAll(t, "戴华", "<font color='red'>戴华</font>");
			t = ky.string.replaceAll(t, "刘克亚", "<font color='red'>刘克亚</font>");
			return t;
		},

		/**
		 * 例子:<br>
		 * 1,定义 render:<br>
		 * var dvdMoneyStateRenderer=ky.util.mappingRenderer( {'0':'<span
		 * style="color:#0000FF" >未付款</span>','1':'<span style="color:#009900 "
		 * >已付款</span>','2':'<span style="color:#FF0000" >无效汇款</span>'},'未定义' );
		 * 
		 * 2,使用render,<br>
		 * alert( '对于状态1的描述是:'+dvdMoneyStateRenderer('1') ); 程序就会 输出 '<span
		 * style="color:#009900 " >已付款</span>' 这句了.从而达到map的效果
		 * 
		 * 
		 * java: <br>
		 * var awardState=ky.util.mappingRenderer(
		 * <%=EnumEx.getGTMappingRenderer((IState[]) AwardStateEnum.values())%> );
		 * 
		 * @param {}
		 *            json
		 * @param {}
		 *            defaultVal
		 * @return {function} 能够进行 从 json 中取得对应关系的 方式操作
		 */
		mappingRenderer : function(json, defaultVal) {

			return function(jsonKey) {
				if (json == null) {
					return ky.util.sNull(defaultVal);
				} else
					return json[jsonKey]
							|| (defaultVal === undefined || defaultVal === null
									? jsonKey
									: defaultVal);
			};
		},

		/**
		 * 返回一个本次 页面中的不相同的数值
		 */
		getNextId : function() {
			// Math.random()
			SYS_RANDOM_NEXT_ID++;
			return SYS_RANDOM_NEXT_ID;
		}

		,

		/**
		 * money判断函数，允许第一位为"-"来表示欠钱 返回true表示格式正确，返回false表示格式错误
		 */
		isMoney : function(str) {
			if ("" == str) {
				return false;
			}
			var tmp;
			if (str == "")
				return true;
			var re = /^[\-\+]?([0-9]\d*|0|[1-9]\d{0,2}(,\d{3})*)(\.\d+)?$/;
			return re.test(str);
		}

		,
		/**
		 * 数字判断函数，返回true表示是全部数字，返回false表示不全部是数字
		 */
		isNumber : function(str) {
			if ("" == str) {
				return false;
			}
			var reg = /\D/;
			return str.match(reg) == null;
		},
		/**
		 * 压缩空格
		 * 
		 * @param {}
		 *            txt
		 * @return {}
		 */
		trim : function(txt) {
			return $.trim(txt);
		},
		/**
		 * 判断邮箱的格式是否正确, 返回true表示不是邮件,false表示是邮件
		 */
		notEmail : function(strEmail) {
			if (strEmail
					.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
				return false;
			else
				return true;
		},
		/*
		 * 电话判断函数，允许“数字”、“;”、“-”、“(”、”)“， true表示是电话号码
		 */
		isTelephone : function(str) {
			var trueChar = "()-;1234567890";
			if ("" == str) {
				return false;
			}
			for (var i = 0; i < str.length; i++) {
				var c = str.charAt(i); // 字符串str中的字符
				if (trueChar.indexOf(c) == -1)
					return false;
			}
			return true;
		},

		/*
		 * 判断用户名是否为数字、字母、下划线
		 */
		notChinese : function(str) {
			var reg = /[^A-Za-z0-9_]/g
			if (reg.test(str)) {
				return (false);
			} else {
				return (true);
			}
		},
		/*
		 * 中文判断函数，允许生僻字用英文“*”代替 返回true表示是符合条件，返回false表示不符合
		 */
		isChinese : function(str) {
			var badChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			badChar += "abcdefghijklmnopqrstuvwxyz";
			badChar += "0123456789";
			badChar += ".@-_";
			if ("" == str) {
				return false;
			}
			for (var i = 0; i < str.length; i++) {
				var c = str.charAt(i);
				if (badChar.indexOf(c) > -1) {
					return false;
				}
			}
			return true;
		},

		/* 判断是否是数字 */
		checkRate : function(input) {
			var re = /^[0-9]+.?[0-9]*$/;
			if (!re.test(input.rate.value)) {
				alert("请输入数字(例:0.02)");
				input.rate.focus();
				return false;
			}
		},

		/*
		 * 判断给定的字符串是否为指定长度的数字 是返回true，不是返回false
		 */
		isNumber_Ex : function(str, len) {
			if ("" == str) {
				return false;
			}
			if (str.length != len) {
				return false;
			}
			if (!isNumber(str)) {
				return false;
			}
			return true;
		},
		/*
		 * 英文判断函数，返回true表示是全部英文，返回false表示不全部是英文
		 */
		isLetter : function(str) {
			if ("" == str) {
				return false;
			}
			for (var i = 0; i < str.length; i++) {
				var c = str.charAt(i);
				if ((c < "a" || c > "z") && (c < "A" || c > "Z")) {
					return false;
				}
			}
			return true;
		},
		/*
		 * 空格判断，当包含有空格返回false，当不包含一个空格返回true ""不能被判断
		 */
		notInSpace : function(str) {
			if ("" == str) {
				return false;
			}
			var badChar = " ";
			badChar += "　";
			for (var i = 0; i < str.length; i++) {
				var c = str.charAt(i);// 字符串str中的字符
				if (badChar.indexOf(c) > -1) {
					return false;
				}
			}
			return true;
		},
		/*
		 * 发票号判断函数，返回true表示是发票号，返回false表示不符合规范
		 */
		isFPH : function(str) {
			if ("" == str) {
				return false;
			}
			for (var i = 0; i < str.length; i++) {
				var c = str.charAt(i);
				if ((c < "0" || c > "9") && (c != "-") && (c != ",")) {
					return false;
				}
			}
			return true;
		},

		/**
		 * 学制可以为1-7，也可以为3.5这种形式，不能超过7年或者低于1年
		 */
		isXZ : function(str) {
			if ("" == str) {
				return false;
			}
			var reg = /^[1-6](\.5)?$/;
			var r = str.match(reg);
			if (null != r) {
				return true;
			} else {
				if (str == "7") {
					return true;
				} else {
					return false;
				}
			}
		},
		/*
		 * 身份证判断函数，是返回true，不是返回false 15位数字，18位数字或者最后一位为X（大写）
		 */
		isSFZ : function(str) {
			if ("" == str) {
				return false;
			}
			if (str.length != 15 && str.length != 18) {// 身份证长度不正确
				return false;
			}
			if (str.length == 15) {
				if (!isNumber(str)) {
					return false;
				}
			} else {
				var str1 = str.substring(0, 17);
				var str2 = str.substring(17, 18);
				var alpha = "X0123456789";
				if (!isNumber(str1) || alpha.indexOf(str2) == -1) {
					return false;
				}
			}
			return true;
		},

		/**
		 * 清空信息域
		 * 
		 * @param divid
		 */
		clearMsgArea : function(divid) {
			var msgSpan = document.getElementById(divid);
			if (msgSpan != undefined && msgSpan != null) {
				msgSpan.innerHTML = "";
			}
		},

		/**
		 * 判断是否数字 三种调用方式: 1.一个参数,简单判断是否为数字,但长度不超过10位 2.三个参数,第二个参数为 '>'(大于) 或 '<'(小于),第3个参数为要比较的数字
		 * 3.三个参数，第二个参数与第三个参数均为整数，判断传入的第一个参数值是否在他们中间。（含边界）
		 */
		judgeDigit : function() {
			var s = arguments[0];
			if (arguments.length == 1) {
				return isDigit(s);
			} else if (arguments.length == 3) {
				// 通过验证
				var patrn = /^-?[0-9]{1,10}$/;
				if (patrn.test(s)) {
					var p1 = arguments[1];
					var sint = parseInt(s);
					if (isDigit(arguments[2])) {
						var pint = parseInt(arguments[2]);
						if (p1 == '>' || p1 == '<') {
							if (p1 == '>') {
								return sint > pint;
							} else if (p1 == '<') {
								return sint < pint;
							}
						} else if (isDigit(p1)) {
							var pmin = parseInt(p1);
							return (sint >= pmin) && (sint <= pint);
						} else {
							alert('arguments error,the 2nd argument is not a number and not an operation:greater|less|equals.');
						}
					} else {
						alert('arguments error,the 3rd argument is not a number.');
					}
				}
			}
			return false;
		},
		/**
		 * 是否数字
		 */
		isDigit : function(s) {
			var patrn = /^-?[0-9]{1,10}$/;
			return patrn.test(s);
		},
		/**
		 * 判断标识符或是登录名，以字母开头,可带数字、"_"、"." 的字串
		 * 限定最小长度(第二个参数)与最大长度(第三个参数)(默认为2--32位)
		 * 
		 * @param string
		 * @param min
		 *            length
		 * @param max
		 *            length
		 */
		isSignName : function() {
			var s = arguments[0];
			if (arguments.length == 1) {
				var patrn = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){1,31}$/;
				return patrn.test(s);
			} else if (arguments.length == 3) {
				if (isDigit(arguments[1]) && isDigit(arguments[2])) {
					eval("var patrn=/^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){"
							+ (parseInt(arguments[1]) - 1) + ","
							+ (parseInt(arguments[2]) - 1) + "}$/;");
					return eval("patrn.test(s);");
				} else {
					alert('Error:the 2nd argument and the 3rd argument must be number.');
					return false;
				}
			} else {
				alert('method invoke error.error arguments number.');
				return false;
			}
		},
		/**
		 * 判断是否是真实姓名
		 */
		isRealName : function(s) {
			var patrn = /^([a-zA-Z0-9]|[._ ]){2,64}$/; // 英文名
			var p2 = /^([^\x00-\xff]|[\s]){2,32}$/; // 双字节名
			return patrn.test(s) || p2.test(s);
		},

		/**
		 * 电话号码 必须以数字开头，除数字外，可含有"-"
		 */
		isTel : function(s) {
			var patrn = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
			return patrn.test(s);
		},

		/** 中国大陆地区手机号码 以13或15开头，使用时请根据变化修改 校验普通电话，除数字外，可用"-"或空格分开 */
		isMobileCN : function(s) {
			var patrn = /^1[3|5|8]{1}[0-9]{1}[-| ]?\d{8}$/;
			return patrn.test(s);
		},
		/** 中国地区邮编 */
		isPostalCodeCN : function(s) {
			var patrn = /^[1-9]\d{5}$/;
			return patrn.test(s);
		},
		/** Emai */
		isEmail : function(s) {
			if (ky.util.isEmpty(s))
				return false;
			if (s
					.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
				return true;
			else
				return false;

		},

		/** URL */
		isURL : function(s) {
			var patrn = /^http:\/\/([\w-]+(\.[\w-]+)+(\/[\w-   .\/\?%&=\u4e00-\u9fa5]*)?)?$/;
			return patrn.test(s);
		},
		/**
		 * IP
		 */
		isIP : function(s) {
			var patrn = /^((1?\d?\d|(2([0-4]\d|5[0-5])))\.){3}(1?\d?\d|(2([0-4]\d|5[0-5])))$/;
			return patrn.test(s);
		},
		/**
		 * 是否是完整的正则表达式 只有开始标记与结束标记相匹配才为TRUE HTML Tag
		 */
		isHtmlTag : function(s) {
			var patrn = /^<(.*)>.*<\/\1>|<(.*) \/>$/;
			return patrn.test(s);
		},
		/**
		 * 身份证号 这里的省与地区码还没有判断 15位
		 */
		isIDNumber15 : function(s) {
			var patrn = /^[\d]{6}((\d{2}((0[13578]|1[02])(0[1-9]|[12]\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\d|30)|02(0[1-9]|1\d|2[0-8])))|([02468][048]|[13579][26])0229)[\d]{3}$/;
			return patrn.test(s);
		},
		/**
		 * 身份证号 这里的省与地区码还没有判断 18位
		 */
		isIDNumber18 : function(s) {
			var patrn = /^[\d]{6}[0-9]{4}(((0[13578]|(10|12))(0[1-9]|[1-2][0-9]|3[0-1]))|(02(0[1-9]|[1-2][0-9]))|((0[469]|11)(0[1-9]|[1-2][0-9]|30)))[\d]{3}[\d|x|X]$/;
			return patrn.test(s);
		},

		/**
		 * 中文
		 */
		isChineseString : function(s) {
			var patrn = /^[\u4e00-\u9fa5]+$/
			return patrn.test(s);
		},
		/**
		 * 双字节
		 */
		isDoubleByteString : function(s) {
			var patrn = /^[^x00-xff]+$/;
			return patrn.test(s);
		},
		/**
		 * 是否包含首尾空格，如果包含，返回TRUE
		 */
		hasHESpace : function(s) {
			var patrn = /^\s+|\s+$/;
			return patrn.test(s);
		},
		/**
		 * QQ，最大10位，最小5位
		 */
		isQQ : function(s) {
			var patrn = /^[1-9]{1}\d{4,9}$/;
			return patrn.test(s);
		},
		printObject : function(object) {
			try {
				// log("printObject begin......." + ky.json.json(object));
				if (object) {
					var isIn = false;// 进入了内部循环
					for (var i in object) {// 怎样查看某个JAVASCRIPT对象含有哪些方法和属性
						var tmp = object[i];

						// log(ky.json.json(tmp));

						for (var j in tmp) {
							log(i + "," + i + " ----- " + ky.json.json(tmp[j]));
						}
						isIn = true;
					}

					if (isIn) {
						for (var j in object) {
							log(j + " ----- " + object[j]);
						}
					}
				} else {
					log("printObject object==null");
				}
			} catch (err) {
				log(err);
			}

		}

	},
	json : {

		toQueryString : function(A) {
			if (!A || ky.json.type(A, "string", "number")) {
				return A;
			}
			var _ = [];
			for (var C in A) {
				var B = A[C];
				if (B !== undefined) {
					B = [].concat(B);
				}
				for (var D = 0; D < B.length; D++) {
					var _s = B[D];
					if (ky.json.type(_s, "object")) {
						_s = json(_s);
					}
					_
							.push(encodeURIComponent(C) + "="
									+ encodeURIComponent(_s));
				}
			}
			return _.join("&");
		},
		toJSONString : function(_s, _) {
			return ky.json.encode(_s, "__gt_", _);
		},
		decode : function(string, secure) {
			if (!ky.json.type(string, "string") || !string.length) {
				return null;
			}
			if (secure
					&& !/^[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]*$/
							.test(string.replace(/\\./g, "@").replace(
									/"[^"\\\n\r]*"/g, ""))) {
				return null;
			}
			return eval("(" + string + ")");
		},
		encode : function(A, _s, B) {
			var _, C = B ? "\n" : "";
			switch (ky.json.type(A)) {
				case "string" :
					return "\"" + A.replace(/[\x00-\x1f\\"]/g, json__m) + "\"";
				case "array" :
					_ = [];
					ky.json.each(A, function(D, A) {
								var C = ky.json.encode(D, _s, B);
								if (C || C === 0) {
									_.push(C);
								}
							});
					return "[" + C + (B ? _.join("," + C) : _) + "]" + C;
				case "object" :
					if (A === null) {
						return "null";
					}
					_ = [];
					ky.json.each(A, function(C, D) {
								if (!_s || D.indexOf(_s) != 0) {
									var A = ky.json.encode(C, _s, B);
									if (A) {
										_.push(ky.json.encode(D, _s, B) + ":"
												+ A);
									}
								}
							}, null, _s);
					return "{" + C + (B ? _.join("," + C) : _) + C + "}" + C;
				case "number" :
				case "boolean" :
					return String(A);
				default :
					;
			}
			return null;
		},

		type : function(_s) {
			var A = arguments.length;
			if (A > 1) {
				for (var B = 1; B < A; B++) {
					if (ky.json.type(_s) == arguments[B]) {
						return true;
					}
				}
				return false;
			}
			var _ = typeof _s;
			if (_s === null) {
				return "object";
			}
			if (_ == "undefined") {
				return "undefined";
			}
			if (_s.htmlElement) {
				return "element";
			}
			if (_ == "object" && _s.nodeType && _s.nodeName) {
				switch (_s.nodeType) {
					case 1 :
						return "element";
					case 3 :
						return /\S/.test(_s.nodeValue)
								? "textnode"
								: "whitespace";
					default :
						;
				}
			}
			if (isArray(_s)) {
				return "array";
			}
			if (_ == "object" && typeof _s.length == "number") {
				return _s.callee ? "arguments" : "collection";
			} else if (_ == "function" && typeof _s.length == "number"
					&& _s[0] !== undefined) {
				return "collection";
			}
			return _;
		},
		each : function(C, E, D, _s) {
			var A = [];
			if (ky.json.type(C, "array", "arguments", "collection") || C
					&& !ky.json.type(C, "string")
					&& ky.json.type(C.length, "number")) {
				for (var F = 0, B = C.length; F < B; F++) {
					A.push(E.call(D || C, C[F], F, C, _s));
				}
			} else {
				for (var _ in C) {
					A.push(E.call(D || C, C[_], _, C, _s));
				}
			}
			return A;
		},
		rB0 : function(_s) {
			return json__m[_s] || "\\u00"
					+ Math.floor(_s.charCodeAt() / 16).toString(16)
					+ (_s.charCodeAt() % 16).toString(16);
		},

		isArray : function(_s) {
			return Object.prototype.toString.apply(_s) === "[object Array]";
		},

		json : function(_s, _) {
			return ky.json.encode(_s, "__gt_", _);
		}

	}

}

function MM_swapImgRestore() { // v3.0
	var i, x, a = document.MM_sr;
	for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
		x.src = x.oSrc;
}

function MM_preloadImages() { // v3.0
	var d = document;
	if (d.images) {
		if (!d.MM_p)
			d.MM_p = new Array();
		var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
		for (i = 0; i < a.length; i++)
			if (a[i].indexOf("#") != 0) {
				d.MM_p[j] = new Image;
				d.MM_p[j++].src = a[i];
			}
	}
}

function MM_findObj(n, d) { // v4.01
	var p, i, x;
	if (!d)
		d = document;
	if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
		d = parent.frames[n.substring(p + 1)].document;
		n = n.substring(0, p);
	}
	if (!(x = d[n]) && d.all)
		x = d.all[n];
	for (i = 0; !x && i < d.forms.length; i++)
		x = d.forms[i][n];
	for (i = 0; !x && d.layers && i < d.layers.length; i++)
		x = MM_findObj(n, d.layers[i].document);
	if (!x && d.getElementById)
		x = d.getElementById(n);
	return x;
}

function MM_swapImage() { // v3.0
	var i, j = 0, x, a = MM_swapImage.arguments;
	document.MM_sr = new Array;
	for (i = 0; i < (a.length - 2); i += 3)
		if ((x = MM_findObj(a[i])) != null) {
			document.MM_sr[j++] = x;
			if (!x.oSrc)
				x.oSrc = x.src;
			x.src = a[i + 2];
		}
}

/**
 * 
 * @param {}
 *            imageObject 图片对象
 * @param {}
 *            path
 * @param {}
 *            imageId
 */
function changeCheckCode(imageObject, path, imageId) {

	if (imageObject == null)
		return;
	if (imageId == null)
		imageId = '110';
	// alert("haha");
	// 从新刷新验证码内容
	try {
		imageObject.src = path + "/authority/radomImageDisplay.do?imageId="
				+ imageId + "&rm=" + Math.random();
	} catch (err) {

	}
}

/**
 * 
 * @param {}
 *            imageObject 图片对象
 * @param {}
 *            path
 * @param {}
 *            imageId
 */
function changeCheckCodeById(imageElementId, path, imageId) {

	if (imageElementId == null)
		return;
	if (imageId == null)
		imageId = '110';
	// alert("haha");
	// 从新刷新验证码内容
	try {
		var imageUrl = path + "/authority/radomImageDisplay.do?imageId="
				+ imageId + "&rm=" + Math.random();
		getS(imageElementId).attr('src', imageUrl)

	} catch (err) {

	}
}
/**
 * 
 * @param {}
 *            v
 * @param {}
 *            allowBlank
 * @return {}
 */
function isEmpty(v, allowBlank) {
	return ky.util.isEmpty(v, allowBlank);
}

function json(_s, _) {
	return ky.json.json(_s, _);
}
/**
 * 判断是否进行debug
 * 
 * @return {Boolean}
 */
function isdebug() {
	return false;
}

function debug() {
	if (window.console && window.console.log && arguments) {
		var isError = false;
		if (arguments.length >= 1) {
			// 判断 参数是否是错误信息
			// var type=(typeof arguments[0]);

			if (arguments[0]) {
				var type = arguments[0].constructor;
				if (type == TypeError) {
					isError = true;

				}
			}
		}

		var txt = Array.prototype.join.call(arguments, '');
		// 在ie下,不显示调用者,发现ie把调用者的代码都输出了
		var _caller = ($.browser.msie || $.browser.safari) ? '' : // 
				(arguments.callee.caller) ? arguments.callee.caller : '';

		var title = (isError == true) ? "ERROR:------------------------=" : "";

		window.console.log('%s: %o', title + txt, _caller);// this
		// console.log("%s: %o", msg, this);
	}
}
/**
 * 数量信息到控制台中
 * 
 * @param {}
 *            value
 */
function log() {
	// $.fn.ajaxSubmit.debug &&
	if (window.console && window.console.log && arguments) {

		var errTxt = '';
		var isError = false;
		if (arguments.length >= 1) {
			// 判断 参数是否是错误信息
			// var type=(typeof arguments[0]);

			if (arguments[0]) {
				var type = arguments[0].constructor;
				if (type == TypeError || (typeof arguments[0] == "object")) {
					isError = true;

					if (isError === true) {
						var err = arguments[0];
						// var errTxt = "There was an error on this page.\n";

						if (!isNaN(err.description)) {
							errTxt += "\nError description : "
									+ err.description;
						}
						if (!isNaN(err.number)) {
							errTxt += "\nError number : " + err.number;
						}

						errTxt += "\nerr.name : " + err.name;
						errTxt += "\nerr.message : " + err.message;
						errTxt += "\n\nClick OK to continue.\n";

					}
				}

				// window.console.log('%s: %o',"bb--"+( typeof arguments[0] )+"
				// cc=="+(arguments[0].constructor),'');// this
			}
		}

		var txt = Array.prototype.join.call(arguments, '');
		// 在ie下,不显示调用者,发现ie把调用者的代码都输出了
		var _caller = arguments.callee.caller;

		if (true) {
			_caller = ($.browser.msie || $.browser.safari) ? '' : // 
					(arguments.callee.caller) ? arguments.callee.caller : '';
		}
		// var title = (isError == true) ? "ERROR:------------------------=" :
		// "";
		var title = (isError == true) ? errTxt : "";

		window.console.log('%s: %o', title + txt, _caller);// this
		// console.log("%s: %o", msg, this);
	}
}
/**
 * 输出对象的层次
 * 
 * http://getfirebug.com/console.html
 * 
 * @param {}
 *            node
 * 
 * <br>
 * 其他方法,监控时间 console.profile(); <br>
 * 中间是其他的执行代码,这个方法能把 代码时间输出出来! <br>
 * console.profileEnd();
 * 
 */
function logDir(node) {
	console.dir(node);
}
/**
 * 调用轨迹
 */
function logTrace() {
	console.trace();
}

/**
 * 列出元素的html层次结构
 * 
 * @param {}
 *            elementId
 */
function logDirXml(elementId) {
	if (typeof elementId == "string") {
		console.dirxml(document.getElementById(elementId));
	} else {
		console.dirxml(elementId);
	}
}

/**
 * 打开一个窗口
 * 
 * @param {}
 *            url
 * @param {}
 *            w
 * @param {}
 *            h
 */
function openWindow(url, w, h, windowname) {
	var wname = ky.util.isEmpty(windowname) ? "tmpwindow" : windowname;
	var txt = 'width='
			+ w
			+ ', height='
			+ h
			+ ', location=no, menubar=no, status=no, toolbar=no, scrollbars=yes, resizable=yes';
	var popupWin = window.open(url, wname, txt);
	popupWin.focus();
	return popupWin;
}
var __cacheElementId = "__div_cache_";
/**
 * 初始化缓存大小
 */
function _initCache(elementId) {
	if (!elementId) {
		elementId = __cacheElementId;
	}

	if (!exist(elementId)) {
		if (isdebug()) {
			log('创建了缓存对象:' + elementId);
		}
		// log('不存在,创建吧');
		var txt = "<div id='" + elementId + "' style='display:none'></div>"
		$(document.body).append(txt);
	}
}
/**
 * 验证对象是否为空
 * 
 * @param {}
 *            object
 * @return {}
 */
function isEmpty(object) {
	return ky.util.isEmpty(object);
}
/**
 * 1,<br>
 * 从缓存中取得数据(文本数据). var txt=cacheGet(key); <br>
 * 
 * 
 * 
 * 2,<br>
 * 把json数据放到缓存中, cachePut( 'name2', { first : 16, last : "pizza!" });
 * 
 * 
 * 从缓存中取数据 <br>
 * var json=cacheGet('name2');
 * 
 * var first=json.first; var last=last;
 * 
 * @param {String}
 *            key
 * @return {Object} 取决于传人的值, 可以用 obj.属性名 的方式来得到对应的值
 */
function cacheGet(key, elementId) {
	if (isEmpty(key)) {
		return "";
	}
	if (!elementId) {
		elementId = __cacheElementId;
	}
	var $c = getS(elementId);
	return $c.data(key);
}
/**
 * 把data数据放到缓存中!前提,需要在页面中,提供 一个div id是"_div_cache"
 * 
 * 代码为:<div id="__div_cache_" />
 * 
 * @param {String}
 *            key
 * @param {Object}
 *            data ,字符或者json数组等
 */
function cachePut(key, data, elementId) {
	if (!elementId) {
		elementId = __cacheElementId;
	}

	var $c = getS(elementId);
	$c.data(key, data);
}

/**
 * 返回start-end之间的随机数，并取整
 * 
 * @param {}
 *            start
 * @param {}
 *            end
 * @return {}
 */
function randomInt(start, end) {
	return Math.ceil(Math.random() * (end - start) + start)
}

/**
 * 通用视频的接口: 有的浏览器下不是得不到URL地址嘛！ 那做域名的判断就会出现问题。 通过这个返回值来确定你的这个网页是有效的。是我们的网页
 * 
 * @return {Number}
 */
function verify_Uegal() {
	return 1;
}
/**
 * 判断这个元素是否存在
 * 
 * @param {object},可以是
 *            对象,也可以是 string
 * 
 * object
 */
function exist(object) {
	if (object) {
		var type = typeof(object);
		// log(type);
		if (type) {
			// return $("#" + object).length > 0;

			if (isString(object)) {
				return $("#" + object)[0];
			} else {
				return true;
			}
		}
	}
	return false;
}

/**
 * 判断变量是否存在
 * 
 * @param {}
 *            object
 * @return {Boolean}
 */
function existVariable(object) {

	// 由于未定义的对象暂时无法作为参数来传递, 所以, 需要copy下面这句话来进行判断
	// var obj= (typeof hello) == 'string' ? hello : ''
}
/**
 * 工作tab页签中的selectedTagIndex的显示,其他的页签隐藏
 * 
 * @param {int }
 *            selectedTagIndex
 * 
 */
function selectedTab(selectedTagIndex, elementId) {

	if (!elementId)
		return;
	if (!selectedTagIndex)
		return;

	// 不知道为什么,必须要设置2遍才能够用
	// 通过disable来控制 页签的隐藏,隐藏需要定义这个样式
	// .ui-state-disabled{
	// display: none; /* disabled tabs don't show up */
	// }
	// if (exist($.formValidator)) {
	// log('cunz');
	// }
	var tab = $('#' + elementId);
	if (exist(tab.tabs)) {

		____changeTabEnabledDisables(selectedTagIndex, tab);
		____changeTabEnabledDisables(selectedTagIndex, tab);
		tab.tabs('option', 'selected', selectedTagIndex);// 选中 可用
	}

}
/**
 * 设置
 * 
 * @param {int}
 *            selectedTagIndex 选择的页签的索引
 * @param {$.tab
 *            对象,jquery.ui中} tab
 */
function ____changeTabEnabledDisables(selectedTagIndex, tab) {
	var length = tab.tabs('length');
	for (var i = 0; i < length; i++) {
		if (i != selectedTagIndex) {
			tab.tabs('disable', i);

			// log(i + '\tdisable ' + i);
		}
	}

	// log('\t选择=========== ' + selectedTagIndex);
	tab.tabs('enable', selectedTagIndex);
	tab.tabs('select', selectedTagIndex);
}

/**
 * 创建一个演播厅的 调用代码 这个生成的完成是调用方的 调用源码,如果需要直接运行,
 * 
 * 需要 进行html的操作();
 * 
 * 
 * @param {}
 *            pcode
 * @param {}
 *            containId
 */
function createStudioHtml(pcode, containId) {
	if (!pcode) {
		return '';
	}

	var crateTxt = '';
	if ((typeof containId) == 'string' && (!ky.util.isEmpty(containId))) {

	} else {
		containId = 'studio_target_' + randomInt(1000, 1000000);

		crateTxt = "<div id='" + containId + "'></div>\n";

	}
	var arr = [
			"<!--演播厅代码开始-->\n",// 
			"<br/><br/>\n",//
			crateTxt,// 
			"<script type=\"text/javascript\">\n",
			"$(document).ready(function() {\n",//
			"	try {\n",//
			"		$('#" + containId + "').kyStudioPlugin({\n",//
			"				waterMark : (typeof ucode) == 'string' ? ucode : '',\n",//
			"				pcode : '" + pcode + "',\n",//
			"				width : 540,\n",//
			"				showByLayer : false,\n",//
			"				userId :(typeof userId) != 'undefined' ? userId : ''\n",//
			"			});\n",//
			"	} catch (err) {log('11出现error了:' + err);}\n",//
			"});\n",//
			"</script>\n", "<!--演播厅代码结束-->\n"];

	return arr.join('');
}

/**
 * 初始化文本的 fourse 和 mourseover 事件
 * 
 * @param {}
 *            elementId
 */
function bind_focus_mouseover(elementId, defvalue) {
	getS(elementId).bind("mouseover", function() {
				$(this).focus();
			}).bind("focus", function() {
				var t = $(this);
				t.select();
				t.css({
							'color' : "#000"
						});
			}).bind("click", function() {
				var t = $(this);
				if (t.val() == defvalue) {
					// 比如:请输入查询条件
					t.val('');
				}
			});;
}
/**
 * 查找组件对象
 * 
 * @param {}
 *            componentObjOrId
 * @return {Object}
 */
function findComponentObject(componentObjOrId) {
	var comp;
	if (typeof componentObjOrId === 'string') {
		comp = getS(componentObjOrId);
	} else {
		comp = componentObjOrId;
	}
	return comp;
}
/**
 * 组合文本
 * 
 * @param {}
 *            name
 * @param {}
 *            txt
 * @return {}
 */
function _append(name, value) {
	return name + " " + value + "\t";
}

/**
 * Cookie plugin
 * 
 * Copyright (c) 2006 Klaus Hartl (stilbuero.de) Dual licensed under the MIT and
 * GPL licenses: http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 * 
 */

/**
 * Create a cookie with the given name and value and other optional parameters.
 * 
 * @example
 * var userName = ky.util.sNull($.cookie('junhuoku_username'));
 * @example
 * $.cookie('the_cookie', 'the_value');
 * @desc Set the value of a cookie.
 *       @example
 *       $.cookie('the_cookie', 'the_value', {expires: 7, path: '/', domain: 'jquery.com', secure: true});
 * @desc Create a cookie with all available options.
 *       @example
 *       $.cookie('the_cookie', 'the_value');
 * @desc Create a session cookie.
 *       @example
 *       $.cookie('the_cookie', null);
 * @desc Delete a cookie by passing null as value.
 * 
 * @param String
 *            name The name of the cookie.
 * @param String
 *            value The value of the cookie.
 * @param Object
 *            options An object literal containing key/value pairs to provide
 *            optional cookie attributes.
 * @option Number|Date expires Either an integer specifying the expiration date
 *         from now on in days or a Date object. If a negative value is
 *         specified (e.g. a date in the past), the cookie will be deleted. If
 *         set to null or omitted, the cookie will be a session cookie and will
 *         not be retained when the the browser exits.
 * @option String path The value of the path atribute of the cookie (default:
 *         path of page that created the cookie).
 * @option String domain The value of the domain attribute of the cookie
 *         (default: domain of page that created the cookie).
 * @option Boolean secure If true, the secure attribute of the cookie will be
 *         set and the cookie transmission will require a secure protocol (like
 *         HTTPS).
 * @type undefined
 * 
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */

/**
 * Get the value of a cookie with the given name.
 * 
 * @example
 * var userName = ky.util.sNull($.cookie('junhuoku_username'));
 * @example
 * $.cookie('the_cookie');
 * @desc Get the value of a cookie.
 * 
 * @param String
 *            name The name of the cookie.
 * @return The value of the cookie.
 * @type String
 * 
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */
jQuery.cookie = function(name, value, options) {
	if (typeof value != 'undefined') { // name and value given, set cookie
		options = options || {};
		if (value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if (options.expires
				&& (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime()
						+ (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString(); // use expires
			// attribute,
			// max-age is not
			// supported by IE
		}
		var path = options.path ? '; path=' + options.path : '';
		var domain = options.domain ? '; domain=' + options.domain : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [name, '=', encodeURIComponent(value), expires, path,
				domain, secure].join('');
	} else { // only name given, get cookie
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for (var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				// Does this cookie string begin with the name we want?
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie
							.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
};