/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Audit_OHD_Address"
 */
public class Audit_OHD_Address implements Serializable {
	private int id;
	private int Address_ID;
	private String address1 = "";
	private String address2 = "";
	private String zip = "";
	private String homephone = "";
	private String workphone = "";
	private String mobile = "";
	private String pager = "";
	private String city = "";
	private String altphone = "";
	private String email = "";
	private int addresstype;
	private String state_ID = "";
	private String countrycode_ID = "";
	private String province = "";
	
	private Audit_OHD_Passenger audit_ohd_passenger;
	

	/**
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_ohd_address_0"
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

	
	/**
	 * @return Returns the audit_ohd_passenger.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Passenger"
	 *                        column="audit_ohd_passenger_id"
	 */
	public Audit_OHD_Passenger getAudit_ohd_passenger() {
		return audit_ohd_passenger;
	}
	/**
	 * @param audit_ohd_passenger The audit_ohd_passenger to set.
	 */
	public void setAudit_ohd_passenger(Audit_OHD_Passenger audit_ohd_passenger) {
		this.audit_ohd_passenger = audit_ohd_passenger;
	}
	
	public String getState() {
		if (state_ID != null && state_ID.length() > 0) {
			return TracerUtils.getState(state_ID, "en").getState();
		}
		return "";
	}

	public String getCountry() {
		if (countrycode_ID != null && countrycode_ID.length() > 0) {
			return TracerUtils.getCountry(countrycode_ID, "en").getCountry();
		}
		return "";
	}

	/**
	 * @return Returns the countrycode_ID.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getCountrycode_ID() {
		return countrycode_ID;
	}

	/**
	 * @param country_ID
	 *          The countrycode_ID to set.
	 */
	public void setCountrycode_ID(String countrycode_ID) {
		this.countrycode_ID = countrycode_ID;
	}

	/**
	 * @return Returns the state_ID.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getState_ID() {
		return state_ID;
	}

	/**
	 * @param state_ID
	 *          The state_ID to set.
	 */
	public void setState_ID(String state_ID) {
		this.state_ID = state_ID;
	}

	/**
	 * @return Returns the addresstype.
	 * 
	 * @hibernate.property type="integer" length="6"
	 */
	public int getAddresstype() {
		return addresstype;
	}

	/**
	 * @param addresstype
	 *          The addresstype to set.
	 */
	public void setAddresstype(int addresstype) {
		this.addresstype = addresstype;
	}

	/**
	 * @return Returns the address_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getAddress_ID() {
		return Address_ID;
	}

	/**
	 * @param address_ID
	 *          The address_ID to set.
	 */
	public void setAddress_ID(int address_ID) {
		Address_ID = address_ID;
	}

	/**
	 * @return Returns the address1.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1
	 *          The address1 to set.
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return Returns the address2.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2
	 *          The address2 to set.
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return Returns the altphone.
	 * 
	 * @hibernate.property type="string" length="15"
	 */
	public String getAltphone() {
		return altphone;
	}

	/**
	 * @param altphone
	 *          The altphone to set.
	 */
	public void setAltphone(String altphone) {
		this.altphone = altphone;
	}

	/**
	 * @return Returns the city.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *          The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return Returns the email.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *          The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the homephone.
	 * 
	 * @hibernate.property type="string" length="15"
	 */
	public String getHomephone() {
		return homephone;
	}

	/**
	 * @param homephone
	 *          The homephone to set.
	 */
	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	/**
	 * @return Returns the mobile.
	 * 
	 * @hibernate.property type="string" length="15"
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *          The mobile to set.
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return Returns the pager.
	 * 
	 * @hibernate.property type="string" length="15"
	 */
	public String getPager() {
		return pager;
	}

	/**
	 * @param pager
	 *          The pager to set.
	 */
	public void setPager(String pager) {
		this.pager = pager;
	}

	/**
	 * @return Returns the workphone.
	 * 
	 * @hibernate.property type="string" length="15"
	 */
	public String getWorkphone() {
		return workphone;
	}

	/**
	 * @param workphone
	 *          The workphone to set.
	 */
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	/**
	 * @return Returns the zip.
	 * 
	 * @hibernate.property type="string" length="9"
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip
	 *          The zip to set.
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the province.
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *          The province to set.
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	public boolean equals(Object obj) {
		Audit_OHD_Address aoi = (Audit_OHD_Address) obj;
		boolean ret = true;

		if (AuditOHDUtils.notEqualObjects(aoi.getAddress1(), this.getAddress1())
				|| AuditOHDUtils.notEqualObjects(aoi.getAddress2(), this.getAddress2())
				|| AuditOHDUtils.notEqualObjects(aoi.getZip(), this.getZip())
				|| AuditOHDUtils.notEqualObjects(aoi.getHomephone(), this.getHomephone())
				|| AuditOHDUtils.notEqualObjects(aoi.getWorkphone(), this.getWorkphone())
				|| AuditOHDUtils.notEqualObjects(aoi.getMobile(), this.getMobile())
				|| AuditOHDUtils.notEqualObjects(aoi.getCity(), this.getCity())
				|| AuditOHDUtils.notEqualObjects(aoi.getAltphone(), this.getAltphone())
				|| AuditOHDUtils.notEqualObjects(aoi.getEmail(), this.getEmail())
				|| AuditOHDUtils.notEqualObjects(aoi.getState(), this.getState())
				|| AuditOHDUtils.notEqualObjects(aoi.getCountry(), this.getCountry())
				|| AuditOHDUtils.notEqualObjects(aoi.getProvince(), this.getProvince())) {
			ret = false;
		}
		return ret;
	}

}