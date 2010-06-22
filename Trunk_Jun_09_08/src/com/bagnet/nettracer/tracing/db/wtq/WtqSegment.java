package com.bagnet.nettracer.tracing.db.wtq;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WtqSegment {
	
	@Column(nullable = false)
	private String legfrom;

	@Column(nullable = false)
	private String legto;
	
	@Column(nullable = false)
	private Date departdate;
	
	@Column(nullable = false)
	private Date departureTime;
	
	@Column(nullable = false)
	private Date arrivedate;
	
	@Column(nullable = false)
	private String airline;
	
	@Column(nullable = false)
	private String flightnum;

	@Column(nullable = false)
	private Date arrivalTime;
	
	public String getLegfrom() {
		return legfrom;
	}

	public void setLegfrom(String legfrom) {
		this.legfrom = legfrom;
	}

	public String getLegto() {
		return legto;
	}

	public void setLegto(String legto) {
		this.legto = legto;
	}

	public Date getDepartdate() {
		return departdate;
	}

	public void setDepartdate(Date departdate) {
		this.departdate = departdate;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivedate() {
		return arrivedate;
	}

	public void setArrivedate(Date arrivedate) {
		this.arrivedate = arrivedate;
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

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void set_DATEFORMAT(String format) {
		// TODO Auto-generated method stub
		
	}
}
