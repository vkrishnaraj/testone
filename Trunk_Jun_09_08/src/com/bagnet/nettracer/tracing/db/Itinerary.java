/*
 * Created on Jul 13, 2004
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
import com.cci.utils.parser.ElementNode;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Itinerary"
 */
public class Itinerary implements Serializable {
	private int Itinerary_ID;
	private int itinerarytype; //0-passenger, 1 - bagrouting
	private String legfrom;
	private String legto;
	private int legfrom_type;
	private int legto_type;
	private Date departdate;
	private Date arrivedate;
	private String airline;
	private String flightnum;
	private Date schdeparttime;
	private Date scharrivetime;
	private Date actdeparttime;
	private Date actarrivetime;
	private Incident incident;
	
	private String _DATEFORMAT; //for date time format purpose only, not
	// literally part of this object
	private String _TIMEFORMAT; //for date time format purpose only, not

	public String toXML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<itinerary_leg>");
		sb.append("<Itinerary_ID>" + Itinerary_ID + "</Itinerary_ID>");
		sb.append("<itinerarytype>" + itinerarytype + "</itinerarytype>");
		sb.append("<from>" + legfrom + "</from>");
		sb.append("<fromtype>" + legfrom_type + "</fromtype>");
		sb.append("<to>" + legto + "</to>");
		sb.append("<totype>" + legto_type + "</totype>");
		sb.append("<departdate>" + (departdate == null ? "" : departdate.toString()) + "</departdate>");
		sb.append("<arrivedate>" + (arrivedate == null ? "" : arrivedate.toString()) + "</arrivedate>");
		sb.append("<airline>" + airline.toString() + "</airline>");
		sb.append("<flightnum>" + flightnum.toString() + "</flightnum>");
		sb.append("<schdeparttime>" + (schdeparttime == null ? "" : schdeparttime.toString()) + "</schdeparttime>");
		sb.append("<scharrivetime>" + (scharrivetime == null ? "" : scharrivetime.toString()) + "</scharrivetime>");
		sb.append("<actdeparttime>" + (actdeparttime == null ? "" : actdeparttime.toString()) + "</actdeparttime>");
		sb.append("<actarrivetime>" + (actarrivetime == null ? "" : actarrivetime.toString()) + "</actarrivetime>");
		sb.append("</itinerary_leg>");

