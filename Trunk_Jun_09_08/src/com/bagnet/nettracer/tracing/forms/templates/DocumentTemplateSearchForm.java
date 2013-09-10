package com.bagnet.nettracer.tracing.forms.templates;

import org.apache.struts.validator.ValidatorForm;

public class DocumentTemplateSearchForm extends ValidatorForm {

	private static final long serialVersionUID = -1493188726906374136L;
	
	private String currpage;
	private String nextpage;
	private String prevpage;
	private String pagination;
	private String _DATEFORMAT;
	private String command;
	
	private long id;
	
	private String name;
	
	private int active;
	
	private String s_createtime = "";
	private String e_createtime = "";

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

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int isActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getActive() {
		return active;
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
