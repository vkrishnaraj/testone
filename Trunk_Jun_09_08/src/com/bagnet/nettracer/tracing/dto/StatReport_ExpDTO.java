/*
 * Created on Sep 1, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.dto;

import java.sql.Date;

import com.bagnet.nettracer.tracing.db.ExpenseType;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;

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
public class StatReport_ExpDTO {
	private double checkamt;
	private double voucheramt;
	private int mileageamt;
	private String currency_ID;
	private int expenseType_ID;
	private Station station;
	private Status status;
	private Station faultstation;
	private int loss_code;
	private String agent_username;
	private Date draftpaiddate;
	
	public String getAgent_username() {
		return agent_username;
	}

	public void setAgent_username(String agent_username) {
		this.agent_username = agent_username;
	}

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
	 * @param loss_code
	 *          The loss_code to set.
	 */
	public void setLoss_code(int loss_code) {
		this.loss_code = loss_code;
	}

	/**
	 * @return Returns the checkamt.
	 */
	public double getCheckamt() {
		return checkamt;
	}

	/**
	 * @param checkamt
	 *          The checkamt to set.
	 */
	public void setCheckamt(double checkamt) {
		this.checkamt = checkamt;
	}

	/**
	 * @return Returns the currency_ID.
	 */
	public String getCurrency_ID() {
		return currency_ID;
	}

	/**
	 * @param currency_ID
	 *          The currency_ID to set.
	 */
	public void setCurrency_ID(String currency_ID) {
		this.currency_ID = currency_ID;
	}

	/**
	 * @return Returns the mileageamt.
	 */
	public int getMileageamt() {
		return mileageamt;
	}

	/**
	 * @param mileageamt
	 *          The mileageamt to set.
	 */
	public void setMileageamt(int mileageamt) {
		this.mileageamt = mileageamt;
	}

	/**
	 * @return Returns the station.
	 */
	public Station getStation() {
		return station;
	}

	/**
	 * @param station
	 *          The station to set.
	 */
	public void setStation(Station station) {
		this.station = station;
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
	 * @return Returns the typedesc.
	 */
	public String getTypedesc() {
		return ExpenseType.MSG_KEY + expenseType_ID;
	}

	/**
	 * @return Returns the voucheramt.
	 */
	public double getVoucheramt() {
		return voucheramt;
	}

	/**
	 * @param voucheramt
	 *          The voucheramt to set.
	 */
	public void setVoucheramt(double voucheramt) {
		this.voucheramt = voucheramt;
	}

	/**
	 * @return Returns the stationcode.
	 */
	public String getStationcode() {
		return station.getStationcode();
	}

	/**
	 * @return Returns the expenselocation_ID.
	 */
	public int getExpenselocation_ID() {
		return station.getStation_ID();
	}

	public String getFaultstationcode() {
		return faultstation != null ? faultstation.getStationcode() : "";
	}

	public int getLoss_code() {
		return loss_code;
	}
	

	/**
	 * @return Returns the draftpaiddate.
	 */
	public Date getDraftpaiddate() {
		return draftpaiddate;
	}
	/**
	 * @param draftpaiddate The draftpaiddate to set.
	 */
	public void setDraftpaiddate(Date draftpaiddate) {
		this.draftpaiddate = draftpaiddate;
	}

	public int getExpenseType_ID() {
		return expenseType_ID;
	}

	public void setExpenseType_ID(int expenseType_ID) {
		this.expenseType_ID = expenseType_ID;
	}
}