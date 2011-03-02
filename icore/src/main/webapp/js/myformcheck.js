// js验证v1.0 author tianbao created 2007-04-13 
// 使用说时：input 对象的 checkInfo 的格式为: 标题;是否为空;正则选项
// checkForm用来验证表单
// 可按规则进行扩展

var FormCheck = {
	Version : '1.1.2',
	Browser : {
		IE : !!(window.attachEvent && !window.opera),
		Opera : !!window.opera,
		WebKit : navigator.userAgent.indexOf('AppleWebKit/') > -1,
		Gecko : navigator.userAgent.indexOf('Gecko') > -1
				&& navigator.userAgent.indexOf('KHTML') == -1,
		MobileSafari : !!navigator.userAgent.match(/Apple.*Mobile.*Safari/)
	},
	patrns : {},
	loadPatrns : function() {// 加载权限验证正则
		this.patrns['patrn1'] = /^\d*$/ // 非负整数（正整数 + 0）
		this.patrns['patrn2'] = /^[0-9]*[1-9][0-9]*$/ // 正整数
		this.patrns['patrn3'] = /^((-\d+)|(0+))$/ // 非正整数（负整数 + 0）
		this.patrns['patrn4'] = /^-[0-9]*[1-9][0-9]*$/ // 负整数
		this.patrns['patrn5'] = /^-?\d+$/ // 正整数
		this.patrns['patrn6'] = /^\d+(\.\d+)?$/ // 非负浮点数（正浮点数 + 0)
		this.patrns['patrn7'] = /^((-\d+(\.\d+)?)|(0+(\.0+)?))$/ // 非正浮点数（负浮点数
		// + 0）
		this.patrns['patrn8'] = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/ // 负浮点数
		this.patrns['patrn9'] = /^(-?\d+)(\.\d+)?$/ // 浮点数
		this.patrns['patrn10'] = /^[A-Za-z]+$/ // 26个英文字母
		this.patrns['patrn11'] = /^[A-Z]+$/ // 26个大写英文字母
		this.patrns['patrn12'] = /^[a-z]+$/ // 26个小写英文字母
		this.patrns['patrn13'] = /^[A-Za-z0-9]+$/ // 数字与字母组成
		this.patrns['patrn14'] = /^\w+$/ // 由数字、26个英文字母或者下划线组成的字符串
		this.patrns['patrn15'] = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/ // email地址
		this.patrns['patrn15'] = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/ // email地址
		this.patrns['patrn16'] = /^\d{4}-[0-1][0-9]-[0-3][0-9]\s[0-2][0-9]:[0-6][0-9]$/ // 日期验证，格度为
		// yyyy-MM-dd
		// HH:mm
		this.patrns['patrn17'] = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/ // 正浮点数
		this.patrns['patrn18'] = /^[A-Z|a-z]{4}[0-9]{7}$/ // 数值必须为4个字母+7个数字
		this.patrns['patrn19'] = /^.{2000}$/ // 数值必须为4个字母+7个数字
		this.patrns['patrn20'] = /\'|\"/ // 验证是否有单引号或双引号
	},
	addPatrn : function(n, v) {// 添加新的权限验证正则,用于自己在页面进行临时扩展
		this.patrns[n] = v;
	},// 给需要验证的表单的必填项进行处理
	createCheckForm : function(form) {
		var els = form.elements;
		for (var i = 0; i < els.length; i++) {
			var eobj = els[i];
			var checkInfo = (FormCheck.Browser.IE == true
					? eobj.checkInfo
					: eobj.getAttribute("checkInfo"));// 得到检验信息
			if (checkInfo == undefined)
				continue;
			// 得取到相应的信息
			var checks = checkInfo.split(";");
			c_title = checks[0];
			c_isnull = checks[1].toUpperCase();
			c_option = checks[2].toLowerCase();
			if (c_isnull == "NOTNULL") {
				// alert(c_isnull);
				var span = document.createElement("span");
				eobj.parentNode.appendChild(span);
				span.innerHTML = "<font color='red'>*</font>";
			}
		}
	}
}
// 加载权限验证的正则
FormCheck.loadPatrns();
// 验证是否为空
function check_null(str) {
	return str.replace(/^\s*|\s*$/g, "") == "";
}
// 验证是否为数字
function check_option(str, value) {
	if (str.toUpperCase() == "NO")
		return;
	return !FormCheck.patrns[str].test(value);
	// return patrn.exec(value)==null; //返回查找到的数组
	// return value.match(patrn)==null; //返回查找到的数组
}
function checkInput() {
	var _event = (arguments.length == 0 ? event : arguments[0]);
	_event.cancelBubble = true;// 取消事件的冒泡对象
	var eobj = (_event.srcElement ? _event.srcElement : _event.target);// 获取触发事件的对象。
	// var checkInfo = eobj.checkInfo;//得到检验信息
	var checkInfo = (FormCheck.Browser.IE == true ? eobj.checkInfo : eobj
			.getAttribute("checkInfo"));// 得到检验信息
	var dispobj = (arguments.length == 2
			? arguments[1]
			: eobj.parentNode.nextSibling.lastChild);// 找到显示验证信息的对象
	var c_title = "";// 标题
	var c_isnull = "";// 是否为空
	var c_option = "";// 检查项
	var c_value = "";// 检查内容
	// 得取到相应的信息
	var checks = checkInfo.split(";");
	c_title = checks[0];
	c_isnull = checks[1].toUpperCase();
	c_option = checks[2].toLowerCase();
	c_value = eobj.value;
	// 非空校验
	if (c_isnull == "NOTNULL") {
		if (check_null(c_value)) {
			dispobj.innerHTML = c_title + "不能为空";
			dispobj.style.color = "red";
			return false;
		} else {
			// 正则验证
			if (check_option(c_option, c_value)) {
				dispobj.innerHTML = c_title + "格式不正确";
				dispobj.style.color = "red";
				return false;
			} else {
				dispobj.innerHTML = "";
				dispobj.style.color = "green";
				return true;
			}
		}
	} else {

		if (!check_null(c_value)) {
			// 正则验证
			if (check_option(c_option, c_value)) {
				dispobj.innerHTML = c_title + "格式不正确";
				dispobj.style.color = "red";
				return false;
			} else {
				dispobj.innerHTML = "";
				dispobj.style.color = "green";
				return true;
			}
		} else {
			dispobj.innerHTML = "";
			dispobj.style.color = "green";
			return true;
		}
	}
}
// 表单元素验证
function checkElment(eobj) {
	var checkInfo = (FormCheck.Browser.IE == true ? eobj.checkInfo : eobj
			.getAttribute("checkInfo"));// 得到检验信息
	// alert(checkInfo);
	// return false;
	if (checkInfo == undefined)
		return new FormMessage('不进行验证', '不进行验证', true);
	if ((FormCheck.Browser.IE == true ? eobj.checkInfo : eobj
			.getAttribute("notcheck")) == "true") {
		return new FormMessage('不进行验证', '不进行验证', true);
	}
	// var dispobj = eobj.parentNode.nextSibling.lastChild;//找到显示验证信息的对象
	var c_title = "";// 标题
	var c_isnull = "";// 是否为空
	var c_option = "";// 检查项
	var c_value = "";// 检查内容
	// 得取到相应的信息
	var checks = checkInfo.split(";");
	c_title = checks[0];
	c_isnull = checks[1].toUpperCase();
	c_option = checks[2].toLowerCase();
	c_value = eobj.value;
	// 非空校验
	if (c_isnull == "NOTNULL") {
		if (check_null(c_value)) {

			return new FormMessage(c_title, '不能为空', false);
		} else {
			// 正则验证
			if (check_option(c_option, c_value)) {

				return new FormMessage(c_title, '格式不正确', false);
			} else {

				return new FormMessage(c_title, '验证通过', true);
			}
		}
	} else {
		if (!check_null(c_value)) {
			// 正则验证
			if (check_option(c_option, c_value)) {

				return new FormMessage(c_title, '格式不正确', false);
			} else {

				return new FormMessage(c_title, '验证通过', true);
			}
		}
		return new FormMessage(c_title, '验证通过', true);
	}
}
function FormMessage(title, content, hasPass) {
	this.title = title;
	this.content = content;
	this.hasPass = hasPass;
	this.toString = function() {
		var message = "验证信息：[" + title + "]" + content;
		return message;
	}
}
// 表单验证
function checkForm(form) {
	var els = form.elements;
	for (var i = 0; i < els.length; i++) {
		var eobj = els[i];
		var formMessage = checkElment(eobj);
		if (!(formMessage.hasPass)) {
			alert(formMessage.toString());
			eobj.focus();
			return false;
		}
	}
	return true;
}
String.prototype.trim = function() {
	return this.replace(/^\s*|\s*$/g, "");
}
Date.prototype.getMaxMM=function(){
	var month = this.getMonth()+1;
	if(month<10)
		month = "0"+month;
	return month;
}
Date.prototype.getMaxDD=function(){
	var d = this.getDate()
	if(d<10)
		d = "0"+d;
	return d;
}
Date.prototype.getMaxHH=function(){
	var h = this.getHours()
	if(h<10)
		h = "0"+h;
	return h;
}
Date.prototype.getMaxSS=function(){
	var s = this.getSeconds()
	if(s<10)
		s = "0"+s;
	return s;
}
