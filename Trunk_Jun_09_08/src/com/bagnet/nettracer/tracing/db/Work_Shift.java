/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Administrator
 * 
 * @hibernate.class table="work_shift"
 * @hibernate.cache usage="read-only"
 */
public class Work_Shift implements Serializable {

	private int shift_id;
	private String shift_code;
	private String shift_description;
	private Company company;
	private String locale;
	private Set agents;

	/**
	 * @hibernate.property type="string"
	 * @return Returns the shift_code.
	 */
	public String getShift_code() {
		return shift_code;
	}

	/**
	 * @param shift_code
	 *          The shift_code to set.
	 */
	public void setShift_code(String shift_code) {
		this.shift_code = shift_code;
	}

	/**
	 * @hibernate.set cascade="all"
	 * @hibernate.key column="shift_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Agent"
	 * 
	 * @return Returns the agents.
	 */
	public Set getAgents() {
		return agents;
	}

	/**
	 * @param agents
	 *          The agents to set.
	 */
	public void setAgents(Set agents) {
		this.agents = agents;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Company"
	 *                        column="companycode_ID"
	 * @return Returns the company
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
	 * @return Returns the shift_description.
	 */
	public String getShift_description() {
		return shift_description;
	}

	/**
	 * @param shift_description
	 *          The shift_description to set.
	 */
	public void setShift_description(String shift_description) {
		this.shift_description = shift_description;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="shift_id"
	 *               unsaved-value="0"
	 * 
	 * @hibernate.generator-param name="sequence" value="work_shift_0"
	 * 
	 * @return Returns the shift_id.
	 */
	public int getShift_id() {
		return shift_id;
	}

	/**
	 * @param shift_id
	 *          The shift_id to set.
	 */
	public void setShift_id(int shift_id) {
		this.shift_id = shift_id;
	}
}