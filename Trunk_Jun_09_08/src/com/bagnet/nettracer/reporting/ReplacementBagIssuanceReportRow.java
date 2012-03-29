package com.bagnet.nettracer.reporting;

public class ReplacementBagIssuanceReportRow {
	
	private String createDate;
	private String stationCode;
	private String incidentId;
	private int numBagsAffected;
	private int numBagsIssued;
	private String levelOfDamage;
	
	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getStationCode() {
		return stationCode;
	}
	
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	
	public String getIncidentId() {
		return incidentId;
	}
	
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	
	public int getNumBagsAffected() {
		return numBagsAffected;
	}
	
	public void setNumBagsAffected(int numBagsAffected) {
		this.numBagsAffected = numBagsAffected;
	}
	
	public int getNumBagsIssued() {
		return numBagsIssued;
	}
	
	public void setNumBagsIssued(int numBagsIssued) {
		this.numBagsIssued = numBagsIssued;
	}
	
	public String getLevelOfDamage() {
		return levelOfDamage;
	}
	
	public void setLevelOfDamage(String levelOfDamage) {
		this.levelOfDamage = levelOfDamage;
	}

}
