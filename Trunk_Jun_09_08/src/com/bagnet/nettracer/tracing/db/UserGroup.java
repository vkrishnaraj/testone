/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Administrator
 * 
 * @hibernate.class table="UserGroup"
 */
public class UserGroup implements Serializable {
	private int UserGroup_ID;
	private String description;
	private String description2;
	private Set componentPolicies;
	
	private String companycode_ID;

	/**
	 * @hibernate.set cascade="all-delete-orphan" order-by="policy_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.GroupComponentPolicy"
	 * @hibernate.key column="group_id"
	 * @return Returns the componentPolicies.
	 */
	public Set getComponentPolicies() {
		return componentPolicies;
	}

	/**
	 * @param componentPolicies
	 *          The componentPolicies to set.
	 */
	public void setComponentPolicies(Set componentPolicies) {
		this.componentPolicies = componentPolicies;
	}

	

	/**
	 * @return Returns the companycode_ID.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getCompanycode_ID() {
		return companycode_ID;
	}
	/**
	 * @param companycode_ID The companycode_ID to set.
	 */
	public void setCompanycode_ID(String companycode_ID) {
		this.companycode_ID = companycode_ID;
	}
	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *          The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getDescription2() {
		return description2;
	}

	/**
	 * @param description
	 *          The description to set.
	 */
	public void setDescription2(String description) {
		this.description2 = description;
	}

	/**
	 * @return Returns the userGroup_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="UserGroup_ID"
	 * @hibernate.generator-param name="sequence" value="UserGroup_0"
	 *  
	 */
	public int getUserGroup_ID() {
		return UserGroup_ID;
	}

	/**
	 * @param userGroup_ID
	 *          The userGroup_ID to set.
	 */
	public void setUserGroup_ID(int userGroup_ID) {
		UserGroup_ID = userGroup_ID;
	}


}