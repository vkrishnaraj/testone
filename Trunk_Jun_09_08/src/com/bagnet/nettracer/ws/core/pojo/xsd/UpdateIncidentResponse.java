package com.bagnet.nettracer.ws.core.pojo.xsd;

public class UpdateIncidentResponse {
	private String errorResponse;
	private boolean success;
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
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
