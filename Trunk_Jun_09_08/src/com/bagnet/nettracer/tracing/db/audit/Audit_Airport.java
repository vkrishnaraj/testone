/*
 * Created on Jul 13, 2004
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

/**
 * @author Administrator
 * 
 * create date - Jul 14, 2004
 * @hibernate.class table="Audit_Airport"
 */
public class Audit_Airport implements Serializable {

	private int audit_id;
	private int id;
	private String airport_code;
	private String airport_desc;
	private String companyCode_ID;
	private String locale;
	private int country;
	
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
	 * @return Returns the locale.
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *          The locale to set.
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the airport_code.
	 */
	public String getAirport_code() {
		return airport_code;
	}

	/**
	 * @param airport_code
	 *          The airport_code to set.
	 */
	public void setAirport_code(String airport_code) {
		this.airport_code = airport_code;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the airport_desc.
	 */
	public String getAirport_desc() {
		return airport_desc;
	}

	/**
	 * @param airport_desc
	 *          The airport_desc to set.
	 */
	public void setAirport_desc(String airport_desc) {
		this.airport_desc = airport_desc;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the companyCode_ID.
	 */
	public String getCompanyCode_ID() {
		return companyCode_ID;
	}

	/**
	 * @param companyCode_ID
	 *          The companyCode_ID to set.
	 */
	public void setCompanyCode_ID(String companyCode_ID) {
		this.companyCode_ID = companyCode_ID;
	}

	/**
	 * @hibernate.property type="integer"
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
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 *               column="audit_id"
	 * @hibernate.generator-param name="sequence" value="audit_airport_0"
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
	
	/**
	 * @return Returns the country.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getCountry() {
		return country;
	}

	/**
	 * @param country
	 *          The country to set.
	 */
	public void setCountry(int country) {
		this.country = country;
	}
}