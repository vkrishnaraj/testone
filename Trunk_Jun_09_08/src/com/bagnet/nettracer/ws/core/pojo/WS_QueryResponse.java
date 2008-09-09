package com.bagnet.nettracer.ws.core.pojo;

public class WS_QueryResponse {
	private String[] result;
	private String errorResponse;

	
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
	 * @return the result
	 */
	public String[] getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String[] result) {
		this.result = result;
	}
}
