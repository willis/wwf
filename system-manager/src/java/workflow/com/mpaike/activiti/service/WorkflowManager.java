/**
 * 
 */
package com.mpaike.activiti.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
/**
 * @author ChenH
 * @date 2012-1-9
 * TODO
 */
public interface WorkflowManager {
	
	public Object getFormByProcessDefinition(String pdid);
	
	public Object submitFormData(String pdid, Map<String, String> data);
	
	/**
	 * 部署流程定义
	 * @param file
	 */
	ProcessDefinition deploy(String name, InputStream file);
	
	/**
	 * 获取所有部署记录
	 * @return
	 */
	public List<Deployment> listAllDeployment();
	
	/**
	 * 挂起流程定义
	 * @param processDefinitionId 流程定义ID
	 */
	void suspendProcessDefinition(String processDefinitionId);
	
	/**
	 * 激活流程定义
	 * @param processDefinitionId 流程定义ID
	 */
	void resumeProcessDefinition(String processDefinitionId);
	
	/**
	 * 获取系统所有的流程定义
	 * @return
	 */
	List<ProcessDefinition> listAllProcessDefinition();
	
	/**
	 * 根据流程定义获取流程实例列表
	 * @param id
	 * @return
	 */
	public List<ProcessInstance> getProcessInstanceByProcessDefinitionId(String id);
	
	/**
	 * 获取可以创建项目的流程定义	
	 * @return
	 */
	List<ProcessDefinition> listAvailableProcessDefinition();
	
	/**
	 * 按照ProcessDefinition的Key进行分组,并返回映射对
	 * @return
	 */
	Map<String, String> listProcessDefinitionGroupByKey();
	
	/**
	 * 得到指定的流程定义
	 * @param processDefinitionId 流程ID
	 * @return
	 */
	ProcessDefinition getProcessDefinitionById(String processDefinitionId);
	
	/**
	 * 得到指定的流程定义
	 * @param processDefinitionKey 流程Key
	 * @return
	 */
	ProcessDefinition getProcessDefinitionByKey(String processDefinitionKey);
	
	/**
	 * 删除流程定义
	 * @param processDefinitionId
	 */
	void deleteProcessDefinition(String processDefinitionId);
	
	/**
	 * 获取流程定义图
	 * @param processDefinitionId
	 * @return
	 */
	InputStream getProcessDefinitionGraphById(String processDefinitionId);
	
	/**
	 * 获取流程定义图
	 * @param processDefinitionKey
	 * @return
	 */
	InputStream getProcessDefinitionGraphByKey(String processDefinitionKey);
	
	/**
	 * 创建项目实例,并返回项目实例
	 * @param processDefinitionId
	 * @param processInstanceKey
	 * @param project
	 * @param variables
	 * @return
	 */
	ProcessInstance createProcessInstance(String processDefinitionId, String processInstanceKey, Map<String, Object> variables);
	
	
	ProcessInstance createProcessInstanceByKey(String processDefinitionKey, String processInstanceKey, Map<String, Object> variables);
	
	/**
	 * 删除项目实例
	 * @param processInstanceKey 流程Key
	 * @param reason 原因
	 * @return
	 */
	void deleteProcessInstanceByKey(String processInstanceKey, String reason);
	
	/**
	 * 删除项目实例
	 * @param processInstanceKey 流程Id
	 * @param reason 原因
	 * @return
	 */
	void deleteProcessInstanceById(String processInstanceId, String reason);
	
	/**
	 * 获取指定的流程实例
	 * @param processInstanceId
	 * @return
	 */
	ProcessInstance getProcessInstanceById(String processInstanceId);
	
	/**
	 * 
	 * @param processInstanceKey
	 * @return
	 */
	ProcessInstance getProcessInstanceByKey(String processInstanceKey);
	
	/**
	 * 
	 * @param processInstanceKey
	 * @return
	 */
	HistoricProcessInstance getHistoryProcessInstanceByKey(String processInstanceKey);
	
	/**
	 * 获取指定用户的任务列表
	 * @param username
	 * @return
	 */
	List<Task> getTaskListByUsername(String username);
	
	/**
	 * 获取指定任务的流出节点
	 * @param taskId
	 * @return
	 */
	Set<String> getTaskOutcomes(String taskId);
	
	/**
	 * 完成任务
	 * @param taskId
	 * @param variables
	 * @param comment
	 */
	public void completeTask(String taskId, Map<String, Object> variables, String comment);
	
	/**
	 * 获取审批意见
	 * @param taskId
	 * @return
	 */
	List<Comment> getTaskComment(String taskId); 	
	/**
	 * 任务
	 * @param processInstanceId
	 * @return
	 */
	//List<HistoryTaskInstanceImpl> getHistoryTaskInstanceList(List<String> taskIds);
	
	/**
	 * 获取一个流程实例的历史任务
	 * @param processInstanceId
	 * @return
	 */
	//List<HistoryTask> getHistoryTaskList(String processInstanceId);
	
	/**
	 * 获取一个流程实例关联的任务
	 * @param processInstanceId
	 * @return
	 */
	List<Task> getTaskByProcessInstanceId(String processInstanceId);
	
	/**
	 * 获取指定的任务
	 * @param taskId
	 * @return
	 */
	Task getTaskById(String taskId);
	
	/**
	 * 获取指定的任务
	 * @param taskId
	 * @param userId
	 */
	void takeTaskById(String taskId, String userId);
	
	/**
	 * 获取活动坐标
	 * @param name
	 * @return
	 */
	//ActivityCoordinates getActivityCoordinatesByName(String processDefinitionId, String name);
	
	/**
	 * 生成流程监控图
	 */
	void generateProcessGraph(String processDefinitionId, String processInstanceId, OutputStream output) throws IOException;
	
	/**
	 * 
	 * @param taskId
	 * @param userId
	 */
	void assignTask(String taskId, String userId);
}
