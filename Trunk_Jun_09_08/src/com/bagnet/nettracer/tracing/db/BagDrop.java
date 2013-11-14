package com.bagnet.nettracer.tracing.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Loupas
 *
 */

@Entity
public class BagDrop {
	@Id
	@GeneratedValue
	private Long id;
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

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	 * Returns a String in the format hh:mm representing the time to carousel (bagdrop time minus actual arrival)
	 * If either bagDropTime or actArrivalTime are null, return empty string
	 * If negative time, return 00:00
	 * 
	 * TODO consider using Joda
	 * 
	 * @return
	 */
	public String getDispTimeToCarousel(){
		if(bagDropTime == null || actArrivalDate == null){
			return "";
		}
		long time = bagDropTime.getTime() - actArrivalDate.getTime();
		if(time <= 0){
			return "00:00";
		}
		
		long mins = (time/(1000*60))%60;
		long hours = (time/(1000*60*60));
		
		return String.format("%02d:%02d", hours, mins);
	}
}
