package com.bagnet.nettracer.tracing.db.taskmanager;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("FIVEDAYTASK")
@Proxy(lazy = true)
public class FiveDayTask extends MorningDutiesTask {
	@Transient
	public String getDescription() {
		return "TASK_DESCRIPTION_5DAY";
	}
	
	@Transient
	public String getLabel() {
		return "TASK_LABEL_5DAY";
	}
	@Transient
	public String getKey() {
		return "5";
	}
	@Transient
	public String getAlert() {
		String s = "Fifth Day Call currently in progress.  Continue working  <a href='GeneralTask.do?loadIncident=" + this.getIncident().getIncident_ID() + "'>" + this.getIncident().getIncident_ID() + "</a>";
		return StringEscapeUtils.unescapeHtml(s);
	}
}
