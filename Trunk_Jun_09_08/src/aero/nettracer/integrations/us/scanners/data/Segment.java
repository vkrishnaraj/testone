package aero.nettracer.integrations.us.scanners.data;

import java.io.Serializable;
import java.util.Calendar;

public class Segment implements Serializable {
	private static final long serialVersionUID = 6619668123207771032L;
	private String from;
	private String to;
	private String carrier;
	private int flightNumber;
	private Calendar flightDate;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public int getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Calendar getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}
}
