/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Set;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.struts.util.MessageResources;

import aero.nettracer.security.AES;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.NumberUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.cci.utils.parser.ElementNode;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Passenger"
 */
public class Passenger implements Serializable {
	private static final long serialVersionUID = 1L;
	private int Passenger_ID;
	private String jobtitle;
	private int salutation;
	private String firstname;
	private String middlename;
	private String lastname;
	private String driverslicense;
	private String dlstate;
	private String commonnum;
	private String countryofissue;
	private int isprimary;
	private AirlineMembership membership;
	private Set<Address> addresses;
	private Incident incident;
	private int numRonKitsIssued;
	private String languageKey;
	private String languageFreeFlow;
	
	private String driversLicenseProvince;
	private String driversLicenseCountry;

	private String passportNumber;
	private String passportIssuer;
	
	public JRBeanCollectionDataSource getAddressesForReport() {
		if (addresses == null || addresses.size() < 1) return null;

		return new JRBeanCollectionDataSource(new ArrayList<Address>(addresses));
	}

	public String toXML() {
		StringBuffer sb = new StringBuffer();

		sb.append("<passenger>");
		sb.append("<Passenger_ID>" + Passenger_ID + "</Passenger_ID>");
		sb.append("<firstname>" + firstname + "</firstname>");
		sb.append("<middlename>" + middlename + "</middlename>");
		sb.append("<lastname>" + lastname + "</lastname>");
		sb.append("<isprimary>" + isprimary + "</isprimary>");
		sb.append("<jobtitle>" + jobtitle + "</jobtitle>");
		sb.append("<salutation>" + salutation + "</salutation>");
		sb.append("<driverslicense>" + driverslicense + "</driverslicense>");
		sb.append("<dlstate>" + dlstate + "</dlstate>");
		sb.append("<dlProvince>" + driversLicenseProvince + "</dlstate>");
		sb.append("<dlCountry>" + driversLicenseCountry + "</dlstate>");
		sb.append("<commonnumber>" + commonnum + "</commonnumber>");
		sb.append("<countryofissue>" + countryofissue + "</countryofissue>");
		if (membership == null) {
			sb.append("<airlinemembership></airlinemembership><airlinemembership_company></airlinemembership_company>");
		} else {
			sb.append("<airlinemembership>" + membership.getMembershipnum() + "</airlinemembership>");
			sb.append("<airlinemembership_company>" + membership.getCompanycode_ID()
					+ "</airlinemembership_company>");
		}
		sb.append("<addresses>");
		if (getAddresses() != null && getAddresses().size() > 0) {
			for (@SuppressWarnings("rawtypes")
			Iterator j = getAddresses().iterator(); j.hasNext();) {
				Address addr = (Address) j.next();
				sb.append(addr.toXML());
			}
		}
		sb.append("</addresses>");
		
		sb.append("</passenger>");
		return sb.toString();
	}

