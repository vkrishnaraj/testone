/*
 * Created on Sep 14, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.match;

import java.util.ArrayList;
import java.util.Date;

import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Itinerary;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class IncidentElements {
	private String recordlocator = "";

	private ArrayList items = new ArrayList();

	private ArrayList claimchecks = new ArrayList();

	private ArrayList itineraries = new ArrayList();

	private ArrayList passengers = new ArrayList();

	private ArrayList addresses = new ArrayList();

	private ArrayList phonelist = new ArrayList();

	private ArrayList memberships = new ArrayList();

	private Date createdate;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < claimchecks.size(); i++) {
			sb.append(" :::claimchecknum ");
			sb.append(((Incident_Claimcheck) claimchecks.get(i)).getClaimchecknum());
		}
		sb.append(" :::recordlocator " + recordlocator);
		for (int i = 0; i < items.size(); i++) {
			sb.append(" :::name" + (i + 3) + " ");
			sb.append(((Item) items.get(i)).getLnameonbag() + ", ");
			sb.append(((Item) items.get(i)).getFnameonbag() + " ");
			sb.append(((Item) items.get(i)).getMnameonbag());
		}

		for (int i = 0; i < itineraries.size(); i++) {
			sb.append(" :::itinerary" + i + " ");
			sb.append(((Itinerary) itineraries.get(i)).getLegfrom() + ", ");
			sb.append(((Itinerary) itineraries.get(i)).getLegto() + ", ");
			sb.append(((Itinerary) itineraries.get(i)).getDepartdate() + ", ");
			sb.append(((Itinerary) itineraries.get(i)).getArrivedate() + ", ");
			sb.append(((Itinerary) itineraries.get(i)).getFlightnum() + ", ");
			sb.append(((Itinerary) itineraries.get(i)).getSchdeparttime() + ", ");
			sb.append(((Itinerary) itineraries.get(i)).getScharrivetime() + ", ");
			sb.append(((Itinerary) itineraries.get(i)).getActdeparttime() + ", ");
			sb.append(((Itinerary) itineraries.get(i)).getActarrivetime() + ", ");
		}

		return sb.toString();
	}

	/**
	 * @return Returns the createdate.
	 */
	public Date getCreatedate() {
		return createdate;
	}

	/**
	 * @param createdate
	 *          The createdate to set.
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
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
	 * @return Returns the items.
	 */
	public ArrayList getItems() {
		return items;
	}

	/**
	 * @param items
	 *          The items to set.
	 */
	public void setItems(ArrayList items) {
		this.items = items;
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
	 * @return Returns the claimchecks.
	 */
	public ArrayList getClaimchecks() {
		return claimchecks;
	}

	/**
	 * @param claimchecks
	 *          The claimchecks to set.
	 */
	public void setClaimchecks(ArrayList claimchecks) {
		this.claimchecks = claimchecks;
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
	 * @return Returns the memberships.
	 */
	public ArrayList getMemberships() {
		return memberships;
	}

	/**
	 * @param memberships
	 *          The memberships to set.
	 */
	public void setMemberships(ArrayList memberships) {
		this.memberships = memberships;
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
}