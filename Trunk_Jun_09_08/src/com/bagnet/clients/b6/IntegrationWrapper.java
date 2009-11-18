package com.bagnet.clients.b6;

public class IntegrationWrapper {

	private String errorMessage;
	private String pnrContents;
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the pnrContents
	 */
	public String getPnrContents() {
		return pnrContents;
	}

	/**
	 * @param pnrContents
	 *            the pnrContents to set
	 */
	public void setPnrContents(String pnrContents) {
		this.pnrContents = pnrContents;
	}
}
