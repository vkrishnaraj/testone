package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.dto.QuickSearchDTO;
import com.bagnet.nettracer.tracing.history.HistoryObject;

/**
 * @author Ankur Gupta
 * 
 *         This class represents the form that is used for inbox related
 *         functionality.
 */
public final class QuickSearchForm extends ActionForm {

	private String search;
	private QuickSearchDTO dto;
	private ArrayList<HistoryObject> histCon;

	public String getSearch() {
		if(search!=null)
			return search;
		else
			return "";
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public QuickSearchDTO getDto() {
		return dto;
	}

	public void setDto(QuickSearchDTO dto) {
		this.dto = dto;
	}
	
	public ArrayList<HistoryObject> getHistCon() {
		return histCon;
	}

	public void setHistCon(ArrayList<HistoryObject> HistCon) {
		this.histCon = HistCon;
	}

}