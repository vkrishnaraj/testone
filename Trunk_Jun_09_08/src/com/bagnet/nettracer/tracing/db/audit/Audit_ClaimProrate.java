/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 * 
 * @hibernate.class table="audit_ClaimProrate"
 */
public class Audit_ClaimProrate implements Serializable {

	private int audit_claimprorate_id;
	private int Claimprorate_ID;
	private Date createdate;
	private String companycode_ID;
	private String file_reference;
	private int pir_attached;
	private int claim_attached;
	private int confirmpayment_attached;
	private int all_prorate;
	private String all_prorate_reason;
	private int remit;
	private double remit_amount;
	private String currency_ID;
	private String remit_to;
	private int clearing_bill;
	private String prorate_officer;
	private String sita_address;
	private String fax_number;
	private double total_percentage;
	private double total_share;
	private Set prorate_itineraries;

	/**
	 * @return Returns the audit_claimprorate_id.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_claimprorate_0"
	 */
	public int getAudit_claimprorate_id() {
		return audit_claimprorate_id;
	}

	/**
	 * @param audit_claimprorate_id
	 *          The audit_claimprorate_id to set.
	 */
	public void setAudit_claimprorate_id(int audit_claimprorate_id) {
		this.audit_claimprorate_id = audit_claimprorate_id;
	}

	/**
	 * @return Returns the prorate_itineraries.
	 * 
	 * @hibernate.set cascade="all" inverse="true" order-by="prorate_itinerary_ID"
	 * @hibernate.key column="audit_claimprorate_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_Prorate_Itinerary"
	 *  
	 */
	public Set getProrate_itineraries() {
		return prorate_itineraries;
	}

	/**
	 * @param prorate_itineraries
	 *          The prorate_itineraries to set.
	 */
	public void setProrate_itineraries(Set prorate_itineraries) {
		this.prorate_itineraries = prorate_itineraries;
	}

	public ArrayList getPi_list() {
		return new ArrayList((prorate_itineraries != null ? prorate_itineraries : new HashSet()));
	}

	/**
	 * @return Returns the all_prorate.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getAll_prorate() {
		return all_prorate;
	}

	/**
	 * @param all_prorate
	 *          The all_prorate to set.
	 */
	public void setAll_prorate(int all_prorate) {
		this.all_prorate = all_prorate;
	}

	/**
	 * @return Returns the all_prorate_reason.
	 * 
	 * @hibernate.property type="string" length="255"
	 */
	public String getAll_prorate_reason() {
		return all_prorate_reason;
	}

	/**
	 * @param all_prorate_reason
	 *          The all_prorate_reason to set.
	 */
	public void setAll_prorate_reason(String all_prorate_reason) {
		this.all_prorate_reason = all_prorate_reason;
	}

	/**
	 * @return Returns the claim_attached.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getClaim_attached() {
		return claim_attached;
	}

	/**
	 * @param claim_attached
	 *          The claim_attached to set.
	 */
	public void setClaim_attached(int claim_attached) {
		this.claim_attached = claim_attached;
	}

	/**
	 * @return Returns the claimprorate_ID.
	 * 
	 * @hibernate.property type="integer" column="Claimprorate_ID"
	 */
	public int getClaimprorate_ID() {
		return Claimprorate_ID;
	}

	/**
	 * @param claimprorate_ID
	 *          The claimprorate_ID to set.
	 */
	public void setClaimprorate_ID(int claimprorate_ID) {
		Claimprorate_ID = claimprorate_ID;
	}

	/**
	 * @return Returns the clearing_bill.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getClearing_bill() {
		return clearing_bill;
	}

	/**
	 * @param clearing_bill
	 *          The clearing_bill to set.
	 */
	public void setClearing_bill(int clearing_bill) {
		this.clearing_bill = clearing_bill;
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
	 * @return Returns the confirmpayment_attached.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getConfirmpayment_attached() {
		return confirmpayment_attached;
	}

	/**
	 * @param confirmpayment_attached
	 *          The confirmpayment_attached to set.
	 */
	public void setConfirmpayment_attached(int confirmpayment_attached) {
		this.confirmpayment_attached = confirmpayment_attached;
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
	 * @return Returns the currency_ID.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getCurrency_ID() {
		return currency_ID;
	}

	/**
	 * @param currency_ID
	 *          The currency_ID to set.
	 */
	public void setCurrency_ID(String currency_ID) {
		this.currency_ID = currency_ID;
	}

	/**
	 * @return Returns the fax_number.
	 * 
	 * @hibernate.property type="string" length="15"
	 */
	public String getFax_number() {
		return fax_number;
	}

	/**
	 * @param fax_number
	 *          The fax_number to set.
	 */
	public void setFax_number(String fax_number) {
		this.fax_number = fax_number;
	}

	/**
	 * @return Returns the file_reference.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getFile_reference() {
		return file_reference;
	}

	/**
	 * @param file_reference
	 *          The file_reference to set.
	 */
	public void setFile_reference(String file_reference) {
		this.file_reference = file_reference;
	}

	/**
	 * @return Returns the pir_attached.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getPir_attached() {
		return pir_attached;
	}

	/**
	 * @param pir_attached
	 *          The pir_attached to set.
	 */
	public void setPir_attached(int pir_attached) {
		this.pir_attached = pir_attached;
	}

	/**
	 * @return Returns the prorate_officer.
	 * 
	 * @hibernate.property type="string" length="255"
	 */
	public String getProrate_officer() {
		return prorate_officer;
	}

	/**
	 * @param prorate_officer
	 *          The prorate_officer to set.
	 */
	public void setProrate_officer(String prorate_officer) {
		this.prorate_officer = prorate_officer;
	}

	/**
	 * @return Returns the remit.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getRemit() {
		return remit;
	}

	/**
	 * @param remit
	 *          The remit to set.
	 */
	public void setRemit(int remit) {
		this.remit = remit;
	}

	/**
	 * @return Returns the remit_amount.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getRemit_amount() {
		return remit_amount;
	}

	/**
	 * @param remit_amount
	 *          The remit_amount to set.
	 */
	public void setRemit_amount(double remit_amount) {
		this.remit_amount = remit_amount;
	}

	/**
	 * @return Returns the remit_to.
	 * 
	 * @hibernate.property type="string" length="255"
	 */
	public String getRemit_to() {
		return remit_to;
	}

	/**
	 * @param remit_to
	 *          The remit_to to set.
	 */
	public void setRemit_to(String remit_to) {
		this.remit_to = remit_to;
	}

	/**
	 * @return Returns the sita_address.
	 * 
	 * @hibernate.property type="string" length="255"
	 */
	public String getSita_address() {
		return sita_address;
	}

	/**
	 * @param sita_address
	 *          The sita_address to set.
	 */
	public void setSita_address(String sita_address) {
		this.sita_address = sita_address;
	}

	/**
	 * @return Returns the total_percentage.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getTotal_percentage() {
		return total_percentage;
	}

	/**
	 * @param total_percentage
	 *          The total_percentage to set.
	 */
	public void setTotal_percentage(double total_percentage) {
		this.total_percentage = total_percentage;
	}

	/**
	 * @return Returns the total_share.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getTotal_share() {
		return total_share;
	}

	/**
	 * @param total_share
	 *          The total_share to set.
	 */
	public void setTotal_share(double total_share) {
		this.total_share = total_share;
	}
}