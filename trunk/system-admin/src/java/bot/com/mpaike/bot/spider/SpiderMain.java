package com.mpaike.bot.spider;

import com.mpaike.util.bot.HTTPSocket;
import com.mpaike.util.bot.IWorkloadStorable;
import com.mpaike.util.bot.ImageReportable;
import com.mpaike.util.bot.Spider;
import com.mpaike.util.bot.SpiderSQLWorkload;

public class SpiderMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String driverName =  "com.mysql.jdbc.Driver";
		String jdbcURL = "jdbc:mysql://127.0.0.1:3306/icore?useUnicode=true&characterEncoding=UTF-8";
		String userName = "root";
		String password = "qweasdzxc";
		 try
		    {
		      IWorkloadStorable wl = new SpiderSQLWorkload(driverName,jdbcURL,userName,password);

		      String url = "http://www.moko.cc";
		      ImageReportable ir = new ImageReportable(url,driverName,jdbcURL,userName,password);
		      Spider _spider = new Spider( ir,url,new HTTPSocket(),100,wl);
		      _spider.setMaxBody(200);
		      _spider.start();
		    }
		    catch(Exception e)
		    {
		   
		    }
	}

}
