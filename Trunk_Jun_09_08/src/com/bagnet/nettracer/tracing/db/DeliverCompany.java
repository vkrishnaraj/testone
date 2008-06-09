package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 * 
 * @hibernate.class table="delivercompany"
 */
public class DeliverCompany implements Serializable {
	private int delivercompany_ID;
	private String name;
	private String address;
	private String phone;
	private Set servicelevels;
	private boolean active;
	private Company company;
	private static Logger logger = Logger.getLogger(DeliverCompany.class);

	/**
	 * @return Returns the address.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *          The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Returns the phone number.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *          The phone number to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	/**
	 * @return Returns the deliverservice_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="delivercompany_ID"
	 * @hibernate.generator-param name="sequence" value="delivercompany_0"
	 *  
	 */
	public int getDelivercompany_ID() {
		return delivercompany_ID;
	}

	/**
	 * @param delivercompany_ID
	 *          The delivercompany_ID to set.
	 */
	public void setDelivercompany_ID(int delivercompany_ID) {
		this.delivercompany_ID = delivercompany_ID;
	}

	/**
	 * @return Returns the name.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *          The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the delivercompanies.
	 * 
	 * @hibernate.set cascade="none" order-by="description"
	 * @hibernate.key column="delivercompany_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Deliver_ServiceLevel"
	 */
	public Set getServicelevels() {
		return servicelevels;
	}

	/**
	 * @param servicelevels
	 *          The servicelevels to set.
	 */
	public void setServicelevels(Set servicelevels) {
		this.servicelevels = servicelevels;
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

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Company"
	 *                        column="companycode_ID"
	 * @return Returns the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *          The company to set.
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public boolean equals(Object o) {
		boolean match = false;
		
		try {
			DeliverCompany obj = (DeliverCompany)o;
			if (obj.getDelivercompany_ID() == this.getDelivercompany_ID()) {
				match = true;
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return match;
	}
}