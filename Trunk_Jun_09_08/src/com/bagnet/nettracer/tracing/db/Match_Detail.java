/*
 * Created on Aug 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="match_detail"
 */
public class Match_Detail implements Serializable {

	private int Matchdetail_ID;
	private String item;
	private String mbr_info;
	private String ohd_info;
	private double percentage;
	
	private Match match;

	public String getReportPercentage() {
		return "" + percentage;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the item.
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item
	 *          The item to set.
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer"
	 *               column="Matchdetail_ID"
	 * @hibernate.generator-param name="sequence" value="match_detail_0"
	 * 
	 * @return Returns the matchdetail_ID.
	 */
	public int getMatchdetail_ID() {
		return Matchdetail_ID;
	}

	/**
	 * 
	 * @param matchdetail_ID
	 *          The matchdetail_ID to set.
	 */
	public void setMatchdetail_ID(int matchdetail_ID) {
		Matchdetail_ID = matchdetail_ID;
	}

	
	/**
	 * @return Returns the match.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Match"
	 *                        column="match_id" not-null="true"
	 */
	public Match getMatch() {
		return match;
	}
	/**
	 * @param match The match to set.
	 */
	public void setMatch(Match match) {
		this.match = match;
	}
	
	/**
	 * @hibernate.property type="double"
	 * @return Returns the percentage.
	 */
	public double getPercentage() {
		return percentage;
	}

	/**
	 * @param percentage
	 *          The percentage to set.
	 */
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	/**
	 * @return Returns the mbr_info.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getMbr_info() {
		return mbr_info;
	}

	/**
	 * @param mbr_info
	 *          The mbr_info to set.
	 */
	public void setMbr_info(String mbr_info) {
		this.mbr_info = mbr_info;
	}

	/**
	 * @return Returns the ohd_info.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getOhd_info() {
		return ohd_info;
	}

	/**
	 * @param ohd_info
	 *          The ohd_info to set.
	 */
	public void setOhd_info(String ohd_info) {
		this.ohd_info = ohd_info;
	}

	/**
	 * @return Returns the disppercent.
	 */
	public String getDisppercent() {
		return TracerUtils.format(getPercentage(), "%");
	}

}