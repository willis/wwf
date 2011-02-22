package com.mpaike.core.util;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class ApplicationContextUtil {

	private static ApplicationContextUtil instance = new ApplicationContextUtil();

	private static AbstractApplicationContext appContext;

	public static ApplicationContextUtil getInstance() {
		return instance;
	}

	private ApplicationContextUtil() {
		creatContext();
	}

	public AbstractApplicationContext getApplictionContext() {
		return appContext;
	}

	private static void creatContext() {
		ApplicationContextUtil.appContext = new ClassPathXmlApplicationContext(
				"classpath:applicationContext*.xml");
		if (ApplicationContextUtil.appContext == null) {
			throw new NullPointerException("ERROR:ApplictionContext is null.");
		}

	}

}