package aero.nettracer.fs.model.detection;

import java.util.Date;

public class AccessRequestDTO {
	String airlinecode;
	boolean pending;
	boolean approved;
	boolean denied;
	String message;
	int type;
	Date startDate;
	Date endDate;
	
	public String getAirlinecode() {
		return airlinecode;
	}
	public void setAirlinecode(String airlinecode) {
		this.airlinecode = airlinecode;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isPending() {
		return pending;
	}
	public void setPending(boolean pending) {
		this.pending = pending;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public boolean isDenied() {
		return denied;
	}
	public void setDenied(boolean denied) {
		this.denied = denied;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
}
