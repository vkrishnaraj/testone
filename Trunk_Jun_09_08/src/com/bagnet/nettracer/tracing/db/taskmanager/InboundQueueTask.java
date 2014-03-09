package com.bagnet.nettracer.tracing.db.taskmanager;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.InboundQueue;

@Entity
@Proxy(lazy = true)
public class InboundQueueTask extends GeneralTask {
	
	private InboundQueue inboundqueue;
	private TaskType taskType;

	@ManyToOne(targetEntity = InboundQueue.class, cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "inboundqueue_id")
	@Fetch(FetchMode.SELECT)
	public InboundQueue getInboundqueue() {
		return inboundqueue;
	}

	public void setInboundqueue(InboundQueue inboundqueue) {
		this.inboundqueue = inboundqueue;
	}

	@ManyToOne
	@JoinColumn(name="task_type_id")
	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
	
	@Transient
	public boolean isInbound(){
		return this instanceof InboundTask;
	}
	
	@Transient
	public boolean isAcaa(){
		return this instanceof AcaaTask;
	}
	
	@Transient
	public boolean isDamaged(){
		return this instanceof DamagedTask;
	}
	
}
