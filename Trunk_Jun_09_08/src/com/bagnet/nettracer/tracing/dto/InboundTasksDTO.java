package com.bagnet.nettracer.tracing.dto;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;

public class InboundTasksDTO {
	
	private long id;
	
	private Agent assigned_agent;
	private boolean searchUnassignedTasks;
	private Status status;
	private long incidentActivityId;
	
	private int startIndex;
	private int maxResults;
	
	private String sort;
	private String dir;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Agent getAssigned_agent() {
		return assigned_agent;
	}
	public void setAssigned_agent(Agent assigned_agent) {
		this.assigned_agent = assigned_agent;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public boolean isSearchUnassignedTasks() {
		return searchUnassignedTasks;
	}
	public void setSearchUnassignedTasks(boolean searchUnassignedTasks) {
		this.searchUnassignedTasks = searchUnassignedTasks;
	}
	public long getIncidentActivityId() {
		return incidentActivityId;
	}
	public void setIncidentActivityId(long incidentActivityId) {
		this.incidentActivityId = incidentActivityId;
	}
}
