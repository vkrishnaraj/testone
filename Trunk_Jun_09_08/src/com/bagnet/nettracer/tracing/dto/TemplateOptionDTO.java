package com.bagnet.nettracer.tracing.dto;

public class TemplateOptionDTO {

	private long value;
	private String description;
	
	public TemplateOptionDTO() { }
	
	public TemplateOptionDTO(long value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public long getValue() {
		return value;
	}
	
	public void setValue(long value) {
		this.value = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
