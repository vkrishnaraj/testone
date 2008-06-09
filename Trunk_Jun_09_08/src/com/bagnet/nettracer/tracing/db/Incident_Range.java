/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @@author Administrator
 * 
 * @hibernate.class table="Incident_Range"
 */
public class Incident_Range implements Serializable {
	private long current_num;
	private String companycode_ID;

	/**
	 * @@return Returns the current_num.
	 * 
	 * @hibernate.id generator-class="native" type="long" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="Incident_Range_0"
	 * 
	 *  
	 */
	public long getCurrent_num() {
		return current_num;
	}

	/**
	 * @@param current_num The current_num to set.
	 */
	public void setCurrent_num(long current_num) {
		this.current_num = current_num;
	}

	/**
	 * @return Returns the companycode_ID.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getCompanycode_ID() {
		return companycode_ID;
	}

	/**
	 * @param companycode_ID
	 *          The companycode_ID to set.
	 */
	public void setCompanycode_ID(String companycode_ID) {
		this.companycode_ID = companycode_ID;
	}

}