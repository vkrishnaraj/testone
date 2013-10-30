package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.ItemTypeBMO;
import com.bagnet.nettracer.tracing.bmo.ManufacturerBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.bmo.XDescElementsBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.ItemType;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for searching incident reports.
 */
public final class SearchIncidentForm extends ValidatorForm {

	private String incident_ID = "";
	private int itemType_ID;
	private String s_createtime = "";
	private String e_createtime = "";
	private String agent = ""; // create agent username

	private int stationcreated_ID;
	private int stationassigned_ID;
	private String agentassigned = "";
	private String companycreated_ID = "";
	private String ticketnumber = "";
	private String airline = ""; // also for custom query
	private String flightnum = ""; // also for custom query
	private String claimchecknum = ""; // also for custom query
	private String firstname = ""; // also for custom query
	private String middlename = ""; // also for custom query
	private String lastname = ""; // also for custom query
	private String companycode_ID = ""; // also for custom query
	private String membershipnum = ""; // also for custom query
	private int status_ID;
	private int[] status_IDs;

	// custom query on top of search incident
	private String recordlocator = "";
	private String email = "";
	private String address1 = "";
	private String address2 = "";
	private String city = "";
	private String state_ID = "";
	private String zip = "";
	private String province = "";
	private String phone = "";
	private String countrycode_ID = "";

	private int manufacturer_ID;
	private String manufacturer_other = "";
	private String color = "";
	private String bagtype = "";
	private int xdescelement_ID1;
	private int xdescelement_ID2;
	private int xdescelement_ID3;
	private int category_ID;
	private String description = "";
	private int noAssignedAgent;
	private String ohd_id = "";
	private String wt_id = "";
	private boolean wtConditionOr;
	private boolean intelligentTagSearch;
	private int intelligentTagSearchType;
	private String claimchecknum2;
	
	private String s_station_assignment_time = "";
	private String e_station_assignment_time = "";
	private int assigned2StationWithin24hrs;
	
	private String expediteTagNum = "";
	private String posId = "";
	private boolean djReport=false;
	
	public int getAssigned2StationWithin24hrs() {
		return assigned2StationWithin24hrs;
	}

	public void setAssigned2StationWithin24hrs(int assigned2StationWithin24hrs) {
		this.assigned2StationWithin24hrs = assigned2StationWithin24hrs;
	}

	public String getS_station_assignment_time() {
		return s_station_assignment_time;
	}

	public void setS_station_assignment_time(String s_station_assignment_time) {
		this.s_station_assignment_time = s_station_assignment_time;
	}

	public String getE_station_assignment_time() {
		return e_station_assignment_time;
	}

