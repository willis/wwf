package com.mpaike.bot.spider;

import com.mpaike.util.bot.HTTPSocket;
import com.mpaike.util.bot.IWorkloadStorable;
import com.mpaike.util.bot.Spider;
import com.mpaike.util.bot.SpiderSQLWorkload;

public class SpiderMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 try
		    {
		      IWorkloadStorable wl = new SpiderSQLWorkload(
		        "com.mysql.jdbc.Driver",
		        "jdbc:mysql://127.0.0.1:3306/icore?useUnicode=true&characterEncoding=UTF-8","root","qweasdzxc");

		      ImageReportable ir = new ImageReportable();
		      Spider _spider
		    = new Spider( ir,"http://www.moko.cc",new HTTPSocket(),100,wl);
		    _spider.setMaxBody(200);
		    _spider.start();

		    }
		    catch(Exception e)
		    {
		   
		    }
	}

}
