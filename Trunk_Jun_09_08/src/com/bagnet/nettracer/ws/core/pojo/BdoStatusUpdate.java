package com.bagnet.nettracer.ws.core.pojo;

import java.util.Calendar;

public class BdoStatusUpdate {
	private int bdo;
	private String remarks;
	private String status;
	private Calendar statusDateTime;

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

	public Calendar getStatusDateTime() {
		return statusDateTime;
	}

	public void setStatusDateTime(Calendar statusDateTime) {
		this.statusDateTime = statusDateTime;
	}
}
