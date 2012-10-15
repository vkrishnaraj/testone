package aero.nettracer.fs.model.transport.v2.detection;

import java.io.Serializable;
import java.util.Date;

public class AccessRequestDTO implements aero.nettracer.fs.model.transport.v0.detection.AccessRequestDTO, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9136707202471488464L;
	public String getAirlinecode() {
		return airlinecode;
	}
	public void setAirlinecode(String airlinecode) {
		this.airlinecode = airlinecode;
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
	String airlinecode;
	boolean pending;
	boolean approved;
	boolean denied;
	int type;
	
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
	Date startDate;
	Date endDate;
}
