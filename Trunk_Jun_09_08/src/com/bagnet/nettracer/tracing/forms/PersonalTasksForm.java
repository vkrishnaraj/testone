package com.bagnet.nettracer.tracing.forms;

import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.taskmanager.InboundQueueTask;
import com.bagnet.nettracer.tracing.dto.InboundTasksDTO;

public class PersonalTasksForm extends ValidatorForm{
	
	private static final long serialVersionUID = 1957255050740201393L;

	private List<InboundQueueTask> taskList;
	private InboundTasksDTO dto;
	private InboundQueueTask nextTask;
	private boolean hasNextTask;
	
	private String currpage;
	private String nextpage;
	private String prevpage;
	private String pagination;
	
	public List<InboundQueueTask> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<InboundQueueTask> taskList) {
		this.taskList = taskList;
	}

	public InboundTasksDTO getDto() {
		return dto;
	}

	public void setDto(InboundTasksDTO dto) {
		this.dto = dto;
	}

	public String getCurrpage() {
		return currpage;
	}

	public void setCurrpage(String currpage) {
		this.currpage = currpage;
	}

	public String getNextpage() {
		return nextpage;
	}

	public void setNextpage(String nextpage) {
		this.nextpage = nextpage;
	}

	public String getPrevpage() {
		return prevpage;
	}

	public void setPrevpage(String prevpage) {
		this.prevpage = prevpage;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public InboundQueueTask getNextTask() {
		return nextTask;
	}

	public void setNextTask(InboundQueueTask nextTask) {
		this.nextTask = nextTask;
	}

	public boolean isHasNextTask() {
		return hasNextTask;
	}

	public void setHasNextTask(boolean hasNextTask) {
		this.hasNextTask = hasNextTask;
	}
	
}
