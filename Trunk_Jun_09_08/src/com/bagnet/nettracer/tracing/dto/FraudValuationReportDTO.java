package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.utils.DateUtils;

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
		return DateUtils.formatDate(getClaimDate(), _DATEFORMAT, null, _TIMEZONE);

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