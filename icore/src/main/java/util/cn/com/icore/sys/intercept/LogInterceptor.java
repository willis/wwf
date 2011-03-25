package cn.com.icore.sys.intercept;

import java.lang.reflect.Method;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import cn.com.icore.sys.model.SystemLog;
import cn.com.icore.sys.service.SystemLogService;
import cn.com.icore.user.model.SysUser;
import cn.com.icore.user.service.LoginControl;


public class LogInterceptor implements MethodBeforeAdvice {
	private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	private SystemLogService systemLogService = null;

	public void setSystemLogService(SystemLogService systemLogService) {
		this.systemLogService = systemLogService;
	}



	public void before(Method method, Object[] params, Object target)
			throws Throwable {

		SysUser sysUser = (SysUser) TransactionSynchronizationManager
		.getResource(LoginControl.USER_OBJ);
		if (sysUser == null) {
			return;
		}
		if ((target instanceof SystemLogService)) {
			return;
		}
	    StringBuffer className = new StringBuffer(target.getClass().getName());
	    className.append(".");
	    className.append(method.getName());

	    String methodName = method.getName();

		SystemLog log = new SystemLog();
		log.setLogtype(SystemLog.TYPE_SYSTEM);
		log.setCreateat(new Date());
		log.setUsername(sysUser.getTruename());
		log.setCreateby(sysUser.getId());
		log.setIp(sysUser.getLoginip());
		log.setLogtitle(target.getClass().getName());
		log.setLogcontent(methodName);
		systemLogService.add(log);
	}

}
