package aero.nettracer.fs.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.detection.Blacklist;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="subclass_type", discriminatorType = DiscriminatorType.STRING, length=16)
@Proxy(lazy = false)
public class FsClaim implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	protected long id;
	protected String airlineClaimId;
	protected String airline;
	protected int claimType;
	protected Date claimDate;
	protected Date travelDate;
	protected double amountClaimed;
	protected String amountClaimedCurrency;
	protected double amountPaid;
	protected int fraudStatus; // 0=unknown, 1 = believed fraud, 2=known fraud
	protected boolean denied;
	protected String privateReasonForDenial;
	protected String publicReasonForDenial;
	protected String ntIncidentId;
	protected int claimProrateId;
	protected int statusId;

//	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<Person> claimants;

	@OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<Segment> segments;

	@OneToOne(targetEntity = aero.nettracer.fs.model.detection.Blacklist.class)
	private Blacklist blacklist;

	@OneToOne(targetEntity = aero.nettracer.fs.model.FsIncident.class, cascade = CascadeType.ALL)
	private aero.nettracer.fs.model.FsIncident incident;
	
	
	
	public aero.nettracer.fs.model.FsIncident getIncident() {
		return incident;
	}

	public void setIncident(aero.nettracer.fs.model.FsIncident incident) {
		this.incident = incident;
	}
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAirlineClaimId() {
		return airlineClaimId;
	}

	public void setAirlineClaimId(String airlineClaimId) {
		this.airlineClaimId = airlineClaimId;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public int getClaimType() {
		return claimType;
	}

	public void setClaimType(int claimType) {
		this.claimType = claimType;
	}

	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public double getAmountClaimed() {
		return amountClaimed;
	}

	public void setAmountClaimed(double amountClaimed) {
		this.amountClaimed = amountClaimed;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public int getFraudStatus() {
		return fraudStatus;
	}

	public void setFraudStatus(int fraudStatus) {
		this.fraudStatus = fraudStatus;
	}

	public boolean isDenied() {
		return denied;
	}

	public void setDenied(boolean denied) {
		this.denied = denied;
	}

	public String getPrivateReasonForDenial() {
		return privateReasonForDenial;
	}

	public void setPrivateReasonForDenial(String privateReasonForDenial) {
		this.privateReasonForDenial = privateReasonForDenial;
	}

	public String getPublicReasonForDenial() {
		return publicReasonForDenial;
	}

	public void setPublicReasonForDenial(String publicReasonForDenial) {
		this.publicReasonForDenial = publicReasonForDenial;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public Blacklist getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Blacklist blacklist) {
		this.blacklist = blacklist;
	}

	public Set<Person> getClaimants() {
		return claimants;
	}

	public void setClaimants(Set<Person> claimants) {
		this.claimants = claimants;
	}

	public Set<Segment> getSegments() {
		return segments;
	}

	public void setSegments(Set<Segment> segments) {
		this.segments = segments;
	}

	public String getAmountClaimedCurrency() {
	  	return amountClaimedCurrency;
	}

	public void setAmountClaimedCurrency(String amountClaimedCurrency) {
		this.amountClaimedCurrency = amountClaimedCurrency;
	}

	public String getNtIncidentId() {
		return ntIncidentId;
	}

	public void setNtIncidentId(String ntIncidentId) {
		this.ntIncidentId = ntIncidentId;
	}

	public int getClaimProrateId() {
		return claimProrateId;
	}

	public void setClaimProrateId(int claimProrateId) {
		this.claimProrateId = claimProrateId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
}
