package com.bagnet.nettracer.tracing.db.taskmanager;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Incident;

@Entity
@Proxy(lazy = true)
public class InboundQueueTask extends GeneralTask {
	
	Incident incident;
	
	@ManyToOne(targetEntity = Incident.class, cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "incident_id")
	@Fetch(FetchMode.SELECT)
	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}
}
