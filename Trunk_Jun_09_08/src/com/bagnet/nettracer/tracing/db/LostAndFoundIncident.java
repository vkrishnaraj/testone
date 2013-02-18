/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="lost_found"
 */
public class LostAndFoundIncident implements Serializable {

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
	private Date dateFoundLost;
	private String location;
	private String item_description;
	private Status disposal_status;
	private Status report_status;
	private Date close_date;
	private Agent closing_agent;
	private Station create_station;
	private int report_type;
	private int category_id;
	private int readonly;
	private String remark;
	private Set photos;

	private String languageKey;
	private String languageFreeFlow;
	
	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE; // timezone

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

	public String getDispCreateTime() {
		if (this.create_date != null && !this.create_date.equals("")) {
			return DateUtils.formatDate(this.getCreate_date(), _DATEFORMAT + " " + _TIMEFORMAT, null,
					_TIMEZONE);
		}
		return "";
	}

	/**
	 * @hibernate.property type="timestamp"
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="closing_agent_id"
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
	 * @hibernate.property type="timestamp"
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
	 * @hibernate.property type="string"
	 * @return Returns the customer_address1.
	 */
	public String getCustomer_address1() {
		return customer_address1;
	}

	/**
	 * @hibernate.property type="timestamp"
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
	 * @hibernate.property type="string"
	 * @return Returns the customer_countrycode_ID.
	 */
	public String getCustomer_countrycode_ID() {
		return (customer_countrycode_ID == null || customer_countrycode_ID.length() ==  0 ? PropertyBMO.getValue(PropertyBMO.PROPERTY_DEFAULT_COUNTRY) : customer_countrycode_ID);
	}

	/**
	 * @param customer_countrycode_ID
	 *          The customer_countrycode_ID to set.
	 */
	public void setCustomer_countrycode_ID(String customer_countrycode_ID) {
		this.customer_countrycode_ID = customer_countrycode_ID;
	}

	/**
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @param customer_address1
	 *          The customer_address1 to set.
	 */
	public void setCustomer_address1(String customer_address1) {
		this.customer_address1 = customer_address1;
	}

	/**
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="create_station_id"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="disposal_status_id"
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
	 * @hibernate.id generator-class="assigned" type="string"
	 *               column="file_ref_number"
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="filing_agent_id"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.set cascade="all" inverse="true" order-by="Photo_ID"
	 * @hibernate.key column="file_ref_number"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.LostAndFound_Photo"
	 * @return Returns the photos.
	 */
	public Set getPhotos() {
		return photos;
	}

	/**
	 * @param photos
	 *          The photos to set.
	 */
	public void setPhotos(Set photos) {
		this.photos = photos;
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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Status"
	 *                        column="report_status_id"
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
	 * @hibernate.property type="integer"
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
	
	/**
	 * @hibernate.property type="integer"
	 * @return Returns the category_id.
	 */
	public int getCategory_id() {
		return category_id;
	}

	/**
	 * @param category_id
	 *          The report_type to set.
	 */
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getState() {
		if (customer_state_ID != null && customer_state_ID.length() > 0) {
			return TracerUtils.getState(customer_state_ID).getState();
		}
		return "";
	}

	public String getCountry() {
		return TracerUtils.getCountry(getCustomer_countrycode_ID()).getCountry();
	}

	public String format(String val) {
		if (val == null) return " ";
		else return val + " ";
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the languageKey.
	 */
	public String getLanguageKey() {
		return languageKey;
	}

	public void setLanguageKey(String languageKey) {
		this.languageKey = languageKey;
	}

	/**
	 * @hibernate.property type="string"
	 * @return Returns the languageFreeFlow.
	 */
	public String getLanguageFreeFlow() {
		return languageFreeFlow;
	}

	public void setLanguageFreeFlow(String languageFreeFlow) {
		this.languageFreeFlow = languageFreeFlow;
	}

}