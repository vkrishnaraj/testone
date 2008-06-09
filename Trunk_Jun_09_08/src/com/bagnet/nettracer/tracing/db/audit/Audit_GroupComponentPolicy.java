/*
 * Created on Jul 20, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.db.SystemComponent;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="audit_group_component_policy"
 */
public class Audit_GroupComponentPolicy implements Serializable {

	private int audit_policy_id;
	private int policy_id;
	private SystemComponent component;
	private Audit_UserGroup audit_usergroup;
	

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
	 * @return Returns the audit_usergroup.
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup"
	 *                        column="audit_group_id"
	 */
	public Audit_UserGroup getAudit_usergroup() {
		return audit_usergroup;
	}
	/**
	 * @param audit_usergroup The audit_usergroup to set.
	 */
	public void setAudit_usergroup(Audit_UserGroup audit_usergroup) {
		this.audit_usergroup = audit_usergroup;
	}


	/**
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="audit_policy_id"
	 * @hibernate.generator-param name="sequence"
	 *                            value="audit_group_component_policy_0"
	 * 
	 * 
	 * 
	 * @return Returns the audit_policy_id.
	 */
	public int getAudit_policy_id() {
		return audit_policy_id;
	}

	/**
	 * @param audit_policy_id
	 *          The audit_policy_id to set.
	 */
	public void setAudit_policy_id(int audit_policy_id) {
		this.audit_policy_id = audit_policy_id;
	}

	/**
	 * @hibernate.property type="integer"
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
}