 function myOpen(url,width,height){
	var w_left = screen.width/2-(width/2);
	var w_height = screen.height/2-(height/2);
	var changeWindow = window.open(url,'','scrollbars=yes,width='+width+',height='+height);
	changeWindow.moveTo(w_left,w_height);
}
var myLocationTo = function (url) {
alert(url);
	window.location.href=url;
}
var changeFrame = function(url,frameId){
	if(!frameId){
		frameId = "rFrame"
	}
	document.getElementById(frameId).src=url;
}


