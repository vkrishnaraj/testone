/*
 * Created on Aug 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="match_history"
 */
public class Match implements Serializable {

	private int match_id;
	private int match_type; // 0 = passive, 1 = active
	private Incident mbr;
	private OHD ohd;

	private int bagnumber; // if this was a bag match, put bag_number here
	private String claimchecknum; // if this was a claimcheck match, put claim
	// check num here

	private double match_percent;
	private Date match_made_on;
	private Status status;

	private Set details;
	private int category;

	private String dispdate;

	public String getReportPercentage() {
		return "" + match_percent;
	}

	public String getReportCategory() {
		return "" + category;
	}

	public String getReportNumber() {
		return mbr.getIncident_ID();
	}

	public List getDisplayDetails() {
		if (details == null || details.size() < 1) return null;

		return new ArrayList(details);
	}

	public JRBeanCollectionDataSource getReportDetails() {
		if (details == null || details.size() < 1) return null;

		return new JRBeanCollectionDataSource(new ArrayList(details));
	}

	/**
	 * @hibernate.property type="integer"
	 * @return Returns the category.
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * @param category
	 *          The category to set.
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * 
	 * @hibernate.set cascade="all" inverse="true" order-by="item"
	 * @hibernate.key column="match_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Match_Detail"
	 * 
	 * @return Returns the details.
	 */
	public Set getDetails() {
		return details;
	}

	/**
	 * @param details
	 *          The details to set.
	 */
	public void setDetails(Set details) {
		this.details = details;
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
	 * @hibernate.id generator-class="native" type="integer" column="match_id"
	 * @hibernate.generator-param name="sequence" value="match_history_0"
	 * 
	 * @return Returns the match_id.
	 */
	public int getMatch_id() {
		return match_id;
	}

	/**
	 * @param match_id
	 *          The match_id to set.
	 */
	public void setMatch_id(int match_id) {
		this.match_id = match_id;
	}

	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the match_made_on.
	 */
	public Date getMatch_made_on() {
		return match_made_on;
	}

	/**
	 * @param match_made_on
	 *          The match_made_on to set.
	 */
	public void setMatch_made_on(Date match_made_on) {
		this.match_made_on = match_made_on;
	}

	/**
	 * @hibernate.property type="double"
	 * @return Returns the match_percent.
	 */
	public double getMatch_percent() {
		return match_percent;
	}

	/**
	 * @param match_percent
	 *          The match_percent to set.
	 */
	public void setMatch_percent(double match_percent) {
		this.match_percent = match_percent;
	}

	/**
	 * @return Returns the match_type.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getMatch_type() {
		return match_type;
	}

	/**
	 * @param match_type
	 *          The match_type to set.
	 */
	public void setMatch_type(int match_type) {
		this.match_type = match_type;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Incident"
	 *                        column="mbr_number"
	 * @return Returns the mbr.
	 */
	public Incident getMbr() {
		return mbr;
	}

	/**
	 * @param mbr
	 *          The mbr to set.
	 */
	public void setMbr(Incident mbr) {
		this.mbr = mbr;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.OHD"
	 *                        column="ohd_id"
	 * @return Returns the ohd.
	 */
	public OHD getOhd() {
		return ohd;
	}

	/**
	 * @param ohd
	 *          The ohd to set.
	 */
	public void setOhd(OHD ohd) {
		this.ohd = ohd;
	}

	/**
	 * @return Returns the bagnumber.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getBagnumber() {
		return bagnumber;
	}

	/**
	 * @param bag_number
	 *          The bagnumber to set.
	 */
	public void setBagnumber(int bagnumber) {
		this.bagnumber = bagnumber;
	}

	/**
	 * @return Returns the claimchecknum.
	 * 
	 * @hibernate.property type="string"
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
	 * @return Returns the dispdate.
	 */
	public String getDispdate() {
		return dispdate;
	}

	/**
	 * @param dispdate
	 *          The dispdate to set.
	 */
	public void setDispdate(String dispdate) {
		this.dispdate = dispdate;
	}

	/**
	 * @return Returns the disppercent.
	 */
	public String getDisppercent() {
		return TracerUtils.format(getMatch_percent(), "%");
	}

}