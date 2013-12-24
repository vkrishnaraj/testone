package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;

public class IncidentActivityTaskSearchDTO {
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;

	private Agent agent;
	private Status status;
	
	private String sort;
	private String dir;

	private boolean financial;
	
	private long id;
	private String incidentId;
	
	private String name;
	
	private boolean active;
	
	private Date startCreateDate;
	
	private Date endCreateDate;
	
	private int currentPage;
	
	private int rowsPerPage;
	
	private String passengerLastName;
	private String passengerFirstName;

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

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(Date startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isFinancial() {
		return financial;
	}

	public void setFinancial(boolean financial) {
		this.financial = financial;
	}

	public String getPassengerLastName() {
		return passengerLastName;
	}

	public void setPassengerLastName(String passengerLastName) {
		this.passengerLastName = passengerLastName;
	}

	public String getPassengerFirstName() {
		return passengerFirstName;
	}

	public void setPassengerFirstName(String passengerFirstName) {
		this.passengerFirstName = passengerFirstName;
	}
	
}
