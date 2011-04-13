package com.bagnet.nettracer.tracing.forms;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;

import com.bagnet.nettracer.tracing.actions.ModifyClaimAction;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.utils.CreditCardType;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Ankur Gupta
 * 
 * This class represents the claim form that is used for accessing claim
 * functionality
 */
public final class ClaimForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private Claim claim;
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;
	
	private String mod_claim_reason;
	private String mod_claim_reason2;
	
	private Person claimant = null;
//	private FsAddress reservationAddress = null;
	
	public Claim getClaim() {
		return claim;
	}

	public void setClaim(Claim claim) {
		this.claim = claim;
	}
	
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}

	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	public void set_TIMEFORMAT(String _TIMEFORMAT) {
		this._TIMEFORMAT = _TIMEFORMAT;
	}

	public java.util.TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	public void set_TIMEZONE(java.util.TimeZone _TIMEZONE) {
		this._TIMEZONE = _TIMEZONE;
	}
	
	public CreditCardType[] getCreditCardTypes() {
		return CreditCardType.values();
	}
	
	public ArrayList<Integer> getCcMonths() {
		return DateUtils.getCcMonths();
	}
	
	public ArrayList<Integer> getCcYears() {
		return DateUtils.getCcYears();
	}

	public String getMod_claim_reason() {
		return mod_claim_reason;
	}

	public void setMod_claim_reason(String mod_claim_reason) {
		this.mod_claim_reason = mod_claim_reason;
	}

	public String getMod_claim_reason2() {
		return mod_claim_reason2;
	}

	public void setMod_claim_reason2(String mod_claim_reason2) {
		this.mod_claim_reason2 = mod_claim_reason2;
	}
	
	public String getHomePhone() {
		return getPhoneNumber(Phone.HOME);
	}
	
	public void setHomePhone(String homePhone) {
		setPhoneNumber(Phone.HOME, homePhone);
	}
	
	public String getWorkPhone() {
		return getPhoneNumber(Phone.WORK);
	}
	
	public void setWorkPhone(String workPhone) {
		setPhoneNumber(Phone.WORK, workPhone);
	}
	public String getMobilePhone() {
		return getPhoneNumber(Phone.MOBILE);
	}
	
	public void setMobilePhone(String mobilePhone) {
		setPhoneNumber(Phone.MOBILE, mobilePhone);
	}
	
	public String getPagerPhone() {
		return getPhoneNumber(Phone.PAGER);
	}
	
	public void setPagerPhone(String pagerPhone) {
		setPhoneNumber(Phone.PAGER, pagerPhone);
	}
	
	public String getAlternatePhone() {
		return getPhoneNumber(Phone.ALTERNATE);
	}
	
	public void setAlternatePhone(String alternatePhone) {
		setPhoneNumber(Phone.ALTERNATE, alternatePhone);
	}

	public Person getClaimant() {
		if (claimant == null) {
			return claim.getClaimants().toArray(new Person[0])[0];
		}
		return claimant;
	}
	
	public void setClaimant(Person claimant) {
		this.claimant = claimant;
		LinkedHashSet<Person> claimants = new LinkedHashSet<Person>();
		claimants.add(claimant);
		claim.setClaimants(claimants);
	}
	
	public String getAddress1() {
		return getClaimant().getAddresses().toArray(new FsAddress[0])[0].getAddress1();
	}
	
	public void setAddress1(String address1) {
		getClaimant().getAddresses().toArray(new FsAddress[0])[0].setAddress1(address1);
	}

	public String getAddress2() {
		return getClaimant().getAddresses().toArray(new FsAddress[0])[0].getAddress2();
	}
	
	public void setAddress2(String address2) {
		getClaimant().getAddresses().toArray(new FsAddress[0])[0].setAddress2(address2);
	}

	public String getCity() {
		return getClaimant().getAddresses().toArray(new FsAddress[0])[0].getCity();
	}
	
	public void setCity(String city) {
		getClaimant().getAddresses().toArray(new FsAddress[0])[0].setCity(city);
	}

	public String getState() {
		return getClaimant().getAddresses().toArray(new FsAddress[0])[0].getState();
	}
	
	public void setState(String state) {
		getClaimant().getAddresses().toArray(new FsAddress[0])[0].setState(state);
	}

	public String getProvince() {
		return getClaimant().getAddresses().toArray(new FsAddress[0])[0].getProvince();
	}
	
	public void setProvince(String province) {
		getClaimant().getAddresses().toArray(new FsAddress[0])[0].setProvince(province);
	}
	
	public String getZip() {
		return getClaimant().getAddresses().toArray(new FsAddress[0])[0].getZip();
	}
	
	public void setZip(String zip) {
		getClaimant().getAddresses().toArray(new FsAddress[0])[0].setZip(zip);
	}

	public String getCountry() {
		return getClaimant().getAddresses().toArray(new FsAddress[0])[0].getCountry();
	}
	
	public void setCountry(String country) {
		getClaimant().getAddresses().toArray(new FsAddress[0])[0].setCountry(country);
	}
	
	public String getSsNumber() {
		return getClaimant().getSocialSecurity();
	}
	
	public void setSsNumber(String ssNumber) {
		getClaimant().setSocialSecurity(ssNumber);
	}
	
//	private FsAddress getReservationAddress() {
//		if (reservationAddress == null) {
//			reservationAddress = claim.getIncident().getReservation().getPassengers().toArray(new Person[0])[0].getAddresses().toArray(new FsAddress[0])[0];
//		}
//		return reservationAddress;
//	}
	
	private String getPhoneNumber(int type) {
		return getPhone(type).getPhoneNumber();
	}

	private void setPhoneNumber(int type, String phoneNumber) {
		if (phoneNumber != null && !phoneNumber.isEmpty()) {
			Phone phone = getPhone(type);
			phone.setPhoneNumber(phoneNumber);
			setPhone(phone);
		}
	}
	
	private Phone getPhone(int type) {
		Phone phone = new Phone();
		
//		Person[] people = claim.getClaimants().toArray(new Person[0]);
		Person person = getClaimant();
		ArrayList<Phone> phones = new ArrayList<Phone>(person.getPhones());

		phone.setType(type);
		phone.setPerson(person);
		phone.setIncident(claim.getIncident());
		
		Phone candidate = null;
		for (int i = 0; i < phones.size(); ++i) {
			candidate = phones.get(i);
			if (candidate == null) {
				break;
			} else {
				if (candidate.getType() == type) {
					phone = candidate;
					break;
				}
			}
		}
		return phone;
	}
	
	private void setPhone(Phone phone) {
		Person[] people = claim.getClaimants().toArray(new Person[0]);
		people[0].getPhones().add(phone);		
	}
	
	public FsAddress getClaimantAddress(int i) {
		Person claimant = getClaimant();
		if (claimant != null) {
			ArrayList<FsAddress> addresses = new ArrayList<FsAddress>(claimant.getAddresses());
			if (addresses != null && i < addresses.size()) {
				return addresses.get(i);
			}
		}
		return null;
	}
	
}