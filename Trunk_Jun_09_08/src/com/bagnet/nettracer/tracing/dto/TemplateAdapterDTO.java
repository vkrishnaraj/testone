package com.bagnet.nettracer.tracing.dto;

import aero.nettracer.fs.model.FsClaim;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.enums.TemplateType;

public class TemplateAdapterDTO {
	
	private TemplateType type;
	
	private Agent agent;
	private FsClaim claim;
	private Incident incident;
	
	public TemplateAdapterDTO(TemplateType type) {
		this.type = type;
	}
	
	public TemplateType getType() {
		return type;
	}

	public void setType(TemplateType type) {
		this.type = type;
	}

	public Agent getAgent() {
		return agent;
	}
	
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	public FsClaim getClaim() {
		return claim;
	}
	
	public void setClaim(FsClaim claim) {
		this.claim = claim;
	}
	
	public Incident getIncident() {
		return incident;
	}
	
	public void setIncident(Incident incident) {
		this.incident = incident;
	}	

}
