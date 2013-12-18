/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.dr.Dispute;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemIncident;
import com.bagnet.nettracer.tracing.utils.ClientUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "Incident")
@Proxy(lazy = false)
public class Incident implements Serializable {

	private int version;

	private String Incident_ID;
	private Station stationcreated;
	private Station stationassigned;
	private Station faultstation;
	private Agent agent;
	private Agent agentassigned;
	private Date createdate;
	private Date createtime;
	private Date closedate;
	private String recordlocator;
	private String manualreportnum;
	private String ticketnumber;
	private int reportmethod;
	private String checkedlocation;
	private int numpassengers;
	private int numbagchecked;
	private int numbagreceived;
	private int voluntaryseparation;
	private int courtesyreport;
	private int tsachecked;
	private int customcleared;
	private int nonrevenue;
	private ItemType itemtype;
	private DeliveryInstructions deliveryInstructions;
	private Status status;
	private int loss_code;
	
	
	private Date printedreceipt;
	
	private Date lastupdated;
	
	private Date ohd_lasttraced;
	
	private WorldTracerFile wtFile;	//world tracer id
	private CrmFile crmFile;
	private Set<Passenger> passengers;

	private List<Item> itemlist;
	private Set<Articles> articles;
	private Set<Remark> remarks;
	private Set<Itinerary> itinerary;
	private long oc_claim_id;
	private Set<Claim> claims;

	private Set<Incident_Claimcheck> claimchecks;

	private List<Itinerary> itinerary_list; // for displaying to the search incident
	// page
	private List<Incident_Claimcheck> claimcheck_list; // for display to the search incident page
	private List<Passenger> passenger_list; // for displaying to the search incident
	// page

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;
	
	private Set<ExpensePayout> expenses;
	private List<ExpensePayout>expenselist;
	
	private String language;
	
	private IncidentControl incidentControl;
	
	private Dispute dispute;
	
	private boolean locked = false;	//for dispute resolution process
	private boolean codeLocked = false;	//for dispute resolution process
	private boolean stationLocked = false;	//for dispute resolution process
	
	private String revenueCode;
	
	private int tracingStatus;
	private Date tracingStarted;
	private Date tracingComplete;
	private Agent tracingAgent;
	
	private Date rxTimestamp;
	private int courtesyReasonId;
	private String courtesyDescription;
	private int custCommId;

	
	private List<IssuanceItemIncident> issuanceItemIncidents;
	
	private Set<IncidentActivity> activities;

	private String wtStationCode;
	private String wtCompanyCode;
	
	@Column(name="tracing_status_id")
	public int getTracingStatus() {
		return tracingStatus;
	}
	
	public void setTracingStatus(int tracingStatus) {
		this.tracingStatus = tracingStatus;
	}

	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getTracingStarted() {
		return tracingStarted;
	}

	public void setTracingStarted(Date tracingStarted) {
		this.tracingStarted = tracingStarted;
	}

	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getTracingComplete() {
		return tracingComplete;
	}

	public void setTracingComplete(Date tracingComplete) {
		this.tracingComplete = tracingComplete;
	}

	@ManyToOne
	@JoinColumn(name = "tracing_agent_ID")
	public Agent getTracingAgent() {
		return tracingAgent;
	}

	public void setTracingAgent(Agent tracingAgent) {
		this.tracingAgent = tracingAgent;
	}

	@Column(name="revenue_code", length=4)
	public String getRevenueCode() {
		return revenueCode;
	}
	
	public void setRevenueCode(String revenueCode) {
		this.revenueCode = revenueCode;
	}
	
	@Column(name="locked", nullable=false)
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Column(name="codeLocked", nullable=false,columnDefinition = "tinyint")
	public boolean isCodeLocked() {
		return codeLocked;
	}
	public void setCodeLocked(boolean locked) {
		this.codeLocked = locked;
	}
	
	@Column(name="stationLocked", nullable=false, columnDefinition="tinyint")
	public boolean isStationLocked() {
		return stationLocked;
	}
	public void setStationLocked(boolean locked) {
		this.stationLocked = locked;
	}
	
	@OneToOne(mappedBy = "incident")
	public Dispute getDispute() {
		return dispute;
	}
	public void setDispute(Dispute dispute) {
		this.dispute = dispute;
	}
	
