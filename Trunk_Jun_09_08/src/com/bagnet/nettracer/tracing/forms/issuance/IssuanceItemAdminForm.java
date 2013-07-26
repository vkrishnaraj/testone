package com.bagnet.nettracer.tracing.forms.issuance;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemQuantity;
import com.bagnet.nettracer.tracing.utils.DateUtils;

import aero.nettracer.fs.model.forum.FsForumSearch;
import aero.nettracer.fs.model.forum.FsForumTag;
import aero.nettracer.fs.model.forum.FsForumThreadInfo;

public final class IssuanceItemAdminForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;
	
	private List<IssuanceItemQuantity> itemQuantities = new ArrayList<IssuanceItemQuantity>();
	
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

	public List<IssuanceItemQuantity> getItemQuantities() {
		return itemQuantities;
	}

	public void setItemQuantities(List<IssuanceItemQuantity> itemQuantities) {
		this.itemQuantities = itemQuantities;
	}

}