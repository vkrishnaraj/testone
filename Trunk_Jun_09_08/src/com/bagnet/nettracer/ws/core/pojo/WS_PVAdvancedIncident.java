package com.bagnet.nettracer.ws.core.pojo;

import java.util.Calendar;



/**
 * @author byron
 *
 */
public class WS_PVAdvancedIncident {
	
	private String Incident_ID;
	private String dispcreatetime;
	private String incident_status;
	private int itemType;
	private Calendar createdate;
	private Calendar closedate;
	private boolean canCreateClaim;
	
	private WS_PVPassenger[] passengers = null;
	private WS_PVItem[] items = null;
	private WS_ClaimCheck[] checks = null;

	private String companycode_id;
	private String errorcode;
	private String comments;
	

	/**
	 * @return the incident_ID
	 */
	public String getIncident_ID() {
		return Incident_ID;
	}

	/**
	 * @param incident_ID the incident_ID to set
	 */
	public void setIncident_ID(String incident_ID) {
		Incident_ID = incident_ID;
	}

	/**
	 * @return the dispcreatetime
	 */
	public String getDispcreatetime() {
		return dispcreatetime;
	}

	/**
	 * @param dispcreatetime the dispcreatetime to set
	 */
	public void setDispcreatetime(String dispcreatetime) {
		this.dispcreatetime = dispcreatetime;
	}

	/**
	 * @return the incident_status
	 */
	public String getIncident_status() {
		return incident_status;
	}

	/**
	 * @param incident_status the incident_status to set
	 */
	public void setIncident_status(String incident_status) {
		this.incident_status = incident_status;
	}

	
	/**
	 * @return the companycode_id
	 */
	public String getCompanycode_id() {
		return companycode_id;
	}

	/**
	 * @param companycode_id the companycode_id to set
	 */
	public void setCompanycode_id(String companycode_id) {
		this.companycode_id = companycode_id;
	}

	/**
	 * @return the errorcode
	 */
	public String getErrorcode() {
		return errorcode;
	}

	/**
	 * @param errorcode the errorcode to set
	 */
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	

	/**
	 * @return the items
	 */
	public WS_PVItem[] getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(WS_PVItem[] items) {
		this.items = items;
	}
	
	/**
	 * @return the passengers
	 */
	public WS_PVPassenger[] getPassengers() {
		return passengers;
	}

	/**
	 * @param passengers the passengers to set
	 */
	public void setPassengers(WS_PVPassenger[] passengers) {
		this.passengers = passengers;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public Calendar getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Calendar createdate) {
		this.createdate = createdate;
	}

	public boolean isCanCreateClaim() {
		return canCreateClaim;
	}

	public void setCanCreateClaim(boolean canCreateClaim) {
		this.canCreateClaim = canCreateClaim;
	}
	
	public WS_ClaimCheck[] getClaimChecks() {
		return checks;
	}

	public void setClaimChecks(WS_ClaimCheck[] checks) {
		this.checks = checks;
	}

	public Calendar getClosedate() {
		return closedate;
	}

	public void setClosedate(Calendar closedate) {
		this.closedate = closedate;
	}
	
}
