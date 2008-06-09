package com.bagnet.nettracer.ws.core.pojo;



public class WS_OHD {

	private String webservice_session_ID;
	private String username;
	
	private String OHD_ID;
	private String status;
	private String foundAtStation;
	private String holdingStation;
	private String storage_location;
	private String agent;
	private String founddatetime;
	private String bagarrivedate;
	private String bagtagnum;
	private String color;
	private String type;
	private String lastname;
	private String firstname;
	private String middlename;
	private String membership;
	private String record_locator;
	private String xdescelement1;
	private String xdescelement2;
	private String xdescelement3;
	private String manufacturer;
	private String disposal_status;
	private String close_date;
	
	private WS_Passenger[] passengers = null;
	private WS_Inventory[] inventories = null;
	private WS_Itinerary[] itineraries = null;

	
	private String companycode_id;
	private String errorcode;
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the webservice_session_ID
	 */
	public String getWebservice_session_ID() {
		return webservice_session_ID;
	}
	/**
	 * @param webservice_session_ID the webservice_session_ID to set
	 */
	public void setWebservice_session_ID(String webservice_session_ID) {
		this.webservice_session_ID = webservice_session_ID;
	}
	
	
	/**
	 * @return the oHD_ID
	 */
	public String getOHD_ID() {
		return OHD_ID;
	}
	/**
	 * @param ohd_id the oHD_ID to set
	 */
	public void setOHD_ID(String ohd_id) {
		OHD_ID = ohd_id;
	}
	/**
	 * @return the foundAtStation
	 */
	public String getFoundAtStation() {
		return foundAtStation;
	}
	/**
	 * @param foundAtStation the foundAtStation to set
	 */
	public void setFoundAtStation(String foundAtStation) {
		this.foundAtStation = foundAtStation;
	}
	/**
	 * @return the holdingStation
	 */
	public String getHoldingStation() {
		return holdingStation;
	}
	/**
	 * @param holdingStation the holdingStation to set
	 */
	public void setHoldingStation(String holdingStation) {
		this.holdingStation = holdingStation;
	}
	/**
	 * @return the storage_location
	 */
	public String getStorage_location() {
		return storage_location;
	}
	/**
	 * @param storage_location the storage_location to set
	 */
	public void setStorage_location(String storage_location) {
		this.storage_location = storage_location;
	}
	/**
	 * @return the agent
	 */
	public String getAgent() {
		return agent;
	}
	/**
	 * @param agent the agent to set
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}
	/**
	 * @return the founddatetime
	 */
	public String getFounddatetime() {
		return founddatetime;
	}
	/**
	 * @param founddatetime the founddatetime to set
	 */
	public void setFounddatetime(String founddatetime) {
		this.founddatetime = founddatetime;
	}
	/**
	 * @return the bagarrivedate
	 */
	public String getBagarrivedate() {
		return bagarrivedate;
	}
	/**
	 * @param bagarrivedate the bagarrivedate to set
	 */
	public void setBagarrivedate(String bagarrivedate) {
		this.bagarrivedate = bagarrivedate;
	}
	/**
	 * @return the bagtagnum
	 */
	public String getBagtagnum() {
		return bagtagnum;
	}
	/**
	 * @param bagtagnum the bagtagnum to set
	 */
	public void setBagtagnum(String bagtagnum) {
		this.bagtagnum = bagtagnum;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the middlename
	 */
	public String getMiddlename() {
		return middlename;
	}
	/**
	 * @param middlename the middlename to set
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	/**
	 * @return the membership
	 */
	public String getMembership() {
		return membership;
	}
	/**
	 * @param membership the membership to set
	 */
	public void setMembership(String membership) {
		this.membership = membership;
	}
	/**
	 * @return the record_locator
	 */
	public String getRecord_locator() {
		return record_locator;
	}
	/**
	 * @param record_locator the record_locator to set
	 */
	public void setRecord_locator(String record_locator) {
		this.record_locator = record_locator;
	}


	/**
	 * @return the xdescelement1
	 */
	public String getXdescelement1() {
		return xdescelement1;
	}
	/**
	 * @param xdescelement1 the xdescelement1 to set
	 */
	public void setXdescelement1(String xdescelement1) {
		this.xdescelement1 = xdescelement1;
	}
	/**
	 * @return the xdescelement2
	 */
	public String getXdescelement2() {
		return xdescelement2;
	}
	/**
	 * @param xdescelement2 the xdescelement2 to set
	 */
	public void setXdescelement2(String xdescelement2) {
		this.xdescelement2 = xdescelement2;
	}
	/**
	 * @return the xdescelement3
	 */
	public String getXdescelement3() {
		return xdescelement3;
	}
	/**
	 * @param xdescelement3 the xdescelement3 to set
	 */
	public void setXdescelement3(String xdescelement3) {
		this.xdescelement3 = xdescelement3;
	}
	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	/**
	 * @return the inventories
	 */
	public WS_Inventory[] getInventories() {
		return inventories;
	}
	/**
	 * @param inventories the inventories to set
	 */
	public void setInventories(WS_Inventory[] inventories) {
		this.inventories = inventories;
	}
	/**
	 * @return the itineraries
	 */
	public WS_Itinerary[] getItineraries() {
		return itineraries;
	}
	/**
	 * @param itineraries the itineraries to set
	 */
	public void setItineraries(WS_Itinerary[] itineraries) {
		this.itineraries = itineraries;
	}
	/**
	 * @return the passengers
	 */
	public WS_Passenger[] getPassengers() {
		return passengers;
	}
	/**
	 * @param passengers the passengers to set
	 */
	public void setPassengers(WS_Passenger[] passengers) {
		this.passengers = passengers;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the disposal_status
	 */
	public String getDisposal_status() {
		return disposal_status;
	}
	/**
	 * @param disposal_status the disposal_status to set
	 */
	public void setDisposal_status(String disposal_status) {
		this.disposal_status = disposal_status;
	}
	/**
	 * @return the close_date
	 */
	public String getClose_date() {
		return close_date;
	}
	/**
	 * @param close_date the close_date to set
	 */
	public void setClose_date(String close_date) {
		this.close_date = close_date;
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
	
	
}
