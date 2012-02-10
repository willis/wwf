package com.mpaike.test;

import org.springframework.context.support.AbstractApplicationContext;

import com.mpaike.bot.model.WebUrl;
import com.mpaike.bot.spider.BotSpider;
import com.mpaike.core.util.ApplicationContextUtil;




public class SpiderTest {

	public static void main(String[] argv){
		testSpider();
		
	}
	
	public static void testSpider(){
		
		AbstractApplicationContext ac = ApplicationContextUtil.getInstance().creatContext();

		BotSpider db = (BotSpider)ac.getBean("botSpider");
		
		WebUrl weburl = new WebUrl();
		weburl.setEnName("moko");
		weburl.setUrl("http://www.moko.cc");
		weburl.setThreadNum(10);
		weburl.setType("");
		weburl.setHeight(500);
		weburl.setWidth(500);
		
		db.startSpider(weburl,true);

	}
}
