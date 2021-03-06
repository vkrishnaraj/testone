package com.bagnet.nettracer.tracing.forms.issuance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.issuance.AuditIssuanceItemInventory;
import com.bagnet.nettracer.tracing.db.issuance.AuditIssuanceItemQuantity;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemQuantity;
import com.bagnet.nettracer.tracing.utils.DateUtils;

import aero.nettracer.fs.model.forum.FsForumSearch;
import aero.nettracer.fs.model.forum.FsForumTag;
import aero.nettracer.fs.model.forum.FsForumThreadInfo;

/**
 * 
 * @author Tyrillius
 *
 */
public final class AuditIssuanceItemAdminForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;
	
	private List<AuditIssuanceItemQuantity> auditItemQuantities = new ArrayList<AuditIssuanceItemQuantity>();
	private List<AuditIssuanceItemInventory> auditItemInventories = new ArrayList<AuditIssuanceItemInventory>();
	private List<IssuanceItemQuantity> itemQuantities = new ArrayList<IssuanceItemQuantity>();
	private List<IssuanceItemInventory> itemInventories = new ArrayList<IssuanceItemInventory>();
	
	private int stationsearch_ID;
	
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

	public List<AuditIssuanceItemQuantity> getAuditItemQuantities() {
		return auditItemQuantities;
	}

	public void setAuditItemQuantities(List<AuditIssuanceItemQuantity> auditItemQuantities) {
		this.auditItemQuantities = auditItemQuantities;
	}

	public List<AuditIssuanceItemInventory> getAuditItemInventories() {
		return auditItemInventories;
	}

	public void setAuditItemInventories(
			List<AuditIssuanceItemInventory> auditItemInventories) {
		this.auditItemInventories = auditItemInventories;
	}

	public List<IssuanceItemQuantity> getItemQuantities() {
		return itemQuantities;
	}

	public void setItemQuantities(List<IssuanceItemQuantity> itemQuantities) {
		this.itemQuantities = itemQuantities;
	}

	public List<IssuanceItemInventory> getItemInventories() {
		return itemInventories;
	}

	public void setItemInventories(List<IssuanceItemInventory> itemInventories) {
		this.itemInventories = itemInventories;
	}

	public int getStationsearch_ID() {
		return stationsearch_ID;
	}

	public void setStationsearch_ID(int stationsearch_ID) {
		this.stationsearch_ID = stationsearch_ID;
	}

	public String getDisplayDate(Date toDisplay) {
		String createDateDisp = "";
		if (toDisplay != null) {
			createDateDisp = DateUtils.formatDate(toDisplay, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		}
		return createDateDisp;
	}
}