package aero.nettracer.fs.model.transport.v2;

import java.io.Serializable;
import aero.nettracer.fs.model.transport.v2.detection.PhoneWhiteList;

public class Phone implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int HOME = 1;
	public static final int MOBILE = 2;
	public static final int WORK = 3;
	public static final int ALTERNATE = 4;
	public static final int PAGER = 5;
	
	private long id;
	private FsIncident incident;
	private PhoneWhiteList whitelist;
	private Reservation reservation;
	private FsClaim claim;
	private Person person;
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
