package com.bagnet.nettracer.wt.connector;

public class WebServiceDto {

	private String connectionReference;
	private byte[] captcha;
	private String captchaTimestamp;
	private String captchaText;
	private boolean useAvailableConnectionsIfAvailable;
	private boolean isCronUser;

	public byte[] getCaptcha() {
		return captcha;
	}

	public void setCaptcha(byte[] captcha) {
		this.captcha = captcha;
	}

	public String getConnectionReference() {
		return connectionReference;
	}

	public void setConnectionReference(String connectionReference) {
		this.connectionReference = connectionReference;
	}

	public String getCaptchaTimestamp() {
		return captchaTimestamp;
	}

	public void setCaptchaTimestamp(String captchaTimestamp) {
		this.captchaTimestamp = captchaTimestamp;
	}

	public String getCaptchaText() {
		return captchaText;
	}

	public void setCaptchaText(String captchaText) {
		this.captchaText = captchaText;
	}

	public boolean isUseAvailableConnectionsIfAvailable() {
		return useAvailableConnectionsIfAvailable;
	}

	public void setUseAvailableConnectionsIfAvailable(
	    boolean useAvailableConnectionsIfAvailable) {
		this.useAvailableConnectionsIfAvailable = useAvailableConnectionsIfAvailable;
	}

	public boolean isCronUser() {
		return isCronUser;
	}

	public void setCronUser(boolean isCronUser) {
		this.isCronUser = isCronUser;
	}

	public void reset() {
		connectionReference = null;
		captcha = null;
		captchaTimestamp = null;
		captchaText = null;
  }

}
