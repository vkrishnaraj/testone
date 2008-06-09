/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Manufacturer"
 */
public class Manufacturer implements Serializable {
	private int Manufacturer_ID;
	private String description;
	private String locale;

	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string" length="50"
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
	 * @return Returns the manufacturer_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="Manufacturer_ID"
	 * @hibernate.generator-param name="sequence" value="Manufacturer_0"
	 *  
	 */
	public int getManufacturer_ID() {
		return Manufacturer_ID;
	}

	/**
	 * @param manufacturer_ID
	 *          The manufacturer_ID to set.
	 */
	public void setManufacturer_ID(int manufacturer_ID) {
		Manufacturer_ID = manufacturer_ID;
	}
}