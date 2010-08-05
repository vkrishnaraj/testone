package com.bagnet.nettracer.tracing.db.taskmanager;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Agent;

@Entity
@Table(name="task")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="task_type", discriminatorType = DiscriminatorType.STRING, length=16)
public class GeneralTask {
	@Id
	@GeneratedValue
	public long getTask_id() {
		return task_id;
	}
	public void setTask_id(long taskId) {
		task_id = taskId;
	}
	public Date getOpened_timestamp() {
		return opened_timestamp;
	}
	public void setOpened_timestamp(Date openedTimestamp) {
		opened_timestamp = openedTimestamp;
	}
	public Date getClosed_timestamp() {
		return closed_timestamp;
	}
	public void setClosed_timestamp(Date closedTimestamp) {
		closed_timestamp = closedTimestamp;
	}
	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@ManyToOne
	@JoinColumn(name = "agent_ID", nullable = false)
	public Agent getAssigned_agent() {
		return assigned_agent;
	}
	public void setAssigned_agent(Agent assignedAgent) {
		assigned_agent = assignedAgent;
	}
	
	long task_id;
	Date opened_timestamp;
	Date closed_timestamp;
	Status status;
	Agent assigned_agent;

	
}
