package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.utils.DateUtils;

public class IncidentActivityRemarkDTO {
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;
	
	private String agent;
	private Date createDate;
	private String remarkText;
	
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

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemarkText() {
		return remarkText;
	}

	public void setRemarkText(String remarkText) {
		this.remarkText = remarkText;
	}

	public String getDispCreateDate() {
		return DateUtils.formatDate(getCreateDate(), get_DATEFORMAT() + " " + get_TIMEFORMAT(), null, get_TIMEZONE());
	}

}
