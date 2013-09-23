package com.bagnet.nettracer.ws.wn.onhandscanning.pojo;

import com.bagnet.nettracer.ws.core.pojo.WS_OHD;

public class ServiceResponse {
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isValidUser() {
		return validUser;
	}
	public void setValidUser(boolean isValidUser) {
		this.validUser = isValidUser;
	}
	public WS_OHD getOnhand() {
		return onhand;
	}
	public void setOnhand(WS_OHD onhand) {
		this.onhand = onhand;
	}
	public String[] getError() {
		return error;
	}
	public void setError(String[] error) {
		this.error = error;
	}
	private boolean success;
	private boolean validUser;
	private WS_OHD onhand;
	private String[] error;
	private String claimCheckInd;
	private BagDrop bagDrop;
	private String createUpdateIndicator;
	private String returnStatus;
	private String assoicatedIncidentId;
	private boolean TBI;
	
	public String getClaimCheckInd() {
		return claimCheckInd;
	}
	public void setClaimCheckInd(String claimCheckInd) {
		this.claimCheckInd = claimCheckInd;
	}
	public String getPositiveIdInd() {
		return positiveIdInd;
	}
	public void setPositiveIdInd(String positiveIdInd) {
		this.positiveIdInd = positiveIdInd;
	}
	public BagDrop getBagDrop() {
		return bagDrop;
	}
	public void setBagDrop(BagDrop bagDrop) {
		this.bagDrop = bagDrop;
	}
	public String getCreateUpdateIndicator() {
		return createUpdateIndicator;
	}
	public void setCreateUpdateIndicator(String createUpdateIndicator) {
		this.createUpdateIndicator = createUpdateIndicator;
	}
	public String getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public String getAssoicatedIncidentId() {
		return assoicatedIncidentId;
	}
	public void setAssoicatedIncidentId(String assoicatedIncidentId) {
		this.assoicatedIncidentId = assoicatedIncidentId;
	}
	public boolean isTBI() {
		return TBI;
	}
	public void setTBI(boolean tBI) {
		TBI = tBI;
	}
	private String positiveIdInd;
	
}
