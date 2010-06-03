package com.bagnet.nettracer.tracing.db.onlineclaims;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "oc_itinerary")
@Proxy(lazy = false)
public class OCItinerary {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim.class)
	@JoinColumn(name = "claimId", nullable = false)
	private OnlineClaim claim;
	
	@Column(length = 3)
	private String departureCity;
	
	@Column(length = 3)
	private String arrivalCity;
	
	@Column(length = 20)
	private String airline;
	
	@Column(length = 5)
	private String flightNum;
	private Date date;
	
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
	public String getFlightNum() {
  	return flightNum;
  }
	public void setFlightNum(String flightNum) {
  	this.flightNum = flightNum;
  }
	public Date getDate() {
  	return date;
  }
	public void setDate(Date date) {
  	this.date = date;
  }
	public long getId() {
  	return id;
  }
	public void setId(long id) {
  	this.id = id;
  }
	public OnlineClaim getClaim() {
		return claim;
	}
	public void setClaim(OnlineClaim claim) {
		this.claim = claim;
	}
}
