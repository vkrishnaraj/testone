package com.bagnet.nettracer.tracing.db.taskmanager;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("TWODAYTASK")
@Proxy(lazy = true)
public class TwoDayTask extends MorningDutiesTask {
	
	@Transient
	public String getDescription() {
		return "TASK_DESCRIPTION_2DAY";
	}
	
	@Transient
	public String getLabel() {
		return "TASK_LABEL_2DAY";
	}
}