	public void setIncidentControl(IncidentControl value) {
		this.incidentControl = value;
	}
	/**
	 * @return Returns the claim.
	 * 
	 *  
	 */
	@OneToOne(mappedBy = "incident")
	public IncidentControl getIncidentControl() {
		return incidentControl;
	}
	
	

	public Double getOverall_weight() {
		return overall_weight;
	}

	public void setOverall_weight(Double overall_weight) {
		this.overall_weight = roundToTwoDecimals(overall_weight);
	}

	public String getOverall_weight_unit() {
		return overall_weight_unit;
	}

	public void setOverall_weight_unit(String overall_weight_unit) {
		this.overall_weight_unit = overall_weight_unit;
	}
	
	private Double overall_weight;
	private String overall_weight_unit;
	
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	private long checklist_version;    // for auto checklist

	@Transient
	public String getReportMethodString(int val) {
//		if (val == 0)
//			return "In Person";
//		else if (val == 1)
//			return "BSO Phone";
//		else if (val == 2)
//			return "Call Center";
//		else if (val == 3)
//			return "Internet";
//		else
//			return "Kiosk";
		return ((ClientUtils) SpringUtils.getBean("clientUtils")).getReportMethodName(val);
	}

	@Version
	public int getVersion() {
		return version;
	}
	
	private void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return returns status of incident
	 */
	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *          The status to set.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return Returns the itemtype.
	 * 
	 */
	@ManyToOne
	@JoinColumn(name = "itemType_ID", nullable = false)
	public ItemType getItemtype() {
		return itemtype;
	}

	/**
	 * @param itemtype
	 *          The itemtype to set.
	 */
	public void setItemtype(ItemType itemtype) {
		this.itemtype = itemtype;
	}

	/**
	 * @return Returns the agent that created this incident.
	 * 
	 */
	@ManyToOne
	@JoinColumn(name = "agent_ID", nullable = false)
	public Agent getAgent() {
		return agent;
	}

	/**
	 * @param agent
	 *          The agent to set.
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	/**
	 * @return Returns the deliveryInstructions associated with this incident.
	 * 
	 */
	@OneToOne
	@JoinColumn(name = "deliveryInstructions_ID", nullable = true)
	public DeliveryInstructions getDeliveryInstructions() {
		return deliveryInstructions;
	}

	/**
	 * @param deliveryInstructions
	 *          The deliveryInstructions to set.
	 */
	public void setDeliveryInstructions(DeliveryInstructions deliveryInstructions) {
		this.deliveryInstructions = deliveryInstructions;
	}

