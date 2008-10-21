package com.bagnet.nettracer.tracing.db.datafeed;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="z_ws_flight_info")
public class FlightInfo {
	
	@Id @GeneratedValue
	private long id;
	
	@Column(length=10)
	private String flightNum;
	
	@Basic
	private Date departureDate;
	
	@Basic
	private Date arrivalDate;
	
	@Column(length=5)
	private String departureCity;
	
	@Column(length=5)
	private String arrivalCity;
	
	@Basic
	private int totalPax;
	
	@Basic
	private int connPax;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

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

	public int getTotalPax() {
		return totalPax;
	}

	public void setTotalPax(int totalPax) {
		this.totalPax = totalPax;
	}

	public int getConnPax() {
		return connPax;
	}

	public void setConnPax(int connPax) {
		this.connPax = connPax;
	}

	
}
