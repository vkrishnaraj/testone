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
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="OHD"
 * @hibernate.typedef name="worldTracerStatus" class="com.bagnet.nettracer.tracing.utils.StringEnumUserType"
 * @hibernate.typedef-param typedef-name="worldTracerStatus" name="enumClassname"
 * 			value="com.bagnet.nettracer.tracing.db.WorldTracerFile$WTStatus"
 */
public class OHD implements Serializable {

	private String OHD_ID;
	private Station foundAtStation;
	private Station holdingStation;
	private String storage_location;
	private Agent agent;
	private Date foundtime;
	private Date founddate;
	private Date bagarrivedate;
	private String claimnum;
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

	private Set items;
	private Set remarks;
	private Set passengers;
	private Set photos;
	private Set tasks;
	private Set controlLog;
	private Set itinerary;
	private Date close_date;
	private Date lastupdated;
	
	private WorldTracerFile wtFile;	// worldtracer id

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;

	
	private int faultStation;
	private int loss_code;
	

	public String getDisplaydate() {
		Date completedate = DateUtils.convertToDate(this.getFounddate().toString() + " "
				+ this.getFoundtime().toString(), TracingConstants.DB_DATETIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
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
	 *                order-by="itinerarytype,departdate,schdeparttime,itinerary_ID"
	 * @hibernate.key column="ohd_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.OHD_Itinerary"
	 * 
	 * @return Returns the itinerary.
	 */
	public Set getItinerary() {
		return itinerary;
	}

	/**
	 * @param itinerary
	 *          The itinerary to set.
	 */
	public void setItinerary(Set itinerary) {
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
	public Set getTasks() {
		return tasks;
	}

	/**
	 * @param tasks
	 *          The tasks to set.
	 */
	public void setTasks(Set tasks) {
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
		this.claimnum = claimnum;
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
	public Set getItems() {
		return items;
	}

	/**
	 * @param items
	 *          The items to set.
	 */
	public void setItems(Set items) {
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
	public Set getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *          The remarks to set.
	 */
	public void setRemarks(Set remarks) {
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
		if (passengers != null && passengers.size() > 0) return (OHD_Passenger) (new ArrayList(
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
	public Set getPassengers() {
		return passengers;
	}

	/**
	 * @param passengers
	 *          The passengers to set.
	 */
	public void setPassengers(Set passengers) {
		this.passengers = passengers;
	}

	public boolean isClosed() {
		return status.getDescription().trim().toLowerCase().equals("closed");
	}

	/**
	 * @hibernate.set cascade="all" inverse="true" order-by="Photo_ID"
	 * @hibernate.key column="OHD_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.OHD_Photo"
	 * @return Returns the photos.
	 */
	public Set getPhotos() {
		return photos;
	}

	/**
	 * @param photos
	 *          The photos to set.
	 */
	public void setPhotos(Set photos) {
		this.photos = photos;
	}

	/**
	 * @hibernate.set cascade="all" order-by="control_id"
	 * @hibernate.key column="file_ref_number"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.ControlLog"
	 * @return Returns the controlLog.
	 */
	public Set getControlLog() {
		return controlLog;
	}

	/**
	 * @param controlLog
	 *          The controlLog to set.
	 */
	public void setControlLog(Set controlLog) {
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
			for (Iterator i = this.getControlLog().iterator(); i.hasNext();) {
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
	 * @hibernate.component class="com.bagnet.nettracer.tracing.db.WorldTracerFile"
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

	public String getText() {
		StringBuffer ret = new StringBuffer(1096);

		ret.append(format(this.getOHD_ID()));

		ret.append(format(this.getFoundAtStation().getStationcode()));
		ret.append(format(this.getHoldingStation().getStationcode()));
		ret.append(format(this.getStorage_location()));
		ret.append(format(this.getAgent().getUsername()));
		ret.append(format(this.getClaimnum()));
		ret.append(format(this.getColor()));
		ret.append(format(this.getType()));

		ret.append(format(this.getLastname()));
		ret.append(format(this.getFirstname()));
		ret.append(format(this.getMiddlename()));

		if (this.getMembership() != null) {
			ret.append(format(this.getMembership().getMembershipnum()));
		}

		ret.append(format(this.getManufacturer()));

		ret.append(format(this.getRecord_locator()));

		if (this.getXdescelement_ID_1() > 0) {
			ret.append(format(TracerUtils.getXdescelement(this.getXdescelement_ID_1())
					.getDescription()));
		}

		if (this.getXdescelement_ID_2() > 0) {
			ret.append(format(TracerUtils.getXdescelement(this.getXdescelement_ID_2())
					.getDescription()));
		}

		if (this.getXdescelement_ID_3() > 0) {
			ret.append(format(TracerUtils.getXdescelement(this.getXdescelement_ID_3())
					.getDescription()));
		}

		ret.append(format(OHDUtils.getStatusDesc(this.getStatus().getStatus_ID())));

		if (this.getItems() != null && this.getItems().size() > 0) {
			for (Iterator i = this.getItems().iterator(); i.hasNext();) {
				OHD_Inventory inv = (OHD_Inventory) i.next();
				ret.append(format(inv.getCategory()));
				ret.append(format(inv.getDescription()));
			}
		}

		if (this.getRemarks() != null && this.getRemarks().size() > 0) {
			for (Iterator i = this.getRemarks().iterator(); i.hasNext();) {
				Remark remark = (Remark) i.next();
				ret.append(format(remark.getRemarktext()));
			}
		}

		if (this.getPassengers() != null && this.getPassengers().size() > 0) {
			for (Iterator i = this.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger pass = (OHD_Passenger) i.next();
				ret.append(format(pass.getFirstname()));
				ret.append(format(pass.getMiddlename()));
				ret.append(format(pass.getLastname()));

				if (pass.getAddresses() != null && pass.getAddresses().size() > 0) {
					for (Iterator j = pass.getAddresses().iterator(); j.hasNext();) {
						OHD_Address addr = (OHD_Address) j.next();

						ret.append(format(addr.getAddress1()));
						ret.append(format(addr.getAddress2()));
						ret.append(format(addr.getAltphone()));
						ret.append(format(addr.getCity()));
						ret.append(format(addr.getCountry()));
						ret.append(format(addr.getEmail()));
						ret.append(format(addr.getHomephone()));
						ret.append(format(addr.getMobile()));
						ret.append(format(addr.getPager()));
						ret.append(format(addr.getProvince()));
						ret.append(format(addr.getState()));
						ret.append(format(addr.getWorkphone()));
						ret.append(format(addr.getZip()));
					}
				}
			}
		}

		if (this.getItinerary() != null && this.getItinerary().size() > 0) {
			for (Iterator i = this.getItinerary().iterator(); i.hasNext();) {
				OHD_Itinerary itinerary = (OHD_Itinerary) i.next();

				ret.append(format(itinerary.getAirline()));
				ret.append(format(itinerary.getFlightnum()));
				ret.append(format(itinerary.getLegfrom()));
				ret.append(format(itinerary.getLegto()));
			}
		}

		/*
		 * if (this.getControlLog() != null && this.getControlLog().size() > 0) {
		 * for (Iterator i = this.getControlLog().iterator(); i.hasNext();) {
		 * ControlLog log = (ControlLog) i.next();
		 * ret.append(format(log.getControlling_station().getStationcode())); } }
		 */

		return ret.toString();
	}



	public String format(String val) {
		if (val == null) return " ";
		else return val + " ";
	}
}