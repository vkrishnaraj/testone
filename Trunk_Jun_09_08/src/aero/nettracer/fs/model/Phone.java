package aero.nettracer.fs.model;

import java.io.Serializable;

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

@Entity
@Proxy(lazy = false)
public class Phone implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int HOME = 1;
	public static final int MOBILE = 2;
	public static final int WORK = 3;
	public static final int ALTERNATE = 4;
	public static final int PAGER = 5;
	
	@Transient
	private String whiteListDescription;
	
	@Id
	@GeneratedValue
	private long id;

	// @OneToOne(targetEntity = aero.nettracer.fs.model.Incident.class)
	@ManyToOne(targetEntity = aero.nettracer.fs.model.FsIncident.class)
	@Fetch(FetchMode.SELECT)
	private FsIncident incident;

	private boolean whiteListed;

	@ManyToOne(targetEntity = aero.nettracer.fs.model.Reservation.class)
	@Fetch(FetchMode.SELECT)
	private Reservation reservation;

	@ManyToOne(targetEntity = aero.nettracer.fs.model.Person.class)
	@Fetch(FetchMode.SELECT)
	private Person person;
	
	@OneToOne(targetEntity = aero.nettracer.fs.model.FsReceipt.class)
	@Fetch(FetchMode.SELECT)
	private FsReceipt receipt;
	
	private String phoneNumber;
	private int type;

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

	public boolean isWhiteListed() {
		return whiteListed;
	}

	public void setWhiteListed(boolean whiteListed) {
		this.whiteListed = whiteListed;
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

	public void setWhiteListDescription(String whiteListDescription) {
		this.whiteListDescription = whiteListDescription;
	}

	public String getWhiteListDescription() {
		return whiteListDescription;
	}
	
}
