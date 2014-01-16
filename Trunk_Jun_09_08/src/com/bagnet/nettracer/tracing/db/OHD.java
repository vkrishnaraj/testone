/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;

/**
 * @author Administrator
 * 
 * @hibernate.class table="OHD"
 * @hibernate.typedef name="worldTracerStatus" class="org.hibernate.type.EnumType"
 * @hibernate.typedef-param typedef-name="worldTracerStatus" name="enumClass"
 * 			value="com.bagnet.nettracer.tracing.db.WorldTracerFile$WTStatus"
 * @hibernate.typedef-param typedef-name="worldTracerStatus" name="type"
 * 			value="12"
 */
public class OHD implements Serializable {
	private static final long serialVersionUID = -6651594161173132503L;
	
	private String OHD_ID;
	private Station foundAtStation;
	private Station holdingStation;
	private String storage_location;
	private Agent agent;
	private Date foundtime;
	private Date founddate;
	private Date bagarrivedate;
	private Date inventoryDate;
	private Date warehouseSentDate;
	private Date warehouseReceivedDate;
	private String claimnum;
	private String claimchecknum_leading;
	private String claimchecknum_ticketingcode;
	private String claimchecknum_carriercode;
	private String claimchecknum_bagnumber;
	private String color;
	private String type;
	private int ohd_type;
	private String lastname;
	private String firstname;
	private String middlename;
	private AirlineMembership membership;
	private String record_locator;
	private int xdescelement_ID_1;
	private int xdescelement_ID_2;
	private int xdescelement_ID_3;
	private int manufacturer_ID;
	private String manufacturer_other;
	private Status status;
	private Status disposal_status;
	private boolean earlyBag;
	private String matched_incident;
	private String externaldesc;
	private int specialCondition;

	private Set<OHD_Inventory> items;
	private Set<Remark> remarks;
	private Set<OHD_Passenger> passengers;
	private Set<OHD_Photo> photos;
	private Set<Task> tasks;
	private Set<ControlLog> controlLog;
	private Set<OHD_Itinerary> itinerary;
	private Date close_date;
	private Date lastupdated;
	
	private WorldTracerFile wtFile;	// worldtracer id

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;

	private int faultStation = 0;
	private int loss_code = 0;
	private boolean tagSentToWt;
	private int tagSentToWtStationId;
	
	private int creationMethod;
	
	private String modifiedBy;
	private Date modifiedDate;
	private String posId;
	private boolean lateCheckInd;

	private int other;
	private boolean noAddFees;

