package com.bagnet.nettracer.tracing.utils.taskmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.taskmanager.InboundQueueTask;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class UnassignedIncidentElement {
	private Incident incident;
	private List<InboundQueueTask> tasks;
	public Incident getIncident() {
		return incident;
	}
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	public List<InboundQueueTask> getTasks() {
		return tasks;
	}
	public void setTasks(List<InboundQueueTask> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Returns a string representation of the task list associated with the incident
	 * 
	 * @return
	 */
	public String getDispTaskList(){
		String ret = "";
		if(tasks != null){
			List<String> slist = new ArrayList<String>();
			for(InboundQueueTask task:tasks){
				slist.add(task.getDescription());
			}
			if(slist.size() > 0){
				ret += StringUtils.join(slist, "<br/>");
			}
		}
		return ret;
	}
	
	/**
	 * Returns the date of the oldest task associated with the this incident
	 * 
	 * @return
	 */
	public Date getOldestTaskDate(){
		InboundQueueTask task = getOldestTask();
		if(task != null){
			return task.getOpened_timestamp();
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the oldest task associated with the this incident
	 * 
	 * @return
	 */
	public InboundQueueTask getOldestTask(){
		if(tasks != null){
			Date currentOldest = DateUtils.convertToGMTDate(new Date());
			InboundQueueTask oldestTask = null;
			for(InboundQueueTask task:tasks){
				if(task.getOpened_timestamp() != null && task.getOpened_timestamp().before(currentOldest)){
					currentOldest = task.getOpened_timestamp();
					oldestTask = task;
				}
			}
			if(oldestTask != null){
				return oldestTask;
			} 
		} 
		return null;
	}
}
