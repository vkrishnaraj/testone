package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import aero.nettracer.fs.model.detection.MatchHistory;

public final class RequestInfoForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private ArrayList<MatchHistory> requestedMatches;
	private String message;
	private String originalClaimId;
	
	public ArrayList<MatchHistory> getRequestedMatches() {
		return requestedMatches;
	}

	public void setRequestedMatches(ArrayList<MatchHistory> requestedMatches) {
		this.requestedMatches = requestedMatches;
	}

	public String getOriginalClaimId() {
		return originalClaimId;
	}
	
	public void setOriginalClaimId(String originalClaimId) {
		this.originalClaimId = originalClaimId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
}