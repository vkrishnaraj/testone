/*
 * Created on Jul 13, 2004
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

import aero.nettracer.security.AES;

/**
 * @author Administrator
 * 
 * @hibernate.class table="audit_passenger"
 */
public class Audit_Passenger implements Serializable {
	private int audit_passenger_id;
	private int Passenger_ID;
	private String jobtitle;
	private int salutation;
	private String firstname;
	private String middlename;
	private String lastname;
	private String driverslicense;
	private String dlstate;
	private String commonnum;
	private String countryofissue;
	private int isprimary;
	private Audit_AirlineMembership audit_membership;
	private Set addresses;
	
	private String driversLicenseProvince;
	private String driversLicenseCountry;
	
	private String passportNumber;
	private String passportIssuer;
	
	private Audit_Incident audit_incident;
	
	public List getAddress_list() {
		return new ArrayList((addresses != null ? addresses : new HashSet()));
	}

	/**
	 * @return Returns the audit_passenger_id.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_passenger_0"
	 * 
	 * 
	 * 
	 *  
	 */
	public int getAudit_passenger_id() {
		return audit_passenger_id;
	}

	/**
	 * @param audit_passenger_id
	 *          The audit_passenger_id to set.
	 */
	public void setAudit_passenger_id(int audit_passenger_id) {
		this.audit_passenger_id = audit_passenger_id;
	}
	
	/**
	 * @return Returns the audit_incident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_Incident"
	 *                        column="audit_incident_id"
	 */
	public Audit_Incident getAudit_incident() {
		return audit_incident;
	}
	/**
	 * @param audit_incident The audit_incident to set.
	 */
	public void setAudit_incident(Audit_Incident audit_incident) {
		this.audit_incident = audit_incident;
	}
	
	

	/**
	 * @return Returns the membership.
	 * @hibernate.many-to-one cascade="all"
	 *                        class="com.bagnet.nettracer.tracing.db.audit.Audit_AirlineMembership"
	 *                        column="audit_membership_id"
	 */
	public Audit_AirlineMembership getAudit_membership() {
		return audit_membership;
	}

	public void setAudit_membership(Audit_AirlineMembership audit_membership) {
		this.audit_membership = audit_membership;
	}

	/**
	 * @hibernate.set cascade="all" inverse="true" order-by="address_ID"
	 * @hibernate.key column="audit_passenger_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_Address"
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
	 * @return Returns the commonnum.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getCommonnum() {
		return commonnum;
	}

	/**
	 * @param commonnum
	 *          The commonnum to set.
	 */
	public void setCommonnum(String commonnum) {
		this.commonnum = commonnum;
	}

	/**
	 * @return Returns the countryofissue.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getCountryofissue() {
		return countryofissue;
	}

	/**
	 * @param countryofissue
	 *          The countryofissue to set.
	 */
	public void setCountryofissue(String countryofissue) {
		this.countryofissue = countryofissue;
	}
	/**
	 * @return Returns the dlstate.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getDlstate() {
		return dlstate;
	}

	/**
	 * @param dlstate
	 *          The dlstate to set.
	 */
	public void setDlstate(String dlstate) {
		this.dlstate = dlstate;
	}

	/**
	 * @return Returns the driverslicense.
	 * 
	 * @hibernate.property type="string" length="10"
	 */
	public String getDriverslicense() {
		return driverslicense;
	}

	/**
	 * @param driverslicense
	 *          The driverslicense to set.
	 */
	public void setDriverslicense(String driverslicense) {
		this.driverslicense = driverslicense;
	}
	
	public String getRedactedDriversLicense() {
		return driverslicense != null && !driverslicense.isEmpty() ? "*********" : "";
	}	
	
	public String getDecriptedDriversLicense() {
		try {
			return AES.decrypt(driverslicense);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * @return Returns the firstname.
	 * 
	 * @hibernate.property type="string" length="20"
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
	 * @return Returns the jobtitle.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getJobtitle() {
		return jobtitle;
	}

	/**
	 * @param jobtitle
	 *          The jobtitle to set.
	 */
	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	/**
	 * @return Returns the lastname.
	 * 
	 * @hibernate.property type="string" length="20"
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
	 * @return Returns the middlename.
	 * 
	 * @hibernate.property type="string" length="20"
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
	 * @return Returns the passenger_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getPassenger_ID() {
		return Passenger_ID;
	}

	/**
	 * @param passenger_ID
	 *          The passenger_ID to set.
	 */
	public void setPassenger_ID(int passenger_ID) {
		Passenger_ID = passenger_ID;
	}

	/**
	 * @return Returns the salutation.
	 * 
	 * @hibernate.property type="integer" length="4"
	 */
	public int getSalutation() {
		return salutation;
	}

	/**
	 * @param salutation
	 *          The salutation to set.
	 */
	public void setSalutation(int salutation) {
		this.salutation = salutation;
	}

	/**
	 * @return Returns the isprimary.
	 * 
	 * @hibernate.property type="integer"
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

	public Audit_Address getAddress(int i) {
		if (this.getAddresses() != null) {
			ArrayList t = new ArrayList(this.getAddresses());
			return (Audit_Address) t.get(i);
		} else return null;
	}

	public void addAddress(Audit_Address address) {
		if (this.getAddresses() == null) this.setAddresses(new HashSet());
		this.getAddresses().add(address);
	}

	/**
	 * @hibernate.property type="string" column="dlprovince"
	 */
	public String getDriversLicenseProvince() {
		return driversLicenseProvince;
	}

	public void setDriversLicenseProvince(String driversLicenseProvince) {
		this.driversLicenseProvince = driversLicenseProvince;
	}

	/**
	 * @hibernate.property type="string" length="2" column="dlcountry"
	 */
	public String getDriversLicenseCountry() {
		return driversLicenseCountry;
	}

	public void setDriversLicenseCountry(String driversLicenseCountry) {
		this.driversLicenseCountry = driversLicenseCountry;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPassportIssuer() {
		return passportIssuer;
	}

	public void setPassportIssuer(String passportIssuer) {
		this.passportIssuer = passportIssuer;
	}
	
	public String getRedactedPassportNumber() {
		return passportNumber != null && !passportNumber.isEmpty() ? "***************" : "";
	}
	
	public void setRedactedPassportNumber(String redactedPassportNumber) {
		// NOOP for struts
	}
	
	public void setDecryptedPassportNumber(String decryptedPassportNumber) {
		if (decryptedPassportNumber == null || decryptedPassportNumber.isEmpty()) return;
		try {
			this.passportNumber = AES.encrypt(decryptedPassportNumber);
		} catch (Exception e) {
			e.printStackTrace();
			this.passportNumber = null;
		}
	}
	
	public String getDecryptedPassportNumber() {
		try {
			return AES.decrypt(passportNumber);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}