package cn.com.icore.util.app;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;

public class ApplictionContext {
	private static ApplictionContext instance = null;

	private WebApplicationContext tempContext = null;

	public static ApplictionContext getInstance() {
		if (instance == null) {
			instance = new ApplictionContext();
		}
		return instance;
	}

	public ApplictionContext() {

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

	public Object getBeanTest(String name) {

		AbstractApplicationContext appContext;
		appContext = new ClassPathXmlApplicationContext(
				"classpath:applicationContext*.xml");
		if (appContext == null) {
			throw new NullPointerException("ERROR:ApplictionContext is null.");
		}
		return appContext.getBean(name);
	}
}
