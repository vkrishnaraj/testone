package com.bagnet.nettracer.tracing.db.taskmanager;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("THREEDAYTASK")
@Proxy(lazy = true)
public class ThreeDayTask extends MorningDutiesTask {
	
	public String getDescription() {
		return "Description";
	}
	
	public String getLabel() {
		return "TASK_LABEL_3DAY";
	}
}
