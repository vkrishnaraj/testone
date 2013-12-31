/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Audit_UserGroup"
 */
public class Audit_UserGroup implements Serializable {

	private int audit_id;
	private int UserGroup_ID;
	private String description;
	private String description2;
	private String companycode_ID;
	private Set componentPolicies;
	private double luvlimit;
	private double bsolimit;

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
	 * @hibernate.set cascade="all" inverse="true" order-by="audit_policy_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_GroupComponentPolicy"
	 * @hibernate.key column="audit_group_id"
	 * @return Returns the componentPolicies.
	 */
	public Set getComponentPolicies() {
		return componentPolicies;
	}

	/**
	 * @param componentPolicies
	 *          The componentPolicies to set.
	 */
	public void setComponentPolicies(Set componentPolicies) {
		this.componentPolicies = componentPolicies;
	}

	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *          The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the description.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getDescription2() {
		return description2;
	}

	/**
	 * @param description
	 *          The description to set.
	 */
	public void setDescription2(String description) {
		this.description2 = description;
	}

	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the userGroup_ID.
	 */
	public int getUserGroup_ID() {
		return UserGroup_ID;
	}

	/**
	 * @param userGroup_ID
	 *          The userGroup_ID to set.
	 */
	public void setUserGroup_ID(int userGroup_ID) {
		UserGroup_ID = userGroup_ID;
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
	 * @hibernate.id generator-class="native" type="integer" column="audit_id"
	 * @hibernate.generator-param name="sequence" value="audit_usergroup_0"
	 * 
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
	
	/**
	 * @hibernate.property type="double"
	 * 
	 * @return Returns the luvlimit.
	 */
	public double getLuvlimit() {
		return luvlimit;
	}
	
	/**
	 * @param luvlimit
	 *          The luvlimit to set.
	 */
	public void setLuvlimit(double luvlimit) {
		this.luvlimit = luvlimit;
	}
	
	
	/**
	 * @hibernate.property type="double"
	 * 
	 * @return Returns the bsolimit.
	 */	
	public double getBsolimit() {
		return bsolimit;
	}
	
	/**
	 * @param bsolimit
	 *          The bsolimit to set.
	 */
	public void setBsolimit(double bsolimit) {
		this.bsolimit = bsolimit;
	}
	
}