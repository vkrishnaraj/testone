/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="item_inventory"
 */
public class Item_Inventory implements Serializable {

	private long inventory_ID;
	private int categorytype_ID = 0;
	private String description = "";
	private Item item;
	private String tempcat;	// this stores category name for matching
	
	private String _DATEFORMAT; // current login agent's date format
	private TimeZone _TIMEZONE;
	
	private Date enteredDate;
	private Date purchaseDate;
	private double invItemCost;
	private String invItemCurrency;
	private int itemStatusId;
	
	private static ConcurrentHashMap<Integer, String> cachedCategoryMap = new ConcurrentHashMap<Integer, String>();

	public String getCachedCategory() {
		Integer key = new Integer(categorytype_ID);
		if (categorytype_ID != 0) {
			if (cachedCategoryMap.containsKey(key)) {
				return cachedCategoryMap.get(key);
			} else {
				String category = getCategory();
				cachedCategoryMap.put(key, category);
				return category;
			}
		}
		return "";
	}
	
	public String getCategory() {
		String category = null;
		if (categorytype_ID != 0) {
			category = CategoryBMO.getCategory(categorytype_ID, "en").getDescription();
		}

		if (category == null) category = "";
		return category;

	}

	public String getCategoryKey() {
		String category = null;
		if (categorytype_ID != 0) {
			category = CategoryBMO.getCategory(categorytype_ID, null).getKey();
		}

		if (category == null) category = "";
		return category;
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
	 * @hibernate.id generator-class="native" type="long"
	 *               column="inventory_ID" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="inventory_0"
	 *  
	 */
	public long getInventory_ID() {
		return inventory_ID;
	}

	/**
	 * @param inventory_ID
	 *          The oHD_Inventory_ID to set.
	 */
	public void setInventory_ID(long inventory_ID) {
		this.inventory_ID = inventory_ID;
	}
	
	

	/**
	 * @return Returns the item.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Item"
	 *                        column="item_ID" not-null="true"
	 */
	public Item getItem() {
		return item;
	}
	/**
	 * @param item The item to set.
	 */
	public void setItem(Item item) {
		this.item = item;
	}
	/**
	 * @return Returns the tempcat for matchin
	 */
	public String getTempcat() {
		return tempcat;
	}
	/**
	 * @param tempcat The tempcat to set.
	 */
	public void setTempcat(String tempcat) {
		this.tempcat = tempcat;
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

	/**
	 * @hibernate.property type="date"
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
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

	public String getDispEnteredDate() {
		return DateUtils.formatDate(getEnteredDate(), _DATEFORMAT, null, _TIMEZONE);
	}
	
	public String getDispPurchaseDate() {
		return DateUtils.formatDate(getPurchaseDate(), _DATEFORMAT, null, _TIMEZONE);
	}

	public void setDispPurchaseDate(String date) {
		this.purchaseDate = DateUtils.convertToDate(date, _DATEFORMAT, null, _TIMEZONE);
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

	public void setDispInvItemCost(String s) {
		setInvItemCost(TracerUtils.convertToDouble(s));
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

	/**
	 * @hibernate.property
	 */
	public int getItemStatusId() {
		return itemStatusId;
	}

	public void setItemStatusId(int itemStatusId) {
		this.itemStatusId = itemStatusId;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Inventory ID=" + this.getInventory_ID());
		sb.append(" Category type=" + this.getCategorytype_ID());
		//sb.append(" Item type=" + this.getOHD_itemtype_ID());
		sb.append(" Description=" + this.getDescription());
		return sb.toString();
	}
}