package com.mpaike.core.util;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
public class ApplicationContextUtil {

	private static ApplicationContextUtil instance = null;

	private WebApplicationContext tempContext = null;

	public static ApplicationContextUtil getInstance() {
		if (instance == null) {
			instance = new ApplicationContextUtil();
		}
		return instance;
	}

	public ApplicationContextUtil() {

	}

	public void setAppContext(WebApplicationContext context) {
		this.tempContext = context;
	}

	public Object getBean(String name) {
		return this.tempContext.getBean(name);
	}

	public WebApplicationContext getAppContext() {
		return this.tempContext;
	}
	
	public static AbstractApplicationContext creatContext() {
		return  new ClassPathXmlApplicationContext(
				"classpath:applicationContext*.xml");
		

	}

}