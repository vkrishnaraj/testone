package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.action.ActionForm;

public class CaptchaForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String captcha_text;

	public String getCaptcha_text() {
		return captcha_text;
	}

	public void setCaptcha_text(String captcha_text) {
		this.captcha_text = captcha_text;
	}

}
