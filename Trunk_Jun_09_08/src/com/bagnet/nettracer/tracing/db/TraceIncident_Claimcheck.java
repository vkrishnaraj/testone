/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Incident_Claimcheck"
 */
public class TraceIncident_Claimcheck implements Serializable {
	private int Claimcheck_ID;
	private String claimchecknum="";
	private String OHD_ID="";
	private String tempOHD_ID="";
	private TraceIncident incident;
	


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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.TraceIncident"
	 *                        column="incident_ID" not-null="true"
	 */
	public TraceIncident getIncident() {
		return incident;
	}
	/**
	 * @param incident The incident to set.
	 */
	public void setIncident(TraceIncident incident) {
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
		this.claimchecknum = claimchecknum;
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