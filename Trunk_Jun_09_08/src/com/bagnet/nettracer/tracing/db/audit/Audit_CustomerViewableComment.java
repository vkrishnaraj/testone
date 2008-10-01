package com.bagnet.nettracer.tracing.db.audit;

import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.utils.DateUtils;

@Entity
@Table(name="customer_viewable_comments")
@Proxy(lazy=false)
public class Audit_CustomerViewableComment {

	private int audit_id;
	private int id;
	private Incident incident;
	private String comment;


	private Agent modifying_agent;
	private Date time_modified;
	private String reason_modified;

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE; // timezone

	public String getDisplaytime_modified() {
		Date completedate = DateUtils.convertToDate(this.getTime_modified().toString(),
				TracingConstants.DB_DATETIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}
	
	/**
	 * @return the id
	 */
	@Basic
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *          the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	@Basic
	public String getComment() {
		return comment;
	}

	/**
	 * @param text
	 *          the text to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the incident
	 */
	@ManyToOne(targetEntity = Incident.class)
	@JoinColumn(name = "incident_id", nullable = true)
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	/**
	 * @return the modifying_agent
	 */
	public Agent getModifying_agent() {
		return modifying_agent;
	}

	/**
	 * @param modifying_agent the modifying_agent to set
	 */
	public void setModifying_agent(Agent modifying_agent) {
		this.modifying_agent = modifying_agent;
	}

	/**
	 * @return the time_modified
	 */
	public Date getTime_modified() {
		return time_modified;
	}

	/**
	 * @param time_modified the time_modified to set
	 */
	public void setTime_modified(Date time_modified) {
		this.time_modified = time_modified;
	}

	/**
	 * @return the reason_modified
	 */
	public String getReason_modified() {
		return reason_modified;
	}

	/**
	 * @param reason_modified the reason_modified to set
	 */
	public void setReason_modified(String reason_modified) {
		this.reason_modified = reason_modified;
	}

	/**
	 * @return the _DATEFORMAT
	 */
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	/**
	 * @param _dateformat the _DATEFORMAT to set
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	/**
	 * @return the _TIMEFORMAT
	 */
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	/**
	 * @param _timeformat the _TIMEFORMAT to set
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	/**
	 * @return the _TIMEZONE
	 */
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	/**
	 * @param _timezone the _TIMEZONE to set
	 */
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}

	/**
	 * @return the audit_id
	 */
	@Id @GeneratedValue
	public int getAudit_id() {
		return audit_id;
	}

	/**
	 * @param audit_id the audit_id to set
	 */
	public void setAudit_id(int audit_id) {
		this.audit_id = audit_id;
	}
}
