package aero.nettracer.serviceprovider.ws_1_0.common;

public class RequestHeader {
	private String username;
	private String password;
	private Parameter parameters;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Parameter getParameters() {
		return parameters;
	}

	public void setParameters(Parameter parameters) {
		this.parameters = parameters;
	}
}
