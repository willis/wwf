package com.mpaike.util.app;


import org.springframework.context.ApplicationContext;



public class ApplictionContext {
	private static ApplictionContext instance = null;

	private ApplicationContext tempContext = null;

	public static ApplictionContext getInstance() {
		if (instance == null) {
			instance = new ApplictionContext();
		}
		return instance;
	}

	public ApplictionContext() {

	}

	public void setAppContext(ApplicationContext context) {
		this.tempContext = context;
	}

	public Object getBean(String name) {
		return this.tempContext.getBean(name);
	}

	public ApplicationContext getAppContext() {
		return this.tempContext;
	}
}
