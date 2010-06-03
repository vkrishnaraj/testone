/*
 * Created on Jul 30, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.dto;

/**
 * @author Administrator
 * 
 * create date - Jul 30, 2004
 */
public class SearchIncident_DTO {
	private String incident_ID;
	private String agent;
	private int itemType_ID;
	private String s_createtime;
	private String e_createtime;
	private int stationcreated_ID;
	private int stationassigned_ID;
	private String agentassigned;
	private String companycreated_ID;
	private String companyfaulted_ID;
	private int faultstation_ID;
	private String ticketnumber;
	private String airline;
	private String flightnum;
	private String claimchecknum;
	private String claimchecknum2;
	private String firstname;
	private String middlename;
	private String lastname;
	private String companycode_ID;
	private String membershipnum;
	private int status_ID;
	private int[] status_IDs;	// used for search incoming incidents
	private int noAssignedAgent;
	private String recordlocator;
	private String wt_id;
	private boolean wtConditionOr;
	private boolean intelligentTagSearch;
	private int intelligentTagSearchType;

	private String s_station_assignment_time = "";
	private String e_station_assignment_time = "";
	private int assigned2StationWithin24hrs;
	
	
	
	public int getAssigned2StationWithin24hrs() {
		return assigned2StationWithin24hrs;
	}

	public void setAssigned2StationWithin24hrs(int assigned2StationWithin24hrs) {
		this.assigned2StationWithin24hrs = assigned2StationWithin24hrs;
	}

	public String getS_station_assignment_time() {
		return s_station_assignment_time == null ? "" : s_station_assignment_time.trim();
	}


	public String getE_station_assignment_time() {
		return e_station_assignment_time == null ? "" : e_station_assignment_time.trim();
	}

	public void setE_station_assignment_time(String e_station_assignment_time) {
		this.e_station_assignment_time = e_station_assignment_time;
	}

	/**
	 * @return Returns the agent.
	 */
	public String getAgent() {
		return agent == null ? "" : agent.trim();
	}

	/**
	 * @param agent
	 *          The agent to set.
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @return Returns the claimchecknum.
	 */
	public String getClaimchecknum() {
		return claimchecknum == null ? "" : claimchecknum.trim();
	}

	/**
	 * @param claimchecknum
	 *          The claimchecknum to set.
	 */
	public void setClaimchecknum(String claimchecknum) {
		this.claimchecknum = claimchecknum;
	}

	/**
	 * @return Returns the comanycode_ID.
	 */
	public String getCompanycode_ID() {
		return companycode_ID == null ? "" : companycode_ID.trim();
	}

	/**
	 * @param comanycode_ID
	 *          The comanycode_ID to set.
	 */
	public void setCompanycode_ID(String comanycode_ID) {
		this.companycode_ID = comanycode_ID;
	}

	/**
	 * @return Returns the e_createtime.
	 */
	public String getE_createtime() {
		return e_createtime == null ? "" : e_createtime.trim();
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
		return firstname == null ? "" : firstname.trim();
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
		return airline == null ? "" : airline.trim();
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
		return flightnum == null ? "" : flightnum.trim();
	}

	/**
	 * @param flightnum
	 *          The flightnum to set.
	 */
	public void setFlightnum(String flightnum) {
		this.flightnum = flightnum;
	}

	/**
	 * @return Returns the incident_ID.
	 */
	public String getIncident_ID() {
		return incident_ID == null ? "" : incident_ID.trim();
	}

