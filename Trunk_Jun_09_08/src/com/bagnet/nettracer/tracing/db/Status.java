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
 * @hibernate.class table="Status"
 */
public class Status implements Serializable {
	private int Status_ID;
	private String description;
	private String locale;
	private int table_ID;

	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string" length="10"
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
	 * @return Returns the status_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="Status_ID"
	 * @hibernate.generator-param name="sequence" value="Status_0"
	 *  
	 */
	public int getStatus_ID() {
		return Status_ID;
	}

	/**
	 * @param status_ID
	 *          The status_ID to set.
	 */
	public void setStatus_ID(int status_ID) {
		Status_ID = status_ID;
	}

	/**
	 * @return Returns the table_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getTable_ID() {
		return table_ID;
	}

	/**
	 * @param table_ID
	 *          The table_ID to set.
	 */
	public void setTable_ID(int table_ID) {
		this.table_ID = table_ID;
	}
}