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
 * @hibernate.class table = "DateFormat"
 */
public class NTDateFormat implements Serializable {
	private int Dateformat_ID;
	private String format;

	/**
	 * @return Returns the dateformat_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer"
	 * @hibernate.generator-param name="sequence" value="DateFormat_0"
	 *  
	 */
	public int getDateformat_ID() {
		return Dateformat_ID;
	}

	/**
	 * @param dateformat_ID
	 *          The dateformat_ID to set.
	 */
	public void setDateformat_ID(int dateformat_ID) {
		Dateformat_ID = dateformat_ID;
	}

	/**
	 * @return Returns the format.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *          The format to set.
	 */
	public void setFormat(String format) {
		this.format = format;
	}
}