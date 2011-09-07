package com.mpaike.bot.action;

import com.mpaike.util.app.BaseAction;

public class SpiderAction extends BaseAction {
	
	private String ajaxContent;
	
	public String startSpider(){
		getBotSpider().startSpider("http://www.moko.cc", "moko.cc", 10);
		return "start_spider";
	}
	
	public String spiderProcess(){
		ajaxContent = getBotSpider().getCurrentUrl("http://www.moko.cc");
		return "ajax_content";
	}

	//set,get
	public String getAjaxContent() {
		return ajaxContent;
	}

	public void setAjaxContent(String ajaxContent) {
		this.ajaxContent = ajaxContent;
	}

}
