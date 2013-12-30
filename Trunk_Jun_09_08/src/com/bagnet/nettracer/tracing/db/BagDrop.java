package com.bagnet.nettracer.tracing.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Loupas
 *
 */

@Entity
public class BagDrop {
	@Id
	@GeneratedValue
	private long id;
	private Date createDate;
	private Date lastUpdated;
	private String arrivalStationCode;
	private String originStationCode;
	
	private String airline;
	private String flight;
	
	private int entryMethod;
	private Date bagDropTime;
	
	private Date schArrivalDate;
	private Date actArrivalDate;
	
	@ManyToOne
	@JoinColumn(name = "createAgent_ID", nullable = true)
	private Agent createAgent;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Agent getCreateAgent() {
		return createAgent;
	}
	public void setCreateAgent(Agent createAgent) {
		this.createAgent = createAgent;
	}
	public int getEntryMethod() {
		return entryMethod;
	}
	public void setEntryMethod(int entryMethod) {
		this.entryMethod = entryMethod;
	}
	public Date getBagDropTime() {
		return bagDropTime;
	}
	public void setBagDropTime(Date bagDropTime) {
		this.bagDropTime = bagDropTime;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public Date getSchArrivalDate() {
		return schArrivalDate;
	}
	public void setSchArrivalDate(Date schArrivalDate) {
		this.schArrivalDate = schArrivalDate;
	}
	public Date getActArrivalDate() {
		return actArrivalDate;
	}
	public void setActArrivalDate(Date actArrivalDate) {
		this.actArrivalDate = actArrivalDate;
	}
	public String getArrivalStationCode() {
		return arrivalStationCode;
	}
	public void setArrivalStationCode(String arrivalStationCode) {
		this.arrivalStationCode = arrivalStationCode;
	}
	public String getOriginStationCode() {
		return originStationCode;
	}
	public void setOriginStationCode(String originStationCode) {
		this.originStationCode = originStationCode;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	
	/**
	 * Returns a String in the format hh:mm representing the time to carousel (bagdrop time minus arrival)
	 * If either bagDropTime or actArrivalTime (and schArrivalTime) are null, return empty string
	 * If actArrivalTime exist, use actArrivalTime for calculation, otherwise use schArrivalTime
	 * If negative time, return 00:00
	 * 
	 * TODO consider using Joda
	 * 
	 * @return
	 */
	public String getDispTimeToCarousel(){
		if(bagDropTime == null || (actArrivalDate == null && schArrivalDate == null)){
			return "&nbsp;";//needed for displaying empty cells in DisplayTag
		}
		
		long time = getTimeToCarousel();
		if(time <= 0){
			return "00:00";
		}
		
		long mins = time%60;
		long hours = time/60;
		
		return String.format("%02d:%02d", hours, mins);
	}
	
	/**
	 * If actArrivalTime exist, use actArrivalTime for calculation, otherwise use schArrivalTime
	 * 
	 * @return
	 */
	public long getTimeToCarousel(){
		if(bagDropTime == null || (actArrivalDate == null && schArrivalDate == null)){
			return 0;
		}
		
		long arrivalTime = actArrivalDate!=null?actArrivalDate.getTime():schArrivalDate.getTime();
		
		return (bagDropTime.getTime() - arrivalTime)/60000;
	}
	
	/**
	 * ************************************************
	 * The following are for date/time display purposes
	 * ************************************************
	 */
	@Transient
	private String _DATEFORMAT;
	@Transient
	private String _TIMEFORMAT;
	@Transient
	private java.util.TimeZone _TIMEZONE;
	@Transient
	private String sbagDropDate;
	@Transient
	private String sbagDropTime;
	
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}
	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}
	
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}
	public void set_TIMEFORMAT(String _TIMEFORMAT) {
		this._TIMEFORMAT = _TIMEFORMAT;
	}
	
	public java.util.TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}
	public void set_TIMEZONE(java.util.TimeZone _TIMEZONE) {
		this._TIMEZONE = _TIMEZONE;
	}
	
	/**
	 * This is needed in order to empty cells to display properly in DisplayTags tables since the show empty cell flag apparently does not work with IE
	 * 
	 * @return
	 */
	public String getDispActArrivalDateTimeCell(){
		String s = DateUtils.formatDate(getActArrivalDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		return s!=null?s:"&nbsp;";
	}
	
	/**
	 * This is needed in order to empty cells to display properly in DisplayTags tables since the show empty cell flag apparently does not work with IE
	 * 
	 * @return
	 */
	public String getDispSchArrivalDateTimeCell(){
		String s = DateUtils.formatDate(getSchArrivalDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		return s!=null?s:"&nbsp;";
	}
	
	/**
	 * This is needed in order to empty cells to display properly in DisplayTags tables since the show empty cell flag apparently does not work with IE
	 * 
	 * @return
	 */
	public String getDispBagDropDateTimeCell() {
		String s = DateUtils.formatDate(getBagDropTime(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		return s!=null?s:"&nbsp;";
	}

	public String getDispActArrivalDateTime(){
		String s = DateUtils.formatDate(getActArrivalDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		return s!=null?s:"";
	}
	
	public String getDispBagDropDate(){
		return DateUtils.formatDate(getBagDropTime(), _DATEFORMAT, null, _TIMEZONE);
	}
	
	public String getDispBagDropTime(){
		return DateUtils.formatDate(getBagDropTime(), _TIMEFORMAT, null, _TIMEZONE);
	}
	
	public void setDispBagDropDate(String date){
		sbagDropDate = date;
		updateBagDropDateTime();
	}
	
	public void setDispBagDropTime(String time){
		sbagDropTime = time;
		updateBagDropDateTime();
	}
	
	private void updateBagDropDateTime(){
		if(sbagDropDate != null && sbagDropDate.trim().length()>0
				&& sbagDropTime != null && sbagDropTime.trim().length()>0){
			Date d = DateUtils.convertToDate(sbagDropDate + " " + sbagDropTime, _DATEFORMAT + " " +_TIMEFORMAT, null, _TIMEZONE);
			setBagDropTime(DateUtils.convertToGMTDate(d));
			sbagDropTime=null;
			sbagDropDate=null;
		}
	}
}
