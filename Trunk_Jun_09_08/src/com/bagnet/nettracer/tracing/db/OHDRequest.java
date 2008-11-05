/*
 * Created on Aug 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="OHD_Request"
 */
public class OHDRequest implements Serializable {
	private int ohd_request_id;
	private Agent requestingAgent;
	private Station requestForStation;
	private String incident_ID;
	private OHD ohd;
	private Date requestTime;
	private String reason;
	private int match_id;
	private Status status;
	private int forward_id;
	private String denialReason;
	private int denied;
	private Date denialDate;

	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;

	public String getRequestingCompany() {
		return requestForStation.getCompany().getCompanyCode_ID();
	}

	public String getRequestingStation() {
		return requestForStation.getStationcode();
	}

	public String getRequestee() {
		return requestingAgent.getUsername();
	}

	public String getRequestStatus() {
		return status.getDescription();
	}

	public String getTimeRequested() {
		return DateUtils.formatDate(this.getRequestTime(), _DATEFORMAT + " " + _TIMEFORMAT, null,
				_TIMEZONE);
	}

	public String getTimeDenied() {
		if (denialDate != null) return DateUtils.formatDate(this.getDenialDate(), _DATEFORMAT + " "
				+ _TIMEFORMAT, null, _TIMEZONE);
		else return "";
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
	 * @hibernate.property type="timestamp"
	 * @return Returns the denialDate.
	 */
	public Date getDenialDate() {
		return denialDate;
	}

	/**
	 * @param denialDate
	 *          The denialDate to set.
	 */
	public void setDenialDate(Date denialDate) {
		this.denialDate = denialDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the denialReason.
	 */
	public String getDenialReason() {
		return denialReason;
	}

	/**
	 * @param denialReason
	 *          The denialReason to set.
	 */
	public void setDenialReason(String denialReason) {
		this.denialReason = denialReason;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the denied.
	 */
	public int getDenied() {
		return denied;
	}

	/**
	 * @param denied
	 *          The denied to set.
	 */
	public void setDenied(int denied) {
		this.denied = denied;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="status_id"
	 * @return Returns the status.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *          The status to set.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="ohd_request_id" unsaved-value="0"
	 * 
	 * @hibernate.generator-param name="sequence" value="OHD_REQUEST_0"
	 * 
	 * @return Returns the ohd_request_id.
	 */
	public int getOhd_request_id() {
		return ohd_request_id;
	}

	/**
	 * @param ohd_request_id
	 *          The ohd_request_id to set.
	 */
	public void setOhd_request_id(int ohd_request_id) {
		this.ohd_request_id = ohd_request_id;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the incident_ID.
	 */
	public String getIncident_ID() {
		return incident_ID;
	}

	/**
	 * @param incident_ID
	 *          The incident_ID to set.
	 */
	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.OHD"
	 *                        column="ohd_ID"
	 * @return Returns the ohd.
	 */
	public OHD getOhd() {
		return ohd;
	}

	/**
	 * @param ohd
	 *          The ohd to set.
	 */
	public void setOhd(OHD ohd) {
		this.ohd = ohd;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the reason.
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *          The reason to set.
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="forward_to_station_ID"
	 * @return Returns the requestForStation.
	 */
	public Station getRequestForStation() {
		return requestForStation;
	}

	/**
	 * @param requestForStation
	 *          The requestForStation to set.
	 */
	public void setRequestForStation(Station requestForStation) {
		this.requestForStation = requestForStation;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="requesting_agent_id"
	 * @return Returns the requestingAgent.
	 */
	public Agent getRequestingAgent() {
		return requestingAgent;
	}

	/**
	 * @param requestingAgent
	 *          The requestingAgent to set.
	 */
	public void setRequestingAgent(Agent requestingAgent) {
		this.requestingAgent = requestingAgent;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the requestTime.
	 */
	public Date getRequestTime() {
		return requestTime;
	}

	/**
	 * @param requestTime
	 *          The requestTime to set.
	 */
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the match_id.
	 */
	public int getMatch_id() {
		return match_id;
	}

	/**
	 * @param match_id
	 *          The match_id to set.
	 */
	public void setMatch_id(int match_id) {
		this.match_id = match_id;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the forward_id.
	 */
	public int getForward_id() {
		return forward_id;
	}

	/**
	 * @param forward_id
	 *          The forward_id to set.
	 */
	public void setForward_id(int forward_id) {
		this.forward_id = forward_id;
	}
}