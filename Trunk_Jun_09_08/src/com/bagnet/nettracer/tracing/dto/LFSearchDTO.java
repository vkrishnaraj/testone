package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class LFSearchDTO extends ActionForm  {
	
	private static final long serialVersionUID = 8462929881743509456L;

	private String currpage;
	private String nextpage;
	private String prevpage;
	private String pagination;
	
	private String firstName;
	private String lastName;
	private long id; // done
	private String startDate; // done
	private String endDate; // done
	private Station station; // done
	private Agent agent; // na
	private Status status; // done
	private Status disposition;
	private String agreementNumber; // done
	private String mvaNumber; // done
	private String phoneNumber;
	private String email;
	private int type; // done

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public Date getStartDateAsDate(){
		return DateUtils.convertToDate(this.startDate, this.agent.getDateformat().getFormat(), this.agent.getDefaultlocale());
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public Date getEndDateAsDate(){
		return DateUtils.convertToDate(this.endDate, this.agent.getDateformat().getFormat(), this.agent.getDefaultlocale());
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
	public Station getStation() {
		return station;
	}
	
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	public Agent getAgent() {
		return agent;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return status;
	}

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	public String getMvaNumber() {
		return mvaNumber;
	}

	public void setMvaNumber(String mvaNumber) {
		this.mvaNumber = mvaNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCurrpage() {
		return currpage;
	}

	public void setCurrpage(String currpage) {
		this.currpage = currpage;
	}

	public String getNextpage() {
		return nextpage;
	}

	public void setNextpage(String nextpage) {
		this.nextpage = nextpage;
	}

	public String getPrevpage() {
		return prevpage;
	}

	public void setPrevpage(String prevpage) {
		this.prevpage = prevpage;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
	
	public int getStatusId() {
		return status.getStatus_ID();
	}
	
	public void setStatusId(int statusId) {
		this.status.setStatus_ID(statusId);
	}
	
	public int getStationId() {
		return station.getStation_ID();
	}
	
	public void setStationId(int stationId) {
		this.station.setStation_ID(stationId);
	}

	public void setDisposition(Status disposition) {
		this.disposition = disposition;
	}

	public Status getDisposition() {
		return disposition;
	}
	
	public int getDispositionId(){
		return disposition.getStatus_ID();
	}
	
	public void setDispositionId(int dispositionId){
		this.disposition.setStatus_ID(dispositionId);
	}
		
}
