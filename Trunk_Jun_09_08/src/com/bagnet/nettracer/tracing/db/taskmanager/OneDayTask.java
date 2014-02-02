package com.bagnet.nettracer.tracing.db.taskmanager;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("1DAYTASK")
@Proxy(lazy = true)
public class OneDayTask extends MorningDutiesTask {
	
	@Transient
	public String getDescription() {
		return "TASK_DESCRIPTION_1DAY";
	}
	
	@Transient
	public String getLabel() {
		return "TASK_LABEL_1DAY";
	}
	
	@Transient
	public String getKey() {
		return "1";
	}
	@Transient
	public String getAlert() {
		String s = "First Day Call currently in progress.  Continue working  <a href='GeneralTask.do?loadIncident=" + this.getIncident().getIncident_ID() + "'>" + this.getIncident().getIncident_ID() + "</a>";
		return StringEscapeUtils.unescapeHtml(s);
	}
}
