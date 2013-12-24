package com.bagnet.nettracer.tracing.forms.communications;

import com.bagnet.nettracer.tracing.forms.templates.CommandForm;

public class IncidentActivityTaskForm extends CommandForm {
	
	private static final long serialVersionUID = -720220756536896194L;

	private String currpage;
	private String nextpage;
	private String prevpage;
	private String pagination;
	private String _DATEFORMAT;
	
	private String s_createtime = "";
	private String e_createtime = "";
	
	private boolean active;

	public String getCurrpage() {
		return currpage;
	}

	public void setCurrpage(String currpage) {
		this.currpage = currpage;
	}

	public String getNextpage() {
		return nextpage;
	}

	public void setNextpage(String nextpage) {
		this.nextpage = nextpage;
	}

	public String getPrevpage() {
		return prevpage;
	}

	public void setPrevpage(String prevpage) {
		this.prevpage = prevpage;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
