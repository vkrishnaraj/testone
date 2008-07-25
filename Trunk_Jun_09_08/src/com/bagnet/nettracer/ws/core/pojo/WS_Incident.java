package com.bagnet.nettracer.ws.core.pojo;


public class WS_Incident {
	
	private String Incident_ID;
	private String companycode_ID;	// company that holds this claim
	private String stationcreated;
	private String stationassigned;
	private String faultairline;
	private String faultstation;
	
	private String agent;
	private String agentassigned;
	private String createdate;
	private String createtime;
	
	private String closedate;
	private String recordlocator;
	private String ticketnumber;
	private String reportmethod;
	private String checkedlocation;
	private int numpassengers;
	private int numbagchecked;
	private int numbagreceived;
	private int voluntaryseparation;
	private int courtesyreport;
	private int tsachecked;
	private int customcleared;
	private int nonrevenue;
	private String itemtype;
	private String status;
	private int loss_code;
	
	private WS_Passenger[] passengers = null;

	private WS_Item[] items = null;
	private WS_Article[] articles = null;
	//private Set remarks;
	private WS_Itinerary[] itineraries = null;
	
	//private Set claims;

	private WS_IncidentClaimCheck[] claimchecks = null;
	
	
	
	private String errorcode;



	/**
	 * @return the incident_ID
	 */
	public String getIncident_ID() {
		return Incident_ID;
	}



	/**
	 * @param incident_ID the incident_ID to set
	 */
	public void setIncident_ID(String incident_ID) {
		Incident_ID = incident_ID;
	}

	
	/**
	 * @return the companycode_ID
	 */
	public String getCompanycode_ID() {
		return companycode_ID;
	}



	/**
	 * @param companycode_ID the companycode_ID to set
	 */
	public void setCompanycode_ID(String companycode_ID) {
		this.companycode_ID = companycode_ID;
	}



	/**
	 * @return the stationcreated
	 */
	public String getStationcreated() {
		return stationcreated;
	}



	/**
	 * @param stationcreated the stationcreated to set
	 */
	public void setStationcreated(String stationcreated) {
		this.stationcreated = stationcreated;
	}



	/**
	 * @return the stationassigned
	 */
	public String getStationassigned() {
		return stationassigned;
	}



	/**
	 * @param stationassigned the stationassigned to set
	 */
	public void setStationassigned(String stationassigned) {
		this.stationassigned = stationassigned;
	}



	/**
	 * @return the faultairline
	 */
	public String getFaultairline() {
		return faultairline;
	}



	/**
	 * @param faultairline the faultairline to set
	 */
	public void setFaultairline(String faultairline) {
		this.faultairline = faultairline;
	}



	/**
	 * @return the faultstation
	 */
	public String getFaultstation() {
		return faultstation;
	}



	/**
	 * @param faultstation the faultstation to set
	 */
	public void setFaultstation(String faultstation) {
		this.faultstation = faultstation;
	}



	/**
	 * @return the agent
	 */
	public String getAgent() {
		return agent;
	}



	/**
	 * @param agent the agent to set
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}



	/**
	 * @return the agentassigned
	 */
	public String getAgentassigned() {
		return agentassigned;
	}



	/**
	 * @param agentassigned the agentassigned to set
	 */
	public void setAgentassigned(String agentassigned) {
		this.agentassigned = agentassigned;
	}



	/**
	 * @return the createdate
	 */
	public String getCreatedate() {
		return createdate;
	}



