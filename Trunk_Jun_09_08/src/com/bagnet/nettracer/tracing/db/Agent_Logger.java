/*
 * Created on Jul 14, 2004
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
 * @author Administrator
 * 
 * @hibernate.class table="agent_logger"
 */
public class Agent_Logger implements Serializable {

	private int ID;
	private int agent_ID;
	private String companycode_ID;
	private Date log_in_time;
	private Date log_off_time;

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;

	private String username;

	private boolean expired = false;

	/**
	 * @return Returns the agent_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getAgent_ID() {
		return agent_ID;
	}
	/**
	 * @param agent_ID The agent_ID to set.
	 */
	public void setAgent_ID(int agent_ID) {
		this.agent_ID = agent_ID;
	}
	/**
	 * @return Returns the companycode_ID.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getCompanycode_ID() {
		return companycode_ID;
	}
	/**
	 * @param companycode_ID The companycode_ID to set.
	 */
	public void setCompanycode_ID(String companycode_ID) {
		this.companycode_ID = companycode_ID;
	}
	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	/**
	 * @param _timeformat
	 *          The _TIMEFORMAT to set.
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	/**
	 * @param _timezone
	 *          The _TIMEZONE to set.
	 */
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}



	public String getDisplayLoggedOn() {
		return DateUtils.formatDate(this.getLog_in_time(), _DATEFORMAT + " " + _TIMEFORMAT, null,
				_TIMEZONE);
	}

	public String getDisplayLoggedOff() {

		if (this.getLog_off_time() == null) return "";

		return DateUtils.formatDate(this.getLog_off_time(), _DATEFORMAT + " " + _TIMEFORMAT, null,
				_TIMEZONE);
	}


	/**
	 * @return Returns the agent_ID.
	 * @hibernate.id generator-class="native" type="integer" column="ID"
	 *               unsaved-value="0"
	 * 
	 * @hibernate.generator-param name="sequence" value="AGENT_LOGGER_0"
	 */
	public int getID() {
		return ID;
	}

	public void setID(int id) {
		ID = id;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the log_in_time.
	 */
	public Date getLog_in_time() {
		return log_in_time;
	}

	/**
	 * @param log_in_time
	 *          The log_in_time to set.
	 */
	public void setLog_in_time(Date log_in_time) {
		this.log_in_time = log_in_time;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the log_off_time.
	 */
	public Date getLog_off_time() {
		return log_off_time;
	}

	/**
	 * @param log_off_time
	 *          The log_off_time to set.
	 */
	public void setLog_off_time(Date log_off_time) {
		this.log_off_time = log_off_time;
	}
	
	
	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return if the session expired
	 * 
	 * @hibernate.property type="boolean"
	 */
	public boolean isExpired() {
		return expired;
	}
	/**
	 * sets the expired status
	 * @param expired
	 * 
	 * @hibernate.property type="boolean"
	 */
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
}