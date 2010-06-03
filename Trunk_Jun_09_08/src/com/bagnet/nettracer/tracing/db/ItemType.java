/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.db.i8n.LocaleBasedObject;

/**
 * @author Administrator
 * 
 * @hibernate.class table="ItemType"
 * @hibernate.cache usage="read-only"
 */
public class ItemType extends LocaleBasedObject implements Serializable {
	private int ItemType_ID;
	private static String MSG_KEY = "ITEMTYPE_";
	private String description;

	/**
	 * @return Returns the itemType_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="ItemType_ID"
	 * @hibernate.generator-param name="sequence" value="ItemType_0"
	 *  
	 */
	public int getItemType_ID() {
		return ItemType_ID;
	}

	/**
	 * @param itemType_ID
	 *          The itemType_ID to set.
	 */
	public void setItemType_ID(int itemType_ID) {
		ItemType_ID = itemType_ID;
	}

	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string" length="10"
	 */
//	public String getDescription() {
//		return description;
//	}

	/**
	 * @param description
	 *          The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getKey() {
		return MSG_KEY + ItemType_ID;
	}
	
	public static String getKey(int id) {
		return MSG_KEY + id;
	}

}