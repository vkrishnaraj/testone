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
 * @hibernate.class table="Permission"
 */
public class SystemPermission implements Serializable {
	private int permission_id;
	private String permission_name;
	private String permission_desc;

	/**
	 * @hibernate.property column="permission_Desc" type="string"
	 * @return Returns the permission_desc.
	 */
	public String getPermission_desc() {
		return permission_desc;
	}

	/**
	 * @param permission_desc
	 *          The permission_desc to set.
	 */
	public void setPermission_desc(String permission_desc) {
		this.permission_desc = permission_desc;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="permission_ID"
	 * @hibernate.generator-param name="sequence" value="Permission_0"
	 * @return Returns the permission_id.
	 */
	public int getPermission_id() {
		return permission_id;
	}

	/**
	 * @param permission_id
	 *          The permission_id to set.
	 */
	public void setPermission_id(int permission_id) {
		this.permission_id = permission_id;
	}

	/**
	 * @hibernate.property column="permission_Name" type="string"
	 * @return Returns the permission_name.
	 */
	public String getPermission_name() {
		return permission_name;
	}

	/**
	 * @param permission_name
	 *          The permission_name to set.
	 */
	public void setPermission_name(String permission_name) {
		this.permission_name = permission_name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer(200);
		sb.append("SystemPermission ID=" + this.getPermission_id() + " Name="
				+ this.getPermission_name() + " Description=" + this.getPermission_desc());
		return sb.toString();
	}
}