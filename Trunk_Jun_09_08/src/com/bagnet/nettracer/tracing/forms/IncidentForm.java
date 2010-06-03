package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.CrmFile;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.WorldTracerFile;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for viewing, updating, and
 * inserting reports (Lost/Delayed, Damaged, On-hand)
 */
public final class IncidentForm extends ValidatorForm {

	private String Incident_ID;

	private int version;
	private String pcn_id = "";

	//associating reports
	private String ld_inc_ID = "";
	private String damage_inc_ID = "";
	private String ma_inc_ID = "";
	private String assoc_ID = "";

	private int readonly = 0;
	private int allow_remark_update = 0;

	private int loss_code;
	private Agent agent;
	private Agent agentassigned;
	private Station stationassigned = new Station();
	private Station stationcreated = new Station();
	private Station faultstation = new Station();
	private String faultcompany_id;
	private Status status;
	private Date createdate;
	private Date createtime;
	private Date closedate;
	private String ticketnumber;
	private String recordlocator;
	private String manualreportnum;
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
	private Date printedreceipt;
	private String bagTagNumber;
	private String language;
	
	private Double overall_weight;
	private String overall_weight_unit;

	public String getOverall_weight_unit() {
		return overall_weight_unit;
	}
	public void setOverall_weight_unit(String overall_weight_unit) {
		this.overall_weight_unit = overall_weight_unit;
	}
	public Double getOverall_weight() {
		return overall_weight;
	}
	public void setOverall_weight(Double overall_weight) {
		this.overall_weight = overall_weight;
	}
	
	
	// airline membership
	private Company company;
	private String membershipnum;
	private String membershipstatus;
	private String otherSystemInformation;
	private CrmFile crmFile;
	
	private boolean notifiedOfRequirements;
	private boolean remarkEnteredWhenNotifiedOfRequirements;
	
	
	//a WestJet feature
	public boolean isRemarkEnteredWhenNotifiedOfRequirements() {
		return remarkEnteredWhenNotifiedOfRequirements;
	}
	public void setRemarkEnteredWhenNotifiedOfRequirements(
			boolean remarkEnteredWhenNotifiedOfRequirements) {
		this.remarkEnteredWhenNotifiedOfRequirements = remarkEnteredWhenNotifiedOfRequirements;
	}


	private WorldTracerFile wtFile;
	{
		String defChecked = TracerProperties.get(TracerProperties.DEFAULT_CHECKED_LOCATION);
		if(defChecked != null) {
			checkedlocation = defChecked;
		}
	}

	public String getWt_id() {
		return wtFile != null ? wtFile.getWt_id() : null;
	}

	public WorldTracerFile getWtFile() {
		return wtFile;
	}

	public void setWtFile(WorldTracerFile wtFile) {
		this.wtFile = wtFile;
	}
	private List<Passenger> passengerlist = new ArrayList<Passenger>();
	// addresses
	//private List addresses = new ArrayList();
	//remark table
	private List<Remark> remarklist = new ArrayList<Remark>();
	//item table
	private List<Item> itemlist = new ArrayList<Item>();
	// articles table
	private List<Articles> articlelist = new ArrayList<Articles>();
	//itinerary table
	// passenger itinerary itinerarytype = 0 ; bag itinerarytype = 1
	private List<Itinerary> itinerarylist = new ArrayList<Itinerary>();

	private List<Incident_Claimcheck> claimchecklist = new ArrayList<Incident_Claimcheck>();

	private Claim claim;
	private List<ExpensePayout> expenselist = new ArrayList<ExpensePayout>();
	
	private List<BDO_Passenger> bdo_passengerlist = new ArrayList<BDO_Passenger>();

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;

	private int email_customer;

	@Override
	public void reset(org.apache.struts.action.ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
		super.reset(mapping, request);
		Agent agent = (Agent)request.getSession().getAttribute("user");
		if(agent != null) {
			if(agent.getStation() != null) {
				language = agent.getStation().getEmailLanguage();
			}
		}
		if (passengerlist != null) {
			for (Passenger p : passengerlist) {
				if (p.getAddresses() != null) {
					for (Address a : (Iterable<Address>) p.getAddresses()) {
						a.setPermanent(false);
					}
				}
			}
		}
		String defChecked = TracerProperties.get(TracerProperties.DEFAULT_CHECKED_LOCATION);
		if(defChecked != null) {
			checkedlocation = defChecked;
		}
		
	}

