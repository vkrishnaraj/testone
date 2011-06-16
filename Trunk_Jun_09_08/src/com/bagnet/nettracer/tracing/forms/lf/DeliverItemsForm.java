package com.bagnet.nettracer.tracing.forms.lf;

import org.apache.struts.action.ActionForm;

public final class DeliverItemsForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String currpage;
	private String nextpage;
	private String prevpage;
	private String pagination;

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
	
}