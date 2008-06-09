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
 * @hibernate.class table = "ExpenseType"
 */
public class ExpenseType implements Serializable {
	private int Expensetype_ID;
	private String description;
	private String locale;
	private Company company;

	/**
	 * @return Returns the company.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Company"
	 *                        column="companycode_ID"
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *          The company to set.
	 */
	public void setCompany(Company company) {
		this.company = company;
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

	/**
	 * @return Returns the expensetype_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="Expensetype_ID" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="expensetype_0"
	 * 
	 *  
	 */
	public int getExpensetype_ID() {
		return Expensetype_ID;
	}

	/**
	 * @param expensetype_ID
	 *          The expensetype_ID to set.
	 */
	public void setExpensetype_ID(int expensetype_ID) {
		Expensetype_ID = expensetype_ID;
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