package aero.nettracer.serviceprovider.ws_1_0.common;

import java.util.Calendar;

public class ClaimCheck {

	private Calendar timeChecked;
	private String tagNumber;
	private String airline;
	private boolean overweight;
	private String posId;

	public Calendar getTimeChecked() {
		return timeChecked;
	}

	public void setTimeChecked(Calendar timeChecked) {
		this.timeChecked = timeChecked;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public boolean isOverweight() {
		return overweight;
	}

	public void setOverweight(boolean overweight) {
		this.overweight = overweight;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}
	
}
