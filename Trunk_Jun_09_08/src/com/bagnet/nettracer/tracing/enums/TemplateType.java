package com.bagnet.nettracer.tracing.enums;

public enum TemplateType {

	INCIDENT("Incident"),
	CLAIM("Claim"),
	COMBINED("Combined"),
	STATIC("Static"),
	INVALID("Invalid");
	
	private String defaultName;
	
	private TemplateType(String defaultName) {
		this.defaultName = defaultName;
	}
	
	public String getDefaultName() {
		return this.defaultName;
	}
	
}
