package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Task;
import com.bagnet.nettracer.tracing.db.WorldTracerFile;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for adding/modifying/viewing
 * onhands
 */
public final class OnHandForm extends ValidatorForm {
	private static final long serialVersionUID = -612610019210354510L;
	
	private Agent agent;
	private String ohd_id;
	private String dispBagArriveDate = "";
	private String dispWarehouseReceivedDate = "";
	private String dispWarehouseSentDate = "";
	private String found_company;
	private String storage_location;
	private String found_station;
	private Date bagarrivedate;
	private Date inventoryDate;
	private Date warehouseReceivedDate;
	private Date warehouseSentDate;
	private String holding_company;
	private String holding_station;
	private String lastname;
	private String firstname;
	private String middlename;
	private String agent_initials;
	private Date foundTime;
	private Date foundDate;
	private String bagTagNumber;
	private String pnr;
	private Company company = new Company();
	private String membershipnum;
	private String membershipstatus;
	private String bagColor;
	private String bagType;
	private int xDesc1;
	private int xDesc2;
	private int xDesc3;
	private int manufacturer_ID;
	private String manufacturer_other;
	private Status status;
	private Status disposal_status = new Status();
	private Date close_date;
	private List remarklist = new ArrayList();
	private List itemlist = new ArrayList();
	private List passengerList = new ArrayList();
	private List photoList = new ArrayList();
	private List taskList = new ArrayList();
	private List controlList = new ArrayList();
	private List itinerarylist = new ArrayList();
	private List existMatchedItemlist = new ArrayList();
	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE; // current login agent's time zone
	private int ohd_type;
	private int readonly = 0;
	private int allow_remark_update=0;
	private String recordlocator;
	private Boolean earlyBag;
	private String matched_incident;
	private String externaldesc;
	private int existMatchedItemType =0;
	private Status existDisposalStatus;
	private int existRemarkSize;
	
	// forward log
	private String forwarded_station;
	private String forwarded_agent;
	private Date forwarded_date;

	private WorldTracerFile wtFile;
	
	private int faultStation = 0;
	private int lossCode = 0;
	private boolean notifiedOfRequirements;
	
	private String teletypeAddress; //for the big Teletype String for historical reporting
	
	private Date modifiedDate;
	private String modifiedAgent;
	private String posId;
	private boolean lateCheck;
	
	public String getTeletypeAddress() {
		return teletypeAddress;
	}
	public void setTeletypeAddress(String teletypeAddress) {
		this.teletypeAddress = teletypeAddress;
	}
	
	public boolean isNotifiedOfRequirements() {
		return notifiedOfRequirements;
	}

	public void setNotifiedOfRequirements(boolean notifiedOfRequirements) {
		this.notifiedOfRequirements = notifiedOfRequirements;
	}

	/**
	 * @return Returns the ohd_type.
	 */
	public int getOhd_type() {
		return ohd_type;
	}

	/**
	 * @param ohd_type
	 *          The ohd_type to set.
	 */
	public void setOhd_type(int ohd_type) {
		this.ohd_type = ohd_type;
	}

	/**
	 * @return Returns the close_date.
	 */
	public Date getClose_date() {
		return close_date;
	}

	/**
	 * @param close_date
	 *          The close_date to set.
	 */
	public void setClose_date(Date close_date) {
		this.close_date = close_date;
	}

	/**
	 * @return Returns the found_company.
	 */
	public String getFound_company() {
		return found_company;
	}

	/**
	 * @param found_company
	 *          The found_company to set.
	 */
	public void setFound_company(String found_company) {
		this.found_company = found_company;
	}

	/**
	 * @return Returns the holding_company.
	 */
	public String getHolding_company() {
		return holding_company;
	}

	/**
	 * @param holding_company
	 *          The holding_company to set.
	 */
	public void setHolding_company(String holding_company) {
		this.holding_company = holding_company;
	}
	

	/**
	 * @return Returns the externaldesc.
	 * 
	 */
	public String getExternaldesc() {
		return externaldesc;
	}

	/**
	 * @param externaldesc
	 *          The externaldesc to set.
	 */
	public void setExternaldesc(String externaldesc) {
		this.externaldesc = externaldesc;
	}

	/**
	 * @return Returns the holding_station.
	 */
	public String getHolding_station() {
		return holding_station;
	}

