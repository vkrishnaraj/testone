package aero.nettracer.serviceprovider.ws_1_0.common;

public class WebServiceError {

	private String description;
	private String systemResponse;

	public WebServiceError(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSystemResponse() {
		return systemResponse;
	}

	public void setSystemResponse(String systemResponse) {
		this.systemResponse = systemResponse;
	}

}
