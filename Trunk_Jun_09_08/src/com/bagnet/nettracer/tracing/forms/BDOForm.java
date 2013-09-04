package com.bagnet.nettracer.tracing.forms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.DeliveryStatusType;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Matt Zhu
 * 
 * This class represent bdo form
 */
public final class BDOForm extends ValidatorForm {

	// if choosebags = 1; get bag and passenger info to select from incident or
	// ohd
	private int choosebags;

	private int BDO_ID;
	private int delivercompany_ID;
	private int servicelevel_ID;

	private Station station = new Station();
	private String companycode_ID;
	private OHD ohd = new OHD();
	private Incident incident = new Incident();

	private Agent agent = new Agent();

	private Date createdate;
	private Date createtime;

	private Date deliverydate;

	private List passengerlist = new ArrayList();
	private List itemlist = new ArrayList();

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;
	private String delivery_comments;
	private String delivery_integration_id;
	private String currency;
	private String cost;
	private boolean canceled;
	private Date lastDeliveryUpdate;
	private DeliveryStatusType deliveryStatus;
	


	private double origDelivCost;
	private double modDelivCost;
	
    private String wt_id;
	/**
	 * @return the wt_id
	 */
	public String getWt_id() {
		return wt_id;
	}

	/**
	 * @param wt_id the wt_id to set
	 */
	public void setWt_id(String wt_id) {
		this.wt_id = wt_id;
	}

	/**
	 * @return Returns the choosebags.
	 */
	public int getChoosebags() {
		return choosebags;
	}

	/**
	 * @param choosebags
	 *          The choosebags to set.
	 */
	public void setChoosebags(int choosebags) {
		this.choosebags = choosebags;
	}

	public List getPassengerlist() {
		return passengerlist;
	}

	public void setPassengerlist(List passengerlist) {
		this.passengerlist = passengerlist;
	}

	public BDO_Passenger getPassenger(int index) {
		if (this.passengerlist.size() <= index) {
			BDO_Passenger a = new BDO_Passenger();
			a.set_DATEFORMAT(get_DATEFORMAT());
			this.passengerlist.add(a);
		}
		return (BDO_Passenger) this.passengerlist.get(index);
	}

	public List getItemlist() {
		return itemlist;
	}

	public void setItemlist(List itemlist) {
		this.itemlist = itemlist;
	}

	public Item getTheitem(int index) {
		if (index < 0) index = 0;
		if (this.itemlist.size() <= index) {
			this.itemlist.add(new Item());
		}
		return (Item) this.itemlist.get(index);
	}

	public Item getItem(int index, int itemtype) {
		if (index < 0) index = 0;
		if (this.itemlist.size() <= index) {
			Item item = new Item(itemtype);
			item.setBagnumber(index);
			this.itemlist.add(item);
		}
		return (Item) this.itemlist.get(index);
	}

	public int getBagcount() {
		if (itemlist != null) return itemlist.size();
		else return 0;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Agent getAgent() {
		return agent;
	}

	public String getAgentinit() {
		return agent.getUsername();
	}

	/**
	 * @return Returns the bDO_ID.
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
		if (BDO_ID == 0) return "";
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
	 * @return Returns the delivercompany_ID.
	 */
	public int getDelivercompany_ID() {
		return delivercompany_ID;
	}

	/**
	 * @param delivercompany_ID
	 *          The delivercompany_ID to set.
	 */
	public void setDelivercompany_ID(int delivercompany_ID) {
		this.delivercompany_ID = delivercompany_ID;
	}

	/**
	 * @return Returns the servicelevel_ID.
	 */
	public int getServicelevel_ID() {
		return servicelevel_ID;
	}

	/**
	 * @param servicelevel_ID
	 *          The servicelevel_ID to set.
	 */
	public void setServicelevel_ID(int servicelevel_ID) {
		this.servicelevel_ID = servicelevel_ID;
	}

	/**
	 * @return Returns the oHD_ID.
	 */
	public String getOHD_ID() {
		return ohd.getOHD_ID();
	}

	/**
	 * @return Returns the ohd.
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

	public String getIncident_ID() {
		return incident.getIncident_ID();
	}

	/**
	 * @return Returns the incident.
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

	/**
	 * @return Returns the station.
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
	 * @return Returns the createdate.
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

	/**
	 * @return Returns the deliverydate.
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

	public void setDispdeliverydate(String s) {
		setDeliverydate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	public String getDispdeliverydate() {
		return DateUtils.formatDate(getDeliverydate(), get_DATEFORMAT(), null, null);
	}

	/**
	 * 
	 * @return the createtime for display only
	 */
	public String getDispcreatetime() {
		Date tempdate = DateUtils.convertToDate( DateUtils.formatDate(getCreatedate(), TracingConstants.DB_DATEFORMAT, null, null) + " "
				+ DateUtils.formatDate(getCreatetime(), TracingConstants.DB_TIMEFORMAT, null, null),TracingConstants.DB_DATETIMEFORMAT,null);
		
		return DateUtils.formatDate(tempdate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
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
	 * @return the delivery_integration_id
	 */
	public String getDelivery_integration_id() {
		return delivery_integration_id;
	}

	/**
	 * @param delivery_integration_id the delivery_integration_id to set
	 */
	public void setDelivery_integration_id(String delivery_integration_id) {
		this.delivery_integration_id = delivery_integration_id;
	}

	/**
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public Date getLastDeliveryUpdate() {
  	return lastDeliveryUpdate;
  }

	public void setLastDeliveryUpdate(Date lastDeliveryUpdate) {
  	this.lastDeliveryUpdate = lastDeliveryUpdate;
  }

	public void setDeliveryStatus(DeliveryStatusType deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public DeliveryStatusType getDeliveryStatus() {
		return deliveryStatus;
	}
	
	public String getDeliveryStatusKey() {
		return "status.delivery." + deliveryStatus;
	}

	public String getDispLastDeliveryUpdate() {
		if (getLastDeliveryUpdate() == null) {
			return "";
		} else {
			return DateUtils.formatDate(getLastDeliveryUpdate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		}
	}

	/**
	 * @return the origDelivCost
	 */
	public double getOrigDelivCost() {
		return origDelivCost;
	}

	/**
	 * @param origDelivCost the origDelivCost to set
	 */
	public void setOrigDelivCost(double origDelivCost) {
		this.origDelivCost = origDelivCost;
	}

	/**
	 * @return the modDelivCost
	 */
	public double getModDelivCost() {
		return modDelivCost;
	}

	/**
	 * @param modDelivCost the modDelivCost to set
	 */
	public void setModDelivCost(double modDelivCost) {
		this.modDelivCost = modDelivCost;
	}
	

	public String getDispOrigDelivCost(){
		if(origDelivCost!=0){
			DecimalFormat dec=new DecimalFormat("###,##0.00 USD");
			return dec.format(origDelivCost);
		}
		return "";
	}
	public String getDispModDelivCost(){
		if(modDelivCost!=0){
			DecimalFormat dec=new DecimalFormat("###,##0.00");
			return dec.format(modDelivCost) +" "+getCurrency();
		}
		return "";
	}
}