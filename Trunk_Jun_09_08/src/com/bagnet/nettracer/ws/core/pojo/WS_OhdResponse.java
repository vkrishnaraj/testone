package com.bagnet.nettracer.ws.core.pojo;

import java.util.Date;

public class WS_OhdResponse {

	private String ohdId;
	private String claimCheckNumber;
	private String errorResponse;
	

	/**
	 * @return the ohdId
	 */
	public String getOhdId() {
		return ohdId;
	}
	/**
	 * @param ohdId the ohdId to set
	 */
	public void setOhdId(String ohdId) {
		this.ohdId = ohdId;
	}

	/**
	 * @return the errorResponse
	 */
	public String getErrorResponse() {
		return errorResponse;
	}
	/**
	 * @param errorResponse the errorResponse to set
	 */
	public void setErrorResponse(String errorResponse) {
		this.errorResponse = errorResponse;
	}
	
	public void setClaimCheckNumber(String claimCheckNumber) {
		this.claimCheckNumber = claimCheckNumber;
	}
	public String getClaimCheckNumber() {
		return claimCheckNumber;
	}
	}
