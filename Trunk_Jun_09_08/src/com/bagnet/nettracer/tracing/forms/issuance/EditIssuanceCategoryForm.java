package com.bagnet.nettracer.tracing.forms.issuance;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItem;
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
public final class EditIssuanceCategoryForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;
	
	private IssuanceCategory category = new IssuanceCategory();
	private List<IssuanceCategory> itemCategories = new ArrayList<IssuanceCategory>();
	
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

	public IssuanceCategory getCategory() {
		return category;
	}

	public void setCategory(IssuanceCategory category) {
		this.category = category;
	}
	
	public List<IssuanceCategory> getItemCategories() {
		return itemCategories;
	}

	public void setItemCategories(List<IssuanceCategory> itemCategories) {
		this.itemCategories = itemCategories;
	}

	public List<IssuanceItem> getItems() {
		if (category.getItems() != null) {
			return new ArrayList<IssuanceItem>(category.getItems());
		}
		return new ArrayList<IssuanceItem>();
	}
	
	public void setItems(List<IssuanceItem> items) {
		category.setItems(new LinkedHashSet<IssuanceItem>(items));
	}
	
	public IssuanceItem getItem(int index) {
		return getItems().get(index);
	}
	
	public void setItem(int index, IssuanceItem item) {
		getItems().set(index, item);
	}
	 
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// reset boolean variables so that checkboxes will work right.
		if (category != null) {
			category.setActive(false);
			category.setDamage(false);
			category.setInventory(false);
			category.setLostdelay(false);
			category.setMissing(false);
			if (category.getItems() != null) {
				for (IssuanceItem item : category.getItems()) {
					item.setActive(false);
				}
			}
		}
	}

}