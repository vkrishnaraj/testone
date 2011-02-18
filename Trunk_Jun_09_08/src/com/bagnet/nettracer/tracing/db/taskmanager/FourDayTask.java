package com.bagnet.nettracer.tracing.db.taskmanager;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("FOURDAYTASK")
@Proxy(lazy = true)
public class FourDayTask extends MorningDutiesTask {
	
	public String getDescription() {
		return "Description";
	}
	
	public String getLabel() {
		return "TASK_LABEL_4DAY";
	}
}
