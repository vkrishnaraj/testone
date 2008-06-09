/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

import com.bagnet.nettracer.tracing.utils.NumberUtils;
import com.cci.utils.parser.ElementNode;

/**
 * @author Administrator
 * 
 * @hibernate.class table = "Claim"
 */
public class Claim implements Serializable {
	private int Claim_ID;
	private double claimamount;
	private String claimcurrency_ID;
	private double total;
	private String ssn;
	private String driverslicense;
	private String dlstate;
	private String commonnum;
	private String countryofissue;
	
	private Incident incident;
	private Status status;
	private Set expenses;
	private ClaimProrate claimprorate;

	public String toXML() {
		StringBuffer sb = new StringBuffer();

		sb.append("<claim>");
		sb.append("<Claim_ID>" + Claim_ID + "</Claim_ID>");
		sb.append("<claimamount>" + claimamount + "</claimamount>");
		sb.append("<claimcurrency_ID>" + claimcurrency_ID + "</claimcurrency_ID>");
		sb.append("<total>" + total + "</total>");
		sb.append("<ssn>" + ssn + "</ssn>");
		sb.append("<driverslicense>" + driverslicense + "</driverslicense>");
		sb.append("<dlstate>" + dlstate + "</dlstate>");
		sb.append("<status_ID>" + status.getStatus_ID() + "</status_ID>");
		sb.append("<status>" + status.getDescription() + "</status>");

		sb.append("<expensepayouts>");
		if (this.getExpenses() != null && this.getExpenses().size() > 0) {
			for (Iterator i = this.getExpenses().iterator(); i.hasNext();) {
				ExpensePayout exp = (ExpensePayout) i.next();
				sb.append(exp.toXML());
			}
		}
		sb.append("</expensepayouts>");

		sb.append((claimprorate != null ? claimprorate.toXML() : ""));

		sb.append("</claim>");

		return sb.toString();
	}
	
	public static Claim XMLtoObject(ElementNode root) {
		Claim obj = new Claim();

		ElementNode child = null, grandchild = null, ggrandchild = null, gggrandchild = null;


		boolean break_main = false;

		Station st = new Station();
		for (ListIterator i = root.get_children().listIterator(); i.hasNext();) {
			child = (ElementNode) i.next();
			if (child.getType().equals("Claim_ID")) {
				obj.setClaim_ID(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("claimamount")) {
				obj.setClaimamount(NumberUtils.parseDouble(child.getTextContents()));
			} else if (child.getType().equals("claimcurrency_ID")) {
				obj.setClaimcurrency_ID(child.getTextContents());
			} else if (child.getType().equals("total")) {
				obj.setTotal(NumberUtils.parseDouble(child.getTextContents()));
			} else if (child.getType().equals("ssn")) {
				obj.setSsn(child.getTextContents());
			} else if (child.getType().equals("driverslicense")) {
				obj.setDriverslicense(child.getTextContents());
			} else if (child.getType().equals("dlstate")) {
				obj.setDlstate(child.getTextContents());
			} else if (child.getType().equals("commonnumber")) {
				obj.setCommonnum(child.getTextContents());
			} else if (child.getType().equals("countryofissue")) {
				obj.setCountryofissue(child.getTextContents());
			} else if (child.getType().equals("status_ID")) {
				Status stat = new Status();
				stat.setStatus_ID(NumberUtils.parseInt(child.getTextContents()));
				obj.setStatus(stat);
			} else if (child.getType().equals("expensepayouts")) {
				ArrayList al = new ArrayList();
				ArrayList c = (ArrayList)child.getChildren();
				for (int z=0;z<c.size();z++) {
					al.add(ExpensePayout.XMLtoObject((ElementNode)c.get(z)));
				}
				obj.setExpenses(new HashSet(al));
			}
			obj.setClaimprorate(ClaimProrate.XMLtoObject(root));
			

		}

		return obj;
	}

	/**
	 * @return Returns the expenses.
	 * 
	 * 
	 * @hibernate.set cascade="all" inverse="true" order-by="createdate"
	 * @hibernate.key column="claim_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.ExpensePayout"
	 *  
	 */
	public Set getExpenses() {
		return expenses;
	}

	/**
	 * @param expenses
	 *          The expenses to set.
	 */
	public void setExpenses(Set expenses) {
		this.expenses = expenses;
	}

	/**
	 * @return Returns the claimprorate.
	 * 
	 * @hibernate.many-to-one cascade="all"
	 *                        class="com.bagnet.nettracer.tracing.db.ClaimProrate"
	 *                        column="claimprorate_ID"
	 */
	public ClaimProrate getClaimprorate() {
		return claimprorate;
	}

	/**
	 * @param claimprorate
	 *          The claimprorate to set.
	 */
	public void setClaimprorate(ClaimProrate claimprorate) {
		this.claimprorate = claimprorate;
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
	 * @param incident
	 *          The incident to set.
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	/**
	 * @return Returns the status.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="status_ID"
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *          The status to set.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return Returns the claim_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="Claim_ID"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="claim_0"
	 * 
	 *  
	 */
	public int getClaim_ID() {
		return Claim_ID;
	}

	/**
	 * @param claim_ID
	 *          The claim_ID to set.
	 */
	public void setClaim_ID(int claim_ID) {
		Claim_ID = claim_ID;
	}

	/**
	 * @return Returns the claimamount.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getClaimamount() {
		return claimamount;
	}

	/**
	 * @param claimamount
	 *          The claimamount to set.
	 */
	public void setClaimamount(double claimamount) {
		this.claimamount = claimamount;
	}

	/**
	 * @return Returns the total.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param total
	 *          The total to set.
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * @return Returns the ssn.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn
	 *          The ssn to set.
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return Returns the dlstate.
	 * 
	 * @hibernate.property type="string" length="2"
	 */
	public String getDlstate() {
		return dlstate;
	}

	/**
	 * @param dlstate
	 *          The dlstate to set.
	 */
	public void setDlstate(String dlstate) {
		this.dlstate = dlstate;
	}

	/**
	 * @return Returns the driverslicense.
	 * 
	 * @hibernate.property type="string" length="10"
	 */
	public String getDriverslicense() {
		return driverslicense;
	}

	/**
	 * @param driverslicense
	 *          The driverslicense to set.
	 */
	public void setDriverslicense(String driverslicense) {
		this.driverslicense = driverslicense;
	}
	
	/**
	 * @return Returns the commonnum.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getCommonnum() {
		return commonnum;
	}

	/**
	 * @param commonnum
	 *          The commonnum to set.
	 */
	public void setCommonnum(String commonnum) {
		this.commonnum = commonnum;
	}

	/**
	 * @return Returns the countryofissue.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getCountryofissue() {
		return countryofissue;
	}

	/**
	 * @param countryofissue
	 *          The countryofissue to set.
	 */
	public void setCountryofissue(String countryofissue) {
		this.countryofissue = countryofissue;
	}
	
	/**
	 * @return Returns the claimcurrency_ID.
	 * @hibernate.property type="string" column="currency_ID"
	 */
	public String getClaimcurrency_ID() {
		return claimcurrency_ID;
	}

	/**
	 * @param claimcurrency_ID
	 *          The claimcurrency_ID to set.
	 */
	public void setClaimcurrency_ID(String claimcurrency_ID) {
		this.claimcurrency_ID = claimcurrency_ID;
	}
}