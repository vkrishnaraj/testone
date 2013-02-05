/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.DeliveryInstructions;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Audit_Incident"
 */
public class Audit_Incident implements Serializable {

	private static final long serialVersionUID = 1L;
	private int audit_incident_id;

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
	private Status status;
	private int loss_code;

	private Date ohd_lasttraced;
	
	private String wt_id;

	private Set passengers;

	private List itemlist;
	private Set articles;
	private Set remarks;
	private Set itinerary;
	private long oc_claim_id;

	private Set claimchecks;
	
	private String instructions;

	private ArrayList itinerary_list; // for displaying to the search incident
	// page
	private ArrayList claimcheck_list; // for display to the search incident page
	private ArrayList passenger_list; // for displaying to the search incident
	// page

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;

	private Agent modify_agent;
	private Date modify_time;
	private String modify_reason;
	
	private String language;
	
	private boolean locked = false;	//for dispute resolution process
	
	private String revenueCode;
	
	private int tracingStatus;

	/**
	 * @return Returns the tracingStatus.
	 * 
	 * @hibernate.property column="tracing_status_id" type="integer"
	 */
	public int getTracingStatus() {
		return tracingStatus;
	}
	
	public void setTracingStatus(int tracingStatus) {
		this.tracingStatus = tracingStatus;
	}
	
	/**
	 * @hibernate.property column="revenue_code" type="string"
	 */
	public String getRevenueCode() {
		return revenueCode;
	}
	
	public void setRevenueCode(String revenueCode) {
		this.revenueCode = revenueCode;
	}
	
	/**
	 * @hibernate.property type="boolean" column="locked"
	 */
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	/**
	 * @return Returns the overall_weight.
	 * 
	 * @hibernate.property type="double"
	 */
	public Double getOverall_weight() {
		return overall_weight;
	}

	public void setOverall_weight(Double overall_weight) {
		this.overall_weight = overall_weight;
	}

	/**
	 * @return Returns the overall_weight_unit.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getOverall_weight_unit() {
		return overall_weight_unit;
	}

	public void setOverall_weight_unit(String overall_weight_unit) {
		this.overall_weight_unit = overall_weight_unit;
	}

	private Double overall_weight;
	private String overall_weight_unit;
	
	/**
	 * @return Returns the instructions associated with this incident.
	 * 
	 * @hibernate.property type="text"
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * @param instructions
	 *          The instructions to set.
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	/**
	 * @return Returns the language.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	private long checklist_version;    // for auto checklist
	
	/**
	 * @return Returns the checklist_versopm.
	 * 
	 * @hibernate.property type="long"
	 */
	public long getChecklist_version() {
		return checklist_version;
	}

	public void setChecklist_version(long checklist_version) {
		this.checklist_version = checklist_version;
	}
	
	/**
	 * @return Returns the modify_agent.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="modify_agent_id"
	 */
	public Agent getModify_agent() {
		return modify_agent;
	}

	/**
	 * @param modify_agent
	 *          The modify_agent to set.
	 */
	public void setModify_agent(Agent modify_agent) {
		this.modify_agent = modify_agent;
	}

	/**
	 * @return Returns the modify_reason.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getModify_reason() {
		return modify_reason;
	}

	/**
	 * @param modify_reason
	 *          The modify_reason to set.
	 */
	public void setModify_reason(String modify_reason) {
		this.modify_reason = modify_reason;
	}

	/**
	 * @return Returns the modify_time.
	 * 
	 * @hibernate.property type="timestamp"
	 */
	public Date getModify_time() {
		return modify_time;
	}

