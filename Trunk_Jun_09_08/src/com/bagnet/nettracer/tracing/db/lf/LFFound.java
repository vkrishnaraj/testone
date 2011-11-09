package com.bagnet.nettracer.tracing.db.lf;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;

@Entity
@Proxy(lazy = false)
public class LFFound implements LFObject {

	@Id
	@GeneratedValue
	private long id;
	
	private String agreementNumber;
	private String mvaNumber;
	
	private Date foundDate;

	@ManyToOne
	@JoinColumn(name = "station_ID", nullable = false)
	private Station location;
	
	@ManyToOne
	@JoinColumn(name = "agent_ID", nullable = false)
	private Agent agent;
	
	@Column(length = 3)
	private String companyId;
	
	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
	private Status status;

	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFItem.class, cascade = CascadeType.ALL)
	private LFItem item;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFPerson.class, cascade = CascadeType.ALL)
	private LFPerson client;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public Station getLocation() {
		return location;
	}

	public void setLocation(Station location) {
		this.location = location;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public LFItem getItem() {
		return item;
	}

	public void setItem(LFItem item) {
		this.item = item;
	}

	public LFPerson getClient() {
		return client;
	}

	public void setClient(LFPerson client) {
		this.client = client;
	}
	
	public String getDisFoundDate(String dateFormat) {
		return DateUtils.formatDate(foundDate, dateFormat, "", null);
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Transient
	public Set<LFItem> getItems() {
		LinkedHashSet<LFItem> items = new LinkedHashSet<LFItem>();
		items.add(item);
		return items;
	}
	
	public int getLocationId() {
		return getLocation().getStation_ID();
	}
	
	public void setLocationId(int locationId) {
		getLocation().setStation_ID(locationId);
	}

	@Override
	@Transient
	public String getDisStation() {
		return location.getStationcode();
	}

	@Override
	@Transient
	public String getStatusDescription() {
		return status.getDescription();
	}

	@Override
	@Transient
	public String getDisplayDate(String dateFormat) {
		return getDisFoundDate(dateFormat);
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
	
}
