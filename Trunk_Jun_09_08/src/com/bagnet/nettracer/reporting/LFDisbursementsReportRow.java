package com.bagnet.nettracer.reporting;


public class LFDisbursementsReportRow {
	
	private String date;
	private double checkAmount;
	private int checkNumber;
	private long lostId;
	private long barcode;
	private String trackingNumber;
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public double getCheckAmount() {
		return checkAmount;
	}
	
	public void setCheckAmount(double checkAmount) {
		this.checkAmount = checkAmount;
	}
	
	public int getCheckNumber() {
		return checkNumber;
	}
	
	public void setCheckNumber(int checkNumber) {
		this.checkNumber = checkNumber;
	}
	
	public long getLostId() {
		return lostId;
	}
	
	public void setLostId(long lostId) {
		this.lostId = lostId;
	}
	
	public long getBarcode() {
		return barcode;
	}
	
	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}
	
	public String getTrackingNumber() {
		return trackingNumber;
	}
	
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

}
