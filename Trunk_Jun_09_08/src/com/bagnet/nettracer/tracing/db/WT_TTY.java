/*
 * Created on Aug 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="wt_tty"
 */
public class WT_TTY implements Serializable {
	public int tty_id;
	public String origin_address;
	public String airline_code;
	public String file_type1;
	public String file_type2;
	public String file_type3;
	public String file_type4;
	public String file_reference1;
	public String file_reference2;
	public String file_reference3;
	public String file_reference4;
	public String teletype_address1;
	public String teletype_address2;
	public String teletype_address3;
	public String teletype_address4;
	public String text;
	public int tty_status;
	public int tty_station_id;
	public Agent tty_agent;
	public Date send_time;

	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;

	




	/**
	 * @return the teletype_address1
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.id generator-class="native" type="integer" column="tty_id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="tty_id_0"
	 * 
	 * 
	 * @return Returns the tty_id.
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
	 * @hibernate.property type="string"
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


	/**
	 * @return the airline_code
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @return the text
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="int"
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
	 * @return the tty_station_id
	 * @hibernate.property type="int"
	 */
	public int getTty_station_id() {
		return tty_station_id;
	}


	/**
	 * @param tty_station_id the tty_station_id to set
	 */
	public void setTty_station_id(int tty_station_id) {
		this.tty_station_id = tty_station_id;
	}


	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="tty_agent_id"
	 * @return Returns the tty_agent.
	 */
	public Agent getTty_agent() {
		return tty_agent;
	}


	/**
	 * @param tty_agent the tty_agent to set
	 */
	public void setTty_agent(Agent tty_agent) {
		this.tty_agent = tty_agent;
	}


	/**
	 * @hibernate.property type="timestamp"
	 * @return Returns the send_time.
	 */
	public Date getSend_time() {
		return send_time;
	}


	/**
	 * @param send_time the send_time to set
	 */
	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}



}