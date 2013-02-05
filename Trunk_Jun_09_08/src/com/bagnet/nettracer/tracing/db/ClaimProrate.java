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
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.NumberUtils;
import com.cci.utils.parser.ElementNode;

/**
 * @author Administrator
 * 
 * @hibernate.class table="ClaimProrate"
 */
public class ClaimProrate implements Serializable {
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

	public String toXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<claimprorate>");
		sb.append("<Claimprorate_ID>" + Claimprorate_ID + "</Claimprorate_ID>");
		sb.append("<createdate>" + createdate.toString() + "</createdate>");
		sb.append("<companycode_ID>" + companycode_ID + "</companycode_ID>");
		sb.append("<pir_attached>" + pir_attached + "</pir_attached>");
		sb.append("<claim_attached>" + claim_attached + "</claim_attached>");
		sb.append("<confirmpayment_attached>" + confirmpayment_attached + "</confirmpayment_attached>");
		sb.append("<all_prorate>" + all_prorate + "</all_prorate>");
		sb.append("<all_prorate_reason>" + all_prorate_reason + "</all_prorate_reason>");
		sb.append("<remit>" + remit + "</remit>");
		sb.append("<remit_amount>" + remit_amount + "</remit_amount>");
		sb.append("<currency_ID>" + currency_ID + "</currency_ID>");
		sb.append("<remit_to>" + remit_to + "</remit_to>");
		sb.append("<clearing_bill>" + clearing_bill + "</clearing_bill>");
		sb.append("<prorate_officer>" + prorate_officer + "</prorate_officer>");
		sb.append("<sita_address>" + sita_address + "</sita_address>");
		sb.append("<fax_number>" + fax_number + "</fax_number>");
		sb.append("<total_percentage>" + total_percentage + "</total_percentage>");
		sb.append("<total_share>" + total_share + "</total_share>");

		sb.append("<prorate_itineraries>");
		if (this.getProrate_itineraries() != null && this.getProrate_itineraries().size() > 0) {
			for (Iterator i = this.getProrate_itineraries().iterator(); i.hasNext();) {
				Prorate_Itinerary itinerary = (Prorate_Itinerary) i.next();
				sb.append(itinerary.toXML());
			}
		}
		sb.append("</prorate_itineraries>");
		sb.append("</claimprorate>");
		return sb.toString();

	}

	public static ClaimProrate XMLtoObject(ElementNode root) {
		ClaimProrate obj = new ClaimProrate();

		ElementNode child = null, grandchild = null, ggrandchild = null, gggrandchild = null;

		Station st = new Station();
		for (ListIterator i = root.get_children().listIterator(); i.hasNext();) {
			child = (ElementNode) i.next();
			if (child.getType().equals("Claimprorate_ID")) {
				obj.setClaimprorate_ID(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("createdate")) {
				obj.setCreatedate(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_DATEFORMAT, null));
			} else if (child.getType().equals("companycode_ID")) {
				obj.setCompanycode_ID(child.getTextContents());
			} else if (child.getType().equals("pir_attached")) {
				obj.setPir_attached(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("claim_attached")) {
				obj.setClaim_attached(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("confirmpayment_attached")) {
				obj.setConfirmpayment_attached(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("all_prorate")) {
				obj.setAll_prorate(NumberUtils.parseInt(child.getTextContents())); 
			} else if (child.getType().equals("all_prorate_reason")) {
				obj.setAll_prorate_reason(child.getTextContents());
			} else if (child.getType().equals("remit")) {
				obj.setRemit(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("remit_amount")) {
				obj.setRemit_amount(NumberUtils.parseDouble(child.getTextContents()));
			} else if (child.getType().equals("currency_ID")) {
				obj.setCurrency_ID(child.getTextContents());
			} else if (child.getType().equals("remit_to")) {
				obj.setRemit_to(child.getTextContents());
			} else if (child.getType().equals("clearing_bill")) {
				obj.setClearing_bill(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("prorate_officer")) {
				obj.setProrate_officer(child.getTextContents());
			} else if (child.getType().equals("sita_address")) {
				obj.setSita_address(child.getTextContents());
			} else if (child.getType().equals("fax_number")) {
				obj.setFax_number(child.getTextContents());	
			} else if (child.getType().equals("total_percentage")) {
				obj.setTotal_percentage(NumberUtils.parseDouble(child.getTextContents()));
			} else if (child.getType().equals("total_share")) {
				obj.setTotal_share(NumberUtils.parseDouble(child.getTextContents()));	
			} else if (child.getType().equals("prorate_itineraries")) {
				ArrayList al = new ArrayList();
				ArrayList c = (ArrayList)child.getChildren();
				for (int z=0;z<c.size();z++) {
					al.add(Prorate_Itinerary.XMLtoObject((ElementNode)c.get(z)));
				}
				obj.setProrate_itineraries(new HashSet(al));
			}
			

		}


		return obj;
	}

	/**
	 * @return Returns the prorate_itineraries.
	 * 
	 * @hibernate.set cascade="all" order-by="prorate_itinerary_ID"
	 * @hibernate.key column="claimprorate_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Prorate_Itinerary"
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
	 * @hibernate.property type="byte"
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
	 * @hibernate.property type="byte"
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
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 *               column="Claimprorate_ID"
	 * @hibernate.generator-param name="sequence" value="claimprorate_0"
	 * 
	 *  
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
	 * @hibernate.property type="byte"
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
	 * @hibernate.property type="byte"
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
	 * @hibernate.property type="byte"
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
	 * @hibernate.property type="byte"
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