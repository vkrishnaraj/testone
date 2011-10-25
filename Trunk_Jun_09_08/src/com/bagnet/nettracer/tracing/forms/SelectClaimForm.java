package com.bagnet.nettracer.tracing.forms;

import java.util.LinkedHashSet;

import org.apache.struts.action.ActionForm;

import aero.nettracer.fs.model.FsClaim;

public final class SelectClaimForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private int type;
	private String incidentId;
	private LinkedHashSet<FsClaim> claims;

	public String getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}

	public LinkedHashSet<FsClaim> getClaims() {
		return claims;
	}

	public void setClaims(LinkedHashSet<FsClaim> claims) {
		this.claims = claims;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}