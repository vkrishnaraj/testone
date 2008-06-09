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
 * @hibernate.class table = "TimeFormat"
 */
public class NTTimeFormat implements Serializable {
	private int Timeformat_ID;
	private String format;

	/**
	 * @return Returns the timeformat_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer"
	 * @hibernate.generator-param name="sequence" value="TimeFormat_0"
	 *  
	 */
	public int getTimeformat_ID() {
		return Timeformat_ID;
	}

	/**
	 * @param timeformat_ID
	 *          The timeformat_ID to set.
	 */
	public void setTimeformat_ID(int timeformat_ID) {
		Timeformat_ID = timeformat_ID;
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