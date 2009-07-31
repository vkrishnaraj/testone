package aero.nettracer.serviceprovider.ws_1_0.common;

import java.util.Date;

public class Itinerary {
	private String departureCity;
	private String arrivalCity;
	private String airline;
	private String flightnum;

	// These will be used to populate fields (local time).
	// The will be used to determine if a field can be
	// auto-populated if gmtCounterparts are not available.
	private Date schdeparttime;
	private Date scharrivetime;
	private Date actdeparttime;
	private Date actarrivetime;

	// These will be used if available to determine
	// whether a value is subject to time constraints.
	private Date schdeparttimeGmt;
	private Date scharrivetimeGmt;
	private Date actdeparttimeGmt;
	private Date actarrivetimeGmt;

	// Also known as an ARNK (pronounced arunk)
	private boolean arrivalUnknown;

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getFlightnum() {
		return flightnum;
	}

	public void setFlightnum(String flightnum) {
		this.flightnum = flightnum;
	}

	public Date getSchdeparttime() {
		return schdeparttime;
	}

	public void setSchdeparttime(Date schdeparttime) {
		this.schdeparttime = schdeparttime;
	}

	public Date getScharrivetime() {
		return scharrivetime;
	}

	public void setScharrivetime(Date scharrivetime) {
		this.scharrivetime = scharrivetime;
	}

	public Date getActdeparttime() {
		return actdeparttime;
	}

	public void setActdeparttime(Date actdeparttime) {
		this.actdeparttime = actdeparttime;
	}

	public Date getActarrivetime() {
		return actarrivetime;
	}

	public void setActarrivetime(Date actarrivetime) {
		this.actarrivetime = actarrivetime;
	}

	public boolean isArrivalUnknown() {
		return arrivalUnknown;
	}

	public void setArrivalUnknown(boolean arrivalUnknown) {
		this.arrivalUnknown = arrivalUnknown;
	}

	public Date getSchdeparttimeGmt() {
		return schdeparttimeGmt;
	}

	public void setSchdeparttimeGmt(Date schdeparttimeGmt) {
		this.schdeparttimeGmt = schdeparttimeGmt;
	}

	public Date getScharrivetimeGmt() {
		return scharrivetimeGmt;
	}

	public void setScharrivetimeGmt(Date scharrivetimeGmt) {
		this.scharrivetimeGmt = scharrivetimeGmt;
	}

	public Date getActdeparttimeGmt() {
		return actdeparttimeGmt;
	}

	public void setActdeparttimeGmt(Date actdeparttimeGmt) {
		this.actdeparttimeGmt = actdeparttimeGmt;
	}

	public Date getActarrivetimeGmt() {
		return actarrivetimeGmt;
	}

	public void setActarrivetimeGmt(Date actarrivetimeGmt) {
		this.actarrivetimeGmt = actarrivetimeGmt;
	}

}
