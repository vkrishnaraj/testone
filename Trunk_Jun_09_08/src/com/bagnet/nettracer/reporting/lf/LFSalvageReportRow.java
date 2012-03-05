package com.bagnet.nettracer.reporting.lf;

public class LFSalvageReportRow {

	private long barcode;
	private String dateReceived;
	private String value;
	private String category;
	private String subCategory;
	private String title;
	
	public long getBarcode() {
		return barcode;
	}
	
	public void setBarcode(long barcode) {
		this.barcode = barcode;
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
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
}