	public static Passenger XMLtoObject(ElementNode root) {
		Passenger obj = new Passenger();

		ElementNode child = null;
		AirlineMembership am = new AirlineMembership();
		
		
		for (@SuppressWarnings("rawtypes")
		ListIterator i = root.get_children().listIterator(); i.hasNext();) {
			child = (ElementNode) i.next();
			if (child.getType().equals("Passenger_ID")) {
				obj.setPassenger_ID(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("firstname")) {
				obj.setFirstname(child.getTextContents());
			} else if (child.getType().equals("middlename")) {
				obj.setMiddlename(child.getTextContents());	
			} else if (child.getType().equals("lastname")) {
				obj.setLastname(child.getTextContents());
			} else if (child.getType().equals("isprimary")) {
				obj.setIsprimary(NumberUtils.parseInt(child.getTextContents()));	
			} else if (child.getType().equals("jobtitle")) {
				obj.setJobtitle(child.getTextContents());
			} else if (child.getType().equals("salutation")) {
				obj.setSalutation(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("driverslicense")) {
				obj.setDriverslicense(child.getTextContents());
			} else if (child.getType().equals("dlstate")) {
				obj.setDlstate(child.getTextContents());
			} else if (child.getType().equals("driversLicenseProvince")) {
				obj.setDriversLicenseProvince(child.getTextContents());
			} else if (child.getType().equals("driversLicenseCountry")) {
				obj.setDriversLicenseCountry(child.getTextContents());
			} else if (child.getType().equals("commonnumber")) {
				obj.setCommonnum(child.getTextContents());
			} else if (child.getType().equals("countryofissue")) {
				obj.setCountryofissue(child.getTextContents());
			} else if (child.getType().equals("airlinemembership")) {
				am.setMembershipnum(child.getTextContents());
			} else if (child.getType().equals("airlinemembership_company")) {
				am.setCompanycode_ID(child.getTextContents());
			} else if (child.getType().equals("addresses")) {
				ArrayList<Address> al = new ArrayList<Address>();
				@SuppressWarnings({ "rawtypes" })
				ArrayList c = (ArrayList)child.getChildren();
				for (int z=0;z<c.size();z++) {
					al.add(Address.XMLtoObject((ElementNode)c.get(z)));
				}
				obj.setAddresses(new HashSet<Address>(al));
			}
			obj.setMembership(am);

		}

		return obj;
	}
	

	public String getDispcountryofissue() {
		if (countryofissue != null && countryofissue.length() > 0) {
			return TracerUtils.getCountry(countryofissue).getCountry();
		}
		return "";
	}


	public String getAirlinememcompany() {
		String ret = "";
		
		if (membership != null && membership.getCompanycode_ID() != null) {
			ret = membership.getCompanycode_ID();
		}
		
		return ret;
	}

	public String getAirlinememstatus() {
		String ret = "";

		if (membership != null && membership.getMembershipstatus() != null) ret = membership
				.getMembershipstatus();

		return ret;
	}

	public String getAirlinememnumber() {
		String ret = "";

		if (membership != null && membership.getMembershipnum() != null) ret = membership
				.getMembershipnum();

		return ret;
	}

	/**
	 * @return Returns the membership.
	 * @hibernate.many-to-one cascade="all"
	 *                        class="com.bagnet.nettracer.tracing.db.AirlineMembership"
	 *                        column="membership_ID"
	 */
	public AirlineMembership getMembership() {
		return membership;
	}

	/**
	 * @param membership
	 *          The membership to set.
	 */
	public void setMembership(AirlineMembership membership) {
		this.membership = membership;
	}

	/**
	 * @hibernate.set cascade="all" inverse="true" order-by="address_ID"
	 * @hibernate.key column="passenger_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Address"
	 * @return Returns the addresses.
	 */
	public Set <Address> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 *          The addresses to set.
	 */
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}
	
	/**
	 * @return Returns the incident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Incident"
	 *                        column="incident_ID" not-null="true"
	 */
	public Incident getIncident() {
		return incident;
	}
	/**
	 * @param incident The incident to set.
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	/**
	 * @return Returns the commonnum.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getCommonnum() {
		return commonnum;
	}

	/**
	 * @param commonnum
	 *          The commonnum to set.
	 */
	public void setCommonnum(String commonnum) {
		this.commonnum = commonnum;
	}

	/**
	 * @return Returns the countryofissue.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getCountryofissue() {
		return countryofissue;
	}

	/**
	 * @param countryofissue
	 *          The countryofissue to set.
	 */
	public void setCountryofissue(String countryofissue) {
		this.countryofissue = countryofissue;
	}


	/**
	 * @return Returns the firstname.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *          The firstname to set.
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return Returns the jobtitle.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getJobtitle() {
		return jobtitle;
	}

	/**
	 * @param jobtitle
	 *          The jobtitle to set.
	 */
	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	/**
	 * @return Returns the lastname.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *          The lastname to set.
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return Returns the middlename.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getMiddlename() {
		return middlename;
	}

	/**
	 * @param middlename
	 *          The middlename to set.
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	/**
	 * @return Returns the dlstate.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getDlstate() {
		return dlstate;
	}

	/**
	 * @param dlstate
	 *          The dlstate to set.
	 */
	public void setDlstate(String dlstate) {
		this.dlstate = dlstate;
	}
	
	public String getDispdlstate() {
		if (dlstate != null && dlstate.length() > 0) {
			return TracerUtils.getState(dlstate).getState();
		}
		return "";
	}

	/**
	 * @return Returns the driverslicense.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getDriverslicense() {
		return driverslicense;
	}

	/**
	 * @param driverslicense
	 *          The driverslicense to set.
	 */
	public void setDriverslicense(String driverslicense) {
		this.driverslicense = driverslicense;
	}
	
	public String getDecriptedDriversLicense() {
		try {
			return AES.decrypt(driverslicense);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public void setDecriptedDriversLicense(String decriptedDriversLicense) {
		if (decriptedDriversLicense == null || decriptedDriversLicense.isEmpty()) return;
		try {
			this.driverslicense = AES.encrypt(decriptedDriversLicense);
		} catch (Exception e) {
			e.printStackTrace();
			this.driverslicense = null;
		}
	}
	
	/**
	 * @return Returns the passenger_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 *               column="Passenger_ID"
	 * @hibernate.generator-param name="sequence" value="Passenger_0"
	 *  
	 */
	public int getPassenger_ID() {
		return Passenger_ID;
	}

	/**
	 * @param passenger_ID
	 *          The passenger_ID to set.
	 */
	public void setPassenger_ID(int passenger_ID) {
		Passenger_ID = passenger_ID;
	}

	/**
	 * @return Returns the salutation.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getSalutation() {
		return salutation;
	}

	public String getSalutationdesc() {
		MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		switch (getSalutation()) {
			case 0: return messages.getMessage(new Locale(TracingConstants.DEFAULT_LOCALE), "select.please_select");
			case 1: return messages.getMessage(new Locale(TracingConstants.DEFAULT_LOCALE), "select.dr");
			case 2: return messages.getMessage(new Locale(TracingConstants.DEFAULT_LOCALE), "select.mr");
			case 3: return messages.getMessage(new Locale(TracingConstants.DEFAULT_LOCALE), "select.ms");
			case 4: return messages.getMessage(new Locale(TracingConstants.DEFAULT_LOCALE), "select.miss");
			case 5: return messages.getMessage(new Locale(TracingConstants.DEFAULT_LOCALE), "select.mrs");
			case 6: return messages.getMessage(new Locale(TracingConstants.DEFAULT_LOCALE), "select.other");
		}
		return "";
	}
	
	public String getDispsalutation() {
		return getSalutationdesc();
	}

	
	/**
	 * @param salutation
	 *          The salutation to set.
	 */
	public void setSalutation(int salutation) {
		this.salutation = salutation;
	}

	/**
	 * @return Returns the isprimary.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getIsprimary() {
		return isprimary;
	}

	/**
	 * @param isprimary
	 *          The isprimary to set.
	 */
	public void setIsprimary(int isprimary) {
		this.isprimary = isprimary;
	}

	public Address getAddress(int i) {
		if (this.getAddresses() != null) {
			ArrayList<Address> t = new ArrayList<Address>(this.getAddresses());
			while (t.size() <= i) {
				Address adddd = new Address();
				adddd.setPassenger(this);
				addAddress(adddd);
				t = new ArrayList<Address>(this.getAddresses());
			}
			return (Address) t.get(i);
		} else return null;
	}

	public void addAddress(Address address) {
		if (this.getAddresses() == null) this.setAddresses(new HashSet<Address>());
		this.getAddresses().add(address);
	}

	/** 
	 * @hibernate.property type="integer"
	 */
	public int getNumRonKitsIssued() {
		return numRonKitsIssued;
	}

	public void setNumRonKitsIssued(int numRonKitsIssued) {
		this.numRonKitsIssued = numRonKitsIssued;
	}

	/**
	 * @return Returns the languageKey.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getLanguageKey() {
		return languageKey;
	}

	public void setLanguageKey(String languageKey) {
		this.languageKey = languageKey;
	}

	/**
	 * @return Returns the languageFreeFlow.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getLanguageFreeFlow() {
		return languageFreeFlow;
	}

	public void setLanguageFreeFlow(String languageFreeFlow) {
		this.languageFreeFlow = languageFreeFlow;
	}
	
	public String getRedactedDriversLicense() {
		return driverslicense != null && !driverslicense.isEmpty() ? "*********" : "";
	}
	
	public void setRedactedDriversLicense(String redactedDriversLicense) {
		// NOOP for struts
	}
	
	/**
	 * @hibernate.property type="string" column="dlprovince"
	 */
	public String getDriversLicenseProvince() {
		return driversLicenseProvince;
	}

	public void setDriversLicenseProvince(String driversLicenseProvince) {
		this.driversLicenseProvince = driversLicenseProvince;
	}

	/**
	 * @hibernate.property type="string" length="2" column="dlcountry"
	 */
	public String getDriversLicenseCountry() {
		return driversLicenseCountry;	
	}

	public void setDriversLicenseCountry(String driversLicenseCountry) {
		this.driversLicenseCountry = driversLicenseCountry;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPassportIssuer() {
		return passportIssuer;
	}

	public void setPassportIssuer(String passportIssuer) {
		this.passportIssuer = passportIssuer;
	}	
	
	public String getRedactedPassportNumber() {
		return passportNumber != null && !passportNumber.isEmpty() ? "***************" : "";
	}
	
	public void setDecryptedPassportNumber(String decryptedPassportNumber) {
		if (decryptedPassportNumber == null || decryptedPassportNumber.isEmpty()) return;
		try {
			this.passportNumber = AES.encrypt(decryptedPassportNumber);
		} catch (Exception e) {
			e.printStackTrace();
			this.passportNumber = null;
		}
	}
	
	public String getDecryptedPassportNumber() {
		try {
			return AES.decrypt(passportNumber);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}