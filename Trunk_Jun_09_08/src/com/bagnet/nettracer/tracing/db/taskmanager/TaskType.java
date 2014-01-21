package com.bagnet.nettracer.tracing.db.taskmanager;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="task_type")
public class TaskType implements Serializable {

	private static final long serialVersionUID = 5433654702759772963L;

	@Id
	@GeneratedValue
	private long id;
	
	private int code;
	
	private String description;
	
	public TaskType() { }
	
	public TaskType(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getKey() {
		return "TASK_TYPE_" + getCode();
	}
	
}
