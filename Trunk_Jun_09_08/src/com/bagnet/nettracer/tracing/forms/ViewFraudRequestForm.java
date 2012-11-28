package com.bagnet.nettracer.tracing.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import aero.nettracer.fs.model.detection.AccessRequestDTO;

public class ViewFraudRequestForm extends ValidatorForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6174708906888687043L;
	AccessRequestDTO dto;
	String startDate;
	String endDate;
	String message;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public AccessRequestDTO getDto() {
		return dto;
	}
	public void setDto(AccessRequestDTO dto) {
		this.dto = dto;
	}
	
	public void reset(ActionMapping mapping, 
			HttpServletRequest request) {
		if(this.dto != null){
			this.dto.setPending(false);
			this.dto.setApproved(false);
			this.dto.setDenied(false);
		}
	}
}
