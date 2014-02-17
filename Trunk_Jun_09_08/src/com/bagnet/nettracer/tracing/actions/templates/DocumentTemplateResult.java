package com.bagnet.nettracer.tracing.actions.templates;

import java.util.ArrayList;
import java.util.List;

public class DocumentTemplateResult {
	
	private boolean success;
	private String messageKey;
	private Object payload;
	private List<String> missingInfoList = new ArrayList<String>();	
	
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
	
	public void addMissingInfo(String missingInfo) {
		missingInfoList.add(missingInfo);
	}
	
	public void setMissingInfoList(List<String> missingInfoList) {
		this.missingInfoList = missingInfoList;
	}

	public List<String> getMissingInfoList() {
		return missingInfoList;
	}

}
