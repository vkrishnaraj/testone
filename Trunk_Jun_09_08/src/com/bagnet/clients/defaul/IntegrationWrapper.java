package com.bagnet.clients.defaul;

import java.util.ArrayList;

import com.bagnet.nettracer.tracing.db.BagDrop;

public class IntegrationWrapper {

	private String errorMessage;
	private String pnrContents;
	private ArrayList<BagDrop> flightInfo;
	
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

	public ArrayList<BagDrop> getFlightInfo() {
		return flightInfo;
	}

	public void setFlightInfo(ArrayList<BagDrop> flightInfo) {
		this.flightInfo = flightInfo;
	}
	
	
}
