/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.ListIterator;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.NumberUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.cci.utils.parser.ElementNode;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Prorate_Itinerary"
 */
public class Prorate_Itinerary implements Serializable {
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
	private int claimprorate_ID;

	private String _DATEFORMAT;
	
	private ClaimProrate claimprorate;

	public String toXML() {
		StringBuffer sb = new StringBuffer();

		sb.append("<itinerary_leg>");
		sb.append("<prorate_Itinerary_ID>" + prorate_Itinerary_ID + "</prorate_Itinerary_ID>");
		sb.append("<airline>" + (airline != null ? airline : "") + "</airline>");
		sb.append("<flightnum>" + (flightnum != null ? flightnum : "") + "</flightnum>");
		sb.append("<legfrom>" + (legfrom != null ? legfrom : "") + "</legfrom>");
		sb.append("<legto>" + (legto != null ? legto : "") + "</legto>");
		sb.append("<departdate>" + (departdate == null ? "" : departdate.toString()) + "</departdate>");
		sb.append("<miles>" + miles + "</miles>");
		sb.append("<percentage>" + percentage + "</percentage>");
		sb.append("<share>" + share + "</share>");
		sb.append("<currency_ID>" + currency_ID + "</currency_ID>");
		sb.append("</itinerary_leg>");

		return sb.toString();
	}

	public static Prorate_Itinerary XMLtoObject(ElementNode root) {
		Prorate_Itinerary obj = new Prorate_Itinerary();

		ElementNode child = null, grandchild = null, ggrandchild = null, gggrandchild = null;

		boolean break_main = false;

		for (ListIterator i = root.get_children().listIterator(); i.hasNext();) {
			child = (ElementNode) i.next();
			if (child.getType().equals("prorate_Itinerary_ID")) {
				obj.setProrate_Itinerary_ID(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("airline")) {
				obj.setAirline(child.getTextContents());
			} else if (child.getType().equals("flightnum")) {
				obj.setFlightnum(child.getTextContents());
			} else if (child.getType().equals("legfrom")) {
				obj.setLegfrom(child.getTextContents());
			} else if (child.getType().equals("legto")) {
				obj.setLegto(child.getTextContents());
			} else if (child.getType().equals("departdate")) {
				obj.setDepartdate(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_DATEFORMAT, null));
			} else if (child.getType().equals("miles")) {
				obj.setMiles(NumberUtils.parseDouble(child.getTextContents()));
			} else if (child.getType().equals("percentage")) {
				obj.setPercentage(NumberUtils.parseDouble(child.getTextContents()));
			} else if (child.getType().equals("share")) {
				obj.setShare(NumberUtils.parseDouble(child.getTextContents()));
			} else if (child.getType().equals("currency_ID")) {
				obj.setCurrency_ID(child.getTextContents());
			}

		}

		return obj;
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
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * 
	 * @hibernate.generator-param name="sequence" value="Prorate_Itinerary_0"
	 *  
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
	 * @hibernate.property type="double" column="shared"
	 */
	public double getShare() {
		return share;
	}

	
	/**
	 * @return Returns the claimprorate.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.ClaimProrate"
	 *                        column="claimprorate_ID" not-null="true"
	 */
	public ClaimProrate getClaimprorate() {
		return claimprorate;
	}
	/**
	 * @param claimprorate The claimprorate to set.
	 */
	public void setClaimprorate(ClaimProrate claimprorate) {
		this.claimprorate = claimprorate;
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
	 * @return Returns the claimprorate_ID.
	 */
	public int getClaimprorate_ID() {
		return claimprorate_ID;
	}

	/**
	 * @param claimprorate_ID
	 *          The claimprorate_ID to set.
	 */
	public void setClaimprorate_ID(int claimprorate_ID) {
		this.claimprorate_ID = claimprorate_ID;
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