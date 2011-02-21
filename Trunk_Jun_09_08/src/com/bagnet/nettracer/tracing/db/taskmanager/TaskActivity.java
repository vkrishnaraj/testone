package com.bagnet.nettracer.tracing.db.taskmanager;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;

@Entity
@Table(name="taskactivity")
public class TaskActivity {
//	@ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy = "GeneralTask")
//	@hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Incident" column="incident_ID" not-null="false" fetch="select"
	@ManyToOne(targetEntity = GeneralTask.class)
	@JoinColumn(name = "task_id",nullable = false)
	@Fetch(FetchMode.SELECT)
	public GeneralTask getTask() {
		return task;
	}
	public void setTask(GeneralTask task) {
		this.task = task;
	}
	@ManyToOne
	@JoinColumn(name = "agent_ID", nullable = false)
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public Date getCompletetime() {
		return completetime;
	}
	public void setCompletetime(Date completetime) {
		this.completetime = completetime;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	@Id
	@GeneratedValue
	public long getTaskactivity_id() {
		return taskactivity_id;
	}
	public void setTaskactivity_id(long taskactivity_id) {
		this.taskactivity_id = taskactivity_id;
	}
	
	
	GeneralTask task;
	Agent agent;
	long duration;
	Date completetime;
	String resolution;
	long taskactivity_id;
	

}
