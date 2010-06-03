package aero.nettracer.serviceprovider.wt_1_0.common;

import aero.nettracer.serviceprovider.ws_1_0.response.GenericResponse;

public class WorldTracerResponse extends GenericResponse {

	private boolean success;
	private String responseId;
	private Ahl ahl;
	ActionFile[] actionFiles = null;
	ActionFileCount[] counts = null;
	private Ohd ohd;
	private String responseData;
	private byte[] captcha;
	private String captchaTimestamp;
	private String connectionRef;

	public String getConnectionRef() {
		return connectionRef;
	}

	public void setConnectionRef(String connectionRef) {
		this.connectionRef = connectionRef;
	}

	public byte[] getCaptcha() {
		return captcha;
	}

	public void setCaptcha(byte[] captcha) {
		this.captcha = captcha;
	}

	public Ahl getAhl() {
		return ahl;
	}

	public Ohd getOhd() {
		return ohd;
	}

	public String getResponseData() {
		return responseData;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setAhl(Ahl ahl) {
		this.ahl = ahl;
	}

	public void setOhd(Ohd ohd) {
		this.ohd = ohd;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setCaptchaTimestamp(String captchaTimestamp) {
		this.captchaTimestamp = captchaTimestamp;
	}

	public ActionFile[] getActionFiles() {
		return actionFiles;
	}


	public void setActionFiles(ActionFile[] actionFiles) {
		this.actionFiles = actionFiles;
	}

	public ActionFileCount[] getCounts() {
		return counts;
	}

	public void setCounts(ActionFileCount[] counts) {
		this.counts = counts;
	}

	public String getCaptchaTimestamp() {
		return captchaTimestamp;
	}

}