	/*
	 * public void reset(ActionMapping mapping,
	 * javax.servlet.http.HttpServletRequest request) { if
	 * (request.getMethod().equals("POST")) { // clear all value
	 * getPassenger(0); itinerarylist = new ArrayList(); remarklist = new
	 * ArrayList(); articlelist = new ArrayList(); claimchecklist = new
	 * ArrayList();
	 * 
	 * Item i = getItem(0, 1);
	 * i.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
	 * i.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
	 * i.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X); i.setBagnumber(0);
	 * }
	 * 
	 * for (int i=0;i<getPassengerlist().size();i++) { Passenger pa =
	 * getPassenger(i); Address ad = pa.getAddress(0); ad.setIs_permanent(0); }
	 * 
	 * }
	 */

	/**
	 * @return Returns the version. just a place holder for versioning
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
	 * @return Returns the readonly.
	 */
	public int getReadonly() {
		return readonly;
	}

	
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return Returns the allow_remark_update.
	 */
	public int getAllow_remark_update() {
		return allow_remark_update;
	}
	/**
	 * @param allow_remark_update The allow_remark_update to set.
	 */
	public void setAllow_remark_update(int allow_remark_update) {
		this.allow_remark_update = allow_remark_update;
	}
	
	/**
	 * @param readonly
	 *          The readonly to set.
	 */
	public void setReadonly(int readonly) {
		this.readonly = readonly;
	}

	/**
	 * ************** beginning of the custom code *************
	 */

	public List<Passenger> getPassengerlist() {
		return passengerlist;
	}

	public void setPassengerlist(List<Passenger> passengerlist) {
		this.passengerlist = passengerlist;
	}

	public Passenger getPassenger(int index) {
		if (index < 0) index = 0;
		if (this.passengerlist.size() <= index) {
			Passenger a = new Passenger();
			if (index == 0) a.setIsprimary(1);
			AirlineMembership am = new AirlineMembership();
			String setDefault = PropertyBMO.getValue(PropertyBMO.PROPERTY_SET_DEFAULT_MEMBERSHIP);
			if(setDefault != null && !setDefault.equalsIgnoreCase("false")) {
				am.setCompanycode_ID(agent.getCompanycode_ID());
			}
			a.setMembership(am);
			Address addr = new Address();
			addr.set_DATEFORMAT(get_DATEFORMAT());
			addr.setCountrycode_ID(PropertyBMO.getValue(PropertyBMO.PROPERTY_DEFAULT_COUNTRY));
			addr.setPassenger(a);
			a.addAddress(addr);
			this.passengerlist.add(a);
		} else {
			// prevent null membership
			Passenger pa = (Passenger) this.passengerlist.get(index);
			if (pa.getMembership() == null) {
				AirlineMembership am = new AirlineMembership();
				String setDefault = PropertyBMO.getValue(PropertyBMO.PROPERTY_SET_DEFAULT_MEMBERSHIP);
				if(setDefault != null && !setDefault.equalsIgnoreCase("false")) {
					am.setCompanycode_ID(agent.getCompanycode_ID());
				}
				pa.setMembership(am);
				this.passengerlist.set(index,pa);
			}
		}

		return this.passengerlist.get(index);
	}

	public Address getAddresses(int addressIdx) {
		//Not more than 20 addresses per passenger
		int pIndex = (int) addressIdx / 20;
		int aIndex = addressIdx % 20;

		return this.getPassenger(pIndex).getAddress(aIndex);
	}


	
	public List<BDO_Passenger> getBdo_passengerlist() {
		return bdo_passengerlist;
	}

	public void setBdo_passengerlist(List<BDO_Passenger> bdo_passengerlist) {
		this.bdo_passengerlist = bdo_passengerlist;
	}

	public List<Remark> getRemarklist() {
		return remarklist;
	}

	public Remark getRemark(int index) {
		if (index < 0) index = 0;
		Remark r = null;
		while (this.remarklist.size() <= index) {
			r = new Remark();
			r.setRemarktype(TracingConstants.REMARK_REGULAR);
			this.remarklist.add(r);
		}
		return (Remark) this.remarklist.get(index);
	}

