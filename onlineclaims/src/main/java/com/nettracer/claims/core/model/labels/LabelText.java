package com.nettracer.claims.core.model.labels;

public class LabelText {
	
	public static final long STATUS_NORMAL = 1;
	public static final long STATUS_REQUIRED = 2;
	public static final long STATUS_HIDDEN = 3;
	
	String label;
	boolean required;
	boolean hidden;
	
	LabelText (String label, long status) {
		this.label = label;
		required = (status == STATUS_REQUIRED);
		hidden = (status == STATUS_HIDDEN);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public void setState(Long state) {
		required = (state == STATUS_REQUIRED);
		hidden = (state == STATUS_HIDDEN);
	}

}
