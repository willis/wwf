package com.mpaike.sys.intercept;

import java.lang.reflect.Method;
import java.util.Date;


import org.apache.struts2.ServletActionContext;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.mpaike.sys.model.SystemLog;
import com.mpaike.sys.service.SystemLogService;
import com.mpaike.user.model.SysUser;
import com.mpaike.user.service.LoginControl;



public class LogInterceptor implements MethodBeforeAdvice {

	private SystemLogService systemLogService = null;

	public void setSystemLogService(SystemLogService systemLogService) {
		this.systemLogService = systemLogService;
	}

	
	public void before(Method method, Object[] params, Object target)
			throws Throwable {


		SysUser sysUser = null;
		
		if(null!=ServletActionContext.getContext())
			sysUser = (SysUser)ServletActionContext.getRequest().getSession().getAttribute(LoginControl.USER_OBJ);
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
