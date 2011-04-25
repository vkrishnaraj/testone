package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.Set;

import org.apache.struts.action.ActionForm;

import aero.nettracer.fs.model.detection.MatchHistory;

public final class FraudResultsForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private long claimId;

	private ArrayList<MatchHistory> primaryResults;
	private ArrayList<MatchHistory> secondaryResults;

	public long getClaimId() {
		return claimId;
	}

	public void setClaimId(long claimId) {
		this.claimId = claimId;
	}

	public ArrayList<MatchHistory> getPrimaryResults() {
		return primaryResults;
	}

	public void setPrimaryResults(ArrayList<MatchHistory> primaryResults) {
		this.primaryResults = primaryResults;
	}

	public ArrayList<MatchHistory> getSecondaryResults() {
		return secondaryResults;
	}

	public void setSecondaryResults(ArrayList<MatchHistory> secondaryResults) {
		this.secondaryResults = secondaryResults;
	}

	
}