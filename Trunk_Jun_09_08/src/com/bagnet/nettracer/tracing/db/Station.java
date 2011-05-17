package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Station"
 * @hibernate.cache usage="read-write"
 */
public class Station implements Serializable {

	private int Station_ID;
	private Company company;
	private int lz;
	private String stationcode;
	private String stationdesc;

	private String address1;
	private String address2;
	private String city;
	private String state_ID;
	private String countrycode_ID;
	private String zip;
	private String phone;
	private String airport_location;
	private String operation_hours;
	private String associated_airport;

	
	private String station_region;
	private String station_region_mgr;
	private double goal;
	
	private boolean active;
	private String wt_stationcode;
	private String emailLanguage;
	
	private int priority;

	/**
	 * @hibernate.property type="string"
	 * @return the wt_stationcode
	 */
	public String getWt_stationcode() {
		return wt_stationcode;
	}

	/**
	 * @param wt_stationcode the wt_stationcode to set
	 */
	public void setWt_stationcode(String wt_stationcode) {
		this.wt_stationcode = wt_stationcode;
	}

	/**
	 * @hibernate.property type="integer"
	 *                         
	 * @return Returns the station's incident LZ station.
	 */
	public int getLz_ID() {
		return lz;
	}
	
	/**
	 * @param lz The lz to set.
	 */
	public void setLz_ID(int lz) {
		this.lz = lz;
	}
	
	
	
	
	/**
	 * @hibernate.property type="string"
	 * @return Returns the countrycode_ID.
	 */
	public String getCountrycode_ID() {
		return (countrycode_ID == null || countrycode_ID.length() ==  0 ? PropertyBMO.getValue(PropertyBMO.PROPERTY_DEFAULT_COUNTRY) : countrycode_ID);
	}

	/**
	 * @param countrycode_ID
	 *          The countrycode_ID to set.
	 */
	public void setCountrycode_ID(String countrycode_ID) {
		this.countrycode_ID = countrycode_ID;
	}
	
	/**
	 * @hibernate.property type="string"
	 * 
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
	 * @hibernate.property type="string"
	 * @return Returns the airport_location.
	 */
	public String getAirport_location() {
		return airport_location;
	}

	/**
	 * @param airport_location
	 *          The airport_location to set.
	 */
	public void setAirport_location(String airport_location) {
		this.airport_location = airport_location;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the operation_hours.
	 */
	public String getOperation_hours() {
		return operation_hours;
	}

	/**
	 * @param operation_hours
	 *          The operation_hours to set.
	 */
	public void setOperation_hours(String operation_hours) {
		this.operation_hours = operation_hours;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the address1.
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1
	 *          The address1 to set.
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the address2.
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2
	 *          The address2 to set.
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *          The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the associated_airport.
	 */
	public String getAssociated_airport() {
		return associated_airport;
	}

	/**
	 * @param associated_airport
	 *          The associated_airport to set.
	 */
	public void setAssociated_airport(String associated_airport) {
		this.associated_airport = associated_airport;
	}

	/**
	 * @hibernate.property type="string"
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Company"
	 *                        column="companycode_ID"  fetch="select"
	 * @return Returns the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *          The company to set.
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	
	/**
	 * @return Returns the station_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="Station_ID"
	 * 
	 * @hibernate.generator-param name="sequence" value="Station_0"
	 *  
	 */
	public int getStation_ID() {
		return Station_ID;
	}

	/**
	 * @param station_ID
	 *          The station_ID to set.
	 */
	public void setStation_ID(int station_ID) {
		Station_ID = station_ID;
	}

	/**
	 * @return Returns the stationcode.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getStationcode() {
		return stationcode;
	}

	/**
	 * @param stationcode
	 *          The stationcode to set.
	 */
	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}

	/**
	 * @return Returns the stationdesc.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getStationdesc() {
		return stationdesc;
	}

	/**
	 * @param stationdesc
	 *          The stationdesc to set.
	 */
	public void setStationdesc(String stationdesc) {
		this.stationdesc = stationdesc;
	}

	
	/**
	 * @return Returns the station_region.
	 * 
	 * @hibernate.property type="string" length="100"
	 */
	public String getStation_region() {
		return station_region;
	}
	/**
	 * @param station_region The station_region to set.
	 */
	public void setStation_region(String station_region) {
		this.station_region = station_region;
	}

	/**
	 * @return Returns the station_region_mgr.
	 * 
	 * @hibernate.property type="string" length="100"
	 */
	public String getStation_region_mgr() {
		return station_region_mgr;
	}
	/**
	 * @param station_region_mgr The station_region_mgr to set.
	 */
	public void setStation_region_mgr(String station_region_mgr) {
		this.station_region_mgr = station_region_mgr;
	}
	/**
	 * @return Returns the goal.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getGoal() {
		return goal;
	}
	/**
	 * @param goal The goal to set.
	 */
	public void setGoal(double goal) {
		this.goal = goal;
	}
	
	


	/**
	 * @return Returns the active.
	 * 
	 * @hibernate.property type="boolean"
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *          The active to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer(200);
		sb.append("Station ID=" + this.getStation_ID() + " station code=" + this.getStationcode()
				+ " station desc=" + this.getStationdesc());
		return sb.toString();
	}

	public String toXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<station>");
		sb.append("<Station_ID>" + Station_ID + "</Station_ID>");
		sb.append("<companycode>" + this.getCompany().getCompanyCode_ID() + "</companycode>");
		sb.append("<stationcode>" + this.getStationcode() + "</stationcode>");
		sb.append("</station>");

		return sb.toString();
	}
	
	public boolean isThisOhdLz() {
		return (this.Station_ID == this.getCompany().getVariable().getOhd_lz());
	}

	/**
	 * @hibernate.property type="string" length="3" column="email_language"
	 */
	public String getEmailLanguage() {
		// TODO Auto-generated method stub
		return emailLanguage;
	}

	public void setEmailLanguage(String emailLanguage) {
		this.emailLanguage = emailLanguage;
	}
	
	/**
	 * @hibernate.property type="integer"
	 * 
	 * @param priority
	 *          The priority to set.
	 */
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}