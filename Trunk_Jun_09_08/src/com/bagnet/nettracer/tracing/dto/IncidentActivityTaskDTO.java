package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.utils.DateUtils;

public class IncidentActivityTaskDTO {
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;
	
	private long taskId;
	private long incidentActivityId;
	private String incidentId;
	private String description;
	private String agent;
	private Date taskDate;
	
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}

	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	public void set_TIMEFORMAT(String _TIMEFORMAT) {
		this._TIMEFORMAT = _TIMEFORMAT;
	}

	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	public void set_TIMEZONE(TimeZone _TIMEZONE) {
		this._TIMEZONE = _TIMEZONE;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public long getIncidentActivityId() {
		return incidentActivityId;
	}

	public void setIncidentActivityId(long incidentActivityId) {
		this.incidentActivityId = incidentActivityId;
	}

	public String getIncidentId() {
		return incidentId;
	}
	
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAgent() {
		return agent != null ? agent : "";
	}
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}
	
	public String getDispTaskDate() {
		return DateUtils.formatDate(getTaskDate(), get_DATEFORMAT() + " " + get_TIMEFORMAT(), null, get_TIMEZONE());
	}

}
