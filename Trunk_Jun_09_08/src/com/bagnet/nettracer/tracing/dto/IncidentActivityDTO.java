package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.utils.DateUtils;

public class IncidentActivityDTO {

	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;
	private long id;
	private Date createDate;
	private Date publishedDate;
	private String agent;
	private String description;
	private int statusId;
	private String fileName;
	private long custCommId;
	private boolean isCustomerCommunication;
	
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

	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getAgent() {
		return agent;
	}
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getCustCommId() {
		return custCommId;
	}

	public void setCustCommId(long custCommId) {
		this.custCommId = custCommId;
	}

	public String getDispCreateDate() {
		return DateUtils.formatDate(getCreateDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	public String getDispPublishedDate() {
		return DateUtils.formatDate(getPublishedDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	public boolean getIsCustomerCommunication() {
		return isCustomerCommunication;
	}

	public void setCustomerCommunication(boolean isCustomerCommunication) {
		this.isCustomerCommunication = isCustomerCommunication;
	}
	
}
