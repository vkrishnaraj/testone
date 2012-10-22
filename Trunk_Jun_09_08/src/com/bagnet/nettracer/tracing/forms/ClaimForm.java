package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import org.apache.struts.action.ActionForm;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsIPAddress;
import aero.nettracer.fs.model.FsReceipt;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;

import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.utils.CreditCardType;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/** * @author Ankur Gupta
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
	
	public String getDispCreateTime() {
		return DateUtils.formatDate(claim.getClaimDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
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
		return getClaimantAddress().getAddress1();
	}
	
	public void setAddress1(String address1) {
		getClaimantAddress().setAddress1(address1);
	}

	public String getAddress2() {
		return getClaimantAddress().getAddress2();
	}
	
	public void setAddress2(String address2) {
		getClaimantAddress().setAddress2(address2);
	}

	public String getCity() {
		return getClaimantAddress().getCity();
	}
	
	public void setCity(String city) {
		getClaimantAddress().setCity(city);
	}

	public String getState() {
		return getClaimantAddress().getState();
	}
	
	public void setState(String state) {
		getClaimantAddress().setState(state);
	}

	public String getProvince() {
		return getClaimantAddress().getProvince();
	}
	
	public void setProvince(String province) {
		getClaimantAddress().setProvince(province);
	}
	
	public String getZip() {
		return getClaimantAddress().getZip();
	}
	
	public void setZip(String zip) {
		getClaimantAddress().setZip(zip);
	}

	public String getCountry() {
		return getClaimantAddress().getCountry();
	}
	
	public void setCountry(String country) {
		getClaimantAddress().setCountry(country);
	}
	
	public String getDispClaimDate() {
		return claim.getDisClaimDate(_DATEFORMAT);
	}
	
	private FsAddress getClaimantAddress() {
		return getClaimant().getAddresses().toArray(new FsAddress[0])[0];
	}
	
	private String getRemark() {
		return claim.getClaimRemark();
	}
	
	private void setRemark(String remark){
		claim.setClaimRemark(remark);
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
	
	public String getClaimantDateOfBirth() {
		return getClaimant().getDisDateOfBirth(_DATEFORMAT);
	}
	
	public void setClaimantDateOfBirth(String dateOfBirth) {
		getClaimant().setDisDateOfBirth(dateOfBirth, _DATEFORMAT);
	}
	
	public Person getPerson(int index) {
		if (claim.getClaimants() != null && index < claim.getClaimants().size()) {
			return new ArrayList<Person>(claim.getClaimants()).get(index);
		}
		return null;
	}
	
	public FsReceipt getReceipt(int index) {
		if (claim.getReceipts() != null && index < claim.getReceipts().size()) {
			return new ArrayList<FsReceipt>(claim.getReceipts()).get(index);
		}
		return null;
	}
	
	public FsIPAddress getIpAddress(int index) {
		if (claim.getIpAddresses() != null && index < claim.getIpAddresses().size()) {
			return new ArrayList<FsIPAddress>(claim.getIpAddresses()).get(index);
		}
		return null;
	}
	
}