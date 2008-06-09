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
 * @hibernate.class table="XDescElement"
 */
public class XDescElement implements Serializable {
	private int XDesc_ID;
	private String code;
	private String description;
	private String locale;

	/**
	 * @return Returns the xDesc_ID.
	 * 
	 * @hibernate.id generator-class="native" type="int" length="6"
	 * @hibernate.generator-param name="sequence" value="xdescelement_0"
	 *  
	 */
	public int getXDesc_ID() {
		return XDesc_ID;
	}

	/**
	 * @param desc_ID
	 *          The xDesc_ID to set.
	 */
	public void setXDesc_ID(int desc_ID) {
		XDesc_ID = desc_ID;
	}

	/**
	 * @return Returns the code.
	 * 
	 * @hibernate.property type="string" length="1"
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *          The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string" length="50"
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