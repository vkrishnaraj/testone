/*
 * Created on Aug 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="Audit_OHD_Passenger"
 */
public class Audit_OHD_Passenger implements Serializable {
	private int id;
	public int passenger_id;
	public String firstname;
	public String middlename;
	public String lastname;
	public int isprimary = 1;
	public Set addresses;
	
	private Audit_OHD ohd;

	/**
	 * @return Returns the ohd.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_OHD"
	 *                        column="audit_ohd_id"
	 */
	public Audit_OHD getOhd() {
		return ohd;
	}
	/**
	 * @param ohd The ohd to set.
	 */
	public void setOhd(Audit_OHD ohd) {
		this.ohd = ohd;
	}
		
	
	public List getAddressList() {
		return new ArrayList((addresses != null ? addresses : new HashSet()));
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_ohd_passenger_0"
	 * 
	 * 
	 * 
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *          The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

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
	 * @hibernate.set cascade="all" inverse="true" order-by="Address_ID"
	 * @hibernate.key column="audit_ohd_passenger_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Address"
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
	 * @hibernate.property type="integer"
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

	public Audit_OHD_Address getAddress(int i) {
		if (this.getAddresses() != null) {
			ArrayList t = new ArrayList(this.getAddresses());
			return (Audit_OHD_Address) t.get(i);
		} else return null;
	}

	public void addAddress(Audit_OHD_Address address) {
		if (this.getAddresses() == null) this.setAddresses(new HashSet());
		this.getAddresses().add(address);
	}

	public boolean equals(Object obj) {
		Audit_OHD_Passenger aoi = (Audit_OHD_Passenger) obj;
		boolean ret = true;

		if (AuditOHDUtils.notEqualObjects(aoi.getFirstname(), this.getFirstname())
				|| AuditOHDUtils.notEqualObjects(aoi.getMiddlename(), this.getMiddlename())
				|| AuditOHDUtils.notEqualObjects(aoi.getLastname(), this.getLastname())) {
			ret = false;
		} else {
			//check for the address List..
			List thisAddressList = this.getAddressList();
			List compAddressList = aoi.getAddressList();

			if (thisAddressList != compAddressList) {
				if (thisAddressList == null || compAddressList == null) ret = false;
				else if (thisAddressList.size() != compAddressList.size()) ret = false;
				else {
					//Size of the sets are equal; compare each item.
					for (int j = 0; j < thisAddressList.size(); j++) {
						Audit_OHD_Address addr1 = (Audit_OHD_Address) thisAddressList.get(j);
						Audit_OHD_Address addr2 = (Audit_OHD_Address) compAddressList.get(j);

						if (AuditOHDUtils.notEqualObjects(addr1, addr2)) {
							ret = false;
						}
					}
				}
			}
		}
		return ret;
	}

}