package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Ankur Gupta
 * 
 * This class represents the form that is used for viewing, updating, and
 * inserting reports (Lost/Delayed, Damaged, On-hand)
 */
public final class LostFoundIncidentForm extends ValidatorForm {

	private String file_ref_number;
	private Agent filing_agent;
	private String finding_agent_name;
	private String customer_firstname;
	private String customer_mname;
	private String customer_lastname;
	private String customer_address1;
	private String customer_address2;
	private String customer_city;
	private String customer_state_ID;
	private String customer_countrycode_ID;
	private String customer_province;
	private String customer_zip;
	private String customer_email;
	private String customer_tel;
	private Date create_date;
	private String location;
	private String item_description;
	private Status disposal_status = new Status();
	private Station create_station;
	private Status report_status;
	private Date dateFoundLost;
	private String dispDateFoundLost = "";
	private List photoList = new ArrayList();
	
	private Date close_date;
	private Agent closing_agent;
	private String remark;
	private int report_type;
	private int readonly;

	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE; // timezone

	public String getState() {
		if (customer_state_ID != null && customer_state_ID.length() > 0) {
			return TracerUtils.getState(customer_state_ID, "en").getState();
		}
		return "";
	}

	public String getCountry() {
		if (customer_countrycode_ID != null && customer_countrycode_ID.length() > 0) {
			return TracerUtils.getCountry(customer_countrycode_ID, "en").getCountry();
		}
		return "";
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *          The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getDispCreateTime() {
		if (this.create_date != null && !this.create_date.equals("")) {
			return DateUtils.formatDate(this.getCreate_date(), _DATEFORMAT + " " + _TIMEFORMAT, null,
					_TIMEZONE);
		}
		return "";
	}

	public String getDispCloseDateTime() {
		if (this.close_date != null && !this.close_date.equals("")) {
			return DateUtils.formatDate(this.getClose_date(), _DATEFORMAT + " " + _TIMEFORMAT, null,
					_TIMEZONE);
		}
		return "";
	}

	/**
	 * @return Returns the closing_agent.
	 */
	public Agent getClosing_agent() {
		return closing_agent;
	}

	/**
	 * @param closing_agent
	 *          The closing_agent to set.
	 */
	public void setClosing_agent(Agent closing_agent) {
		this.closing_agent = closing_agent;
	}

	/**
	 * @return Returns the create_date.
	 */
	public Date getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date
	 *          The create_date to set.
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	/**
	 * @return Returns the dateFoundLost.
	 */
	public Date getDateFoundLost() {
		return dateFoundLost;
	}

	/**
	 * @param dateFoundLost
	 *          The dateFoundLost to set.
	 */
	public void setDateFoundLost(Date dateFoundLost) {
		this.dateFoundLost = dateFoundLost;
	}

	/**
	 * @param dispDateFoundLost
	 *          The dispDateFoundLost to set.
	 */
	public void setDispDateFoundLost(String dispDateFoundLost) {
		this.dispDateFoundLost = dispDateFoundLost;
	}

	/**
	 * @return Returns the dispDateFoundLost.
	 */
	public String getDispDateFoundLost() {

		if (this.dispDateFoundLost == null || this.dispDateFoundLost.equals("")) {
			if (this.getDateFoundLost() != null) this.dispDateFoundLost = DateUtils.formatDate(this
					.getDateFoundLost(), _DATEFORMAT, null, _TIMEZONE);
		}
		return this.dispDateFoundLost;
	}

	/**
	 * @return Returns the customer_countrycode_ID.
	 */
	public String getCustomer_countrycode_ID() {
		return customer_countrycode_ID;
	}

	/**
	 * @param customer_countrycode_ID
	 *          The customer_countrycode_ID to set.
	 */
	public void setCustomer_countrycode_ID(String customer_countrycode_ID) {
		this.customer_countrycode_ID = customer_countrycode_ID;
	}

	/**
	 * @return Returns the customer_address1.
	 */
	public String getCustomer_address1() {
		return customer_address1;
	}

	/**
	 * @param customer_address1
	 *          The customer_address1 to set.
	 */
	public void setCustomer_address1(String customer_address1) {
		this.customer_address1 = customer_address1;
	}

	/**
	 * @return Returns the customer_address2.
	 */
	public String getCustomer_address2() {
		return customer_address2;
	}

	/**
	 * @param customer_address2
	 *          The customer_address2 to set.
	 */
	public void setCustomer_address2(String customer_address2) {
		this.customer_address2 = customer_address2;
	}

	/**
	 * @return Returns the customer_city.
	 */
	public String getCustomer_city() {
		return customer_city;
	}

	/**
	 * @param customer_city
	 *          The customer_city to set.
	 */
	public void setCustomer_city(String customer_city) {
		this.customer_city = customer_city;
	}

	/**
	 * @return Returns the customer_email.
	 */
	public String getCustomer_email() {
		return customer_email;
	}

	/**
	 * @param customer_email
	 *          The customer_email to set.
	 */
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	/**
	 * @return Returns the customer_province.
	 */
	public String getCustomer_province() {
		return customer_province;
	}

	/**
	 * @param customer_province
	 *          The customer_province to set.
	 */
	public void setCustomer_province(String customer_province) {
		this.customer_province = customer_province;
	}

	/**
	 * @return Returns the customer_state_ID.
	 */
	public String getCustomer_state_ID() {
		return customer_state_ID;
	}

	/**
	 * @param customer_state_ID
	 *          The customer_state_ID to set.
	 */
	public void setCustomer_state_ID(String customer_state_ID) {
		this.customer_state_ID = customer_state_ID;
	}

	/**
	 * @return Returns the customer_zip.
	 */
	public String getCustomer_zip() {
		return customer_zip;
	}

	/**
	 * @param customer_zip
	 *          The customer_zip to set.
	 */
	public void setCustomer_zip(String customer_zip) {
		this.customer_zip = customer_zip;
	}

	/**
	 * @return Returns the customer_firstname.
	 */
	public String getCustomer_firstname() {
		return customer_firstname;
	}

	/**
	 * @param customer_firstname
	 *          The customer_firstname to set.
	 */
	public void setCustomer_firstname(String customer_firstname) {
		this.customer_firstname = customer_firstname;
	}

	/**
	 * @return Returns the customer_lastname.
	 */
	public String getCustomer_lastname() {
		return customer_lastname;
	}

	/**
	 * @param customer_lastname
	 *          The customer_lastname to set.
	 */
	public void setCustomer_lastname(String customer_lastname) {
		this.customer_lastname = customer_lastname;
	}

	/**
	 * @return Returns the customer_mname.
	 */
	public String getCustomer_mname() {
		return customer_mname;
	}

	/**
	 * @param customer_mname
	 *          The customer_mname to set.
	 */
	public void setCustomer_mname(String customer_mname) {
		this.customer_mname = customer_mname;
	}

	/**
	 * @return Returns the create_station.
	 */
	public Station getCreate_station() {
		return create_station;
	}

	/**
	 * @param create_station
	 *          The create_station to set.
	 */
	public void setCreate_station(Station create_station) {
		this.create_station = create_station;
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
	 * @return Returns the filing_agent.
	 */
	public Agent getFiling_agent() {
		return filing_agent;
	}

	/**
	 * @param filing_agent
	 *          The filing_agent to set.
	 */
	public void setFiling_agent(Agent filing_agent) {
		this.filing_agent = filing_agent;
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
	 * @return the photoList
	 */
	public List getPhotoList() {
		return photoList;
	}

	/**
	 * @param photoList the photoList to set
	 */
	public void setPhotoList(List photoList) {
		this.photoList = photoList;
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
	 * @return Returns the report_status.
	 */
	public Status getReport_status() {
		return report_status;
	}

	/**
	 * @param report_status
	 *          The report_status to set.
	 */
	public void setReport_status(Status report_status) {
		this.report_status = report_status;
	}

	/**
	 * @return Returns the report_type.
	 */
	public int getReport_type() {
		return report_type;
	}

	/**
	 * @param report_type
	 *          The report_type to set.
	 */
	public void setReport_type(int report_type) {
		this.report_type = report_type;
	}
}