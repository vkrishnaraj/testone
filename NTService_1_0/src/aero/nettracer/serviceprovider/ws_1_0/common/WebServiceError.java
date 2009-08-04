package aero.nettracer.serviceprovider.ws_1_0.common;

public class WebServiceError {
	
	public static final String USER_NOT_AUTHORIZED = "USER NOT AUTHORIZED";
	public static final String CONFIGURATION_ERROR = "CONFIGURATION ERROR";
	public static final String UNEXPECTED_EXCEPTION = "UNEXPECTED EXCEPTION ENCOUNTERED";
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
