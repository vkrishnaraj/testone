package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import aero.nettracer.fs.model.detection.MatchHistory;

public final class RequestInfoForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private ArrayList<MatchHistory> requestedMatches;
	private String message;
	private String contact;
	private String originalClaimId;
	private String contactName;
	private String contactEmail;
	private String contactPhone;
	
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
	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	
	
}