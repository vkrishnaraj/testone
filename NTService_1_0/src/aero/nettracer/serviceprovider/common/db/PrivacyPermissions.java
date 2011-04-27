package aero.nettracer.serviceprovider.common.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity     
@Table(name="privacy_permissions")     
public class PrivacyPermissions implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5753903039210144230L;

	PrivacyPermissionsKey key;
	
	@Id
	public PrivacyPermissionsKey getKey() {
		return key;
	}

	public void setKey(PrivacyPermissionsKey key) {
		this.key = key;
	}

	boolean allEnabled = false;
	
	@Transient
	public boolean isAllEnabled() {
		return allEnabled;
	}

	public void setAllEnabled(boolean allEnabled) {
		this.allEnabled = allEnabled;
	}

	boolean autosend;
	
	boolean name;
	boolean address;
	boolean phonenumber;
	boolean email;
	
	boolean claimtype;
	boolean claimdate;
	boolean traveldate;
	boolean amountclaimed;
	boolean amountpaid;
	boolean fraudstatus;
	boolean denied;
	boolean denialreason;
	
	boolean cc;
	boolean drivers;
	boolean ffn;
	boolean ssn;
	boolean passport;
	boolean pnrloc;
	boolean pnrdata;
	boolean dob;
	boolean itin;
	boolean ticketamount;


	boolean bag;
    boolean bdo;
	boolean incdate;
	boolean incremarks;
	


	public boolean isTicketamount() {
		return ticketamount;
	}

	public void setTicketamount(boolean ticketamount) {
		this.ticketamount = ticketamount;
	}

	public boolean isCc() {
		return cc;
	}

	public void setCc(boolean cc) {
		this.cc = cc;
	}

	public boolean isDrivers() {
		return drivers;
	}

	public void setDrivers(boolean drivers) {
		this.drivers = drivers;
	}

	public boolean isFfn() {
		return ffn;
	}

	public void setFfn(boolean ffn) {
		this.ffn = ffn;
	}

	public boolean isSsn() {
		return ssn;
	}

	public void setSsn(boolean ssn) {
		this.ssn = ssn;
	}

	public boolean isPassport() {
		return passport;
	}

	public void setPassport(boolean passport) {
		this.passport = passport;
	}

	public boolean isPnrloc() {
		return pnrloc;
	}

	public void setPnrloc(boolean pnrloc) {
		this.pnrloc = pnrloc;
	}

	public boolean isPnrdata() {
		return pnrdata;
	}

	public void setPnrdata(boolean pnrdata) {
		this.pnrdata = pnrdata;
	}

	public boolean isDob() {
		return dob;
	}

	public void setDob(boolean dob) {
		this.dob = dob;
	}

	public boolean isItin() {
		return itin;
	}

	public void setItin(boolean itin) {
		this.itin = itin;
	}
	
	
	public boolean isClaimtype() {
		return claimtype;
	}

	public void setClaimtype(boolean claimtype) {
		this.claimtype = claimtype;
	}

	public boolean isClaimdate() {
		return claimdate;
	}

	public void setClaimdate(boolean claimdate) {
		this.claimdate = claimdate;
	}

	public boolean isTraveldate() {
		return traveldate;
	}

	public void setTraveldate(boolean traveldate) {
		this.traveldate = traveldate;
	}

	public boolean isAmountclaimed() {
		return amountclaimed;
	}

	public void setAmountclaimed(boolean amountclaimed) {
		this.amountclaimed = amountclaimed;
	}

	public boolean isAmountpaid() {
		return amountpaid;
	}

	public void setAmountpaid(boolean amountpaid) {
		this.amountpaid = amountpaid;
	}

	public boolean isFraudstatus() {
		return fraudstatus;
	}

	public void setFraudstatus(boolean fraudstatus) {
		this.fraudstatus = fraudstatus;
	}

	public boolean isDenied() {
		return denied;
	}

	public void setDenied(boolean denied) {
		this.denied = denied;
	}

	public boolean isDenialreason() {
		return denialreason;
	}

	public void setDenialreason(boolean denialreason) {
		this.denialreason = denialreason;
	}
	
	public boolean isEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}

	public static enum AccessLevelType {
		def("default"), req("request");
		
		private String label;
		
		private AccessLevelType(String label) {
			this.label = label;
		}
		
		public String getLabel(){
			return this.label;
		}
		
	}

	public boolean isAutosend() {
		return autosend;
	}

	public void setAutosend(boolean autosend) {
		this.autosend = autosend;
	}
	
	public boolean isName() {
		return name;
	}

	public void setName(boolean name) {
		this.name = name;
	}

	public boolean isAddress() {
		return address;
	}

	public void setAddress(boolean address) {
		this.address = address;
	}

	public boolean isPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(boolean phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	
	public boolean isBag() {
		return bag;
	}

	public void setBag(boolean bag) {
		this.bag = bag;
	}

	public boolean isBdo() {
		return bdo;
	}

	public void setBdo(boolean bdo) {
		this.bdo = bdo;
	}

	public boolean isIncdate() {
		return incdate;
	}

	public void setIncdate(boolean incdate) {
		this.incdate = incdate;
	}
	
	public boolean isIncremarks() {
		return incremarks;
	}

	public void setIncremarks(boolean incremarks) {
		this.incremarks = incremarks;
	}
	
}
