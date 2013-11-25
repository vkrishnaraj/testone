package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for forward functionality
 */
public final class ForwardOnHandForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5287123912640811860L;
	private int lz = 0; //is this Forwarding for LZ.
	private String bag_request_id; //baggage request
	private String ohd_ID; //on-hand id
	private List<LabelValueBean> ohdList;
	private String expediteNumber; //expedite number
	private String destStation; //destination station
	private String message; //message
	private String companyCode; //company code
	private List<OHD_Log_Itinerary> itinerarylist = new ArrayList<OHD_Log_Itinerary>();
	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;

	

	/**
	 * @return Returns the lz.
	 */
	public int getLz() {
		return lz;
	}

	/**
	 * @param lz
	 *          The lz to set.
	 */
	public void setLz(int lz) {
		this.lz = lz;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *          The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the bag_request_id.
	 */
	public String getBag_request_id() {
		return bag_request_id;
	}

	/**
	 * @param bag_request_id
	 *          The bag_request_id to set.
	 */
	public void setBag_request_id(String bag_request_id) {
		this.bag_request_id = bag_request_id;
	}

	/**
	 * @return Returns the destStation.
	 */
	public String getDestStation() {
		return destStation;
	}

	/**
	 * @param destStation
	 *          The destStation to set.
	 */
	public void setDestStation(String destStation) {
		this.destStation = destStation;
	}

	/**
	 * @return Returns the expediteNumber.
	 */
	public String getExpediteNumber() {
		return expediteNumber;
	}

	/**
	 * @param expediteNumber
	 *          The expediteNumber to set.
	 */
	public void setExpediteNumber(String expediteNumber) {
		this.expediteNumber = expediteNumber;
	}

	/**
	 * @return Returns the itinerarylist.
	 */
	public List<OHD_Log_Itinerary> getItinerarylist() {
		return itinerarylist;
	}

	/**
	 * @param itinerarylist
	 *          The itinerarylist to set.
	 */
	public void setItinerarylist(List<OHD_Log_Itinerary> itinerarylist) {
		this.itinerarylist = itinerarylist;
	}

	/**
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
	 * @return Returns the ohd_ID.
	 */
	public String getOhd_ID() {
		return ohd_ID;
	}

	/**
	 * @param ohd_ID
	 *          The ohd_ID to set.
	 */
	public void setOhd_ID(String ohd_ID) {
		this.ohd_ID = ohd_ID;
	}

	public OHD_Log_Itinerary getItinerary(int index) {
		if (this.itinerarylist.size() <= index) {
			OHD_Log_Itinerary i = new OHD_Log_Itinerary();
			i.setItinerarytype(1);
			/* following code set the legs types to the legfrom and legto */
			// first two itinerary( passenger and bag)
			if (index <= 1) {
				i.setLegfrom_type(TracingConstants.LEG_B_STATION);
				i.setLegto_type(TracingConstants.LEG_E_STATION);
			} else {
				// make the previous terminating station into trasfer station
				int tempindex = index - 2;
				OHD_Log_Itinerary tempi = getItinerary(tempindex);
				tempi.setLegto_type(TracingConstants.LEG_T_STATION);
				i.setLegfrom_type(TracingConstants.LEG_T_STATION);
				i.setLegto_type(TracingConstants.LEG_E_STATION);
			}
			this.itinerarylist.add(i);
		}
		return (OHD_Log_Itinerary) this.itinerarylist.get(index);
	}

	public List<LabelValueBean> getOhdList() {
		return ohdList;
	}

	public void setOhdList(List<LabelValueBean> ohdList) {
		this.ohdList = ohdList;
	}

	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}

	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	public void set_TIMEFORMAT(String _TIMEFORMAT) {
		this._TIMEFORMAT = _TIMEFORMAT;
	}

	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	public void set_TIMEZONE(TimeZone _TIMEZONE) {
		this._TIMEZONE = _TIMEZONE;
	}

}