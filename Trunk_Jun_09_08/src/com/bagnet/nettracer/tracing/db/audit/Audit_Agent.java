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
import com.bagnet.nettracer.tracing.db.NTDateFormat;
import com.bagnet.nettracer.tracing.db.NTTimeFormat;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.db.Work_Shift;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Audit_Agent"
 */
public class Audit_Agent implements Serializable {

	private int audit_agent_id;
	private int Agent_ID;
	private Station station;
	private UserGroup group;
	private String firstname;
	private String mname;
	private String lastname;
	private int timeout;
	private String username;
	private String password;
	private boolean active;
	private String defaultlocale;
	private String currentlocale;
	private String defaultcurrency;
	private String defaulttimezone;
	private String currenttimezone;
	private Work_Shift shift;
	private NTDateFormat dateformat;
	private NTTimeFormat timeformat;
	private String companycode_ID;

	private Date last_logged_on;
	private int is_online;

	private Agent modifying_agent;
	private Date time_modified;
	private String reason_modified;
	
	private int is_wt_user;
	
	private boolean ws_enabled;
	private boolean web_enabled;
	private int max_ws_sessions;

	private boolean resetPassword;
	private boolean accountLocked;
	
	private boolean inboundQueue;

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE; // timezone

	public String getDisplaytime_modified() {
		Date completedate = DateUtils.convertToDate(this.getTime_modified().toString(),
				TracingConstants.DB_DATETIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	public String getDtZone() {
		return AdminUtils.getTimeZoneById(defaulttimezone).getTimezone();
	}

	public String getCtZone() {
		return AdminUtils.getTimeZoneById(currenttimezone).getTimezone();
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Work_Shift"
	 *                        column="shift_id"
	 * @return Returns the shift.
	 */
	public Work_Shift getShift() {
		return shift;
	}

	/**
	 * @param shift
	 *          The shift to set.
	 */
	public void setShift(Work_Shift shift) {
		this.shift = shift;
	}

	/**
	 * @return Returns the dateformat.
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.NTDateFormat"
	 *                        column="dateformat_ID"
	 */
	public NTDateFormat getDateformat() {
		return dateformat;
	}

	/**
	 * @param dateformat
	 *          The dateformat to set.
	 */
	public void setDateformat(NTDateFormat dateformat) {
		this.dateformat = dateformat;
	}

	/**
	 * @return Returns the timeformat.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.NTTimeFormat"
	 *                        column="timeformat_ID"
	 */
	public NTTimeFormat getTimeformat() {
		return timeformat;
	}

	/**
	 * @param timeformat
	 *          The timeformat to set.
	 */
	public void setTimeformat(NTTimeFormat timeformat) {
		this.timeformat = timeformat;
	}

	public String getInitial() {
		StringBuffer sb = new StringBuffer(3);
		if (firstname != null && firstname.length() > 1) {
			sb.append(firstname.substring(0, 1));
		}
		if (mname != null && mname.length() > 1) {
			sb.append(mname.substring(0, 1));
		}
		if (lastname != null && lastname.length() > 1) {
			sb.append(lastname.substring(0, 1));
		}
		return sb.toString();
	}

	/**
	 * @return Returns the mname.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getMname() {
		return mname;
	}

	/**
	 * @param mname
	 *          The mname to set.
	 */
	public void setMname(String mname) {
		this.mname = mname;
	}

	/**
	 * @return Returns the active.
	 * 
	 * @hibernate.property type="boolean"
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *          The active to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 *               column="audit_agent_id"
	 * @hibernate.generator-param name="sequence" value="audit_agent_0"
	 * @return Returns the audit_agent_id.
	 */
	public int getAudit_agent_id() {
		return audit_agent_id;
	}

	/**
	 * @param audit_agent_id
	 *          The audit_agent_id to set.
	 */
	public void setAudit_agent_id(int audit_agent_id) {
		this.audit_agent_id = audit_agent_id;
	}

	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the agent_ID.
	 */
	public int getAgent_ID() {
		return Agent_ID;
	}

	/**
	 * @param agent_ID
	 *          The agent_ID to set.
	 */
	public void setAgent_ID(int agent_ID) {
		Agent_ID = agent_ID;
	}

	/**
	 * @return Returns the currentlocale.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getCurrentlocale() {
		return currentlocale;
	}

	/**
	 * @param currentlocale
	 *          The currentlocale to set.
	 */
	public void setCurrentlocale(String currentlocale) {
		this.currentlocale = currentlocale;
	}

	/**
	 * @return Returns the defaultcurrency.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getDefaultcurrency() {
		return defaultcurrency;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the companycode_ID.
	 */
	public String getCompanycode_ID() {
		return companycode_ID;
	}

	/**
	 * @param companycode_ID
	 *          The companycode_ID to set.
	 */
	public void setCompanycode_ID(String companycode_ID) {
		this.companycode_ID = companycode_ID;
	}

	/**
	 * @param defaultcurrency
	 *          The defaultcurrency to set.
	 */
	public void setDefaultcurrency(String defaultcurrency) {
		this.defaultcurrency = defaultcurrency;
	}

	/**
	 * @return Returns the defaultlocale.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getDefaultlocale() {
		return defaultlocale;
	}

	/**
	 * @param defaultlocale
	 *          The defaultlocale to set.
	 */
	public void setDefaultlocale(String defaultlocale) {
		this.defaultlocale = defaultlocale;
	}

	/**
	 * @return Returns the firstname.
	 * 
	 * @hibernate.property type="string"
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
	 * @return Returns the lastname.
	 * 
	 * @hibernate.property type="string"
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
	 * @return Returns the password.
	 * @hibernate.property type="string"
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *          The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the timeout.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *          The timeout to set.
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return Returns the username.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *          The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return Returns the currenttimezone.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getCurrenttimezone() {
		return currenttimezone;
	}

	/**
	 * @param currenttimezone
	 *          The currenttimezone to set.
	 */
	public void setCurrenttimezone(String currenttimezone) {
		this.currenttimezone = currenttimezone;
	}

	/**
	 * @return Returns the defaulttimezone.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getDefaulttimezone() {
		return defaulttimezone;
	}

	/**
	 * @param defaulttimezone
	 *          The defaulttimezone to set.
	 */
	public void setDefaulttimezone(String defaulttimezone) {
		this.defaulttimezone = defaulttimezone;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.UserGroup"
	 *                        column="usergroup_ID"
	 * @return Returns the group.
	 */
	public UserGroup getGroup() {
		return group;
	}

	/**
	 * @param group
	 *          The group to set.
	 */
	public void setGroup(UserGroup group) {
		this.group = group;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="station_ID"
	 * @return Returns the station.
	 */
	public Station getStation() {
		return station;
	}

	/**
	 * @param station
	 *          The station to set.
	 */
	public void setStation(Station station) {
		this.station = station;
	}

	/**
	 * @return Returns the is_online.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getIs_online() {
		return is_online;
	}

	/**
	 * @param is_online
	 *          The is_online to set.
	 */
	public void setIs_online(int is_online) {
		this.is_online = is_online;
	}

	/**
	 * @return Returns the last_logged_on.
	 * 
	 * @hibernate.property type="timestamp"
	 */
	public Date getLast_logged_on() {
		return last_logged_on;
	}

	/**
	 * @param last_logged_on
	 *          The last_logged_on to set.
	 */
	public void setLast_logged_on(Date last_logged_on) {
		this.last_logged_on = last_logged_on;
	}
	
	
	/**
	 * @return the is_wt_user
	 * @hibernate.property type="integer"
	 */
	public int getIs_wt_user() {
		return is_wt_user;
	}

	/**
	 * @param is_wt_user the is_wt_user to set
	 */
	public void setIs_wt_user(int is_wt_user) {
		this.is_wt_user = is_wt_user;
	}

	/**
	 * @return the ws_enabled
	 * 
	 * @hibernate.property type="boolean"
	 */
	public boolean isWs_enabled() {
		return ws_enabled;
	}

	/**
	 * @param ws_enabled the ws_enabled to set
	 */
	public void setWs_enabled(boolean ws_enabled) {
		this.ws_enabled = ws_enabled;
	}

	/**
	 * @return the web_enabled
	 * 
	 * @hibernate.property type="boolean"
	 */
	public boolean isWeb_enabled() {
		return web_enabled;
	}

	/**
	 * @param web_enabled the web_enabled to set
	 */
	public void setWeb_enabled(boolean web_enabled) {
		this.web_enabled = web_enabled;
	}

	/**
	 * @return the max_ws_sessions
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getMax_ws_sessions() {
		return max_ws_sessions;
	}

	/**
	 * @param max_ws_sessions the max_ws_sessions to set
	 */
	public void setMax_ws_sessions(int max_ws_sessions) {
		this.max_ws_sessions = max_ws_sessions;
	}
	
	/**
	 * @return the resetPassword
	 * @hibernate.property type="boolean"
	 */
	public boolean isReset_password() {
		return resetPassword;
	}

	/**
	 * @param resetPassword the resetPassword to set
	 */
	public void setReset_password(boolean resetPassword) {
		this.resetPassword = resetPassword;
	}

	/**
	 * @return whether the account is locked
	 * @hibernate.property type="boolean"
	 */
	public boolean isAccount_locked() {
		return accountLocked;
	}

	/**
	 * @param accountLocked locks the account out
	 */
	public void setAccount_locked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	
	/**
	 * @return whether agent can be assigned to inbound queue
	 * @hibernate.property type="boolean"
	 */
	public boolean isInboundQueue() {
		return inboundQueue;
	}

	public void setInboundQueue(boolean inboundQueue) {
		this.inboundQueue = inboundQueue;
	}
}