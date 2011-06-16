package com.bagnet.nettracer.tracing.db.lf;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
	
	private String mvaNumber;
	
	private Date foundDate;

	@ManyToOne
	@JoinColumn(name = "station_ID", nullable = false)
	private Station location;
	
	@ManyToOne
	@JoinColumn(name = "agent_ID", nullable = false)
	private Agent agent;
	
	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
	private Status status;

	@ManyToOne
	@JoinColumn(name = "disposition_status_ID", nullable = true)
	private Status disposition;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFItem.class, cascade = CascadeType.ALL)
	private LFItem item;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFPerson.class, cascade = CascadeType.ALL)
	private LFPerson client;
	
	@Transient
	private boolean selected;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public Status getDisposition() {
		return disposition;
	}

	public void setDisposition(Status disposition) {
		this.disposition = disposition;
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
