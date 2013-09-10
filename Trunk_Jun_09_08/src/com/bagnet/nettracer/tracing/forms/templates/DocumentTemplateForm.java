package com.bagnet.nettracer.tracing.forms.templates;

import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.templates.DocumentTemplateVar;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class DocumentTemplateForm extends ValidatorForm {

	private static final long serialVersionUID = 2248013898627441965L;
	
	private String command;
	
	private long id;
	
	private Date createDate;
	
	private Date lastUpdated;
	
	private String name;
	
	private String description;
	
	private boolean active;
	
	private String data;
	
	private String _DATEFORMAT;
	
	private String _TIMEFORMAT;
	
	private TimeZone _TIMEZONE;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
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

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

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

	public String getDisCreateDate() {
		return DateUtils.formatDate(getCreateDate(), get_DATEFORMAT() + " " + get_TIMEFORMAT(), null, get_TIMEZONE());
	}

	public String getDisLastUpdatedDate() {
		return DateUtils.formatDate(getLastUpdated(), get_DATEFORMAT() + " " + get_TIMEFORMAT(), null, get_TIMEZONE());
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.active = false;
	}
}
