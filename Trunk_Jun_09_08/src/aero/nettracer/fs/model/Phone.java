package aero.nettracer.fs.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import aero.nettracer.fs.model.detection.PhoneWhiteList;

@Entity
@Proxy(lazy = false)
public class Phone implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int HOME = 1;
	public static final int MOBILE = 2;
	public static final int WORK = 3;
	public static final int ALTERNATE = 4;
	public static final int PAGER = 5;
	
	
	@Id
	@GeneratedValue
	private long id;

	// @OneToOne(targetEntity = aero.nettracer.fs.model.Incident.class)
	@ManyToOne(targetEntity = aero.nettracer.fs.model.FsIncident.class)
	@Fetch(FetchMode.SELECT)
	private FsIncident incident;

	@ManyToOne(targetEntity = aero.nettracer.fs.model.detection.PhoneWhiteList.class)
	@Fetch(FetchMode.SELECT)
	private PhoneWhiteList whitelist;

	@ManyToOne(targetEntity = aero.nettracer.fs.model.Reservation.class)
	@Fetch(FetchMode.SELECT)
	private Reservation reservation;

	@ManyToOne(targetEntity = aero.nettracer.fs.model.FsClaim.class)
	@Fetch(FetchMode.SELECT)
	private FsClaim claim;
	
	@ManyToOne(targetEntity = aero.nettracer.fs.model.Person.class)
	@Fetch(FetchMode.SELECT)
	private Person person;
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.FsReceipt.class)
	@Fetch(FetchMode.SELECT)
	private FsReceipt receipt;
	
	private String phoneNumber;
	private int type;
	
	private String association;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public FsReceipt getReceipt() {
		return receipt;
	}

	public void setReceipt(FsReceipt receipt) {
		this.receipt = receipt;
	}

	public void setWhitelist(PhoneWhiteList whitelist) {
		this.whitelist = whitelist;
	}

	public PhoneWhiteList getWhitelist() {
		return whitelist;
	}

	public boolean isEmpty() {
		return phoneNumber != null && !phoneNumber.isEmpty();
	}

	public void setAssociation(String association) {
		this.association = association;
	}

	public String getAssociation() {
		return association;
	}

	public void setClaim(FsClaim claim) {
		this.claim = claim;
	}

	public FsClaim getClaim() {
		return claim;
	}
	
}
