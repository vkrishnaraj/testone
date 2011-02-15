/*
 * Created on Aug 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="OHD_Passenger"
 */
public class TraceOHD_Passenger implements Serializable {

	private static final long serialVersionUID = 1L;
	public int passenger_id;
	public String firstname = "";
	public String middlename = "";
	public String lastname = "";
	public int isprimary = 1;
	public Set addresses;
	
	private TraceOHD ohd;
	
	public JRBeanCollectionDataSource getAddressesForReport() {
		if (addresses == null || addresses.size() < 1) return null;

		return new JRBeanCollectionDataSource(new ArrayList(addresses));
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the isprimary.
	 */
	public int getIsprimary() {
		return isprimary;
	}

	/**
	 * @param isprimary
	 *          The isprimary to set.
	 */
	public void setIsprimary(int isprimary) {
		this.isprimary = isprimary;
	}
	
	/**
	 * @return Returns the ohd.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.TraceOHD"
	 *                        column="OHD_ID" not-null="true"
	 * 
	 */
	public TraceOHD getOhd() {
		return ohd;
	}
	/**
	 * @param ohd The ohd to set.
	 */
	public void setOhd(TraceOHD ohd) {
		this.ohd = ohd;
	}
	
	/**
	 * @hibernate.set cascade="all" inverse="true" order-by="Address_ID"
	 * @hibernate.key column="passenger_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.TraceOHD_Address"
	 * @return Returns the addresses.
	 */
	public Set getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 *          The addresses to set.
	 */
	public void setAddresses(Set addresses) {
		this.addresses = addresses;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the firstname.
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *          The firstname to set.
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the lastname.
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *          The lastname to set.
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the middlename.
	 */
	public String getMiddlename() {
		return middlename;
	}

	/**
	 * @param middlename
	 *          The middlename to set.
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="passenger_id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="ohd_passenger_0"
	 * 
	 * @return Returns the passenger_id.
	 */
	public int getPassenger_id() {
		return passenger_id;
	}

	/**
	 * @param passenger_id
	 *          The passenger_id to set.
	 */
	public void setPassenger_id(int passenger_id) {
		this.passenger_id = passenger_id;
	}

	public TraceOHD_Address getAddress(int i) {
		if (this.getAddresses() != null && i < this.getAddresses().size()) {
			ArrayList t = new ArrayList(this.getAddresses());
			return (TraceOHD_Address) t.get(i);
		} else return null;
	}

	public void addAddress(TraceOHD_Address address) {
		if (this.getAddresses() == null) this.setAddresses(new HashSet());
		this.getAddresses().add(address);
	}

}