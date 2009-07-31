package aero.nettracer.serviceprovider.ws_1_0.common;

import java.util.Calendar;

public class ClaimCheck {

	private Calendar timeChecked;
	private String tagNumber;
	private String airline;

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
}
