package com.bagnet.nettracer.tracing.db.onlineclaims.audit;

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
@Table(name = "oc_audit_itinerary")
@Proxy(lazy = false)
public class AOCItinerary {
	@Id
	@GeneratedValue
	private long aid;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim.class)
	@JoinColumn(name = "auditClaimId", nullable = false)
	private AOCClaim claim;

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

	public AOCClaim getClaim() {
		return claim;
	}

	public void setClaim(AOCClaim claim) {
		this.claim = claim;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}
}
