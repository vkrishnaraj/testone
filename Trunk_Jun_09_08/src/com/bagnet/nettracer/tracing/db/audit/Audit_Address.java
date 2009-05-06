/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.Date;

import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Administrator
 * 
 * create date - Jul 14, 2004
 * @hibernate.class table="audit_address"
 */
public class Audit_Address implements Serializable {
	private int audit_address_id;
	private int Address_ID;

	private String address1;
	private String address2;
	private String city;
	private String zip;
	private String hotel;
	private String homephone;
	private String workphone;
	private String mobile;
	private String pager;
	private String altphone;
	private String email;
	private int addresstype;
	private boolean permanent;
	private String state_ID;
	private String countrycode_ID;
	private String province;

	private Date valid_bdate;
	private Date valid_edate;

	private String _DATEFORMAT; //for date time format purpose only, not
	
	private Audit_Passenger audit_passenger;
	
	/**
	 * @return Returns the audit_address_id.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_address_0"
	 * 
	 *  
	 */
	public int getAudit_address_id() {
		return audit_address_id;
	}

	/**
	 * @param audit_address_id
	 *          The audit_address_id to set.
	 */
	public void setAudit_address_id(int audit_address_id) {
		this.audit_address_id = audit_address_id;
	}

	/**
	 * @return Returns the audit_passenger.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_Passenger"
	 *                        column="audit_passenger_id"
	 */
	public Audit_Passenger getAudit_passenger() {
		return audit_passenger;
	}
	/**
	 * @param audit_passenger The audit_passenger to set.
	 */
	public void setAudit_passenger(Audit_Passenger audit_passenger) {
		this.audit_passenger = audit_passenger;
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
	 * @return Returns the is_permanent.
	 * 
	 * @hibernate.property type="boolean" column="is_permanent"
	 */
	public boolean isPermanent() {
		return permanent;
	}
	/**
	 * @param is_permanent The is_permanent to set.
	 */
	public void setPermanent(boolean is_permanent) {
		this.permanent = is_permanent;
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @return Returns the hotel.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getHotel() {
		return hotel;
	}

	/**
	 * @param hotel
	 *          The hotel to set.
	 */
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	/**
	 * @return Returns the mobile.
	 * 
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @return Returns the province.
	 * 
	 * @hibernate.property type="string"
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

	/**
	 * @return Returns the valid_bdate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getValid_bdate() {
		return valid_bdate;
	}

	/**
	 * @param valid_bdate
	 *          The valid_bdate to set.
	 */
	public void setValid_bdate(Date valid_bdate) {
		this.valid_bdate = valid_bdate;
	}

	public void setDispvalid_bdate(String s) {
		setValid_bdate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	public String getDispvalid_bdate() {
		return DateUtils.formatDate(getValid_bdate(), get_DATEFORMAT(), null, null);
	}

	/**
	 * @return Returns the valid_edate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getValid_edate() {
		return valid_edate;
	}

	/**
	 * @param valid_edate
	 *          The valid_edate to set.
	 */
	public void setValid_edate(Date valid_edate) {
		this.valid_edate = valid_edate;
	}

	public void setDispvalid_edate(String s) {
		setValid_edate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	public String getDispvalid_edate() {
		return DateUtils.formatDate(getValid_edate(), get_DATEFORMAT(), null, null);
	}

	/**
	 * @return Returns the _DATEFORMAT.
	 */
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	public String getState() {
		if (state_ID != null && state_ID.length() > 0) {
			return TracerUtils.getState(state_ID).getState();
		}
		return "";
	}

	public String getCountry() {
		if (countrycode_ID != null && countrycode_ID.length() > 0) {
			return TracerUtils.getCountry(countrycode_ID).getCountry();
		}
		return "";
	}

}