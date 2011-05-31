package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

//for westjet incidents assigned to station within
//last 24 hours and related features
@Entity
@Table(name = "INCIDENT_CONTROL")

public class IncidentControl {
	private long id;
	private Incident incident;
	private Date assignedDate;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Incident.class)
	@JoinColumn(name = "Incident_ID", nullable = false)
	@Fetch(FetchMode.SELECT)
	public Incident getIncident() {
		return incident;
	}
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	public Date getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	
}
