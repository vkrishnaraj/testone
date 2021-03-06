/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import com.bagnet.nettracer.tracing.bmo.CategoryBMO;

/**
 * @author Administrator
 * 
 * @hibernate.class table="item_inventory"
 */
public class TraceItem_Inventory implements Serializable {

	private long inventory_ID;
	private int categorytype_ID = 0;
	private String description = "";
	private TraceItem item;
	private String tempcat;	// this stores category name for matching
	
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.TraceItem"
	 *                        column="item_ID" not-null="true"
	 */
	public TraceItem getItem() {
		return item;
	}
	/**
	 * @param item The item to set.
	 */
	public void setItem(TraceItem item) {
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
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Inventory ID=" + this.getInventory_ID());
		sb.append(" Category type=" + this.getCategorytype_ID());
		//sb.append(" Item type=" + this.getOHD_itemtype_ID());
		sb.append(" Description=" + this.getDescription());
		return sb.toString();
	}
}