	/**
	 * @param modify_time
	 *          The modify_time to set.
	 */
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}

	public String getDispmodify_time() {
		Date completedate = DateUtils.convertToDate(getModify_time().toString(),
				TracingConstants.DB_DATETIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	/**
	 * @return Returns the audit_incident_id.
	 * 
	 * @hibernate.id generator-class="native" type="integer" unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="audit_incident_0"
	 * 
	 * 
	 *  
	 */
	public int getAudit_incident_id() {
		return audit_incident_id;
	}

	/**
	 * @param audit_incident_id
	 *          The audit_incident_id to set.
	 */
	public void setAudit_incident_id(int audit_incident_id) {
		this.audit_incident_id = audit_incident_id;
	}

	/**
	 * @return Returns the status.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="status_ID"
	 */
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.ItemType"
	 *                        column="itemType_ID"
	 */
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
	 * @return Returns the agent.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="agent_ID"   foreign-key="agent_ID"
	 */
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
	 * @return Returns the agent.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="agentassigned_ID"   foreign-key="agent_ID"
	 */
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="stationassigned_ID" foreign-key="station_ID"
	 */
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="stationcreated_ID" foreign-key="station_ID"
	 */
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="faultstation_ID" foreign-key="station_ID"
	 */
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
	 * @hibernate.set cascade="all" inverse="true" order-by="claimcheck_ID"
	 * @hibernate.key column="audit_incident_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_Incident_Claimcheck"
	 */
	public Set getClaimchecks() {
		return claimchecks;
	}

	/**
	 * @param claimchecks
	 *          The claimchecks to set.
	 */
	public void setClaimchecks(Set claimchecks) {
		this.claimchecks = claimchecks;
		this.claimcheck_list = (claimchecks != null ? new ArrayList(claimchecks) : new ArrayList());
	}

	/**
	 * @return Returns the itemlist.
	 * @hibernate.list cascade="all" inverse="true"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_Item"
	 * @hibernate.index column="bagnumber"
	 * @hibernate.key column="audit_incident_id"
	 *  
	 */

	public List getItemlist() {
		return itemlist;
	}

	/**
	 * @param itemlist
	 *          The itemlist to set.
	 */
	public void setItemlist(List itemlist) {
		this.itemlist = itemlist;
	}

	/**
	 * @return Returns the articles.
	 * 
	 * @hibernate.set cascade="all" inverse="true" order-by="articles_ID DESC"
	 * @hibernate.key column="audit_incident_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_Articles"
	 *  
	 */
	public Set getArticles() {
		return articles;
	}

	/**
	 * @param articles
	 *          The articles to set.
	 */
	public void setArticles(Set articles) {
		this.articles = articles;
	}

	/**
	 * @hibernate.set cascade="all" order-by="passenger_id"
	 * @hibernate.key column="audit_incident_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_Passenger"
	 * @return Returns the passengers.
	 */
	public Set getPassengers() {
		return passengers;
	}

	/**
	 * @param passengers
	 *          The passengers to set.
	 */
	public void setPassengers(Set passengers) {
		this.passengers = passengers;
		this.passenger_list = (passengers != null ? new ArrayList(passengers) : new ArrayList());
	}

	/**
	 * @return Returns the remarks.
	 * 
	 * @hibernate.set cascade="all" order-by="createtime"
	 * @hibernate.key column="audit_incident_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_Remark"
	 *  
	 */
	public Set getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *          The remarks to set.
	 */
	public void setRemarks(Set remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the itinerary.
	 * 
	 * @hibernate.set cascade="all" order-by="itinerary_ID"
	 * @hibernate.key column="audit_incident_id"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.audit.Audit_Itinerary"
	 *  
	 */
	public Set getItinerary() {
		return itinerary;
	}

	/**
	 * @param itinerary
	 *          The itinerary to set.
	 */
	public void setItinerary(Set itinerary) {
		this.itinerary = itinerary;
		this.itinerary_list = (itinerary != null ? new ArrayList(itinerary) : new ArrayList());
	}

	/**
	 * @return Returns the checkedlocation.
	 * 
	 * @hibernate.property type="string" length="1"
	 */
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
	 * @hibernate.property type="integer"
	 */
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
	 * @hibernate.property type="date"
	 */
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
	 * @return Returns the createtime.
	 * 
	 * @hibernate.property type="time"
	 */
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

	public String getDisplaydate() {
		Date completedate = DateUtils.convertToDate(getCreatedate().toString() + " "
				+ getCreatetime().toString(), TracingConstants.DB_DATETIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	/**
	 * @return Returns the closedate.
	 * 
	 * @hibernate.property type="timestamp" column="close_date"
	 */
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

	public String getDispclosedate() {
		return DateUtils.formatDate(getClosedate(), get_DATEFORMAT() + " " + get_TIMEFORMAT(), null, get_TIMEZONE());
	}

	/**
	 * @return Returns the _DATEFORMAT.
	 */
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
	 * @hibernate.property type="string"
	 */
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
	 * @hibernate.property type="integer"
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
	 * @hibernate.property type="integer"
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
	 * @hibernate.property type="integer"
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
	 * @hibernate.property type="integer"
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
	 * @hibernate.property type="string" length="14"
	 */
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
	 * @hibernate.property type="integer"
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
	 * @hibernate.property type="integer"
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
	 * @hibernate.property type="integer"
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
	 * @hibernate.property type="string" length="20"
	 */
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
	 * @hibernate.property type="string"
	 */
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
	 * @hibernate.property type="integer"
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
	 * @hibernate.property @hibernate.property type="timestamp"
	 */
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
	 * @hibernate.property type="integer"
	 * @return Returns the loss_code.
	 */
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
	 * @hibernate.property type="string"
	 */
	public String getWt_id() {
		return wt_id;
	}


	/**
	 * @param wt_id the wt_id to set
	 */
	public void setWt_id(String wt_id) {
		this.wt_id = wt_id;
	}

	public ArrayList getItinerary_list() {
		return itinerary_list;
	}

	public ArrayList getClaimcheck_list() {
		return claimcheck_list;
	}

	public ArrayList getPassenger_list() {
		return passenger_list;
	}

	public ArrayList getRemark_list() {
		return new ArrayList((remarks != null ? remarks : new HashSet()));
	}

	public ArrayList getArticle_list() {
		return new ArrayList((articles != null ? articles : new HashSet()));
	}

	
	/**
	 * @return Returns the oc_claim_id.
	 * 
	 * @hibernate.property type="long"
	 */
	public long getOc_claim_id() {
  	return oc_claim_id;
  }
	public void setOc_claim_id(long oc_claim_id) {
  	this.oc_claim_id = oc_claim_id;
  }

}