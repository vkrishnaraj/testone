package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.db.issuance.IssuanceItem;
import com.bagnet.nettracer.tracing.dto.CSSStationsDTO;

public class GeneralTaskForm extends ActionForm{
	String currpage;
	String prevpage;
	int day;
	List<CSSStationsDTO> stationList;
	
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

	public List<CSSStationsDTO> getStationList() {
		return stationList;
	}

	public void setStationList(List<CSSStationsDTO> stationList) {
		this.stationList = stationList;
	}
	
	public CSSStationsDTO getCssStation(int index) {
		if (this.stationList == null) {
			this.stationList = new ArrayList<CSSStationsDTO>();
		}
		while (index >= this.stationList.size()) {
			this.stationList.add(new CSSStationsDTO());
		}
		return this.stationList.get(index);
	}
	 
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// reset boolean variables so that checkboxes will work right.
		if (stationList != null) {
			for (CSSStationsDTO dto : stationList) {
				dto.setStation1Checked(false);
				dto.setStation2Checked(false);
				dto.setStation3Checked(false);
			}
		}
	}
}
