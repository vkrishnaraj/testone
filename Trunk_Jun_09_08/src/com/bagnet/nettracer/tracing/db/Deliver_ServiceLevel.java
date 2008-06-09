package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * @hibernate.class table="deliver_servicelevel"
 */
public class Deliver_ServiceLevel implements Serializable {
	private int servicelevel_ID;
	private String description;
	private DeliverCompany delivercompany;
	private boolean active;
	

	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *          The description to set.
	 */
	public void setDescription(String desc) {
		this.description = desc;
	}

	/**
	 * @return Returns the servicelevel_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="servicelevel_ID"
	 * @hibernate.generator-param name="sequence" value="deliver_servicelevel_0"
	 *  
	 */
	public int getServicelevel_ID() {
		return servicelevel_ID;
	}

	/**
	 * @param servicelevel_ID
	 *          The servicelevel_ID to set.
	 */
	public void setServicelevel_ID(int servicelevel_ID) {
		this.servicelevel_ID = servicelevel_ID;
	}
	
	
	/**
	 * @return Returns the delivercompany.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.DeliverCompany"
	 *                        column="delivercompany_ID"
	 */
	public DeliverCompany getDelivercompany() {
		return delivercompany;
	}
	/**
	 * @param delivercompany The delivercompany to set.
	 */
	public void setDelivercompany(DeliverCompany delivercompany) {
		this.delivercompany = delivercompany;
	}
	
	/**
	 * @return Returns the active.
	 * 
	 * @hibernate.property type="boolean"
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *          The active to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}