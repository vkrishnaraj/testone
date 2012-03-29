package com.bagnet.nettracer.reporting;

public class RonKitIssuanceReportRow {
	
	private String createDate;
	private String stationCode;
	private String incidentId;
	private int numIssued;
	
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
	
	public int getNumIssued() {
		return numIssued;
	}
	
	public void setNumIssued(int numIssued) {
		this.numIssued = numIssued;
	}

}
