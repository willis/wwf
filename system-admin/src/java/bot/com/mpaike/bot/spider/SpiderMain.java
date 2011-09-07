package com.mpaike.bot.spider;

import com.mpaike.image.dao.IPictureDao;
import com.mpaike.util.bot.HTTPSocket;
import com.mpaike.util.bot.IWorkloadStorable;
import com.mpaike.util.bot.Spider;
import com.mpaike.util.bot.SpiderSQLWorkload;

public class SpiderMain {

	/**
	 * @param args
	 */
	public static void startSpider(String driverName,String jdbcURL,String userName,String password,IPictureDao pictureDao) {
		 try
		    {
			 ImageReportable.IMAGES_PATH= "D:\\data\\images\\";
		      IWorkloadStorable wl = new SpiderSQLWorkload(driverName,jdbcURL,userName,password);

		      String url = "http://www.moko.cc";
		      ImageReportable ir = new ImageReportable(url,driverName,jdbcURL,userName,password,null);
		      Spider _spider = new Spider( ir,url,new HTTPSocket(),10,wl);
		      //_spider.setMaxBody(200);
		      _spider.start();
		    }
		    catch(Exception e)
		    {
		    		System.out.println("爬虫启动失败："+e);
		    }
	}

}
