package com.bagnet.nettracer.tracing.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//for auto checklist feature
@Entity
@Table(name = "INCIDENT_CHECKLIST")
public class IncidentChecklist {
	private long id;
	private Incident incident;
	private ChecklistTask checklistTask;
	private ChecklistTaskOption checklistTaskOption;
	private Agent agent;
	private Date timestamp;

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.Incident.class)
	@JoinColumn(name = "Incident_ID", nullable = false)
	public Incident getIncident() {
		return incident;
	}
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.ChecklistTask.class)
	@JoinColumn(name = "Task_ID", nullable = false)
	
	public ChecklistTask getChecklistTask() {
		return checklistTask;
	}
	public void setChecklistTask(ChecklistTask checklistTask) {
		this.checklistTask = checklistTask;
	}
	
	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.ChecklistTaskOption.class)
	@JoinColumn(name = "Option_ID", nullable = false)
	public ChecklistTaskOption getChecklistTaskOption() {
		return checklistTaskOption;
	}
	public void setChecklistTaskOption(ChecklistTaskOption checklistTaskOption) {
		this.checklistTaskOption = checklistTaskOption;
	}
	
	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.Agent.class)
	@JoinColumn(name = "agent_Agent_ID", nullable = true)
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toString() {
		String result;
		StringBuffer mySb = new StringBuffer();
		mySb.append("id=" + id);
		mySb.append("incident_id=" + incident.getIncident_ID());
		mySb.append("checklistTask_id=" + checklistTask.getId());
		result = "" + mySb.toString();
		return result;
	}
}
