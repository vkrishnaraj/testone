package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Administrator
 * 
 * create date - Jul 14, 2004
 * @hibernate.class table="Billing"
 */
public class Billing implements Serializable {

	private int billing_id;
	private String companyCode;
	private int station_id;
	private Date create_date_time;
	private Date status_change_time;
	private int agent_id;

	private Incident incident;

	private String _DATEFORMAT; // currently logged in agent's date format
	private String _TIMEFORMAT; // currently logged in agent's time format
	private TimeZone _TIMEZONE; // currently logged in agent's time zone

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

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the agent_id.
	 */
	public int getAgent_id() {
		return agent_id;
	}

	/**
	 * @param agent_id
	 *          The agent_id to set.
	 */
	public void setAgent_id(int agent_id) {
		this.agent_id = agent_id;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="billing_id"
	 * @hibernate.generator-param name="sequence" value="billing_0"
	 * 
	 * 
	 * @return Returns the billing_id.
	 */
	public int getBilling_id() {
		return billing_id;
	}

	/**
	 * @param billing_id
	 *          The billing_id to set.
	 */
	public void setBilling_id(int billing_id) {
		this.billing_id = billing_id;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *          The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the create_date_time.
	 */
	public Date getCreate_date_time() {
		return create_date_time;
	}

	/**
	 * @param create_date_time
	 *          The create_date_time to set.
	 */
	public void setCreate_date_time(Date create_date_time) {
		this.create_date_time = create_date_time;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the station_id.
	 */
	public int getStation_id() {
		return station_id;
	}

	/**
	 * @param station_id
	 *          The station_id to set.
	 */
	public void setStation_id(int station_id) {
		this.station_id = station_id;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the status_change_time.
	 */
	public Date getStatus_change_time() {
		return status_change_time;
	}

	/**
	 * @param status_change_time
	 *          The status_change_time to set.
	 */
	public void setStatus_change_time(Date status_change_time) {
		this.status_change_time = status_change_time;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Incident"
	 *                        column="report_num"
	 * 
	 * @return Returns the incident.
	 */
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param incident
	 *          The incident to set.
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
}