package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

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

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.utils.DateUtils;

@Entity
@Proxy(lazy = false)
public class LFFound implements LFObject, Serializable {

	private static final long serialVersionUID = 2713442669778194028L;

	@Id
	@GeneratedValue
	private long id;
	
	private String agreementNumber;
	private String mvaNumber;
	private String flightNumber;
	
	private Date receivedDate;
	private Date foundDate;
	private Date deliveredDate;
	
	private int checkNumber;
	private double checkAmount;

	@ManyToOne
	@JoinColumn(name = "station_ID", nullable = false)
	private Station location;
	
	@ManyToOne
	@JoinColumn(name = "agent_ID", nullable = false)
	private Agent agent;
	
	@Column(length = 3)
	private String companyId;
	
	@JoinColumn(name = "salvageBoxId")
	private String salvageBoxId;
	
	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
	private Status status;

	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFItem.class, cascade = CascadeType.ALL)
	private LFItem item;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFPerson.class, cascade = CascadeType.ALL)
	private LFPerson client;
	
	private String barcode;
	
	@OneToMany(mappedBy = "found", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<LFRemark> agentRemarks;

	private int itemLocation;
	private String binId; 
	
	@ManyToOne
	@JoinColumn(name = "salvage_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	private LFSalvage salvage;
		
	@OneToOne(targetEntity=com.bagnet.nettracer.tracing.db.documents.Document.class, cascade=CascadeType.ALL)
	private Document receiptFile;
	
	@Transient
	private int entryStatus;
	
	@Transient
	private long lastLoaded;
	
	@Transient
	private boolean selected;
	
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
	
	public String getSalvageBoxId() {
		return salvageBoxId;
	}

	public void setSalvageBoxId(String salvageBoxId) {
		this.salvageBoxId = salvageBoxId;
	}

	public String getMvaNumber() {
		return mvaNumber;
	}

	public void setMvaNumber(String mvaNumber) {
		this.mvaNumber = mvaNumber;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
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
	
	public String getDisReceivedDate() {
		return DateUtils.formatDate(receivedDate, agent.getDateformat().getFormat(), agent.getCurrenttimezone(), null);
	}
	
	public void setDisReceivedDate(String date, String dateFormat) {
		this.receivedDate = DateUtils.convertToDate(date, dateFormat, null);
	}

	public String getDisReceivedDate(String dateFormat) {
		return DateUtils.formatDate(receivedDate, dateFormat, agent.getCurrenttimezone(), null);
	}
	
	public String getDisDeliveredDate(String dateFormat) {
		return DateUtils.formatDate(deliveredDate, dateFormat, agent.getCurrenttimezone(), null);
	}
	
	public void setDisDeliveredDate(String date, String dateFormat) {
		this.deliveredDate = DateUtils.convertToDate(date, dateFormat, null);
	}
	
	public String getDisFoundDate(String dateFormat) {
		return DateUtils.formatDate(foundDate, dateFormat, agent.getCurrenttimezone(), null);
	}

	public void setDisFoundDate(String date, String dateFormat) {
		this.foundDate = DateUtils.convertToDate(date, dateFormat, null);
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
		if(client == null){
			return "";
		}
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

	public void setBarcode(String barcode) {
		if (barcode != null) {
			this.barcode = barcode.trim();
		}
	}

	public String getBarcode() {
		return barcode;
	}

	public void setAgentRemarks(Set<LFRemark> agentRemarks) {
		this.agentRemarks = agentRemarks;
	}

	public Set<LFRemark> getAgentRemarks() {
		return agentRemarks;
	}
	
	@Transient
	public String getExtendedSummaryDesc() {
		if (item != null) {
			String toReturn = item.getExtendedSummaryDesc();
			if (toReturn == null) {
				toReturn = "";
			}
			return toReturn;
		}
		return "";
	}
	
	@Transient
	public String getSummaryDesc() {
		if (item != null) {
			String toReturn = item.getSummaryDesc();
			if (toReturn == null) {
				toReturn = "";
			}
			return toReturn;
		}
		return "";
	}

	public int getItemLocation() {
		return itemLocation;
	}

	public void setItemLocation(int itemLocation) {
		this.itemLocation = itemLocation;
	}

	public String getBinId() {
		return binId;
	}

	public void setBinId(String binId) {
		this.binId = binId;
	}

	public int getEntryStatus() {
		return entryStatus;
	}

	public void setEntryStatus(int entryStatus) {
		this.entryStatus = entryStatus;
	}
	
	public boolean hasContactInfo() {
		return !client.isEmpty();
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(Date deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public int getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(int checkNumber) {
		this.checkNumber = checkNumber;
	}

	public double getCheckAmount() {
		return checkAmount;
	}

	public void setCheckAmount(double checkAmount) {
		this.checkAmount = checkAmount;
	}

	public LFSalvage getSalvage() {
		return salvage;
	}

	public void setSalvage(LFSalvage salvage) {
		this.salvage = salvage;
	}

	public void setLastLoaded(long lastLoaded) {
		this.lastLoaded = lastLoaded;
	}

	public long getLastLoaded() {
		return lastLoaded;
	}

	public Document getReceiptFile() {
		return receiptFile;
	}

	public void setReceiptFile(Document receiptFile) {
		this.receiptFile = receiptFile;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