	public String getDisplaydate() {
		Date completedate = DateUtils.convertToDate(this.getFounddate().toString() + " "
				+ this.getFoundtime().toString(), TracingConstants.DB_DATETIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}
	
	public Date getFullFoundDate() {
		return DateUtils.convertToDate(getFounddate().toString() + " " + getFoundtime().toString(), TracingConstants.DB_DATETIMEFORMAT,
				null);
	}


	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the ohd_type.
	 */
	public int getOhd_type() {
		return ohd_type;
	}

	/**
	 * @param ohd_type
	 *          The ohd_type to set.
	 */
	public void setOhd_type(int ohd_type) {
		this.ohd_type = ohd_type;
	}

	/**
	 * @hibernate.property type="date"
	 * @return Returns the bagarrivedate.
	 */
	public Date getBagarrivedate() {
		return bagarrivedate;
	}

	/**
	 * @param bagarrivedate
	 *          The bagarrivedate to set.
	 */
	public void setBagarrivedate(Date bagarrivedate) {
		this.bagarrivedate = bagarrivedate;
	}
	
	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the inventoryDate.
	 */
	public Date getInventoryDate() {
		return inventoryDate;
	}

	/**
	 * @param inventoryDate
	 * The inventoryDate to set.
	 */
	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public String getDispInventoryDate() {
		String dispInventoryDate = DateUtils.formatDate(this.getInventoryDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		return dispInventoryDate != null && 0 < dispInventoryDate.lastIndexOf(' ') ? dispInventoryDate.substring(0, dispInventoryDate.lastIndexOf(' ')) : "";
	}
	
	/**
	 * @hibernate.property type="date"
	 * @return Returns the warehouseSentDate.
	 */
	public Date getWarehouseSentDate() {
		return warehouseSentDate;
	}

	/**
	 * @param warehouseSentDate
	 *          The warehouseSentDate to set.
	 */
	public void setWarehouseSentDate(Date warehouseSentDate) {
		this.warehouseSentDate = warehouseSentDate;
	}
	
	/**
	 * @hibernate.property type="date"
	 * @return Returns the warehouseReceivedDate.
	 */
	public Date getWarehouseReceivedDate() {
		return warehouseReceivedDate;
	}

	/**
	 * @param warehouseReceivedDate
	 *          The warehouseReceivedDate to set.
	 */
	public void setWarehouseReceivedDate(Date warehouseReceivedDate) {
		this.warehouseReceivedDate = warehouseReceivedDate;
	}

	/**
	 * @return Returns the lastupdated.
	 * 
	 * @hibernate.property type="timestamp"
	 */
	public Date getLastupdated() {
		return lastupdated;
	}
	/**
	 * @param lastupdated The lastupdated to set.
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}
	
	/**
	 * @hibernate.set cascade="all" inverse="true"
	 *                order-by="itinerarytype,itinerary_ID"
	 * @hibernate.key column="ohd_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.OHD_Itinerary"
	 * 
	 * @return Returns the itinerary.
	 */
	public Set<OHD_Itinerary> getItinerary() {
		return itinerary;
	}

	/**
	 * @param itinerary
	 *          The itinerary to set.
	 */
	public void setItinerary(Set<OHD_Itinerary> itinerary) {
		this.itinerary = itinerary;
	}

	/**
	 * @return Returns the membership.
	 * @hibernate.many-to-one cascade="all"
	 *                        class="com.bagnet.nettracer.tracing.db.AirlineMembership"
	 *                        column="membership_ID"
	 *  
	 */
	public AirlineMembership getMembership() {
		return membership;
	}

	/**
	 * @param membership
	 *          The membership to set.
	 */
	public void setMembership(AirlineMembership membership) {
		this.membership = membership;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the record_locator.
	 */
	public String getRecord_locator() {
		return record_locator;
	}

	/**
	 * @param record_locator
	 *          The record_locator to set.
	 */
	public void setRecord_locator(String record_locator) {
		this.record_locator = record_locator;
	}

	/**
	 * @hibernate.set cascade="all" order-by="due_date_time"
	 * @hibernate.key column="file_ref_number"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Task"
	 * @return Returns the tasks.
	 */
	public Set<Task> getTasks() {
		return tasks;
	}

	/**
	 * @param tasks
	 *          The tasks to set.
	 */
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="agent_ID"
	 * @return Returns the agent.
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * @param agent
	 *          The agent to set.
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	/**
	 * @hibernate.property type="string" length="13"
	 * @return Returns the claimnum.
	 */
	public String getClaimnum() {
		return claimnum;
	}

	/**
	 * @param claimnum
	 *          The claimnum to set.
	 */
	public void setClaimnum(String claimnum) {
		if (claimnum != null)
			claimnum = TracerUtils.removeSpaces(claimnum);
		this.claimnum = claimnum;
		setClaimSearchParams(claimnum);
	}
	
	private void setClaimSearchParams(String claimchecknum) {
		if (claimchecknum != null && claimchecknum.length() > 3 && claimchecknum.length() < 12) {
			String leading = null;
			String ticketing = null;
			String carrier = null;
			String bagnum = null;
			
			/*
	    	 * Check if claimchecknum is Untagged Bagtag and mark carrier and bagnum if so	
	    	 */
			if(claimchecknum.substring(0, 3).toUpperCase().equals(TracingConstants.UTB_CHECK)){
				ticketing = claimchecknum.substring(0, 3);
				bagnum = claimchecknum.substring(3);
			} else if (claimchecknum.length() == 8) {
				carrier = claimchecknum.substring(0, 2);
				ticketing = LookupAirlineCodes.getThreeDigitTicketingCode(carrier);
				bagnum = claimchecknum.substring(2);
			} else if (claimchecknum.length() == 9) {
				ticketing = claimchecknum.substring(0, 3);
				carrier = LookupAirlineCodes.getTwoLetterAirlineCode(ticketing);
				bagnum = claimchecknum.substring(3);
			} else if (claimchecknum.length() == 10) {
				leading = claimchecknum.substring(0, 1);
				ticketing = claimchecknum.substring(1, 4);
				carrier = LookupAirlineCodes.getTwoLetterAirlineCode(ticketing);
				bagnum = claimchecknum.substring(4);
			}
			
			if (leading != null && leading.matches("^[0-9]{1}$")) {
				setClaimchecknum_leading(leading);
			}
			setClaimchecknum_carriercode(carrier);
			setClaimchecknum_ticketingcode(ticketing);
			setClaimchecknum_bagnumber(bagnum);
		}
	}

	/**
	 * @return Returns the claimchecknum_leading.
	 * 
	 * @hibernate.property type="string" length="1"
	 */
	public String getClaimchecknum_leading() {
		return claimchecknum_leading;
	}

	/**
	 * @param claimchecknum_leading
	 *          The claimchecknum_leading to set.
	 */
	public void setClaimchecknum_leading(String claimchecknum_leading) {
		this.claimchecknum_leading = claimchecknum_leading;
	}
	
	/**
	 * @return Returns the externaldesc.
	 * 
	 * @hibernate.property type="string" length="50"
	 */
	public String getExternaldesc() {
		return externaldesc;
	}

	/**
	 * @param externaldesc
	 *          The externaldesc to set.
	 */
	public void setExternaldesc(String externaldesc) {
		this.externaldesc = externaldesc;
	}

	/**
	 * @return Returns the claimchecknum_ticketingcode.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getClaimchecknum_ticketingcode() {
		return claimchecknum_ticketingcode;
	}

	/**
	 * @param claimchecknum_ticketingcode
	 *          The claimchecknum_ticketingcode to set.
	 */
	public void setClaimchecknum_ticketingcode(String claimchecknum_ticketingcode) {
		this.claimchecknum_ticketingcode = claimchecknum_ticketingcode;
	}

	/**
	 * @return Returns the claimchecknum_carriercode.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getClaimchecknum_carriercode() {
		return claimchecknum_carriercode;
	}

	/**
	 * @param claimchecknum_carriercode
	 *          The claimchecknum_carriercode to set.
	 */
	public void setClaimchecknum_carriercode(String claimchecknum_carriercode) {
		this.claimchecknum_carriercode = claimchecknum_carriercode;
	}

	/**
	 * @return Returns the claimchecknum_bagnumber.
	 * 
	 * @hibernate.property type="string" length="6"
	 */
	public String getClaimchecknum_bagnumber() {
		return claimchecknum_bagnumber;
	}

	/**
	 * @param claimchecknum_bagnumber
	 *          The claimchecknum_bagnumber to set.
	 */
	public void setClaimchecknum_bagnumber(String claimchecknum_bagnumber) {
		this.claimchecknum_bagnumber = claimchecknum_bagnumber;
	}

	/**
	 * 
	 * @hibernate.property type="string" length="2"
	 * @return Returns the color.
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @param color
	 *          The color to set.
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @hibernate.set cascade="all" inverse="true" order-by="OHD_Inventory_ID"
	 * @hibernate.key column="OHD_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.OHD_Inventory"
	 * 
	 * @return Returns the items.
	 */
	public Set<OHD_Inventory> getItems() {
		return items;
	}

	/**
	 * @param items
	 *          The items to set.
	 */
	public void setItems(Set<OHD_Inventory> items) {
		this.items = items;
	}

	/**
	 * @hibernate.id generator-class="assigned" type="string" column="OHD_ID"
	 * @return Returns the oHD_ID.
	 */
	public String getOHD_ID() {
		return OHD_ID;
	}

	/**
	 * @param ohd_id
	 *          The oHD_ID to set.
	 */
	public void setOHD_ID(String ohd_id) {
		OHD_ID = ohd_id;
	}

	/**
	 * 
	 * @hibernate.set cascade="all" inverse="true" order-by="createtime"
	 * @hibernate.key column="ohd_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Remark"
	 * 
	 * @return Returns the remarks.
	 */
	public Set<Remark> getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *          The remarks to set.
	 */
	public void setRemarks(Set<Remark> remarks) {
		this.remarks = remarks;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="status_ID"
	 * @return Returns the status.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *          The status to set.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="disposal_status_id"
	 * @return Returns the disposal_status.
	 */
	public Status getDisposal_status() {
		return disposal_status;
	}

	/**
	 * @param disposal_status
	 *          The disposal_status to set.
	 */
	public void setDisposal_status(Status disposal_status) {
		this.disposal_status = disposal_status;
	}
	
	/**
	 * @hibernate.property type="string"
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *          The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @hibernate.property type="integer"
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
	 * 
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="integer"
	 * @return Returns the xdescelement_ID_1.
	 */
	public int getXdescelement_ID_1() {
		return xdescelement_ID_1;
	}

	/**
	 * @param xdescelement_ID_1
	 *          The xdescelement_ID_1 to set.
	 */
	public void setXdescelement_ID_1(int xdescelement_ID_1) {
		this.xdescelement_ID_1 = xdescelement_ID_1;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the xdescelement_ID_2.
	 */
	public int getXdescelement_ID_2() {
		return xdescelement_ID_2;
	}

	/**
	 * @param xdescelement_ID_2
	 *          The xdescelement_ID_2 to set.
	 */
	public void setXdescelement_ID_2(int xdescelement_ID_2) {
		this.xdescelement_ID_2 = xdescelement_ID_2;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the xdescelement_ID_3.
	 */
	public int getXdescelement_ID_3() {
		return xdescelement_ID_3;
	}

	/**
	 * @param xdescelement_ID_3
	 *          The xdescelement_ID_3 to set.
	 */
	public void setXdescelement_ID_3(int xdescelement_ID_3) {
		this.xdescelement_ID_3 = xdescelement_ID_3;
	}

	public OHD_Passenger getPassenger() {
		if (passengers != null && passengers.size() > 0) return (OHD_Passenger) (new ArrayList<OHD_Passenger>(
				passengers)).get(0);
		else {
			OHD_Passenger pass = new OHD_Passenger();
			pass.setFirstname("");
			pass.setMiddlename("");
			pass.setLastname("");
			return pass;
		}
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="found_station_ID"
	 * @return Returns the foundAtStation.
	 */
	public Station getFoundAtStation() {
		return foundAtStation;
	}

	/**
	 * @param foundAtStation
	 *          The foundAtStation to set.
	 */
	public void setFoundAtStation(Station foundAtStation) {
		this.foundAtStation = foundAtStation;
	}

	/**
	 * @hibernate.property type="time"
	 * @return Returns the foundtime.
	 */
	public Date getFoundtime() {
		return foundtime;
	}

	/**
	 * @param foundtime
	 *          The foundtime to set.
	 */
	public void setFoundtime(Date foundtime) {
		this.foundtime = foundtime;
	}

	/**
	 * @hibernate.set cascade="all" inverse="true" order-by="passenger_id"
	 * @hibernate.key column="OHD_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.OHD_Passenger"
	 * @return Returns the passengers.
	 */
	public Set<OHD_Passenger> getPassengers() {
		return passengers;
	}

	/**
	 * @param passengers
	 *          The passengers to set.
	 */
	public void setPassengers(Set<OHD_Passenger> passengers) {
		this.passengers = passengers;
	}

	/**
	 * @hibernate.set cascade="all" inverse="true" order-by="Photo_ID"
	 * @hibernate.key column="OHD_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.OHD_Photo"
	 * @return Returns the photos.
	 */
	public Set<OHD_Photo> getPhotos() {
		return photos;
	}

	/**
	 * @param photos
	 *          The photos to set.
	 */
	public void setPhotos(Set<OHD_Photo> photos) {
		this.photos = photos;
	}

	/**
	 * @hibernate.set cascade="all" order-by="control_id"
	 * @hibernate.key column="file_ref_number"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.ControlLog"
	 * @return Returns the controlLog.
	 */
	public Set<ControlLog> getControlLog() {
		return controlLog;
	}

	/**
	 * @param controlLog
	 *          The controlLog to set.
	 */
	public void setControlLog(Set<ControlLog> controlLog) {
		this.controlLog = controlLog;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="holding_station_ID"
	 * @return Returns the holdingStation.
	 */
	public Station getHoldingStation() {
		return holdingStation;
	}

	/**
	 * @param holdingStation
	 *          The holdingStation to set.
	 */
	public void setHoldingStation(Station holdingStation) {
		this.holdingStation = holdingStation;
	}

	/**
	 * @hibernate.property type="date"
	 * @return Returns the founddate.
	 */
	public Date getFounddate() {
		return founddate;
	}

	/**
	 * @param founddate
	 *          The founddate to set.
	 */
	public void setFounddate(Date founddate) {
		this.founddate = founddate;
	}

	public ControlLog getLastLog() {
		if (this.getControlLog() != null) {
			for (Iterator<ControlLog> i = this.getControlLog().iterator(); i.hasNext();) {
				ControlLog log = (ControlLog) i.next();
				if (log.getEnd_date() == null || log.getEnd_date().equals("")) return log;
			}
		}
		return null;
	}

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

	/**
	 * @hibernate.property type="string"
	 * @return Returns the storage_location.
	 */
	public String getStorage_location() {
		return storage_location;
	}

	/**
	 * @param storage_location
	 *          The storage_location to set.
	 */
	public void setStorage_location(String storage_location) {
		this.storage_location = storage_location;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the close_date.
	 */
	public Date getClose_date() {
		return close_date;
	}

	public String getManufacturer() {
		String ret = "";

		if (this.getManufacturer_ID() > 0) {
			if (this.getManufacturer_ID() == TracingConstants.MANUFACTURER_OTHER_ID) {
				if (this.getManufacturer_other() != null && this.getManufacturer_other().length() > 0) {
					ret = this.getManufacturer_other();
				}
			} else {
				Manufacturer manuf = TracerUtils.getManufacturer(this.getManufacturer_ID());
				if (manuf != null) {
					ret = manuf.getDescription();
				}
			}
		}

		return ret;
	}

	/**
	 * @param close_date
	 *          The close_date to set.
	 *          
	 */
	public void setClose_date(Date close_date) {
		this.close_date = close_date;
	}
	
	/**
	 * @return the wtFile object
	 * 
	 * @hibernate.component class="com.bagnet.nettracer.tracing.db.WorldTracerFile" lazy="false"
	 */
	public WorldTracerFile getWtFile() {
		return wtFile;
	}


	/**
	 * @param wt_id the wt_id to set
	 */
	public void setWtFile(WorldTracerFile wtFile) {
		this.wtFile = wtFile;
	}
	
	public String getWt_id() {
		if(wtFile != null) {
			return wtFile.getWt_id();
		}
		return null;
	}

	/**
	 * @return Returns the faultStation.
	 * @hibernate.property type="integer"
	 */
	public int getFaultstation_ID() {
		return faultStation;
	}

	/**
	 * @param faultstation
	 *          The faultstation to set.
	 */
	public void setFaultstation_ID(int faultStation) {
		this.faultStation = faultStation;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the loss_code.
	 */
	public int getLoss_code() {
		return loss_code;
	}

	/**
	 * @param loss_code
	 *          The loss_code to set.
	 */
	public void setLoss_code(int loss_code) {
		this.loss_code = loss_code;
	}

	/**
	 * @hibernate.property type="boolean"
	 * @return
	 */
	public boolean isEarlyBag() {
		return earlyBag;
	}

	public void setEarlyBag(boolean earlyBag) {
		this.earlyBag = earlyBag;
	}




	public String format(String val) {
		if (val == null) return " ";
		else return val + " ";
	}

	/**
	 * @hibernate.property type="string" length="13"
	 * @return Returns the claimnum.
	 */
	public String getMatched_incident() {
		return matched_incident;
	}

	public void setMatched_incident(String matchedIncident) {
		this.matched_incident = matchedIncident;
	}
	
	public String getXdescelement2() {
		if (xdescelement_ID_2 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_2);
		return xd.getDescription();
	}

	public String getXdescelement3() {
		if (xdescelement_ID_3 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_3);
		return xd.getDescription();
	}
	
	public String getX1() {
		if (xdescelement_ID_1 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_1);
		return xd.getCode();
	}

	public String getX2() {
		if (xdescelement_ID_2 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_2);
		return xd.getCode();
	}

	public String getX3() {
		if (xdescelement_ID_3 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_3);
		return xd.getCode();
	}
	
	public String getXdescelement1Key() {
		if (xdescelement_ID_1 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_1);
		return xd.getKey();
	}
	public String getXdescelement2Key() {
		if (xdescelement_ID_2 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_2);
		return xd.getKey();
	}
	public String getXdescelement3Key() {
		if (xdescelement_ID_3 <= 0) return "";
		XDescElement xd = TracerUtils.getXdescelement(xdescelement_ID_3);
		return xd.getKey();
	}
	
	/**
	 * @hibernate.property type="boolean"
	 * @return
	 */
	public boolean isTagSentToWt() {
  	return tagSentToWt;
  }

	public void setTagSentToWt(boolean tagSentToWt) {
  	this.tagSentToWt = tagSentToWt;
  }

	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return Returns the ohd_type.
	 */
	public int getTagSentToWtStationId() {
  	return tagSentToWtStationId;
  }

	public void setTagSentToWtStationId(int tagSentToWtStationId) {
  	this.tagSentToWtStationId = tagSentToWtStationId;
  }

	
	/**
	 * @hibernate.property type="integer"
	 * 
	 * @return
	 */
	public int getCreationMethod() {
		return creationMethod;
	}

	public void setCreationMethod(int creationMethod) {
		this.creationMethod = creationMethod;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @hibernate.property type="timestamp"
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @hibernate.property type="string" length="8"
	 */
	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	/**
	 * @hibernate.property type="boolean"
	 */
	public boolean getLateCheckInd() {
		return lateCheckInd;
	}

	public void setLateCheckInd(boolean lateCheckInd) {
		this.lateCheckInd = lateCheckInd;
	}
	
	public String getDispDestination() {
		if (itinerary == null || itinerary.isEmpty()) return "";
		Object[] items = itinerary.toArray();
		OHD_Itinerary itinerary = (OHD_Itinerary) items[items.length - 1];
		return itinerary.getLegto() != null ? itinerary.getLegto() : "";
	}
	
	public String getDispModifiedDate() {
		String modDate = DateUtils.formatDate(this.getModifiedDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		return modDate != null ? modDate.substring(0, modDate.lastIndexOf(' ')) : "";
	}
	
	public String getDispModifiedTime() {
		String modTime = DateUtils.formatDate(this.getModifiedDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		return modTime != null ? modTime.substring(modTime.lastIndexOf(' '), modTime.length()) : "";
	}

	/**
	 * @return The Category of the OHD
	 * 
	 * @hibernate.property type="int"
	 */
	public int getOther() {
		return other;
	}

	/**
	 * @param other
	 *          The other to set.
	 */
	public void setOther(int other) {
		this.other = other;
	}

	/**
	 * @hibernate.property type="boolean"
	 */
	public boolean isNoAddFees() {
		return noAddFees;
	}

	public void setNoAddFees(boolean noAddFees) {
		this.noAddFees = noAddFees;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getSpecialCondition() {
		return specialCondition;
	}

	public void setSpecialCondition(int specialCondition) {
		this.specialCondition = specialCondition;
	}
	
	public String getFullName() {
		StringBuilder fullName = new StringBuilder();
		boolean haveLast = lastname != null && !lastname.isEmpty();
		boolean haveFirst = firstname != null && !firstname.isEmpty();
		boolean haveMiddle = middlename != null && !middlename.isEmpty();
		
		if (haveLast) {
			fullName.append(getLastname());
		}
		
		if (haveFirst) {
			if (haveLast) {
				fullName.append(", ");
			}
			fullName.append(getFirstname());
		}
		
		if (haveMiddle) {
			if (haveLast || haveFirst) {
				fullName.append(" ");
			}
			fullName.append(getMiddlename());
		}
		
		return fullName.toString();
	}
	
}