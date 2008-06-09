/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.Date;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="audit_Prorate_Itinerary"
 */
public class Audit_Prorate_Itinerary implements Serializable {
	private int audit_prorate_itinerary_id;

	private int prorate_Itinerary_ID;
	private String airline;
	private String flightnum;
	private Date departdate;
	private String legfrom;
	private String legto;
	private double miles;
	private double percentage;
	private double share;
	private String currency_ID;

	private String _DATEFORMAT;
	private Audit_ClaimProrate audit_claimprorate;
	
	/**
	 * @return Returns the audit_prorate_itinerary_id.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * @hibernate.generator-param name="sequence"
	 *                            value="audit_prorate_itinerary_0"
	 * 
	 * 
	 *  
	 */
	public int getAudit_prorate_itinerary_id() {
		return audit_prorate_itinerary_id;
	}

	/**
	 * @param audit_prorate_itinerary_id
	 *          The audit_prorate_itinerary_id to set.
	 */
	public void setAudit_prorate_itinerary_id(int audit_prorate_itinerary_id) {
		this.audit_prorate_itinerary_id = audit_prorate_itinerary_id;
	}

	/**
	 * @return Returns the audit_claimprorate.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.audit.Audit_ClaimProrate"
	 *                        column="audit_claimprorate_id"
	 */
	public Audit_ClaimProrate getAudit_claimprorate() {
		return audit_claimprorate;
	}
	/**
	 * @param audit_claimprorate The audit_claimprorate to set.
	 */
	public void setAudit_claimprorate(Audit_ClaimProrate audit_claimprorate) {
		this.audit_claimprorate = audit_claimprorate;
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
	 * @return Returns the departdate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getDepartdate() {
		return departdate;
	}

	/**
	 * @param departdate
	 *          The departdate to set.
	 */
	public void setDepartdate(Date departdate) {
		this.departdate = departdate;
	}

	public void setDisdepartdate(String s) {
		setDepartdate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	public String getDisdepartdate() {
		return DateUtils.formatDate(getDepartdate(), get_DATEFORMAT(), null, null);
	}

	/**
	 * @return Returns the airline.
	 * @hibernate.property type="string"
	 */

	public String getAirline() {
		return airline;
	}

	/**
	 * @param airline
	 *          The airline to set.
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * @return Returns the flightnum.
	 * 
	 * @hibernate.property type="string" length="7"
	 */
	public String getFlightnum() {
		return flightnum;
	}

	/**
	 * @param flightnum
	 *          The flightnum to set.
	 */
	public void setFlightnum(String flightnum) {
		this.flightnum = flightnum;
	}

	/**
	 * @return Returns the legfrom.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getLegfrom() {
		return legfrom;
	}

	/**
	 * @param legfrom
	 *          The legfrom to set.
	 */
	public void setLegfrom(String legfrom) {
		this.legfrom = legfrom;
	}

	/**
	 * @return Returns the legto.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getLegto() {
		return legto;
	}

	/**
	 * @param legto
	 *          The legto to set.
	 */
	public void setLegto(String legto) {
		this.legto = legto;
	}

	/**
	 * @return Returns the miles.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getMiles() {
		return miles;
	}

	/**
	 * @param miles
	 *          The miles to set.
	 */
	public void setMiles(double miles) {
		this.miles = miles;
	}

	public String getDismiles() {
		return TracingConstants.DECIMALFORMAT.format(getMiles());
	}

	public void setDismiles(String s) {
		setMiles(TracerUtils.convertToDouble(s));
	}

	/**
	 * @return Returns the percentage.
	 * 
	 * @hibernate.property type="double"
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

	public String getDispercentage() {
		return TracingConstants.DECIMALFORMAT.format(getPercentage());
	}

	public void setDispercentage(String s) {
		setPercentage(TracerUtils.convertToDouble(s));
	}

	/**
	 * @return Returns the prorate_Itinerary_ID.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getProrate_Itinerary_ID() {
		return prorate_Itinerary_ID;
	}

	/**
	 * @param prorate_Itinerary_ID
	 *          The prorate_Itinerary_ID to set.
	 */
	public void setProrate_Itinerary_ID(int prorate_Itinerary_ID) {
		this.prorate_Itinerary_ID = prorate_Itinerary_ID;
	}

	/**
	 * @return Returns the share.
	 * 
	 * @hibernate.property type="double"
	 */
	public double getShare() {
		return share;
	}

	/**
	 * @param share
	 *          The share to set.
	 */
	public void setShare(double share) {
		this.share = share;
	}

	public String getDisshare() {
		return TracingConstants.DECIMALFORMAT.format(getShare());
	}

	public void getDisshare(String s) {
		setShare(TracerUtils.convertToDouble(s));
	}

	/**
	 * @return Returns the _DATEFORMAT.
	 */
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}
}