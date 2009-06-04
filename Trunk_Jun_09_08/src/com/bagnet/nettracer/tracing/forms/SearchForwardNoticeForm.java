package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

public class SearchForwardNoticeForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;
	private int status = 0;
	private String s_createtime = "";
	private String e_createtime = "";
	private String select;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}
}
