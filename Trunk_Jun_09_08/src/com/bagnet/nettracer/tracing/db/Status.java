/*
 * Created on Jul 14, 2004
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
 * @hibernate.class table="Status"
 * @hibernate.cache usage="read-only"
 */
public class Status extends LocaleBasedObject implements Serializable {
	
	private static final long serialVersionUID = -5115842004692528313L;
	
	private static String MSG_KEY = "STATUS_KEY_";
	private int Status_ID;
	private int table_ID;
	private String description;

	public Status() { }

	public Status(int statusId) {
		this.Status_ID = statusId;
	}
	
	/**
	 * @return Returns the status_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="Status_ID"
	 * @hibernate.generator-param name="sequence" value="Status_0"
	 *  
	 */
	public int getStatus_ID() {
		return Status_ID;
	}

	/**
	 * @param status_ID
	 *          The status_ID to set.
	 */
	public void setStatus_ID(int status_ID) {
		Status_ID = status_ID;
	}

	/**
	 * @return Returns the table_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getTable_ID() {
		return table_ID;
	}

	/**
	 * @param table_ID
	 *          The table_ID to set.
	 */
	public void setTable_ID(int table_ID) {
		this.table_ID = table_ID;
	}

	public String getKey() {
		return MSG_KEY + Status_ID;
	}
	
	public static String getKey(int id) {
		return MSG_KEY + id;
	}

	/**
	* @hibernate.property type="string"
	*/
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}