	/**
	 * 
	 * @return set of expense payouts for this claim
	 */
	@OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "createdate")
	@Fetch(FetchMode.SELECT)
	public Set<ExpensePayout> getExpenses() {
		return expenses;
	}

	public void setExpenses(Set<ExpensePayout> expenses) {
		this.expenses = expenses;
	}
	
	/**
	 * @return Returns the agent assigned to this incident
	 * 
	 */
	@ManyToOne
	@JoinColumn(name  = "agentassigned_ID", nullable = true)
	public Agent getAgentassigned() {
		return agentassigned;
	}
	
	/**
	 * @param agentassigned The agentassigned to set.
	 */
	public void setAgentassigned(Agent agentassigned) {
		this.agentassigned = agentassigned;
	}
	/**
	 * @return Returns the stationassigned.
	 * 
	 */
	@ManyToOne
	@JoinColumn(name = "stationassigned_ID", nullable = false)
	public Station getStationassigned() {
		return stationassigned;
	}

	/**
	 * @param stationassigned
	 *          The stationassigned to set.
	 */
	public void setStationassigned(Station stationassigned) {
		this.stationassigned = stationassigned;
	}

	/**
	 * @return Returns the stationcreated.
	 * 
	 */
	@ManyToOne
	@JoinColumn(name = "stationcreated_ID", nullable = false)
	public Station getStationcreated() {
		return stationcreated;
	}

	/**
	 * @param stationcreated
	 *          The stationcreated to set.
	 */
	public void setStationcreated(Station stationcreated) {
		this.stationcreated = stationcreated;
	}

	/**
	 * @return Returns the faultstation.
	 * 
	 */
	@ManyToOne
	@JoinColumn(name = "faultstation_ID", nullable = true)
	public Station getFaultstation() {
		return faultstation;
	}

	/**
	 * @param faultstation
	 *          The faultstation to set.
	 */
	public void setFaultstation(Station faultstation) {
		this.faultstation = faultstation;
	}

	/**
	 * @return Returns the claimchecks.
	 * 
	 */
	@OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause="claimcheck_ID")
	@Fetch(FetchMode.SELECT)
	public Set<Incident_Claimcheck> getClaimchecks() {
		return claimchecks;
	}

	/**
	 * @param claimchecks
	 *          The claimchecks to set.
	 */
	public void setClaimchecks(Set<Incident_Claimcheck> claimchecks) {
		this.claimchecks = claimchecks;
		
	}

	/**
	 * @return Returns the itemlist.
	 *  
	 */
	@OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.IndexColumn(name = "bagnumber")
	@Fetch(FetchMode.SELECT)
	public List<Item> getItemlist() {
		return itemlist;
	}

	/**
	 * @param itemlist
	 *          The itemlist to set.
	 */
	public void setItemlist(List<Item> itemlist) {
		this.itemlist = itemlist;
	}

	/**
	 * @return Returns the articles.
	 * 
	 *  
	 */
	@OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "articles_ID DESC")
	@Fetch(FetchMode.SELECT)
	public Set<Articles> getArticles() {
		return articles;
	}

	/**
	 * @param articles
	 *          The articles to set.
	 */
	public void setArticles(Set<Articles> articles) {
		this.articles = articles;
	}

	/**
	 * @return Returns the passengers.
	 */
	@OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "passenger_id")
	@Fetch(FetchMode.SELECT)
	public Set<Passenger> getPassengers() {
		return passengers;
	}

	/**
	 * @param passengers
	 *          The passengers to set.
	 */
	public void setPassengers(Set<Passenger> passengers) {
		this.passengers = passengers;
		//this.passenger_list = (passengers != null ? new ArrayList<Passenger>(passengers) : new ArrayList<Passenger>());
	}

	/**
	 * @return Returns the remarks.
	 * 
	 *  
	 */
	@OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause="createtime")
	@Fetch(FetchMode.SELECT)
	public Set<Remark> getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *          The remarks to set.
	 */
	public void setRemarks(Set<Remark> remarks) {
		this.remarks = remarks;
	}

	@OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "itinerary_ID")
	@Fetch(FetchMode.SELECT)
	public Set<Itinerary> getItinerary() {
		return itinerary;
	}

	/**
	 * @param itinerary
	 *          The itinerary to set.
	 */
	public void setItinerary(Set<Itinerary> itinerary) {
		this.itinerary = itinerary;
		//this.itinerary_list = (itinerary != null ? new ArrayList<Itinerary>(itinerary) : new ArrayList<Itinerary>());
	}
	
	

	/**
	 * @return Returns the checkedlocation.
	 * 
	 */
	@Column(columnDefinition = "char")
	public String getCheckedlocation() {
		return checkedlocation;
	}

	/**
	 * @param checkedlocation
	 *          The checkedlocation to set.
	 */
	public void setCheckedlocation(String checkedlocation) {
		this.checkedlocation = checkedlocation;
	}

	/**
	 * @return Returns the courtesyreport.
	 * 
	 */
	@Basic
	public int getCourtesyreport() {
		return courtesyreport;
	}

	/**
	 * @param courtesyreport
	 *          The courtesyreport to set.
	 */
	public void setCourtesyreport(int courtesyreport) {
		this.courtesyreport = courtesyreport;
	}

	/**
	 * @return Returns the createdate.
	 * 
	 */
	@Temporal(value = TemporalType.DATE)
	public Date getCreatedate() {
		return createdate;
	}

	/**
	 * @param createdate
	 *          The createdate to set.
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	
	/**
	 * @return Returns the lastupdated.
	 * 
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getLastupdated() {
		return lastupdated;
	}
	/**
	 * @param lastupdated The lastupdated to set.
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}
	
	/**
	 * @return Returns the createtime.
	 * 
	 */
	@Temporal(value = TemporalType.TIME)
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime
	 *          The createtime to set.
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Transient
	public String getDisplaydate() {
		Date completedate = DateUtils.convertToDate(getCreatedate().toString() + " " + getCreatetime().toString(), TracingConstants.DB_DATETIMEFORMAT,
				null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}
	
	@Transient
	public Date getFullCreateDate() {
		return DateUtils.convertToDate(getCreatedate().toString() + " " + getCreatetime().toString(), TracingConstants.DB_DATETIMEFORMAT,
				null);
	}
	
	@Transient
	public Date getFullCloseDate() {
		return getClosedate();
	}

	/**
	 * @return Returns the closedate.
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "close_date")
	public Date getClosedate() {
		return closedate;
	}

	/**
	 * @param closedate
	 *          The closedate to set.
	 */
	public void setClosedate(Date closedate) {
		this.closedate = closedate;
	}

	/**
	 * @return Returns the _DATEFORMAT.
	 */
	@Transient
	public String get_DATEFORMAT() {
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
	@Transient
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	/**
	 * @param _timeformat
	 *          The _TIMEFORMAT to set.
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	/**
	 * @return Returns the _TIMEZONE.
	 */
	@Transient
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	/**
	 * @param _timezone
	 *          The _TIMEZONE to set.
	 */
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}

	/**
	 * @return Returns the incident_ID.
	 * 
	 */
	@Id
	@Column(name = "Incident_ID", length=13)
	public String getIncident_ID() {
		return Incident_ID;
	}

	/**
	 * @param incident_ID
	 *          The incident_ID to set.
	 */
	public void setIncident_ID(String incident_ID) {
		Incident_ID = incident_ID;
	}

	/**
	 * @return Returns the numbagchecked.
	 * 
	 */
	public int getNumbagchecked() {
		return numbagchecked;
	}

	/**
	 * @param numbagchecked
	 *          The numbagchecked to set.
	 */
	public void setNumbagchecked(int numbagchecked) {
		this.numbagchecked = numbagchecked;
	}

	/**
	 * @return Returns the numbagreceived.
	 * 
	 */
	public int getNumbagreceived() {
		return numbagreceived;
	}

	/**
	 * @param numbagreceived
	 *          The numbagreceived to set.
	 */
	public void setNumbagreceived(int numbagreceived) {
		this.numbagreceived = numbagreceived;
	}

	/**
	 * @return Returns the numpassengers.
	 * 
	 */
	public int getNumpassengers() {
		return numpassengers;
	}

	/**
	 * @param numpassengers
	 *          The numpassengers to set.
	 */
	public void setNumpassengers(int numpassengers) {
		this.numpassengers = numpassengers;
	}

	/**
	 * @return Returns the reportmethod.
	 * 
	 */
	public int getReportmethod() {
		return reportmethod;
	}

	/**
	 * @param reportmethod
	 *          The reportmethod to set.
	 */
	public void setReportmethod(int reportmethod) {
		this.reportmethod = reportmethod;
	}

	/**
	 * @return Returns the ticketnumber.
	 * 
	 */
	@Column(length = 14)
	public String getTicketnumber() {
		return ticketnumber;
	}

	/**
	 * @param ticketnumber
	 *          The ticketnumber to set.
	 */
	public void setTicketnumber(String ticketnumber) {
		this.ticketnumber = ticketnumber;
	}

	/**
	 * @return Returns the tsachecked.
	 * 
	 */
	public int getTsachecked() {
		return tsachecked;
	}

	/**
	 * @param tsachecked
	 *          The tsachecked to set.
	 */
	public void setTsachecked(int tsachecked) {
		this.tsachecked = tsachecked;
	}

	/**
	 * @return Returns the customcleared.
	 */
	public int getCustomcleared() {
		return customcleared;
	}

	/**
	 * @param customcleared
	 *          The customcleared to set.
	 */
	public void setCustomcleared(int customcleared) {
		this.customcleared = customcleared;
	}

	/**
	 * @return Returns the voluntaryseparation.
	 * 
	 */
	public int getVoluntaryseparation() {
		return voluntaryseparation;
	}

	/**
	 * @param voluntaryseparation
	 *          The voluntaryseparation to set.
	 */
	public void setVoluntaryseparation(int voluntaryseparation) {
		this.voluntaryseparation = voluntaryseparation;
	}

	/**
	 * @return Returns the manualreportnum.
	 * 
	 */
	@Column(length = 20)
	public String getManualreportnum() {
		return manualreportnum;
	}

	/**
	 * @param manualreportnum
	 *          The manualreportnum to set.
	 */
	public void setManualreportnum(String manualreportnum) {
		this.manualreportnum = manualreportnum;
	}

	/**
	 * @return Returns the recordlocator.
	 * 
	 */
	@Column(length = 10)
	public String getRecordlocator() {
		return recordlocator;
	}

	/**
	 * @param recordlocator
	 *          The recordlocator to set.
	 */
	public void setRecordlocator(String recordlocator) {
		this.recordlocator = recordlocator;
	}

	/**
	 * @return Returns the nonrevenue.
	 * 
	 */
	public int getNonrevenue() {
		return nonrevenue;
	}

	/**
	 * @param nonrevenue
	 *          The nonrevenue to set.
	 */
	public void setNonrevenue(int nonrevenue) {
		this.nonrevenue = nonrevenue;
	}

	/**
	 * @return Returns the ohd_lasttraced.
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	public Date getOhd_lasttraced() {
		return ohd_lasttraced;
	}

	/**
	 * @param ohd_lasttraced
	 *          The ohd_lasttraced to set.
	 */
	public void setOhd_lasttraced(Date ohd_lasttraced) {
		this.ohd_lasttraced = ohd_lasttraced;
	}

	/**
	 * @return Returns the claim.
	 * 
	 *  
	 */
	@OneToMany(mappedBy = "ntIncident", fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "claimdate")
	@Fetch(FetchMode.SELECT)
	public Set<Claim> getClaims() {
		return claims;
	}

	/**
	 * @param claims
	 *          The claims to set.
	 */
	public void setClaims(Set<Claim> claims) {
		this.claims = claims;
	}
	

	@OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "issuedate")
	@Fetch(FetchMode.SELECT)
	public List<IssuanceItemIncident> getIssuanceItemIncidents() {
		return issuanceItemIncidents;
	}

	public void setIssuanceItemIncidents(List<IssuanceItemIncident> issuanceItemIncidents) {
		this.issuanceItemIncidents = issuanceItemIncidents;
	}

	/**
	 * @return Returns the loss_code.
	 */
	@Basic
	public int getLoss_code() {
		return loss_code;
	}

	/**
	 * @param loss_code
	 *          The loss_code to set.
	 */
	public void setLoss_code(int loss_code) {
		this.loss_code = loss_code;
	}
	
	/**
	 * @return the wt_id
	 * 
	 */
	@Embedded
	public WorldTracerFile getWtFile() {
		return wtFile;
	}


	/**
	 * @param wt_id the wt_id to set
	 */
	public void setWtFile(WorldTracerFile wtFile) {
		this.wtFile = wtFile;
	}
	
	@Transient
	public String getWt_id() {
		if(wtFile != null) {
			return wtFile.getWt_id();
		}
		return null;
	}

	@Transient
	public List<Itinerary> getItinerary_list() {
		return (itinerary == null ? new ArrayList<Itinerary>() : new ArrayList<Itinerary>(itinerary));
	}
	
	public void setItinerary_list(List<Itinerary> itinList) {
		this.itinerary_list = itinList;
	}

	@Transient
	public List<Incident_Claimcheck> getClaimcheck_list() {
		return claimchecks != null ? new ArrayList<Incident_Claimcheck>(claimchecks) : new ArrayList<Incident_Claimcheck>();
	}
	
	public void setClaimcheck_list(List<Incident_Claimcheck> cc_list) {
		this.claimcheck_list = cc_list;
	}
	
	@Transient
	public List<ExpensePayout> getExpenselist() {
		return expenses != null ? new ArrayList<ExpensePayout>(expenses) : new ArrayList<ExpensePayout>();
	}

	public void setExpenselist(List<ExpensePayout> expenselist) {
		this.expenses = new LinkedHashSet<ExpensePayout>(expenselist);
	}

	@Transient
	public List<Passenger> getPassenger_list() {
		return passengers != null ? new ArrayList<Passenger>(passengers) : new ArrayList<Passenger>();
	}

	public void setPassenger_list(List<Passenger> paxList) {
		this.passenger_list = paxList;
	}
	/** * for reporting ** */
	@Transient
	public int getItemtype_ID() {
		return itemtype.getItemType_ID();
	}

	@Transient
	public String getTypedesc() {
		return itemtype.getDescription();
	}