	/**
	 * @param holding_station
	 *          The holding_station to set.
	 */
	public void setHolding_station(String holding_station) {
		this.holding_station = holding_station;
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
	 * @return Returns the controlList.
	 */
	public List getControlList() {
		return controlList;
	}

	/**
	 * @param controlList
	 *          The controlList to set.
	 */
	public void setControlList(List controlList) {
		this.controlList = controlList;
	}

	/**
	 * @return Returns the agent_initials.
	 */
	public String getAgent_initials() {
		return agent_initials;
	}

	/**
	 * @param agent_initials
	 *          The agent_initials to set.
	 */
	public void setAgent_initials(String agent_initials) {
		this.agent_initials = agent_initials;
	}

	/**
	 * @return Returns the found_station.
	 */
	public String getFound_station() {
		return found_station;
	}

	/**
	 * 
	 * @return Returns the firstname.
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname
	 *          The firstname to set.
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * 
	 * @return Returns the lastname.
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname
	 *          The lastname to set.
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * 
	 * @return Returns the middlename.
	 */
	public String getMiddlename() {
		return middlename;
	}

	/**
	 * @param middlename
	 *          The middlename to set.
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	/**
	 * @return Returns the readonly.
	 */
	public int getReadonly() {
		return readonly;
	}

	/**
	 * @param readonly
	 *          The readonly to set.
	 */
	public void setReadonly(int readonly) {
		this.readonly = readonly;
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
	 * @param found_station
	 *          The found_station to set.
	 */
	public void setFound_station(String found_station) {
		this.found_station = found_station;
	}

	/**
	 * @return Returns the bagColor.
	 */
	public String getBagColor() {
		return bagColor;
	}

	/**
	 * @param bagColor
	 *          The bagColor to set.
	 */
	public void setBagColor(String bagColor) {
		this.bagColor = bagColor;
	}

	/**
	 * @return Returns the bagTagNumber.
	 */
	public String getBagTagNumber() {
		return bagTagNumber;
	}

	/**
	 * @param bagTagNumber
	 *          The bagTagNumber to set.
	 */
	public void setBagTagNumber(String bagTagNumber) {
		this.bagTagNumber = bagTagNumber;
	}

	/**
	 * @return Returns the bagType.
	 */
	public String getBagType() {
		return bagType;
	}

	/**
	 * @param bagType
	 *          The bagType to set.
	 */
	public void setBagType(String bagType) {
		this.bagType = bagType;
	}

	/**
	 * @return Returns the foundTime.
	 */
	public Date getFoundTime() {
		return foundTime;
	}

	/**
	 * @param foundTime
	 *          The foundTime to set.
	 */
	public void setFoundTime(Date foundTime) {
		this.foundTime = foundTime;
	}

	/**
	 * @return Returns the manufacturer.
	 */
	public int getManufacturer_ID() {
		return manufacturer_ID;
	}

	/**
	 * @param manufacturer
	 *          The manufacturer to set.
	 */
	public void setManufacturer_ID(int manufacturer_ID) {
		this.manufacturer_ID = manufacturer_ID;
	}

	/**
	 * @return Returns the manufacturer_other.
	 */
	public String getManufacturer_other() {
		return manufacturer_other;
	}

	/**
	 * @param manufacturer_other
	 *          The manufacturer_other to set.
	 */
	public void setManufacturer_other(String manufacturer_other) {
		this.manufacturer_other = manufacturer_other;
	}

	/**
	 * @return Returns the ohd_id.
	 */
	public String getOhd_id() {
		return ohd_id;
	}

	/**
	 * @param ohd_id
	 *          The ohd_id to set.
	 */
	public void setOhd_id(String ohd_id) {
		this.ohd_id = ohd_id;
	}

	/**
	 * @return Returns the remarklist.
	 */
	public List getRemarklist() {
		return remarklist;
	}

	/**
	 * @param remarklist
	 *          The remarklist to set.
	 */
	public void setRemarklist(List remarklist) {
		this.remarklist = remarklist;
	}

	/**
	 * @return Returns the xDesc1.
	 */
	public int getXDesc1() {
		return xDesc1;
	}

	/**
	 * @param desc1
	 *          The xDesc1 to set.
	 */
	public void setXDesc1(int desc1) {
		xDesc1 = desc1;
	}

	/**
	 * @return Returns the xDesc2.
	 */
	public int getXDesc2() {
		return xDesc2;
	}

	/**
	 * @param desc2
	 *          The xDesc2 to set.
	 */
	public void setXDesc2(int desc2) {
		xDesc2 = desc2;
	}

	/**
	 * @return Returns the xDesc3.
	 */
	public int getXDesc3() {
		return xDesc3;
	}

	/**
	 * @param desc3
	 *          The xDesc3 to set.
	 */
	public void setXDesc3(int desc3) {
		xDesc3 = desc3;
	}

	/**
	 * @return Returns the itemlist.
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

	public Remark getRemark(int index) {
		Remark r = null;
		while (this.remarklist.size() <= index) {
			r = new Remark();
			r.setRemarktype(TracingConstants.REMARK_REGULAR);
			this.remarklist.add(r);
		}
		return (Remark) this.remarklist.get(index);
	}

	public Remark getClosingRemark(int index) {
		Remark r = null;
		while (this.remarklist.size() <= index) {
			r = new Remark();
			r.setRemarktype(TracingConstants.REMARK_CLOSING);
			this.remarklist.add(r);
		}
		return (Remark) this.remarklist.get(index);
	}

	public OHD_Inventory getItem(int index) {
		if (this.itemlist.size() <= index) {
			this.itemlist.add(new OHD_Inventory());
		}
		return (OHD_Inventory) this.itemlist.get(index);
	}

	public Task getTask(int index) {
		if (this.taskList.size() <= index) {
			this.taskList.add(new Task());
		}
		return (Task) this.taskList.get(index);
	}

	public OHD_Passenger getPassenger(int index) {
		if (index < 0) index = 0;
		if (this.passengerList.size() <= index) {
		//if ((index == 0 && this.passengerList.size() < 1) || (this.passengerList.size() <= index)) {
			OHD_Address address = new OHD_Address();
			address.setCountrycode_ID(PropertyBMO.getValue(PropertyBMO.PROPERTY_DEFAULT_COUNTRY));
			OHD_Passenger passenger = new OHD_Passenger();
			passenger.setIsprimary(0);
			address.setOhd_passenger(passenger);
			passenger.addAddress(address);
			this.passengerList.add(passenger);
		}
		return (OHD_Passenger) this.passengerList.get(index);
	}

	public OHD_Address getAddresses(int addressIdx) {
		//Not more than 20 addresses per passenger
		int pIndex = (int) addressIdx / 20;
		int aIndex = addressIdx % 20;

		return this.getPassenger(pIndex).getAddress(aIndex);
	}

	public void removeAddress(int addressIdx) {

		//Not more than 20 addresses per passenger
		int pIndex = (int) addressIdx / 20;
		int aIndex = addressIdx % 20;

		OHD_Passenger passenger = this.getPassenger(pIndex);
		OHD_Address address = this.getPassenger(pIndex).getAddress(aIndex);
		passenger.getAddresses().remove(address);
	}

	/**
	 * @return Returns the passengerList.
	 */
	public List getPassengerList() {
		return passengerList;
	}

	/**
	 * @param passengerList
	 *          The passengerList to set.
	 */
	public void setPassengerList(List passengerList) {
		this.passengerList = passengerList;
	}

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
	 * @return Returns the disposal_status.
	 */
	public Status getDisposal_status() {
		return disposal_status;
	}

	/**
	 * @param disposal_status
	 *          The disposal_status to set.
	 */
	public void setDisposal_status(Status disposal_status) {
		this.disposal_status = disposal_status;
	}
	
	/**
	 * @return Returns the photoList.
	 */
	public List getPhotoList() {
		return photoList;
	}

	/**
	 * @param photoList
	 *          The photoList to set.
	 */
	public void setPhotoList(List photoList) {
		this.photoList = photoList;
	}

	/**
	 * @return Returns the taskList.
	 */
	public List getTaskList() {
		return taskList;
	}

	/**
	 * @param taskList
	 *          The taskList to set.
	 */
	public void setTaskList(List taskList) {
		this.taskList = taskList;
	}

	/**
	 * @return Returns the itinerarylist.
	 */
	public List getItinerarylist() {
		return itinerarylist;
	}

	/**
	 * @param itinerarylist
	 *          The itinerarylist to set.
	 */
	public void setItinerarylist(List itinerarylist) {
		this.itinerarylist = itinerarylist;
	}

	/**
	 * @return Returns the pnr.
	 */
	public String getPnr() {
		return pnr;
	}

	/**
	 * @param pnr
	 *          The pnr to set.
	 */
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public OHD_Itinerary getItinerary(int index) {
		if (this.itinerarylist.size() <= index) {
			OHD_Itinerary i = new OHD_Itinerary();
			i.set_DATEFORMAT(agent.getDateformat().getFormat());
			i.set_TIMEFORMAT(agent.getTimeformat().getFormat());
			
			//i.setAirline(TracingConstants.DEFAULT_AIRLINE);
			
			i.setItinerarytype(0);
			/* following code set the legs types to the legfrom and legto */
			// first two itinerary( passenger and bag)
			if (index <= 1) {
				i.setLegfrom_type(TracingConstants.LEG_B_STATION);
				i.setLegto_type(TracingConstants.LEG_E_STATION);
			} else {
				// make the previous terminating station into trasfer station
				int tempindex = index - 2;
				OHD_Itinerary tempi = getItinerary(tempindex);
				tempi.setLegto_type(TracingConstants.LEG_T_STATION);
				i.setLegfrom_type(TracingConstants.LEG_T_STATION);
				i.setLegto_type(TracingConstants.LEG_E_STATION);
			}
			this.itinerarylist.add(i);
		}
		return (OHD_Itinerary) this.itinerarylist.get(index);
	}

	/**
	 * 
	 * @return
	 */
	public String getDispBagArriveDate() {
		if (this.dispBagArriveDate == null || this.dispBagArriveDate.equals("")) {
			if (this.getBagarrivedate() != null) this.dispBagArriveDate = DateUtils.formatDate(this
					.getBagarrivedate(), _DATEFORMAT, null, null);
		}
		return this.dispBagArriveDate;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDispWarehouseReceivedDate() {
		if (this.dispWarehouseReceivedDate == null || this.dispWarehouseReceivedDate.equals("")) {
			if (this.getWarehouseReceivedDate() != null) this.dispWarehouseReceivedDate = DateUtils.formatDate(this
					.getWarehouseReceivedDate(), _DATEFORMAT, null, null);
		}
		return this.dispWarehouseReceivedDate; 
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDispWarehouseSentDate() {
		if (this.dispWarehouseSentDate == null || this.dispWarehouseSentDate.equals("")) {
			if (this.getWarehouseSentDate() != null) this.dispWarehouseSentDate = DateUtils.formatDate(this
					.getWarehouseSentDate(), _DATEFORMAT, null, null);
		}
		return this.dispWarehouseSentDate;
	}

	/**
	 * @return
	 */
	public String getDispFoundTime() {
		return DateUtils.formatDate(this.getFoundDate(), _DATEFORMAT + " " + _TIMEFORMAT, null,	_TIMEZONE);
	}

	/**
	 * 
	 * @return
	 */
	public String getDispCloseDate() {
		return DateUtils.formatDate(this.getClose_date(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	/**
	 * @return Returns the agent.
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
	 * @param membershipnum
	 *          The membershipnum to set.
	 */
	public void setMembershipnum(String membershipnum) {
		this.membershipnum = membershipnum;
	}

	/**
	 * @return Returns the membershipnum.
	 */
	public String getMembershipnum() {
		return membershipnum;
	}

	/**
	 * @return Returns the foundDate.
	 */
	public Date getFoundDate() {
		return foundDate;
	}

	/**
	 * @param foundDate
	 *          The foundDate to set.
	 */
	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
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
	 * @return Returns the storage_location.
	 */
	public String getStorage_location() {
		return storage_location;
	}

	/**
	 * @param storage_location
	 *          The storage_location to set.
	 */
	public void setStorage_location(String storage_location) {
		this.storage_location = storage_location;
	}

	/**
	 * @return Returns the warehouseReceivedDate.
	 */
	public Date getWarehouseReceivedDate() {
		return warehouseReceivedDate;
	}

	/**
	 * @param bagarrivedate
	 *          The warehouseReceivedDate to set.
	 */
	public void setWarehouseReceivedDate(Date warehouseReceivedDate) {
		this.warehouseReceivedDate = warehouseReceivedDate;
	}
	
	/**
	 * @return Returns the warehouseSentDate.
	 */
	public Date getWarehouseSentDate() {
		return warehouseSentDate;
	}

	/**
	 * @param bagarrivedate
	 *          The warehouseSentDate to set.
	 */
	public void setWarehouseSentDate(Date warehouseSentDate) {
		this.warehouseSentDate = warehouseSentDate;
	}
	
	/**
	 * @return Returns the bagarrivedate.
	 */
	public Date getBagarrivedate() {
		return bagarrivedate;
	}

	/**
	 * @param bagarrivedate
	 *          The bagarrivedate to set.
	 */
	public void setBagarrivedate(Date bagarrivedate) {
		this.bagarrivedate = bagarrivedate;
	}

	/**
	 * @param dispBagArriveDate
	 *          The dispBagArriveDate to set.
	 */
	public void setDispBagArriveDate(String dispBagArriveDate) {
		this.dispBagArriveDate = dispBagArriveDate;
	}
	
	/**
	 * @param dispBagArriveDate
	 *          The dispBagArriveDate to set.
	 */
	public void setDispWarehouseReceivedDate(String dispWarehouseReceivedDate) {
		this.dispWarehouseReceivedDate = dispWarehouseReceivedDate;
	}
	
	/**
	 * @param dispBagArriveDate
	 *          The dispBagArriveDate to set.
	 */
	public void setDispWarehouseSentDate(String dispWarehouseSentDate) {
		this.dispWarehouseSentDate = dispWarehouseSentDate;
	}

	public String getForwarded_station() {
		return forwarded_station;
	}

	public void setForwarded_station(String forwarded_station) {
		this.forwarded_station = forwarded_station;
	}

	public String getForwarded_agent() {
		return forwarded_agent;
	}

	public void setForwarded_agent(String forwarded_agent) {
		this.forwarded_agent = forwarded_agent;
	}

	public Date getForwarded_date() {
		return forwarded_date;
	}

	public void setForwarded_date(Date forwarded_date) {
		this.forwarded_date = forwarded_date;
	}
	
	public String getDispForwarded_date() {
		return DateUtils.formatDate(this.getForwarded_date(), _DATEFORMAT + " " + _TIMEFORMAT, null,
				_TIMEZONE);
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

	/**
	 * @return the recordlocator
	 */
	public String getRecordlocator() {
		return recordlocator;
	}

	/**
	 * @param recordlocator the recordlocator to set
	 */
	public void setRecordlocator(String recordlocator) {
		this.recordlocator = recordlocator;
	}

	public Boolean getEarlyBag() {
		return earlyBag;
	}

	public void setEarlyBag(Boolean earlyBag) {
		this.earlyBag = earlyBag;
	}

	public String getMatched_incident() {
		return matched_incident;
	}

	public void setMatched_incident(String matched_incident) {
		this.matched_incident = matched_incident;
	}

	public int getFaultstation_ID() {
		return faultStation;
	}

	public void setFaultstation_ID(int faultStation) {
		this.faultStation = faultStation;
	}

	public int getLoss_code() {
		return lossCode;
	}

	public void setLoss_code(int lossCode) {
		this.lossCode = lossCode;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedAgent() {
		return modifiedAgent;
	}
	public void setModifiedAgent(String modifiedAgent) {
		this.modifiedAgent = modifiedAgent;
	}
	
	public String getDispModifiedDate() {
		return DateUtils.formatDate(this.getModifiedDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}

	public Date getInventoryDate() {
		return inventoryDate;
	}
	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}
	public String getDispInventoryDate() {
		return DateUtils.formatDate(this.getInventoryDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
	}
	
	public String getPosId() {
		return posId;
	}
	public void setPosId(String posId) {
		this.posId = posId;
	}
	public boolean getLateCheck() {
		return lateCheck;
	}
	public void setLateCheck(boolean lateCheck) {
		this.lateCheck = lateCheck;
	}
	
	public String getLateCheckValue() {
		return lateCheck ? "on" : "off";
	}

	// NOOP to keep struts happy
	public void setLateCheckValue(String lateCheckValue) { }
	
	public List getExistMatchedItemlist() {
		return existMatchedItemlist;
	}
	public void setExistMatchedItemlist(List existMatchedItemlist) {
		this.existMatchedItemlist = existMatchedItemlist;
	}
	public int getExistMatchedItemType() {
		return existMatchedItemType;
	}
	public void setExistMatchedItemType(int existMatchedItemType) {
		this.existMatchedItemType = existMatchedItemType;
	}
	public Status getExistDisposalStatus() {
		return existDisposalStatus;
	}
	public void setExistDisposalStatus(Status existDisposalStatus) {
		this.existDisposalStatus = existDisposalStatus;
	}
	public int getExistRemarkSize() {
		return existRemarkSize;
	}
	public void setExistRemarkSize(int existRemarkSize) {
		this.existRemarkSize = existRemarkSize;
	}
}