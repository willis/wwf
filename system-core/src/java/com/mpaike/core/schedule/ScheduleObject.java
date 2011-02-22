package com.mpaike.core.schedule;

import java.util.Map;

import org.quartz.JobDetail;


public class ScheduleObject {
	
	private String className;
	private String cronExpression;
	private String groupName;
	private Map scheduleContext;
	private JobDetail jobDetail;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public Map getScheduleContext() {
		return scheduleContext;
	}
	public void setScheduleContext(Map scheduleContext) {
		this.scheduleContext = scheduleContext;
	}
	
	

}
