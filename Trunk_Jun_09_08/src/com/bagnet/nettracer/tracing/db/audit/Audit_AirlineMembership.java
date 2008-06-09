/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table = "Audit_AirlineMembership"
 */
public class Audit_AirlineMembership implements Serializable {
	private int audit_membership_id;
	private int Membership_ID;
	private String membershipnum;
	private String membershipstatus;
	private String companycode_ID;

	/**
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 *               column="audit_membership_id"
	 * @hibernate.generator-param name="sequence"
	 *                            value="audit_airlinemembership_0"
	 * @return Returns the audit_membership_id.
	 */
	public int getAudit_membership_id() {
		return audit_membership_id;
	}

	/**
	 * @param audit_membership_id
	 *          The audit_membership_id to set.
	 */
	public void setAudit_membership_id(int audit_membership_id) {
		this.audit_membership_id = audit_membership_id;
	}


	/**
	 * @return Returns the companycode_ID.
	 * 
	 * @hibernate.property type="string" length="2"
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
	 * @hibernate.property type="integer"
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

	public boolean equals(Object obj) {
		Audit_AirlineMembership aam = (Audit_AirlineMembership) obj;
		boolean ret = true;

		if (AuditOHDUtils.notEqualObjects(aam.getMembershipnum(), this.getMembershipnum())
				|| AuditOHDUtils.notEqualObjects(aam.getMembershipstatus(), this.getMembershipstatus())
				|| AuditOHDUtils.notEqualObjects(aam.getCompanycode_ID(), this.getCompanycode_ID())) {
			ret = false;
		}
		return ret;
	}
}