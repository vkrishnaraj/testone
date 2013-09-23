package com.bagnet.nettracer.tracing.dto;

import java.util.List;

import aero.nettracer.fs.model.FsClaim;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.enums.TemplateType;

public class TemplateAdapterDTO {
	
	private List<TemplateType> types;
	
	private Agent agent;
	private FsClaim claim;
	private Incident incident;
	private LFFound found;
	
	public List<TemplateType> getTypes() {
		return types;
	}

	public void setTypes(List<TemplateType> types) {
		this.types = types;
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

	public LFFound getFound() {
		return found;
	}

	public void setFound(LFFound found) {
		this.found = found;
	}	

}
