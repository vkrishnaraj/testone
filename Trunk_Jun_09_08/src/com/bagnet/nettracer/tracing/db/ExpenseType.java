/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.db.i8n.LocaleBasedObject;

/**
 * @author Administrator
 * 
 * @hibernate.class table = "ExpenseType"
 * @hibernate.cache usage="read-only"
 */
public class ExpenseType extends LocaleBasedObject implements Serializable {
	private int Expensetype_ID;
	private Company company;
	public static final String MSG_KEY = "EXPENSETYPE_KEY_";

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


	public String getKey() {
		return MSG_KEY + Expensetype_ID;
	}
}