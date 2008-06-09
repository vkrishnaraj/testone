/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * @hibernate.class table="audit_Incident_Claimcheck"
 */
public class Audit_Incident_Claimcheck implements Serializable {
	private int audit_claimcheck_id;
	private int Claimcheck_ID;
	private String claimchecknum;
	private String OHD_ID;
	
	private Audit_Incident audit_incident;

	/**
	 * @return Returns the audit_claimcheck_id.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * @hibernate.generator-param name="sequence"
	 *                            value="audit_incident_claimcheck_0"
	 * 
	 * 
	 *  
	 */
	public int getAudit_claimcheck_id() {
		return audit_claimcheck_id;
	}

	/**
	 * @param audit_claimcheck_id
	 *          The audit_claimcheck_id to set.
	 */
	public void setAudit_claimcheck_id(int audit_claimcheck_id) {
		this.audit_claimcheck_id = audit_claimcheck_id;
	}

	
	/**
	 * @return Returns the audit_incident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_Incident"
	 *                        column="audit_incident_id"
	 */
	public Audit_Incident getAudit_incident() {
		return audit_incident;
	}
	/**
	 * @param audit_incident The audit_incident to set.
	 */
	public void setAudit_incident(Audit_Incident audit_incident) {
		this.audit_incident = audit_incident;
	}
	
	/**
	 * @return Returns the claimcheck_ID.
	 * 
	 * @hibernate.property type="integer"
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
}