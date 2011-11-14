package com.bagnet.nettracer.reporting;

public class LostFoundJasperReportRow {
	
	private long id;
	private String vantiveNumber;
	private String date;
	private String station;
	private String status;
	private String disposition;
	private String trackingNumber;
	private String dropoff;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getVantiveNumber() {
		return vantiveNumber;
	}

	public void setVantiveNumber(String vantiveNumber) {
		this.vantiveNumber = vantiveNumber;
	}

	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDisposition() {
		return disposition;
	}
	
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	
	public String getDropoff() {
		return dropoff;
	}

	public void setDropoff(String dropoff) {
		this.dropoff = dropoff;
	}
	
}
