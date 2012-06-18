package com.bagnet.nettracer.tracing.dto;

import java.sql.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;

import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.db.Status;

public class FraudValuationReportDTO {
	
	private String _DATEFORMAT; // current login agent's date format
	private TimeZone _TIMEZONE;
	
	private Date claimDate;
	private String claimID;
	private String incidentID;
	private String amountClaimed;
	private String amountClaimedCurrency;
	private String amountPaid;
	private String amountPaidCurrency;
	private String status;
	private int matches;
	
	public Date getClaimDate() {
		return claimDate;
	}
	
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public String getReportClaimDate() {
		java.util.Date tempdate = DateUtils.convertToDate( DateUtils.formatDate(getClaimDate(), TracingConstants.DB_DATEFORMAT, null, null) + " "
				+ DateUtils.formatDate(getClaimDate(), TracingConstants.DB_TIMEFORMAT, null, null),TracingConstants.DB_DATETIMEFORMAT,null);
		
		return DateUtils.formatDate(tempdate, _DATEFORMAT, null, _TIMEZONE);

	}
	
	public String getClaimID() {
		return claimID;
	}
	
	public void setClaimID(String claimID) {
		this.claimID = claimID;
	}
	
	public String getIncidentID() {
		return incidentID;
	}
	
	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}
	
	public String getAmountClaimed() {
		return amountClaimed;
	}
	
	public void setAmountClaimed(String amountClaimed) {
		this.amountClaimed = amountClaimed;
	}
	
	public String getAmountClaimedCurrency() {
		return amountClaimedCurrency;
	}
	
	public void setAmountClaimedCurrency(String amountClaimedCurrency) {
		this.amountClaimedCurrency = amountClaimedCurrency;
	}
	
	public String getAmountPaid() {
		return amountPaid;
	}
	
	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}
	
	public String getAmountPaidCurrency() {
		return amountPaidCurrency;
	}
	
	public void setAmountPaidCurrency(String amountPaidCurrency) {
		this.amountPaidCurrency = amountPaidCurrency;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getMatches() {
		return matches;
	}
	
	public void setMatches(int matches) {
		this.matches = matches;
	}
	
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}
	
	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}
	
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}
	
	public void set_TIMEZONE(TimeZone _TIMEZONE) {
		this._TIMEZONE = _TIMEZONE;
	}

}