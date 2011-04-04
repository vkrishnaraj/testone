package aero.nettracer.fs.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.detection.Blacklist;

import com.bagnet.nettracer.tracing.db.ClaimProrate;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;

@Entity
@Table(name = "FS_Claim")
@Proxy(lazy = false)
public class Claim {
	
	@Id
	@GeneratedValue
	private long id;
	private String airlineClaimId;
	private String airline;
	private int claimType;
	private Date claimDate;
	private Date travelDate;
	private double amountClaimed;
	private String amountClaimedCurrency;
	private double amountPaid;
	private int fraudStatus; // 0=unknown, 1 = believed fraud, 2=known fraud
	private boolean denied;
	private String privateReasonForDenial;
	private String publicReasonForDenial;

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
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Incident.class, mappedBy = "claim", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Incident ntIncident; 
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Claimprorate_ID")
	private ClaimProrate claimprorate;

	@ManyToOne
	@JoinColumn(name = "Status_ID", nullable = false)
	private Status status;

	public ClaimProrate getClaimprorate() {
		return claimprorate;
	}

	public void setClaimprorate(ClaimProrate claimprorate) {
		this.claimprorate = claimprorate;
	}
	
	public aero.nettracer.fs.model.FsIncident getIncident() {
		return incident;
	}

	public void setIncident(aero.nettracer.fs.model.FsIncident incident) {
		this.incident = incident;
	}
	
	public Incident getNtIncident() {
		return ntIncident;
	}
	
	public void setNtIncident(Incident ntIncident) {
		this.ntIncident = ntIncident;
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
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
