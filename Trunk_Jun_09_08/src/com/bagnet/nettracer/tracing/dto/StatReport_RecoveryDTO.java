/*
 * Created on Sep 1, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.dto;

import java.util.TimeZone;
import java.text.DecimalFormat;

/**
 * @author Administrator
 *
 * create date - Sep 1, 2004
 */
/**
 * top flight temporary class
 * 
 * @author Administrator
 * 
 * create date - Sep 1, 2004
 */
public class StatReport_RecoveryDTO {
	private String stationcode;
	private int reportstaken;
	private int reportsclosed;
	private double recoveryratio;
	private String createdate;
	private String closedate;

	private String faultstationcode = "";
	private int loss_code;

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;
	
	public String getDisplayRecoveryratio() {
		String result = "";
		DecimalFormat df = new DecimalFormat("0.00");
		result += df.format(recoveryratio) + " %";
		return result;
	}	
	
	/**
	 * @return Returns the recoveryratio.
	 */
	public double getRecoveryratio() {
		return recoveryratio;
	}

	/**
	 * @param recoveryratio
	 *          The recoveryratio to set.
	 */
	public void setRecoveryratio(double recoveryratio) {
		this.recoveryratio = recoveryratio;
	}

	/**
	 * @return Returns the reportsclosed.
	 */
	public int getReportsclosed() {
		return reportsclosed;
	}

	/**
	 * @param reportclosed
	 *          The reportsclosed to set.
	 */
	public void setReportsclosed(int reportsclosed) {
		this.reportsclosed = reportsclosed;
	}

	/**
	 * @return Returns the reporttaken.
	 */
	public int getReportstaken() {
		return reportstaken;
	}

	/**
	 * @param reporttaken
	 *          The reporttaken to set.
	 */
	public void setReportstaken(int reportstaken) {
		this.reportstaken = reportstaken;
	}

	/**
	 * @return Returns the stationcode.
	 */
	public String getStationcode() {
		return stationcode;
	}

	/**
	 * @param stationcode
	 *          The stationcode to set.
	 */
	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}

	/**
	 * @return Returns the closedate.
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
	 * @return Returns the createdate.
	 */
	public String getCreatedate() {
		return createdate;
	}

	/**
	 * @param createdate
	 *          The createdate to set.
	 */
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	/**
	 * @return Returns the faultstationcode.
	 */
	public String getFaultstationcode() {
		return faultstationcode;
	}

	/**
	 * @param faultstationcode
	 *          The faultstationcode to set.
	 */
	public void setFaultstationcode(String faultstationcode) {
		this.faultstationcode = faultstationcode;
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
}