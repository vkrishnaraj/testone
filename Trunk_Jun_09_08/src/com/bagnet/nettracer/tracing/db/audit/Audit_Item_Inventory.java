/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.CurrencyUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="audit_item_inventory"
 */
public class Audit_Item_Inventory implements Serializable {
	private int id;
	private long inventory_ID;
	private int categorytype_ID = 0;
	//private int OHD_itemtype_ID;
	private String description = "";
	
	private Audit_Item audit_item;
	
	private String _DATEFORMAT; // current login agent's date format
	private TimeZone _TIMEZONE;
	
	private Date enteredDate;
	private Date purchaseDate;
	private double invItemCost;
	private String invItemCurrency;
	private int itemStatusId;

	public String getCategory() {
		String category = null;
		if (categorytype_ID != 0) {
			category = CategoryBMO.getCategory("" + categorytype_ID, "en").getDescription();
		}

		if (category == null) category = "";
		return category;

	}

	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}

	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	public void set_TIMEZONE(TimeZone _TIMEZONE) {
		this._TIMEZONE = _TIMEZONE;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_inventory_0"
	 * 
	 * 
	 * 
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *          The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}			

	/**
	 * @return Returns the audit_item.
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_Item"
	 *                        column="audit_item_ID"
	 * 
	 */
	public Audit_Item getAudit_item() {
		return audit_item;
	}
	/**
	 * @param audit_item The audit_item to set.
	 */
	public void setAudit_item(Audit_Item audit_item) {
		this.audit_item = audit_item;
	}
	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string" length="255"
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *          The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the categorytype_ID.
	 */
	public int getCategorytype_ID() {
		return categorytype_ID;
	}

	/**
	 * @param ohd_categorytype_id
	 *          The oHD_categorytype_ID to set.
	 */
	public void setCategorytype_ID(int categorytype_id) {
		this.categorytype_ID = categorytype_id;
	}

	/**
	 * @return Returns the oHD_Inventory_ID.
	 * 
	 * @hibernate.property type="long"
	 *  
	 */
	public long getInventory_ID() {
		return inventory_ID;
	}

	/**
	 * @param inventory_ID
	 *      
	 */
	public void setInventory_ID(long inventory_ID) {
		this.inventory_ID = inventory_ID;
	}

	/**
	 * @hibernate.property type="date"
	 */
	public Date getEnteredDate() {
		return enteredDate;
	}

	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}
	
	public String getDispEnteredDate() {
		return DateUtils.formatDate(getEnteredDate(), _DATEFORMAT, null, _TIMEZONE);
	}

	/**
	 * @hibernate.property type="date"
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public String getDispPurchaseDate() {
		return DateUtils.formatDate(getPurchaseDate(), _DATEFORMAT, null, _TIMEZONE);
	}
	
	/**
	 * @hibernate.property type="double"
	 */
	public double getInvItemCost() {
		return invItemCost;
	}

	public void setInvItemCost(double invItemCost) {
		this.invItemCost = invItemCost;
	}
	
	public String getDispInvItemCost() {
		if (getInvItemCost() != 0)
			return TracingConstants.DECIMALFORMAT.format(getInvItemCost());
		else
			return "";
	}
	
	/**
	 * @hibernate.property type="string" length="3"
	 */
	public String getInvItemCurrency() {
		return invItemCurrency;
	}

	public void setInvItemCurrency(String invItemCurrency) {
		this.invItemCurrency = invItemCurrency;
	}
	
	public String getDispInvItemCurrency() {
		return getInvItemCurrency() != null ? CurrencyUtils.getCurrency(getInvItemCurrency()).getDescription() : "";
	}
	
	/**
	 * @hibernate.property
	 */
	public int getItemStatusId() {
		return itemStatusId;
	}

	public void setItemStatusId(int itemStatusId) {
		this.itemStatusId = itemStatusId;
	}
	
	public String getDispItemStatus() {
		return TracerUtils.getText(Status.getKey(getItemStatusId()), (String) null);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Inventory ID=" + this.getInventory_ID());
		sb.append(" Category type=" + this.getCategorytype_ID());
		//sb.append(" Item type=" + this.getOHD_itemtype_ID());
		sb.append(" Description=" + this.getDescription());
		return sb.toString();
	}
	
	public boolean equals(Object obj) {
		Audit_Item_Inventory aoi = (Audit_Item_Inventory) obj;
		boolean ret = true;

		if (AuditOHDUtils.notEqualObjects(aoi.getCategory(), this.getCategory())
				|| AuditOHDUtils.notEqualObjects(aoi.getDescription(), this.getDescription())) {
			ret = false;
		}
		return ret;
	}

}