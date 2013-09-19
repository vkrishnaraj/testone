package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.utils.DateUtils;
/**
 * @author Sean Fine
 * Class containing information for the Depreciation Summary Report
 */
public class DeprecSumDTO {
	
	private String _DATEFORMAT; // current login agent's date format
	private TimeZone _TIMEZONE;
	
	private String claimID;
	private String dateCalculate;
	private String claimType;
	private String totalWeight;
	private String weightMetric;
	private String claimDeprecID;
	private String itemDesc;
	private double amountClaimed;
	private String datePurchase;
	private String category;
	private String receiptProvided;
	private String notCoveredCoc;
	private String calculatedValue;
	private double claimValue;
	private String totalApprovedPayout;
	
	public String getClaimID() {
		return claimID;
	}
	
	public void setClaimID(String claimID) {
		this.claimID = claimID;
	}
	
	public String getClaimDeprecID() {
		return claimDeprecID;
	}
	
	public void setClaimDeprecID(String claimDeprecID) {
		this.claimDeprecID = claimDeprecID;
	}
	
	public String getItemDesc() {
		return itemDesc;
	}
	
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	public double getAmountClaimed() {
		return amountClaimed;
	}
	
	public void setAmountClaimed(double amountClaimed) {
		this.amountClaimed = amountClaimed;
	}
	
	public String getDatePurchase() {
		return datePurchase;
	}
	
	public void setDatePurchase(String datePurchase) {
		this.datePurchase = datePurchase;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getReceiptProvided() {
		return receiptProvided;
	}
	
	public void setReceiptProvided(String receiptProvided) {
		this.receiptProvided = receiptProvided;
	}
	
	public String getNotCoveredCoc() {
		return notCoveredCoc;
	}
	
	public void setNotCoveredCoc(String notCoveredCoc) {
		this.notCoveredCoc = notCoveredCoc;
	}
	
	public String getCalculatedValue() {
		return calculatedValue;
	}
	
	public void setCalculatedValue(String calculatedValue) {
		this.calculatedValue = calculatedValue;
	}

	public double getClaimValue() {
		return claimValue;
	}
	
	public void setClaimValue(double claimValue) {
		this.claimValue = claimValue;
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

	public String getWeightMetric() {
		return weightMetric;
	}

	public void setWeightMetric(String weightMetric) {
		this.weightMetric = weightMetric;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public String getDateCalculate() {
		return dateCalculate;
	}

	public void setDateCalculate(String dateCalculate) {
		this.dateCalculate = dateCalculate;
	}

	public String getTotalApprovedPayout() {
		return totalApprovedPayout;
	}

	public void setTotalApprovedPayout(String totalApprovedPayout) {
		this.totalApprovedPayout = totalApprovedPayout;
	}

	
}