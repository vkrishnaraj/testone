package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Audit_Station"
 */
public class Audit_Station implements Serializable {

	private int audit_station_id;
	private int Station_ID;
	private String companycode_ID;
	private String stationcode;
	private String stationdesc;
	private String locale;
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
	
	private Agent modifying_agent;
	private Date time_modified;
	private String reason_modified;

	
	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE; // timezone

	/**
	 * @return Returns the _DATEFORMAT.
	 */
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	/**
	 * @return Returns the _TIMEFORMAT.
	 */
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	/**
	 * @param _timeformat
	 *          The _TIMEFORMAT to set.
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	/**
	 * @return Returns the _TIMEZONE.
	 */
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	/**
	 * @param _timezone
	 *          The _TIMEZONE to set.
	 */
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}

	public String getDisplaytime_modified() {
		Date completedate = DateUtils.convertToDate(this.getTime_modified().toString(),
				TracingConstants.DB_DATETIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="modifying_agent_ID"
	 * @return Returns the modifying_agent.
	 */
	public Agent getModifying_agent() {
		return modifying_agent;
	}

	public String getState() {
		if (state_ID != null && state_ID.length() > 0) {
			return TracerUtils.getState(state_ID, "en").getState();
		}
		return "";
	}

	public String getCountry() {
		if (countrycode_ID != null && countrycode_ID.length() > 0) {
			return TracerUtils.getCountry(countrycode_ID, "en").getCountry();
		}
		return "";
	}

	/**
	 * @param modifying_agent
	 *          The modifying_agent to set.
	 */
	public void setModifying_agent(Agent modifying_agent) {
		this.modifying_agent = modifying_agent;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the reason_modified.
	 */
	public String getReason_modified() {
		return reason_modified;
	}

	/**
	 * @param reason_modified
	 *          The reason_modified to set.
	 */
	public void setReason_modified(String reason_modified) {
		this.reason_modified = reason_modified;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the time_modified.
	 */
	public Date getTime_modified() {
		return time_modified;
	}

	/**
	 * @param time_modified
	 *          The time_modified to set.
	 */
	public void setTime_modified(Date time_modified) {
		this.time_modified = time_modified;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the countrycode_ID.
	 */
	public String getCountrycode_ID() {
		return countrycode_ID;
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
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="audit_station_id"
	 * 
	 * @hibernate.generator-param name="sequence" value="audit_station_0"
	 * 
	 * 
	 * 
	 * 
	 * @return Returns the audit_station_id.
	 */
	public int getAudit_station_id() {
		return audit_station_id;
	}

	/**
	 * @param audit_station_id
	 *          The audit_station_id to set.
	 */
	public void setAudit_station_id(int audit_station_id) {
		this.audit_station_id = audit_station_id;
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
	 * @return Returns the locale.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *          The locale to set.
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return Returns the station_ID.
	 * 
	 * @hibernate.property type="integer"
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
	 * @hibernate.property type="string"
	 * 
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


	
}