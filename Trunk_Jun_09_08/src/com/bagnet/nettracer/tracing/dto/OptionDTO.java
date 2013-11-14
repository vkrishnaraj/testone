package com.bagnet.nettracer.tracing.dto;

public class OptionDTO {

	private String value;
	private String description;
	
	public OptionDTO() { }
	
	public OptionDTO(String value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