	public void setE_station_assignment_time(String e_station_assignment_time) {
		this.e_station_assignment_time = e_station_assignment_time;
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
	 * @return Returns the companycode_ID.
	 */
	public String getCompanycode_ID() {
		return companycode_ID;
	}

	/**
	 * @param companycode_ID
	 *          The companycode_ID to set.
	 */
	public void setCompanycode_ID(String companycode_ID) {
		this.companycode_ID = companycode_ID;
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
	 * @return Returns the incident_ID.
	 */
	public String getIncident_ID() {
		return incident_ID;
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

	/**
	 * @return Returns the companycreated_ID.
	 */
	public String getCompanycreated_ID() {
		return companycreated_ID;
	}

	/**
	 * @param companycreated_ID
	 *          The companycreated_ID to set.
	 */
	public void setCompanycreated_ID(String companycreated_ID) {
		this.companycreated_ID = companycreated_ID;
	}



	/**
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param altphone
	 *          The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * @return Returns the city.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *          The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
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
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *          The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return Returns the manufacturer_other.
	 */
	public String getManufacturer_other() {
		return manufacturer_other;
	}

	/**
	 * @param manufacturer_other
	 *          The manufacturer_other to set.
	 */
	public void setManufacturer_other(String manufacturer_other) {
		this.manufacturer_other = manufacturer_other;
	}

	/**
	 * @return Returns the province.
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *          The province to set.
	 */
	public void setProvince(String province) {
		this.province = province;
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
	 * @return Returns the state_ID.
	 */
	public String getState_ID() {
		return state_ID;
	}

	/**
	 * @param state_ID
	 *          The state_ID to set.
	 */
	public void setState_ID(String state_ID) {
		this.state_ID = state_ID;
	}

	/**
	 * @return Returns the xdescelement_ID1.
	 */
	public int getXdescelement_ID1() {
		return xdescelement_ID1;
	}

	/**
	 * @param xdescelement_ID1
	 *          The xdescelement_ID1 to set.
	 */
	public void setXdescelement_ID1(int xdescelement_ID1) {
		this.xdescelement_ID1 = xdescelement_ID1;
	}

	/**
	 * @return Returns the xdescelement_ID2.
	 */
	public int getXdescelement_ID2() {
		return xdescelement_ID2;
	}

	/**
	 * @param xdescelement_ID2
	 *          The xdescelement_ID2 to set.
	 */
	public void setXdescelement_ID2(int xdescelement_ID2) {
		this.xdescelement_ID2 = xdescelement_ID2;
	}

	/**
	 * @return Returns the xdescelement_ID3.
	 */
	public int getXdescelement_ID3() {
		return xdescelement_ID3;
	}

	/**
	 * @param xdescelement_ID3
	 *          The xdescelement_ID3 to set.
	 */
	public void setXdescelement_ID3(int xdescelement_ID3) {
		this.xdescelement_ID3 = xdescelement_ID3;
	}

	/**
	 * @return Returns the zip.
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip
	 *          The zip to set.
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return Returns the category_ID.
	 */
	public int getCategory_ID() {
		return category_ID;
	}

	/**
	 * @param category_ID
	 *          The category_ID to set.
	 */
	public void setCategory_ID(int category_ID) {
		this.category_ID = category_ID;
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCountrycode_ID() {
		return countrycode_ID;
	}

	public void setCountrycode_ID(String countrycode_ID) {
		this.countrycode_ID = countrycode_ID;
	}
	
	/**
	 * @return Visual representation of manufacturer information.
	 */
	public String getManufacturerString() {
		if (manufacturer_other != null) {
			return manufacturer_other;
		}
		
		return ManufacturerBMO.getManufacturerString(this.getManufacturer_ID());
	}
	
	/**
	 * @return Visual representation of status information.
	 */
	public String getStatusString() {
		return StatusBMO.getStatus(status_ID).getTextDescription(null);
	}
	
	/**
	 * @return Visual representation of create station information.
	 */
	public String getStationCreatedString() {
		return StationBMO.getStation(stationcreated_ID).getStationcode();
	}
	
	/**
	 * @return Visual representation of assigned station information.
	 */
	public String getStationAssignedString() {
		return StationBMO.getStation(stationassigned_ID).getStationcode();
	}
	
	/**
	 * @return Visual representation of category information.
	 */
	public String getCategoryString() {
		return CategoryBMO.getCategory(category_ID, null).getDescription();
	}
	
	/**
	 * @return Visual representation of item type information.
	 */
	public String getItemTypeString() {
		if (itemType_ID > 0) {
			//return ItemTypeBMO.getItemType(itemType_ID).getDescription();
			return ItemTypeBMO.getItemType(itemType_ID).getKey();
		}
		return null;
	}
	
	/**
	 * @return Visual representation of descriptive elements information.
	 */
	public String getDescElementsString() {
		StringBuffer retValue = new StringBuffer();
		if (xdescelement_ID1 > 0) {
			retValue.append(XDescElementsBMO.getXdescelementcode(xdescelement_ID1));
		}
		
		if (xdescelement_ID2 > 0) {
			retValue.append(XDescElementsBMO.getXdescelementcode(xdescelement_ID2));
		}
		
		if (xdescelement_ID3 > 0) {
			retValue.append(XDescElementsBMO.getXdescelementcode(xdescelement_ID3));
		}
		return retValue.toString();
	}

	public String getOhd_id() {
		return ohd_id;
	}

	public void setOhd_id(String ohd_id) {
		this.ohd_id = ohd_id;
	}

	public String getWt_id() {
		return wt_id;
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
		return claimchecknum2;
	}

	public void setClaimchecknum2(String claimchecknum2) {
		this.claimchecknum2 = claimchecknum2;
	}

	public String getExpediteTagNum() {
		return expediteTagNum;
	}

	public void setExpediteTagNum(String expediteTagNum) {
		this.expediteTagNum = expediteTagNum;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public boolean isDjReport() {
		return djReport;
	}

	public void setDjReport(boolean djReport) {
		this.djReport = djReport;
	}
	
}
