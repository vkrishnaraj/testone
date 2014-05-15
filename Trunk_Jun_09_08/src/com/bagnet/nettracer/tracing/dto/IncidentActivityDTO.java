package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.db.Status;
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
	private Status status;
	private String fileName;
	private long custCommId;
	private boolean isCustomerCommunication;
	private boolean isCorrespondence;
	private boolean toBeAcknowledged;
	private boolean published;

	private String activityCode;
	
	private List<MessageDTO> messages;
	private List<FileDTO> files;
	
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
		return (this.status == null) ? 0 : this.status.getStatus_ID();
	}

	public Status getStatus() {
		return this.status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
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

	public boolean getIsCorrespondence() {
		return isCorrespondence;
	}

	public void setCorrespondence(boolean isCorrespondence) {
		this.isCorrespondence = isCorrespondence;
	}

	public List<MessageDTO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageDTO> messages) {
		this.messages = messages;
	}

	public List<FileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<FileDTO> files) {
		this.files = files;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isToBeAcknowledged() {
		return toBeAcknowledged;
	}

	public void setToBeAcknowledged(boolean toBeAcknowledged) {
		this.toBeAcknowledged = toBeAcknowledged;
	}
	
}
