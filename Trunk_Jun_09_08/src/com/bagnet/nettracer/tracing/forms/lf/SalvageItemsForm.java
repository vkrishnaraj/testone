package com.bagnet.nettracer.tracing.forms.lf;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;

public final class SalvageItemsForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String currpage;
	private String nextpage;
	private String prevpage;
	private String pagination;
	
	ArrayList<LFFound> foundItems;

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

	public ArrayList<LFFound> getFoundItems() {
		return foundItems;
	}

	public void setFoundItems(ArrayList<LFFound> foundItems) {
		this.foundItems = foundItems;
	}

	public LFFound getItem(int index) {
		if (foundItems != null && index < foundItems.size()) {
			return foundItems.get(index);
		}
		return null;
	}
	
}