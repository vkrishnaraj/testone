/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Incident_Claimcheck"
 */
public class Incident_Claimcheck implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7863821205236377195L;
	private int Claimcheck_ID;
	private String claimchecknum="";
	private String claimchecknum_leading;
	private String claimchecknum_ticketingcode;
	private String claimchecknum_carriercode;
	private String claimchecknum_bagnumber;
	private String OHD_ID="";
	private String tempOHD_ID="";
	private Incident incident;
	


	/**
	 * @return Returns the claimcheck_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="incident_claimcheck_0"
	 * 
	 *  
	 */
	public int getClaimcheck_ID() {
		return Claimcheck_ID;
	}

	/**
	 * @param claimcheck_ID
	 *          The claimcheck_ID to set.
	 */
	public void setClaimcheck_ID(int claimcheck_ID) {
		Claimcheck_ID = claimcheck_ID;
	}

	
	/**
	 * @return Returns the incident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Incident"
	 *                        column="incident_ID" not-null="true"
	 */
	public Incident getIncident() {
		return incident;
	}
	/**
	 * @param incident The incident to set.
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	/**
	 * @return Returns the claimchecknum.
	 * 
	 * @hibernate.property type="string" length="13"
	 */
	public String getClaimchecknum() {
		return claimchecknum;
	}

	/**
	 * @param claimchecknum
	 *          The claimchecknum to set.
	 */
	public void setClaimchecknum(String claimchecknum) {
		if (claimchecknum != null)
			claimchecknum = TracerUtils.removeSpaces(claimchecknum);
		this.claimchecknum = claimchecknum;
		setClaimSearchParams(claimchecknum);
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
	 * @return Returns the oHD_ID.
	 * 
	 * @hibernate.property type="string"
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

	
	public String getTempOHD_ID() {
		return tempOHD_ID;
	}

	public void setTempOHD_ID(String tempOHD_ID) {
		this.tempOHD_ID = tempOHD_ID;
	}
	
}