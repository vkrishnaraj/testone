package aero.nettracer.serviceprovider.ws_1_0.res.sabre.dto;

import java.util.Calendar;

public class SabreParsedBags {
	private String carrier;
	private Calendar checkedTime;
	public Calendar getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(Calendar checkedTime) {
		this.checkedTime = checkedTime;
	}

	private String tag;

	public String getCarrier() {
		return carrier;
	}

	public String getTag() {
		return tag;
	}

	public String key() {
		return tag;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
