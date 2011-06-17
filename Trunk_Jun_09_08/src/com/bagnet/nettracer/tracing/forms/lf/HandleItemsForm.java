package com.bagnet.nettracer.tracing.forms.lf;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.lf.LFItem;

public final class HandleItemsForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String currpage;
	private String nextpage;
	private String prevpage;
	private String pagination;
	
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
	
}