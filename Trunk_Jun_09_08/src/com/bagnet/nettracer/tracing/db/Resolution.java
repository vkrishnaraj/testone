/*
 * Created on Jul 22, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * create date - Jul 22, 2004
 * 
 * @hibernate.class table="Resolution"
 */
public class Resolution implements Serializable {
	private int Resolution_ID;
	private String status;
	private String locale;
	private Company company;

	/**
	 * @return Returns the companycode.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Company"
	 *                        column="companycode_ID"
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param companycode
	 *          The companycode to set.
	 */
	public void setCompany(Company company) {
		this.company = company;
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
	 * @return Returns the resolution_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="Resolution_ID"
	 * @hibernate.generator-param name="sequence" value="Resolution_0"
	 *  
	 */
	public int getResolution_ID() {
		return Resolution_ID;
	}

	/**
	 * @param resolution_ID
	 *          The resolution_ID to set.
	 */
	public void setResolution_ID(int resolution_ID) {
		Resolution_ID = resolution_ID;
	}

	/**
	 * @return Returns the status.
	 * 
	 * @hibernate.property type="string" length="10"
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *          The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}