package com.bagnet.nettracer.ws.onlineclaims;

import com.bagnet.nettracer.ws.core.pojo.WS_PVAdvancedIncident;

public class PassengerView {
	
	private boolean authenticationSuccess;
	private long claimId;
	private WS_PVAdvancedIncident data;
	
	public boolean isAuthenticationSuccess() {
  	return authenticationSuccess;
  }

	public void setAuthenticationSuccess(boolean authenticationSuccess) {
  	this.authenticationSuccess = authenticationSuccess;
  }

	public WS_PVAdvancedIncident getData() {
  	return data;
  }

	public void setData(WS_PVAdvancedIncident data) {
  	this.data = data;
  }

	public long getClaimId() {
  	return claimId;
  }

	public void setClaimId(long claimId) {
  	this.claimId = claimId;
  }
}
