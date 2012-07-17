/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Audit_Claim"
 */
public class Audit_Claim implements Serializable {
	private int audit_claim_id;
	private long Claim_ID;
	private double claimamount;
	private String claimcurrency_ID;
	private String ssn;
	private String driverslicense;
	private String dlstate;
	private String commonnum;
	private String countryofissue;
	
	private Status status;

	private Incident incident;

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;

	private Agent modify_agent;
	private Date modify_time;
	private String modify_reason;

	private Set expenses;
	private Audit_ClaimProrate audit_claimprorate;

	/**
	 * @return Returns the modify_agent.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="modify_agent_id"
	 */
	public Agent getModify_agent() {
		return modify_agent;
	}

	/**
	 * @param modify_agent
	 *          The modify_agent to set.
	 */
	public void setModify_agent(Agent modify_agent) {
		this.modify_agent = modify_agent;
	}

	/**
	 * @return Returns the modify_reason.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getModify_reason() {
		return modify_reason;
	}

	/**
	 * @param modify_reason
	 *          The modify_reason to set.
	 */
	public void setModify_reason(String modify_reason) {
		this.modify_reason = modify_reason;
	}

	/**
	 * @return Returns the modify_time.
	 * 
	 * @hibernate.property type="timestamp"
	 */
	public Date getModify_time() {
		return modify_time;
	}

	/**
	 * @param modify_time
	 *          The modify_time to set.
	 */
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}

	public String getDispmodify_time() {
		Date completedate = DateUtils.convertToDate(getModify_time().toString(),
				TracingConstants.DB_DATETIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	/**
	 * @return Returns the expenses.
	 * 
	 * 
	 * @hibernate.set cascade="all" inverse="true" order-by="createdate"
	 * @hibernate.key column="audit_claim_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_ExpensePayout"
	 *  
	 */
	public Set getExpenses() {
		return expenses;
	}

	/**
	 * @param expenses
	 *          The expenses to set.
	 */
	public void setExpenses(Set expenses) {
		this.expenses = expenses;
	}

	public ArrayList getExpense_list() {
		return new ArrayList((expenses != null ? expenses : new HashSet()));
	}

	/**
	 * @return Returns the audit_claimprorate.
	 * 
	 * @hibernate.many-to-one cascade="all"
	 *                        class="com.bagnet.nettracer.tracing.db.audit.Audit_ClaimProrate"
	 *                        column="audit_claimprorate_id"
	 */
	public Audit_ClaimProrate getAudit_claimprorate() {
		return audit_claimprorate;
	}

	/**
	 * @param audit_claimprorate
	 *          The audit_claimprorate to set.
	 */
	public void setAudit_claimprorate(Audit_ClaimProrate audit_claimprorate) {
		this.audit_claimprorate = audit_claimprorate;
	}

	/**
	 * @return Returns the audit_claim_id.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_claim_0"
	 */
	public int getAudit_claim_id() {
		return audit_claim_id;
	}

	/**
	 * @param audit_claim_id
	 *          The audit_claim_id to set.
	 */
	public void setAudit_claim_id(int audit_claim_id) {
		this.audit_claim_id = audit_claim_id;
	}

	/**
	 * @return Returns the status.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="status_ID"
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
	 * @return Returns the claimamount.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getClaimamount() {
		return claimamount;
	}

	/**
	 * @param claimamount
	 *          The claimamount to set.
	 */
	public void setClaimamount(double claimamount) {
		this.claimamount = claimamount;
	}

	public String getDisclaimamount() {
		return TracingConstants.DECIMALFORMAT.format(getClaimamount());
	}

	/**
	 * @return Returns the ssn.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn
	 *          The ssn to set.
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return Returns the dlstate.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getDlstate() {
		return dlstate;
	}

	/**
	 * @param dlstate
	 *          The dlstate to set.
	 */
	public void setDlstate(String dlstate) {
		this.dlstate = dlstate;
	}

	/**
	 * @return Returns the driverslicense.
	 * 
	 * @hibernate.property type="string" length="10"
	 */
	public String getDriverslicense() {
		return driverslicense;
	}

	/**
	 * @param driverslicense
	 *          The driverslicense to set.
	 */
	public void setDriverslicense(String driverslicense) {
		this.driverslicense = driverslicense;
	}

	/**
	 * @return Returns the commonnum.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getCommonnum() {
		return commonnum;
	}

	/**
	 * @param commonnum
	 *          The commonnum to set.
	 */
	public void setCommonnum(String commonnum) {
		this.commonnum = commonnum;
	}

	/**
	 * @return Returns the countryofissue.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getCountryofissue() {
		return countryofissue;
	}

	/**
	 * @param countryofissue
	 *          The countryofissue to set.
	 */
	public void setCountryofissue(String countryofissue) {
		this.countryofissue = countryofissue;
	}
	
	
	/**
	 * @return Returns the claim_ID.
	 * 
	 * @hibernate.property type="long"
	 */
	public long getClaim_ID() {
		return Claim_ID;
	}

	/**
	 * @param claim_ID
	 *          The claim_ID to set.
	 */
	public void setClaim_ID(long claim_ID) {
		Claim_ID = claim_ID;
	}

	/**
	 * @return Returns the claimcurrency_ID.
	 * @hibernate.property type="string" column="currency_ID"
	 */
	public String getClaimcurrency_ID() {
		return claimcurrency_ID;
	}

	/**
	 * @param claimcurrency_ID
	 *          The claimcurrency_ID to set.
	 */
	public void setClaimcurrency_ID(String claimcurrency_ID) {
		this.claimcurrency_ID = claimcurrency_ID;
	}

	/**
	 * @return Returns the incident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Incident"
	 *                        column="incident_ID"
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
}