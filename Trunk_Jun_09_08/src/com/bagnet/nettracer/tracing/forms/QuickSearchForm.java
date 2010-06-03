package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.dto.QuickSearchDTO;

/**
 * @author Ankur Gupta
 * 
 *         This class represents the form that is used for inbox related
 *         functionality.
 */
public final class QuickSearchForm extends ActionForm {

	private String search;
	private QuickSearchDTO dto;

	public String getSearch() {
		return search;
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

}