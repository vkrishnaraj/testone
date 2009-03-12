package com.bagnet.nettracer.tracing.forms;

import java.util.TimeZone;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

public class SearchExpenseForm extends ValidatorForm {

	private String dateFormat = TracingConstants.DB_DATEFORMAT;
	private String timeZone = TimeZone.getDefault().getID();
	
	private String createStartDate;
	private String createEndDate;
	private String approveStartDate;
	private String approveEndDate;
	private String incident_id;
	private int stationCode;
	private String createAgent;
	private int statusId;
	private int expenseTypeId;
	private double minimumAmount;
	private double maximumAmount;
	private int rowsPerPage = TracingConstants.ROWS_PER_PAGE;
	private int currentPage = 0;
	
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getCreateStartDate() {
		return createStartDate;
	}
	public void setCreateStartDate(String createStartDate) {
		this.createStartDate = createStartDate;
	}
	public String getCreateEndDate() {
		return createEndDate;
	}
	public void setCreateEndDate(String createEndDate) {
		this.createEndDate = createEndDate;
	}
	public String getApproveStartDate() {
		return approveStartDate;
	}
	public void setApproveStartDate(String approveStartDate) {
		this.approveStartDate = approveStartDate;
	}
	public String getApproveEndDate() {
		return approveEndDate;
	}
	public void setApproveEndDate(String approveEndDate) {
		this.approveEndDate = approveEndDate;
	}
	public String getIncident_id() {
		return incident_id;
	}
	public void setIncident_id(String incident_id) {
		this.incident_id = incident_id;
	}
	public String getCreateAgent() {
		return createAgent;
	}
	public void setCreateAgent(String createAgent) {
		this.createAgent = createAgent;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public int getExpenseTypeId() {
		return expenseTypeId;
	}
	public void setExpenseTypeId(int expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}
	public double getMinimumAmount() {
		return minimumAmount;
	}
	public void setMinimumAmount(double minimumAmount) {
		this.minimumAmount = minimumAmount;
	}
	public double getMaximumAmount() {
		return maximumAmount;
	}
	public void setMaximumAmount(double maximumAmount) {
		this.maximumAmount = maximumAmount;
	}
	public int getStationCode() {
		return stationCode;
	}
	public void setStationCode(int stationCode) {
		this.stationCode = stationCode;
	}
	
}
