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
 * @hibernate.class table="Currency"
 *  
 */
public class Currency implements Serializable {
	private String Currency_ID;
	private String description;
	private String locale;
	private String id_desc;

	/**
	 * @return Returns the currency_ID.
	 * 
	 * @hibernate.id generator-class="assigned" type="string" length="3"
	 *               column="Currency_ID"
	 */
	public String getCurrency_ID() {
		return Currency_ID;
	}

	/**
	 * @param currency_ID
	 *          The currency_ID to set.
	 */
	public void setCurrency_ID(String currency_ID) {
		Currency_ID = currency_ID;
	}

	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string" length="20"
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
	 * for displaying
	 */
	public String getId_desc() {
		return Currency_ID + " - " + description;
	}

}