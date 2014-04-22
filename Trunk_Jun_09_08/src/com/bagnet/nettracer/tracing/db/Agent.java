/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;

import com.bagnet.nettracer.tracing.bmo.UsergroupBMO;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Agent"
 */
public class Agent implements Serializable {

	private static final long serialVersionUID = 6039540000777064696L;

	private int Agent_ID;
	private Station station;
	private Subcompany subcompany;

	private int usergroup_id;
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
	private int is_wt_user;
	private boolean resetPassword;
	private int failedLogins;

	private Date last_logged_on;
	private int is_online;
	private Date last_pass_reset_date;
	private boolean accountLocked;
	
	private boolean web_enabled;
	private boolean ws_enabled;
	private int max_ws_sessions;
	private UserGroup cachedGroup = null;
	
	private boolean inboundQueue;
	private double loadpercentage;
	private boolean inbound;
	private boolean acaa;
	private boolean damaged;
	
	public Agent() { }
	
	public Agent(int Agent_ID) {
		this.Agent_ID = Agent_ID;
	}
	
	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Work_Shift"
	 *                        column="shift_id" fetch="select"
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
	 *                        column="dateformat_ID" fetch="select"
	 *  
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
	 *                        column="timeformat_ID" fetch="select"
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
			sb.append(firstname.substring(0, 1).toUpperCase());
		}
		if (mname != null && mname.length() > 1) {
			sb.append(mname.substring(0, 1).toUpperCase());
		}
		if (lastname != null && lastname.length() > 1) {
			sb.append(lastname.substring(0, 1).toUpperCase());
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
	 * @return Returns the agent_ID.
	 * @hibernate.id generator-class="native" type="integer" column="Agent_ID"
	 *               unsaved-value="0"
	 * 
	 * @hibernate.generator-param name="sequence" value="AGENT_0"
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
   * @hibernate.property type="integer"
	 * @return Returns the group.
	 */
	public int getUsergroup_id() {
		return usergroup_id;
	}
	
	

	/**
	 * @param group
	 *          The group to set.
	 */
	public void setUsergroup_id(int usergroup_id) {
		this.usergroup_id = usergroup_id;
		cachedGroup = null;
	}
	
	
	/**
	 * @return Returns the group.
	 */
	public UserGroup getGroup() {
		if (cachedGroup != null && cachedGroup.getUserGroup_ID() == this.usergroup_id) {
			return cachedGroup;
		}
		return cachedGroup = UsergroupBMO.getUsergroup(this.usergroup_id);
	}

	/**
	 * @param group
	 *          The group to set.
	 */
	public void setGroup(UserGroup group) {
		this.usergroup_id = group.getUserGroup_ID();
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="station_ID" not-null="true"
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.lf.Subcompany"
	 *                        column="subcompany_ID" not-null="false"
	 * @return Returns the station.
	 */
	public Subcompany getSubcompany() {
		return subcompany;
	}

	/**
	 * @param subcompany
	 *          The subcompany to set.
	 */
	public void setSubcompany(Subcompany subcompany) {
		this.subcompany = subcompany;
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
	 * @return Returns the last_pass_reset_date.
	 * 
	 * @hibernate.property type="timestamp"
	 */
	public Date getLast_pass_reset_date() {
		return last_pass_reset_date;
	}
	/**
	 * @param last_pass_reset_date The last_pass_reset_date to set.
	 */
	public void setLast_pass_reset_date(Date last_pass_reset_date) {
		this.last_pass_reset_date = last_pass_reset_date;
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
	 * @return the failedLogins
	 * @hibernate.property type="integer"
	 */
	public int getFailed_logins() {
		return failedLogins;
	}

	/**
	 * @param is_wt_user the is_wt_user to set
	 */
	public void setFailed_logins(int failedLogins) {
		this.failedLogins = failedLogins;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */

	public String toString() {
		StringBuffer sb = new StringBuffer(2000);
		sb.append("Agent ID=" + this.getAgent_ID() + " fname=" + this.getFirstname() + " lname="
				+ this.getLastname() + " timeout=" + this.getTimeout() + " username=" + this.getUsername()
				+ " password=" + this.getPassword() + " active=" + this.isActive() + " dlocale="
				+ this.getDefaultlocale() + " clocale=" + this.getCurrentlocale() + " dcure="
				+ this.getDefaultcurrency());
		sb.append("\n\t " + this.getStation());
		return sb.toString();
	}

	public String toXML() {
		StringBuffer sb = new StringBuffer("<agent>");
		sb.append("<Agent_ID>" + Agent_ID + "</Agent_ID>");
		sb.append("<username>" + username + "</username>");
		sb.append(station.toXML());
		sb.append("</agent>");
		return sb.toString();
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
	/**
	 * @return what percentage agent is assigned to tasks
	 * @hibernate.property type="double"
	 */
	public double getLoadpercentage() {
		return loadpercentage;
	}

	public void setLoadpercentage(double loadpercentage) {
		this.loadpercentage = loadpercentage;
	}	
	
	/**
	 * @return is agent is assigned to inbound
	 * @hibernate.property type="org.hibernate.type.BooleanType" column="inbound"
	 */
	public boolean getInbound() {
		return inbound;
	}

	public void setInbound(boolean inbound) {
		this.inbound = inbound;
	}	
	/**
	 * @return is agent is assigned to acaa
	 * @hibernate.property type="org.hibernate.type.BooleanType" column="acaa"
	 */
	public boolean getAcaa() {
		return acaa;
	}

	public void setAcaa(boolean acaa) {
		this.acaa = acaa;
	}	
	/**
	 * @return is agent is assigned to damaged
	 * @hibernate.property type="org.hibernate.type.BooleanType" column="damaged"
	 */
	public boolean getDamaged() {
		return damaged;
	}

	public void setDamaged(boolean damaged) {
		this.damaged = damaged;
	}	

	
}