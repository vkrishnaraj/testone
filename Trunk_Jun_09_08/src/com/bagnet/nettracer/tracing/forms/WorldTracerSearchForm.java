package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

public class WorldTracerSearchForm extends ValidatorForm{
	
	public String getOhd_id() {
		return ohd_id;
	}
	public void setOhd_id(String ohdId) {
		ohd_id = ohdId;
	}
	public String getAhl_id() {
		return ahl_id;
	}
	public void setAhl_id(String ahlId) {
		ahl_id = ahlId;
	}
	private String ohd_id;
	private String ahl_id;
	

}
