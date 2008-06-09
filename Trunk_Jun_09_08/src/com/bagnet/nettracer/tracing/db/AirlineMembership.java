/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * @hibernate.class table = "AirlineMembership"
 */
public class AirlineMembership implements Serializable {
	private int Membership_ID;
	private String membershipnum = "";
	private String membershipstatus = "";
	private String companycode_ID;

	
	
	/**
	 * @return Returns the companycode_ID.
	 * 
	 *  @hibernate.property type="string" length="2"
	 */
	public String getCompanycode_ID() {
		return companycode_ID;
	}
	/**
	 * @param companycode_ID The companycode_ID to set.
	 */
	public void setCompanycode_ID(String companycode_ID) {
		this.companycode_ID = companycode_ID;
	}

	/**
	 * @return Returns the membership_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="Membership_ID" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="airlinemembership_0"
	 * 
	 *  
	 */
	public int getMembership_ID() {
		return Membership_ID;
	}

	/**
	 * @param membership_ID
	 *          The membership_ID to set.
	 */
	public void setMembership_ID(int membership_ID) {
		Membership_ID = membership_ID;
	}

	/**
	 * @return Returns the membership status.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getMembershipstatus() {
		return membershipstatus;
	}

	/**
	 * @param membershipstatus
	 *          The membershipstatus to set.
	 */
	public void setMembershipstatus(String membershipstatus) {
		this.membershipstatus = membershipstatus;
	}

	/**
	 * @return Returns the membershipnum.
	 * 
	 * @hibernate.property type="string" length="20"
	 */
	public String getMembershipnum() {
		return membershipnum;
	}

	/**
	 * @param membershipnm
	 *          The membershipnm to set.
	 */
	public void setMembershipnum(String membershipnum) {
		this.membershipnum = membershipnum;
	}


}