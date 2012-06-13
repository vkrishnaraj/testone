package aero.nettracer.fs.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class Segment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	private Date date;
	private String departure;
	private String arrival;
	private String airline;
	private String flight;
	@ManyToOne(targetEntity = aero.nettracer.fs.model.Reservation.class)
	@Fetch(FetchMode.SELECT)
	private Reservation reservation;
	@ManyToOne(targetEntity = aero.nettracer.fs.model.FsIncident.class)
	@Fetch(FetchMode.SELECT)
	private FsIncident incident;
	@ManyToOne(targetEntity = aero.nettracer.fs.model.FsClaim.class)
	@Fetch(FetchMode.SELECT)
	private FsClaim claim;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getFlight() {
		return flight;
	}

	public void setFlight(String flight) {
		this.flight = flight;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public FsIncident getIncident() {
		return incident;
	}

	public void setIncident(FsIncident incident) {
		this.incident = incident;
	}

	public FsClaim getClaim() {
		return claim;
	}

	public void setClaim(FsClaim claim) {
		this.claim = claim;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
