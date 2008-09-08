/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Incident"
 * @hibernate.typedef name="worldTracerStatus" class="com.bagnet.nettracer.tracing.utils.StringEnumUserType"
 * @hibernate.typedef-param typedef-name="worldTracerStatus" name="enumClassname"
 * 			value="com.bagnet.nettracer.tracing.db.WorldTracerFile$WTStatus"
 */
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
	private String closedate;
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
	
	private Date printedreceipt;
	
	private Date lastupdated;
	
	private String ohd_lasttraced;
	
	private WorldTracerFile wtFile;	//world tracer id
	private Set passengers;

	private List itemlist;
	private Set articles;
	private Set remarks;
	private Set itinerary;
	private Set claims;

	private Set claimchecks;

	private ArrayList itinerary_list; // for displaying to the search incident
	// page
	private ArrayList claimcheck_list; // for display to the search incident page
	private ArrayList passenger_list; // for displaying to the search incident
	// page

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;
	private String otherSystemInformation;


	public String getReportMethodString(int val) {
		if (val == 0)
			return "In Person";
		else if (val == 1)
			return "BSO Phone";
		else if (val == 2)
			return "Call Center";
		else if (val == 3)
			return "Internet";
		else
			return "Kiosk";
	}

	/**
	 * @return Returns the version.
	 * @hibernate.version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version
	 *          The version to set.
	 */
	public void setVersion(int version) {
		this.version = version;
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
	 *                        column="agent_ID"  foreign-key="agent_ID"
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
	 *                        column="agentassigned_ID" foreign-key="agent_ID"
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
	 * @hibernate.key column="incident_ID" 
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Incident_Claimcheck"
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
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Item"
	 * @hibernate.index column="bagnumber"
	 * @hibernate.key column="incident_ID"
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
	 * @hibernate.key column="incident_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Articles"
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
	 * @hibernate.set cascade="all" inverse="true" order-by="passenger_id"
	 * @hibernate.key column="incident_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Passenger"
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
	 * @hibernate.set cascade="all" inverse="true" order-by="createtime"
	 * @hibernate.key column="incident_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Remark"
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
	 * @hibernate.set cascade="all" inverse="true" order-by="departdate,schdeparttime,itinerary_ID"
	 * @hibernate.key column="incident_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Itinerary"
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
	 * @hibernate.property type="int"
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
	 * @return Returns the lastupdated.
	 * 
	 * @hibernate.property type="timestamp"
	 */
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
		Date completedate = DateUtils.convertToDate(getCreatedate().toString() + " " + getCreatetime().toString(), TracingConstants.DB_DATETIMEFORMAT,
				null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}
	
	public Date getFullCreateDate() {
		return DateUtils.convertToDate(getCreatedate().toString() + " " + getCreatetime().toString(), TracingConstants.DB_DATETIMEFORMAT,
				null);
	}
	
	public Date getFullCloseDate() {
		return DateUtils.convertToDate(getClosedate().toString(), TracingConstants.DB_DATETIMEFORMAT,
				null);
	}

	/**
	 * @return Returns the closedate.
	 * 
	 * @hibernate.property type="string" column="close_date"
	 */
	public String getClosedate() {
		return closedate;
	}

	/**
	 * @param closedate
	 *          The closedate to set.
	 */
	public void setClosedate(String closedate) {
		this.closedate = closedate;
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
	 * @hibernate.id generator-class="assigned" type="string" column="Incident_ID"
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
	 * @hibernate.property type="integer" length="4"
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
	 * @hibernate.property type="integer" length="4"
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
	 * @hibernate.property type="integer" length="4"
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
	 * @hibernate.property type="integer" length="4"
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
	 * @hibernate.property type="integer" length="1"
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
	 * @hibernate.property type="string"
	 */
	public String getOhd_lasttraced() {
		return ohd_lasttraced;
	}

	/**
	 * @param ohd_lasttraced
	 *          The ohd_lasttraced to set.
	 */
	public void setOhd_lasttraced(String ohd_lasttraced) {
		this.ohd_lasttraced = ohd_lasttraced;
	}

	/**
	 * @return Returns the claim.
	 * 
	 * @hibernate.set cascade="all" order-by="claim_ID" inverse="true"
	 * @hibernate.key column="incident_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Claim"
	 *  
	 */
	public Set getClaims() {
		return claims;
	}

	/**
	 * @param claims
	 *          The claims to set.
	 */
	public void setClaims(Set claims) {
		this.claims = claims;
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
	 * @hibernate.component class="com.bagnet.nettracer.tracing.db.WorldTracerFile"
	 */
	public WorldTracerFile getWtFile() {
		return wtFile;
	}


	/**
	 * @param wt_id the wt_id to set
	 */
	public void setWtFile(WorldTracerFile wtFile) {
		this.wtFile = wtFile;
	}
	
	public String getWt_id() {
		if(wtFile != null) {
			return wtFile.getWt_id();
		}
		return null;
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

	/** * for reporting ** */
	public int getItemtype_ID() {
		return itemtype.getItemType_ID();
	}

	public String getTypedesc() {
		return itemtype.getDescription();
	}

	public String getStatusdesc() {
		return status.getDescription();
	}

	public String getStationcode() {
		return stationassigned.getStationcode();
	}

	public int getStation_ID() {
		return stationassigned.getStation_ID();
	}

	public String getAgent_username() {
		return agent.getUsername();
	}

	public String getFaultstationcode() {
		return (faultstation == null ? "" : faultstation.getStationcode());
	}

	/**
	 * @return Returns the printedreceipt.
	 * 
	 * @hibernate.property type="timestamp"
	 */
	public Date getPrintedreceipt() {
		return printedreceipt;
	}
	/**
	 * @param printedreceipt The printedreceipt to set.
	 */
	public void setPrintedreceipt(Date printedreceipt) {
		this.printedreceipt = printedreceipt;
	}

	
	public String getRcreatedate() {
		Date completedate = DateUtils.convertToDate(getCreatedate().toString(), TracingConstants.DB_DATEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT, null, _TIMEZONE);
	}

	public String getRcreatetime() {
		Date completedate = DateUtils.convertToDate(getCreatetime().toString(), TracingConstants.DB_TIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _TIMEFORMAT, null, _TIMEZONE);
	}

	public String getText() {

		StringBuffer ret = new StringBuffer(1096);
		ret.append(format(this.getIncident_ID()));
		if (this.getStationcreated() != null) {
			ret.append(format(this.getStationcreated().getStationcode()));
		}
		if (this.getDispStationAssigned() != null) {
			ret.append(format(this.getDispStationAssigned().getStationcode()));
		}
		if (this.getAgent() != null) {
			ret.append(format(this.getAgent().getUsername()));
		}
		ret.append(format(this.getRecordlocator()));
		ret.append(format(this.getTicketnumber()));
		ret.append(format(this.getReportMethodString(this.getReportmethod())));
		ret.append(format(this.getCheckedlocation()));
		ret.append(format(this.getStatus().getDescription()));

		if (this.getPassengers() != null && this.getPassengers().size() > 0) {
			for (Iterator i = this.getPassengers().iterator(); i.hasNext();) {
				Passenger pass = (Passenger) i.next();

				ret.append(format(pass.getFirstname()));
				ret.append(format(pass.getMiddlename()));
				ret.append(format(pass.getLastname()));
				ret.append(format(pass.getJobtitle()));
				ret.append(format(pass.getDriverslicense()));
				ret.append(format(pass.getDispdlstate()));
				ret.append(format(pass.getCommonnum()));
				ret.append(format(pass.getDispcountryofissue()));
				if (pass.getMembership() != null) {
					ret.append(format(pass.getMembership().getMembershipnum()));
				}

				if (pass.getAddresses() != null && pass.getAddresses().size() > 0) {
					for (Iterator j = pass.getAddresses().iterator(); j.hasNext();) {
						Address addr = (Address) j.next();

						ret.append(format(addr.getAddress1()));
						ret.append(format(addr.getAddress2()));
						ret.append(format(addr.getCity()));
						ret.append(format(addr.getZip()));
						ret.append(format(addr.getHotel()));
						ret.append(format(addr.getHomephone()));
						ret.append(format(addr.getWorkphone()));
						ret.append(format(addr.getMobile()));
						ret.append(format(addr.getPager()));
						ret.append(format(addr.getAltphone()));
						ret.append(format(addr.getEmail()));
						ret.append(format(addr.getState()));
						ret.append(format(addr.getCountry()));
						ret.append(format(addr.getProvince()));
					}
				}
			}
		}

		if (this.getRemarks() != null && this.getRemarks().size() > 0) {
			for (Iterator i = this.getRemarks().iterator(); i.hasNext();) {
				Remark remark = (Remark) i.next();
				ret.append(format(remark.getRemarktext()));
			}
		}

		if (this.getItinerary() != null && this.getItinerary().size() > 0) {
			for (Iterator i = this.getItinerary().iterator(); i.hasNext();) {
				Itinerary itinerary = (Itinerary) i.next();
				ret.append(format(itinerary.getAirline()));
				ret.append(format(itinerary.getFlightnum()));
				ret.append(format(itinerary.getLegfrom()));
				ret.append(format(itinerary.getLegto()));
			}
		}

		if (this.getClaimchecks() != null && this.getClaimchecks().size() > 0) {
			for (Iterator i = this.getClaimchecks().iterator(); i.hasNext();) {
				Incident_Claimcheck ccheck = (Incident_Claimcheck) i.next();
				ret.append(format(ccheck.getClaimchecknum()));
			}
		}

		if (this.getItemlist() != null && this.getItemlist().size() > 0) {
			for (Iterator i = this.getItemlist().iterator(); i.hasNext();) {
				Item item = (Item) i.next();

				ret.append(format(item.getColor()));
				ret.append(format(item.getBagtype()));
				ret.append(format(item.getXdescelement1()));
				ret.append(format(item.getXdescelement2()));
				ret.append(format(item.getXdescelement3()));
				ret.append(format(item.getManufacturer()));
				ret.append(format(item.getManufacturer_other()));
				
				if (item.getInventory() != null && item.getInventory().size() > 0) {
					for (Iterator ii = item.getInventory().iterator(); ii.hasNext();) {
						Item_Inventory iin = (Item_Inventory) ii.next();
						ret.append(format(iin.getDescription()));
					}
				}
				ret.append(format(item.getResolutiondesc()));
				ret.append(format(item.getFnameonbag()));
				ret.append(format(item.getMnameonbag()));
				ret.append(format(item.getLnameonbag()));
				ret.append(format(item.getDamage()));
				ret.append(format(item.getDisplvlofdamage()));
			}
		}

		if (this.getArticles() != null && this.getArticles().size() > 0) {
			for (Iterator i = this.getArticles().iterator(); i.hasNext();) {
				Articles artcl = (Articles) i.next();

				ret.append(format(artcl.getArticle()));
				ret.append(format(artcl.getDescription()));
			}
		}
		return ret.toString();
	}

	public Station getDispStationAssigned() {
		Station ret = null;

		if (this.getStationassigned() != null)
			ret = StationBMO.getStation("" + this.getStationassigned().getStation_ID());

		return ret;
	}

	public String format(String val) {
		if (val == null)
			return " ";
		else
			return val + " ";
	}

	/**
	 * @return the otherSystemInformation
	 * @hibernate.property type="string" column="other_system_information"
	 */
	public String getOtherSystemInformation() {
		return otherSystemInformation;
	}

	/**
	 * @param otherSystemInformation the otherSystemInformation to set
	 */
	public void setOtherSystemInformation(String otherSystemInformation) {
		this.otherSystemInformation = otherSystemInformation;
	}

}