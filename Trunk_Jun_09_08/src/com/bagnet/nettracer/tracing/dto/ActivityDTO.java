/*
 * Created on Aug 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.dto;

import java.io.Serializable;

/**
 * @author Ankur Gupta
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ActivityDTO implements Serializable {

	private int component_id;
	private String activityloc;
	private String activityinfo;
	private String activityinfomenu;
	private String entries;

	/**
	 * @return Returns the component_id.
	 */
	public int getComponent_id() {
		return component_id;
	}

	/**
	 * @param component_id
	 *          The component_id to set.
	 */
	public void setComponent_id(int component_id) {
		this.component_id = component_id;
	}

	/**
	 * @return Returns the activityinfo.
	 */
	public String getActivityinfo() {
		return activityinfo;
	}

	/**
	 * @param activityinfo
	 *          The activityinfo to set.
	 */
	public void setActivityinfo(String activityinfo) {
		this.activityinfo = activityinfo;
	}

	/**
	 * @return Returns the activityinfomenu.
	 */
	public String getActivityinfomenu() {
		return activityinfomenu;
	}

	/**
	 * @param activityinfomenu
	 *          The activityinfomenu to set.
	 */
	public void setActivityinfomenu(String activityinfomenu) {
		this.activityinfomenu = activityinfomenu;
	}

	/**
	 * @return Returns the activityloc.
	 */
	public String getActivityloc() {
		return activityloc;
	}

	/**
	 * @param activityloc
	 *          The activityloc to set.
	 */
	public void setActivityloc(String activityloc) {
		this.activityloc = activityloc;
	}

	/**
	 * @return Returns the entries.
	 */
	public String getEntries() {
		return entries;
	}

	/**
	 * @param entries
	 *          The entries to set.
	 */
	public void setEntries(String entries) {
		this.entries = entries;
	}
}