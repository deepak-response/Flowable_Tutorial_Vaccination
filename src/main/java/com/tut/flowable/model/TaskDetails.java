package com.tut.flowable.model;

import java.util.Date;
import java.util.Map;

public class TaskDetails {

    String taskId;
    String taskName;
    
    Date created_date;
    Date updated_date;
    String taskDefKey;

    public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}

	Map<String, Object> taskData;

    public TaskDetails(String taskId, String taskName, Map<String, Object> taskData,Date created_date,Date updated_date,String taskDefKey) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskData = taskData;
        this.created_date=created_date;
        this.updated_date=updated_date;
        this.taskDefKey=taskDefKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Map<String, Object> getTaskData() {
        return taskData;
    }

    public void setTaskData(Map<String, Object> taskData) {
        this.taskData = taskData;
    }
}