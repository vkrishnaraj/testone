package com.bagnet.nettracer.tracing.db.taskmanager;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;

@Entity
@DiscriminatorValue("INCACTIVITYTASK")
@Proxy(lazy = true)
public class IncidentActivityTask extends GeneralTask {

	private IncidentActivity incidentActivity;
	
	@ManyToOne(targetEntity = IncidentActivity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "incidentActivityId", nullable = false)
	public IncidentActivity getIncidentActivity() {
		return incidentActivity;
	}
	
	public void setIncidentActivity(IncidentActivity incidentActivity) {
		this.incidentActivity = incidentActivity;
	}

}
