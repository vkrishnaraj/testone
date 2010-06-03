package com.bagnet.nettracer.tracing.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "Crm")
@Proxy(lazy = false)
public class CrmFile {

	private long id;
	
	private Incident incident;
	
	private String crm_key;
	
	private CRMStatus status;
	
	public enum CRMStatus {
		SUCCESS, FAILED
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "incident_id")
	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	@Column(length = 128, name = "crm_key")
	public String getCrm_key() {
		return crm_key;
	}

	public void setCrm_key(String crm_key) {
		this.crm_key = crm_key;
	}

	@Enumerated(EnumType.STRING)
	public CRMStatus getStatus() {
		return status;
	}

	public void setStatus(CRMStatus status) {
		this.status = status;
	}
	
}
