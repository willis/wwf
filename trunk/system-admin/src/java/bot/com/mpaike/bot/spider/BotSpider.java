package com.mpaike.bot.spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.mpaike.bot.model.WebUrl;
import com.mpaike.image.dao.IPictureDao;
import com.mpaike.util.bot.HTTPSocket;
import com.mpaike.util.bot.IWorkloadStorable;
import com.mpaike.util.bot.Spider;
import com.mpaike.util.bot.SpiderSQLWorkload;

public class BotSpider{
	
	private static String path;
	private IPictureDao pictureDao;
	private DataSource dataSource;
	private Map<String,Spider> spiderMap = new HashMap<String,Spider>();

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setPictureDao(IPictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}



	public String getCurrentUrl(String url){
		Spider spider = spiderMap.get(url);
		if(spider!=null){
			return spider.getCurrentUrl();
		}
		return "没有爬取";
	}

	/**
	 * @param args
	 */
	public void startSpider(WebUrl weburl,boolean restart) {
		if(weburl==null){
			return;
		}
		Spider sd = spiderMap.get(weburl.getUrl());
		if(sd!=null){
			sd.halt();
		}
		 try{
			 	IWorkloadStorable wl = new SpiderSQLWorkload(dataSource.getConnection(),weburl.getEnName(),restart);
			 	ImageReportable ir = new ImageReportable(weburl.getUrl(),path,weburl.getEnName(),dataSource.getConnection(),pictureDao,weburl.getType(),weburl.getWidth(),weburl.getHeight());
			 	Spider spider = new Spider( ir,weburl.getUrl(),new HTTPSocket(),weburl.getThreadNum(),wl);
			 	spiderMap.put(weburl.getUrl(), spider);
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
			spiderMap.remove(url);
		}
		System.out.println("********* spider stop = "+url+" **********");
	}
	
	public List<String> spiderLog(String url){
		Spider spider = spiderMap.get(url);
		List<String> list = new ArrayList<String>();
		if(spider!=null){
			String[] log = spider.getSpiderLog().toArray(new String[0]);
			for(int i=log.length-1;i>=0;i--){
				list.add(log[i]);
			}
		}
		return list;
	}
	

}
