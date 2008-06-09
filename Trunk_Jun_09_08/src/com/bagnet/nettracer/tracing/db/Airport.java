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
 * @hibernate.class table="Airport"
 */
public class Airport implements Serializable {
	private int id;
	private String airport_code;
	private String airport_desc;
	private String companyCode_ID;
	private String locale;
	private int country;

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

	/**
	 * @hibernate.property type="string"
	 * @return Returns the airport_code.
	 */
	public String getAirport_code() {
		return airport_code;
	}

	/**
	 * @param airport_code
	 *          The airport_code to set.
	 */
	public void setAirport_code(String airport_code) {
		this.airport_code = airport_code;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the airport_desc.
	 */
	public String getAirport_desc() {
		return airport_desc;
	}

	/**
	 * @param airport_desc
	 *          The airport_desc to set.
	 */
	public void setAirport_desc(String airport_desc) {
		this.airport_desc = airport_desc;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the companyCode_ID.
	 */
	public String getCompanyCode_ID() {
		return companyCode_ID;
	}

	/**
	 * @param companyCode_ID
	 *          The companyCode_ID to set.
	 */
	public void setCompanyCode_ID(String companyCode_ID) {
		this.companyCode_ID = companyCode_ID;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="airport_0"
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
	 * @return Returns the country.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getCountry() {
		return country;
	}

	/**
	 * @param country
	 *          The country to set.
	 */
	public void setCountry(int country) {
		this.country = country;
	}
}