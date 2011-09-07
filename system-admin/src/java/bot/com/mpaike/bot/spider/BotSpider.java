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
	
	private String path;
	private IPictureDao pictureDao;
	private DataSource dataSource;
	
	private Map<String,Spider> spiderMap = new HashMap<String,Spider>();

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public IPictureDao getPictureDao() {
		return pictureDao;
	}

	public void setPictureDao(IPictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getCurrentUrl(String url){
		Spider spider = spiderMap.get(url);
		if(spider!=null){
			return spider.getCurrentUrl();
		}
		return null;
	}

	/**
	 * @param args
	 */
	public void startSpider(String url,String enName,int threadNum) {
		if(url==null){
			return;
		}
		Spider sd = spiderMap.get(url);
		if(sd!=null){
			sd.halt();
		}
		 try{
			 	IWorkloadStorable wl = new SpiderSQLWorkload(dataSource);
			 	ImageReportable ir = new ImageReportable(url,path+enName+"/",dataSource,pictureDao);
			 	Spider spider = new Spider( ir,url,new HTTPSocket(),threadNum,wl);
			 	spiderMap.put(url, spider);
			 	spider.setMaxBody(200);
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
