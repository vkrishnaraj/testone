package com.bagnet.nettracer.tracing.forms.lf;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;

public final class TraceResultsForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<LFMatchHistory> matches;
	private TraceResultsFilter filter;

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

	public ArrayList<LFMatchHistory> getMatches() {
		return matches;
	}

	public void setMatches(ArrayList<LFMatchHistory> matches) {
		this.matches = matches;
	}
	
	public TraceResultsFilter getFilter() {
		return filter;
	}
	
	public void setFilter(TraceResultsFilter filter) {
		this.filter = filter;
	}

	public LFMatchHistory getMatch(int index) {
		if (matches != null && index >= 0 && index < matches.size()) {
			return matches.get(index);
		}
		return null;
	}
	
}