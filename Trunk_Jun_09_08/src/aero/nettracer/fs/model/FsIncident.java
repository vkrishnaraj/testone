package aero.nettracer.fs.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name="FS_Incident")
@Proxy(lazy = false)
public class FsIncident {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(unique=true)
	private String airlineIncidentId;
	private Date incidentCreated;
	private int incidentType;

	@OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<Segment> segments;
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.FsClaim.class, cascade = CascadeType.ALL) 
	private FsClaim claim;
	private int numberOfBdos;
	private int numberDaysOpen;
	private Date timestampOpen;
	private Date timestampClosed;
	private int itinComplexity;
	private String incidentDescription;

	@OneToOne(targetEntity = aero.nettracer.fs.model.Reservation.class, cascade = CascadeType.ALL, mappedBy = "incident")
	private Reservation reservation;
	
	private String remarks;

	@OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<Bag> bags;

	@OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<Person> passengers;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAirlineIncidentId() {
		return airlineIncidentId;
	}

	public void setAirlineIncidentId(String airlineIncidentId) {
		this.airlineIncidentId = airlineIncidentId;
	}

	public int getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(int incidentType) {
		this.incidentType = incidentType;
	}

	public FsClaim getClaim() {
		return claim;
	}

	public void setClaim(FsClaim claim) {
		this.claim = claim;
	}

	public int getNumberOfBdos() {
		return numberOfBdos;
	}

	public void setNumberOfBdos(int numberOfBdos) {
		this.numberOfBdos = numberOfBdos;
	}

	public int getNumberDaysOpen() {
		return numberDaysOpen;
	}

	public void setNumberDaysOpen(int numberDaysOpen) {
		this.numberDaysOpen = numberDaysOpen;
	}

	public Date getTimestampOpen() {
		return timestampOpen;
	}

	public void setTimestampOpen(Date timestampOpen) {
		this.timestampOpen = timestampOpen;
	}

	public Date getTimestampClosed() {
		return timestampClosed;
	}

	public void setTimestampClosed(Date timestampClosed) {
		this.timestampClosed = timestampClosed;
	}

	public int getItinComplexity() {
		return itinComplexity;
	}

	public void setItinComplexity(int itinComplexity) {
		this.itinComplexity = itinComplexity;
	}

	public String getIncidentDescription() {
		return incidentDescription;
	}

	public void setIncidentDescription(String incidentDescription) {
		this.incidentDescription = incidentDescription;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Date getIncidentCreated() {
		return incidentCreated;
	}

	public void setIncidentCreated(Date incidentCreated) {
		this.incidentCreated = incidentCreated;
	}

	public Set<Bag> getBags() {
		return bags;
	}

	public void setBags(Set<Bag> bags) {
		this.bags = bags;
	}

	public Set<Person> getPassengers() {
  	return passengers;
  }

	public void setPassengers(Set<Person> passengers) {
  	this.passengers = passengers;
  }

	public Set<Segment> getSegments() {
  	return segments;
  }

	public void setSegments(Set<Segment> segments) {
  	this.segments = segments;
  }

}
