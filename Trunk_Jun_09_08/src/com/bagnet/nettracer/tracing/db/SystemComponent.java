/*
 * Created on Jul 20, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="SystemComponents"
 */
public class SystemComponent implements Serializable {
	private int component_ID;
	private String component_Name;
	private String component_Desc;
	private SystemComponent parent;
	private String component_action_link;
	private int sort_order;
	private int display;

	/**
	 * @hibernate.property type="integer"
	 * @return Returns whether the component is displayable.
	 */
	public int getDisplay() {
		return display;
	}

	/**
	 * @param display
	 *          is it displayable
	 */
	public void setDisplay(int display) {
		this.display = display;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the sort_order.
	 */
	public int getSort_order() {
		return sort_order;
	}

	/**
	 * @param sort_order
	 *          The sort_order to set.
	 */
	public void setSort_order(int sort_order) {
		this.sort_order = sort_order;
	}

	/**
	 * @hibernate.property column="component_action_link" type="string"
	 * @return Returns the component_action_link.
	 */
	public String getComponent_action_link() {
		return component_action_link;
	}

	/**
	 * @param component_action_link
	 *          The component_action_link to set.
	 */
	public void setComponent_action_link(String component_action_link) {
		this.component_action_link = component_action_link;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.SystemComponent"
	 *                        column="parent_component_id"
	 * @return Returns the parent.
	 */
	public SystemComponent getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *          The parent to set.
	 */
	public void setParent(SystemComponent parent) {
		this.parent = parent;
	}

	/**
	 * @hibernate.property column="component_Desc" type="string"
	 * @return Returns the component_Desc.
	 */
	public String getComponent_Desc() {
		return component_Desc;
	}

	/**
	 * @param component_Desc
	 *          The component_Desc to set.
	 */
	public void setComponent_Desc(String component_Desc) {
		this.component_Desc = component_Desc;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="component_ID"
	 * @hibernate.generator-param name="sequence" value="SystemComponents_0"
	 * 
	 * @return Returns the component_ID.
	 */
	public int getComponent_ID() {
		return component_ID;
	}

	/**
	 * @param component_ID
	 *          The component_ID to set.
	 */
	public void setComponent_ID(int component_ID) {
		this.component_ID = component_ID;
	}

	/**
	 * @hibernate.property column="component_name" type="string"
	 * @return Returns the component_Name.
	 */
	public String getComponent_Name() {
		return component_Name;
	}

	/**
	 * @param component_name
	 *          The component_name to set.
	 */
	public void setComponent_Name(String component_Name) {
		this.component_Name = component_Name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer(200);
		sb.append("Component ID=" + this.getComponent_ID() + " Name=" + this.getComponent_Name()
				+ " Desc=" + this.getComponent_Desc() + " Component Link="
				+ this.getComponent_action_link() + " parent=" + this.getParent().getComponent_ID());
		return sb.toString();
	}
}