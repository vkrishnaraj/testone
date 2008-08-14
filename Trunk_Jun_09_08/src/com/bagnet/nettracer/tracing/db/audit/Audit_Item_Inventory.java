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
 * @hibernate.class table="audit_item_inventory"
 */
public class Audit_Item_Inventory implements Serializable {
	private int id;
	private long inventory_ID;
	private int categorytype_ID = 0;
	//private int OHD_itemtype_ID;
	private String description = "";
	
	private Audit_Item audit_item;

	public String getCategory() {
		String category = null;
		if (categorytype_ID != 0) {
			category = CategoryBMO.getCategory("" + categorytype_ID, "en").getCategorytype();
		}

		if (category == null) category = "";
		return category;

	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_inventory_0"
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
	 * @return Returns the audit_item.
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_Item"
	 *                        column="audit_item_ID"
	 * 
	 */
	public Audit_Item getAudit_item() {
		return audit_item;
	}
	/**
	 * @param audit_item The audit_item to set.
	 */
	public void setAudit_item(Audit_Item audit_item) {
		this.audit_item = audit_item;
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
	 * @return Returns the categorytype_ID.
	 */
	public int getCategorytype_ID() {
		return categorytype_ID;
	}

	/**
	 * @param ohd_categorytype_id
	 *          The oHD_categorytype_ID to set.
	 */
	public void setCategorytype_ID(int categorytype_id) {
		this.categorytype_ID = categorytype_id;
	}

	/**
	 * @return Returns the oHD_Inventory_ID.
	 * 
	 * @hibernate.property type="long"
	 *  
	 */
	public long getInventory_ID() {
		return inventory_ID;
	}

	/**
	 * @param inventory_ID
	 *      
	 */
	public void setInventory_ID(long inventory_ID) {
		this.inventory_ID = inventory_ID;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Inventory ID=" + this.getInventory_ID());
		sb.append(" Category type=" + this.getCategorytype_ID());
		//sb.append(" Item type=" + this.getOHD_itemtype_ID());
		sb.append(" Description=" + this.getDescription());
		return sb.toString();
	}
	
	public boolean equals(Object obj) {
		Audit_Item_Inventory aoi = (Audit_Item_Inventory) obj;
		boolean ret = true;

		if (AuditOHDUtils.notEqualObjects(aoi.getCategory(), this.getCategory())
				|| AuditOHDUtils.notEqualObjects(aoi.getDescription(), this.getDescription())) {
			ret = false;
		}
		return ret;
	}

}