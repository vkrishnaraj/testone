/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table = "BDO"
 * @hibernate.typedef name="deliveryIntegrationType" class="com.bagnet.nettracer.tracing.utils.StringEnumUserType"
 * @hibernate.typedef-param typedef-name="deliveryIntegrationType" name="enumClassname"
 * 			value="com.bagnet.nettracer.tracing.db.DeliveryIntegrationType"
 */
public class BDO implements Serializable {

	private int BDO_ID;
	private DeliverCompany delivercompany;
	private Deliver_ServiceLevel servicelevel;

	private Station station = new Station();
	private String companycode_ID;

	private Agent agent;

	private Date createdate;
	private Date createtime;

	private Date deliverydate;

	private OHD ohd;
	private Incident incident;

	private Set passengers; // passenger name and addresses
	private Set items;

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;

	private DeliveryIntegrationType delivery_integration_type;
	private String delivery_integration_id;
	private int integrationDelivercompany_ID;
	private String delivery_comments;
	private Set expensePayouts;
	
	
	/**
	 * @return Returns the passengers.
	 * 
	 * @hibernate.set cascade="all" inverse="true" order-by="bdo_passenger_ID"
	 * @hibernate.key column="bdo_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.BDO_Passenger"
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
	 * @return Returns the items.
	 * 
	 * @hibernate.set cascade="all" inverse="true" order-by="Item_ID"
	 * @hibernate.key column="bdo_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Item"
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
	 * @return Returns the bagcount.
	 */
	public int getBagcount() {
		if (items != null && items.size() > 0) return items.size();
		else if (incident == null && ohd != null) return 1;
		else return 0;
	}

	/**
	 * @return Returns the agent.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="agent_ID"
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

	public String getAgentinit() {
		return agent.getUsername();
	}

	/**
	 * @return Returns the bDO_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="bdo_ID"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="bdo_0"
	 * 
	 *  
	 */
	public int getBDO_ID() {
		return BDO_ID;
	}

	/**
	 * @param bdo_id
	 *          The bDO_ID to set.
	 */
	public void setBDO_ID(int bdo_id) {
		BDO_ID = bdo_id;
	}
	
	public String getBDO_ID_ref() {
		StringBuffer s = new StringBuffer();
		s.append("BDO");
		String num = Integer.toString(BDO_ID);
		// padd new number to # digits that total length will equal to
		// tracingconstants.incident_len
		for (int i = 0; i < TracingConstants.INCIDENT_LEN - num.length() - 3; i++) {
			s.append("0");
		}
		s.append(num);
		return s.toString();
	}

