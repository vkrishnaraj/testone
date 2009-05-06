/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Audit_CompanyCode"
 */
public class Audit_Company implements Serializable {

	private int audit_id;
	private String CompanyCode_ID;
	private String companydesc;
	private String address1;
	private String address2;
	private String city;
	private String state_ID;
	private String countrycode_ID;
	private String zip;
	private String phone;
	private String email_address;
	private Audit_Company_Specific_Variable variable;

	private Agent modifying_agent;
	private Date time_modified;
	private String reason_modified;

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE; // timezone

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

	/**
	 * @return Returns the _TIMEFORMAT.
	 */
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	/**
	 * @param _timeformat
	 *          The _TIMEFORMAT to set.
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	/**
	 * @return Returns the _TIMEZONE.
	 */
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	/**
	 * @param _timezone
	 *          The _TIMEZONE to set.
	 */
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}

	public String getDisplaytime_modified() {
		Date completedate = DateUtils.convertToDate(this.getTime_modified().toString(),
				TracingConstants.DB_DATETIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="modifying_agent_ID"
	 * @return Returns the modifying_agent.
	 */
	public Agent getModifying_agent() {
		return modifying_agent;
	}

	/**
	 * @param modifying_agent
	 *          The modifying_agent to set.
	 */
	public void setModifying_agent(Agent modifying_agent) {
		this.modifying_agent = modifying_agent;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the reason_modified.
	 */
	public String getReason_modified() {
		return reason_modified;
	}

	/**
	 * @param reason_modified
	 *          The reason_modified to set.
	 */
	public void setReason_modified(String reason_modified) {
		this.reason_modified = reason_modified;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the time_modified.
	 */
	public Date getTime_modified() {
		return time_modified;
	}

	/**
	 * @param time_modified
	 *          The time_modified to set.
	 */
	public void setTime_modified(Date time_modified) {
		this.time_modified = time_modified;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the address1.
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
	 * @hibernate.property type="string"
	 * @return Returns the address2.
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
	 * @hibernate.property type="string"
	 * @return Returns the city.
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
	 * @hibernate.property type="string"
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *          The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the zip.
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
	 * @return Returns the email_address.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getEmail_address() {
		return email_address;
	}
	/**
	 * @param email_address The email_address to set.
	 */
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
	
	/**
	 * @return Returns the companyCode_ID.
	 * @hibernate.property type="string"
	 */
	public String getCompanyCode_ID() {
		return CompanyCode_ID;
	}

	/**
	 * @param companyCode_ID
	 *          The companyCode_ID to set.
	 */
	public void setCompanyCode_ID(String companyCode_ID) {
		CompanyCode_ID = companyCode_ID;
	}

	/**
	 * @return Returns the companydesc.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getCompanydesc() {
		return companydesc;
	}

	/**
	 * @param companydesc
	 *          The companydesc to set.
	 */
	public void setCompanydesc(String companydesc) {
		this.companydesc = companydesc;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the countrycode_ID.
	 */
	public String getCountrycode_ID() {
		return countrycode_ID;
	}

	/**
	 * @param countrycode_ID
	 *          The countrycode_ID to set.
	 */
	public void setCountrycode_ID(String countrycode_ID) {
		this.countrycode_ID = countrycode_ID;
	}

	/**
	 * @hibernate.property type="string"
	 * 
	 * @return Returns the state_ID.
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_Company_Specific_Variable"
	 *                        column="audit_variablecode"
	 * @return Returns the variable.
	 */
	public Audit_Company_Specific_Variable getVariable() {
		return variable;
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

	/**
	 * @param variable
	 *          The variable to set.
	 */
	public void setVariable(Audit_Company_Specific_Variable variable) {
		this.variable = variable;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="audit_id"
	 * 
	 * @hibernate.generator-param name="sequence" value="Audit_companycode_0"
	 * 
	 * 
	 * 
	 * @return Returns the audit_id.
	 */
	public int getAudit_id() {
		return audit_id;
	}

	/**
	 * @param audit_id
	 *          The audit_id to set.
	 */
	public void setAudit_id(int audit_id) {
		this.audit_id = audit_id;
	}
}