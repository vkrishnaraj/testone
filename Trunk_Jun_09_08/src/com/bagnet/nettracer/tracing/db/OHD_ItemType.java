/*
 * Created on Jul 13, 2004
 *
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * @hibernate.class table="OHD_ItemType"
 */
public class OHD_ItemType implements Serializable {
	private int OHD_ItemType_ID;
	private String itemtype;
	private String locale;

	/**
	 * @return Returns the itemtype.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getItemtype() {
		return itemtype;
	}

	/**
	 * @param itemtype
	 *          The itemtype to set.
	 */
	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	/**
	 * @return Returns the locale.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *          The locale to set.
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return Returns the oHD_ItemType_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="OHD_ItemType_ID"
	 * @hibernate.generator-param name="sequence" value="ohd_itemtype_0"
	 *  
	 */
	public int getOHD_ItemType_ID() {
		return OHD_ItemType_ID;
	}

	/**
	 * @param itemType_ID
	 *          The oHD_ItemType_ID to set.
	 */
	public void setOHD_ItemType_ID(int itemType_ID) {
		OHD_ItemType_ID = itemType_ID;
	}
}