//	@Transient
//	public String getStatusdesc(Agent a) {
//		return status.getTextDescription(a);
//	}

	@Transient
	public String getStationcode() {
		return stationassigned.getStationcode();
	}

	@Transient
	public int getStation_ID() {
		return stationassigned.getStation_ID();
	}

	@Transient
	public String getAgent_username() {
		return agent.getUsername();
	}

	@Transient
	public String getFaultstationcode() {
		return (faultstation == null ? "" : faultstation.getStationcode());
	}

	/**
	 * @return Returns the printedreceipt.
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPrintedreceipt() {
		return printedreceipt;
	}
	
	/**
	 * @param printedreceipt The printedreceipt to set.
	 */
	public void setPrintedreceipt(Date printedreceipt) {
		this.printedreceipt = printedreceipt;
	}

	@Transient
	public String getRcreatedate() {
		Date completedate = DateUtils.convertToDate(getCreatedate().toString(), TracingConstants.DB_DATEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT, null, _TIMEZONE);
	}

	@Transient
	public String getRcreatetime() {
		Date completedate = DateUtils.convertToDate(getCreatetime().toString(), TracingConstants.DB_TIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _TIMEFORMAT, null, _TIMEZONE);
	}

//	@Transient
//	public String getText() {
//
//		StringBuffer ret = new StringBuffer(1096);
//		ret.append(format(this.getIncident_ID()));
//		if (this.getStationcreated() != null) {
//			ret.append(format(this.getStationcreated().getStationcode()));
//		}
//		if (this.getDispStationAssigned() != null) {
//			ret.append(format(this.getDispStationAssigned().getStationcode()));
//		}
//		if (this.getAgent() != null) {
//			ret.append(format(this.getAgent().getUsername()));
//		}
//		ret.append(format(this.getRecordlocator()));
//		ret.append(format(this.getTicketnumber()));
//		ret.append(format(this.getReportMethodString(this.getReportmethod())));
//		ret.append(format(this.getCheckedlocation()));
//		ret.append(format(this.getStatus().getTextDescription()));
//
//		if (this.getPassengers() != null && this.getPassengers().size() > 0) {
//			for (Passenger pass : this.getPassengers()) {
//				ret.append(format(pass.getFirstname()));
//				ret.append(format(pass.getMiddlename()));
//				ret.append(format(pass.getLastname()));
//				ret.append(format(pass.getJobtitle()));
//				ret.append(format(pass.getDriverslicense()));
//				ret.append(format(pass.getDispdlstate()));
//				ret.append(format(pass.getCommonnum()));
//				ret.append(format(pass.getDispcountryofissue()));
//				if (pass.getMembership() != null) {
//					ret.append(format(pass.getMembership().getMembershipnum()));
//				}
//
//				if (pass.getAddresses() != null && pass.getAddresses().size() > 0) {
//					for (Iterator j = pass.getAddresses().iterator(); j.hasNext();) {
//						Address addr = (Address) j.next();
//
//						ret.append(format(addr.getAddress1()));
//						ret.append(format(addr.getAddress2()));
//						ret.append(format(addr.getCity()));
//						ret.append(format(addr.getZip()));
//						ret.append(format(addr.getHotel()));
//						ret.append(format(addr.getHomephone()));
//						ret.append(format(addr.getWorkphone()));
//						ret.append(format(addr.getMobile()));
//						ret.append(format(addr.getPager()));
//						ret.append(format(addr.getAltphone()));
//						ret.append(format(addr.getEmail()));
//						ret.append(format(addr.getState()));
//						ret.append(format(addr.getCountry()));
//						ret.append(format(addr.getProvince()));
//					}
//				}
//			}
//		}
//
//		if (this.getRemarks() != null && this.getRemarks().size() > 0) {
//			for (Remark remark : this.getRemarks()) {
//				ret.append(format(remark.getRemarktext()));
//			}
//		}
//
//		if (this.getItinerary() != null && this.getItinerary().size() > 0) {
//			for (Itinerary itinerary : this.getItinerary()) {
//				ret.append(format(itinerary.getAirline()));
//				ret.append(format(itinerary.getFlightnum()));
//				ret.append(format(itinerary.getLegfrom()));
//				ret.append(format(itinerary.getLegto()));
//			}
//		}
//
//		if (this.getClaimchecks() != null && this.getClaimchecks().size() > 0) {
//			for (Incident_Claimcheck ccheck : this.getClaimchecks()) {
//				ret.append(format(ccheck.getClaimchecknum()));
//			}
//		}
//
//		if (this.getItemlist() != null && this.getItemlist().size() > 0) {
//			for (Item item : this.getItemlist()) {
//				ret.append(format(item.getColor()));
//				ret.append(format(item.getBagtype()));
//				ret.append(format(item.getXdescelement1()));
//				ret.append(format(item.getXdescelement2()));
//				ret.append(format(item.getXdescelement3()));
//				ret.append(format(item.getManufacturer()));
//				ret.append(format(item.getManufacturer_other()));
//				
//				if (item.getInventory() != null && item.getInventory().size() > 0) {
//					for (Iterator ii = item.getInventory().iterator(); ii.hasNext();) {
//						Item_Inventory iin = (Item_Inventory) ii.next();
//						ret.append(format(iin.getDescription()));
//					}
//				}
//				ret.append(format(item.getResolutiondesc()));
//				ret.append(format(item.getFnameonbag()));
//				ret.append(format(item.getMnameonbag()));
//				ret.append(format(item.getLnameonbag()));
//				ret.append(format(item.getDamage()));
//				ret.append(format(item.getDisplvlofdamage()));
//			}
//		}
//
//		if (this.getArticles() != null && this.getArticles().size() > 0) {
//			for (Articles artcl : this.getArticles()) {
//				ret.append(format(artcl.getArticle()));
//				ret.append(format(artcl.getDescription()));
//			}
//		}
//		return ret.toString();
//	}

	@Transient
	public Station getDispStationAssigned() {
		Station ret = null;

		if (this.getStationassigned() != null)
			ret = StationBMO.getStation("" + this.getStationassigned().getStation_ID());

		return ret;
	}
	
	public long getChecklist_version() {
		return checklist_version;
	}

	public void setChecklist_version(long checklist_version) {
		this.checklist_version = checklist_version;
	}

	private String format(String val) {
		if (val == null)
			return " ";
		else
			return val + " ";
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 23;
		result = 37 * result + (Incident_ID == null ? 0 : Incident_ID.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object otherObject) {
		// TODO Auto-generated method stub
		if(this == otherObject) return true;
		if(otherObject == null) return false;
		if(!(otherObject instanceof Incident)) return false;
		
		if(Incident_ID == null) return false;
		
		Incident o = (Incident) otherObject;
		return Incident_ID.equals(o.getIncident_ID());
	}

	@OneToOne(mappedBy = "incident")
	public CrmFile getCrmFile() {
		return crmFile;
	}

	public void setCrmFile(CrmFile crmFile) {
		this.crmFile = crmFile;
	}

	private Double roundToTwoDecimals(Double d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
    	return Double.valueOf(twoDForm.format(d));
	}
	
	@Basic
	public long getOc_claim_id() {
  	return oc_claim_id;
  }
	public void setOc_claim_id(long oc_claim_id) {
  	this.oc_claim_id = oc_claim_id;
  }
	  
		@Column(name = "wtStationId", length=3)
		public String getWtStationCode() {
			return wtStationCode;
		}

		public void setWtStationCode(String wtStationCode) {
			this.wtStationCode = wtStationCode;
		}

		@Column(name = "wtCompanyId", length=3)
		public String getWtCompanyCode() {
			return wtCompanyCode;
		}

		public void setWtCompanyCode(String wtCompanyCode) {
			this.wtCompanyCode = wtCompanyCode;
		}

	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getRxTimestamp() {
		return rxTimestamp;
	}

	public void setRxTimestamp(Date rxTimestamp) {
		this.rxTimestamp = rxTimestamp;
	}

	public int getCourtesyReasonId() {
		return courtesyReasonId;
	}

	public void setCourtesyReasonId(int courtesyReasonId) {
		this.courtesyReasonId = courtesyReasonId;
	}

	@Column(length=100)
	public String getCourtesyDescription() {
		return courtesyDescription;
	}

	public void setCourtesyDescription(String courtesyDescription) {
		this.courtesyDescription = courtesyDescription;
	}

	@Column(columnDefinition="int(11) default 1301")
	public int getCustCommId() {
		return custCommId;
	}

	public void setCustCommId(int custCommId) {
		this.custCommId = custCommId;
	}

	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "incident")
	@org.hibernate.annotations.OrderBy(clause = "createDate")
	@Fetch(FetchMode.SELECT)
	public Set<IncidentActivity> getActivities() {
		return activities;
	}

	public void setActivities(Set<IncidentActivity> activities) {
		this.activities = activities;
	}
	
	@Transient
	public boolean isSwaLocked(){
		return false;
	}
	
}