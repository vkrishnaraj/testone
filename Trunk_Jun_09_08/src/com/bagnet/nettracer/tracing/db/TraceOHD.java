package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="OHD"
 */
public class TraceOHD implements Serializable {

	private static final long serialVersionUID = 1L;
	private String OHD_ID;
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
	private String externaldesc;
	private Status status;

	private Set items;
	private Set passengers;
	private Set itinerary;
	private Date close_date;
	private Date lastupdated;
	
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
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.TraceOHD_Itinerary"
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
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.TraceOHD_Inventory"
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
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.TraceOHD_Passenger"
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

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the close_date.
	 */
	public Date getClose_date() {
		return close_date;
	}

	public String getCachedManufacturerDescription() {
		String ret = "";

		if (this.getManufacturer_ID() > 0) {
			if (this.getManufacturer_ID() == TracingConstants.MANUFACTURER_OTHER_ID) {
				if (this.getManufacturer_other() != null && this.getManufacturer_other().length() > 0) {
					ret = this.getManufacturer_other();
				}
			} else {
				ret = TracerUtils.getCachedManufacturerDescription(this.getManufacturer_ID());
			}
		}

		return ret;
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
	



	public String format(String val) {
		if (val == null) return " ";
		else return val + " ";
	}
}