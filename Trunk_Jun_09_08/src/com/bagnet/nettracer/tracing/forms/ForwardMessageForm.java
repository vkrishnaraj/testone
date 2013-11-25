package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for forward functionality
 */
public final class ForwardMessageForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3932455278209837715L;
	private String bag_tag; //baggage request
	private String expediteNumber; //expedite number
	private String destStation; //destination station
	private String message; //message
	private String companyCode; //company code
	private List<OHD_Itinerary> bagitinerarylist = new ArrayList<OHD_Itinerary>();
	private List<OHD_Log_Itinerary> forwarditinerarylist = new ArrayList<OHD_Log_Itinerary>();
	private List<TagNumber> taglist = new ArrayList<TagNumber>();
	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;
	private String lossCode;
	private String faultStation;
	private String specialInstructions;
	private boolean expediteSticker;
	

	/**
	 * @return Returns the bagitinerarylist.
	 */
	public List<OHD_Itinerary> getBagitinerarylist() {
		return bagitinerarylist;
	}

	/**
	 * @param bagitinerarylist
	 *          The bagitinerarylist to set.
	 */
	public void setBagitinerarylist(List<OHD_Itinerary> bagitinerarylist) {
		this.bagitinerarylist = bagitinerarylist;
	}
	
	/**
	 * @return Returns the taglist.
	 */
	public List<TagNumber> getTaglist() {
		return taglist;
	}

	/**
	 * @param taglist
	 *          The taglist to set.
	 */
	public void setTaglist(List<TagNumber> taglist) {
		this.taglist = taglist;
	}

	/**
	 * @return Returns the forwarditinerarylist.
	 */
	public List<OHD_Log_Itinerary> getForwarditinerarylist() {
		return forwarditinerarylist;
	}

	/**
	 * @param forwarditinerarylist
	 *          The forwarditinerarylist to set.
	 */
	public void setForwarditinerarylist(List<OHD_Log_Itinerary> forwarditinerarylist) {
		this.forwarditinerarylist = forwarditinerarylist;
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
	 * @return Returns the bag_tag.
	 */
	public String getBag_tag() {
		return bag_tag;
	}

	/**
	 * @param bag_tag
	 *          The bag_tag to set.
	 */
	public void setBag_tag(String bag_tag) {
		this.bag_tag = bag_tag;
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
	 * @return Returns the expediteSticker.
	 */
	public boolean getExpediteSticker() {
		return expediteSticker;
	}

	/**
	 * @param expediteSticker
	 *          The expediteSticker to set.
	 */
	public void setExpediteStick(boolean expediteSticker) {
		this.expediteSticker = expediteSticker;
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
	 * @return Returns the lossCode.
	 */
	public String getLossCode() {
		return lossCode;
	}

	/**
	 * @param lossCode
	 *          The lossCode to set.
	 */
	public void setLossCode(String lossCode) {
		this.lossCode = lossCode;
	}

	/**
	 * @return Returns the faultStation.
	 */
	public String getFaultStation() {
		return faultStation;
	}

	/**
	 * @param faultStation
	 *          The faultStation to set.
	 */
	public void setFaultStation(String faultStation) {
		this.faultStation = faultStation;
	}
	
	public OHD_Itinerary getBagItinerary(int index) {
		if (this.bagitinerarylist.size() <= index) {
			OHD_Itinerary i = new OHD_Itinerary();
			i.setItinerarytype(1);
			/* following code set the legs types to the legfrom and legto */
			// first two itinerary( passenger and bag)
			if (index <= 1) {
				i.setLegfrom_type(TracingConstants.LEG_B_STATION);
				i.setLegto_type(TracingConstants.LEG_E_STATION);
			} else {
				// make the previous terminating station into trasfer station
				int tempindex = index - 2;
				OHD_Itinerary tempi = getBagItinerary(tempindex);
				tempi.setLegto_type(TracingConstants.LEG_T_STATION);
				i.setLegfrom_type(TracingConstants.LEG_T_STATION);
				i.setLegto_type(TracingConstants.LEG_E_STATION);
			}
			this.bagitinerarylist.add(i);
		}
		return (OHD_Itinerary) this.bagitinerarylist.get(index);
	}
	
	public TagNumber getTagNumber(int index) {
		if (this.taglist.size() <= index) {
			TagNumber i = new TagNumber();
			
			this.taglist.add(i);
		}
		return (TagNumber) this.taglist.get(index);
	}
	
	public TagNumber deleteTagNumber(int index) {
		if (this.taglist.size() <= index) {
			return null;
		}
		return this.taglist.remove(index);
	}

	public OHD_Log_Itinerary getItinerary(int index) {
		if (this.forwarditinerarylist.size() <= index) {
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
			this.forwarditinerarylist.add(i);
		}
		return (OHD_Log_Itinerary) this.forwarditinerarylist.get(index);
	}

	/**
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions() {
		return specialInstructions;
	}

	/**
	 * @param specialInstructions the specialInstructions to set
	 */
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	public void set_TIMEFORMAT(String _TIMEFORMAT) {
		this._TIMEFORMAT = _TIMEFORMAT;
	}

	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}

	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	public void set_TIMEZONE(TimeZone _TIMEZONE) {
		this._TIMEZONE = _TIMEZONE;
	}

	public class TagNumber {
		private String bagTagNumber;
		private String expediteNumber;
		private boolean expediteSticker;
		
		/**
		 * @return Returns the bagTagNumber.
		 */
		public String getBagTagNumber() {
			return bagTagNumber;
		}

		/**
		 * @param bagTagNumber
		 *          The bagTagNumber to set.
		 */
		public void setBagTagNumber(String bagTagNumber) {
			this.bagTagNumber = bagTagNumber;
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
		 * @return Returns the expediteSticker.
		 */
		public boolean getExpediteSticker() {
			return expediteSticker;
		}

		/**
		 * @param expediteSticker
		 *          The expediteSticker to set.
		 */
		public void setExpediteSticker(boolean expediteSticker) {
			this.expediteSticker = expediteSticker;
		}
	}
}