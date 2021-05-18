
package com.tut.flowable.controllers;

import java.util.List;

import com.tut.flowable.model.VaccinationObj;
import com.tut.flowable.model.TaskDetails;
import com.tut.flowable.model.TaskInfo;
import com.tut.flowable.service.ApplicationService;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@ComponentScan("com.tut.flowable.service")
public class ProcessController {

	Logger logger = LoggerFactory.getLogger(ProcessController.class);

	@Autowired
	ApplicationService applicationService;

	@PostMapping("/deploy")
	public void deployWorkflow()
	{
		applicationService.deployProcessDefinition();
	}

	@PostMapping("/createVaccinationRequest")
	public void createProcessInstance(@RequestBody VaccinationObj vaccinationObj)
			throws FlowableException, FlowableObjectNotFoundException {
		String processDefKey = "process";

		try{
			applicationService.initiateWorkflow(vaccinationObj,processDefKey);
		}
		catch (FlowableObjectNotFoundException flw) { //
			logger.info("Error while creating process id in workflow");
		}
	}


	@PostMapping("/completeTask")
	public void completeTask(@RequestBody TaskInfo taskInfo)  throws FlowableException, FlowableObjectNotFoundException {


		try {
			if (null == taskInfo.getTaskId()) {
				System.out.println("TaskId can't be null to complete a userTask");
			}
				applicationService.completeUserTask(taskInfo);

		} catch (FlowableObjectNotFoundException flw) { //
			System.out.println("TaskId doesn't exists");
		}
	}

	@PostMapping("/claimTask")
	public void claimTask(@RequestBody TaskInfo taskInfo) throws FlowableException, FlowableObjectNotFoundException {

		try {
			if (null == taskInfo.getTaskId()) {
				System.out.println("TaskId can't be null to claim a userTask");
			}
				applicationService.claimTask(taskInfo);

		} catch (FlowableObjectNotFoundException flw) { //
			System.out.println("TaskId doesn't exists");
		}

	}

	@PostMapping("/unclaimTask")
	public void unclaimTask(@RequestBody TaskInfo taskInfo)
			throws FlowableException, FlowableObjectNotFoundException {


		try {
			if (null == taskInfo.getTaskId()) {
				System.out.println("TaskId can't be null to claim a userTask");
			}
				applicationService.unclaimTask(taskInfo.getTaskId());

		} catch (FlowableObjectNotFoundException flw) { //
			logger.info("TaskId doesn't exists");
		}
	}

	@GetMapping("/getPendingTasks")
	public List<TaskDetails> getUserTasks() {
		return applicationService.getUserTasks();
	}

}
