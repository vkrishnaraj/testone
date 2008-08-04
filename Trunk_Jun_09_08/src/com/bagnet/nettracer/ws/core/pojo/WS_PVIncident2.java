package com.bagnet.nettracer.ws.core.pojo;



public class WS_PVIncident2 {
	
	private String Incident_ID;
	private String dispcreatetime;
	private String incident_status;
	
	private WS_PVPassenger[] passengers = null;
	private WS_PVItem[] items = null;
	
	private String companycode_id;
	private String errorcode;
	

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
	 * @param items the items to set
	 */
	public void setItems(WS_PVPassenger[] passengers) {
		this.passengers = passengers;
	}
}
