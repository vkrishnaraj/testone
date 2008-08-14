/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Audit_OHD_Inventory"
 */
public class Audit_OHD_Inventory implements Serializable {

	private int id;
	private long OHD_Inventory_ID;
	private int OHD_categorytype_ID = 0;
	private String description = "";
	
	private Audit_OHD ohd;

	/**
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_ohd_inventory_0"
	 * 
	 * 
	 * 
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *          The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Returns the ohd.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_OHD"
	 *                        column="audit_ohd_id"
	 */
	public Audit_OHD getOhd() {
		return ohd;
	}
	/**
	 * @param ohd The ohd to set.
	 */
	public void setOhd(Audit_OHD ohd) {
		this.ohd = ohd;
	}
	
	public String getCategory() {
		String category = null;
		if (OHD_categorytype_ID != 0) {
			category = CategoryBMO.getCategory("" + OHD_categorytype_ID, "en").getCategorytype();
		}

		if (category == null) category = "";
		return category;

	}

	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string" length="255"
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
	 * @hibernate.property type="integer"
	 * @return Returns the oHD_categorytype_ID.
	 */
	public int getOHD_categorytype_ID() {
		return OHD_categorytype_ID;
	}

	/**
	 * @param ohd_categorytype_id
	 *          The oHD_categorytype_ID to set.
	 */
	public void setOHD_categorytype_ID(int ohd_categorytype_id) {
		OHD_categorytype_ID = ohd_categorytype_id;
	}

	/**
	 * @return Returns the oHD_Inventory_ID.
	 * 
	 * @hibernate.property type="long"
	 */
	public long getOHD_Inventory_ID() {
		return OHD_Inventory_ID;
	}

	/**
	 * @param inventory_ID
	 *          The oHD_Inventory_ID to set.
	 */
	public void setOHD_Inventory_ID(long inventory_ID) {
		OHD_Inventory_ID = inventory_ID;
	}

	public boolean equals(Object obj) {
		Audit_OHD_Inventory aoi = (Audit_OHD_Inventory) obj;
		boolean ret = true;

		if (AuditOHDUtils.notEqualObjects(aoi.getCategory(), this.getCategory())
				|| AuditOHDUtils.notEqualObjects(aoi.getDescription(), this.getDescription())) {
			ret = false;
		}
		return ret;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Inventory ID=" + this.getOHD_Inventory_ID());
		sb.append(" Category type=" + this.getOHD_categorytype_ID());
		//sb.append(" Item type=" + this.getOHD_itemtype_ID());
		sb.append(" Description=" + this.getDescription());
		return sb.toString();
	}
}