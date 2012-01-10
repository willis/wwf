package com.mpaike.test;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.impl.test.SpringActivitiTestCase;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;


public class ActivitiWithSpringTest extends AbstractSpringTest {
	
    @Test
    public void testAll(){
      
        Deployment deployment = repositoryService.createDeployment()
          .addClasspathResource("activiti/FinancialReportProcess.bpmn20.xmll")
          .deploy();
        
        runtimeService = applicationContext.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financialReport");
        
        //List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("webcat").list();
        
        //System.out.println("tasks=========" + tasks.size());
        
    }
	@Override
	protected void beforeTest() throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void afterTest() throws Exception {
		// TODO Auto-generated method stub
		
	}
}