package aero.nettracer.selfservice.claims;

import java.util.Calendar;

public class Segments {
	private String from;
	private String to;
	private String airline;
	private String flightNumber;
	private Calendar date;
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
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
}