		return sb.toString();
	}

	public static Itinerary XMLtoObject(ElementNode root) {
		Itinerary obj = new Itinerary();

		ElementNode child = null, grandchild = null, ggrandchild = null, gggrandchild = null;

		for (ListIterator i = root.get_children().listIterator(); i.hasNext();) {
			child = (ElementNode) i.next();
			if (child.getType().equals("Itinerary_ID")) {
				obj.setItinerary_ID(NumberUtils.parseInt(child.getTextContents()));
			} else if (child.getType().equals("itinerarytype")) {
				obj.setItinerarytype(NumberUtils.parseInt(child.getTextContents()));	
			} else if (child.getType().equals("from")) {
				obj.setLegfrom(child.getTextContents());
			} else if (child.getType().equals("fromtype")) {
				obj.setLegfrom_type(NumberUtils.parseInt(child.getTextContents()));	
			} else if (child.getType().equals("to")) {
				obj.setLegto(child.getTextContents());
			} else if (child.getType().equals("totype")) {
				obj.setLegto_type(NumberUtils.parseInt(child.getTextContents()));	
			} else if (child.getType().equals("departdate")) {
				obj.setDepartdate(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_DATEFORMAT, null));
			} else if (child.getType().equals("arrivedate")) {
				obj.setArrivedate(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_DATEFORMAT, null));
			} else if (child.getType().equals("airline")) {
				obj.setAirline(child.getTextContents());
			} else if (child.getType().equals("flightnum")) {
				obj.setFlightnum(child.getTextContents());
			} else if (child.getType().equals("schdeparttime")) {
				obj.setSchdeparttime(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_TIMEFORMAT, null));
			} else if (child.getType().equals("scharrivetime")) {
				obj.setScharrivetime(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_TIMEFORMAT, null));
			} else if (child.getType().equals("actdeparttime")) {
				obj.setActdeparttime(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_TIMEFORMAT, null));
			} else if (child.getType().equals("actarrivetime")) {
				obj.setActarrivetime(DateUtils.convertToDate(child.getTextContents(),
						TracingConstants.DB_TIMEFORMAT, null));
				
			}
			

		}
		
		
		
		return obj;
	}

	
	// literally part of this objec

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
	 * @return Returns the flightnum.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getFlightnum() {
		return flightnum;
	}

	/**
	 * @param flightnum
	 *          The flightnum to set.
	 */
	public void setFlightnum(String flightnum) {
		this.flightnum = (flightnum != null ? flightnum.toUpperCase() : flightnum);
	}

	/**
	 * @return Returns the itinerary_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="Itinerary_ID"
	 *               unsaved-value="0"
	 * 
	 * @hibernate.generator-param name="sequence" value="ITINERARY_0"
	 */
	public int getItinerary_ID() {
		return Itinerary_ID;
	}

	/**
	 * @param itinerary_ID
	 *          The itinerary_ID to set.
	 */
	public void setItinerary_ID(int itinerary_ID) {
		Itinerary_ID = itinerary_ID;
	}

	/**
	 * @return Returns the itinerarytype.
	 * 
	 * @hibernate.property type="integer" length="1"
	 */
	public int getItinerarytype() {
		return itinerarytype;
	}

	/**
	 * @param itinerarytype
	 *          The itinerarytype to set.
	 */
	public void setItinerarytype(int itinerarytype) {
		this.itinerarytype = itinerarytype;
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
		this.legfrom = (legfrom != null ? legfrom.toUpperCase() : legfrom);
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
		this.legto = (legto != null ? legto.toUpperCase() : legto);
	}

	/**
	 * @return Returns the legfrom_type.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getLegfrom_type() {
		return legfrom_type;
	}

	/**
	 * @param legfrom_type
	 *          The legfrom_type to set.
	 */
	public void setLegfrom_type(int legfrom_type) {
		this.legfrom_type = legfrom_type;
	}

	/**
	 * @return Returns the legto_type.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getLegto_type() {
		return legto_type;
	}

	/**
	 * @param legto_type
	 *          The legto_type to set.
	 */
	public void setLegto_type(int legto_type) {
		this.legto_type = legto_type;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the airline.
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
	 * @return Returns the arrivedate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getArrivedate() {
		return arrivedate;
	}

	/**
	 * @param arrivedate
	 *          The arrivedate to set.
	 */
	public void setArrivedate(Date arrivedate) {
		this.arrivedate = arrivedate;
	}

	public void setDisarrivedate(String s) {
		setArrivedate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	public String getDisarrivedate() {
		return DateUtils.formatDate(getArrivedate(), get_DATEFORMAT(), null, null);
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
	 * @return Returns the actarrivetime.
	 * 
	 * @hibernate.property type="time"
	 */
	public Date getActarrivetime() {
		return actarrivetime;
	}

	/**
	 * @param actarrivetime
	 *          The actarrivetime to set.
	 */
	public void setActarrivetime(Date actarrivetime) {
		this.actarrivetime = actarrivetime;
	}

	public void setDisactarrivetime(String s) {
		setActarrivetime(DateUtils.convertToDate(s, get_TIMEFORMAT(), null));
	}

	public String getDisactarrivetime() {
		return DateUtils.formatDate(getActarrivetime(), get_TIMEFORMAT(), null, null);
	}

	/**
	 * @return Returns the actdeparttime.
	 * 
	 * @hibernate.property type="time"
	 */
	public Date getActdeparttime() {
		return actdeparttime;
	}

	/**
	 * @param actdeparttime
	 *          The actdeparttime to set.
	 */
	public void setActdeparttime(Date actdeparttime) {
		this.actdeparttime = actdeparttime;
	}

	public void setDisactdeparttime(String s) {
		setActdeparttime(DateUtils.convertToDate(s, get_TIMEFORMAT(), null));
	}

	public String getDisactdeparttime() {
		return DateUtils.formatDate(getActdeparttime(), get_TIMEFORMAT(), null, null);
	}

	/**
	 * @return Returns the scharrivetime.
	 * 
	 * @hibernate.property type="time"
	 */
	public Date getScharrivetime() {
		return scharrivetime;
	}

	/**
	 * @param scharrivetime
	 *          The scharrivetime to set.
	 */
	public void setScharrivetime(Date scharrivetime) {
		this.scharrivetime = scharrivetime;
	}

	public void setDisscharrivetime(String s) {
		setScharrivetime(DateUtils.convertToDate(s, get_TIMEFORMAT(), null));
	}

	public String getDisscharrivetime() {
		return DateUtils.formatDate(getScharrivetime(), get_TIMEFORMAT(), null, null);
	}

	/**
	 * @return Returns the schdeparttime.
	 * 
	 * @hibernate.property type="time"
	 */
	public Date getSchdeparttime() {
		return schdeparttime;
	}

	/**
	 * @param schdeparttime
	 *          The schdeparttime to set.
	 */
	public void setSchdeparttime(Date schdeparttime) {
		this.schdeparttime = schdeparttime;
	}

	public void setDisschdeparttime(String s) {
		setSchdeparttime(DateUtils.convertToDate(s, get_TIMEFORMAT(), null));
	}

	public String getDisschdeparttime() {
		return DateUtils.formatDate(getSchdeparttime(), get_TIMEFORMAT(), null, null);
	}

	/**
	 * @return Returns the _DATEFORMAT.
	 */
	public String get_DATEFORMAT() {
		if (_DATEFORMAT == null && incident != null) _DATEFORMAT = incident.getAgent().getDateformat()
				.getFormat();
		return _DATEFORMAT;
	}

	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	/**
	 * @return Returns the _TIMEFORMAT.
	 */
	public String get_TIMEFORMAT() {
		if (_TIMEFORMAT == null && incident != null) _TIMEFORMAT = incident.getAgent().getTimeformat()
				.getFormat();
		return _TIMEFORMAT;
	}

	/**
	 * @param _timeformat
	 *          The _TIMEFORMAT to set.
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

}