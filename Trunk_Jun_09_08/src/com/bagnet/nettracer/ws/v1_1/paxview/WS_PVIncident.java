package com.bagnet.nettracer.ws.v1_1.paxview;

/**
 * @author byron
 * 
 */
public class WS_PVIncident {

	private String comments;
	private String dispcreatetime;
	private String errorcode;
	private String incident_ID;
	private String incident_status;
	private String itemType;
	public String getItemType() {
		return itemType;
	}


	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	private WS_PVItem[] items = null;
	private WS_PVPassenger[] passengers = null;
	private WS_PVPaxCommunication[] paxCommunication = null;
	private WS_PVClaimChecks[] paxClaimchecks = null;

	public WS_PVClaimChecks[] getPaxClaimchecks() {
		return paxClaimchecks;
	}


	public void setPaxClaimchecks(WS_PVClaimChecks[] paxClaimchecks) {
		this.paxClaimchecks = paxClaimchecks;
	}


	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}


	/**
	 * @return the dispcreatetime
	 */
	public String getDispcreatetime() {
		return dispcreatetime;
	}

	/**
	 * @return the errorcode
	 */
	public String getErrorcode() {
		return errorcode;
	}

	public String getIncident_ID() {
		return incident_ID;
	}

	/**
	 * @return the incident_status
	 */
	public String getIncident_status() {
		return incident_status;
	}

	/**
	 * @return the items
	 */
	public WS_PVItem[] getItems() {
		return items;
	}

	/**
	 * @return the passengers
	 */
	public WS_PVPassenger[] getPassengers() {
		return passengers;
	}

	public WS_PVPaxCommunication[] getPaxCommunication() {
		return paxCommunication;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @param dispcreatetime
	 *            the dispcreatetime to set
	 */
	public void setDispcreatetime(String dispcreatetime) {
		this.dispcreatetime = dispcreatetime;
	}

	/**
	 * @param errorcode
	 *            the errorcode to set
	 */
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}

	/**
	 * @param incident_status
	 *            the incident_status to set
	 */
	public void setIncident_status(String incident_status) {
		this.incident_status = incident_status;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(WS_PVItem[] items) {
		this.items = items;
	}

	/**
	 * @param passengers
	 *            the passengers to set
	 */
	public void setPassengers(WS_PVPassenger[] passengers) {
		this.passengers = passengers;
	}

	public void setPaxCommunication(WS_PVPaxCommunication[] paxCommunication) {
		this.paxCommunication = paxCommunication;
	}
}
