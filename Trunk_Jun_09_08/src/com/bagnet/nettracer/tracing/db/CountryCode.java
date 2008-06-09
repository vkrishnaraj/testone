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
 * @hibernate.class table="CountryCode"
 */
public class CountryCode implements Serializable {
	private String CountryCode_ID;
	private String country;
	private String locale;

	/**
	 * @return Returns the country.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *          The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return Returns the countryCode_ID.
	 * 
	 * @hibernate.id generator-class="assigned" type="string" length="3"
	 *               column="CountryCode_ID"
	 */
	public String getCountryCode_ID() {
		return CountryCode_ID;
	}

	/**
	 * @param countryCode_ID
	 *          The countryCode_ID to set.
	 */
	public void setCountryCode_ID(String countryCode_ID) {
		CountryCode_ID = countryCode_ID;
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
}