	public Remark getClosingRemark(int index) {
		if (index < 0) index = 0;
		Remark r = null;
		while (this.remarklist.size() <= index) {
			r = new Remark();
			r.setRemarktype(TracingConstants.REMARK_CLOSING);
			this.remarklist.add(r);
		}
		return (Remark) this.remarklist.get(index);
	}

	public void setRemarklist(List<Remark> remarklist) {
		this.remarklist = remarklist;
	}

	public List<Item> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<Item> itemlist) {
		this.itemlist = itemlist;
	}

	// for struts form to work
	public Item getTheitem(int index) {
		if (index < 0) index = 0;
		if (this.itemlist.size() <= index) {

			this.itemlist.add(new Item());
		}
		return (Item) this.itemlist.get(index);
	}

	public Item getItem(int index, int itemtype) {
		if (index < 0) index = 0;
		if (this.itemlist.size() <= index) {
			Item item = new Item(itemtype);
			item.setBagnumber(index);
			int inventoryCount = 1;
			String tmp = PropertyBMO.getValue(PropertyBMO.PROPERTY_NUM_CONTENT_FIELDS);
			if(tmp != null) {
				try {
					inventoryCount = Integer.parseInt(tmp);
				} catch (NumberFormatException e) {
					//pass
				}
			}
			for(int i = 0; i < inventoryCount; i++) {
				Item_Inventory ii = new Item_Inventory();
				ii.setItem(item);
				item.getInventorylist().add(ii);
			}
			this.itemlist.add(item);
			return item;
		}
		return (Item) this.itemlist.get(index);
	}


	public Item_Inventory getInventorylist(int inventoryIdx) {
		//Not more than 20 addresses per passenger
		int pIndex = (int) inventoryIdx / 20;
		int aIndex = inventoryIdx % 20;
		
		if (this.getItem(pIndex,-1).getInventorylist().size() <= aIndex) {
			Item_Inventory ii = new Item_Inventory();
			ii.setItem(this.getItem(pIndex,-1));
			this.getItem(pIndex,-1).getInventorylist().add(ii);
			return ii;
		}
		return (Item_Inventory)this.getItem(pIndex,-1).getInventorylist().get(aIndex);
	}


	/**
	 * @return Returns the claimchecklist.
	 */
	public List<Incident_Claimcheck> getClaimchecklist() {
		return claimchecklist;
	}

	/**
	 * @param claimchecklist
	 *          The claimchecklist to set.
	 */
	public void setClaimchecklist(List<Incident_Claimcheck> claimchecklist) {
		this.claimchecklist = claimchecklist;
	}

	public Incident_Claimcheck getClaimcheck(int index) {
		if (index < 0) index = 0;
		while (this.claimchecklist.size() <= index) {
			this.claimchecklist.add(new Incident_Claimcheck());
		}
		return (Incident_Claimcheck) this.claimchecklist.get(index);
	}

	public List<Itinerary> getItinerarylist() {
		return itinerarylist;
	}

	public List<Itinerary> getBagItineraryList() {

		List<Itinerary> bagList = new ArrayList<Itinerary>();
		if (this.getItinerarylist() != null && this.getItinerarylist().size() > 0) {
			for (Itinerary itin : itinerarylist) {
				if (itin.getItinerarytype() == 1) {
					bagList.add(itin);
				}
			}
		}

		return bagList;
	}

	public List<Itinerary> getPassItineraryList() {

		List<Itinerary> passList = new ArrayList<Itinerary>();
		if (this.getItinerarylist() != null && this.getItinerarylist().size() > 0) {
			for (Itinerary itin : itinerarylist) {
				if (itin.getItinerarytype() == 0) {
					passList.add(itin);
				}
			}
		}

		return passList;
	}

	public void setItinerarylist(List<Itinerary> itinerarylist) {
		this.itinerarylist = itinerarylist;
	}

	/** prevent outofbound * */
	public Itinerary getTheitinerary(int index) {
		while (this.itinerarylist.size() <= index) {
			this.itinerarylist.add(new Itinerary());
		}
		return itinerarylist.get(index);
	}

	public Itinerary getItinerary(int index, int type) {
		if (index < 0) index = 0;
		if (this.itinerarylist.size() <= index) {
			Itinerary i = new Itinerary();
			i.set_DATEFORMAT(get_DATEFORMAT());
			i.set_TIMEFORMAT(get_TIMEFORMAT());
			//i.setAirline(TracingConstants.DEFAULT_AIRLINE);
			
			if (type >= 0) i.setItinerarytype(type);

			/* following code set the legs types to the legfrom and legto */
			// first two itinerary( passenger and bag)
			if (index <= 1) {
				i.setLegfrom_type(TracingConstants.LEG_B_STATION);
				i.setLegto_type(TracingConstants.LEG_E_STATION);
			} else {
				// make the previous terminating station into trasfer station
				int tempindex = index - 2;
				Itinerary tempi = getItinerary(tempindex, -1);
				tempi.setLegto_type(TracingConstants.LEG_T_STATION);
				i.setLegfrom_type(TracingConstants.LEG_T_STATION);
				i.setLegto_type(TracingConstants.LEG_E_STATION);
			}

			this.itinerarylist.add(i);

		}

		return this.itinerarylist.get(index);
	}

	public List<Articles> getArticlelist() {
		return articlelist;
	}

	public void setArticlelist(List<Articles> articlelist) {
		this.articlelist = articlelist;
	}

	public Articles getArticle(int index) {
		if (index < 0) index = 0;
		// if index is bigger than size, add a new article
		while (this.articlelist.size() <= index) {
			Articles a = new Articles();
			a.set_DATEFORMAT(agent.getDateformat().getFormat());
			this.articlelist.add(a);
		}
		return (Articles) this.articlelist.get(index);
	}

	/**
	 * @return Returns the createdate.
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

	/**
	 * @return Returns the closedate.
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

	/**
	 * 
	 * @return the createtime for display only
	 */
	public String getDispcreatetime() {

		return DateUtils.formatDate(getCreatedate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	public String getDispclosedate() {
		return DateUtils.formatDate(getClosedate(),	get_DATEFORMAT() + " " + get_TIMEFORMAT(), null, get_TIMEZONE());
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Agent getAgent() {
		return agent;
	}

	public String getAgentinit() {
		return agent.getUsername();
	}


	public Agent getAgentassigned() {
		if (agentassigned == null) 
			agentassigned = new Agent();
		
		return agentassigned;
	}

	public void setAgentassigned(Agent agentassigned) {
		this.agentassigned = agentassigned;
	}
	
	public int getAgentassigned_ID() {
		return getAgentassigned().getAgent_ID();
	}

	public void setAgentassigned_ID(int agent_ID) {
		getAgentassigned().setAgent_ID(agent_ID);
	}
	/**
	 * @return Returns the stationcreated.
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

	public int getStationcreated_ID() {
		return getStationcreated().getStation_ID();
	}

	public void setStationcreated_ID(int station_ID) {
		getStationcreated().setStation_ID(station_ID);
	}

	public String getStationcreatedcode() {
		return getStationcreated().getStationcode();
	}

	/**
	 * @return Returns the stationassigned.
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

	public int getStationassigned_ID() {
		return getStationassigned().getStation_ID();
	}

	public void setStationassigned_ID(int station_ID) {
		getStationassigned().setStation_ID(station_ID);
	}

	/**
	 * @return Returns the faultcompany_id.
	 */
	public String getFaultcompany_id() {
		return faultcompany_id;
	}

	/**
	 * @param faultcompany_id
	 *          The faultcompany_id to set.
	 */
	public void setFaultcompany_id(String faultcompany_id) {
		this.faultcompany_id = faultcompany_id;
	}

	/**
	 * @return Returns the faultstation.
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

	public int getFaultstation_id() {
		return getFaultstation().getStation_ID();
	}

	public void setFaultstation_id(int station_id) {
		getFaultstation().setStation_ID(station_id);
	}

	public int getStatus_ID() {
		return getStatus().getStatus_ID();
	}

	public void setStatus_ID(int status_ID) {
		getStatus().setStatus_ID(status_ID);
	}

	/** for airline membership companycode */
	public String getCompanycode_ID() {
		if (getCompany() != null) return getCompany().getCompanyCode_ID();
		return "";
	}

	/** for airline membership companycode */
	public void setCompanycode_ID(String companycode_ID) {
		if (getCompany() == null) setCompany(new Company());
		getCompany().setCompanyCode_ID(companycode_ID);
	}

	/**
	 * ************** end of the custom code *************
	 */

	/**
	 * @return Returns the status.
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
	 * @return Returns the manualreportnum.
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
	 * @return Returns the checkedlocation.
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
	 * @return Returns the nonrevenue.
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
	 * @return Returns the incident_ID.
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
	 * @return Returns the company.
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 *          The company to set.
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return Returns the membershipnum.
	 */
	public String getMembershipnum() {
		return membershipnum;
	}

	/**
	 * @param membershipnum
	 *          The membershipnum to set.
	 */
	public void setMembershipnum(String membershipnum) {
		this.membershipnum = membershipnum;
	}

	/**
	 * @return Returns the membershipstatus.
	 */
	public String getMembershipstatus() {
		return membershipstatus;
	}

	/**
	 * @param membershipstatus
	 *          The membershipstatus to set.
	 */
	public void setMembershipstatus(String membershipstatus) {
		this.membershipstatus = membershipstatus;
	}

	/**
	 * @return Returns the email_customer.
	 */
	public int getEmail_customer() {
		return email_customer;
	}

	/**
	 * @param email_customer
	 *          The email_customer to set.
	 */
	public void setEmail_customer(int email_customer) {
		this.email_customer = email_customer;
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
	 * @return Returns the claims.
	 */
	public Claim getClaim() {
		return claim;
	}

	/**
	 * @param claims
	 *          The claims to set.
	 */
	public void setClaim(Claim claim) {
		this.claim = claim;
	}

	public void setExpenselist(List<ExpensePayout> expenselist) {
		this.expenselist = expenselist;
	}

	public List<ExpensePayout> getExpenselist() {
		return expenselist;
	}

	/**
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
	 * @return Returns the damage_inc_ID.
	 */
	public String getDamage_inc_ID() {
		return damage_inc_ID;
	}

	/**
	 * @param damage_inc_ID
	 *          The damage_inc_ID to set.
	 */
	public void setDamage_inc_ID(String damage_inc_ID) {
		this.damage_inc_ID = damage_inc_ID;
	}

	/**
	 * @return Returns the ld_inc_ID.
	 */
	public String getLd_inc_ID() {
		return ld_inc_ID;
	}

	/**
	 * @param ld_inc_ID
	 *          The ld_inc_ID to set.
	 */
	public void setLd_inc_ID(String ld_inc_ID) {
		this.ld_inc_ID = ld_inc_ID;
	}

	/**
	 * @return Returns the ma_inc_ID.
	 */
	public String getMa_inc_ID() {
		return ma_inc_ID;
	}

	/**
	 * @param ma_inc_ID
	 *          The ma_inc_ID to set.
	 */
	public void setMa_inc_ID(String ma_inc_ID) {
		this.ma_inc_ID = ma_inc_ID;
	}

	/**
	 * @return Returns the assoc_ID.
	 */
	public String getAssoc_ID() {
		return assoc_ID;
	}

	/**
	 * @param assoc_ID
	 *          The assoc_ID to set.
	 */
	public void setAssoc_ID(String assoc_ID) {
		this.assoc_ID = assoc_ID;
	}


	/**
	 * @return Returns the printedreceipt.
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

	/**
	 * @return the otherSystemInformation
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

	public String getBagTagNumber() {
		return bagTagNumber;
	}

	public void setBagTagNumber(String bagTagNumber) {
		this.bagTagNumber = bagTagNumber;
	}

	public boolean isNotifiedOfRequirements() {
		return notifiedOfRequirements;
	}

	public void setNotifiedOfRequirements(boolean notifiedOfRequirements) {
		this.notifiedOfRequirements = notifiedOfRequirements;
	}

	public String getPcn_id() {
		return pcn_id;
	}

	public void setPcn_id(String pcn_id) {
		this.pcn_id = pcn_id;
	}

	public CrmFile getCrmFile() {
		return crmFile;
	}

	public void setCrmFile(CrmFile crmFile) {
		this.crmFile = crmFile;
	}
}