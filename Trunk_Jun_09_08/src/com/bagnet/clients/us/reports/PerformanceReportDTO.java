package com.bagnet.clients.us.reports;

public class PerformanceReportDTO {

	private Integer agentcount;
	private Integer optionCounts;
	private String task;
	private String taskOption;
	private String username;

	public Integer getAgentcount() {
		return agentcount;
	}

	public Integer getOptionCounts() {
		return optionCounts;
	}

	public String getTask() {
		return task;
	}

	public String getTaskOption() {
		return taskOption;
	}

	public String getUsername() {
		return username;
	}

	public void setAgentcount(Integer agentcount) {
		this.agentcount = agentcount;
	}

	public void setOptionCounts(Integer optionCounts) {
		this.optionCounts = optionCounts;
	}

	public void setTask(String task) {
		this.task = task;
	}
	public void setTaskOption(String taskOption) {
		this.taskOption = taskOption;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}