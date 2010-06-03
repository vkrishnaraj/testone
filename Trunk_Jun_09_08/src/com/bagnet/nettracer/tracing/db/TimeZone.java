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
 * @hibernate.class table="timezone"
 * @hibernate.cache usage="read-only"
 */
public class TimeZone implements Serializable {
	private int id;
	private String timezone;
	private String description;
	private String locale;

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
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 * @hibernate.generator-param name="sequence" value="TimeZone_0"
	 * 
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *          The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the timezone.
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone
	 *          The timezone to set.
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string"
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
}