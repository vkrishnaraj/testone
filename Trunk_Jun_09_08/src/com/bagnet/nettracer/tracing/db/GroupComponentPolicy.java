/*
 * Created on Jul 20, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="group_component_policy"
 */
public class GroupComponentPolicy implements Serializable {
	private int policy_id;
	private SystemComponent component;
	private Set componentPermissions;
	
	private UserGroup usergroup;

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
	 * @hibernate.set cascade="all" table="policycomponentpermissions"
	 *                order-by="comp_policy_id"
	 * @hibernate.many-to-many column="permission_id"
	 *                                    class="com.bagnet.nettracer.tracing.db.SystemPermission"
	 * @hibernate.key column="policy_id"
	 * @return Returns the componentPermissions.
	 */
	public Set getComponentPermissions() {
		return componentPermissions;
	}

	/**
	 * @param componentPermissions
	 *          The componentPermissions to set.
	 */
	public void setComponentPermissions(Set componentPermissions) {
		this.componentPermissions = componentPermissions;
	}
	
	

	/**
	 * @return Returns the usergroup.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.UserGroup"
	 *                        column="group_ID"
	 */
	public UserGroup getUsergroup() {
		return usergroup;
	}
	/**
	 * @param usergroup The usergroup to set.
	 */
	public void setUsergroup(UserGroup usergroup) {
		this.usergroup = usergroup;
	}
	/**
	 * @hibernate.id generator-class="native" type="integer" column="policy_id"
	 * @hibernate.generator-param name="sequence" value="group_component_policy_0"
	 * 
	 * @return Returns the policy_id.
	 */
	public int getPolicy_id() {
		return policy_id;
	}

	/**
	 * @param policy_id
	 *          The policy_id to set.
	 */
	public void setPolicy_id(int policy_id) {
		this.policy_id = policy_id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer(200);
		sb.append("Policy ID=" + this.getPolicy_id());
		sb.append("\n\t " + this.getComponent());
		if (this.getComponentPermissions() != null && this.getComponentPermissions().size() > 0) {
			for (Iterator i = this.getComponentPermissions().iterator(); i.hasNext();) {
				SystemPermission p = (SystemPermission) i.next();
				sb.append("\n\t" + p);
			}
		}
		return sb.toString();
	}

}