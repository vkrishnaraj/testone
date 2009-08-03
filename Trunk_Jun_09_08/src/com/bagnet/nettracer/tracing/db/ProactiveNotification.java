package com.bagnet.nettracer.tracing.db;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "proactiveNotification")
@Proxy(lazy = false)
public class ProactiveNotification {

	public static final int STATUS_OPEN = 81;
	public static final int STATUS_CLOSED = 82;
	
	@Id
	@GeneratedValue
	private long pcn_id;

	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "destinationStation_ID", nullable = false)
	private Station destinationStation;

	@ManyToOne
	@JoinColumn(name = "incident_ID", nullable = true)
	private Incident incident;
	
	@OneToMany(mappedBy = "pcn", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private Set<OHD_Log> ohd_logs;
	
	@Column(length = 40)
	private String name;
	
	@Column(length = 6)
	private String locator;
	
	@Column(length = 2)
	private String missedFlightAirline;
	
	@Basic
	private String missedFlightNumber;
	
	@Basic
	private Date missedFlightDate;
	
	@Basic
	private String missedFlightDestination;
	
	@Transient
	private boolean finalFlightsMatch;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public Set<OHD_Log> getOhd_logs() {
		return ohd_logs;
	}

	public void setOhd_logs(Set<OHD_Log> ohd_log) {
		this.ohd_logs = ohd_logs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocator() {
		return locator;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}

	public String getMissedFlightAirline() {
		return missedFlightAirline;
	}

	public void setMissedFlightAirline(String missedFlightAirline) {
		this.missedFlightAirline = missedFlightAirline;
	}

	public Date getMissedFlightDate() {
		return missedFlightDate;
	}

	public void setMissedFlightDate(Date missedFlightDate) {
		this.missedFlightDate = missedFlightDate;
	}

	public static int getSTATUS_OPEN() {
		return STATUS_OPEN;
	}

	public static int getSTATUS_CLOSED() {
		return STATUS_CLOSED;
	}

	public long getPcn_id() {
		return pcn_id;
	}

	public void setPcn_id(long pcn_id) {
		this.pcn_id = pcn_id;
	}

	public Station getDestinationStation() {
		return destinationStation;
	}

	public void setDestinationStation(Station destinationStation) {
		this.destinationStation = destinationStation;
	}

	public boolean isFinalFlightsMatch() {
		return finalFlightsMatch;
	}

	public void setFinalFlightsMatch(boolean finalFlightsMatch) {
		this.finalFlightsMatch = finalFlightsMatch;
	}

	public void setMissedFlightNumber(String missedFlightNumber) {
		this.missedFlightNumber = missedFlightNumber;
	}

	public String getMissedFlightNumber() {
		return missedFlightNumber;
	}

	public String getMissedFlightDestination() {
		return missedFlightDestination;
	}

	public void setMissedFlightDestination(String missedFlightDestination) {
		this.missedFlightDestination = missedFlightDestination;
	}
}
