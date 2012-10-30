
function checkValueInt(value){
	var numberRegex = /^[0-9]*$/;
	return numberRegex.test(value);
}
function MyGetKeyCode(event){ 
  var code; 
  var e =   window.event   ||   event;
  if (e.keyCode){ 
    code = e.keyCode; 
  }else if (e.which){ 
    code = e.which; 
  } 
  return code; 
} 
function MyCheckInt(obj,e) 
{ 
  var code = MyGetKeyCode(e); 
  if ((code <48 || code>57)){
  	alert('not integer');
  	obj.value = 1;
  }
} 
function showPages(name,errorinfo,resource) {
	this.name = name;
	this.inputerror = errorinfo || 'not integer';
	this.argName = 'page';
	this.showTimes = 1;
	this.resource = resource || '../';
	this.firstPage = false;
	this.firstResult = 20;
	this.lastPage = true;
	this.nextPage = 2;
	this.pageNo = 2;
	this.pageSize = 20;
	this.prePage = 1;
	this.totalCount = 0;
	this.totalPage = 1;
}

showPages.prototype.getPage = function(){
	var args = location.search;
	var reg = new RegExp('[\?&]?' + this.argName + '=([^&]*)[&$]?', 'gi');
	var chk = args.match(reg);
	this.page = RegExp.$1;
}
showPages.prototype.checkPages = function(){
	if (isNaN(parseInt(this.page))) this.page = 1;
	if (isNaN(parseInt(this.pageCount))) this.pageCount = 1;
	if (this.page < 1) this.page = 1;
	if (this.pageCount < 1) this.pageCount = 1;
	if (this.page > this.pageCount) this.page = this.pageCount;
	this.page = parseInt(this.page);
	this.pageCount = parseInt(this.pageCount);
}
showPages.prototype.createHtml = function(){
	var strHtml = '';
	strHtml += '每页显示<input id="pageSize"  onblur="javascrip:' + this.name + '.toPage(this.value);" value="'+this.pageSize+'" type="text" style="width:20px; border:solid #CCC 1px">条&nbsp;|&nbsp;共<font color="red">'+this.totalPage+'</font>页 , <font color="red">'+this.totalCount+'</font>条数据&nbsp;|&nbsp;';
	if(this.firstPage){
		strHtml += '<input id="firstPage" type="button" disabled="disabled" name="button" value="首页" style="background:#FFF ; border:none;">&nbsp;<input id="prePage" type="button" disabled="disabled" name="button" value="上一页" style="background:#FFF ; border:none;">&nbsp;';
	}else{
		strHtml += '<input id="firstPage" type="button" onclick="javascript:' + this.name + '.toPage(1);" name="button" value="首页" style="background:#FFF ; border:none; cursor:pointer;">&nbsp;<input id="prePage" type="button" onclick="javascript:' + this.name + '.toPage(' + this.prePage + ');" name="button" value="上一页" style="background:#FFF ; border:none; cursor:pointer;">&nbsp;';

	}
	if(this.lastPage){
		strHtml += '<input id="nextPage" type="button" disabled="disabled" name="button" value="下一页" style="background:#FFF ; border:none;">&nbsp;<input id="lastPage" type="button" disabled="disabled" name="button" value="尾页" style="background:#FFF ; border:none;">&nbsp;';
	}else{
		strHtml += '<input id="nextPage" onclick="javascript:' + this.name + '.toPage(' + this.nextPage + ');" type="button" name="button" value="下一页" style="background:#FFF ; border:none; cursor:pointer;">&nbsp;<input id="lastPage" type="button" onclick="javascript:' + this.name + '.toPage(' + this.totalPage + ');" name="button" value="尾页" style="background:#FFF ; border:none; cursor:pointer;">&nbsp;';
	}
	strHtml += '&nbsp;|&nbsp;第<input id="Pg" value="'+this.pageNo+'" type="text" style="width:20px; border:solid #CCC 1px" onblur="javascrip:' + this.name + '.toPage(this.value);">页';
return strHtml;
}
showPages.prototype.createUrl = function (page) {
	if (isNaN(parseInt(page))) page = 1;
	if (page < 1) page = 1;
	if (page > this.totalPage) page = this.totalPage;
	var url = location.protocol + '//' + location.host + location.pathname;
	var args = location.search;
	var reg = new RegExp('([\?&]?)' + this.argName + '=[^&]*[&$]?', 'gi');
	args = args.replace(reg,'$1');
	if (args == '' || args == null) {
		args += '?' + this.argName + '=' + page;
	} else if (args.substr(args.length - 1,1) == '?' || args.substr(args.length - 1,1) == '&') {
			args += this.argName + '=' + page;
	} else {
			args += '&' + this.argName + '=' + page;
	}
	return url + args;
}
showPages.prototype.toPage = function(page){
	var turnTo = 1;
	if (typeof(page) == 'object') {
		turnTo = page.options[page.selectedIndex].value;
	} else {
		turnTo = page;
	}
	self.location.href = this.createUrl(turnTo);
}
showPages.prototype.printHtml = function(){
	this.getPage();
	this.checkPages();
	this.showTimes += 1;
	document.write(this.createHtml());
	//document.write('<div id="pages_' + this.name + '_' + this.showTimes + '" align="center"></div>');
	//document.getElementById('pages_' + this.name + '_' + this.showTimes).innerHTML = this.createHtml();
	
}
showPages.prototype.formatInputPage = function(e){
	var ie = navigator.appName=="Microsoft Internet Explorer"?true:false;
	if(!ie) var key = e.which;
	else var key = event.keyCode;
	if (key == 8 || key == 46 || (key >= 48 && key <= 57)) return true;
	return false;
}