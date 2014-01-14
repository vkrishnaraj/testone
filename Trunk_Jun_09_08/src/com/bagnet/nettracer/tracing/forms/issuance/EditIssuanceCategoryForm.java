package com.bagnet.nettracer.tracing.forms.issuance;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItem;

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
	private List<Template> documentList = new ArrayList<Template>();	

	
	public long getTemplateId() {
		if (getCategory() != null && getCategory().getTemplate() != null)
			return getCategory().getTemplate().getId();
		else 
			return 0;
	}

	public void setTemplateId(long templateId) {
		if (templateId != 0 && getCategory() != null && getDocumentList() != null) {
			List<Template> templates = getDocumentList();
			for (Template item : templates){
				if (item.getId() == templateId) {
					getCategory().setTemplate(item);
					break;
				}
			}
		}
		else {
			getCategory().setTemplate(null);
		}
			
	}		

	public List<Template> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<Template> documentList) {
		this.documentList = documentList;
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
			if (category.getId() <= 0) { // only reset inventory for new categories.
				category.setInventory(false);
			}
			category.setLostdelay(false);
			category.setMissing(false);
			category.setLimitByPassenger(false);
			if (category.getItems() != null) {
				for (IssuanceItem item : category.getItems()) {
					item.setActive(false);
				}
			}
		}
	}

}