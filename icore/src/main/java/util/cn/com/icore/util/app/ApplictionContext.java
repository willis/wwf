package cn.com.icore.util.app;


import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class ApplictionContext {
	private static ApplictionContext instance = new ApplictionContext();

	private static AbstractApplicationContext appContext;
	

	
	
	public static ApplictionContext getInstance() {
		return instance;
	}

	private ApplictionContext() {
		creatContext();
	}

	public Object getBean(String name){
		return getApplictionContext().getBean(name);
	}
	public AbstractApplicationContext getApplictionContext() {
		return appContext;
	}

	private static void creatContext() {
		ApplictionContext.appContext = new ClassPathXmlApplicationContext(
				"classpath:applicationContext*.xml");
		if (ApplictionContext.appContext == null) {
			throw new NullPointerException("ERROR:ApplictionContext is null.");
		}

	}
}
