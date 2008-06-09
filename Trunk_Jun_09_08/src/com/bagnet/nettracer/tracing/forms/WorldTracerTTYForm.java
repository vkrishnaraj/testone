package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.WT_FWD_Log_Itinerary;

public class WorldTracerTTYForm extends ValidatorForm {

	private int tty_id;
	private String origin_address;
	private String airline_code;
	private String file_type1;
	private String file_type2;
	private String file_type3;
	private String file_type4;
	private String file_reference1;
	private String file_reference2;
	private String file_reference3;
	private String file_reference4;
	private String teletype_address1;
	private String teletype_address2;
	private String teletype_address3;
	private String teletype_address4;
	private String text;
	private int tty_status;


	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;
	/**
	 * @return the tty_id
	 */
	public int getTty_id() {
		return tty_id;
	}
	/**
	 * @param tty_id the tty_id to set
	 */
	public void setTty_id(int tty_id) {
		this.tty_id = tty_id;
	}

	/**
	 * @return the origin_address
	 */
	public String getOrigin_address() {
		return origin_address;
	}
	/**
	 * @param origin_address the origin_address to set
	 */
	public void setOrigin_address(String origin_address) {
		this.origin_address = origin_address;
	}
	/**
	 * @return the airline_code
	 */
	public String getAirline_code() {
		return airline_code;
	}
	/**
	 * @param airline_code the airline_code to set
	 */
	public void setAirline_code(String airline_code) {
		this.airline_code = airline_code;
	}
	/**
	 * @return the file_type1
	 */
	public String getFile_type1() {
		return file_type1;
	}
	/**
	 * @param file_type1 the file_type1 to set
	 */
	public void setFile_type1(String file_type1) {
		this.file_type1 = file_type1;
	}
	/**
	 * @return the file_type2
	 */
	public String getFile_type2() {
		return file_type2;
	}
	/**
	 * @param file_type2 the file_type2 to set
	 */
	public void setFile_type2(String file_type2) {
		this.file_type2 = file_type2;
	}
	/**
	 * @return the file_type3
	 */
	public String getFile_type3() {
		return file_type3;
	}
	/**
	 * @param file_type3 the file_type3 to set
	 */
	public void setFile_type3(String file_type3) {
		this.file_type3 = file_type3;
	}
	/**
	 * @return the file_type4
	 */
	public String getFile_type4() {
		return file_type4;
	}
	/**
	 * @param file_type4 the file_type4 to set
	 */
	public void setFile_type4(String file_type4) {
		this.file_type4 = file_type4;
	}
	/**
	 * @return the file_reference1
	 */
	public String getFile_reference1() {
		return file_reference1;
	}
	/**
	 * @param file_reference1 the file_reference1 to set
	 */
	public void setFile_reference1(String file_reference1) {
		this.file_reference1 = file_reference1;
	}
	/**
	 * @return the file_reference2
	 */
	public String getFile_reference2() {
		return file_reference2;
	}
	/**
	 * @param file_reference2 the file_reference2 to set
	 */
	public void setFile_reference2(String file_reference2) {
		this.file_reference2 = file_reference2;
	}
	/**
	 * @return the file_reference3
	 */
	public String getFile_reference3() {
		return file_reference3;
	}
	/**
	 * @param file_reference3 the file_reference3 to set
	 */
	public void setFile_reference3(String file_reference3) {
		this.file_reference3 = file_reference3;
	}
	/**
	 * @return the file_reference4
	 */
	public String getFile_reference4() {
		return file_reference4;
	}
	/**
	 * @param file_reference4 the file_reference4 to set
	 */
	public void setFile_reference4(String file_reference4) {
		this.file_reference4 = file_reference4;
	}
	/**
	 * @return the teletype_address1
	 */
	public String getTeletype_address1() {
		return teletype_address1;
	}
	/**
	 * @param teletype_address1 the teletype_address1 to set
	 */
	public void setTeletype_address1(String teletype_address1) {
		this.teletype_address1 = teletype_address1;
	}
	/**
	 * @return the teletype_address2
	 */
	public String getTeletype_address2() {
		return teletype_address2;
	}
	/**
	 * @param teletype_address2 the teletype_address2 to set
	 */
	public void setTeletype_address2(String teletype_address2) {
		this.teletype_address2 = teletype_address2;
	}
	/**
	 * @return the teletype_address3
	 */
	public String getTeletype_address3() {
		return teletype_address3;
	}
	/**
	 * @param teletype_address3 the teletype_address3 to set
	 */
	public void setTeletype_address3(String teletype_address3) {
		this.teletype_address3 = teletype_address3;
	}
	/**
	 * @return the teletype_address4
	 */
	public String getTeletype_address4() {
		return teletype_address4;
	}
	/**
	 * @param teletype_address4 the teletype_address4 to set
	 */
	public void setTeletype_address4(String teletype_address4) {
		this.teletype_address4 = teletype_address4;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the tty_status
	 */
	public int getTty_status() {
		return tty_status;
	}
	/**
	 * @param tty_status the tty_status to set
	 */
	public void setTty_status(int tty_status) {
		this.tty_status = tty_status;
	}
	/**
	 * @return the _DATEFORMAT
	 */
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}
	/**
	 * @param _dateformat the _DATEFORMAT to set
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}
	/**
	 * @return the _TIMEFORMAT
	 */
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}
	/**
	 * @param _timeformat the _TIMEFORMAT to set
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}
	/**
	 * @return the _TIMEZONE
	 */
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}
	/**
	 * @param _timezone the _TIMEZONE to set
	 */
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}

}