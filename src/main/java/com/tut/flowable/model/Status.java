package com.tut.flowable.model;

public class Status {

	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public String getStatusRejection() {
		return StatusRejection;
	}
	public void setStatusRejection(String statusRejection) {
		StatusRejection = statusRejection;
	}
	private StatusEnum status;
	private String StatusRejection ;
}
