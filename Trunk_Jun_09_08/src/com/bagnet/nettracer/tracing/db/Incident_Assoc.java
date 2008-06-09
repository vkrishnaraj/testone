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
 * @hibernate.class table="Incident_Assoc"
 */
public class Incident_Assoc implements Serializable {
	private int ID;
	private String assoc_ID;
	private String incident_ID;
	private int itemtype_ID;

	/**
	 * @return Returns the assoc_ID.
	 * 
	 * @hibernate.property type="string" length="13"
	 */
	public String getAssoc_ID() {
		return assoc_ID;
	}

	/**
	 * @param assoc_ID
	 *          The assoc_ID to set.
	 */
	public void setAssoc_ID(String assoc_ID) {
		this.assoc_ID = assoc_ID;
	}

	/**
	 * @return Returns the iD.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="incident_assoc_0"
	 * 
	 *  
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param id
	 *          The iD to set.
	 */
	public void setID(int id) {
		ID = id;
	}

	/**
	 * @return Returns the incident_ID.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getIncident_ID() {
		return incident_ID;
	}

	/**
	 * @param incident_ID
	 *          The incident_ID to set.
	 */
	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}

	/**
	 * @return Returns the itemtype_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getItemtype_ID() {
		return itemtype_ID;
	}

	/**
	 * @param itemtype_ID
	 *          The itemtype_ID to set.
	 */
	public void setItemtype_ID(int itemtype_ID) {
		this.itemtype_ID = itemtype_ID;
	}
}