package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/*
 * Created on Jul 20, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
/**
 * @author Ankur Gupta
 *  
 */
public class ComponentPermission implements Serializable {
	private SystemComponent component;
	private List component_Permissions;

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.SystemComponent"
	 *                        column="component_id"
	 * @return Returns the component.
	 */
	public SystemComponent getComponent() {
		return component;
	}

	/**
	 * @param component
	 *          The component to set.
	 */
	public void setComponent(SystemComponent component) {
		this.component = component;
	}

	/**
	 * @return Returns the component_Permissions.
	 */
	public List getComponent_Permissions() {
		return component_Permissions;
	}

	/**
	 * @param component_Permissions
	 *          The component_Permissions to set.
	 */
	public void setComponent_Permissions(List component_Permissions) {
		this.component_Permissions = component_Permissions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer(200);
		sb.append(this.getComponent());
		if (this.getComponent_Permissions() != null && this.getComponent_Permissions().size() > 0) {
			for (Iterator i = this.getComponent_Permissions().iterator(); i.hasNext();) {
				ComponentPermission p = (ComponentPermission) i.next();
				if (p != null) sb.append("\n\t" + p);
			}
		}
		return sb.toString();
	}
}