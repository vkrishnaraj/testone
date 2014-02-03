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
	private boolean tbi;
	private String positionId; 
	private boolean lateCheckIndicator;
	private String ohdId;
	
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
	public boolean isTbi() {
		return tbi;
	}
	public void setTbi(boolean tbi) {
		this.tbi = tbi;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public boolean isLateCheckIndicator() {
		return lateCheckIndicator;
	}
	public void setLateCheckIndicator(boolean lateCheckIndicator) {
		this.lateCheckIndicator = lateCheckIndicator;
	}
	public String getOhdId() {
		return ohdId;
	}
	public void setOhdId(String ohdId) {
		this.ohdId = ohdId;
	}
	private String positiveIdInd;
	
}
