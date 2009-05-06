/*
 * Created on Aug 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.db.i8n.LocaleBasedObject;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="priority"
 */
public class Priority extends LocaleBasedObject implements Serializable {

	private int priority_ID;
	private String MSG_KEY = "PRIORITY_KEY_";

	/**
	 * @hibernate.id generator-class="native" type="integer" column="priority_ID"
	 * @hibernate.generator-param name="sequence" value="Priority_0"
	 * 
	 * @return Returns the priority_ID.
	 */
	public int getPriority_ID() {
		return priority_ID;
	}

	/**
	 * @param priority_ID
	 *          The priority_ID to set.
	 */
	public void setPriority_ID(int priority_ID) {
		this.priority_ID = priority_ID;
	}

	public String getKey() {
		return MSG_KEY + priority_ID;
	}
}