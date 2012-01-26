package com.bagnet.nettracer.tracing.forms.lfc;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public final class ShelvedTraceResultsForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private String dateFormat;
	private String inLoc;
	private ArrayList<LFFound> foundList;
	private String currpage;
	private String nextpage;
	private String prevpage;
	private String pagination;
	
	private int value;
	
	public ArrayList<LFFound> getFoundList() {
		return foundList;
	}

	public void setFoundList(ArrayList<LFFound> foundList) {
		this.foundList = foundList;
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}