	/**
	 * @param createdate the createdate to set
	 */
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}



	/**
	 * @return the createtime
	 */
	public String getCreatetime() {
		return createtime;
	}



	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}



	/**
	 * @return the closedate
	 */
	public String getClosedate() {
		return closedate;
	}



	/**
	 * @param closedate the closedate to set
	 */
	public void setClosedate(String closedate) {
		this.closedate = closedate;
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


	/**
	 * @return the ticketnumber
	 */
	public String getTicketnumber() {
		return ticketnumber;
	}



	/**
	 * @param ticketnumber the ticketnumber to set
	 */
	public void setTicketnumber(String ticketnumber) {
		this.ticketnumber = ticketnumber;
	}



	/**
	 * @return the reportmethod
	 */
	public String getReportmethod() {
		return reportmethod;
	}



	/**
	 * @param reportmethod the reportmethod to set
	 */
	public void setReportmethod(String reportmethod) {
		this.reportmethod = reportmethod;
	}



	/**
	 * @return the checkedlocation
	 */
	public String getCheckedlocation() {
		return checkedlocation;
	}



	/**
	 * @param checkedlocation the checkedlocation to set
	 */
	public void setCheckedlocation(String checkedlocation) {
		this.checkedlocation = checkedlocation;
	}



	/**
	 * @return the numpassengers
	 */
	public int getNumpassengers() {
		return numpassengers;
	}



	/**
	 * @param numpassengers the numpassengers to set
	 */
	public void setNumpassengers(int numpassengers) {
		this.numpassengers = numpassengers;
	}



	/**
	 * @return the numbagchecked
	 */
	public int getNumbagchecked() {
		return numbagchecked;
	}



	/**
	 * @param numbagchecked the numbagchecked to set
	 */
	public void setNumbagchecked(int numbagchecked) {
		this.numbagchecked = numbagchecked;
	}



	/**
	 * @return the numbagreceived
	 */
	public int getNumbagreceived() {
		return numbagreceived;
	}



	/**
	 * @param numbagreceived the numbagreceived to set
	 */
	public void setNumbagreceived(int numbagreceived) {
		this.numbagreceived = numbagreceived;
	}



	/**
	 * @return the voluntaryseparation
	 */
	public int getVoluntaryseparation() {
		return voluntaryseparation;
	}



	/**
	 * @param voluntaryseparation the voluntaryseparation to set
	 */
	public void setVoluntaryseparation(int voluntaryseparation) {
		this.voluntaryseparation = voluntaryseparation;
	}



	/**
	 * @return the courtesyreport
	 */
	public int getCourtesyreport() {
		return courtesyreport;
	}



	/**
	 * @param courtesyreport the courtesyreport to set
	 */
	public void setCourtesyreport(int courtesyreport) {
		this.courtesyreport = courtesyreport;
	}



	/**
	 * @return the tsachecked
	 */
	public int getTsachecked() {
		return tsachecked;
	}



	/**
	 * @param tsachecked the tsachecked to set
	 */
	public void setTsachecked(int tsachecked) {
		this.tsachecked = tsachecked;
	}



	/**
	 * @return the customcleared
	 */
	public int getCustomcleared() {
		return customcleared;
	}



	/**
	 * @param customcleared the customcleared to set
	 */
	public void setCustomcleared(int customcleared) {
		this.customcleared = customcleared;
	}



	/**
	 * @return the nonrevenue
	 */
	public int getNonrevenue() {
		return nonrevenue;
	}



	/**
	 * @param nonrevenue the nonrevenue to set
	 */
	public void setNonrevenue(int nonrevenue) {
		this.nonrevenue = nonrevenue;
	}



	/**
	 * @return the itemtype
	 */
	public String getItemtype() {
		return itemtype;
	}



	/**
	 * @param itemtype the itemtype to set
	 */
	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}



	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}



	/**
	 * @return the loss_code
	 */
	public int getLoss_code() {
		return loss_code;
	}



	/**
	 * @param loss_code the loss_code to set
	 */
	public void setLoss_code(int loss_code) {
		this.loss_code = loss_code;
	}



	/**
	 * @return the passengers
	 */
	public WS_Passenger[] getPassengers() {
		return passengers;
	}



	/**
	 * @param passengers the passengers to set
	 */
	public void setPassengers(WS_Passenger[] passengers) {
		this.passengers = passengers;
	}



	/**
	 * @return the items
	 */
	public WS_Item[] getItems() {
		return items;
	}



	/**
	 * @param items the items to set
	 */
	public void setItems(WS_Item[] items) {
		this.items = items;
	}



	/**
	 * @return the articles
	 */
	public WS_Article[] getArticles() {
		return articles;
	}



	/**
	 * @param articles the articles to set
	 */
	public void setArticles(WS_Article[] articles) {
		this.articles = articles;
	}



	/**
	 * @return the itineraries
	 */
	public WS_Itinerary[] getItineraries() {
		return itineraries;
	}



	/**
	 * @param itineraries the itineraries to set
	 */
	public void setItineraries(WS_Itinerary[] itineraries) {
		this.itineraries = itineraries;
	}



	/**
	 * @return the claimchecks
	 */
	public WS_IncidentClaimCheck[] getClaimchecks() {
		return claimchecks;
	}



	/**
	 * @param claimchecks the claimchecks to set
	 */
	public void setClaimchecks(WS_IncidentClaimCheck[] claimchecks) {
		this.claimchecks = claimchecks;
	}



	/**
	 * @return the errorcode
	 */
	public String getErrorcode() {
		return errorcode;
	}



	/**
	 * @param errorcode the errorcode to set
	 */
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	
	
	
}
