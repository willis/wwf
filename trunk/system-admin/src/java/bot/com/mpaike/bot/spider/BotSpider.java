package com.mpaike.bot.spider;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.mpaike.image.dao.IPictureDao;
import com.mpaike.util.bot.HTTPSocket;
import com.mpaike.util.bot.IWorkloadStorable;
import com.mpaike.util.bot.Spider;
import com.mpaike.util.bot.SpiderSQLWorkload;

public class BotSpider {
	
	@Autowired
	private IPictureDao pictureDao;
	@Autowired
	private DataSource dataSource;
	
	private Map<String,Spider> spiderMap = new HashMap<String,Spider>();

	/**
	 * @param args
	 */
	public void startSpider(String url,String path,int theardNum) {
		if(url==null){
			return;
		}
		 try{
			 	IWorkloadStorable wl = new SpiderSQLWorkload(dataSource);
			 	ImageReportable ir = new ImageReportable(url,path,dataSource,pictureDao);
			 	Spider spider = new Spider( ir,url,new HTTPSocket(),theardNum,wl);
			 	spiderMap.put(url, spider);
			 	//spider.setMaxBody(200);
			 	spider.start();
		    }
		    catch(Exception e)
		    {
		    		System.out.println("爬虫启动失败："+e);
		    }
	}
	
	public void haltSpider(String url){
		Spider spider = spiderMap.get(url);
		if(spider!=null){
			spider.halt();
		}
	}

}
