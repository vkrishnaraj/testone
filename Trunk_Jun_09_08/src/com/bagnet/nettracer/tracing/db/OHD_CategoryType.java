/*
 * Created on Jul 13, 2004
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
 * @hibernate.class table="OHD_CategoryType"
 * @hibernate.cache usage="read-only"
 */
public class OHD_CategoryType extends LocaleBasedObject implements Serializable {
	private int OHD_CategoryType_ID;
	private String categoryKey;
	private String wtCategory;
	private String MSG_KEY = "CATEGORY_";	


	/**
	 * @return Returns the categoryKey.
	 * @hibernate.property type="string" column="categorytype" length="10"
	 */
	public String getCategoryKey() {
		return categoryKey;
	}

	/**
	 * @param categoryKey
	 *          The categoryKey to set.
	 */
	public void setCategoryKey(String categoryKey) {
		this.categoryKey = categoryKey;
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
	 * @hibernate.column name="wt_category"
	 */
	public String getWtCategory() {
		return wtCategory;
	}

	public void setWtCategory(String wtCategory) {
		this.wtCategory = wtCategory;
	}
	
	public String getKey() {
		return MSG_KEY + getCategoryKey();
	}

	
}