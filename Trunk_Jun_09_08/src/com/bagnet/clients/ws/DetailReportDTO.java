package com.bagnet.clients.ws;

import java.util.Date;

public class DetailReportDTO {
	private String flightDate;
	private String cityPair;
	private String faultStation;
	private Integer lossCode;
	private String flightNum;
	private String bagTag;
	private String recordLocator;
	private String reasonForLoss;
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public String getCityPair() {
		return cityPair;
	}
	public void setCityPair(String cityPair) {
		this.cityPair = cityPair;
	}
	public String getFaultStation() {
		return faultStation;
	}
	public void setFaultStation(String faultStation) {
		this.faultStation = faultStation;
	}
	public Integer getLossCode() {
		return lossCode;
	}
	public void setLossCode(Integer lossCode) {
		this.lossCode = lossCode;
	}
	public String getFlightNum() {
		return flightNum;
	}
	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	public String getBagTag() {
		return bagTag;
	}
	public void setBagTag(String bagTag) {
		this.bagTag = bagTag;
	}
	public String getRecordLocator() {
		return recordLocator;
	}
	public void setRecordLocator(String recordLocator) {
		this.recordLocator = recordLocator;
	}
	public String getReasonForLoss() {
		return reasonForLoss;
	}
	public void setReasonForLoss(String reasonForLoss) {
		this.reasonForLoss = reasonForLoss;
	}
	
	
}
