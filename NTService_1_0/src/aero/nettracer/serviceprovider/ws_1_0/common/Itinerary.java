package aero.nettracer.serviceprovider.ws_1_0.common;

import java.util.Calendar;
import java.util.Date;

public class Itinerary {
	private String departureCity;
	private String arrivalCity;
	private String airline;
	private String flightnum;
	

	// These will be used to populate fields (local time).
	// The will be used to determine if a field can be
	// auto-populated if gmtCounterparts are not available.
	private Calendar schdeparttime;
	private Calendar scharrivetime;
	private Calendar actdeparttime;
	private Calendar actarrivetime;

	// These will be used if available to determine
	// whether a value is subject to time constraints.
	private Date schdeparttimeGmt;
	private Date scharrivetimeGmt;
	private Date actdeparttimeGmt;
	private Date actarrivetimeGmt;
	private Calendar timeChecked;

	public Calendar getTimeChecked() {
		return timeChecked;
	}

	public void setTimeChecked(Calendar timeChecked) {
		this.timeChecked = timeChecked;
	}

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

	public Calendar getSchdeparttime() {
		return schdeparttime;
	}

	public void setSchdeparttime(Calendar schdeparttime) {
		this.schdeparttime = schdeparttime;
	}

	public Calendar getScharrivetime() {
		return scharrivetime;
	}

	public void setScharrivetime(Calendar scharrivetime) {
		this.scharrivetime = scharrivetime;
	}

	public Calendar getActdeparttime() {
		return actdeparttime;
	}

	public void setActdeparttime(Calendar actdeparttime) {
		this.actdeparttime = actdeparttime;
	}

	public Calendar getActarrivetime() {
		return actarrivetime;
	}

	public void setActarrivetime(Calendar actarrivetime) {
		this.actarrivetime = actarrivetime;
	}

}
