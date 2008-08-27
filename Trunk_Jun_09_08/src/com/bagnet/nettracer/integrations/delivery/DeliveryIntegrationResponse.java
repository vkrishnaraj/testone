package com.bagnet.nettracer.integrations.delivery;

import com.bagnet.nettracer.tracing.db.DeliveryIntegrationType;

public class DeliveryIntegrationResponse {
	private boolean success;
	private String uniqueIntegrationId;
	private String response;
	private DeliveryIntegrationType integrationType;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getUniqueIntegrationId() {
		return uniqueIntegrationId;
	}
	public void setUniqueIntegrationId(String uniqueIntegrationId) {
		this.uniqueIntegrationId = uniqueIntegrationId;
	}
	/**
	 * @return the integrationType
	 */
	public DeliveryIntegrationType getIntegrationType() {
		return integrationType;
	}
	/**
	 * @param integrationType the integrationType to set
	 */
	public void setIntegrationType(DeliveryIntegrationType integrationType) {
		this.integrationType = integrationType;
	}
	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	

}
