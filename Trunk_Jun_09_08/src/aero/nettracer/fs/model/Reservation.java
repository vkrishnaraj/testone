package aero.nettracer.fs.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.detection.Whitelist;

@Entity
@Proxy(lazy = false)
public class Reservation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@OneToOne(targetEntity = aero.nettracer.fs.model.FsIncident.class)
	private FsIncident incident;
	private String airline;
	private String recordLocator;
	private Date travelDate;
	private String formOfPayment;
	private String ccType;
	private String ccNumber;
	private String ccLName;
	private String ccFName;
	private String ccMName;
	private int ccExpMonth;
	private int ccExpYear;

	private Double totalFare;

	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<Segment> segments;

	@OneToOne(targetEntity = aero.nettracer.fs.model.detection.Whitelist.class, cascade = CascadeType.ALL)
	private Whitelist ccWhitelist;
	private double cashAmount;
	private double checkAmount;
	private double ccAmount;
	private int itinComplexity;
	private int tripLength;
	@OneToOne(targetEntity = aero.nettracer.fs.model.PnrData.class, cascade = CascadeType.ALL)
	private PnrData pnrData;

	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<Person> passengers;

	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<Phone> phones;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public FsIncident getIncident() {
		return incident;
	}

	public void setIncident(FsIncident incident) {
		this.incident = incident;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getRecordLocator() {
		return recordLocator;
	}

	public void setRecordLocator(String recordLocator) {
		this.recordLocator = recordLocator;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public String getFormOfPayment() {
		return formOfPayment;
	}

	public void setFormOfPayment(String formOfPayment) {
		this.formOfPayment = formOfPayment;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public double getCashAmount() {
		return cashAmount;
	}
	
	public void setCashAmount(double cashAmount) {
		this.cashAmount = cashAmount;
	}

	public double getCheckAmount() {
		return checkAmount;
	}
	
	public void setCheckAmount(double checkAmount) {
		this.checkAmount = checkAmount;
	}
	public double getCcAmount() {
		return ccAmount;
	}

	public void setCcAmount(double ccAmount) {
		this.ccAmount = ccAmount;
	}

	public int getItinComplexity() {
		return itinComplexity;
	}

	public void setItinComplexity(int itinComplexity) {
		this.itinComplexity = itinComplexity;
	}

	public int getTripLength() {
		return tripLength;
	}

	public void setTripLength(int tripLength) {
		this.tripLength = tripLength;
	}

	public PnrData getPnrData() {
		return pnrData;
	}

	public void setPnrData(PnrData pnrData) {
		this.pnrData = pnrData;
	}

	public Set<Person> getPassengers() {
		return passengers;
	}

	public void setPassengers(Set<Person> passengers) {
		this.passengers = passengers;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	public Whitelist getCcWhitelist() {
		return ccWhitelist;
	}

	public void setCcWhitelist(Whitelist ccWhitelist) {
		this.ccWhitelist = ccWhitelist;
	}

	public Set<Segment> getSegments() {
		return segments;
	}

	public void setSegments(Set<Segment> segments) {
		this.segments = segments;
	}

	public String getCcType() {
		return ccType;
	}

	public void setCcType(String ccType) {
		this.ccType = ccType;
	}

	public Double getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(Double totalFare) {
		this.totalFare = totalFare;
	}

	public String getCcLName() {
		return ccLName;
	}

	public void setCcLName(String ccLName) {
		this.ccLName = ccLName;
	}

	public String getCcFName() {
		return ccFName;
	}

	public void setCcFName(String ccFName) {
		this.ccFName = ccFName;
	}

	public String getCcMName() {
		return ccMName;
	}

	public void setCcMName(String ccMName) {
		this.ccMName = ccMName;
	}

	public int getCcExpMonth() {
		return ccExpMonth;
	}

	public void setCcExpMonth(int ccExpMonth) {
		this.ccExpMonth = ccExpMonth;
	}

	public int getCcExpYear() {
		return ccExpYear;
	}

	public void setCcExpYear(int ccExpYear) {
		this.ccExpYear = ccExpYear;
	}

}
