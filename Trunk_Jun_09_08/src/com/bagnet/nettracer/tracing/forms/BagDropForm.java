package com.bagnet.nettracer.tracing.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.BagDrop;
import com.bagnet.nettracer.tracing.db.audit.Audit_BagDrop;
import com.bagnet.nettracer.tracing.dto.BagDropDTO;

public class BagDropForm extends ValidatorForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 854060078161377014L;
	
	
	private String currpage;
	private String nextpage;
	private String prevpage;
	private String pagination;
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;
	
	private List <BagDrop> bagDropList;
	private BagDropDTO dto;
	private BagDrop editBagDrop;
	private List<Audit_BagDrop> auditList;
	
	public List <BagDrop> getBagDropList() {
		return bagDropList;
	}

	public void setBagDropList(List <BagDrop> bagDropList) {
		this.bagDropList = bagDropList;
	}

	public BagDropDTO getDto() {
		return dto;
	}

	public void setDto(BagDropDTO dto) {
		this.dto = dto;
	}

	public BagDrop getEditBagDrop() {
		return editBagDrop;
	}

	public void setEditBagDrop(BagDrop editBagDrop) {
		this.editBagDrop = editBagDrop;
	}

	public void reset(ActionMapping mapping, 
			HttpServletRequest request) {
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

	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}

	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	public void set_TIMEFORMAT(String _TIMEFORMAT) {
		this._TIMEFORMAT = _TIMEFORMAT;
	}

	public java.util.TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	public void set_TIMEZONE(java.util.TimeZone _TIMEZONE) {
		this._TIMEZONE = _TIMEZONE;
	}

	public List<Audit_BagDrop> getAuditList() {
		return auditList;
	}

	public void setAuditList(List<Audit_BagDrop> auditList) {
		this.auditList = auditList;
	}
	
	
	/**The following is need for Calendar.js to work, 
	 * does not seem to work when accessing dto/bagdrop object directly**/

	public String getSearchStartDate() {
		if(dto != null){
			return dto.getDispStartScheduleArrivalDate();
		} else {
			return "";
		}
	}

	public void setSearchStartDate(String searchStartDate) {
		if(dto != null){
			dto.setDispStartScheduleArrivalDate(searchStartDate);
		}
	}

	public String getSearchEndDate() {
		if(dto != null){
			return dto.getDispEndScheduleArrivalDate();
		} else {
			return "";
		}
	}

	public void setSearchEndDate(String searchEndDate) {
		if(dto != null){
			dto.setDispEndScheduleArrivalDate(searchEndDate);
		}
	}

	public String getBagDropDate() {
		if(editBagDrop != null){
			return editBagDrop.getDispBagDropDate();
		} else {
			return "";
		}
	}

	public void setBagDropDate(String bagDropDate) {
		if(editBagDrop != null){
			editBagDrop.setDispBagDropDate(bagDropDate);
		}
	}

}