	/**
	 * @return Returns the companycode_ID.
	 * 
	 * @hibernate.property type="string"
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
	 * @return Returns the createdate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getCreatedate() {
		return createdate;
	}

	/**
	 * @param createdate
	 *          The createdate to set.
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	/**
	 * @return Returns the createtime.
	 * 
	 * @hibernate.property type="time"
	 */
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime
	 *          The createtime to set.
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDispcreatetime() {
		Date tempdate = DateUtils.convertToDate( DateUtils.formatDate(getCreatedate(), TracingConstants.DB_DATEFORMAT, null, null) + " "
				+ DateUtils.formatDate(getCreatetime(), TracingConstants.DB_TIMEFORMAT, null, null),TracingConstants.DB_DATETIMEFORMAT,null);
		
		return DateUtils.formatDate(tempdate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	/**
	 * @return Returns the delivercompany.
	 * 
	 * @hibernate.many-to-one cascade="none"
	 *                        class="com.bagnet.nettracer.tracing.db.DeliverCompany"
	 *                        column="delivercompany_ID"
	 */
	public DeliverCompany getDelivercompany() {
		return delivercompany;
	}

	/**
	 * @param delivercompany
	 *          The delivercompany to set.
	 */
	public void setDelivercompany(DeliverCompany delivercompany) {
		this.delivercompany = delivercompany;
	}

	/**
	 * @return Returns the servicelevel.
	 * 
	 * @hibernate.many-to-one cascade="none"
	 *                        class="com.bagnet.nettracer.tracing.db.Deliver_ServiceLevel"
	 *                        column="servicelevel_ID"
	 */
	public Deliver_ServiceLevel getServicelevel() {
		return servicelevel;
	}

	/**
	 * @param servicelevel
	 *          The servicelevel to set.
	 */
	public void setServicelevel(Deliver_ServiceLevel servicelevel) {
		this.servicelevel = servicelevel;
	}

	/**
	 * @return Returns the deliverydate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getDeliverydate() {
		return deliverydate;
	}

	/**
	 * @param deliverydate
	 *          The deliverydate to set.
	 */
	public void setDeliverydate(Date deliverydate) {
		this.deliverydate = deliverydate;
	}

	public String getDispdeliverydate() {
		return DateUtils.formatDate(getDeliverydate(), _DATEFORMAT, null, null);
	}

	/**
	 * @return Returns the station.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="station_ID"
	 */
	public Station getStation() {
		return station;
	}

	/**
	 * @param station
	 *          The station to set.
	 */
	public void setStation(Station station) {
		this.station = station;
	}

	/**
	 * @return Returns the ohd.
	 * 
	 * @hibernate.many-to-one cascade="none"
	 *                        class="com.bagnet.nettracer.tracing.db.OHD"
	 *                        column="OHD_ID"
	 */
	public OHD getOhd() {
		return ohd;
	}

	/**
	 * @param ohd
	 *          The ohd to set.
	 */
	public void setOhd(OHD ohd) {
		this.ohd = ohd;
	}

	/**
	 * @return Returns the incident.
	 * 
	 * @hibernate.many-to-one cascade="none"
	 *                        class="com.bagnet.nettracer.tracing.db.Incident"
	 *                        column="Incident_ID"
	 */
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param incident
	 *          The incident to set.
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public BDO_Passenger getPassenger(int i) {
		if (this.getPassengers() != null) {
			ArrayList t = new ArrayList(this.getPassengers());
			return (BDO_Passenger) t.get(i);
		} else return null;
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
	 * @return the delivery_integration_type
	 * @hibernate.property type="deliveryIntegrationType"
	 */
	public DeliveryIntegrationType getDelivery_integration_type() {
		return delivery_integration_type;
	}

	/**
	 * @param delivery_integration_type the delivery_integration_type to set
	 */
	public void setDelivery_integration_type(
			DeliveryIntegrationType delivery_integration_type) {
		this.delivery_integration_type = delivery_integration_type;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns unique ID provided by delivery company.
	 */
	public String getDelivery_integration_id() {
		return delivery_integration_id;
	}

	/**
	 * @param delivery_integration_id
	 */
	public void setDelivery_integration_id(String delivery_integration_id) {
		this.delivery_integration_id = delivery_integration_id;
		 
	}

	/**
	 * @hibernate.property type="integer"
	 * @return the integrationDelivercompany
	 */
	public int getIntegrationDelivercompany_ID() {
		return integrationDelivercompany_ID;
	}

	/**
	 * @param integrationDelivercompany the integrationDelivercompany to set
	 */
	public void setIntegrationDelivercompany_ID(
			int integrationDelivercompany_ID) {
		this.integrationDelivercompany_ID = integrationDelivercompany_ID;
	}

	
	/**
	 * @hibernate.property type="string"
	 * @return the delivery_comments
	 */
	public String getDelivery_comments() {
		return delivery_comments;
	}

	/**
	 * @param delivery_comments the delivery_comments to set
	 */
	public void setDelivery_comments(String delivery_comments) {
		this.delivery_comments = delivery_comments;
	}

	/**
	 * @return Returns the items.
	 * 
	 * @hibernate.set cascade="all" inverse="true"
	 * @hibernate.key column="bdo_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.ExpensePayout"
	 */
	public Set getExpensePayouts() {
		return expensePayouts;
	}

	public void setExpensePayouts(Set expensePayouts) {
		this.expensePayouts = expensePayouts;
	}

	public ExpensePayout getExpensePayout() {
		if (expensePayouts == null || expensePayouts.size() == 0) {
			return null;
		}
		return (ExpensePayout) expensePayouts.toArray()[0];
	}

	public void setExpensePayout(ExpensePayout createNewBdoPayout) {
		expensePayouts = new HashSet();
		expensePayouts.add(createNewBdoPayout);
	}
}