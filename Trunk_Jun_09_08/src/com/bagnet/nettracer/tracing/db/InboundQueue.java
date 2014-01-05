package com.bagnet.nettracer.tracing.db;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.communications.Activity;

@Entity
@Table(name = "inboundqueue")
@Proxy(lazy = false)
public class InboundQueue {
	
	private long id;
	
	Incident incident;
	
	private Activity activity;
	private long incidentActivityId;
	
	@ManyToOne(targetEntity = Incident.class, cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "incident_id")
	@Fetch(FetchMode.SELECT)
	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	@ManyToOne
	@JoinColumn(name = "activity_id", nullable = true)
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public long getIncidentActivityId() {
		return incidentActivityId;
	}

	public void setIncidentActivityId(long incidentActivityId) {
		this.incidentActivityId = incidentActivityId;
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
