/*
 * Created on Aug 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="OHD_Log"
 */
public class OHD_Log implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1754832116819197388L;
	public int OHDLog_ID;
	public String expeditenum;
	public String message;
	public Agent forwarding_agent;
	public OHD ohd;
	public int destStationCode;
	public int ohd_request_id;
	public Date forward_time;
	private Set<OHD_Log_Itinerary> itinerary;
	public int log_status;
	private ProactiveNotification pcn;
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;

	public List<OHD_Log_Itinerary> getItinerarylist() {
		if (itinerary == null || itinerary.size() < 1) return null;

		return new ArrayList<OHD_Log_Itinerary>(itinerary);
	}

	public JRBeanCollectionDataSource getItineraryReport() {
		if (itinerary == null || itinerary.size() < 1) return null;

		return new JRBeanCollectionDataSource(new ArrayList<OHD_Log_Itinerary>(itinerary));
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

	public String getDestCompany() {
		if (destStationCode != 0 && destStationCode > 0) return StationBMO.getStation(
				"" + destStationCode).getCompany().getCompanyCode_ID();
		else return "";
	}

	public String getDestStation() {
		if (destStationCode != 0 && destStationCode > 0) return StationBMO.getStation(
				"" + destStationCode).getStationcode();
		else return "";
	}

	public String getDispForwardTime() {
		return DateUtils.formatDate(this.getForward_time(), _DATEFORMAT + " " + _TIMEFORMAT, null,
				_TIMEZONE);
	}

	/**
	 * @hibernate.set cascade="all"
	 *                order-by="itinerarytype,departdate,schdeparttime"
	 * @hibernate.key column="OHDLog_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary"
	 * 
	 * @return Returns the itinerary.
	 */
	public Set<OHD_Log_Itinerary> getItinerary() {
		return itinerary;
	}

	/**
	 * @param itinerary
	 *          The itinerary to set.
	 */
	public void setItinerary(Set<OHD_Log_Itinerary> itinerary) {
		this.itinerary = itinerary;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the destStationCode.
	 */
	public int getDestStationCode() {
		return destStationCode;
	}

	/**
	 * @param destStationCode
	 *          The destStationCode to set.
	 */
	public void setDestStationCode(int destStationCode) {
		this.destStationCode = destStationCode;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the ohd_request_id.
	 */
	public int getOhd_request_id() {
		return ohd_request_id;
	}

	/**
	 * @param ohd_request_id
	 *          The ohd_request_id to set.
	 */
	public void setOhd_request_id(int ohd_request_id) {
		this.ohd_request_id = ohd_request_id;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the expeditenum.
	 */
	public String getExpeditenum() {
		return expeditenum;
	}

	/**
	 * @param expeditenum
	 *          The expeditenum to set.
	 */
	public void setExpeditenum(String expeditenum) {
		this.expeditenum = expeditenum;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the forward_time.
	 */
	public Date getForward_time() {
		return forward_time;
	}

	/**
	 * @param forward_time
	 *          The forward_time to set.
	 */
	public void setForward_time(Date forward_time) {
		this.forward_time = forward_time;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="send_agent_id"
	 * @return Returns the forwarding_agent.
	 */
	public Agent getForwarding_agent() {
		return forwarding_agent;
	}

	/**
	 * @param forwarding_agent
	 *          The forwarding_agent to set.
	 */
	public void setForwarding_agent(Agent forwarding_agent) {
		this.forwarding_agent = forwarding_agent;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *          The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="OHDLog_ID"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="ohd_log_0"
	 * 
	 * 
	 * @return Returns the oHDLog_ID.
	 */
	public int getOHDLog_ID() {
		return OHDLog_ID;
	}

	/**
	 * @param log_ID
	 *          The oHDLog_ID to set.
	 */
	public void setOHDLog_ID(int log_ID) {
		OHDLog_ID = log_ID;
	}


	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.OHD"
	 *                        column="OHD_ID" not-null="true"
	 * @return
	 */
	public OHD getOhd() {
		return ohd;
	}

	public void setOhd(OHD ohd) {
		this.ohd = ohd;
	}

	/**
	 * @hibernate.property type="integer"
	 * @return
	 */
	public int getLog_status() {
		return log_status;
	}

	public void setLog_status(int log_status) {
		this.log_status = log_status;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.ProactiveNotification"
	 *                        column="pcn"
	 */
	public ProactiveNotification getPcn() {
		return pcn;
	}

	public void setPcn(ProactiveNotification pcn) {
		this.pcn = pcn;
	}	
	public String getDispDestinationAirline() {
		if (itinerary == null || itinerary.isEmpty()) return "";
		Object[] items = itinerary.toArray();
		OHD_Log_Itinerary itinerary = (OHD_Log_Itinerary) items[items.length - 1];
		return itinerary.getAirline() != null ? itinerary.getAirline() : "";
	}
	
	public String getDispDestinationFlightnum() {
		if (itinerary == null || itinerary.isEmpty()) return "";
		Object[] items = itinerary.toArray();
		OHD_Log_Itinerary itinerary = (OHD_Log_Itinerary) items[items.length - 1];
		return itinerary.getFlightnum() != null ? itinerary.getFlightnum() : "";
	}
		
}