package aero.nettracer.serviceprovider.ws_1_0.response;

import aero.nettracer.serviceprovider.ws_1_0.common.WebServiceError;

public class GenericResponse {
	private WebServiceError error = null;
	private boolean success;

	public WebServiceError getError() {
		return error;
	}

	public void setError(WebServiceError error) {
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
