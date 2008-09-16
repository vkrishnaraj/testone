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
 * @hibernate.class table="OHD_CategoryType"
 */
public class OHD_CategoryType implements Serializable {
	private int OHD_CategoryType_ID;
	private String categorytype;
	private String locale;
	private String wtCategory;

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
	 * @return Returns the categorytype.
	 * 
	 * @hibernate.property type="string" length="10"
	 */
	public String getCategorytype() {
		return categorytype;
	}

	/**
	 * @param categorytype
	 *          The categorytype to set.
	 */
	public void setCategorytype(String categorytype) {
		this.categorytype = categorytype;
	}

	/**
	 * @return Returns the oHD_CategoryType_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="OHD_CategoryType_ID"
	 * 
	 * @hibernate.generator-param name="sequence" value="ohd_categorytype_0"
	 *  
	 */
	public int getOHD_CategoryType_ID() {
		return OHD_CategoryType_ID;
	}

	/**
	 * @param categoryType_ID
	 *          The oHD_CategoryType_ID to set.
	 */
	public void setOHD_CategoryType_ID(int categoryType_ID) {
		OHD_CategoryType_ID = categoryType_ID;
	}

	/**
	 * What this maps to in world tracer.
	 * 
	 * @hibernate.property type="string" length="15"
	 * @hibernate.column name="wtcategory"
	 */
	public String getWtCategory() {
		return wtCategory;
	}

	public void setWtCategory(String wtCategory) {
		this.wtCategory = wtCategory;
	}
	
	
}