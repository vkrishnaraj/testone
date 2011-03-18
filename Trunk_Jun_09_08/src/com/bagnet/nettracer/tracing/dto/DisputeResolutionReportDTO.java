package com.bagnet.nettracer.tracing.dto;

import java.sql.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;

import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.db.Status;

public class DisputeResolutionReportDTO {
	
	public Date getDate_created() {
		return date_created;
	}
	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}
	public String getIncident_id() {
		return incident_id;
	}
	public void setIncident_id(String incident_id) {
		this.incident_id = incident_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getTypeOfChange() {
		return typeOfChange;
	}
	public void setTypeOfChange(String typeOfChange) {
		this.typeOfChange = typeOfChange;
	}
	public int getPreviousFaultCode() {
		return previousFaultCode;
	}
	public void setPreviousFaultCode(int previousFaultCode) {
		this.previousFaultCode = previousFaultCode;
	}
//	public int getPreviousFaultStation() {
//		return previousFaultStation;
//	}
//	public void setPreviousFaultStation(int previousFaultStation) {
//		this.previousFaultStation = previousFaultStation;
//	}
	public int getSuggestedFaultCode() {
		return suggestedFaultCode;
	}
	public void setSuggestedFaultCode(int suggestedFaultCode) {
		this.suggestedFaultCode = suggestedFaultCode;
	}
	public String getSuggestedFaultStation() {
		return suggestedFaultStation;
	}
	public void setSuggestedFaultStation(String suggestedFaultStation) {
		this.suggestedFaultStation = suggestedFaultStation;
	}
	public int getNewFaultCode() {
		return newFaultCode;
	}
	public void setNewFaultCode(int newFaultCode) {
		this.newFaultCode = newFaultCode;
	}
	public String getNewFaultStation() {
		return newFaultStation;
	}
	public void setNewFaultStation(String newFaultStation) {
		this.newFaultStation = newFaultStation;
	}
	
	
	
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}

	public String getReportDateCreated() {
		java.util.Date tempdate = DateUtils.convertToDate( DateUtils.formatDate(getDate_created(), TracingConstants.DB_DATEFORMAT, null, null) + " "
				+ DateUtils.formatDate(getDate_created(), TracingConstants.DB_TIMEFORMAT, null, null),TracingConstants.DB_DATETIMEFORMAT,null);
		
		return DateUtils.formatDate(tempdate, _DATEFORMAT, null, _TIMEZONE);

	}
	
	public Date getDate_resolved() {
		return date_resolved;
	}
	public void setDate_resolved(Date date_resolved) {
		this.date_resolved = date_resolved;
	}
	public String getReportDateResolved() {
		java.util.Date tempdate = DateUtils.convertToDate( DateUtils.formatDate(getDate_resolved(), TracingConstants.DB_DATEFORMAT, null, null) + " "
				+ DateUtils.formatDate(getDate_resolved(), TracingConstants.DB_TIMEFORMAT, null, null),TracingConstants.DB_DATETIMEFORMAT,null);
		return DateUtils.formatDate(tempdate, _DATEFORMAT, null, _TIMEZONE);
	}
	public void setReportDateResolved(String reportDateResolved) {
		this.reportDateResolved = reportDateResolved;
	}

//	public String getStatusDesc() {
//		String result = "" + TracerUtils.getText(Status.getKey((Integer) status), "");
//		return result;
//	}

	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
	public String getDisputeAgentName() {
		return disputeAgentName;
	}
	public void setDisputeAgentName(String disputeAgentName) {
		this.disputeAgentName = disputeAgentName;
	}

	public String getWorkingAgentName() {
		return workingAgentName;
	}
	public void setWorkingAgentName(String workingAgentName) {
		this.workingAgentName = workingAgentName;
	}

	public String getBeforeDisputeFaultStation() {
		return beforeDisputeFaultStation;
	}
	public void setBeforeDisputeFaultStation(String beforeDisputeFaultStation) {
		this.beforeDisputeFaultStation = beforeDisputeFaultStation;
	}


	private Date date_created;


	private Date date_incident_created;
	public Date getDate_incident_created() {
		return date_incident_created;
	}
	public void setDate_incident_created(Date date_incident_created) {
		this.date_incident_created = date_incident_created;
	}

	public String getReportIncidentDateCreate() {
		java.util.Date tempdate = DateUtils.convertToDate( DateUtils.formatDate(getDate_incident_created(), TracingConstants.DB_DATEFORMAT, null, null) + " "
				+ DateUtils.formatDate(getDate_incident_created(), TracingConstants.DB_TIMEFORMAT, null, null),TracingConstants.DB_DATETIMEFORMAT,null);
		return DateUtils.formatDate(tempdate, _DATEFORMAT, null, _TIMEZONE);
	}
	
	private String reportIncidentDateCreate;
	
	private Date date_resolved;
	private String reportDateCreated;
	private String reportDateResolved;
	private String incident_id;
	private int status;
	private String statusDesc;
	private String disputeAgentName;
//	private int workingAgent;
	private String workingAgentName;
	private String typeOfChange;
	
	private int previousFaultCode;
	private String beforeDisputeFaultStation;
//	private int previousFaultStation;
	
	private int suggestedFaultCode;
//	private int suggestedFaultStation;
	private String suggestedFaultStation;
	
	private int newFaultCode;
//	private int newFaultStation;
	private String newFaultStation;
	
	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;

}