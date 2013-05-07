package com.bagnet.nettracer.tracing.forms.lf;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.lf.LFItem;

public final class ToBeShippedForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String currpage;
	private String nextpage;
	private String prevpage;
	private String pagination;
	
	private String dateFormat;
	private String startDate;
	private String endDate;
	private int value;
	
	ArrayList<LFItem> foundItems;

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

	public ArrayList<LFItem> getFoundItems() {
		return foundItems;
	}

	public void setFoundItems(ArrayList<LFItem> foundItems) {
		this.foundItems = foundItems;
	}

	public LFItem getItem(int index) {
		if (foundItems != null && index < foundItems.size()) {
			return foundItems.get(index);
		}
		return null;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
}