package com.bagnet.nettracer.tracing.actions.templates;

public class DocumentTemplateResult {
	
	private boolean success;
	private String messageKey;
	private Object payload;
	
	
	public DocumentTemplateResult() {
		this(false, "");
	}
	
	public DocumentTemplateResult(boolean success) {
		this(success, "");
	}
	
	public DocumentTemplateResult(String messageKey) {
		this(false, messageKey);
	}
	
	public DocumentTemplateResult(boolean success, String messageKey) {
		this.success = success;
		this.messageKey = messageKey;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

}
