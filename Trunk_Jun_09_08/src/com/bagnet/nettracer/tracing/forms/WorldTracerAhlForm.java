package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

public class WorldTracerAhlForm extends ValidatorForm {
	public String getPnrloc() {
		return pnrloc;
	}
	public void setPnrloc(String pnrloc) {
		this.pnrloc = pnrloc;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String firstName) {
		first_name = firstName;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String lastName) {
		last_name = lastName;
	}
	String pnrloc;
	String first_name;
	String last_name;
}
