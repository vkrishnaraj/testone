package com.nettracer.claims.core.model;

import com.nettracer.claims.utils.IncidentProperties;

public class IncidentPhoneBean {
	private String number;
	private int type;
	private String other;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	public String getTypeLabel() {
		return IncidentProperties.get("stepthree_radio_phtype_" + getType());
	}

}