	/**
	 * @param incident_ID
	 *          The incident_ID to set.
	 */
	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}

	/**
	 * @return Returns the itemType_ID.
	 */
	public int getItemType_ID() {
		return itemType_ID;
	}

	/**
	 * @param itemType_ID
	 *          The itemType_ID to set.
	 */
	public void setItemType_ID(int itemType_ID) {
		this.itemType_ID = itemType_ID;
	}

	/**
	 * @return Returns the lastname.
	 */
	public String getLastname() {
		return lastname == null ? "" : lastname.trim();
	}

	/**
	 * @param lastname
	 *          The lastname to set.
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return Returns the membershipnum.
	 */
	public String getMembershipnum() {
		return membershipnum == null ? "" : membershipnum.trim();
	}

	/**
	 * @param membershipnum
	 *          The membershipnum to set.
	 */
	public void setMembershipnum(String membershipnum) {
		this.membershipnum = membershipnum;
	}

	/**
	 * @return Returns the middlename.
	 */
	public String getMiddlename() {
		return middlename == null ? "" : middlename.trim();
	}

	/**
	 * @param middlename
	 *          The middlename to set.
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	/**
	 * @return Returns the s_createtime.
	 */
	public String getS_createtime() {
		return s_createtime == null ? "" : s_createtime.trim();
	}

	/**
	 * @param s_createtime
	 *          The s_createtime to set.
	 */
	public void setS_createtime(String s_createtime) {
		this.s_createtime = s_createtime;
	}

	/**
	 * @return Returns the stationassigned_ID.
	 */
	public int getStationassigned_ID() {
		return stationassigned_ID;
	}

	/**
	 * @param stationassigned_ID
	 *          The stationassigned_ID to set.
	 */
	public void setStationassigned_ID(int stationassigned_ID) {
		this.stationassigned_ID = stationassigned_ID;
	}


	/**
	 * @return Returns the agentassigned.
	 */
	public String getAgentassigned() {
		return agentassigned;
	}
	/**
	 * @param agentassigned The agentassigned to set.
	 */
	public void setAgentassigned(String agentassigned) {
		this.agentassigned = agentassigned;
	}
	/**
	 * @return Returns the stationcreated_ID.
	 */
	public int getStationcreated_ID() {
		return stationcreated_ID;
	}

	/**
	 * @param stationcreated_ID
	 *          The stationcreated_ID to set.
	 */
	public void setStationcreated_ID(int stationcreated_ID) {
		this.stationcreated_ID = stationcreated_ID;
	}

	/**
	 * @return Returns the ticketnumber.
	 */
	public String getTicketnumber() {
		return ticketnumber == null ? "" : ticketnumber.trim();
	}

	/**
	 * @param ticketnumber
	 *          The ticketnumber to set.
	 */
	public void setTicketnumber(String ticketnumber) {
		this.ticketnumber = ticketnumber;
	}

	/**
	 * @return Returns the status_ID.
	 */
	public int getStatus_ID() {
		return status_ID;
	}

	/**
	 * @param status_ID
	 *          The status_ID to set.
	 */
	public void setStatus_ID(int status_ID) {
		this.status_ID = status_ID;
	}


	/**
	 * @return Returns the status_IDs.
	 */
	public int[] getStatus_IDs() {
		if (status_IDs != null && status_IDs.length <=0) return null;
		return status_IDs;
	} 
	/**
	 * @param status_IDs The status_IDs to set.
	 */
	public void setStatus_IDs(int[] status_IDs) {
		this.status_IDs = status_IDs;
	}

	/**
	 * @return Returns the companycreated_ID.
	 */
	public String getCompanycreated_ID() {
		return companycreated_ID == null ? "" : companycreated_ID;
	}

	/**
	 * @param companycreated_ID
	 *          The companycreated_ID to set.
	 */
	public void setCompanycreated_ID(String companycreated_ID) {
		this.companycreated_ID = companycreated_ID;
	}

	/**
	 * @return Returns the noAssignedAgent.
	 */
	public int getNoAssignedAgent() {
		return noAssignedAgent;
	}

	/**
	 * @param noAssignedAgent
	 *          The noAssignedAgent to set.
	 */
	public void setNoAssignedAgent(int noAssignedAgent) {
		this.noAssignedAgent = noAssignedAgent;
	}

	public String getRecordlocator() {
		return recordlocator;
	}

	public void setRecordlocator(String recordlocator) {
		this.recordlocator = recordlocator;
	}

	public String getCompanyfaulted_ID() {
		return companyfaulted_ID;
	}

	public void setCompanyfaulted_ID(String companyfaulted_ID) {
		this.companyfaulted_ID = companyfaulted_ID;
	}

	public int getFaultstation_ID() {
		return faultstation_ID;
	}

	public void setFaultstation_ID(int faultstation_ID) {
		this.faultstation_ID = faultstation_ID;
	}


	public String getWt_id() {
		return wt_id == null ? "" : wt_id.trim();
	}

	public void setWt_id(String wt_id) {
		this.wt_id = wt_id;
	}

	public boolean isWtConditionOr() {
		return wtConditionOr;
	}

	public void setWtConditionOr(boolean wtConditionOr) {
		this.wtConditionOr = wtConditionOr;
	}


	public boolean isIntelligentTagSearch() {
		return intelligentTagSearch;
	}

	public void setIntelligentTagSearch(boolean intelligentTagSearch) {
		this.intelligentTagSearch = intelligentTagSearch;
	}

	public int getIntelligentTagSearchType() {
		return intelligentTagSearchType;
	}

	public void setIntelligentTagSearchType(int intelligentTagSearchType) {
		this.intelligentTagSearchType = intelligentTagSearchType;
	}

	public String getClaimchecknum2() {
		return claimchecknum2 == null ? "" : claimchecknum2.trim();
	}

	public void setClaimchecknum2(String claimchecknum2) {
		this.claimchecknum2 = claimchecknum2;
	}	
	
}