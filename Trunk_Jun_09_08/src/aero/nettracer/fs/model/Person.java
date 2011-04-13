package aero.nettracer.fs.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.actions.ModifyClaimAction;
import com.bagnet.nettracer.tracing.utils.StringUtils;

import org.apache.commons.codec.language.Soundex;
import org.apache.commons.codec.language.DoubleMetaphone;

@Entity
@Proxy(lazy = false)
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(Person.class);

	@Id
	@GeneratedValue
	private long id;

	// @OneToOne(targetEntity = aero.nettracer.fs.model.Claim.class)
	@ManyToOne(targetEntity = aero.nettracer.fs.model.FsClaim.class)
	private FsClaim claim;

	// @OneToOne(targetEntity = aero.nettracer.fs.model.Incident.class)
	@ManyToOne(targetEntity = aero.nettracer.fs.model.FsIncident.class)
	private FsIncident incident;

	// @OneToOne(targetEntity = aero.nettracer.fs.model.Reservation.class)
	@ManyToOne(targetEntity = aero.nettracer.fs.model.Reservation.class)
	private Reservation reservation;
	private boolean whiteListed;
	private boolean ccContact;
	private String firstName;
	private String middleName;
	private String lastName;
	private String firstNameSoundex;
	private String lastNameSoundex;
	private String firstNameDmp;
	private String lastNameDmp;
	private String socialSecurity;
	private String driversLicenseIssuer;
	private String driversLicenseNumber;
	private String passportIssuer;
	private String passportNumber;
	private String emailAddress;
	private String description;
	private String ffAirline;
	private String ffNumber;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<Phone> phones;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<FsAddress> addresses;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		this.setFirstNameSoundex((new Soundex()).encode(firstName));
		this.setFirstNameDmp((new DoubleMetaphone()).encode(firstName));
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		this.setLastNameSoundex((new Soundex()).encode(lastName));
		this.setLastNameDmp((new DoubleMetaphone()).encode(lastName));
	}

	public String getSocialSecurity() {
		String toReturn = "";
		if (socialSecurity != null && !socialSecurity.isEmpty()) {
			toReturn = "*********";
		}
		return toReturn;
	}

	public void setSocialSecurity(String socialSecurity) {
		if (socialSecurity.matches("[0-9]{9}")) {
			this.socialSecurity = StringUtils.getMd5Hash(socialSecurity);
		}
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFirstNameSoundex() {
		return firstNameSoundex;
	}

	public void setFirstNameSoundex(String firstNameSoundex) {
		this.firstNameSoundex = firstNameSoundex;
	}

	public String getLastNameSoundex() {
		return lastNameSoundex;
	}

	public void setLastNameSoundex(String lastNameSoundex) {
		this.lastNameSoundex = lastNameSoundex;
	}

	public String getFirstNameDmp() {
		return firstNameDmp;
	}

	public void setFirstNameDmp(String firstNameDmp) {
		this.firstNameDmp = firstNameDmp;
	}

	public String getLastNameDmp() {
		return lastNameDmp;
	}

	public void setLastNameDmp(String lastNameDmp) {
		this.lastNameDmp = lastNameDmp;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	public Set<FsAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<FsAddress> addresses) {
		this.addresses = addresses;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public FsClaim getClaim() {
		return claim;
	}

	public void setClaim(FsClaim claim) {
		this.claim = claim;
	}

	public FsIncident getIncident() {
		return incident;
	}

	public void setIncident(FsIncident incident) {
		this.incident = incident;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public boolean isWhiteListed() {
		return whiteListed;
	}

	public void setWhiteListed(boolean whiteListed) {
		this.whiteListed = whiteListed;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getDriversLicenseIssuer() {
		return driversLicenseIssuer;
	}

	public void setDriversLicenseIssuer(String driversLicenseIssuer) {
		this.driversLicenseIssuer = driversLicenseIssuer;
	}

	public String getDriversLicenseNumber() {
		String toReturn = "";
		if (driversLicenseNumber != null && !driversLicenseNumber.isEmpty()) {
			toReturn = "*********";
		}
		return toReturn;
	}

	public void setDriversLicenseNumber(String driversLicenseNumber) {
		this.driversLicenseNumber = StringUtils.getMd5Hash(driversLicenseNumber);
	}

	public String getPassportIssuer() {
		return passportIssuer;
	}

	public void setPassportIssuer(String passportIssuer) {
		this.passportIssuer = passportIssuer;
	}

	public String getPassportNumber() {
		String toReturn = "";
		if (passportNumber != null && !passportNumber.isEmpty()) {
			toReturn = "***************";
		}
		return toReturn;
	}

	public void setPassportNumber(String passportNumber) {
		if (passportNumber.matches("[0-9]{8,15}?")) {
			this.passportNumber = StringUtils.getMd5Hash(passportNumber);
		}
	}

	public String getFfAirline() {
		return ffAirline;
	}

	public void setFfAirline(String ffAirline) {
		this.ffAirline = ffAirline;
	}

	public String getFfNumber() {
		return ffNumber;
	}

	public void setFfNumber(String ffNumber) {
		this.ffNumber = ffNumber;
	}

	public boolean isCcContact() {
  	return ccContact;
  }

	public void setCcContact(boolean ccContact) {
  	this.ccContact = ccContact;
  }
}
