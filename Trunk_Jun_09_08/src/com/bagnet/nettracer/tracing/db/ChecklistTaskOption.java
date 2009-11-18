package com.bagnet.nettracer.tracing.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts.util.MessageResources;

// for auto checklist feature
@Entity
@Table(name = "CHECKLIST_TASK_OPTION")
public class ChecklistTaskOption {
	
	private long id;
	//private long task_id;
	private long order_id;
	private String description;   // to store resource key
	
	private ChecklistTask checklistTask;  // an option is for a task
	
	@Id
	@GeneratedValue
	@Column(name = "Option_ID")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
/*	@Column(nullable = false)
	public long getTask_id() {
		return task_id;
	}
	public void setTask_id(long task_id) {
		this.task_id = task_id;
	}*/
	
	public long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "Task_ID")
	public ChecklistTask getChecklistTask() {
		return checklistTask;
	}
	public void setChecklistTask(ChecklistTask checklistTask) {
		this.checklistTask = checklistTask;
	}
	
	private String dispName;  // to replace description with this key for internationalization
	
	@Transient
	public String getDispName() {
		return setDispNameByKey(description);
	}
	private String setDispNameByKey( String value ){  
		String result = null;
		MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");  
		result = "" + messages.getMessage( value ); 
		
		return result;
	} 
}
