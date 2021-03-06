package com.bagnet.nettracer.tracing.db;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.FsClaim;

@Entity
@DiscriminatorValue("NTCLAIM")
@Proxy(lazy = true)
public class Claim extends FsClaim {
	
	public Claim(){
		super();
	}
	
	public Claim(long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ntIncident_Incident_ID")
	private Incident ntIncident; 
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Claimprorate_ID")
	private ClaimProrate claimprorate;

	@ManyToOne
	@JoinColumn(name = "Status_ID")
	private Status status;
	
	public Incident getNtIncident() {
		return ntIncident;
	}
	
	public void setNtIncident(Incident ntIncident) {
		super.setNtIncidentId(ntIncident.getIncident_ID());
		this.ntIncident = ntIncident;
	}
	
	public ClaimProrate getClaimprorate() {
		return claimprorate;
	}

	public void setClaimprorate(ClaimProrate claimprorate) {
		if (claimprorate != null) {
			super.setClaimProrateId(claimprorate.getClaimprorate_ID());
		}
		this.claimprorate = claimprorate;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		super.setStatusId(status.getStatus_ID());
		this.status = status;
	}
	
	@Transient
	public int getStatusId() {
		return status.getStatus_ID();
	}
	
	public void setStatusId(int statusId) {
		super.setStatusId(statusId);
		status.setStatus_ID(statusId);
	}
	
}
