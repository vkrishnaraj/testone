package com.bagnet.nettracer.ws.core.pojo;



public class WS_PVIncident {
	
	private String firstname;
	private String middlename;
	private String lastname;
	private String homephone;
	private String workphone;
	private String mobile;
	private String hotel;
	private String email;
	private String Incident_ID;
	private String dispcreatetime;
	private String incident_status;
	
	private WS_PVItem[] items = null;
	
	private String companycode_id;
	private String errorcode;
	
	
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
	 * @return the homephone
	 */
	public String getHomephone() {
		return homephone;
	}

	/**
	 * @param homephone the homephone to set
	 */
	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	/**
	 * @return the workphone
	 */
	public String getWorkphone() {
		return workphone;
	}

	/**
	 * @param workphone the workphone to set
	 */
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the hotel
	 */
	public String getHotel() {
		return hotel;
	}

	/**
	 * @param hotel the hotel to set
	 */
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

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
}
