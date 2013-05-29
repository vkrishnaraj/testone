package com.bagnet.nettracer.tracing.dto;

import java.util.Date;

import org.apache.struts.action.ActionForm;
import org.springframework.beans.factory.annotation.Autowired;

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
	private long id; 
	private String startDate;
	private String endDate; 
	private String startRentDate;
	private String endRentDate; 
	private Station station;
	private Agent agent; // na
	private String agentName;
	private Status status;
	private Status disposition;
	private String agreementNumber;
	private String mvaNumber; 
	private String phoneNumber;
	private String intNumber;
	private String areaNumber;
	private String exchangeNumber;
	private String lineNumber;
	private String extension;
	private String email;
	private String companyId;
	private int type; 
	private long category;
	private long subCategory;
	private String brand;
	private String itemDescription;
	private String barcode;
	private String serialNumber;
	private int dispositionId;
	private int statusId;
	private int stationId;
	
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
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId =companyId;
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
		return statusId;
	}
	
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	public int getStationId() {
		return stationId;
	}
	
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public void setDisposition(Status disposition) {
		this.disposition = disposition;
	}

	public Status getDisposition() {
		return disposition;
	}
	
	public int getDispositionId(){
		return dispositionId;
	}
	
	public void setDispositionId(int dispositionId){
		this.dispositionId = dispositionId;
	}

	public long getCategory() {
		return category;
	}

	public void setCategory(long category) {
		this.category = category;
	}

	public long getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(long subCategory) {
		this.subCategory = subCategory;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getStartRentDate() {
		return startRentDate;
	}
	
	public Date getStartRentDateAsDate(){
		return DateUtils.convertToDate(this.startRentDate, this.agent.getDateformat().getFormat(), this.agent.getDefaultlocale());
	}

	public void setStartRentDate(String startRentDate) {
		this.startRentDate = startRentDate;
	}

	public String getEndRentDate() {
		return endRentDate;
	}
	
	public Date getEndRentDateAsDate(){
		return DateUtils.convertToDate(this.endRentDate, this.agent.getDateformat().getFormat(), this.agent.getDefaultlocale());
	}

	public void setEndRentDate(String endRentDate) {
		this.endRentDate = endRentDate;
	}

	public String getIntNumber() {
		return intNumber;
	}

	public void setIntNumber(String intNumber) {
		this.intNumber = intNumber;
	}

	public String getAreaNumber() {
		return areaNumber;
	}

	public void setAreaNumber(String areaNumber) {
		this.areaNumber = areaNumber;
	}

	public String getExchangeNumber() {
		return exchangeNumber;
	}

	public void setExchangeNumber(String exchangeNumber) {
		this.exchangeNumber = exchangeNumber;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
		
}
