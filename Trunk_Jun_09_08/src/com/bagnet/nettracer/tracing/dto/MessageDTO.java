package com.bagnet.nettracer.tracing.dto;

import java.util.TimeZone;

public class MessageDTO {
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;
	
	private long id;
	private String incidentId;
	private String messageText;
	private String dispCreateDate;
	private String username;
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIncidentId() {
		return incidentId;
	}
	
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	
	public String getReadonlymessage() {
		if(messageText != null)
		{
			return messageText.replaceAll("\r\n", "<br>");
		}
		else
		{
			return "";
		}
	}

	public String getDispCreateDate() {
		return dispCreateDate;
	}

	public void setDispCreateDate(String dispCreateDate) {
		this.dispCreateDate = dispCreateDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
