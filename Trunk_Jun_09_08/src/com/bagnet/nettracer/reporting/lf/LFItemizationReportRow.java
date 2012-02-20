package com.bagnet.nettracer.reporting.lf;

public class LFItemizationReportRow {
	
	private long barcode;
	private String dateRecorded;
	private String dateReceived;
	private String value;
	private String stationcode;
	private String category;
	private String subCategory;
	private String status;
	private String disposition;
	private String title;
	private int returnTime;
	private String trackingNumber;
	private String returnedWithLostReport;
	
	public String getStationcode() {
		return stationcode;
	}
	
	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}
	
	public long getBarcode() {
		return barcode;
	}
	
	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}
	
	public String getDateRecorded() {
		return dateRecorded;
	}
	
	public void setDateRecorded(String dateRecorded) {
		this.dateRecorded = dateRecorded;
	}
	
	public String getDateReceived() {
		return dateReceived;
	}
	
	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getSubCategory() {
		return subCategory;
	}
	
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
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
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getReturnTime() {
		return returnTime;
	}
	
	public void setReturnTime(int returnTime) {
		this.returnTime = returnTime;
	}
	
	public String getTrackingNumber() {
		return trackingNumber;
	}
	
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getReturnedWithLostReport() {
		return returnedWithLostReport;
	}
	
	public void setReturnedWithLostReport(String returnedWithLostReport) {
		this.returnedWithLostReport = returnedWithLostReport;
	}

}
