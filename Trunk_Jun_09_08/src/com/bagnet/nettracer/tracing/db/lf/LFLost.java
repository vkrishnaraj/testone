package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import java.lang.Cloneable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;

@Entity
@Proxy(lazy = false)
public class LFLost implements LFObject, Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7672592006533855019L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(name = "agent_ID", nullable = false)
	private Agent agent;

	@ManyToOne
	@JoinColumn(name = "closeagent_ID", nullable = true)
	private Agent closeAgent;
	
	@ManyToOne
	@JoinColumn(name = "station_ID", nullable = false)
	private Station location;

	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
	private Status status;
	
	@Column(length = 3)
	private String companyId;

	private Date openDate;

	private Date closeDate;

	private Date emailSentDate;

	private String remarks;
	
	private String vantiveNumber;
	
	@OneToOne(mappedBy = "lost", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private LFShipping shipment;

	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFPerson.class, cascade = CascadeType.ALL)
	private LFPerson client;

	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFLossInfo.class, cascade = CascadeType.ALL)
	private LFLossInfo lossInfo;

	@OneToMany(mappedBy = "lost", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<LFSegment> segments;

	@OneToMany(mappedBy = "lost", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<LFItem> items;

	@OneToMany(mappedBy = "lost", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<LFDelivery> deliveries;
	
	@OneToMany(mappedBy = "lost", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<LFRemark> agentRemarks;
	
	private boolean email1;
	
	private boolean email2;
	
	private boolean email3;
	
	private boolean email4;
	
	private boolean email5;
	
	private boolean foundEmail;
	
	private String feedback;
	
	@Transient
	private long lastLoaded;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Station getLocation() {
		return location;
	}

	public void setLocation(Station location) {
		this.location = location;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public LFPerson getClient() {
		return client;
	}

	public void setClient(LFPerson client) {
		this.client = client;
	}

	public LFShipping getShipment() {
		return shipment;
	}

	public void setShipment(LFShipping shipment) {
		this.shipment = shipment;
	}

	public LFLossInfo getLossInfo() {
		return lossInfo;
	}

	public void setLossInfo(LFLossInfo lossInfo) {
		this.lossInfo = lossInfo;
	}

	public Set<LFItem> getItems() {
		return items;
	}

	public void setItems(Set<LFItem> items) {
		this.items = items;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getEmailSentDate() {
		return emailSentDate;
	}

	public void setEmailSentDate(Date emailSentDate) {
		this.emailSentDate = emailSentDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getDisOpenDate() {
		return DateUtils.formatDate(openDate, this.agent.getDateformat().getFormat(), "", null);
	}

	public String getDisOpenDate(String dateFormat) {
		return DateUtils.formatDate(openDate, dateFormat, "", null);
	}
	
	public String getDisClosedDate(String dateFormat) {
		return DateUtils.formatDate(closeDate, dateFormat, "", null);
	}
	
	public Set<LFDelivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(Set<LFDelivery> deliveries) {
		this.deliveries = deliveries;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	@Transient
	public String getDisStation() {
		if(TracingConstants.LF_LF_COMPANY_ID.equals(agent.getCompanycode_ID()))
		{
			String toReturn="";
			for(LFSegment seg:getSegments())
			{
				toReturn=seg.getDestination().getStationcode();
			}
			return toReturn;
		}
		else
		{
			return getLossInfo().getDestination().getStationcode();
		}
	}

	@Override
	@Transient
	public String getStatusDescription() {
		return status.getDescription();
	}

	@Override
	@Transient
	public String getDisplayDate(String dateFormat) {
		return getDisOpenDate(dateFormat);
	}

	@Override
	@Transient
	public String getBarcode() {
		return "";
	}

	@Override
	@Transient
	public String getClientName() {
		String clientName = client.getLastName() + ", " + client.getFirstName();
		String middleName = client.getMiddleName();
		if (middleName != null && !middleName.isEmpty()) {
			clientName += " " + middleName + ".";
		}
		return clientName;
	}
	
	@Transient
	public int getStatusId() {
		return status.getStatus_ID();
	}
	
	public void setStatusId(int statusId) {
		status.setStatus_ID(statusId);
	}

	@Transient
	public LFItem getItem() {
		LFItem item = null;
		if (items != null) {
			for(LFItem i:items){
				if(i.getType() == TracingConstants.LF_TYPE_LOST){
					item = i;
				}
			}
		}
		return item;
	}
	
	public void setItem(LFItem item) {
		this.items = new LinkedHashSet<LFItem>();
		items.add(item);
	}

	public void setCloseAgent(Agent closeAgent) {
		this.closeAgent = closeAgent;
	}

	public Agent getCloseAgent() {
		return closeAgent;
	}

	public String getVantiveNumber() {
		return vantiveNumber;
	}

	public void setVantiveNumber(String vantiveNumber) {
		this.vantiveNumber = vantiveNumber;
	}

	public void setAgentRemarks(Set<LFRemark> agentRemarks) {
		this.agentRemarks = agentRemarks;
	}

	public Set<LFRemark> getAgentRemarks() {
		return agentRemarks;
	}

	public void setEmail1(boolean email1) {
		this.email1 = email1;
	}

	public boolean isEmail1() {
		return email1;
	}

	public void setEmail2(boolean email2) {
		this.email2 = email2;
	}

	public boolean isEmail2() {
		return email2;
	}

	public boolean isEmail3() {
		return email3;
	}

	public void setEmail3(boolean email3) {
		this.email3 = email3;
	}

	public boolean isEmail4() {
		return email4;
	}

	public void setEmail4(boolean email4) {
		this.email4 = email4;
	}

	public boolean isEmail5() {
		return email5;
	}

	public void setEmail5(boolean email5) {
		this.email5 = email5;
	}
	
	public void setFoundEmail(boolean foundEmail) {
		this.foundEmail = foundEmail;
	}

	public boolean isFoundEmail() {
		return foundEmail;
	}

	public void setLastLoaded(long lastLoaded) {
		this.lastLoaded = lastLoaded;
	}

	public long getLastLoaded() {
		return lastLoaded;
	}

	public Set<LFSegment> getSegments() {
		return segments;
	}

	public void setSegments(Set<LFSegment> segments) {
		this.segments = segments;
	}
	
	@Transient
	public String getSegmentSQL() {
		ArrayList<String> stations = new ArrayList<String>();
		stations.add(0 + "");
		if (segments != null) {
			for (LFSegment seg: segments) {
				stations.add(seg.getOriginId() + "");
				stations.add(seg.getDestinationId() + "");
			}
		}
		return "(" + StringUtils.join(stations,",") + ")";
	}
	

	private String firstName;
	
	private String middleName;
	
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Transient
	public boolean isEmail(int notice){
		switch (notice){
		case 1:return isEmail1();
		case 2:return isEmail2();
		case 3:return isEmail3();
		case 4:return isEmail4();
		case 5:return isEmail5();
		default:return false;
		}
	}
	
	@Transient
	public void setEmail(int notice, boolean sent){
		switch (notice){
		case 1:setEmail1(sent);break;
		case 2:setEmail2(sent);break;
		case 3:setEmail3(sent);break;
		case 4:setEmail4(sent);break;
		case 5:setEmail5(sent);break;
		}
	}
}

