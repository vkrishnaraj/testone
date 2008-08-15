package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public final class SearchOnHandFormX extends ValidatorForm {

	private String ohd_ID = ""; // DIFFERENT (incident_ID)
	private String status_ID = "";
	private String ticketnumber = "";
	private int OHD_categorytype_ID; // DIFFERENT
	private String description = "";
	private String s_createtime = "";
	private String e_createtime = "";
	private String airline = "";
	private String flightnum = "";
	private String firstname = "";
	private String middlename = "";
	private String lastname = "";
	private String foundStation = ""; // DIFFERENT (stationcreated_ID)
	private String heldStation = ""; // DIFFERENT (stationassigned_ID)
	private String foundCompany = ""; // DIFFERENT (companycreated_ID)
	private String heldCompany = ""; // DIFFERENT (companycode_ID)
	private String agent; // 

	/**
	 * @return Returns the agent.
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * @param agent
	 *          The agent to set.
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *          The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the e_createtime.
	 */
	public String getE_createtime() {
		return e_createtime;
	}

	/**
	 * @param e_createtime
	 *          The e_createtime to set.
	 */
	public void setE_createtime(String e_createtime) {
		this.e_createtime = e_createtime;
	}

	/**
	 * @return Returns the firstname.
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
	 * @return Returns the airline.
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * @param airline
	 *          The airline to set.
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}
	
	/**
	 * @return Returns the flightnum.
	 */
	public String getFlightnum() {
		return flightnum;
	}

	/**
	 * @param flightnum
	 *          The flightnum to set.
	 */
	public void setFlightnum(String flightnum) {
		this.flightnum = flightnum;
	}

	/**
	 * @return Returns the foundCompany.
	 */
	public String getCompanycreated_ID() {
		return foundCompany;
	}

	/**
	 * @param foundCompany
	 *          The foundCompany to set.
	 */
	public void setCompanycreated_ID(String foundCompany) {
		this.foundCompany = foundCompany;
	}

	/**
	 * @return Returns the foundStation.
	 */
	public String getStationcreated_ID() {
		return foundStation;
	}

	/**
	 * @param foundStation
	 *          The foundStation to set.
	 */
	public void setStationcreated_ID(String foundStation) {
		this.foundStation = foundStation;
	}

	/**
	 * @return Returns the heldCompany.
	 */
	public String getCompanycode_ID() {
		return heldCompany;
	}

	/**
	 * @param heldCompany
	 *          The heldCompany to set.
	 */
	public void setCompanycode_ID(String heldCompany) {
		this.heldCompany = heldCompany;
	}

	/**
	 * @return Returns the heldStation.
	 */
	public String getStationassigned_ID() {
		return heldStation;
	}

	/**
	 * @param heldStation
	 *          The heldStation to set.
	 */
	public void setStationassigned_ID(String heldStation) {
		this.heldStation = heldStation;
	}

	/**
	 * @return Returns the lastname.
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
	 * @return Returns the oHD_categorytype_ID.
	 */
	public int getCategory_ID() {
		return OHD_categorytype_ID;
	}

	/**
	 * @param ohd_categorytype_id
	 *          The oHD_categorytype_ID to set.
	 */
	public void setCategory_ID(int ohd_categorytype_id) {
		OHD_categorytype_ID = ohd_categorytype_id;
	}

	/**
	 * @return Returns the ohd_ID.
	 */
	public String getIncident_ID() {
		return ohd_ID;
	}

	/**
	 * @param ohd_ID
	 *          The ohd_ID to set.
	 */
	public void setIncident_ID(String ohd_ID) {
		this.ohd_ID = ohd_ID;
	}

	/**
	 * @return Returns the s_createtime.
	 */
	public String getS_createtime() {
		return s_createtime;
	}

	/**
	 * @param s_createtime
	 *          The s_createtime to set.
	 */
	public void setS_createtime(String s_createtime) {
		this.s_createtime = s_createtime;
	}

	/**
	 * @return Returns the status_ID.
	 */
	public String getStatus_ID() {
		return status_ID;
	}

	/**
	 * @param status_ID
	 *          The status_ID to set.
	 */
	public void setStatus_ID(String status_ID) {
		this.status_ID = status_ID;
	}

	/**
	 * @return Returns the ticketnumber.
	 */
	public String getTicketnumber() {
		return ticketnumber;
	}

	/**
	 * @param ticketnumber
	 *          The ticketnumber to set.
	 */
	public void setTicketnumber(String ticketnumber) {
		this.ticketnumber = ticketnumber;
	}
}