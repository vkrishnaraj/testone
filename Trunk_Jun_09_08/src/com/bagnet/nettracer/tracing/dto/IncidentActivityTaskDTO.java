package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class IncidentActivityTaskDTO {
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;
	
	private long id;
	private long incidentActivityId;
	private long taskid;
	private String incidentId;
	private int expenseId;
	private String description;
	private String agent;
	private String status;
	private Date taskDate;
	private String lastPrinted;
	

	/**
	 * Passenger Information
	 */
	private String name;
	private String address;
	private String aptnum;
	private String city;
	private String state;
	private String zip;
	
	private String airline;
	private String pnr;
	private String reason;
	private String specialist;
	private String approver;
	
	/**
	 * Expense Information
	 */
	private String expensedraft;
	private String expensedraftdate;
	private String expensemaildate;
	private double expensecheckamt;
	private double expensevoucheramt;
	
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

	public int getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpensedraft() {
		return expensedraft;
	}

	public void setExpensedraft(String expensedraft) {
		this.expensedraft = expensedraft;
	}

	public String getExpensedraftdate() {
		return expensedraftdate;
	}

	public void setExpensedraftdate(String expensedraftdate) {
		this.expensedraftdate = expensedraftdate;
	}

	public String getExpensemaildate() {
		return expensemaildate;
	}

	public void setExpensemaildate(String expensemaildate) {
		this.expensemaildate = expensemaildate;
	}

	public double getExpensecheckamt() {
		return expensecheckamt;
	}

	public void setExpensecheckamt(double expensecheckamt) {
		this.expensecheckamt = expensecheckamt;
	}

	public String getDispExpensecheckamt() {
		return TracingConstants.DECIMALFORMAT.format(expensecheckamt);
	}

	public double getExpensevoucheramt() {
		return expensevoucheramt;
	}

	public void setExpensevoucheramt(double expensevoucheramt) {
		this.expensevoucheramt = expensevoucheramt;
	}

	public String getDispExpensevoucheramt() {
		return TracingConstants.DECIMALFORMAT.format(expensevoucheramt);
	}

	public long getTaskid() {
		return taskid;
	}

	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}

	public String getLastPrinted() {
		return lastPrinted;
	}

	public void setLastPrinted(String lastPrinted) {
		this.lastPrinted = lastPrinted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAptnum() {
		return aptnum;
	}

	public void setAptnum(String aptnum) {
		this.aptnum = aptnum;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getSpecialist() {
		return specialist;
	}

	public void setSpecialist(String specialist) {
		this.specialist = specialist;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

}
