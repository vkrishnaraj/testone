package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class BagDropDTO {
	private long id;
	private String airlineCode;
	private String flightNumber;
	private Date startScheduleArrivalDate;
	private Date endScheduleArrivalDate;
	private String arrivalStation;
	private String originStation;
	
	private int startIndex;
	private int maxResults;
	
	private String sort;
	private String dir;
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private java.util.TimeZone _TIMEZONE;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getArrivalStation() {
		return arrivalStation;
	}
	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}
	public String getOriginStation() {
		return originStation;
	}
	public void setOriginStation(String originStation) {
		this.originStation = originStation;
	}
	public Date getStartScheduleArrivalDate() {
		return startScheduleArrivalDate;
	}
	public void setStartScheduleArrivalDate(Date startScheduleArrivalDate) {
		this.startScheduleArrivalDate = startScheduleArrivalDate;
	}
	public Date getEndScheduleArrivalDate() {
		return endScheduleArrivalDate;
	}
	public void setEndScheduleArrivalDate(Date endScheduleArrivalDate) {
		this.endScheduleArrivalDate = endScheduleArrivalDate;
	}
	public String getDispStartScheduleArrivalDate(){
		return DateUtils.formatDate(getStartScheduleArrivalDate(), _DATEFORMAT, null, _TIMEZONE);
	}
	public void setDispStartScheduleArrivalDate(String date){
		
	}
	//TODO setter
	public String getDispEndScheduleArrivalDate(){
		return DateUtils.formatDate(getEndScheduleArrivalDate(), _DATEFORMAT, null, _TIMEZONE);
	}
	public void setDispEndScheduleArrivalDate(String date){
		
	}
	//TODO setter
	
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
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
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
}
