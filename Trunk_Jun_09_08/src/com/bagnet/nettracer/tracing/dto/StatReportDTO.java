/*
 * Created on Aug 24, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.dto;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class StatReportDTO {

	private String stationCode;

	private int reportnum;
	private int customreportnum;
	private int itemType_ID;

	private String starttime;
	private String endtime;
	private String cstarttime;
	private String cendtime;
	private String[] station_ID;
	private int status_ID;
	private Integer[] status_id_combo;
	private Integer[] disposition_id_combo;
	
	private int dispositionId;
	private int type;
	private String dateFormat;
	private String event;
	private String companyCode;
	private String subcompCode;
		
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	public String getSubcompCode() {
		return subcompCode;
	}

	public void setSubcompCode(String subcompCode) {
		this.subcompCode = subcompCode;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public int getDispositionId() {
		return dispositionId;
	}

	public void setDispositionId(int dispositionId) {
		this.dispositionId = dispositionId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer[] getStatus_id_combo() {
		return status_id_combo;
	}

	public void setStatus_id_combo(Integer[] status_id_combo) {
		this.status_id_combo = status_id_combo;
	}
	
	public Integer[] getDisposition_id_combo() {
		return disposition_id_combo;
	}

	public void setDisposition_id_combo(Integer[] disposition_id_combo) {
		this.disposition_id_combo = disposition_id_combo;
	}

	private int boarded;
	private int perpassengers;

	private int numtop;

	private int expensetype_ID;
	private Integer[] expensetype_id_combo;

	public Integer[] getExpensetype_id_combo() {
		return expensetype_id_combo;
	}

	public void setExpensetype_id_combo(Integer[] expensetype_id_combo) {
		this.expensetype_id_combo = expensetype_id_combo;
	}

	private String b_stationcode;
	private String e_stationcode;
	private String t_stationcode;
	private String departdate;
	private String arrivaldate;

	private String[] faultstation_ID;
	private String[] previousFaultStation_ID;
	
	public String[] getPreviousFaultStation_ID() {
		return previousFaultStation_ID;
	}

	public void setPreviousFaultStation_ID(String[] previousFaultStation_ID) {
		this.previousFaultStation_ID = previousFaultStation_ID;
	}

	private int loss_code;
	private Integer[] loss_code_combo;
	private String primary_sort_order;
	private String secondary_sort_order;

	private boolean showAll;
	private boolean showAssignCity;
	private boolean showType;
	private boolean showLastName;
	private boolean showFirstName;
	private boolean showReportID;
	private boolean showDate;
	private boolean showTime;
	private boolean showItinerary;
	private boolean showDestination;
	private boolean showStatus;
	private boolean showFaultCity;
	private boolean showLossCode;
	

	public String getSecondary_sort_order() {
		return secondary_sort_order;
	}

	public void setSecondary_sort_order(String secondary_sort_order) {
		this.secondary_sort_order = secondary_sort_order;
	}
	
	public String getPrimary_sort_order() {
		return primary_sort_order;
	}

	public void setPrimary_sort_order(String primary_sort_order) {
		this.primary_sort_order = primary_sort_order;
	}

	public Integer[] getLoss_code_combo() {
		return loss_code_combo;
	}

	public void setLoss_code_combo(Integer[] loss_code_combo) {
		this.loss_code_combo = loss_code_combo;
	}

	private String[] holdingstation_ID;

	private int groupby;
	private int sumordet;

	private int outputtype = -1;

	private String agent;
	private String lz_id;
	
	private String[] company_ID;
	

	public String[] getCompany_ID() {
		return company_ID;
	}

	public void setCompany_ID(String[] company_ID) {
		this.company_ID = company_ID;
	}
	
	private String paxflightstarttime;
	private String paxflightendtime;
	private String[] createstation_ID;

	public String[] getCreatestation_ID() {
		return createstation_ID;
	}

	public void setCreatestation_ID(String[] createstation_ID) {
		this.createstation_ID = createstation_ID;
	}

	public String getPaxflightstarttime() {
		return paxflightstarttime;
	}

	public void setPaxflightstarttime(String paxflightstarttime) {
		this.paxflightstarttime = paxflightstarttime;
	}

	public String getPaxflightendtime() {
		return paxflightendtime;
	}

	public void setPaxflightendtime(String paxflightendtime) {
		this.paxflightendtime = paxflightendtime;
	}

	/**
	 * @return Returns the itemType_ID.
	 */
	public int getItemType_ID() {
		return itemType_ID;
	}

	/**
	 * @param itemType_ID
	 *          The itemType_ID to set.
	 */
	public void setItemType_ID(int itemType_ID) {
		this.itemType_ID = itemType_ID;
	}

	/**
	 * @return Returns the endtime.
	 */
	public String getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime
	 *          The endtime to set.
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	/**
	 * @return Returns the cendtime.
	 */
	public String getCendtime() {
		return cendtime;
	}

	/**
	 * @param cendtime
	 *          The cendtime to set.
	 */
	public void setCendtime(String cendtime) {
		this.cendtime = cendtime;
	}

	/**
	 * @return Returns the cstarttime.
	 */
	public String getCstarttime() {
		return cstarttime;
	}

	/**
	 * @param cstarttime
	 *          The cstarttime to set.
	 */
	public void setCstarttime(String cstarttime) {
		this.cstarttime = cstarttime;
	}

	/**
	 * @return Returns the reportnum.
	 */
	public int getReportnum() {
		return reportnum;
	}

	/**
	 * @param reportnum
	 *          The reportnum to set.
	 */
	public void setReportnum(int reportnum) {
		this.reportnum = reportnum;
	}

	
	/**
	 * @return Returns the customreportnum.
	 */
	public int getCustomreportnum() {
		return customreportnum;
	}
	/**
	 * @param customreportnum The customreportnum to set.
	 */
	public void setCustomreportnum(int customreportnum) {
		this.customreportnum = customreportnum;
	}
	/**
	 * @return Returns the starttime.
	 */
	public String getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime
	 *          The starttime to set.
	 */
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	/**
	 * @return Returns the station_ID.
	 */
	public String[] getStation_ID() {
		if (station_ID != null && station_ID.length <= 0) return null;
		return station_ID;
	}

	/**
	 * @param station_ID
	 *          The station_ID to set.
	 */
	public void setStation_ID(String[] station_ID) {
		this.station_ID = station_ID;
	}

	/**
	 * @return Returns the faultstation_ID.
	 */
	public String[] getFaultstation_ID() {
		if (faultstation_ID != null && faultstation_ID.length <= 0) return null;
		return faultstation_ID;
	}

	/**
	 * @param faultstation_ID
	 *          The faultstation_ID to set.
	 */
	public void setFaultstation_ID(String[] faultstation_ID) {
		this.faultstation_ID = faultstation_ID;
	}

	
	/**
	 * @return the holdingstation_ID
	 */
	public String[] getHoldingstation_ID() {
		if (holdingstation_ID != null && holdingstation_ID.length <= 0) return null;
		return holdingstation_ID;
	}

	/**
	 * @param holdingstation_ID the holdingstation_ID to set
	 */
	public void setHoldingstation_ID(String[] holdingstation_ID) {
		this.holdingstation_ID = holdingstation_ID;
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
	 * @return Returns the status_ID.
	 */
	public int getStatus_ID() {
		return status_ID;
	}

	/**
	 * @param status_ID
	 *          The status_ID to set.
	 */
	public void setStatus_ID(int status_ID) {
		this.status_ID = status_ID;
	}

	/**
	 * @return Returns the boarded.
	 */
	public int getBoarded() {
		return boarded;
	}

	/**
	 * @param dailyboarding
	 *          The boarded to set.
	 */
	public void setBoarded(int boarded) {
		this.boarded = boarded;
	}

	/**
	 * @return Returns the perpassengers.
	 */
	public int getPerpassengers() {
		return perpassengers;
	}

	/**
	 * @param perpassengers
	 *          The perpassengers to set.
	 */
	public void setPerpassengers(int perpassengers) {
		this.perpassengers = perpassengers;
	}

	/**
	 * @return Returns the numtop.
	 */
	public int getNumtop() {
		return numtop;
	}

	/**
	 * @param numtop
	 *          The numtop to set.
	 */
	public void setNumtop(int numtop) {
		this.numtop = numtop;
	}

	/**
	 * @return Returns the expensetype_ID.
	 */
	public int getExpensetype_ID() {
		return expensetype_ID;
	}

	/**
	 * @param expensetype_ID
	 *          The expensetype_ID to set.
	 */
	public void setExpensetype_ID(int expensetype_ID) {
		this.expensetype_ID = expensetype_ID;
	}

	/**
	 * @return Returns the b_stationcode.
	 */
	public String getB_stationcode() {
		return b_stationcode;
	}

	/**
	 * @param b_stationcode
	 *          The b_stationcode to set.
	 */
	public void setB_stationcode(String b_stationcode) {
		this.b_stationcode = b_stationcode;
	}

	/**
	 * @return Returns the e_stationcode.
	 */
	public String getE_stationcode() {
		return e_stationcode;
	}

	/**
	 * @param e_stationcode
	 *          The e_stationcode to set.
	 */
	public void setE_stationcode(String e_stationcode) {
		this.e_stationcode = e_stationcode;
	}

	/**
	 * @return Returns the t_stationcode.
	 */
	public String getT_stationcode() {
		return t_stationcode;
	}

	/**
	 * @param t_stationcode
	 *          The t_stationcode to set.
	 */
	public void setT_stationcode(String t_stationcode) {
		this.t_stationcode = t_stationcode;
	}

	/**
	 * @return Returns the arrivaldate.
	 */
	public String getArrivaldate() {
		return arrivaldate;
	}

	/**
	 * @param arrivaldate
	 *          The arrivaldate to set.
	 */
	public void setArrivaldate(String arrivaldate) {
		this.arrivaldate = arrivaldate;
	}

	/**
	 * @return Returns the departdate.
	 */
	public String getDepartdate() {
		return departdate;
	}

	/**
	 * @param departdate
	 *          The departdate to set.
	 */
	public void setDepartdate(String departdate) {
		this.departdate = departdate;
	}

	/**
	 * @return Returns the sumordet.
	 */
	public int getSumordet() {
		return sumordet;
	}

	/**
	 * @param sumordet
	 *          The sumordet to set.
	 */
	public void setSumordet(int sumordet) {
		this.sumordet = sumordet;
	}

	/**
	 * @return Returns the outputtype.
	 */
	public int getOutputtype() {
		return outputtype;
	}

	/**
	 * @param outputtype
	 *          The outputtype to set.
	 */
	public void setOutputtype(int outputtype) {
		this.outputtype = outputtype;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @return the lz_id
	 */
	public String getLz_id() {
		return lz_id;
	}

	/**
	 * @param lz_id the lz_id to set
	 */
	public void setLz_id(String lz_id) {
		this.lz_id = lz_id;
	}

	public int getGroupby() {
		return groupby;
	}

	public void setGroupby(int groupby) {
		this.groupby = groupby;
	}
	
	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	
	private long salvageId;

	public long getSalvageId() {
		return salvageId;
	}

	public void setSalvageId(long salvageId) {
		this.salvageId = salvageId;
	}

	public boolean isShowAssignCity() {
		return showAssignCity;
	}

	public void setShowAssignCity(boolean showAssignCity) {
		this.showAssignCity = showAssignCity;
	}

	public boolean isShowType() {
		return showType;
	}

	public void setShowType(boolean showType) {
		this.showType = showType;
	}

	public boolean isShowLastName() {
		return showLastName;
	}

	public void setShowLastName(boolean showLastName) {
		this.showLastName = showLastName;
	}

	public boolean isShowFirstName() {
		return showFirstName;
	}

	public void setShowFirstName(boolean showFirstName) {
		this.showFirstName = showFirstName;
	}

	public boolean isShowReportID() {
		return showReportID;
	}

	public void setShowReportID(boolean showReportID) {
		this.showReportID = showReportID;
	}

	public boolean isShowDate() {
		return showDate;
	}

	public void setShowDate(boolean showDate) {
		this.showDate = showDate;
	}

	public boolean isShowTime() {
		return showTime;
	}

	public void setShowTime(boolean showTime) {
		this.showTime = showTime;
	}

	public boolean isShowItinerary() {
		return showItinerary;
	}

	public void setShowItinerary(boolean showItinerary) {
		this.showItinerary = showItinerary;
	}

	public boolean isShowDestination() {
		return showDestination;
	}

	public void setShowDestination(boolean showDestination) {
		this.showDestination = showDestination;
	}

	public boolean isShowLossCode() {
		return showLossCode;
	}

	public void setShowLossCode(boolean showLossCode) {
		this.showLossCode = showLossCode;
	}

	public boolean isShowFaultCity() {
		return showFaultCity;
	}

	public void setShowFaultCity(boolean showFaultCity) {
		this.showFaultCity = showFaultCity;
	}

	public boolean isShowStatus() {
		return showStatus;
	}

	public void setShowStatus(boolean showStatus) {
		this.showStatus = showStatus;
	}

	public boolean isShowAll() {
		return showAll;
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	private long claimId;

	public long getClaimId() {
		return claimId;
	}

	public void setClaimId(long claimId) {
		this.claimId = claimId;
	}


	public int getNumChecked()
	{
		int checked=0;
		if(!this.showAll){
		if(this.showAssignCity) checked++;
		if(this.showType) checked++;
		if(this.showLastName) checked++;
		if(this.showFirstName) checked++;
		if(this.showReportID) checked++;
		if(this.showDate) checked++;
		if(this.showTime) checked++;
		if(this.showItinerary) checked++;
		if(this.showDestination) checked++;
		if(this.showStatus) checked++;
		if(this.showFaultCity) checked++;
		if(this.showLossCode) checked++;}
		else {
			checked=12;
		}
		return checked;
	}
}