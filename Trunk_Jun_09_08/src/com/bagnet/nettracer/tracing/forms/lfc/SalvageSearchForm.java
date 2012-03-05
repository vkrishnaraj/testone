package com.bagnet.nettracer.tracing.forms.lfc;

import org.apache.struts.action.ActionForm;

public final class SalvageSearchForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private String dateFormat;
	private String inLoc;
	private String currpage;
	private String nextpage;
	private String prevpage;
	private String pagination;
	
	private long salvageId;
	private int salvageStatus;
	private String s_createtime;
	private String e_createtime;
	
	public String getInLoc() {
		return inLoc;
	}

	public void setInLoc(String inLoc) {
		this.inLoc = inLoc;
	}

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

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getSalvageStatus() {
		return salvageStatus;
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

	public long getSalvageId() {
		return salvageId;
	}

	public void setSalvageId(long salvageId) {
		this.salvageId = salvageId;
	}

}