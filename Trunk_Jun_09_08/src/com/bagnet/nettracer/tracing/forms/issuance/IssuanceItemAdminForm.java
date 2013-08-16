package com.bagnet.nettracer.tracing.forms.issuance;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory;
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
public final class IssuanceItemAdminForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;
	
	private List<IssuanceItemQuantity> itemQuantities = new ArrayList<IssuanceItemQuantity>();
	private List<IssuanceItemInventory> itemInventories = new ArrayList<IssuanceItemInventory>();
	private List<IssuanceItemInventory> itemLoanInventories = new ArrayList<IssuanceItemInventory>();
	private List<IssuanceItemInventory> itemIssueInventories = new ArrayList<IssuanceItemInventory>();
	private List<IssuanceItemInventory> itemDiscardInventories = new ArrayList<IssuanceItemInventory>();
	private List<IssuanceCategory> itemCategories = new ArrayList<IssuanceCategory>();
	
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

	public List<IssuanceCategory> getItemCategories() {
		return itemCategories;
	}

	public void setItemCategories(List<IssuanceCategory> itemCategories) {
		this.itemCategories = itemCategories;
	}

	public List<IssuanceItemInventory> getItemLoanInventories() {
		return itemLoanInventories;
	}

	public void setItemLoanInventories(
			List<IssuanceItemInventory> itemLoanInventories) {
		this.itemLoanInventories = itemLoanInventories;
	}

	public List<IssuanceItemInventory> getItemIssueInventories() {
		return itemIssueInventories;
	}

	public void setItemIssueInventories(
			List<IssuanceItemInventory> itemIssueInventories) {
		this.itemIssueInventories = itemIssueInventories;
	}

	public List<IssuanceItemInventory> getItemDiscardInventories() {
		return itemDiscardInventories;
	}

	public void setItemDiscardInventories(
			List<IssuanceItemInventory> itemDiscardInventories) {
		this.itemDiscardInventories = itemDiscardInventories;
	}

	public int getStationsearch_ID() {
		return stationsearch_ID;
	}

	public void setStationsearch_ID(int stationsearch_ID) {
		this.stationsearch_ID = stationsearch_ID;
	}

}