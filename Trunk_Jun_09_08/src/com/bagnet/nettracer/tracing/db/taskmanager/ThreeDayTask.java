package com.bagnet.nettracer.tracing.db.taskmanager;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("THREEDAYTASK")
@Proxy(lazy = true)
public class ThreeDayTask extends MorningDutiesTask {
	
	@Transient
	public String getDescription() {
		return "TASK_DESCRIPTION_3DAY";
	}
	
	@Transient
	public String getLabel() {
		return "TASK_LABEL_3DAY";
	}
	@Transient
	public String getKey() {
		return "3";
	}
}
