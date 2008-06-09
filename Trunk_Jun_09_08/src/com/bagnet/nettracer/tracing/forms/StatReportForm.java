package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for viewing statistical reports.
 */
public final class StatReportForm extends ValidatorForm {

	private int itemType_ID;
	private int reportnum;
	private int customreportnum;
	private String starttime;
	private String endtime;
	private String cstarttime; // close date for recovery report , or draft paid date for expense report
	private String cendtime; // close date for recovery report , or draft paid date for expense report
	private String[] station_ID;
	
	private int status_ID;
	private int boarded;
	private int perpassengers;
	private int numtop;
	private int expensetype_ID;
	private String b_stationcode;
	private String e_stationcode;
	private String t_stationcode;
	private String departdate;
	private String arrivaldate;

	private String[] faultstation_ID;
	private int loss_code;
	
	private String[] holdingstation_ID;
	
	private int sumordet;

	private int outputtype = 0;

	private String agent;

	/**
	 * Initialize the form
	 */
	public void init() {
		itemType_ID = 0;
		starttime = "";
		endtime = "";
		cstarttime = "";
		cendtime = "";
		departdate = "";
		arrivaldate = "";
		station_ID = null;
		faultstation_ID = null;
		holdingstation_ID = null;
		loss_code = 0;
		status_ID = 0;
		boarded = 0;
		perpassengers = 1000;
		numtop = 5;
		expensetype_ID = 0;
		b_stationcode = "";
		e_stationcode = "";
		t_stationcode = "";
		sumordet = 1;
		agent = "";
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
	 * @return Returns the boarded.
	 */
	public int getBoarded() {
		return boarded;
	}

	/**
	 * @param boarded
	 *          The boarded to set.
	 */
	public void setBoarded(int boarded) {
		this.boarded = boarded;
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
	 * @return Returns the faultstation_ID.
	 */
	public String[] getFaultstation_ID() {
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
}