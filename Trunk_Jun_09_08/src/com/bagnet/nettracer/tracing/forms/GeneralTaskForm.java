package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.action.ActionForm;

public class GeneralTaskForm extends ActionForm{
	String currpage;
	String prevpage;
	int day;
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getPrevpage() {
		return prevpage;
	}

	public void setPrevpage(String prevpage) {
		this.prevpage = prevpage;
	}

	public String getNextpage() {
		return nextpage;
	}

	public void setNextpage(String nextpage) {
		this.nextpage = nextpage;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	String nextpage;
	String pagination;
	

	public String getCurrpage() {
		return currpage;
	}

	public void setCurrpage(String currpage) {
		this.currpage = currpage;
	}
}
