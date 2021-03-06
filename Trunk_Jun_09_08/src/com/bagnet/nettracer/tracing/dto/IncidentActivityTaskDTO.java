package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class IncidentActivityTaskDTO {
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;
	private Locale agentLocale;
	
	private long id;
	private long incidentActivityId;
	private long taskid;
	private String incidentId;
	private int expenseId;
	private String description;
	private String agent;
	private String taskType;
	private String status;
	private Date taskDate;
	private String lastPrinted;
	

	/**
	 * Passenger Information
	 */
	private String lastName;
	private String firstName;
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
	private String explastName="";
	private String expfirstName="";
	private String expaddress;
	private String expaptnum;
	private String expcity;
	private String expstate;
	private String expzip;
	
	private String acaa;
	
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

	public Locale getAgentLocale() {
		return agentLocale;
	}

	public void setAgentLocale(Locale agentLocale) {
		this.agentLocale = agentLocale;
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

	public String getTaskType() {
		return taskType != null ? taskType : "&nbsp;";
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getStatus() {
		return status != null ? status : "";
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

	public String getLastName() {
		return lastName != null ? lastName : "";
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName != null ? firstName : "";
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public String getAcaa() {
		return acaa;
	}

	public void setAcaa(String acaa) {
		this.acaa = acaa;
	}

	public String getExplastName() {
		return explastName;
	}

	public void setExplastName(String explastName) {
		this.explastName = explastName;
	}

	public String getExpfirstName() {
		return expfirstName;
	}

	public void setExpfirstName(String expfirstName) {
		this.expfirstName = expfirstName;
	}

	public String getExpname() {
		StringBuilder name=new StringBuilder();
	
		if(getExpfirstName()!=null && !getExpfirstName().isEmpty()){
			name.append(getExpfirstName());
		}
		if(getExpfirstName()!=null && !getExpfirstName().isEmpty()
				&& getExplastName()!=null && !getExplastName().isEmpty()){
			name.append(" ");
			
		}
		if (getExplastName()!=null && !getExplastName().isEmpty()){
			name.append(getExplastName());
		}
		return name.toString();
		
	}

	public String getExpaddress() {
		return expaddress;
	}

	public void setExpaddress(String expaddress) {
		this.expaddress = expaddress;
	}

	public String getExpaptnum() {
		return expaptnum;
	}

	public void setExpaptnum(String expaptnum) {
		this.expaptnum = expaptnum;
	}

	public String getExpcity() {
		return expcity;
	}

	public void setExpcity(String expcity) {
		this.expcity = expcity;
	}

	public String getExpstate() {
		return expstate;
	}

	public void setExpstate(String expstate) {
		this.expstate = expstate;
	}

	public String getExpzip() {
		return expzip;
	}

	public void setExpzip(String expzip) {
		this.expzip = expzip;
	}
	
}