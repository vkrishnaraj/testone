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
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Audit_work_shift"
 */
public class Audit_Work_Shift implements Serializable {

	private int audit_shift_id;
	private int shift_id;
	private String shift_code;
	private String shift_description;
	private Company company;
	private String locale;

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
	 * @return Returns the shift_code.
	 */
	public String getShift_code() {
		return shift_code;
	}

	/**
	 * @param shift_code
	 *          The shift_code to set.
	 */
	public void setShift_code(String shift_code) {
		this.shift_code = shift_code;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Company"
	 *                        column="companycode_ID"
	 * @return Returns the company
	 */
	public Company getCompany() {
		return company;
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
	 * @return Returns the shift_description.
	 */
	public String getShift_description() {
		return shift_description;
	}

	/**
	 * @param shift_description
	 *          The shift_description to set.
	 */
	public void setShift_description(String shift_description) {
		this.shift_description = shift_description;
	}

	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the shift_id.
	 */
	public int getShift_id() {
		return shift_id;
	}

	/**
	 * @param shift_id
	 *          The shift_id to set.
	 */
	public void setShift_id(int shift_id) {
		this.shift_id = shift_id;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="audit_shift_id" unsaved-value="0"
	 *  *
	 * @hibernate.generator-param name="sequence" value="audit_work_shift_0"
	 * 
	 * 
	 * 
	 * @return Returns the audit_shift_id.
	 */
	public int getAudit_shift_id() {
		return audit_shift_id;
	}

	/**
	 * @param audit_shift_id
	 *          The audit_shift_id to set.
	 */
	public void setAudit_shift_id(int audit_shift_id) {
		this.audit_shift_id = audit_shift_id;
	}

	/**
	 * @param company
	 *          The company to set.
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
}