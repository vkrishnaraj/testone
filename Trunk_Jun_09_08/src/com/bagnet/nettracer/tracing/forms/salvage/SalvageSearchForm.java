package com.bagnet.nettracer.tracing.forms.salvage;

import org.apache.struts.validator.ValidatorForm;

@SuppressWarnings("serial")
public class SalvageSearchForm extends ValidatorForm {
	
	private int salvageId;
	private int salvageStatus;
	private String s_createtime;
	private String e_createtime;

	public int getSalvageId() {
		return salvageId;
	}
	
	public void setSalvageId(int salvageId) {
		this.salvageId = salvageId;
	}
	
	public int getSalvageStatus() {
		return this.salvageStatus;
	}
	
	public void setSalvageStatus(int salvageStatus) {
		this.salvageStatus = salvageStatus;
	}
	
	public String getS_createtime() {
		return s_createtime;
	}
	
	public void setS_createtime(String s_createtime) {
		this.s_createtime = s_createtime;
	}
	
	public String getE_createtime() {
		return e_createtime;
	}
	
	public void setE_createtime(String e_createtime) {
		this.e_createtime = e_createtime;
	}
	
}
