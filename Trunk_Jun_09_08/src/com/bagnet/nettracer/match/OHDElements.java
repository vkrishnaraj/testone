/*
 * Created on Sep 14, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.match;

import java.util.ArrayList;

import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class OHDElements {
	private String claimchecknum = "";
	private String recordlocator = "";

	private String membershipnum = "";
	private String membership_company = "";

	private int manufacturer_ID;

	private int xdesc1, xdesc2, xdesc3;

	private String color, bagtype;

	private ArrayList passengers = new ArrayList();

	private ArrayList addresses = new ArrayList();

	private ArrayList phonelist = new ArrayList();

	private ArrayList itineraries = new ArrayList();

	private ArrayList contents = new ArrayList();

	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(" :::claimchecknum " + claimchecknum);
		sb.append(" :::recordlocator " + recordlocator + " :::membershipnum " + membershipnum);

		for (int i = 0; i < passengers.size(); i++) {
			sb.append(" :::names" + i + " ");
			sb.append(((OHD_Passenger) passengers.get(i)).getLastname() + ", ");
			sb.append(((OHD_Passenger) passengers.get(i)).getFirstname() + " ");
			sb.append(((OHD_Passenger) passengers.get(i)).getMiddlename());
		}

		for (int i = 0; i < addresses.size(); i++) {
			sb.append(" :::address" + i + " ");
			sb.append(((OHD_Address) addresses.get(i)).getAddress1() + ", ");
			sb.append(((OHD_Address) addresses.get(i)).getAddress2() + ", ");
			sb.append(((OHD_Address) addresses.get(i)).getCity() + ", ");
			sb.append(((OHD_Address) addresses.get(i)).getState_ID() + ", ");
			sb.append(((OHD_Address) addresses.get(i)).getZip() + ", ");
			sb.append(((OHD_Address) addresses.get(i)).getEmail() + ", ");

		}

		sb.append(" ::: phones ");
		for (int i = 0; i < phonelist.size(); i++) {
			sb.append((String) phonelist.get(i) + ", ");
		}

		for (int i = 0; i < itineraries.size(); i++) {
			sb.append(" :::itinerary" + i + " ");
			sb.append(((OHD_Itinerary) itineraries.get(i)).getLegfrom() + ", ");
			sb.append(((OHD_Itinerary) itineraries.get(i)).getLegto() + ", ");
			sb.append(((OHD_Itinerary) itineraries.get(i)).getDepartdate() + ", ");
			sb.append(((OHD_Itinerary) itineraries.get(i)).getArrivedate() + ", ");
			sb.append(((OHD_Itinerary) itineraries.get(i)).getFlightnum() + ", ");
			sb.append(((OHD_Itinerary) itineraries.get(i)).getSchdeparttime() + ", ");
			sb.append(((OHD_Itinerary) itineraries.get(i)).getScharrivetime() + ", ");
			sb.append(((OHD_Itinerary) itineraries.get(i)).getActdeparttime() + ", ");
			sb.append(((OHD_Itinerary) itineraries.get(i)).getActarrivetime() + ", ");
		}

		return sb.toString();
	}

	/**
	 * @return Returns the addresses.
	 */
	public ArrayList getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 *          The addresses to set.
	 */
	public void setAddresses(ArrayList addresses) {
		this.addresses = addresses;
	}

	/**
	 * @return Returns the passengers.
	 */
	public ArrayList getPassengers() {
		return passengers;
	}

	/**
	 * @param passengers
	 *          The passengers to set.
	 */
	public void setPassengers(ArrayList passengers) {
		this.passengers = passengers;
	}

	/**
	 * @return Returns the recordlocator.
	 */
	public String getRecordlocator() {
		return recordlocator;
	}

	/**
	 * @param recordlocator
	 *          The recordlocator to set.
	 */
	public void setRecordlocator(String recordlocator) {
		this.recordlocator = recordlocator;
	}

	/**
	 * @return Returns the claimchecknum.
	 */
	public String getClaimchecknum() {
		return claimchecknum;
	}

	/**
	 * @param claimchecknum
	 *          The claimchecknum to set.
	 */
	public void setClaimchecknum(String claimchecknum) {
		this.claimchecknum = claimchecknum;
	}

	/**
	 * @return Returns the membershipnum.
	 */
	public String getMembershipnum() {
		return membershipnum;
	}

	/**
	 * @param membershipnum
	 *          The membershipnum to set.
	 */
	public void setMembershipnum(String membershipnum) {
		this.membershipnum = membershipnum;
	}

	/**
	 * @return Returns the membership_company.
	 */
	public String getMembership_company() {
		return membership_company;
	}

	/**
	 * @param membership_company
	 *          The membership_company to set.
	 */
	public void setMembership_company(String membership_company) {
		this.membership_company = membership_company;
	}

	/**
	 * @return Returns the bagtype.
	 */
	public String getBagtype() {
		return bagtype;
	}

	/**
	 * @param bagtype
	 *          The bagtype to set.
	 */
	public void setBagtype(String bagtype) {
		this.bagtype = bagtype;
	}

	/**
	 * @return Returns the color.
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *          The color to set.
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return Returns the manufacturer_ID.
	 */
	public int getManufacturer_ID() {
		return manufacturer_ID;
	}

	/**
	 * @param manufacturer_ID
	 *          The manufacturer_ID to set.
	 */
	public void setManufacturer_ID(int manufacturer_ID) {
		this.manufacturer_ID = manufacturer_ID;
	}

	/**
	 * @return Returns the xdesc1.
	 */
	public int getXdesc1() {
		return xdesc1;
	}

	/**
	 * @param xdesc1
	 *          The xdesc1 to set.
	 */
	public void setXdesc1(int xdesc1) {
		this.xdesc1 = xdesc1;
	}

	/**
	 * @return Returns the xdesc2.
	 */
	public int getXdesc2() {
		return xdesc2;
	}

	/**
	 * @param xdesc2
	 *          The xdesc2 to set.
	 */
	public void setXdesc2(int xdesc2) {
		this.xdesc2 = xdesc2;
	}

	/**
	 * @return Returns the xdesc3.
	 */
	public int getXdesc3() {
		return xdesc3;
	}

	/**
	 * @param xdesc3
	 *          The xdesc3 to set.
	 */
	public void setXdesc3(int xdesc3) {
		this.xdesc3 = xdesc3;
	}

	/**
	 * @return Returns the phonelist.
	 */
	public ArrayList getPhonelist() {
		return phonelist;
	}

	/**
	 * @param phonelist
	 *          The phonelist to set.
	 */
	public void setPhonelist(ArrayList phonelist) {
		this.phonelist = phonelist;
	}

	/**
	 * @return Returns the itineraries.
	 */
	public ArrayList getItineraries() {
		return itineraries;
	}

	/**
	 * @param itineraries
	 *          The itineraries to set.
	 */
	public void setItineraries(ArrayList itineraries) {
		this.itineraries = itineraries;
	}

	/**
	 * @return Returns the contents.
	 */
	public ArrayList getContents() {
		return contents;
	}

	/**
	 * @param contents
	 *          The contents to set.
	 */
	public void setContents(ArrayList contents) {
		this.contents = contents;
	}
}