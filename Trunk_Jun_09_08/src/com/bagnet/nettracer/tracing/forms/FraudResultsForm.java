package com.bagnet.nettracer.tracing.forms;

import java.util.Set;

import org.apache.struts.action.ActionForm;

import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.detection.MatchHistory;

public final class FraudResultsForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private long claimId;

	private Set<MatchHistory> primaryResults;
	private Set<MatchHistory> secondaryResults;

	public long getClaimId() {
		return claimId;
	}

	public void setClaimId(long claimId) {
		this.claimId = claimId;
	}

	public Set<MatchHistory> getPrimaryResults() {
		return primaryResults;
	}

	public void setPrimaryResults(Set<MatchHistory> primaryResults) {
		this.primaryResults = primaryResults;
	}

	public Set<MatchHistory> getSecondaryResults() {
		return secondaryResults;
	}

	public void setSecondaryResults(Set<MatchHistory> secondaryResults) {
		this.secondaryResults = secondaryResults;
	}
	
}