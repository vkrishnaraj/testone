/*
 * Created on Jul 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.dto;

import com.bagnet.nettracer.tracing.db.SystemComponent;

/**
 * @author Ankur Gupta
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ComponentDTO {

	/**
	 * @return Returns the checked.
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked
	 *          The checked to set.
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * @return Returns the component_Description.
	 */
	public String getComponent_Description() {
		return component_Description;
	}

	/**
	 * @param component_Description
	 *          The component_Description to set.
	 */
	public void setComponent_Description(String component_Description) {
		this.component_Description = component_Description;
	}

	/**
	 * @return Returns the component_ID.
	 */
	public String getComponent_ID() {
		return component_ID;
	}

	/**
	 * @param component_ID
	 *          The component_ID to set.
	 */
	public void setComponent_ID(String component_ID) {
		this.component_ID = component_ID;
	}

	/**
	 * @return Returns the component_Name.
	 */
	public String getComponent_Name() {
		return component_Name;
	}

	/**
	 * @param component_Name
	 *          The component_Name to set.
	 */
	public void setComponent_Name(String component_Name) {
		this.component_Name = component_Name;
	}

	/**
	 * @return Returns the parent_component_ID.
	 */
	public String getParent_component_ID() {
		return parent_component_ID;
	}

	/**
	 * @param parent_component_ID
	 *          The parent_component_ID to set.
	 */
	public void setParent_component_ID(String parent_component_ID) {
		this.parent_component_ID = parent_component_ID;
	}

	private String component_ID;
	private String component_Name;
	private String component_Description;
	private String parent_component_ID = "0";
	private boolean checked = false;

	public void populate(SystemComponent component, boolean checked) {
		component_ID = "" + component.getComponent_ID();
		component_Name = component.getComponent_Name();
		component_Description = component.getComponent_Desc();
		parent_component_ID = "" + component.getParent().getComponent_ID();
		this.checked = checked;
	}

	public String getHtml(String options) {

		StringBuffer sb = new StringBuffer(1024);

		sb.append("<tr>");
		sb.append("<td width=\"5%\">");
		sb.append("<INPUT TYPE=CHECKBOX NAME=\"" + component_ID + (checked ? "\" CHECKED " : "\"")
				+ " onClick=\"" + options + ".check(this);\" >");
		sb.append("</td>");

		sb.append("<td width=\"45%\">");
		sb.append("<bean:message key=\"" + component_Name.replaceAll(" ", "_") + "\"/>");
		sb.append("</td>");

		sb.append("<td width=\"50%\">");
		if (component_Description != null) {
			sb.append(component_Description);
		} else {
			sb.append("&nbsp;");
		}
		sb.append("</td>");

		sb.append("</tr>");

		return sb.toString();
	}

	public String getHtmlGroup(String controlName) {
		return controlName + ".addToGroup(\"" + component_ID + "\");";
	}
}