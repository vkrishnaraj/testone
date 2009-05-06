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
 * @hibernate.class table="OHD_Inventory"
 */
public class OHD_Inventory implements Serializable {

	private long OHD_Inventory_ID;
	private int OHD_categorytype_ID = 0;
	//private int OHD_itemtype_ID;
	private String description = "";
	private OHD ohd;
	private static ConcurrentHashMap<Integer, String> cachedCategoryString = new ConcurrentHashMap<Integer, String>();

	
	public String getCachedCategory() {
		Integer key = new Integer(OHD_categorytype_ID);
		if (OHD_categorytype_ID != 0) {
			if (cachedCategoryString.containsKey(key)) {
				return cachedCategoryString.get(key);
			} else {
				String category = getCategory();
				cachedCategoryString.put(key, category);
				return category;
			}
		}
		return "";
	}

	public String getCategory() {
		String category = null;
		if (OHD_categorytype_ID != 0) {
			category = CategoryBMO.getCategory("" + OHD_categorytype_ID, "en").getDescription();
		}

		if (category == null) category = "";
		return category;

	}


	public String getCategoryKey() {
		String category = null;
		if (OHD_categorytype_ID != 0) {
			category = CategoryBMO.getCategory(OHD_categorytype_ID, null).getKey();
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
	 * @return Returns the oHD_categorytype_ID.
	 */
	public int getOHD_categorytype_ID() {
		return OHD_categorytype_ID;
	}

	/**
	 * @param ohd_categorytype_id
	 *          The oHD_categorytype_ID to set.
	 */
	public void setOHD_categorytype_ID(int ohd_categorytype_id) {
		OHD_categorytype_ID = ohd_categorytype_id;
	}

	/**
	 * @return Returns the oHD_Inventory_ID.
	 * 
	 * @hibernate.id generator-class="native" type="long"
	 *               column="OHD_Inventory_ID" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="ohd_inventory_0"
	 *  
	 */
	public long getOHD_Inventory_ID() {
		return OHD_Inventory_ID;
	}

	/**
	 * @param inventory_ID
	 *          The oHD_Inventory_ID to set.
	 */
	public void setOHD_Inventory_ID(long inventory_ID) {
		OHD_Inventory_ID = inventory_ID;
	}
	
	

	/**
	 * @return Returns the ohd.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.OHD"
	 *                        column="OHD_ID" not-null="true"
	 */
	public OHD getOhd() {
		return ohd;
	}
	/**
	 * @param ohd The ohd to set.
	 */
	public void setOhd(OHD ohd) {
		this.ohd = ohd;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Inventory ID=" + this.getOHD_Inventory_ID());
		sb.append(" Category type=" + this.getOHD_categorytype_ID());
		//sb.append(" Item type=" + this.getOHD_itemtype_ID());
		sb.append(" Description=" + this.getDescription());
		return sb.toString();
	}
}