package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.WT_FWD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.wtq.WtqSegment;

public class WorldTracerFOHForm extends ValidatorForm {

	private String ohd_ID;
	private String wt_id;
	private String matchingAhl;
	private String destinationAirline;
	private String destinationStation;
	private String bagtag;
	private String expeditenum;
	private String passenger1;
	private String passenger2;
	private String passenger3;
	private String comments;
	private String supplementary_information;
	private String teletype_address1;
	private String teletype_address2;
	private String teletype_address3;
	private String teletype_address4;

	private int fwd_status;
	
	public class FwdFormSegment {
		private String legto;
		private String legfrom;
		private String airline;
		private String flightnum;
		private String departdate;
		public String getLegto() {
			return legto;
		}
		public void setLegto(String legto) {
			this.legto = legto;
		}
		public String getLegfrom() {
			return legfrom;
		}
		public void setLegfrom(String legfrom) {
			this.legfrom = legfrom;
		}
		public String getAirline() {
			return airline;
		}
		public void setAirline(String airline) {
			this.airline = airline;
		}
		public String getFlightnum() {
			return flightnum;
		}
		public void setFlightnum(String flightnum) {
			this.flightnum = flightnum;
		}
		public String getDepartdate() {
			return departdate;
		}
		public void setDepartdate(String departdate) {
			this.departdate = departdate;
		}
	}
	
	private List<FwdFormSegment> itinerarylist = new ArrayList<FwdFormSegment>();

	
	/**
	 * @return the companyCode
	 */
	public String getDestinationAirline() {
		return destinationAirline;
	}

	/**
	 * @param companycode the companyCode to set
	 */
	public void setDestinationAirline(String companyCode) {
		this.destinationAirline = companyCode;
	}



	/**
	 * @return the destStation
	 */
	public String getDestinationStation() {
		return destinationStation;
	}

	/**
	 * @param destStation the destStation to set
	 */
	public void setDestinationStation(String destStation) {
		this.destinationStation = destStation;
	}

	/**
	 * @return the bagtag
	 */
	public String getBagtag() {
		return bagtag;
	}

	/**
	 * @param bagtag the bagtag to set
	 */
	public void setBagtag(String bagtag) {
		this.bagtag = bagtag;
	}

	/**
	 * @return the expeditenum
	 */
	public String getExpeditenum() {
		return expeditenum;
	}

	/**
	 * @param expeditenum the expeditenum to set
	 */
	public void setExpeditenum(String expeditenum) {
		this.expeditenum = expeditenum;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the teletype_address1
	 */
	public String getTeletype_address1() {
		return teletype_address1;
	}

	/**
	 * @param teletype_address1 the teletype_address1 to set
	 */
	public void setTeletype_address1(String teletype_address1) {
		this.teletype_address1 = teletype_address1;
	}

	/**
	 * @return the teletype_address2
	 */
	public String getTeletype_address2() {
		return teletype_address2;
	}

	/**
	 * @param teletype_address2 the teletype_address2 to set
	 */
	public void setTeletype_address2(String teletype_address2) {
		this.teletype_address2 = teletype_address2;
	}

	/**
	 * @return the teletype_address3
	 */
	public String getTeletype_address3() {
		return teletype_address3;
	}

	/**
	 * @param teletype_address3 the teletype_address3 to set
	 */
	public void setTeletype_address3(String teletype_address3) {
		this.teletype_address3 = teletype_address3;
	}

	/**
	 * @return the teletype_address4
	 */
	public String getTeletype_address4() {
		return teletype_address4;
	}

	/**
	 * @param teletype_address4 the teletype_address4 to set
	 */
	public void setTeletype_address4(String teletype_address4) {
		this.teletype_address4 = teletype_address4;
	}

	/**
	 * @return the fwd_status
	 */
	public int getFwd_status() {
		return fwd_status;
	}

	/**
	 * @param fwd_status the fwd_status to set
	 */
	public void setFwd_status(int fwd_status) {
		this.fwd_status = fwd_status;
	}

	/**
	 * @return Returns the itinerarylist.
	 */
	public List<FwdFormSegment> getItinerarylist() {
		return itinerarylist;
	}

	/**
	 * @param itinerarylist
	 *          The itinerarylist to set.
	 */
	public void setItinerarylist(List<FwdFormSegment> itinerarylist) {
		this.itinerarylist = itinerarylist;
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
    

	public FwdFormSegment getItinerary(int index) {
		if (this.itinerarylist.size() <= index) {
			return null;
		}
		return this.itinerarylist.get(index);
	}


	/**
	 * @return the supplementary_information
	 */
	public String getSupplementary_information() {
		return supplementary_information;
	}

	/**
	 * @param supplementary_information the supplementary_information to set
	 */
	public void setSupplementary_information(String supplementary_information) {
		this.supplementary_information = supplementary_information;
	}

	/**
	 * @return the placed_in_file
	 */
	public String getMatchingAhl() {
		return matchingAhl;
	}

	/**
	 * @param placed_in_file the placed_in_file to set
	 */
	public void setMatchingAhl(String placed_in_file) {
		this.matchingAhl = placed_in_file;
	}

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
	 * @return the passenger1
	 */
	public String getPassenger1() {
		return passenger1;
	}

	/**
	 * @param passenger1 the passenger1 to set
	 */
	public void setPassenger1(String passenger1) {
		this.passenger1 = passenger1;
	}

	/**
	 * @return the passenger2
	 */
	public String getPassenger2() {
		return passenger2;
	}

	/**
	 * @param passenger2 the passenger2 to set
	 */
	public void setPassenger2(String passenger2) {
		this.passenger2 = passenger2;
	}

	/**
	 * @return the passenger3
	 */
	public String getPassenger3() {
		return passenger3;
	}

	/**
	 * @param passenger3 the passenger3 to set
	 */
	public void setPassenger3(String passenger3) {
		this.passenger3 = passenger3;
	}

	public void addSegment() {
		this.itinerarylist.add(new FwdFormSegment());
	}

	public void removeSegment(int itinIndex) {
		if (itinIndex < itinerarylist.size()) {
			itinerarylist.remove(itinIndex);
		}
		
	}
}
