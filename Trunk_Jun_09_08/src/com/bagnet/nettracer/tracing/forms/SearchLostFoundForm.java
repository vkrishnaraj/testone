package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public final class SearchLostFoundForm extends ValidatorForm {

	private String file_ref_number = "";
	private String finding_agent_name = "";
	private String customer_name = "";
	private String customer_address = "";
	private String customer_tel = "";
	private String s_createtime = "";
	private String e_createtime = "";
	private String location;
	private int filing_station;
	private String item_description;
	private String disposal_status_ID;
	private String report_status_ID;
	private String report_type;

	/**
	 * @return Returns the customer_address.
	 */
	public String getCustomer_address() {
		return customer_address;
	}

	/**
	 * @param customer_address
	 *          The customer_address to set.
	 */
	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	/**
	 * @return Returns the customer_name.
	 */
	public String getCustomer_name() {
		return customer_name;
	}

	/**
	 * @param customer_name
	 *          The customer_name to set.
	 */
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	/**
	 * @return Returns the customer_tel.
	 */
	public String getCustomer_tel() {
		return customer_tel;
	}

	/**
	 * @param customer_tel
	 *          The customer_tel to set.
	 */
	public void setCustomer_tel(String customer_tel) {
		this.customer_tel = customer_tel;
	}

	/**
	 * @return Returns the disposal_status_ID.
	 */
	public String getDisposal_status_ID() {
		return disposal_status_ID;
	}

	/**
	 * @param disposal_status_ID
	 *          The disposal_status_ID to set.
	 */
	public void setDisposal_status_ID(String disposal_status_ID) {
		this.disposal_status_ID = disposal_status_ID;
	}

	/**
	 * @return Returns the e_createtime.
	 */
	public String getE_createtime() {
		return e_createtime;
	}

	/**
	 * @param e_createtime
	 *          The e_createtime to set.
	 */
	public void setE_createtime(String e_createtime) {
		this.e_createtime = e_createtime;
	}

	/**
	 * @return Returns the file_ref_number.
	 */
	public String getFile_ref_number() {
		return file_ref_number;
	}

	/**
	 * @param file_ref_number
	 *          The file_ref_number to set.
	 */
	public void setFile_ref_number(String file_ref_number) {
		this.file_ref_number = file_ref_number;
	}

	/**
	 * @return Returns the finding_agent_name.
	 */
	public String getFinding_agent_name() {
		return finding_agent_name;
	}

	/**
	 * @param finding_agent_name
	 *          The finding_agent_name to set.
	 */
	public void setFinding_agent_name(String finding_agent_name) {
		this.finding_agent_name = finding_agent_name;
	}

	/**
	 * @return Returns the item_description.
	 */
	public String getItem_description() {
		return item_description;
	}

	/**
	 * @param item_description
	 *          The item_description to set.
	 */
	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}

	/**
	 * @return Returns the location.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *          The location to set.
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return Returns the filing_station.
	 */
	public int getFiling_station() {
		return filing_station;
	}
	/**
	 * @param filing_station The filing_station to set.
	 */
	public void setFiling_station(int filing_station) {
		this.filing_station = filing_station;
	}
	/**
	 * @return Returns the report_status_ID.
	 */
	public String getReport_status_ID() {
		return report_status_ID;
	}

	/**
	 * @param report_status_ID
	 *          The report_status_ID to set.
	 */
	public void setReport_status_ID(String report_status_ID) {
		this.report_status_ID = report_status_ID;
	}

	/**
	 * @return Returns the report_type.
	 */
	public String getReport_type() {
		return report_type;
	}

	/**
	 * @param report_type
	 *          The report_type to set.
	 */
	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	/**
	 * @return Returns the s_createtime.
	 */
	public String getS_createtime() {
		return s_createtime;
	}

	/**
	 * @param s_createtime
	 *          The s_createtime to set.
	 */
	public void setS_createtime(String s_createtime) {
		this.s_createtime = s_createtime;
	}
}