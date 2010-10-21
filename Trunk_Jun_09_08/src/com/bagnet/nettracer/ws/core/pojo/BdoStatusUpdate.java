package com.bagnet.nettracer.ws.core.pojo;

public class BdoStatusUpdate {
	private int bdo;
	private String remarks;
	private String status;
	
	public int getBdo() {
		return bdo;
	}
	public void setBdo(int bdo) {
		this.bdo = bdo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
