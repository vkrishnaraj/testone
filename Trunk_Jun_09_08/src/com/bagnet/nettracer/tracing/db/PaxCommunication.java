package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.i8n.LocaleBasedObject;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

@Entity
@Table(name = "pax_communication")
public class PaxCommunication extends LocaleBasedObject implements Serializable {

	public static enum PaxCommunicationStatus {
		ACKNOWLEDGED, NEW, READ, RESPONDED
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String _DATEFORMAT; // current login agent's date format

	private String _TIMEFORMAT; // current login agent's time format

	private TimeZone _TIMEZONE;
	// many to 1
	private Agent acknowledge_agent;

	private Date acknowledge_timestamp;

	private Agent agent;
	// 1500, not null
	private String comment;
	private Date createdate;
	private String dispAcknowledgedDate;
	private String dispDate;
	private String dispStatus;
	private int id;
	private Incident incident;

	private PaxCommunicationStatus status = PaxCommunicationStatus.NEW;

	public PaxCommunication() {
	}

	@Transient
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	@Transient
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	@Transient
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.Agent.class)
	@JoinColumn(name = "acknowledge_agent_Agent_ID", nullable = true)
	public Agent getAcknowledge_agent() {
		return acknowledge_agent;
	}

	public Date getAcknowledge_timestamp() {
		return acknowledge_timestamp;
	}

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.Agent.class)
	@JoinColumn(name = "agent_Agent_ID", nullable = true)
	public Agent getAgent() {
		return agent;
	}

	@Column(nullable = false, length = 1500)
	public String getComment() {
		return comment;
	}

	public Date getCreatedate() {
		return createdate;
	}

	@Transient
	public String getDispAcknowledgedDate() {
		// need more work to support multiple language
		// refer to OHD_CategoryType for example on custom solution
		return DateUtils.formatDate(acknowledge_timestamp, _DATEFORMAT + " "
				+ _TIMEFORMAT, null, _TIMEZONE);
	}

	@Transient
	public String getDispDate() {
		// need more work to support multiple language
		// refer to OHD_CategoryType for example on custom solution
		return DateUtils.formatDate(createdate,
				_DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	@Transient
	public String getDispStatus() {
		return "PAX_MSG_" + status.name();
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.Incident.class)
	@JoinColumn(name = "incident_Incident_ID", nullable = false)
	public Incident getIncident() {
		return incident;
	}

	@Override
	@Transient
	public String getKey() {
		return getDispStatus();
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "status", length = 12)
	public PaxCommunicationStatus getStatus() {
		return status;
	}

	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}

	public void setAcknowledge_agent(Agent acknowledge_agent) {
		this.acknowledge_agent = acknowledge_agent;
	}

	public void setAcknowledge_timestamp(Date acknowledge_timestamp) {
		this.acknowledge_timestamp = acknowledge_timestamp;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public void setStatus(PaxCommunicationStatus status) {
		this.status = status;
	}

	public String toString() {
		String result;
		StringBuffer mySb = new StringBuffer();
		mySb.append("id=" + id);
		mySb.append("comment=" + comment);
		mySb.append("incident_id=" + incident.getIncident_ID());
		result = "" + mySb.toString();
		return result;
	}
	
	@Transient
	public boolean isHighPriority() {
		
		if (agent != null || this.status != PaxCommunicationStatus.NEW) {
			return false;
		}
		
		int hoursBack = 0;
		Date ageDate = TracerDateTime.getGMTDate();
		
		try {
			hoursBack = PropertyBMO.getValueAsInt(PropertyBMO.HIGH_PRIORITY_PAX_COMMUNICATION_HOURS);
			ageDate.setTime(ageDate.getTime() - (hoursBack * 60 * 60 * 1000));
		} catch (Exception e) {
		
		}
		
		if (hoursBack != 0 && this.createdate.getTime() <= ageDate.getTime()) {
			return true;
		}
		return false;
		
	}

}
