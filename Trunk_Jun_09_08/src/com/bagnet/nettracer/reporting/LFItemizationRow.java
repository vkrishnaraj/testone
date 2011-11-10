package com.bagnet.nettracer.reporting;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

public class LFItemizationRow {
	
	private long id;
	private String station;
	private String date;
	private long status;
	private long disposition;
	private String category;
	private String subCategory;
	private String brand;
	private String description;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getStation() {
		return station;
	}
	
	public void setStation(String station) {
		this.station = station;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public long getStatus() {
		return status;
	}
	
	public void setStatus(long status) {
		this.status = status;
	}
	
	public long getDisposition() {
		return disposition;
	}
	
	public void setDisposition(long disposition) {
		this.disposition = disposition;
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
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDisStatus() {
		if (this.status == TracingConstants.LF_STATUS_OPEN) {
			return "Open";
		}
		return "Closed";
	}
	
	public String getItemReturned() {
		if (this.disposition == TracingConstants.LF_DISPOSITION_DELIVERED 
				|| this.disposition == TracingConstants.LF_DISPOSITION_PICKED_UP) {
			return "Yes";
		}
		return "No";
	}

}
