/*
 * Created on Aug 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="priority"
 */
public class Priority implements Serializable {

	private int priority_ID;
	private String description;
	private String locale;

	/**
	 * @hibernate.property type="string"
	 * @return Returns the description.
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
	 * @hibernate.id generator-class="native" type="integer" column="priority_ID"
	 * @hibernate.generator-param name="sequence" value="Priority_0"
	 * 
	 * @return Returns the priority_ID.
	 */
	public int getPriority_ID() {
		return priority_ID;
	}

	/**
	 * @param priority_ID
	 *          The priority_ID to set.
	 */
	public void setPriority_ID(int priority_ID) {
		this.priority_ID = priority_ID;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the locale.
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
}