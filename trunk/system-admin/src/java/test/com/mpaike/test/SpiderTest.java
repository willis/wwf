package com.mpaike.test;

import org.springframework.context.support.AbstractApplicationContext;

import com.mpaike.bot.spider.BotSpider;
import com.mpaike.core.util.ApplicationContextUtil;




public class SpiderTest {

	public static void main(String[] argv){
		testSpider();
		
	}
	
	public static void testSpider(){
		
		AbstractApplicationContext ac = ApplicationContextUtil.getInstance().getApplictionContext();

		BotSpider db = (BotSpider)ac.getBean("botSpider");
		
		db.startSpider("http://www.moko.cc", "moko.cc", 10);

	}
}
