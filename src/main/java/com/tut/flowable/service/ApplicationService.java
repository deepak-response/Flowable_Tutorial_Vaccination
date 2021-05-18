package com.tut.flowable.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tut.flowable.model.VaccinationObj;
import com.tut.flowable.model.TaskDetails;
import com.tut.flowable.model.TaskInfo;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	RepositoryService repositoryService;

	public void deployProcessDefinition() {

		Deployment deployment = repositoryService.createDeployment().addClasspathResource("testProcess.bpmn20.xml")
				.deploy();
	}

	public void initiateWorkflow(VaccinationObj vaccinationObj, String processDefKey) throws FlowableException, FlowableObjectNotFoundException {

		System.out.println("initiating new process in Flowable Workflow for Vaccination for user: "+vaccinationObj.getName());

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("name", vaccinationObj.getName());
		variables.put("kyc", vaccinationObj.getKyc());
		variables.put("age", vaccinationObj.getAge());
		variables.put("state", vaccinationObj.getState());
		variables.put("jobTitle", vaccinationObj.getTitle());
		variables.put("status", vaccinationObj.getStatus());

		try{
			ProcessInstance processInstance =
					runtimeService.startProcessInstanceByKey(processDefKey, variables) ;

			System.out.println("Process Instance Id-" + processInstance.getProcessInstanceId() + " is created successfully!");

		}
		catch (Exception e) { //
			e.printStackTrace();
			System.out.println("Error while creating process instance id !!!");
		}

	}


	public void completeUserTask(TaskInfo taskInfo)
			throws FlowableException, FlowableObjectNotFoundException {

		String userAction = taskInfo.getUserOutcome();

		Map<String, Object> variables = new HashMap<String, Object>();

		variables.put("userOutcome", userAction);
		taskService.complete(taskInfo.getTaskId(), variables);
		System.out.println("Task is completed successfully: " + taskInfo.getTaskId());
	}

	public void claimTask(TaskInfo taskInfo) throws FlowableException, FlowableObjectNotFoundException {

		String taskId = taskInfo.getTaskId();
		String userId = taskInfo.getAssignee();
		taskService.claim(taskId, userId);
		System.out.println("Task-" + taskId + " is claimed successfully by user: " + userId);
	}

	public void unclaimTask(String taskId) throws FlowableException, FlowableObjectNotFoundException {

		taskService.unclaim(taskId);
		System.out.println("Task-" + taskId + " is unclaimed successfully!");
	}



	public List<TaskDetails> getUserTasks() {

		List<Task> tasks = taskService.createTaskQuery().taskUnassigned().list();
		List<TaskDetails> taskDetails = getTaskDetails(tasks);

		return taskDetails;
	}
	
	public List<Task> getAllTasks() {

		List<Task> tasks = taskService.createTaskQuery().active().list();
		//List<TaskDetails> taskDetails = getTaskDetails(tasks);

		return tasks;
	}


	private List<TaskDetails> getTaskDetails(List<Task> tasks) {
		List<TaskDetails> taskDetails = new ArrayList<>();
		for (Task task : tasks) {
			Map<String, Object> processVariables = taskService.getVariables(task.getId());
			taskDetails.add(new TaskDetails(task.getId(), task.getName(), processVariables,task.getCreateTime(),task.getDueDate(),task.getTaskDefinitionKey()));
		}
		return taskDetails;
	}


	private String getUserOutcomeVariableforTask(String taskId){

		String taskDef = "";
		String userOutcomeVariable = "";
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		taskDef = task.getTaskDefinitionKey();
		System.out.println("Task Definition is: "+taskDef);
		switch (taskDef)
		{
			case "AWT_CAND_CONSENT" :
				userOutcomeVariable= "candidateConsent";
				break;
			case "PND_INTERVIEW":
				userOutcomeVariable= "interviewFeedback";
				break;
			default:
				userOutcomeVariable= "userOutcome";
		}

		System.out.println("User Outcome Variable for Task#  : "+taskDef+ " is: " +userOutcomeVariable);

		return  userOutcomeVariable;
	}



}
