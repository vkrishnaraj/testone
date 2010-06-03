/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * create date - Jul 14, 2004
 * @hibernate.class table="LocaleReceipt"
 * @hibernate.cache usage="read-only"
 */
public class ReceiptDbLocale implements Serializable {
	private String locale_id;
	private String locale_description;

	/**
	 * @hibernate.property type="string"
	 * @return Returns the locale_description.
	 */
	public String getLocale_description() {
		return locale_description;
	}

	/**
	 * @param locale_description
	 *          The locale_description to set.
	 */
	public void setLocale_description(String locale_description) {
		this.locale_description = locale_description;
	}

	/**
	 * @hibernate.id generator-class="assigned" type="string" column="locale_id"
	 * @return Returns the locale_id.
	 */
	public String getLocale_id() {
		return locale_id;
	}

	/**
	 * @param locale_id
	 *          The locale_id to set.
	 */
	public void setLocale_id(String locale_id) {
		this.locale_id = locale_id;
	}
}