package com.bagnet.nettracer.tracing.db.taskmanager;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("FOURDAYTASK")
@Proxy(lazy = true)
public class FourDayTask extends MorningDutiesTask {
	@Transient
	public String getDescription() {
		return "TASK_DESCRIPTION_4DAY";
	}
	
	@Transient
	public String getLabel() {
		return "TASK_LABEL_4DAY";
	}
	@Transient
	public String getKey() {
		return "4";
	}
}
